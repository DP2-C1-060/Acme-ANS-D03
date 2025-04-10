
package acme.features.agent.tracking;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claim.Claim;
import acme.entities.tracking.Tracking;
import acme.entities.tracking.TrackingStatus;
import acme.realms.agent.Agent;

@GuiService
public class AgentTrackingCreateService extends AbstractGuiService<Agent, Tracking> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AgentTrackingRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Claim claim;

		masterId = super.getRequest().getData("masterId", int.class);
		claim = this.repository.findClaimById(masterId);
		status = claim != null && claim.isDraftMode() && super.getRequest().getPrincipal().hasRealm(claim.getAgent());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tracking tracking;
		int masterId;
		Claim claim;

		masterId = super.getRequest().getData("masterId", int.class);
		claim = this.repository.findClaimById(masterId);

		tracking = new Tracking();
		tracking.setDraftMode(true);
		tracking.setLastUpdateMoment(MomentHelper.getCurrentMoment());
		tracking.setClaim(claim);

		super.getBuffer().addData(tracking);
	}

	@Override
	public void bind(final Tracking tracking) {
		super.bindObject(tracking, "resolution", "resolutionPercentage", "indicator", "step");
	}

	@Override
	public void validate(final Tracking tracking) {
		;
	}

	@Override
	public void perform(final Tracking tracking) {
		this.repository.save(tracking);
	}

	@Override
	public void unbind(final Tracking tracking) {
		Dataset dataset;
		SelectChoices stateChoices;

		stateChoices = SelectChoices.from(TrackingStatus.class, tracking.getIndicator());
		dataset = super.unbindObject(tracking, "resolution", "resolutionPercentage", "step", "indicator", "lastUpdateMoment", "draftMode");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));
		dataset.put("states", stateChoices);

		super.getResponse().addData(dataset);
	}

}
