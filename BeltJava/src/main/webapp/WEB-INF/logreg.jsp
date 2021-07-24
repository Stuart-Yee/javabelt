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
	<div id="logregwrapper">
		<div class="logregcol">
			<h1>Register</h1>
			<form:form method="POST" action="/" modelAttribute="newUser">
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
				<input type="submit" value="Register"/>
			
			</form:form>
		
		</div>
		<div class="logregcol">
			<h1>Login</h1>
			<form method="POST" action="/login">
				<p class="formLine">
					<span class="error"><c:out value="${loginError }"/></span>
					<label for="logEmail">Email:</label>
					<input name="logEmail" type="email">
				</p>
				<p class="formLine">
					<label for="logPassword">Password:</label>
					<input name="logPassword" type="password">
				</p>
				<button>Login</button>
			
			</form>
		</div>
	
	</div>

</body>
</html>