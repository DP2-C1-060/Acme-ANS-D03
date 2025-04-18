
package acme.features.manager.flight;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flights.Flight;
import acme.entities.legs.Leg;
import acme.realms.manager.Manager;

@GuiService
public class ManagerFlightDeleteService extends AbstractGuiService<Manager, Flight> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerFlightRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int flightId;
		Flight flight;
		Manager manager;

		flightId = super.getRequest().getData("id", int.class);
		flight = this.repository.findFlightById(flightId);
		manager = flight == null ? null : flight.getManager();
		status = flight != null && super.getRequest().getPrincipal().hasRealm(manager) && flight.isDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int flightId;
		Flight flight;

		flightId = super.getRequest().getData("id", int.class);
		flight = this.repository.findFlightById(flightId);

		super.getBuffer().addData(flight);
	}

	@Override
	public void bind(final Flight flight) {

		super.bindObject(flight, "tag", "selfTransfer", "cost", "description");
	}

	@Override
	public void validate(final Flight flight) {
		if (flight != null) {
			List<Leg> publishedLegs = this.repository.findPublishedLegsByFlightId(flight.getId());
			boolean canDelete = publishedLegs.isEmpty();
			super.state(canDelete, "*", "acme.validation.flight.delete");

		}
	}

	@Override
	public void perform(final Flight flight) {
		List<Leg> flightLegs = this.repository.findLegsByFlight(flight.getId());

		this.repository.deleteAll(flightLegs);
		this.repository.delete(flight);
	}

	@Override
	public void unbind(final Flight flight) {
		Dataset dataset;

		dataset = super.unbindObject(flight, "tag", "selfTransfer", "cost", "description", "draftMode");
		dataset.put("scheduledDeparture", flight.getScheduledDeparture());
		dataset.put("scheduledArrival", flight.getScheduledArrival());
		dataset.put("departureCity", flight.getOriginCity());
		dataset.put("arrivalCity", flight.getDestinationCity());
		dataset.put("layovers", flight.getLayoverCount());

		super.getResponse().addData(dataset);
	}

}
