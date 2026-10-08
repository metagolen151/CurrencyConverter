# Currency Converter

Aplikacja umożliwia przeliczanie kosztów zakupu komputerów z USD na PLN na podstawie kursu NBP dla wskazanej daty.
Wyniki są zapisywane w bazie PostgreSQL oraz pliku XML.

## Technologie

- Java 21
- Spring Boot 3.5.7
- Spring Data JPA
- PostgreSQL
- Liquibase
- MapStruct
- Lombok
- Jackson XML
- JUnit 5 / Mockito
- Testcontainers
- Docker

## Funkcjonalności

- Rejestracja zakupionego komputera
- Pobieranie kursu USD z API NBP dla wskazanej daty
- Obsługa dat przypadających na weekend
- Przeliczanie ceny USD → PLN
- Zapis danych w PostgreSQL
- Zapis i aktualizacja danych w pliku XML
- Wyszukiwanie komputerów po fragmencie nazwy
- Wyszukiwanie po dacie księgowania
- Sortowanie wyników po nazwie i dacie księgowania
- Walidacja danych wejściowych i obsługa wyjątków

## Przykładowe użycie

### Rejestracja komputera

`POST /api/v1/computers`

Endpoint służy do rejestracji zakupionego komputera.
Po otrzymaniu danych aplikacja pobiera kurs USD z API NBP dla podanej daty, uwzględnia weekendy, przelicza koszt na PLN,
a następnie zapisuje dane w bazie PostgreSQL oraz pliku XML.

```json
{
  "name": "ACER Aspire",
  "bookingDate": "2026-07-03",
  "costUsd": 345
}
```

Przykładowa odpowiedź:

```json
{
  "name": "ACER Aspire",
  "bookingDate": "2026-07-03",
  "costUsd": 345,
  "costPln": 1290.99
}
```

### Pobieranie komputerów

`GET /api/v1/computers`

Endpoint umożliwia wyszukiwanie oraz sortowanie zapisanych komputerów.

## Testy

Projekt zawiera testy jednostkowe oraz integracyjne.

Testowane są m.in.:

- logika obsługi dni roboczych
- przeliczanie kosztu USD na PLN
- mapowanie obiektów
- komunikacja z API NBP
- generowanie i aktualizacja pliku XML
- walidacja endpointów REST
- zapis i odczyt danych z PostgreSQL

Do testów wykorzystano:

- **JUnit 5** – tworzenie i uruchamianie testów
- **Mockito** – mockowanie zależności w testach jednostkowych
- **Testcontainers** – uruchamianie PostgreSQL w kontenerze podczas testów integracyjnych

Przed uruchomieniem wszystkich testów należy uruchomić PostgreSQL:

```bash
docker compose -f docker/docker-compose.yml up -d
```

Następnie można uruchomić wszystkie testy:

```bash
.\mvnw.cmd test
```

## Uruchomienie

Wymagane: Java 21 oraz Docker.

Uruchom PostgreSQL:

```bash
docker compose -f docker/docker-compose.yml up -d
```

Następnie uruchom aplikację:

```bash
.\mvnw.cmd spring-boot:run
```