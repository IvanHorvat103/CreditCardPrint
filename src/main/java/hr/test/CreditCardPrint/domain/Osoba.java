package hr.test.CreditCardPrint.domain;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "osoba")
public class Osoba {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, length = 50)
	private String ime;
	@Column(nullable = false, length = 50)
	private String prezime;
	@Column(nullable = false, length = 11)
	private String oib;
	@Column(nullable = false)
	private boolean status;	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_timestamp")
    private LocalDateTime createdTimestamp;
	
	
	public Osoba() {
		this.createdTimestamp = LocalDateTime.now();
	}

	public Osoba( String ime, String prezime, String oib) {
		this.ime = ime;
		this.prezime = prezime;
		this.oib = oib;
		this.status = true;
		this.createdTimestamp = LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getOib() {
		return oib;
	}
	public void setOib(String oib) {
		this.oib = oib;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDateTime getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	@Override
	public String toString() {
		return "Osoba [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", oib=" + oib + ", status=" + status
				+ ", createdTimestamp=" + createdTimestamp + "]";
	}

}
