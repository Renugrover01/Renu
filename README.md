# SHINE CANDIDATE AUTOMATION TEST SUITE

This project is developed to test the Shine Candidate web site. 


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

* Maven
* Java 8 JDK
* ChromeDriver 2.38 or latest
* Jenkins
* Eclipse

### Installation / Requirements

 * Step 1: Install and verify Java installation on your machine.
 * Step 2: Set JAVA environment.
 * Step 3: Download Maven archive.
 * Step 4: Extract the Maven archive.
 * Step 5: Set Maven environment variables.
 * Step 6: Add Maven bin directory location to system path.
 * Step 7: Verify Maven installation.
 * Step 8: Download Latest chromedriver.exe from [here](http://chromedriver.storage.googleapis.com/index.html).
 * Step 9: Set chromedriver.exe path to D:\selenium_driver or change it in the project CONFIG file.


## Deployment
### Jenkins - Run code from GIT.
  * Repo url - https://github.com/firefly-eventures/QA.git
  * Branch Name: refs/remotes/origin/{Your branch name}
  * Maven goal: -X clean test exec:java -Dexec.mainClass="com.shine.msite.utils.EmailUtil"
  * SAVE & APPLY


### Tests Execution

  * pom.xml (Recommended).
  * TestNG.xml.
  * Test Can also be run directly from the {test}.java files.
  
  
## Bug Reports

When creating/opening new issues or commenting on existing issues please make sure the issue is throughly checked on the Shine front end and backend level and should not be a false positive from the Selenium software.

It's imperative that issue reports outline the steps to reproduce the defect. If the issue can't be reproduced it will be closed.
  


## API Reference

* Search API - baseURL/api/v2/search/simple/?q={JOB-ID}
* Insight API - baseURL/api/v2/search/application-insights/?q={JOB-ID}


## Authors

**Creator & Maintainer**
* Abhishek Dhoundiyal <abhishek.dhoundiyal@hindustantimes.com>
	* Created new code structure and framework.
	* Added Maven and github support.
	




## License

This site is controlled and operated by Shine.com. All material on this repository, including code, data, credentials, report and logo's are protected by copyrights, trademarks, and other intellectual property rights. Material on repository is only for internal use of HT Media Ltd. You must not copy, reproduce, republish, upload, post, transmit or distribute such material in any way, including by email or other electronic means and whether directly or indirectly and You must not assist any other person to do so. Without the prior written consent of the owner, modification of the materials, use of the materials on any other website or networked computer environment or use of the materials for any purpose is a violation of the copyrights, trademarks and other proprietary rights, and is prohibited.