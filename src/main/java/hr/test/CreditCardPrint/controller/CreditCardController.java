package hr.test.CreditCardPrint.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.test.CreditCardPrint.domain.Osoba;
import hr.test.CreditCardPrint.service.OsobaRepository;
import hr.test.CreditCardPrint.util.FileIOUtility;
import hr.test.CreditCardPrint.util.exception.OsobaNotFoundException;

@RestController
public class CreditCardController {
	
	@Autowired
	private final OsobaRepository repository;
	private static final Logger log = LoggerFactory.getLogger(CreditCardController.class);
	
	public CreditCardController(OsobaRepository repository) {
		super();
		this.repository = repository;
	}
	
	// create new credit card in database
	@PostMapping("/createnew")
	@Transactional
	Osoba createNewOsoba(@RequestParam String ime, @RequestParam String prezime, @RequestParam String oib) {
		log.info("Creating new credit card in database with: ime = " + ime + ", prezime = " + prezime + ", and OIB = " + oib);
	    // Retrieve existing Osoba entities with the same OIB
	    List<Osoba> existingOsobe = repository.findByOib(oib);

	    // Set the status to false for existing Osoba entities
	    for (Osoba existingOsoba : existingOsobe) {
	        existingOsoba.setStatus(false);
	    }

	    // Save the updated existing Osoba entities
	    repository.saveAll(existingOsobe);
	    
	    //Change status to false for chosen OIB 
	    try {
			FileIOUtility.changeStatusInFilesByOib(oib);
		} catch (IOException e) {
			log.error("An exception occurred while changing status in file for OIB: " + oib, e);
		}
	    // Create and save the new Osoba
		Osoba newOsoba = new Osoba(ime,prezime,oib);
		return repository.save(newOsoba);
	}
	
	//get credit card in file with oib
	@GetMapping("/creditcard/{oib}")
	public List<Osoba> getCreditCard(@PathVariable String oib) {	
		log.info("Getting credit card from database with OIB: " + oib);
		Osoba osoba = repository.findTopByOibOrderByCreatedTimestampDesc(oib);
	    if (osoba == null) {
	        throw new OsobaNotFoundException(oib);
	    }
	    
	    //Change status to false for chosen OIB 
	    try {
			FileIOUtility.changeStatusInFilesByOib(oib);
		} catch (IOException e) {
			log.error("An exception occurred while changing status in file for OIB: " + oib, e);
		}
		FileIOUtility.writeOsobaToFile(osoba);
		return repository.findByOib(oib);
	}
	
	  //delete osoba and change status in file of oib to false (inactive)
	  @DeleteMapping("/deletecreditcard/{oib}")
	  @Transactional
	  void deleteAuthor(@PathVariable String oib) {
		log.info("Deleting credit card in database with OIB = " + oib);
	    repository.deleteByOib(oib);
	    
	    //Change status to false for chosen OIB 
	    try {
			FileIOUtility.changeStatusInFilesByOib(oib);
		} catch (IOException e) {
			log.error("An exception occurred while changing status in file for OIB: " + oib, e);
		}
	    log.info("Credit card details successfully deleted");
	  }

}
