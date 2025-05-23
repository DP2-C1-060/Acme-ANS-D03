<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <acme:input-select code="customer.bookingRecord.form.label.passenger" path="passenger" choices="${passengers}"/>
    <acme:input-select code="customer.bookingRecord.form.label.booking" path="booking" choices="${bookings}"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="customer.bookingPassenger.form.button.create" action="/customer/booking-record/create"/>
			
		</jstl:when>		
	</jstl:choose>	
</acme:form>