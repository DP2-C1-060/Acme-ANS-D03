
package acme.features.manager.leg;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.legs.Leg;
import acme.realms.manager.Manager;

@GuiService
public class ManagerLegListService extends AbstractGuiService<Manager, Leg> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerLegRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Leg> legs;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		legs = this.repository.findLegByManagerId(managerId);

		super.getBuffer().addData(legs);
	}

	@Override
	public void unbind(final Leg leg) {
		Dataset dataset;

		dataset = super.unbindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival");
		if (leg.isDraftMode())
			dataset.put("draftMode", "✔");
		else
			dataset.put("draftMode", "✖");

		super.addPayload(dataset, leg, "status", "departure", "arrival", "aircraft.registrationNumber", "flight.tag");
		super.getResponse().addData(dataset);
	}
}
