package io.github.robson.correios_api.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT,value = HttpStatus.NO_CONTENT, reason = "Not exist!")
public class NoContentException extends Exception{
}
