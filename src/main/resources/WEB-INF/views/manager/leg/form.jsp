<%@ page %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:form>
    <acme:input-textbox code="manager.leg.form.label.flightNumber" path="flightNumber" />
	<acme:input-moment code="manager.leg.form.label.scheduledDeparture" path="scheduledDeparture" />
	<acme:input-moment code="manager.leg.form.label.scheduledArrival" path="scheduledArrival" />
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete|publish')}">
		<acme:input-textbox code="manager.leg.form.label.duration" path="duration" readonly="true"/>
	</jstl:if>
	<acme:input-select code="manager.leg.form.label.status" path="status" choices="${status}" />
	<acme:input-select code="manager.leg.form.label.departureAirport" path="departureAirport" choices="${departures}"/>
	<acme:input-select code="manager.leg.form.label.arrivalAirport" path="arrivalAirport" choices="${arrivals}"/>
	<acme:input-select code="manager.leg.form.label.aircraft" path="aircraft" choices="${aircrafts}"/>


    <jstl:choose>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
            <acme:submit code="manager.leg.form.button.publish" action="/manager/leg/publish"/>
			<acme:submit code="manager.leg.form.button.delete" action="/manager/leg/delete"/>
			<acme:submit code="manager.leg.form.button.update" action="/manager/leg/update"/>
        </jstl:when>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="manager.leg.form.button.create" action="/manager/leg/create?masterId=${masterId}"/>
        </jstl:when>
    </jstl:choose>   
</acme:form>
