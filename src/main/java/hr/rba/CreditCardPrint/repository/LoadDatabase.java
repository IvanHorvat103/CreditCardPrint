package hr.rba.CreditCardPrint.repository;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hr.rba.CreditCardPrint.domain.CreditCard;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initCreditCards(CreditCardRepository OsobaRepository) throws IOException {
		//can be used to fill database with some data
		return args -> {
			//log.info("Preloading " + CreditCardRepository.save(new Osoba("Ivan", "Horvat","12345678910")));
		};
    }

}
