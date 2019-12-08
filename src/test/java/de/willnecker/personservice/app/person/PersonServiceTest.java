package de.willnecker.personservice.app.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonServiceTest {

    private PersonService personService = new PersonService();

    @Test
    void validationTest() {
        assertEquals(false, personService.validationOfSurName("AB"), "Nachname - Länge - 2");
        assertEquals(true, personService.validationOfSurName("ABC"), "Nachname - Länge - 3");
        assertEquals(true, personService.validationOfSurName("Willnecker"), "Nachname");
        assertEquals(true, personService.validationOfSurName("WillneckerWillnecker"), "Nachname - Länge - 20");
        assertEquals(false, personService.validationOfSurName("WillneckerWillneckerW"), "Nachname - Länge - 21");

        assertEquals(true, personService.validationOfFirstName(""), "Vorname - Leer");
        assertEquals(true, personService.validationOfFirstName("Tim"), "Vorname");
        assertEquals(true, personService.validationOfFirstName("WillneckerWillnecker"), "Vorname - Länge - 20");
        assertEquals(false, personService.validationOfFirstName("WillneckerWillneckerW"), "Vorname - Länge - 21");


    }
}
