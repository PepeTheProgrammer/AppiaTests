package dataProviderClasses;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadExcelFile {
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;

    public ReadExcelFile(String excelPath) throws IOException {
        try {
            File src = new File(excelPath);
            FileInputStream stream = new FileInputStream(src);
            workbook = new HSSFWorkbook(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getData(int sheetNumber, int row, int column){
        sheet = workbook.getSheetAt(sheetNumber);
        return sheet.getRow(row).getCell(column).getStringCellValue();
    }

    public int getRowCount(int sheetIndex){
        return workbook.getSheetAt(sheetIndex).getLastRowNum();
    }
}
