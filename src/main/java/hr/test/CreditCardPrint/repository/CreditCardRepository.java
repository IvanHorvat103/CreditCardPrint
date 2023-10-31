package hr.test.CreditCardPrint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.test.CreditCardPrint.domain.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
	List<CreditCard> findByIme(String ime);
	List<CreditCard> findByOib(String oib);
	CreditCard findTopByOibOrderByCreatedTimestampDesc(String oib);
    void deleteByOib(String oib);

}
