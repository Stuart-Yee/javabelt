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

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

	<title>Ideas</title>
</head>
<body>
	
	<t:nav>
	<div class="content">
		<h1>Edit User</h1>
			<form:form  action="/users/${thisUser.id}/edit" modelAttribute="thisUser" method="POST"  id="userEdit">
				<p class="formLine">
					<form:label path="name">Name:</form:label>
					<span class="error"><form:errors path="name"/></span>
					<form:input path="name"/>
				</p>
				<p class="formLine">
					<form:label path="email">Email:</form:label>
					<span class="error"><form:errors path="email"/></span>
					<form:input type="email" path="email"/>
				</p>
				<p class="formLine">
					<form:label path="password">Password:</form:label>
					<span class="error"><form:errors path="password"/></span>
					<form:input type="password" path="password"/>
				</p>
				<p class="formLine">
					<form:label path="passwordConfirm">Confirm Password:</form:label>
					<span class="error"><form:errors path="passwordConfirm"/></span>
					<form:input type="password" path="passwordConfirm"/>
				</p>
				<button class="btn btn-outline-primary">Update</button>
			
			</form:form>
			<a href="/ideas">Cancel</a>
	</div>
	</t:nav>

</body>
</html>