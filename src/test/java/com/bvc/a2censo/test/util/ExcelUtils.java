package com.bvc.a2censo.test.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;

public class ExcelUtils {

    private static XSSFSheet sheet;
    private static XSSFWorkbook book;
    private static XSSFCell cell;
    private static XSSFRow row;

    public static String[][] getData(String FilePath, String SheetName, Boolean ignoreFirstRow){
        String[][] table = null;
        try {
            FileInputStream ExcelFile = new  FileInputStream(FilePath);
            // Access the required test data sheet
            book = new XSSFWorkbook(ExcelFile);
            sheet = book.getSheet(SheetName);
            int totalRows = sheet.getLastRowNum()+1;
            XSSFRow r = sheet.getRow(sheet.getFirstRowNum());
            int totalCols=  r.getLastCellNum();
            table = new String[totalRows][totalCols];
            for (int i=0;i<totalRows;i++) {
                for (int j=0;j<totalCols;j++){
                    table[i][j] = getCellData(i,j);
                }
            }
            if (ignoreFirstRow) {
                table = ArrayUtils.remove(table,0);
            }
        } catch (FileNotFoundException e){
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        }
        return table;
    }

    public static String getCellData(int RowNum, int ColNum) throws Exception {
        try {
            cell = sheet.getRow(RowNum).getCell(ColNum);
            int dataType = cell.getCellType();
            if (dataType == 3) {
                return "";
            } else {
                String CellData = cell.getStringCellValue();
                return CellData;
            }
        } catch (Exception e){
                System.out.println(e.getMessage());
                throw (e);
        }
    }

}