<%--
- menu.jsp
-
- Copyright (C) 2012-2025 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:menu-bar>
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link" action="http://www.example.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.javmunrom" action="https://www.linkedin.com/in/javier-mu%C3%B1oz-romero-a982391b9/"/>
			<acme:menu-suboption code="master.menu.anonymous.nicpergom" action="https://www.linkedin.com/in/nicolas-perez-gomez-5748572a1/"/>
			<acme:menu-suboption code="master.menu.anonymous.juanunsan" action="https://www.linkedin.com/in/juan-nunez-sanchez/"/>
			<acme:menu-suboption code="master.menu.anonymous.serconbaj" action="https://www.linkedin.com/in/sergio-conde-bajo"/>
			<acme:menu-suboption code="master.menu.anonymous.ivafrurai" action="https://www.linkedin.com/in/ivan-frutos-raigon"/>
			
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.flight" access="hasRealm('Any')">
			<acme:menu-suboption code="master.menu.anonymous.list-flights" action="/any/flight/list" />
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRealm('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.list-user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.list-bookings" action="/administrator/booking/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-airlines" action="/administrator/airline/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-db-initial" action="/administrator/system/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-db-sample" action="/administrator/system/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-system-down" action="/administrator/system/shut-down"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.technician" access="hasRealm('Technician')">
			<acme:menu-suboption code="master.menu.technician.list-maintenanceRecord" action="/technician/maintenance-record/list"/>
			<acme:menu-suboption code="master.menu.technician.list-mine-maintenanceRecord" action="/technician/maintenance-record/list-mine"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.technician.list-task" action="/technician/task/list"/>
			<acme:menu-suboption code="master.menu.technician.list-mine-task" action="/technician/task/list-mine"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.provider" access="hasRealm('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.manager" access="hasRealm('Manager')">
			<acme:menu-suboption code="master.menu.manager.list-legs" action="/manager/leg/list"/>
			<acme:menu-suboption code="master.menu.manager.list-flights" action="/manager/flight/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.manager.dashboard" action="/manager/manager-dashboard/show" />
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRealm('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.customer" access="hasRealm('Customer')">
			<acme:menu-suboption code="master.menu.customer.dashboard" action="/customer/customer-dashboard/show"/>
			<acme:menu-suboption code="master.menu.customer.list-bookings" action="/customer/booking/list"/>
			<acme:menu-suboption code="master.menu.customer.list-passengers" action="/customer/passenger/list"/>
			<acme:menu-suboption code="master.menu.customer.list-booking-records" action="/customer/booking-record/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.agent" access="hasRealm('Agent')">
			<acme:menu-suboption code="master.menu.agent.list-claims" action="/agent/claim/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.member" access="hasRealm('FlightCrewMember')">
			<acme:menu-suboption code="master.menu.member.completedlist-flightAssignment" action="/flight-crew-member/flight-assignment/completedlist"/>
			<acme:menu-suboption code="master.menu.member.notCompletedlist-flightAssignment" action="/flight-crew-member/flight-assignment/notCompletedlist"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.member.myCompletedList-flightAssignment" action="/flight-crew-member/flight-assignment/myCompletedList"/>
			<acme:menu-suboption code="master.menu.member.myNotCompletedList-flightAssignment" action="/flight-crew-member/flight-assignment/myNotCompletedList"/>		
		</acme:menu-option>
		
	</acme:menu-left>

	<acme:menu-right>		
		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-profile" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRealm('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider-profile" action="/authenticated/provider/update" access="hasRealm('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRealm('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer-profile" action="/authenticated/consumer/update" access="hasRealm('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-customer" action="/authenticated/customer/create" access="!hasRealm('Customer')"/>
 			<acme:menu-suboption code="master.menu.user-account.customer-profile" action="/authenticated/customer/update" access="hasRealm('Customer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-manager" action="/authenticated/manager/create" access="!hasRealm('Manager')"/>
			<acme:menu-suboption code="master.menu.user-account.manager-profile" action="/authenticated/manager/update" access="hasRealm('Manager')"/>
		</acme:menu-option>
	</acme:menu-right>
</acme:menu-bar>

