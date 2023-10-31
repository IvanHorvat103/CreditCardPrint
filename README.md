# CreditCardPrint

API za zapisivanje, brisanje i ispis osoba sa kreditnim karticama u potrebe za print. 

# API koristi sljedće tehnologije

* Java - 17
* PostgreSQL 14.5
* Spring boot 3.1.5
* Maven 3.8.8

# API endpointovi u aplikaciji

### Osobe
| Method | Url | Parametars |
| ------ | --- | ----------- |
| POST | /newcreditcard | Stvara novu karticu za osobu | ime, prezime, oib |
| GET | /creditcard/{oib} | Dohvaćanje postojeće kartice, i ispis u datoteku | oib |
| DELETE | /deletecreditcard/{oib} | Brisanje postojeće kartice i invalidacija postojećih zapisa u datoteci | oib |

U slučaju pozivanja bilo koijh od API endpointova 
	- sve kartice koje su vezane na oib (ako se stvara novi zapis, briše ili dohvaća) status u datotekama se mjenja u false, što bi značilo da više nisu aktivne.

# Korisne komande za testiranje API:

  *  curl -X POST "http://localhost:8080/createnew" -d "ime=Ivan" -d "prezime=Horvat" -d "oib=12345612345"
  *  curl -X GET "http://localhost:8080/creditcard/12345612345"
  *  curl -X DELETE "http://localhost:8080/deletecreditcard/12345612345
