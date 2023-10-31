package hr.test.CreditCardPrint.service;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hr.test.CreditCardPrint.domain.Osoba;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initAuthors(OsobaRepository OsobaRepository) throws IOException {
		return args -> {
			//log.info("Preloading " + OsobaRepository.save(new Osoba("Ivan", "Horvat","12345678910")));
		};
    }

}
