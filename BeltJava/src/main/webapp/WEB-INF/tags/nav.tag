<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="/css/stylesheet.css">

		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

		<title>Ideas</title>
	</head>
	<body>
		<div class="container">
			<div class="jumbotron jumbotron-fluid">
  				<div class="container">
  						<img src="/images/bulb.png" alt="bulb" class="bulb">
  						<div class="jumboText">
    						<h1 class="display-4">Great Solutions</h1>
    						<p class="lead">Welcome, <c:out value="${loggedIn.name }"/>
    					
    					<span id="smallnav">
				<c:choose>
					<c:when test="${loggedIn.id == 0}">
						Login to Create a New Idea
					</c:when>
					<c:otherwise>
						<a href="/ideas/new">Create an Idea</a>
					</c:otherwise>
				</c:choose> |
				<a href="/ideas">Ideas Dashboard</a>
				| <a href="users/${loggedIn.id}/edit">Edit Profile</a>
				<c:choose>
					<c:when test="${loggedIn.permissions == 1 }">
						| <a href="/users">Users</a>
					</c:when>
				</c:choose>
			 | <a href="/logout">Logout</a></span>
			 </p>
			 </div>
  				</div>
		</div>
			<hr>
			<jsp:doBody/>
		</div>





		
	</body>
</html>