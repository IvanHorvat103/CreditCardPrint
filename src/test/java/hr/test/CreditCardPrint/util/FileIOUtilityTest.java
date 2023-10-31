package hr.test.CreditCardPrint.util;

import hr.test.CreditCardPrint.domain.CreditCard;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.*;

public class FileIOUtilityTest {

    @Test
    public void testWriteCreditCardToFile() throws IOException {
        // Create a temporary test directory for file output
        String testDir = "test-output";

        // Mock the Osoba object
        CreditCard creditCard = new CreditCard();
        creditCard.setIme("Ivan");
        creditCard.setPrezime("Horvat");
        creditCard.setOib("32132132112");
        creditCard.setStatus(true);

        // Mock LocalDateTime and set a fixed timestamp
        LocalDateTime fixedTimestamp = creditCard.getCreatedTimestamp();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // Call the method
        FileIOUtility.writeCreditCardToFile(creditCard);

        // Verify the file content
        String expectedContent = "Ivan|Horvat|32132132112|true";
        String timestamp = fixedTimestamp.format(formatter);
        String expectedFileName = creditCard.getOib() + "_" + timestamp + ".txt";

        String fileContent = readFromFileByOib(creditCard.getOib());
        assertNotNull(fileContent);
        assertEquals(expectedContent, fileContent);

        // Clean up the test directory
        deleteFile(expectedFileName);
    }
    
    private String readFromFileByOib(String oib) throws IOException {
        File directory = new File(System.getProperty("user.dir"));
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

    private void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}