package hr.rba.creditcardprint.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * The {@code Oib} annotation is used to validate the OIB (Personal Identification Number) format.
 *
 * Used in OpenApi specification as x-validations-oib: true
 * It is applied to fields, parameters, or methods to ensure that the provided value conforms to the OIB format.
 *
 * This annotation is implemented in {@link OibValidator} class.
 */
@Documented
@Constraint(validatedBy = OibValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Oib {

    /**
     * The default error message to be used when the validation fails.
     *
     * @return The error message.
     */
    String message() default "{hr.rba.creditcardprint.validation.message}";

    /**
     * Defines the validation groups to which this constraint belongs.
     *
     * @return The validation groups.
     */
    Class<?>[] groups() default {};

    /**
     * Payload is a mechanism for adding additional metadata information to a constraint.
     * The default is an empty array, indicating that there are no additional payloads.
     *
     * @return The payload classes.
     */
    Class<? extends Payload>[] payload() default {};
}
