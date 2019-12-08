package de.willnecker.personservice.app.http;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class HttpPersonResponse<T> {

    private HttpStatus status;
    private boolean error = false;
    private String returnText;
    private String exceptionMessage;
    T returnObject;

}
