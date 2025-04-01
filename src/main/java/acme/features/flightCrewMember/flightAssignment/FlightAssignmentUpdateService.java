
package acme.features.flightCrewMember.flightAssignment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.assignments.AssignmentStatus;
import acme.entities.assignments.FlightAssignment;
import acme.entities.assignments.FlightCrewDuty;
import acme.entities.legs.Leg;
import acme.realms.flightCrewMember.AvailabilityStatus;
import acme.realms.flightCrewMember.FlightCrewMember;

@GuiService
public class FlightAssignmentUpdateService extends AbstractGuiService<FlightCrewMember, FlightAssignment> {

	@Autowired
	private FlightAssignmentRepository flightAssignmentRepository;


	@Override
	public void authorise() {
		boolean status;
		int flightAssignmentId;
		FlightAssignment flightAssignment;
		FlightCrewMember crewMember;

		flightAssignmentId = super.getRequest().getData("id", int.class);
		flightAssignment = this.flightAssignmentRepository.findFlightAssignmentById(flightAssignmentId);
		crewMember = flightAssignment == null ? null : flightAssignment.getFlightCrewMember();

		status = flightAssignment != null && super.getRequest().getPrincipal().hasRealm(crewMember);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		FlightAssignment flightAssignment;

		id = super.getRequest().getData("id", int.class);
		flightAssignment = this.flightAssignmentRepository.findFlightAssignmentById(id);
		super.getBuffer().addData(flightAssignment);
	}

	@Override
	public void bind(final FlightAssignment flightAssignment) {

		int crewMemberId = super.getRequest().getData("flightCrewMember", int.class);
		FlightCrewMember crewMember = this.flightAssignmentRepository.findCrewMemberById(crewMemberId);

		int legId = super.getRequest().getData("leg", int.class);
		Leg leg = this.flightAssignmentRepository.findLegById(legId);

		super.bindObject(flightAssignment, "duty", "status", "remarks");

		flightAssignment.setFlightCrewMember(crewMember);
		flightAssignment.setLeg(leg);

	}

	@Override
	public void perform(final FlightAssignment flightAssignment) {
		flightAssignment.setLastUpdate(MomentHelper.getCurrentMoment());
		this.flightAssignmentRepository.save(flightAssignment);
	}

	@Override
	public void validate(final FlightAssignment flightAssignment) {
		{

			List<FlightCrewDuty> ls = this.flightAssignmentRepository.findPilotsInLegByLegId(flightAssignment.getLeg().getId());
			boolean status = true;
			if (flightAssignment.getDuty().equals(FlightCrewDuty.PILOT))
				if (ls.contains(FlightCrewDuty.PILOT))
					status = false;
			if (flightAssignment.getDuty().equals(FlightCrewDuty.COPILOT))
				if (ls.contains(FlightCrewDuty.COPILOT))
					status = false;
			super.state(status, "duty", "acme.validation.confirmation.message.moreThanOnePilotOrCopilot");

		}
		{

			boolean crewMemberAvailable;
			crewMemberAvailable = flightAssignment.getFlightCrewMember().getAvailabilityStatus() == AvailabilityStatus.AVAILABLE;
			super.state(crewMemberAvailable, "flightCrewMember", "acme.validation.confirmation.message.crewMemberNotAvailable");

		}

		{
			boolean onlyOneLeg;
			onlyOneLeg = this.flightAssignmentRepository.findLegsByCrewMemberId(flightAssignment.getFlightCrewMember().getId()).isEmpty();
			super.state(onlyOneLeg, "flightCrewMember", "acme.validation.confirmation.message.crewMemberAlreadyInLeg");

		}
	}

	@Override
	public void unbind(final FlightAssignment flightAssignment) {
		Dataset dataset;
		SelectChoices legChoices;
		SelectChoices dutyChoices;
		SelectChoices statusChoices;
		SelectChoices crewMemberChoices;

		dutyChoices = SelectChoices.from(FlightCrewDuty.class, null);
		statusChoices = SelectChoices.from(AssignmentStatus.class, null);

		List<Leg> legList = this.flightAssignmentRepository.findAllLegs();
		legChoices = SelectChoices.from(legList, "flightNumber", null);

		List<FlightCrewMember> crewMemberList = this.flightAssignmentRepository.findAllCrewMembers();
		crewMemberChoices = SelectChoices.from(crewMemberList, "employeeCode", null);

		dataset = super.unbindObject(flightAssignment, "duty", "status", "lastUpdate", "draftMode");
		dataset.put("duties", dutyChoices);
		dataset.put("statuses", statusChoices);
		dataset.put("lastUpdate", flightAssignment.getLastUpdate());
		dataset.put("flightCrewMembers", crewMemberChoices);
		dataset.put("flightCrewMember", crewMemberChoices.getSelected().getKey());
		dataset.put("legs", legChoices);
		dataset.put("leg", legChoices.getSelected().getKey());

		super.getResponse().addData(dataset);
	}
}
