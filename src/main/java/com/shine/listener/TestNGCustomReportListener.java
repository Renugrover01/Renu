package com.shine.listener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


import org.testng.IInvokedMethod;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.collections.Lists;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;


public class TestNGCustomReportListener implements IReporter{

	private PrintWriter writer;
	private int m_row;
	private Integer m_testIndex;
	private int m_methodIndex;
	private String reportTitle= "Shine Candidate Test Report";
	private String reportFileName = "custom-report.html";


	/** Creates summary of the run */
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outdir) {
		try {
			writer = createWriter(outdir);
		} catch (IOException e) {
			System.err.println("Unable to create output file");
			e.printStackTrace();
			return;
		}

		startHtml(writer);
		//writeReportTitle(reportTitle);
		generateSuiteSummaryReport(suites);
		generateMethodSummaryReport(suites);
		generateMethodDetailReport(suites);
		endHtml(writer);
		writer.flush();
		writer.close();
	}

	protected PrintWriter createWriter(String outdir) throws IOException {
		new File(outdir).mkdirs();
		return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, reportFileName))));
	}

	/**
	 * Creates a table showing the highlights of each test method with links to
	 * the method details
	 */
	protected void generateMethodSummaryReport(List<ISuite> suites) {
		m_methodIndex = 0;
		startResultSummaryTable("methodOverview");
		int testIndex = 1;
		for (ISuite suite : suites) {
			if (suites.size() >= 1) {
				titleRow(suite.getName(), 5);
			}

			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				String testName = testContext.getName();
				m_testIndex = testIndex;
				resultSummary(suite, testContext.getFailedConfigurations(), testName, "failed", " (configuration methods)");
				resultSummary(suite, testContext.getFailedTests(), testName, "failed", "");
				resultSummary(suite, testContext.getSkippedConfigurations(), testName, "skipped", " (configuration methods)");
				resultSummary(suite, testContext.getSkippedTests(), testName, "skipped", "");
				resultSummary(suite, testContext.getPassedConfigurations(), testName, "passed", " (configuration methods)");
				resultSummary(suite, testContext.getPassedTests(), testName, "passed", "");
				testIndex++;
			}
		}
		writer.println("</table>");
	}

	/** Creates a section showing known results for each method */
	protected void generateMethodDetailReport(List<ISuite> suites) {
		m_methodIndex = 0;
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				if (r.values().size() > 0) {
					writer.println("<h1>" + testContext.getName() + "</h1>");
				}
				resultDetail(testContext.getFailedConfigurations());
				resultDetail(testContext.getFailedTests());
				resultDetail(testContext.getSkippedConfigurations());
				resultDetail(testContext.getSkippedTests());
				resultDetail(testContext.getPassedConfigurations());
				resultDetail(testContext.getPassedTests());
			}
		}
	}

	/**
	 * @param tests
	 */
	private void resultSummary(ISuite suite, IResultMap tests, String testname,
			String style, String details) {

		if (tests.getAllResults().size() > 0) {
			StringBuffer buff = new StringBuffer();
			String lastClassName = "";
			int mq = 0;
			int cq = 0;
			for (ITestNGMethod method : getMethodSet(tests, suite)) {
				m_row += 1;
				m_methodIndex += 1;
				ITestClass testClass = method.getTestClass();
				String className = testClass.getName();
				if (mq == 0) {
					String id = (m_testIndex == null ? null : "t"
							+ Integer.toString(m_testIndex));
					titleRow(testname + " &#8212; " + style + details, 5, id);
					m_testIndex = null;
				}
				if (!className.equalsIgnoreCase(lastClassName)) {
					if (mq > 0) {
						cq += 1;
						writer.print("<tr class='" + style
								+ (cq % 2 == 0 ? "even" : "odd") + "'>"
								+ "<td");
						if (mq > 1) {
							writer.print(" rowspan='" + mq + "'");
						}
						writer.println(">" + lastClassName + "</td>" + buff);
					}
					mq = 0;
					buff.setLength(0);
					lastClassName = className;
				}
				Set<ITestResult> resultSet = tests.getResults(method);
				long end = Long.MIN_VALUE;
				long start = Long.MAX_VALUE;
				long startMS=0;
				String firstLine="";

				for (ITestResult testResult : tests.getResults(method)) {
					if (testResult.getEndMillis() > end) {
						end = testResult.getEndMillis()/1000;
					}
					if (testResult.getStartMillis() < start) {
						startMS = testResult.getStartMillis();
						start =startMS/1000;
					}

					Throwable exception=testResult.getThrowable();
					boolean hasThrowable = exception != null;
					if(hasThrowable){
						@SuppressWarnings("deprecation")
						String str = Utils.stackTrace(exception, true)[0];
						@SuppressWarnings("resource")
						Scanner scanner = new Scanner(str);
						firstLine = scanner.nextLine();
					}
				}
				DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(startMS);

				mq += 1;
				if (mq > 1) {
					buff.append("<tr class='" + style
							+ (cq % 2 == 0 ? "odd" : "even") + "'>");
				}
				String description = method.getDescription();
				String testInstanceName = resultSet
						.toArray(new ITestResult[] {})[0].getTestName();
				buff.append("<td><a href='#m"
						+ m_methodIndex
						+ "'>"
						+ qualifiedName(method)
						+ " "
						+ (description != null && description.length() > 0 ? "('" + description + "')"
								: "")
						+ "</a>"
						+ (null == testInstanceName ? "" : "<br>("
								+ testInstanceName + ")") + "</td>"
								+ "<td class='numi' style='text-align:left;padding-right:2em'>" + firstLine+"<br/></td>"
								+ "<td style='text-align:right'>" + formatter.format(calendar.getTime()) + "</td>" + "<td class='numi'>"
								+ timeConversion(end - start) + "</td>" + "</tr>");

			}
			if (mq > 0) {
				cq += 1;
				writer.print("<tr class='" + style + (cq % 2 == 0 ? "even" : "odd") + "'>" + "<td");
				if (mq > 1) {
					writer.print(" rowspan='" + mq + "'");
				}
				writer.println(">" + lastClassName + "</td>" + buff);
			}
		}
	}


	private String timeConversion(long seconds) {

		final int MINUTES_IN_AN_HOUR = 60;
		final int SECONDS_IN_A_MINUTE = 60;

		int minutes = (int) (seconds / SECONDS_IN_A_MINUTE);
		seconds -= minutes * SECONDS_IN_A_MINUTE;

		int hours = minutes / MINUTES_IN_AN_HOUR;
		minutes -= hours * MINUTES_IN_AN_HOUR;

		return prefixZeroToDigit(hours) + ":" + prefixZeroToDigit(minutes) + ":" + prefixZeroToDigit((int)seconds);
	}

	private String prefixZeroToDigit(int num){
		int number=num;
		if(number<=9){
			String sNumber="0"+number;
			return sNumber;
		}
		else
			return ""+number;

	}

	/** Starts and defines columns result summary table */
	private void startResultSummaryTable(String style) {
		tableStart(style, "summary");
		writer.println("<tr><th>Class</th>"
				+ "<th>Method</th><th>Exception Info</th><th>Start Time </th><th>Execution Time<br/>(hh:mm:ss)</th></tr>");
		m_row = 0;
	}

	private String qualifiedName(ITestNGMethod method) {
		StringBuilder addon = new StringBuilder();
		String[] groups = method.getGroups();
		int length = groups.length;
		if (length > 0 && !"basic".equalsIgnoreCase(groups[0])) {
			addon.append("(");
			for (int i = 0; i < length; i++) {
				if (i > 0) {
					addon.append(", ");
				}
				addon.append(groups[i]);
			}
			addon.append(")");
		}

		return "<b>" + method.getMethodName() + "</b> " + addon;
	}

	private void resultDetail(IResultMap tests) {
		Set<ITestResult> testResults=tests.getAllResults();
		List<ITestResult> testResultsList = new ArrayList<ITestResult>(testResults);
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		System.setProperty("java.util.Collections.useLegacyMergeSort", "true");
		Collections.sort(testResultsList, new TestResultsSorter());
		for (ITestResult result : testResultsList) {
			ITestNGMethod method = result.getMethod();
			m_methodIndex++;
			String cname = method.getTestClass().getName();
			writer.println("<h2 id='m" + m_methodIndex + "'>" + cname + ":"
					+ method.getMethodName() + "</h2>");
			Set<ITestResult> resultSet = tests.getResults(method);
			generateResult(result, method, resultSet.size());
			writer.println("<p class='totop'><a href='#summary'>back to summary</a></p>");

		}
	}

	private void generateResult(ITestResult ans, ITestNGMethod method,
			int resultSetSize) {
		Object[] parameters = ans.getParameters();
		boolean hasParameters = parameters != null && parameters.length > 0;
		if (hasParameters) {
			tableStart("result", null);
			writer.print("<tr class='param'>");
			for (int x = 1; x <= parameters.length; x++) {
				writer.print("<th>Param." + x + "</th>");
			}
			writer.println("</tr>");
			writer.print("<tr class='param stripe'>");
			for (Object p : parameters) {
				writer.println("<td>" + Utils.escapeHtml(Utils.toString(p))
				+ "</td>");
			}
			writer.println("</tr>");
		}
		List<String> msgs = Reporter.getOutput(ans);
		boolean hasReporterOutput = msgs.size() > 0;
		Throwable exception = ans.getThrowable();
		boolean hasThrowable = exception != null;
		if (hasReporterOutput || hasThrowable) {
			if (hasParameters) {
				writer.print("<tr><td");
				if (parameters.length > 1) {
					writer.print(" colspan='" + parameters.length + "'");
				}
				writer.println(">");
			} else {
				writer.println("<div>");
			}
			if (hasReporterOutput) {
				if (hasThrowable) {
					writer.println("<h3>Test Messages</h3>");
				}
				for (String line : msgs) {
					writer.println(line + "<br/>");
				}
			}
			if (hasThrowable) {
				boolean wantsMinimalOutput = ans.getStatus() == ITestResult.SUCCESS;
				if (hasReporterOutput) {
					writer.println("<h3>"
							+ (wantsMinimalOutput ? "Expected Exception"
									: "Failure") + "</h3>");
				}
				generateExceptionReport(exception, method);
			}
			if (hasParameters) {
				writer.println("</td></tr>");
			} else {
				writer.println("</div>");
			}
		}
		if (hasParameters) {
			writer.println("</table>");
		}
	}

	@SuppressWarnings("deprecation")
	protected void generateExceptionReport(Throwable exception, ITestNGMethod method) {
		writer.print("<div class='stacktrace'>");
		writer.print(Utils.stackTrace(exception, true)[0]);
		writer.println("</div>");
	}

	/**
	 * Since the methods will be sorted chronologically, we want to return the
	 * ITestNGMethod from the invoked methods.
	 */
	private Collection<ITestNGMethod> getMethodSet(IResultMap tests, ISuite suite) {

		List<IInvokedMethod> r = Lists.newArrayList();
		List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
		for (IInvokedMethod im : invokedMethods) {
			if (tests.getAllMethods().contains(im.getTestMethod())) {
				r.add(im);
			}
		}

		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		System.setProperty("java.util.Collections.useLegacyMergeSort", "true");
		Collections.sort(r,new TestSorter());
		List<ITestNGMethod> result = Lists.newArrayList();

		// Add all the invoked methods
		for (IInvokedMethod m : r) {
			for (ITestNGMethod temp : result) {
				if (!temp.equals(m.getTestMethod()))
					result.add(m.getTestMethod());
			}
		}

		// Add all the methods that weren't invoked (e.g. skipped) that we
		// haven't added yet
		Collection<ITestNGMethod> allMethodsCollection=tests.getAllMethods();
		List<ITestNGMethod> allMethods=new ArrayList<ITestNGMethod>(allMethodsCollection);
		Collections.sort(allMethods, new TestMethodSorter());

		for (ITestNGMethod m : allMethods) {
			if (!result.contains(m)) {
				result.add(m);
			}
		}
		return result;
	}

	@SuppressWarnings("unused")
	public void generateSuiteSummaryReport(List<ISuite> suites) {
		tableStart("testOverview", null);
		writeReportTitle(reportTitle);
		writeReportStatus();
		writer.print("<tr>");
		tableColumnStart("Test Case");
		tableColumnStart("Passed");
		tableColumnStart("Skipped");
		tableColumnStart("Failed");
		tableColumnStart("Browser");
		tableColumnStart("Start Time<br/>(hh:mm:ss)");
		tableColumnStart("End Time<br/>(hh:mm:ss)");
		tableColumnStart("Total Time</br> (hh:mm:ss)");
		/*tableColumnStart("Included<br/>Groups");
		tableColumnStart("Excluded<br/>Groups");*/

		writer.println("</tr>");
		NumberFormat formatter = new DecimalFormat("#,##0.0");
		int qty_tests = 0;
		int qty_pass_m = 0;
		int qty_skip = 0;
		long time_start = Long.MAX_VALUE;
		int qty_fail = 0;
		long time_end = Long.MIN_VALUE;
		long time_start2 = Long.MAX_VALUE;
		long time_end2 = Long.MIN_VALUE;
		m_testIndex = 1;
		for (ISuite suite : suites) {
			if (suites.size() >= 1) {
				titleRow(suite.getName(), 10);
			}
			Map<String, ISuiteResult> tests = suite.getResults();
			for (ISuiteResult r : tests.values()) {
				qty_tests += 1;
				ITestContext overview = r.getTestContext();

				startSummaryRow(overview.getName());
				int q = getMethodSet(overview.getPassedTests(), suite).size();
				qty_pass_m += q;
				summaryCell(q, Integer.MAX_VALUE);
				q = getMethodSet(overview.getSkippedTests(), suite).size();
				qty_skip += q;
				summaryCell(q, 0);
				q = getMethodSet(overview.getFailedTests(), suite).size();
				qty_fail += q;
				summaryCell(q, 0);

				// Write OS and Browser
				summaryCell(suite.getParameter("browserType"), true);
				writer.println("</td>");

				SimpleDateFormat summaryFormat = new SimpleDateFormat("hh:mm:ss");
				summaryCell(summaryFormat.format(overview.getStartDate()),true);				
				writer.println("</td>");

				summaryCell(summaryFormat.format(overview.getEndDate()),true);
				writer.println("</td>");

				time_start = Math.min(overview.getStartDate().getTime(), time_start);
				time_end = Math.max(overview.getEndDate().getTime(), time_end);
				summaryCell(timeConversion((overview.getEndDate().getTime() - overview.getStartDate().getTime()) / 1000), true);

				/*summaryCell(overview.getIncludedGroups());
				summaryCell(overview.getExcludedGroups());*/
				writer.println("</tr>");
				m_testIndex++;
			}
		}
		if (qty_tests > 0) {
			
			writer.println("<tr class='total'><td>Total</td>");
			summaryCell(qty_pass_m, Integer.MAX_VALUE);
			summaryCell(qty_skip, 0);
			summaryCell(qty_fail, 0);
			summaryCell(" ", true);
			summaryCell(" ", true);
			summaryCell(" ", true);
			summaryCell(timeConversion(((time_end - time_start) / 1000)), true);
			writer.println("</tr>");
			createGraph(qty_tests, qty_pass_m, qty_fail, qty_skip);
		}
		writer.println("</table>");
	}

	/*
	 * Create Graph
	 * 
	 */
	private void createGraph(int tocase,int Passedcase,int failedItems,int skipitems) {
		//writer.print("<center><h3>" + title + " - " + getDateAsString() + "</h3></center>");
		try {
			System.out.println("Passed case: "+Passedcase);
			System.out.println("Failed case: "+failedItems);
			System.out.println("Skipped case: "+skipitems);
			float totalCases = Passedcase + failedItems + skipitems;
			int passedPercent = Math.round((Passedcase * 100) / totalCases);
			int failedPercent = Math.round((failedItems * 100) / totalCases);
			int skippedPercent = Math.round((skipitems * 100) / totalCases);
			System.out.println(totalCases);
			System.out.println("Passed case %: "+passedPercent);
			System.out.println("Failed case %: "+failedPercent);
			System.out.println("Skipped case %: "+skippedPercent);
			String skippdata="";
			String faileddata="";
			String passedddata="";
			if(skippedPercent>4){
				skippdata ="<div class='cases skipped' style='margin-bottom: 6px; background: #f1c40f;color: white;text-align:center;font-weight: 400;display: inline-block;width:"+skippedPercent+"%;'><b>"+skippedPercent+"%</b></div><br>";
			}
			else if(skippedPercent>0){
				skippdata ="<div class='cases skipped' style='margin-bottom: 6px; background: #f1c40f;color: white;text-align:center;font-weight: 400;display: inline-block;width:4%;'><b>"+skippedPercent+"%</b></div><br>";
				
			}
			if(failedPercent>4){
				faileddata ="<div class='cases failed' style='margin-bottom: 6px; background: #FA8072;color: white;text-align:center;font-weight: 400;display: inline-block;width:"+failedPercent+"%;'><b>"+failedPercent+"%</b></div><br>";
			}
			else if(failedPercent>0){
				faileddata ="<div class='cases failed' style='margin-bottom: 6px; background: #FA8072;color: white;text-align:center;font-weight: 400;display: inline-block;width:4%;'><b>"+failedPercent+"%</b></div><br>";
				
			}
			if(passedPercent>4){
				passedddata ="<div class='cases passed' style='margin-bottom:6px;background: #27ae60;color:white;text-align:center;font-weight: 400;display: inline-block;width:"+passedPercent+"%;'><b>"+passedPercent+"%</b></div><br>";
			}
			else if(passedPercent>0){
				passedddata ="<div class='cases failed' style='margin-bottom: 6px; background: #FA8072;color: white;text-align:center;font-weight: 400;display: inline-block;width:4%;'><b>"+passedPercent+"%</b></div><br>";
				
			}
			
			
			
			writer.print("<tr><td colspan='8'> "
					+ "<h4>Result Analysis:</h4>"
					+ passedddata
					+ faileddata
					+ skippdata	
					+ "</td></tr>");

		} catch(Exception e){
			
		}
		

	}

	@SuppressWarnings("unused")
	private void summaryCell(String[] val) {
		StringBuffer b = new StringBuffer();
		for (String v : val) {
			b.append(v + " ");
		}
		summaryCell(b.toString(), true);
	}

	private void summaryCell(String v, boolean isgood) {
		writer.print("<td class='numi" + (isgood ? "" : "_attn") + "'>" + v
				+ "</td>");
	}

	private void startSummaryRow(String label) {
		m_row += 1;
		writer.print("<tr"
				+ (m_row % 2 == 0 ? " class='stripe'" : "")
				+ "><td style='text-align:left;padding-right:2em'><a href='#t"
				+ m_testIndex + "'><b>" + label + "</b></a>" + "</td>");

	}

	private void summaryCell(int v, int maxexpected) {
		summaryCell(String.valueOf(v), v <= maxexpected);
	}

	private void tableStart(String cssclass, String id) {
		writer.println("<table align='center' cellpadding='0' cellspacing='0' border='0' width='100%' style='border-radius: 9px;box-shadow: 2px 4px 75px #bdbdbd;max-width:600px;font-size:15px;background-color:#fff;' "
				+ (cssclass != null ? " class='" + cssclass + "'"
						: " style='padding-bottom:2em'")
				+ (id != null ? " id='" + id + "'" : "") + ">");
		m_row = 0;
	}

	private void tableColumnStart(String label) {
		writer.print("<th>" + label + "</th>");
	}
	
	/*New updates which change row background color*/
/*	private void tableColumnStart_withBgColor(String label, String color) {
		writer.print("<th bgcolor="+color+">" + label + "</th>");
	}*/
	

	private void titleRow(String label, int cq) {
		titleRow(label, cq, null);
	}

	private void titleRow(String label, int cq, String id) {
		writer.print("<tr");
		if (id != null) {
			writer.print(" id='" + id + "'");
		}
		writer.println("><th colspan='" + cq + "'>" + label + "</th></tr>");
		m_row = 0;
	}

	protected void writeReportTitle(String title) {
		writer.print("<tr> <td bgcolor=#82BD1A width=16.6%></td><td bgcolor=#00C0E4 width=16.6%></td><td bgcolor=#EAC14D width=16.6%></td><td bgcolor=#E6567A width=16.6%></td><td bgcolor=#5BD999 width=16.6%></td><td bgcolor=#7658F8 width=16.6%></td><td bgcolor=#82BD1A width=16.6%></td><td bgcolor=#00C0E4 width=16.6%></td></tr>");
		writer.print("<tr><td colspan='8' style='/*background-color:#0a69b7 !important;*/padding:10px 25px;'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td align='left' width='15%' style='border:0px !important;'><a href='#m_-5457254916091177664_'>"
				+ "<img src='https://ci3.googleusercontent.com/proxy/wj22Zp_7-9YaIs4KC8thmATE3ZfDZN_Co243RR_2MAJX2coboF-00Yfehs6KvvVs76jCr4zG6X9x4txKrDnCAVAv_8j3onfwBiD8=s0-d-e1-ft#https://static1.shine.com/mailers/midout/img/logo.png' class='CToWUd'></a></td>"
				+ "<td align='left' style='border:0px !important;/*color:#fff;*/font-size:18px;padding-left:15px'>"
				+ "<b>"+title + " - " + getDateAsString() + "</b></td></tr></tbody></table> </td></tr>");
	}
	
	protected void writeReportStatus() {
		 writer.print("<tr><td style='display:none;'>{{ng-report}}</td></tr>");
         writer.print("<tr><td colspan=8></td></tr>");
	}


	/*
	 * Method to get Date as String
	 */
	private String getDateAsString() {
		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/** Starts HTML stream */
	protected void startHtml(PrintWriter out) {
		out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.1//EN' 'http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd'>");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
		out.println("<head>");
		out.println("<title>TestNG Report</title>");
		out.println("<style type='text/css'>");
		out.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
		out.println("td,th {border:1px solid #e5e5e5;padding:.25em .5em}");
		out.println(".result th {vertical-align:bottom}");
		out.println(".param th {padding-left:1em;padding-right:1em}");
		out.println(".param td {padding-left:.5em;padding-right:2em}");
		out.println(".stripe td,.stripe th {background-color: #E6EBF9}");
		out.println(".numi,.numi_attn {text-align:right}");
		out.println(".total td {font-weight:bold}");
		out.println(".passedodd td {background-color: #32CD32}");
		out.println(".passedeven td {background-color: #3F3}");
		out.println(".skippedodd td {background-color: #FFFF66}");
		out.println(".skippedeven td {background-color: #FFFF66}");
		out.println(".failedodd td,.numi_attn {background-color: #FA8072}");
		out.println(".failedeven td,.stripe .numi_attn {background-color: #FA8072}");
		out.println(".stacktrace {white-space:pre-wrap;font-family:monospace;background: #efebeb;border:1px dotted}");
		out.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
		out.println("a {text-decoration: none;}");
		out.println("body{font-family: calibri;}");
		out.println();
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");

	}

	/** Finishes HTML stream */
	protected void endHtml(PrintWriter out) {
		out.println("<center> TestNG Report </center>");
		out.println("</body></html>");
	}

	// ~ Inner Classes --------------------------------------------------------
	/** Arranges methods by classname and method name */
	private class TestSorter implements Comparator<IInvokedMethod> {
		// ~ Methods
		// -------------------------------------------------------------

		/** Arranges methods by classname and method name */
		@Override
		public int compare(IInvokedMethod obj1, IInvokedMethod obj2) {
			int r = obj1.getTestMethod().getTestClass().getName().compareTo(obj2.getTestMethod().getTestClass().getName());
			return r;
		}
	}

	private class TestMethodSorter implements Comparator<ITestNGMethod> {
		@Override
		public int compare(ITestNGMethod obj1, ITestNGMethod obj2) {
			int r = obj1.getTestClass().getName().compareTo(obj2.getTestClass().getName());
			if (r == 0) {
				r = obj1.getMethodName().compareTo(obj2.getMethodName());
			}
			return r;
		}
	}

	private class TestResultsSorter implements Comparator<ITestResult> {
		@Override
		public int compare(ITestResult obj1, ITestResult obj2) {
			int result = obj1.getTestClass().getName().compareTo(obj2.getTestClass().getName());
			if (result == 0) {
				result = obj1.getMethod().getMethodName().compareTo(obj2.getMethod().getMethodName());
			}
			return result;
		}
	}

}