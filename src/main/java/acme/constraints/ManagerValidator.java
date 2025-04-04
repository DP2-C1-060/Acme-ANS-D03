
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.realms.manager.Manager;
import acme.realms.manager.ManagerRepository;

@Validator
public class ManagerValidator extends AbstractValidator<ValidManager, Manager> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidManager annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Manager manager, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (manager == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			{
				boolean uniqueManager;
				Manager existingManager;

				existingManager = this.repository.findManagerByIdentifier(manager.getIdentifier());
				uniqueManager = existingManager == null || existingManager.equals(manager);

				super.state(context, uniqueManager, "ticker", "acme.validation.manager.duplicated-identifier.message");
			}
			{
				boolean correctIdentifier = true;

				if (manager.getIdentifier() != null)
					correctIdentifier = Character.toUpperCase(manager.getIdentifier().charAt(0)) == Character.toUpperCase(manager.getIdentity().getName().charAt(0))
						&& Character.toUpperCase(manager.getIdentifier().charAt(1)) == Character.toUpperCase(manager.getIdentity().getSurname().charAt(0));

				super.state(context, correctIdentifier, "identifier", "acme.validators.manager.correct-pattern");
			}
		}

		result = !super.hasErrors(context);

		return result;

	}
}
