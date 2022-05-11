package com.shine.recruiter.tests;

import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.InternalServerErrorException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;


public class Test_isJobPublished extends TestBaseSetup {

    String url = "";
    String jobid = "9154082";

    String job_title = "";
    String recruiter_name = "";
    String recruiter_mno = "";
    String recruiter_email = "";
    String recruiter_company_detail = "";
    String recruiter_company_name = "";
    String job_salary = "";
    String job_description = "";

    String job_location = "";
    String job_keyword = "";
    String job_functional_area = "";

    String job_experience = "";

    String job_skills = "";
    String job_other_skills = "";
    String job_industry = "";

    @BeforeClass
    public void TestSetup() {
        APP_LOGS.debug("Start of job publish verification test");
        if (baseUrl.contains("sumosr")) {
            url = "https://sumosc.shine.com";
        } else {
            url = "https://www.shine.com";
        }
        set_actual_job_data_fromApi(jobid);
    }

    @Test(priority = 0)
    public void verify_job_title() {
        String expected = "Opening in Shine Learning, HT Media, CAREER COUNSELORS";
        assert_result(job_title, expected, false);
    }

    @Test(priority = 1)
    public void verify_company_name() {
        String expected = "EQS PLACEMENTS PVT LTD";
        assert_result(recruiter_company_name, expected, false);
    }

    @Test(priority = 2)
    public void verify_funcational_area() {
        String expected = "Career / Education Counselling";
        assert_result(job_functional_area, expected, true);
    }


    /**
     * Assert result
     *
     * @param actual
     * @param expected
     * @param isSpecialCase- to match array containing particular elements
     */
    public void assert_result(String actual, String expected, boolean isSpecialCase) {
        if (isSpecialCase)
            Assert.assertTrue(actual.contains(expected), actual);
        else
            Assert.assertEquals(actual, expected);
    }


    /*GET API RESPONSE*/
    public void set_actual_job_data_fromApi(String jobid) {
        try {
            String api_url = baseUrl + "/api/v2/search/job-description/" + jobid + "/";
            ResteasyClient client = new ResteasyClientBuilder().build();
            ResteasyWebTarget target = client.target(api_url);
            APP_LOGS.debug(api_url);
            Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .get();
            String api_result = response.readEntity(String.class);
            APP_LOGS.debug("JD Page Api Response: " + api_result);
            int status = response.getStatus();
            APP_LOGS.info("status: " + status);
            Assert.assertEquals(status, 200);
            parse_api_response(api_result);
            response.close();
            client.close();
        } catch (InternalServerErrorException ispe) {
            APP_LOGS.debug(ispe.getMessage());
            ispe.printStackTrace();
        } catch (ServiceUnavailableException ispe) {
            APP_LOGS.debug(ispe.getMessage());
        } catch (WebApplicationException ispe) {
            APP_LOGS.debug(ispe.getMessage());
        } catch (BadRequestException ispe) {
            APP_LOGS.debug(ispe.getMessage());
        }
    }


    private void parse_api_response(String response) {
        JSONObject obj = new JSONObject(response);
        JSONObject jsonObject = (JSONObject) obj;
        APP_LOGS.debug(jsonObject);
        int count = jsonObject.getInt("count");
        Assert.assertTrue(count > 0, String.valueOf(count));
        JSONArray resultArray = (JSONArray) jsonObject.get("results");
        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject _JSONObject = (JSONObject) resultArray.get(i);
            job_title = _JSONObject.getString("jJT");
            recruiter_name = _JSONObject.getString("jRN");
            recruiter_mno = _JSONObject.getString("jRP");

            recruiter_email = _JSONObject.getString("jRE");
            recruiter_company_detail = _JSONObject.getString("jCD");
            recruiter_company_name = _JSONObject.getString("jCName");
            job_salary = _JSONObject.getString("jSal");
            job_description = _JSONObject.getString("jJD");

            job_location = _JSONObject.get("jLoc").toString();// Hack: User contains to check, as it contain array
            job_keyword = _JSONObject.getString("jKwd");
            job_functional_area = _JSONObject.get("jArea").toString();
            ;// Hack: User contains to check, as it contain array
            job_experience = _JSONObject.getString("jExp");
            job_skills = _JSONObject.getString("jKwds");
            job_other_skills = _JSONObject.getString("jKwdns");
            job_industry = _JSONObject.getString("jInd");

        }

    }

}
