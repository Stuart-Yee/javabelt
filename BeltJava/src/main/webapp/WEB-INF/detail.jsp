<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/stylesheet.css">
<title>Ideas</title>
</head>
<body>
<h1><c:out value="${thisIdea.name }"/></h1>
<p><h3>Created By: <c:out value="${thisIdea.creator.name}"/></h3></p>
<h2>Users who liked your idea:</h2>
<table>
	<thead><tr><th>Name</th></tr></thead>
	<tbody>
		<c:forEach items="${thisIdea.likers }" var="person">
			<tr><td>
			<c:out value="${person.name }"/>
			</td></tr>
		</c:forEach>
	</tbody>
</table>
<c:choose>
	<c:when test="${loggedIn.id == thisIdea.creator.id }">
		<a href="/ideas/${thisIdea.id }/edit">Edit Your Idea</a>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>


</body>
</html>