package de.willnecker.personservice.app.person;

import de.willnecker.personservice.app.http.HttpPersonResponse;
import de.willnecker.personservice.app.person.entities.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("socoto")
@Slf4j
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping(value = "/persons")
    public HttpPersonResponse getPersons(){
        HttpPersonResponse response = personService.getPersons();
        return response;
    }

    @GetMapping(value = "/person/{idCardNumber}")
    public HttpPersonResponse getPerson(@PathVariable int idCardNumber){
        HttpPersonResponse response = personService.getPerson(idCardNumber);
        return response;
    }

    @PostMapping(value = "/person")
    public HttpPersonResponse createPerson(@RequestBody PersonEntity person) {
        HttpPersonResponse response = personService.createOrUpdatePerson(person);
        return response;
    }

    @PutMapping(value = "/person")
    public HttpPersonResponse updatePerson(@RequestBody PersonEntity person) {
        HttpPersonResponse response = personService.createOrUpdatePerson(person);
        return response;

    }

    @DeleteMapping(value = "person/{id}")
    public HttpPersonResponse deletePerson(@PathVariable int id){
        HttpPersonResponse response = personService.deletePerson(id);
        return response;
    }
}
