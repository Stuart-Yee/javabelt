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
<h1><c:out value="${thisIdea.name}"/></h1>

<form:form method="POST" action="/ideas/${thisIdea.id}/edit" modelAttribute="thisIdea">
	<p>
		<form:label path="name">Content:</form:label>
		<span class="error"><form:errors path="name"/></span>
		<form:textarea path="name"/>
	</p>
	<input type="submit" value="Update">

</form:form>
<a href="/ideas/${thisIdea.id }/delete">Delete</a>
</t:nav>
</body>
</html>