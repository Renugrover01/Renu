package com.shine.tests;

import java.util.ArrayList;
import org.testng.annotations.Test;
import com.shine.base.TestBaseSetup;
import com.shine.common.utils.MarkProfileBadAPI;

public class Test_MarkProfileBad extends TestBaseSetup{


	@Test
	public void mark_profile_bad() {
		ArrayList<String> setBad = new ArrayList<>();
		ArrayList<String> notSetBad = new ArrayList<>();
		MarkProfileBadAPI _MarkProfileBadAPI = new MarkProfileBadAPI();
		String access_token = _MarkProfileBadAPI.get_access_token();
		APP_LOGS.info("mark_profile_bad token: "+access_token);
		APP_LOGS.info("Email list array: "+_Utility.email_list);
		int counter = 0;
		for(int i=0;i<_Utility.email_list.size();i++) {
			boolean isBad = _MarkProfileBadAPI.set_profile_bad_api(_Utility.email_list.get(i), access_token);
			try {
				if(isBad){
					setBad.add(_Utility.email_list.get(i));
				}
				else {
					notSetBad.add(_Utility.email_list.get(i));
					counter++;
				}
			}catch(Throwable t) {
				APP_LOGS.error("mark_profile_bad: "+t.getMessage());
			}
		}

		APP_LOGS.error(counter +" Email id not set to bad: "+notSetBad);
		APP_LOGS.info("Email id set to bad: "+setBad);


	}


}
