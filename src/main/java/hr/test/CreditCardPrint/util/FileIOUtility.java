package hr.test.CreditCardPrint.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.test.CreditCardPrint.domain.Osoba;

public class FileIOUtility {
	private static final String DELIMITER = "|";
	private static final Logger log = LoggerFactory.getLogger(FileIOUtility.class);
    public static void writeOsobaToFile(Osoba osoba) {
    	log.info("Printing osoba to file:" + osoba.toString());
    	String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = osoba.getOib() + "_" + timestamp + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the Osoba object's attributes to the file with '|' delimiter
            writer.write(osoba.getIme() + DELIMITER + osoba.getPrezime() + DELIMITER + osoba.getOib() + DELIMITER + osoba.isStatus());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Printing osoba to file successfull");
    }
    
    public static void changeStatusInFilesByOib(String oib) throws IOException {
        File directory = new File(System.getProperty("user.dir"));
        File[] files = directory.listFiles((dir, name) -> name.startsWith(oib + "_"));

        if (files != null && files.length > 0) {
            log.info("Files for oib (" + oib + ") found!");

            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Split the lines by a '|' delimiter
                        String[] parts = line.split("\\|");
                        if (parts.length == 4) {
                            // Check if the status is "true" and change it to "false"
                            if (parts[3].equals("true")) {
                                parts[3] = "false";
                            }
                            content.append(String.join(DELIMITER, parts));
                            content.append("\n");
                        }
                    }
                    
                    // Write the updated content back to the same file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(content.toString());
                    }
                }
            }
        }
    }
}