<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Admin Console</title>
		<style>
			.error {
				color: #ff0000;
				font-weight: bold;
			}
		</style>
	</head>

	<body>
		<form:form method="post" modelAttribute="user">
			<form:errors path="*" cssClass="error" />
			<h3>Mr Peacock's Admin Console</h3>
			<br>
				<table>
					<tr>
						<th>User Name</th>
						<td>
							<form:input path="userId" />
							<form:errors path="userId" cssClass="error" />
						</td>	
					</tr>
					<tr>
						<th>Password</th>
						<td>
							<form:input path="userPw" />
							<form:errors path="userPw" cssClass="error" />
						</td>	
					</tr>
				</table>
				<br>
			<input type="submit" value="Login" />
		</form:form>

	</body>
</html>