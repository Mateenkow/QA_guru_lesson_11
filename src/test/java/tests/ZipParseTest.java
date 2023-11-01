package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author mateenkov
 */

public class ZipParseTest {
    ClassLoader classloader = ZipParseTest.class.getClassLoader();

    @Test
    void pdfZipTest() throws Exception {
        try (InputStream inputStream = classloader.getResourceAsStream("qa_guru.zip");
             ZipInputStream zis = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("xlsx")) {
                    XLS xls = new XLS(zis);
                    final String email = xls.excel
                            .getSheetAt(0)
                            .getRow(3)
                            .getCell(1)
                            .getStringCellValue();
                    final int sheetCount = xls.excel.getNumberOfSheets();
                    Assertions.assertEquals("uho@gmail.com", email);
                    Assertions.assertEquals(2, sheetCount);
                    break;
                }
            }
        }
    }


    @Test
    void pdfTest() throws Exception {
        try (InputStream inputStream = classloader.getResourceAsStream("qa_guru.zip");
             ZipInputStream zis = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null)
                if (entry.getName().contains(".pdf")) {
                    PDF pdf = new PDF(zis);
                    Assertions.assertEquals(1, pdf.numberOfPages);
                    Assertions.assertEquals(false, pdf.signed);
                    break;
                }
        }
    }


    @Test
    void csvTest() throws Exception {
        try (InputStream inputStream = classloader.getResourceAsStream("qa_guru.zip");
             ZipInputStream zis = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = csvReader.readAll();
                    Assertions.assertEquals(1, content.size());
                    final String[] secondRow = content.get(0);
                    Assertions.assertArrayEquals(new String[]{"Line1", "Column2", "Column3", "Column4"}, secondRow);
                }
                break;
            }
        }

    }
}
