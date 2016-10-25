package com.tom.excelfileIO;

import com.github.lwhite1.tablesaw.api.Table;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Class that works with the Excel file, it will read data from the Excel file
 * and put that data into a more suitable format for the application. I will
 * close the file once the work related to it is done.
 */
public class ExcelFileIO {

    private final File excelFile;
    private int cellRow = 0, cellCol = 0;//location of the required week no
    private XSSFWorkbook workbook;//which workbook to work on
    private XSSFSheet sheet;//which sheet to look
    private int sheetNo = 0;
    private final ArrayList<ArrayList<String>> excelDataList = new ArrayList<>();

    public ExcelFileIO(File excelFile) {
        this.excelFile = excelFile;
    }

    /**
     * Method to find the Row and Col related to Week. It find from where the
     * data starts. Once we have the row and col we can start with data
     * extraction
     *
     *
     * @param weekNumbersStringSet
     * @return
     */
    public ArrayList<ArrayList<String>> getExcelData(Set<String> weekNumbersStringSet) {//Wk 37
        XSSFRow row;
        try {
            workbook = new XSSFWorkbook(excelFile);
            sheet = workbook.getSheetAt(getSheetNo());
            Iterator<String> setIterator = weekNumbersStringSet.iterator();
            while (setIterator.hasNext()) {
                String stringToFind = setIterator.next();
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    row = (XSSFRow) rowIterator.next();
                    Iterator<Cell> cellIterator = row.iterator();
                    {
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_STRING:
                                    if (cell.getStringCellValue().equals(stringToFind)) {
                                        System.out.print("-" + cell.getStringCellValue() + " ");
                                        cellRow = cell.getRowIndex();
                                        cellCol = cell.getColumnIndex();
                                        System.out.println("Row Index: " + cellRow);
                                        System.out.println("Column Index: " + cellCol);
                                    }
                                    break;
                            }
                        }
                    }
                }
                ArrayList<String> tmpDataList = extractData();
                if (tmpDataList.size() == 0) {
                    System.out.println("No data found for " + stringToFind);
                } else {
                    excelDataList.add(tmpDataList);//execute method for the said row and cols and then move to next week
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ExcelFileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ExcelFileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return excelDataList;
    }

    /**
     * Method to extract the data from excel and store it into class
     *
     * @return
     */
    private ArrayList<String> extractData() {
        ArrayList<String> dataList = new ArrayList<>();
        if (cellCol != 0 && cellRow != 0) {
            Row row;
            Cell cell;
            for (int i = cellRow + 1; i < cellRow + 20; i++) { //cellRow= Wk 39 etc, so head row, and we need to collect values till cellRow=20
                row = sheet.getRow(i);
                cell = row.getCell(cellCol);
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            Date d = cell.getDateCellValue();
                            String durationOfCall = d.getHours() + "." + d.getMinutes();//seperate minutes and seconds by dot
                            dataList.add(durationOfCall);
                        } else {
                            dataList.add(String.format("%.2f", cell.getNumericCellValue()));
                        }
                        break;
                    case Cell.CELL_TYPE_STRING:
                        dataList.add(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                        CellValue cellValue = evaluator.evaluate(cell);
                        dataList.add(String.format("%.2f", cellValue.getNumberValue()));
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        dataList.add("");
                }
            }
        }
        System.out.println(dataList);
        return dataList;
    }

    /**
     * @return the sheetNo
     */
    public int getSheetNo() {
        return sheetNo;
    }

    /**
     * @param sheetNo the sheetNo to set
     */
    public void setSheetNo(int sheetNo) {
        this.sheetNo = sheetNo;
    }

}
