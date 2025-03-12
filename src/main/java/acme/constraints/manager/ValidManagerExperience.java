
package acme.constraints.manager;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidManagerExperienceValidator.class)
@Documented
public @interface ValidManagerExperience {

	String message() default "{acme.validation.manager.experience.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
