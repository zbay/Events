<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login/Registration Page</title>
</head>
<body>
<h1 class="col">Welcome, <c:out value="${currentUser.firstName}"></c:out></h1>
<form id="logoutForm" class="col" method="POST" action="/logout">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Logout"/>
</form>
<h2>Events in your state:</h2>
<table>
	<thead>
		<tr>
			<td>Name</td>
			<td>Date</td>
			<td>Location</td>
			<td>Host</td>
			<td>Action / Status</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${events}" var="event">
			<c:if test="${event.state.equals(currentUser.state)}">
			<tr>
				<td>
					<a href="/events/${event.id}">
						<c:out value="${event.name}"></c:out>
					</a>
				</td>
				<td>
					<fmt:formatDate pattern = "MMMM d, yyyy" dateStyle = "long" value = "${event.date}" />
				</td>
				<td>
					<c:out value="${event.city}"></c:out>
				</td>
				<td>
					<c:out value="${event.getHost().getFirstName()}"></c:out>
				</td>
				<td>
				<c:choose>
					<c:when test="${event.host.equals(currentUser)}">
						<a href="/events/${event.id}/edit">Edit</a> | 
						<a href="/events/${event.id}/delete">Delete</a>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${currentUser.getJoinedEvents().contains(event)}">
								<span>Joining</span> | 
								<a href="/events/${event.id}/cancel">Cancel</a>
							</c:when>
							<c:otherwise>
								<a href="/events/${event.id}/join">Join</a>			
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:if>
		</c:forEach>
	</tbody>
</table>
<h2>Events in other states:</h2>
<table>
	<thead>
		<tr>
			<td>Name</td>
			<td>Date</td>
			<td>Location</td>
			<td>State</td>
			<td>Host</td>
			<td>Action / Status</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${events}" var="event">
		<c:if test="${!event.state.equals(currentUser.state)}">
			<tr>
				<td>
					<a href="/events/${event.id}">
						<c:out value="${event.name}"></c:out>
					</a>
				</td>
				<td>
					<fmt:formatDate pattern = "MMMM d, yyyy" dateStyle = "long" value = "${event.date}" />
				</td>
				<td>
					<c:out value="${event.city}"></c:out>
				</td>
				<td>
					<c:out value="${event.state}"></c:out>
				</td>
				<td>
					<c:out value="${event.getHost().getFirstName()}"></c:out>
				</td>
				<td>
				<c:choose>
					<c:when test="${event.host.equals(currentUser)}">
						<a href="/events/${event.id}/edit">Edit</a> | 
						<a href="/events/${event.id}/delete">Delete</a>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${currentUser.getJoinedEvents().contains(event)}">
								<span>Joining</span> | 
								<a href="/events/${event.id}/cancel">Cancel</a>
							</c:when>
							<c:otherwise>
								<a href="/events/${event.id}/join">Join</a>			
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:if>
		</c:forEach>
	</tbody>
</table>
<h2>Create an Event</h2>
<form:form method="POST" action="/events" modelAttribute="newEvent">
	<form:label path="name">Name</form:label>
	<form:errors path="name"></form:errors>
	<form:input path="name"></form:input>
	<br />
	<form:label path="date">Date</form:label>
	<form:errors path="date"></form:errors>
	<form:input type="date" path="date"></form:input>
	<br />
	<form:label path="city">Location:</form:label>
	<form:errors path="city"></form:errors>
	<form:input path="city"></form:input>	
	<form:select path="state">
		<form:options items="${states}"/>
	</form:select>
	<br />
	<input type="submit" value="Create Event"/>
</form:form>
</body>
</html>