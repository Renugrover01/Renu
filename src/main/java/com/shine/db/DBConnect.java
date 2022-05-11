package com.shine.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class DBConnect {

	/*For test purpose
	 * public static void main(String[] args) {
		insertData("http://www.shine.com", 8,7,1,2);
	}*/


    public void insertData(String url, int total, int passed, int failed, int skipped) {
        List<BasicDBObject> data = parseResultXML();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        //get current date time with Date()
        Date date = new Date();
        String timestamp = dateFormat.format(date);
        try {
            MongoClientURI uri = new MongoClientURI(
                    "mongodb://abhishekdh:redhat@logdb-shard-00-00-stfvg.mongodb.net:27017,logdb-shard-00-01-stfvg.mongodb.net:27017,logdb-shard-00-02-stfvg.mongodb.net:27017/admin?ssl=true&replicaSet=logdb-shard-0&authSource=admin&retryWrites=true");
            MongoClient mongo = new MongoClient(uri);
            @SuppressWarnings("deprecation")
            DB db = mongo.getDB("shine_db");
            DBCollection collection = db.getCollection("shine_logs");
            BasicDBObject document = new BasicDBObject();
            document.put("url", url);
            document.put("total", total);
            document.put("passed", passed);
            document.put("failed", failed);
            document.put("skipped", skipped);
            document.put("fail_test", data);
            document.put("error", getErrors());
            document.put("logs", getLogs());
            document.put("log_creation_date", timestamp);
            document.put("log_creation_time", timeFormat.format(date.getTime()));
            document.put("createdDate", new Date());
            document.put("flag", 0);

            collection.insert(document);
            mongo.close();
            System.out.println("Inserted in db");

        } catch (Exception e) {
            System.out.println("DB Insertion error"+e.getMessage());
        }
    }

    private List<BasicDBObject> getErrors() {
        List<BasicDBObject> errorList = new ArrayList<>();
        try (
            FileInputStream fstream = new FileInputStream(System.getProperty("user.dir") + "/logs/DBApplication.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        )
            {  String strLine;
            /* read log line by line */
            while ((strLine = br.readLine()) != null) {
                BasicDBObject data = new BasicDBObject();
                /* parse strLine to obtain what you want */

                data.put("source_url", StringUtils.substringBefore(strLine, ", found:").replace("Source:", "").trim());
                data.put("found_code", StringUtils.substringBetween(strLine, "found: ", "status"));
                data.put("status_code", StringUtils.substringAfter(strLine, "status: "));
                System.out.println(strLine);
                errorList.add(data);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return errorList;
    }


    private static String getLogs() {
        FileInputStream fis = null;
        try {
            File file = new File(System.getProperty("user.dir") + "/logs/DBApplication.log");
            fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            int count = 0;
            String str = "";
            while ((count = fis.read(data)) > 0) {
                str = new String(data, "UTF-8");
            }
            return str;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception ignore) {
                    // Nothing to do
                }
            }
        }
    }

    private static List<BasicDBObject> parseResultXML() {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(System.getProperty("user.dir") + "/target/surefire-reports/testng-failed.xml"));
            // normalize text representation
            doc.getDocumentElement().normalize();
            return getNodeValue(doc);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }

    }

    public static List<BasicDBObject> getNodeValue(Document doc) {
        ArrayList<BasicDBObject> testCaseList = new ArrayList<>();
        NodeList results = doc.getElementsByTagName("test");
        for (int i = 0; i < results.getLength(); i++) {
            BasicDBObject data = new BasicDBObject();
            try {
                Element firstNameElement = (Element) results.item(i);
                NamedNodeMap testNameTag = firstNameElement.getAttributes();
                String failedtestname = testNameTag.getNamedItem("name").getNodeValue().replace("(failed)", "");
                System.out.println(failedtestname);
                try {
                    data.put("test_name", failedtestname);
                } catch (Throwable t) {
                    data.append("test_name", "N/A");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            NodeList results2 = doc.getElementsByTagName("class");
            try {
                Element secondNameElement = (Element) results2.item(i);
                NamedNodeMap testCaseTag = secondNameElement.getAttributes();
                String failedtestcase = testCaseTag.getNamedItem("name").getNodeValue().replace("(failed)", "");
                System.out.println(failedtestcase);
                try {
                    data.put("test_case", failedtestcase);
                } catch (Throwable t) {
                    data.append("test_case", "N/A");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            testCaseList.add(data);
        }
        return testCaseList;
    }

}
