package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import org.testng.annotations.DataProvider;
import java.lang.reflect.Method;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Iterator;


public class DP {
	
	  // TODO: use correct annotation to connect the Data Provider with your Test Cases
    @DataProvider(name = "data-provider")
    public Object[][] dpMethod(Method m) throws IOException {

        String testSheetName = m.getName();
        String var = testSheetName;
        System.out.println(testSheetName);
        int rowIndex = 0;
        int cellIndex = 0;
        //List<List> outputList = new ArrayList<List>();
        List<List<String>> outputList = new ArrayList<>();

        FileInputStream excelFile = new FileInputStream(new File("Resources/DatasetofQtrip.xlsx"));
       
		try(Workbook workbook = new XSSFWorkbook(excelFile)){
        Sheet selectedSheet = workbook.getSheet(var);
        Iterator<Row> iterator = selectedSheet.iterator();
        
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            List<String> innerList = new ArrayList<String>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (rowIndex > 0 && cellIndex > 0) {
                    if (cell.getCellType() == CellType.STRING) {
                        innerList.add(cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        innerList.add(String.valueOf(cell.getNumericCellValue()));
                    }
                }
                cellIndex = cellIndex + 1;
            }
            rowIndex = rowIndex + 1;
            cellIndex = 0;
            if (innerList.size() > 0)
                outputList.add(innerList);

        }
		}
        excelFile.close();

        //String[][] stringArray = outputList.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
     // Create the outer array with the size of the outputList
        String[][] stringArray = new String[outputList.size()][];

        // Iterate over each element in the outputList
        for (int i = 0; i < outputList.size(); i++) {
            // Convert each List<String> to a String[]
            List<String> innerList = outputList.get(i);
            stringArray[i] = innerList.toArray(new String[0]);
        }
        return stringArray;

    }

}
