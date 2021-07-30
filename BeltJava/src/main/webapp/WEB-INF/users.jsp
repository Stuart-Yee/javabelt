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
		<div id="ideasBar">
			<h2>Users</h2>
			<a href="/users/demote">Demote Self</a>

		</div>
		
		<table class="table">
			<thead>
				<tr><th>User</th><th>Email</th><th>Permissions</th><th>Action</th></tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
				<tr><td>${user.name }</td>
				<td>${user.email }</td>
				<td>${ idea.numberOfLikes}
					<c:choose>
						<c:when test="${user.permissions } == 1">
						Admin
						</c:when>
						<c:otherwise>
						User
						</c:otherwise>
					</c:choose>
				</td>
				
				<td><a href="/users/reset/${user.id}">Reset Password</a>
				<c:choose>
					<c:when test="${user.permissions == 0}">
						 | <a href="/users/promote/${user.id }">Promote</a>
					</c:when>
					<c:otherwise>				
						| Already Admin
					</c:otherwise>			
				</c:choose>
				
				
				
				</td></tr>
				</c:forEach>
			</tbody>
		</table>

		
		
		</div>
	</t:nav>

</body>
</html>