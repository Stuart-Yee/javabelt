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
<h1><c:out value="${thisIdea.name}"/></h1>

<form method="POST" action="/ideas/${thisIdea.id }/edit">
	<p>
		<label for="entry">Content:</label>
		
		<textarea name="entry">${thisIdea.name }</textarea>
		<span class="error"><form:errors path="name"/></span>
	</p>
	<button>Update</button>

<form>
<a href="/ideas/${thisIdea.id }/delete">Delete</a>

</body>
</html>