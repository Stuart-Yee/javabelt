<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "t" tagdir = "/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="/css/stylesheet.css">
	<title>Ideas</title>
</head>
<body>
<t:nav>
<h2><c:out value="${thisIdea.name }"/></h2>
<p><h4>Created By: <c:out value="${thisIdea.creator.name}"/></h4></p>
<h4>Users who liked your idea:</h4>
<table class="table">
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
</t:nav>

</body>
</html>