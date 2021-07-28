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
	<div id="ideaWrapper">
		<h1>Welcome, <c:out value="${loggedIn.name }"/></h1>
		<div id="topBar">
			<h2>Ideas</h2>
			<ul>
				<li><a href="/ideas/asc">Low Likes</a></li>
				<li><a href="/ideas/desc">High Likes</a></li>
			</ul>
		</div>
		
		<table>
			<thead>
				<tr><th>Idea</th><th>Creator</th><th>Likes</th><th>Action</th></tr>
			</thead>
			<tbody>
				<c:forEach items="${ideas}" var="idea">
				<tr><td><a href="/ideas/${idea.id}">${idea.name }</a></td>
				<td>${idea.creator.name }</td><td>${ idea.numberOfLikes}</td>
				<td><a href="/like/${idea.id}">
				<c:choose>
					<c:when test="${loggedIn.id == 0}">
					Login to Like
					</c:when>
					<c:otherwise>				
					<c:choose>
						<c:when test="${idea.likers.contains(loggedIn) }">Unlike
						</c:when>
						<c:otherwise>
						Like
						</c:otherwise>
					</c:choose>		
					</c:otherwise>			
				</c:choose>
				
				
				
				</a></td></tr>
				</c:forEach>
			</tbody>
		</table>
		<c:choose>
			<c:when test="${loggedIn.id == 0}">
				<p>Login to Create a New Idea</p>
			</c:when>
			<c:otherwise>
				<p><a href="/ideas/new">Create an Idea</a></p>
			</c:otherwise>
		</c:choose>
		
		
		<p><a href="/logout">Logout</a></p>
	</div>

</body>
</html>