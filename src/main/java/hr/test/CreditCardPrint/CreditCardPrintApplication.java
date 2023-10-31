package hr.test.CreditCardPrint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("hr.test.CreditCardPrint.domain")
public class CreditCardPrintApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditCardPrintApplication.class, args);
	}

}
