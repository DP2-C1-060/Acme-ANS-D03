
package acme.entities.activityLogs;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.constraints.ValidLongText;
import acme.constraints.activityLog.ValidActivityLog;
import acme.entities.assignments.FlightAssignment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@ValidActivityLog
public class ActivityLog extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationMoment;

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped
	private String				typeOfIncident;

	@Mandatory
	@ValidLongText
	@Automapped
	private String				description;

	@Mandatory
	@ValidNumber(min = 0, max = 10)
	@Automapped
	private Integer				severityLevel;

	//Relationships ---------------------------------
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private FlightAssignment	flightAssignment;

}
