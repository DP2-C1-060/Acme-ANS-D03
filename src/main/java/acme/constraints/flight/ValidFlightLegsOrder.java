
package acme.constraints.flight;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFlightLegsOrderValidator.class)
@Documented
public @interface ValidFlightLegsOrder {

	String message() default "{acme.validation.flight.legs.order.message}";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
