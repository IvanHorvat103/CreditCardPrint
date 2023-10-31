package hr.test.CreditCardPrint.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.test.CreditCardPrint.domain.Osoba;

public interface OsobaRepository extends JpaRepository<Osoba,Long> {
	List<Osoba> findByIme(String ime);
	List<Osoba> findByOib(String oib);
	Osoba findTopByOibOrderByCreatedTimestampDesc(String oib);
    void deleteByOib(String oib);

}
