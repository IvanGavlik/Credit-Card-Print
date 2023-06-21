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
/**
 * Controller advice class for handling error responses and exceptions globally.
 */
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    /**
     * Handles the ConstraintViolationException and returns a validation error response.
     *
     * @param e the ConstraintViolationException
     * @return the validation error response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onConstraintValidationException(
            final ConstraintViolationException e) {
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

    /**
     * Handles the MethodArgumentNotValidException and returns a validation error response.
     *
     * @param e the MethodArgumentNotValidException
     * @return the validation error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
            final MethodArgumentNotValidException e) {
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


    /**
     * Handles the CreditCardPrintAlreadyExistException and returns an error
     *  response with HTTP status CONFLICT.
     *
     * @param e the CreditCardPrintAlreadyExistException
     * @return the error response
     */
    @ExceptionHandler(CreditCardPrintAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse onCreditCardPrintAlreadyExistException(
            final CreditCardPrintAlreadyExistException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.name());
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }

    /**
     * Handles any other exception and returns an error response
     * with HTTP status INTERNAL_SERVER_ERROR.
     *
     * @param e the exception
     * @return the error response
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse onException(final Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }
}

