package hr.rba.creditcardprint.api;

import hr.rba.creditcardprint.openapi.model.ValidationErrorResponse;
import hr.rba.creditcardprint.openapi.model.Violation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e) {
        final List<Violation> errors = new ArrayList<>();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            Violation v = new Violation();
            v.setFieldName(violation.getPropertyPath().toString());
            v.setMessage(violation.getMessage());
            errors.add(v);
        }
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.setViolations(errors);
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final List<Violation> errors = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            Violation v = new Violation();
            v.setFieldName(fieldError.getField());
            v.setMessage(fieldError.getDefaultMessage());
            errors.add(v);
        }
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.setViolations(errors);
        return errorResponse;
    }
}

