package hr.rba.CreditCardPrint.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.rba.CreditCardPrint.domain.CreditCard;
import hr.rba.CreditCardPrint.repository.CreditCardRepository;
import hr.rba.CreditCardPrint.util.FileIOUtility;
import hr.rba.CreditCardPrint.util.exception.CreditCardNotFoundException;

@RestController
public class CreditCardController {
	
	@Autowired
	private final CreditCardRepository repository;
	private static final Logger log = LoggerFactory.getLogger(CreditCardController.class);
	
	public CreditCardController(CreditCardRepository repository) {
		super();
		this.repository = repository;
	}
	
	// create new credit card in database
	@PostMapping("/newcreditcard")
	ResponseEntity<CreditCard> createNewCreditCard(@RequestParam String ime, @RequestParam String prezime, @RequestParam String oib) {
		log.info("Creating new credit card in database with: ime = " + ime + ", prezime = " + prezime + ", and OIB = " + oib);
	    // Retrieve existing CreditCard entities with the same OIB
	    List<CreditCard> existingCreditCards = repository.findByOib(oib);
	    if(!existingCreditCards.isEmpty()) {
		    // Set the status to false for existing CreditCard entities
		    for (CreditCard existingCreditCard : existingCreditCards) {
		        existingCreditCard.setStatus(false);
		    }
	
		    // Save the updated existing CreditCard entities
		    repository.saveAll(existingCreditCards);
		    
		    //Change status to false for chosen OIB 
		    try {
				FileIOUtility.changeStatusInFilesByOib(oib);
			} catch (IOException e) {
				log.error("An exception occurred while changing status in file for OIB: " + oib, e);
			}
	    }
	    // Create and save the new credit card
		CreditCard newCreditCard = new CreditCard(ime,prezime,oib);
        if (newCreditCard != null) {        	
        	//save credit card
        	repository.save(newCreditCard);
        	log.info("Credit card saved to database: {}",newCreditCard.toString());
        	// HTTP 200 OK
            return ResponseEntity.ok(newCreditCard);  
        } 
        return ResponseEntity.notFound().build();  
        
	}
	
	//get credit card in file with oib
	@GetMapping("/creditcard/{oib}")
	public ResponseEntity<CreditCard> getCreditCard(@PathVariable String oib) {	
		log.info("Getting credit card from database with OIB: " + oib);
		//Checking if credit card exists in database, 
		//if it does it sets status to false for existing cards on file for that OIB
		CreditCard creditCard = repository.findTopByOibOrderByCreatedTimestampDesc(oib);	
		if(creditCard == null) {			 
        	log.warn("Credit card not found in database.");
        	throw new CreditCardNotFoundException(oib);		        
		}
	    //Change status to false for chosen OIB 
	    try {
			FileIOUtility.changeStatusInFilesByOib(oib);
		} catch (IOException e) {
			log.error("An exception occurred while changing status in file for OIB: " + oib, e);
		}
		FileIOUtility.writeCreditCardToFile(creditCard);
        if (creditCard != null) {
        	log.info("Credit card fetched from database: {}", creditCard.toString());
        	// HTTP 200 OK
            return ResponseEntity.ok(creditCard);  
        }
        return ResponseEntity.notFound().build();
	}
	
	  //delete credit card and change status in file of oib to false (inactive)
	  @DeleteMapping("/deletecreditcard/{oib}")
	  @Transactional
	  void deleteCreditCard(@PathVariable String oib) {
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
