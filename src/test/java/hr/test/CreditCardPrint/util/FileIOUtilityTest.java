package hr.test.CreditCardPrint.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.rba.CreditCardPrint.domain.CreditCard;
import hr.rba.CreditCardPrint.util.FileIOUtility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileIOUtilityTest {
private static final String TEST_DIRECTORY = "test-directory";
	
    @BeforeAll
    public static void setUp() throws IOException {
        // Create a test directory and sample files
        File directory = new File(TEST_DIRECTORY);
        directory.mkdir();
        createSampleFiles();
    }

    @AfterAll
    public static void tearDown() throws IOException {
        // Clean up the test directory and sample files
        File directory = new File(TEST_DIRECTORY);
        deleteSampleFiles(directory);
        directory.delete();
    }
    
    @Test
    public void testChangeStatusInFilesByOib() throws IOException {
        String oib = "32132132112";

        // Call the method to change the status
        FileIOUtility.changeStatusInFilesByOib(oib,TEST_DIRECTORY);

        // Verify that the status in the files has been changed as expected
        String expectedContent = "Ivan|Horvat|32132132112|false";
        String fileContent = readFromFileByOib(oib);
        assertNotNull(fileContent);
        assertEquals(expectedContent, fileContent);
    }
    
    @Test
    public void testWriteCreditCardToFile() throws IOException {
    	//Mock CreditCard
        CreditCard creditCard = new CreditCard();
        creditCard.setIme("Ivan");
        creditCard.setPrezime("Horvat");
        creditCard.setOib("32132132112");
        creditCard.setStatus(true);
        // Mock LocalDateTime and set a fixed timestamp
        LocalDateTime fixedTimestamp = creditCard.getCreatedTimestamp();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // Call the method
        FileIOUtility.writeCreditCardToFile(creditCard,TEST_DIRECTORY);

        // Verify the file content
        String expectedContent = "Ivan|Horvat|32132132112|true";
        String timestamp = fixedTimestamp.format(formatter);

        String fileContent = readFromFileByOib(creditCard.getOib());
        assertNotNull(fileContent);
        assertEquals(expectedContent, fileContent);
    }
    
    private String readFromFileByOib(String oib) throws IOException {
        File directory = new File(TEST_DIRECTORY);
        File[] files = directory.listFiles((dir, name) -> name.startsWith(oib + "_"));

        if (files != null && files.length > 0) {
            File file = files[0];
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                return content.toString();
            }
        }
        return null;
    }
    
    private static void createSampleFiles() throws IOException {
        File directory = new File(TEST_DIRECTORY);
        directory.mkdirs();

        List<String> oibs = new ArrayList<>();
        oibs.add("32132132112");
        oibs.add("98765432111");

        for (String oib : oibs) {
            LocalDateTime fixedTimestamp = LocalDateTime.of(2023, 11, 6, 12, 0, 0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String timestamp = fixedTimestamp.format(formatter);

            String fileName = oib + "_" + timestamp + ".txt";
            File file = new File(directory, fileName); 
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Ivan|Horvat|"+oib+"|true");
            }
        }
    }

    private static void deleteSampleFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();             
            }
        }
    }
}