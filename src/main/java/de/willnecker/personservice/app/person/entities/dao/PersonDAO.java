package de.willnecker.personservice.app.person.entities.dao;

import de.willnecker.personservice.app.person.entities.PersonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PersonDAO extends CrudRepository<PersonEntity, Integer> {

    @Query("select d from PersonEntity d where d.idCardNumber = :idCardNumber")
    List<PersonEntity> findByIdCardNumber(int idCardNumber);

    @Query("select d from PersonEntity d")
    List<PersonEntity> find();

}
