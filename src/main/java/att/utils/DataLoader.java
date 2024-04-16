package att.utils;

import att.model.Input;
import att.model.TestModel;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader {
    private String dataPath = System.getProperty("dataPath");

    private Workbook getWorkbook() throws IOException {
        FileInputStream fis = new FileInputStream(dataPath);
        Workbook workbook = null;

        if (dataPath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(fis);
        } else if (dataPath.endsWith("xls")) {
            workbook = new HSSFWorkbook(fis);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case NUMERIC:
                return String.format("%f", cell.getNumericCellValue()).replaceAll("\\.?0*$", "");
            default:
                return cell.getStringCellValue();
        }
    }

    public Object[] generateData() {
        ArrayList<Input> inputs = new ArrayList<>();
        try {
            Workbook workbook = getWorkbook();
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                int cellCount = 0;
                Input input = new Input();
                ArrayList<TestModel> testModels = new ArrayList<>();
                for (Cell cell : row) {
                    switch (cellCount++) {
                        case 0:
                            input.setUsername(getCellValue(cell));
                            break;
                        case 1:
                            input.setPassword(getCellValue(cell));
                            break;
//                        case 2:
//                            TestModel requiredTest = TestModel.fromString(getCellValue(cell));
//                            input.setRequiredTest(requiredTest);
//                            break;
//                        case 3:
//                            TestModel optionalTest = );
//                            input.setOptionalTest(optionalTest);
//                            break;
                        default:
                            testModels.add(TestModel.fromString(getCellValue(cell)));
                            break;
                    }
                }
                input.setTestModels(testModels);
                inputs.add(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputs.toArray();
    }
}
