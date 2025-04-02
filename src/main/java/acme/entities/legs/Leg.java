
package acme.entities.legs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidString;
import acme.constraints.ValidLeg;
import acme.entities.aircraft.Aircraft;
import acme.entities.airport.Airport;
import acme.entities.flights.Flight;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidLeg
public class Leg extends AbstractEntity {

	// Serialisation version --------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidString(pattern = "^[A-Z]{3}\\d{4}$")
	@Column(unique = true)
	private String				flightNumber;

	@Mandatory
	@ValidMoment(past = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				scheduledDeparture;

	@Mandatory
	@ValidMoment(past = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				scheduledArrival;

	@Mandatory
	@Automapped
	@Valid
	private LegStatus			status;

	@Mandatory
	@Automapped
	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Double getDuration() {
		if (this.scheduledDeparture == null || this.scheduledArrival == null)
			return 0.0;
		long diffMillis = this.scheduledArrival.getTime() - this.scheduledDeparture.getTime();
		double diffMinutes = diffMillis / 60000.0;
		return diffMinutes;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@ManyToOne(optional = false)
	@Valid
	private Airport		departureAirport;

	@Mandatory
	@ManyToOne(optional = false)
	@Valid
	private Airport		arrivalAirport;

	@Mandatory
	@ManyToOne(optional = false)
	@Valid
	private Aircraft	aircraft;

	@Mandatory
	@ManyToOne(optional = false)
	@Valid
	private Flight		flight;
}
