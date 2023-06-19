package hr.rba.creditcardprint.api;

import hr.rba.creditcardprint.openapi.model.ErrorResponse;
import hr.rba.creditcardprint.openapi.model.ValidationErrorResponse;
import hr.rba.creditcardprint.openapi.model.Violation;
import hr.rba.creditcardprint.request.CreditCardPrintAlreadyExistException;
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

    @ExceptionHandler(CreditCardPrintAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse onCreditCardPrintAlreadyExistException(
            CreditCardPrintAlreadyExistException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.name());
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse onException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }
}

