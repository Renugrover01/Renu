package com.shine.api;

import static org.testng.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;


import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONObject;

import com.shine.base.TestBaseSetup;

import com.shine.beans.CandidateStatus;


public class RevivalAPI extends TestBaseSetup {

    String api_url = "";

    /*GET API RESPONSE*/
    public String resetCandidateProfileTimestamp(String email) {

        try {
            String url = "https://sumosc.shine.com/candidate-reset-profile-timestamps/?format=json";
            Response response = null;
            System.out.println(url);
            ResteasyClient client = new ResteasyClientBuilder().build();
            ResteasyWebTarget target = client.target(url);
            String jsonString = "";

            jsonString = new JSONObject()
                    .put("email", email)
                    .put("profile_fields", new JSONObject()
                            .put("last_login", "2011-01-01")
                            .put("resume_timestamp", "2011-01-01")
                            .put("total_experience_last_modified", "2011-01-01")
                            .put("salary_last_modified", "2011-01-01")
                            .put("last_revival_date", JSONObject.NULL)
                            .put("revival_vendor_id", JSONObject.NULL)
                            .put("last_experience_modified", "2011-01-01")).toString();

            Entity<?> payload = Entity.json(jsonString);
            response = target.request()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .post(payload);
            String resp = response.readEntity(String.class);
            APP_LOGS.debug("Revival API Response" + resp);
            int status = response.getStatus();
            assertEquals(status, 201);
            response.close();
            client.close();

            return resp;
        } catch (Exception e) {
            APP_LOGS.error("error in resetCandidateProfileTimestamp: " + e.getMessage());
            return "Failure :" + e.getMessage();
        }
    }


    /*GET API RESPONSE*/
    public CandidateStatus getCandidateProfileStatus(String email) {
        CandidateStatus _CandidateStatus = new CandidateStatus();
        String url = "https://sumosc.shine.com/candidate-profile-db-states-status/?email=" + email;
        try {
            Response response = null;
            System.out.println(url);
            ResteasyClient client = new ResteasyClientBuilder().build();
            ResteasyWebTarget target = client.target(url);
            response = target.request()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .get();

            String resp = response.readEntity(String.class);
            APP_LOGS.debug("Revival API Response" + resp);
            @SuppressWarnings("unused")
            int status = response.getStatus();
            response.close();
            client.close();
            _CandidateStatus = jParser(resp, _CandidateStatus);
            return _CandidateStatus;
        } catch (Exception e) {
            APP_LOGS.error("error in getCandidateProfileStatus: " + e.getMessage());
            return null;
        }
    }


    /*Parse json response*/
    public CandidateStatus jParser(String response, CandidateStatus _CandidateStatus) {
        try {
            JSONObject obj = new JSONObject(response);
            JSONObject jsonObject = (JSONObject) obj;
            _CandidateStatus.setMid_out(jsonObject.getInt("mid_out"));
            _CandidateStatus.setResume_mid_out(jsonObject.getInt("resume_mid_out"));
            _CandidateStatus.setLast_revival_date(jsonObject.get("last_revival_date").toString());
            _CandidateStatus.setRevival_vendor_id(jsonObject.get("revival_vendor_id").toString());
            _CandidateStatus.setExit_reason(jsonObject.getInt("exit_reason"));
            _CandidateStatus.setExit_date(jsonObject.get("exit_date").toString());
            _CandidateStatus.setComputed_exit_date(jsonObject.get("computed_exit_date").toString());

            return _CandidateStatus;
        } catch (Exception ex) {
            APP_LOGS.error("No data found: " + ex.getMessage());
            return null;
        }

    }


    /*GET API RESPONSE*/
    public String resetUpdateFlowAPI(String email) {
        String resetUpdateFlow = "https://sumosc.shine.com/myshine/reset-profile-update-flows/?email=" + email;
        try {
            String url = resetUpdateFlow;
            System.out.println(url);
            ResteasyClient client = new ResteasyClientBuilder().build();
            ResteasyWebTarget target = client.target(url);
            Response response = target.request()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .get();
            String value = response.readEntity(String.class);
            @SuppressWarnings("unused")
            int status = response.getStatus();
            response.close();
            client.close();
            return value;

        } catch (Exception e) {
            APP_LOGS.error("error in resetUpdateFlowAPI " + e.getMessage());
            return "Failure :" + e.getMessage();
        }
    }


}
