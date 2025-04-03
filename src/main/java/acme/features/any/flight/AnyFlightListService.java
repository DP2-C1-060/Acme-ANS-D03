
package acme.features.any.flight;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Any;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flights.Flight;

@GuiService
public class AnyFlightListService extends AbstractGuiService<Any, Flight> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyFlightRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Flight> flights;

		flights = this.repository.findPublishedFlights();

		super.getBuffer().addData(flights);
	}

	@Override
	public void unbind(final Flight flight) {
		Dataset dataset;

		dataset = super.unbindObject(flight, "tag", "cost", "description");
		if (flight.isSelfTransfer())
			dataset.put("selfTransfer", "✔");
		else
			dataset.put("selfTransfer", "✖");

		dataset.put("departure-arrival", flight.getOriginCity() + " - " + flight.getDestinationCity());
		dataset.put("manager", flight.getManager().getUserAccount().getUsername() + " | " + flight.getManager().getIdentifier());
		super.getResponse().addData(dataset);
	}
}
