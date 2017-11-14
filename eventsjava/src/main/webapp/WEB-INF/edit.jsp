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
<h1>
	<c:out value="${event.name}"></c:out>
</h1>

<h2>Edit Event</h2>
<form:form method="POST" action="/events/${event.id}/edit" modelAttribute="event">
	<form:label path="name">Name: </form:label>
	<form:errors path="name"></form:errors>
	<form:input path="name"></form:input>
	<br />
	<form:label path="date">Date: </form:label>
	<form:errors path="date"></form:errors>
	<form:input type="date" path="date"></form:input>
	<br />
	<form:label path="city">Location: </form:label>
	<form:errors path="city"></form:errors>
	<form:input path="city"></form:input>	
	<form:select path="state">
		<form:options items="${states}"/>
	</form:select>
	<br />
	<input type="submit" value="Edit Event"/>
</form:form>
</body>
</html>