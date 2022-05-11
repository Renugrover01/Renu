/**
 * @author Abhishek F3282
 */
package com.shine.common.utils;


import java.io.FileInputStream;

import jxl.Sheet;
import jxl.Workbook;


/**
 * This is a utility class created to read the excel test data file before performing the test steps.
 * This class loads the excel file and
 * reads its column entries.
 *
 */
public class ExcelReader {

    /*The work-sheet to read in Excel file*/
    private Sheet sheet;
    /*The Excel file to read*/
    private Workbook workbook = null;

    /**
     * @param ExcelSheetPath, SheetName

     * @return arrayExcelData

     */

    public String[][] getExcelData(String excelSheetPath, String sheetName) {
        String[][] arrayExcelData = null;
        try (
                FileInputStream fileStream = new FileInputStream(excelSheetPath);
        ) {
            workbook = Workbook.getWorkbook(fileStream);
            sheet = workbook.getSheet(sheetName);

            int totalNoOfCols = sheet.getColumns();
            int totalNoOfRows = sheet.getRows();

            arrayExcelData = new String[totalNoOfRows - 1][totalNoOfCols];

            for (int i = 1; i < totalNoOfRows; i++) {

                for (int j = 0; j < totalNoOfCols; j++) {
                    arrayExcelData[i - 1][j] = sheet.getCell(j, i).getContents();
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return arrayExcelData;
    }


    public String[][] getExcelData(String excelSheetPath, String sheetName, int column) {
        String[][] arrayExcelData = null;
        try {
            FileInputStream fileStream = new FileInputStream(excelSheetPath);
            workbook = Workbook.getWorkbook(fileStream);
            sheet = workbook.getSheet(sheetName);

            int totalNoOfCols = sheet.getColumns();
            int totalNoOfRows = sheet.getRows();

            arrayExcelData = new String[totalNoOfRows - 1][totalNoOfCols];

            for (int i = 1; i < totalNoOfRows; i++) {
                arrayExcelData[i - 1][column] = sheet.getCell(column, i).getContents();

				/*for (int j=0; j < totalNoOfCols; j++) 
				{
					arrayExcelData[i-1][j] = _Sheet.getCell(j, i).getContents();
				}*/

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return arrayExcelData;
    }
	
	
	
	
	
	/*public static LinkedHashMap<String, List<String>> getData(){
		int masterSheetColumnIndex = _Sheet.getColumns();
	    List<String> ExpectedColumns = new ArrayList<String>();
	    for (int x = 0; x < masterSheetColumnIndex; x++) {
	        Cell celll = _Sheet.getCell(x, 0);
	        String d = celll.getContents();
	        ExpectedColumns.add(d);
	    }
	    LinkedHashMap<String, List<String>> columnDataValues = new LinkedHashMap<String, List<String>>();

	    List<String> column1 = new ArrayList<String>();
	    // read values from driver sheet for each column
	    for (int j = 0; j < masterSheetColumnIndex; j++) {
	        column1 = new ArrayList<String>();
	        for (int i = 1; i < _Sheet.getRows(); i++) {
	            Cell cell = _Sheet.getCell(j, i);
	            column1.add(cell.getContents());
	        }
	        columnDataValues.put(ExpectedColumns.get(j), column1);
	    }
		return columnDataValues;
	}
*/


}

