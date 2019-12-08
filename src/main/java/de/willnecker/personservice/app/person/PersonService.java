package de.willnecker.personservice.app.person;

import de.willnecker.personservice.app.http.HttpPersonResponse;
import de.willnecker.personservice.app.person.entities.PersonEntity;
import de.willnecker.personservice.app.person.entities.dao.PersonDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PersonService implements Serializable {

    @Autowired
    PersonDAO personDAO;

    public HttpPersonResponse getPersons() {
        HttpPersonResponse response = new HttpPersonResponse();
        try{
            List<PersonEntity> persons = personDAO.find();
            log.info("", persons);
            if(persons.isEmpty()){
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setReturnObject(null);
                response.setReturnText("NO PERSONS FOUND!");
            }else{
                response.setStatus(HttpStatus.OK);
                response.setReturnObject(persons);
                response.setReturnText("ALL PERSONS SELECTED!");
            }
        }catch (Exception e){
            response.setError(true);
            response.setReturnObject(e);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setExceptionMessage("NO PERSONS FOUND!");
        }
        return response;
    }

    public HttpPersonResponse getPerson(int idCardNumber) {
        HttpPersonResponse response = new HttpPersonResponse();
        try{
             List<PersonEntity> person = personDAO.findByIdCardNumber(idCardNumber);
             if(person.isEmpty()){
                 response.setStatus(HttpStatus.NOT_FOUND);
                 response.setReturnObject(null);
                 response.setReturnText("NO PERSON WIRH IDCARDNUMBER " + idCardNumber + " FOUND!");
             }else{
                 response.setStatus(HttpStatus.OK);
                 response.setReturnText("PERSON SELECTED!");
                 response.setReturnObject(person);
             }
        }catch (Exception e){
            response.setError(true);
            response.setReturnObject(e);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setExceptionMessage("NO PERSON FOUND WITH THIS IDCARDNUMBER: " + idCardNumber);
        }
        return response;
    }

    public HttpPersonResponse createOrUpdatePerson(PersonEntity person) {
        HttpPersonResponse response = new HttpPersonResponse();
        List<String> validationExceptions = checkValidationOfPerson(person);
        if(validationExceptions.isEmpty()){
            try {
                if(!personDAO.findByIdCardNumber(person.getIdCardNumber()).isEmpty()){
                    response.setReturnText("PERSON " + person.getIdCardNumber() + " UPDATED!");
                }else{
                    response.setReturnText("PERSON " + person.getIdCardNumber() + " CREATED!");
                }
                personDAO.save(person);
                response.setReturnObject(person);
                response.setStatus(HttpStatus.OK);
            }catch (Exception e){
                response.setError(true);
                response.setReturnObject(e);
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setExceptionMessage("COULD NOT CREATE PERSON!");
            }
        }else{
            response.setError(true);
            response.setReturnObject(validationExceptions);
            response.setStatus(HttpStatus.CONFLICT);
            response.setExceptionMessage("Validierungsfehler!");
        }
        return response;
    }

    public HttpPersonResponse deletePerson(int idCardNumber) {
        HttpPersonResponse response = new HttpPersonResponse();
        try{
            List<PersonEntity> personToDelete = personDAO.findByIdCardNumber(idCardNumber);
            if(!personToDelete.isEmpty()){
                personDAO.deleteById(idCardNumber);
                response.setStatus(HttpStatus.OK);
                response.setReturnObject(personToDelete);
                response.setReturnText("PERSON : " + idCardNumber + " DELETED!");
            }else{
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setReturnObject(null);
                response.setReturnText("PERSON WITH IDCARDNUMBER :" +idCardNumber + " NOT FOUND!");
            }
        }catch (Exception e){
            response.setError(true);
            response.setReturnObject(e);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setExceptionMessage("PERSON : " + idCardNumber + " COULD NOT BE DELETED!");
        }
        return response;
    }

    public List<String> checkValidationOfPerson(PersonEntity person){
        List<String> exceptions = new ArrayList<>();
        if (!validationOfFirstName(person.getFirstName())){
            exceptions.add("Der Vorname darf maximal 20 Zeichen lang sein");
        }
        if(!validationOfSurName(person.getSurName())){
            exceptions.add("Der Nachname muss zwischen 3 und 20 Zeichen lang sein");
        }
        if(!validationOfBirthday(person.getBirthday())){
            exceptions.add("Der Geburtstag darf nicht in der Zukunft liegen");
        }
        return exceptions;
    }

    public boolean validationOfSurName(String surName){
        if(surName.length() >= 3 && surName.length() <= 20){
            return true;
        }else{
            return false;
        }
    }

    public boolean validationOfFirstName(String firstName){
        if(firstName.length() <= 20) {
            return true;
        }else{
            return false;
        }
    }

    public boolean validationOfBirthday (Date birthday){
        Date currentDate = new Date();
        if(birthday.after(currentDate )){
            return false;
        }else {
            return true;
        }
    }
}
