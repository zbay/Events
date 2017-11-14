<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
	.col{
		display:inline-block;
		vertical-align:top;
	}
	#wall{
		height:300px;
		overflow-y:scroll;
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
<c:out value="${event.getName()}"></c:out>
</title>
</head>
<body>
	<div class="col">
		<h1>
			<c:out value="${event.getName()}"></c:out>
		</h1>
		<p>
			Host: <c:out value="${event.host.getFirstName()}"></c:out>
		</p>
		<p>
			Date: <fmt:formatDate pattern = "MMMM d, yyyy" dateStyle = "long" value = "${event.getDate()}" />
		</p>
		<p>
			Location: <c:out value="${event.getCity()}"></c:out>, <c:out value="${event.getState()}"></c:out>
		</p>
		<p>
			People who are attending this event: <c:out value="${event.joiners.size()}"></c:out>
		</p>
		<table>
			<tr>
				<td>Name</td>
				<td>Location</td>
			</tr>
			<c:forEach items="${event.joiners}" var="joiner">
				<tr>
					<td>
						<c:out value="${joiner.getFirstName()}"></c:out> <c:out value="${joiner.getLastName()}"></c:out>
					</td>
					<td>
						<c:out value="${joiner.getCity()}"></c:out>
					</td>
				</tr>
			</c:forEach>
			</table>
			</div>
			<div class="col">
				<h1>Message Wall</h1>
				<div id="wall">
					<c:forEach items="${event.getMessages()}" var="message">
						<p class="message">
							<c:out value="${message.author.getFirstName()}"></c:out> says <c:out value="${message.getText()}"></c:out>
						</p>
					</c:forEach>
				</div>
			</div>
		<form action="/events/${event.getId()}" method="post">
			<label for="text">New Comment</label>
			<input type="text" name="text"/>
			 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="submit" value="Post"/>
		</form>
</body>
</html>