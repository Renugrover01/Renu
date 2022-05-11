package com.shine.db;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class GetDBData {

	public static void main(String[] args) {
		getData();

	}

	public static void getData(){
		MongoClientURI uri = new MongoClientURI(
				"mongodb://abhishekdh:redhat@logdb-shard-00-00-stfvg.mongodb.net:27017,logdb-shard-00-01-stfvg.mongodb.net:27017,logdb-shard-00-02-stfvg.mongodb.net:27017/admin?ssl=true&replicaSet=logdb-shard-0&authSource=admin&retryWrites=true");

		try(MongoClient mongo = new MongoClient(uri);)
		{

			@SuppressWarnings("deprecation")
			DB db = mongo.getDB("shine_db");
			DBCollection collection = db.getCollection("shine_logs");

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("url", "https://www.shine.com");
			DBCursor cursor = collection.find(whereQuery);
			while(cursor.hasNext()) {
				DBObject document = cursor.next();
				if(document.get("error")!=null) {
					BasicDBList errorList = (BasicDBList) document.get("error");
					for(int i =0; i<errorList.size();i++) {
						System.out.println(errorList.get(i));
						BasicDBObject query = new BasicDBObject();
						//query.put("error" , new BasicDBObject("status_code", 500));
						query.put("error" , new BasicDBObject("status_code", "404"));
						long count = collection.count(query);
						System.out.println(count);
					}
				}

			}
			System.out.println("Search operation done");

		}
		catch (Exception e)
		{
			System.out.println("DB Insertion error "+e.getMessage());
		}
	}


}
