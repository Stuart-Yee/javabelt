<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/stylesheet.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<title>Ideas</title>
</head>
<body>
	<div class="jumbotron jumbotron-fluid">
  		<div class="container">
    		<h1 class="display-4">Great Ideas</h1>
    		<p class="lead">Dream up ideas and collaborate!</p>
  		</div>
	</div>
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
				<button class="btn btn-outline-primary">Register</button>
			
			</form:form>
		
		</div>
		<div class="logregcol">
			<h1>Login</h1>
			<form method="POST" action="/login">
				<p class="formLine">
					
					<label for="logEmail">Email:</label>
					<input name="logEmail" type="email">
				</p>
				<p><span class="error"><c:out value="${loginError }"/></span></p>
				<p class="formLine">
					<label for="logPassword">Password:</label>
					<input name="logPassword" type="password">
				</p>
				<button class="btn btn-outline-success">Login</button>
			
			</form>
		</div>
	
	</div>

</body>
</html>