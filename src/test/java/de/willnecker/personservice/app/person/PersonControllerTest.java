package de.willnecker.personservice.app.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.willnecker.personservice.app.http.HttpPersonResponse;
import de.willnecker.personservice.app.person.entities.PersonEntity;
import de.willnecker.personservice.app.person.entities.dao.PersonDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"classpath:db.sql/create_person.sql"})
@Slf4j
class PersonControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper objectMapper;

    private PersonEntity person;
    protected MockMvc mvc;

    @PostConstruct
    protected void init() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeEach
    public void setUp() {
        person = new PersonEntity();
        person.setIdCardNumber(123245);
        person.setSalutation("Herr");
        person.setFirstName("Tim");
        person.setSurName("Willnecker");
        person.setStreet("Fröschenpfuhl 27");
        person.setCity("Koblenz");
        person.setPlace(56070);
        person.setBirthday(new Date(System.currentTimeMillis()));
    }

    @Test
    public void createOrUpdatePerson() throws Exception {

        MvcResult mvcResult;
            mvcResult = mvc
                    .perform(MockMvcRequestBuilders.post("/socoto/person")
                            .header(HttpHeaders.AUTHORIZATION, "Basic c29jb3RvLWFkbWluOjpIc186Q2NidmQkSj5FcXN4OktWbSlCZk1IbXo3WEs2KDJwd2o2")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(person))).andReturn();
            MockHttpServletResponse post = mvcResult.getResponse();


        assertEquals(HttpStatus.OK.value(), post.getStatus(), "POST SUCCESS");
        HttpPersonResponse<Map<String, Object>> response = objectMapper.readValue(post.getContentAsString(), HttpPersonResponse.class);
        assertEquals(123245, response.getReturnObject().get("idCardNumber"), "SAME ID");
    }

    @Autowired
    private PersonDAO personDAO;

    @Test
    public void findByIdCardNumber() {
        personDAO.save(person);
        List<PersonEntity> getPerson = personDAO.findByIdCardNumber(123245);
        assertEquals(123245, getPerson.get(0).getIdCardNumber(), "SAME ID");
    }
}
