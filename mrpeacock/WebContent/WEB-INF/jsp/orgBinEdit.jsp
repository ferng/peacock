<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	
		<table border="1">
			<tr>
				<td>
					<img src="images/mrPeacock.jpg" title="picture" alt="logo"/>
				</td>
				<td>Mr Peacock  - Organization Bin Range Management Screen:</td>
			</tr>
			<tr>
				<td>
					<a href="orgShowAllOrgs">Edit an Organisation, users and bin ranges</a><br>
					<a href="adminLoggedOut">Log out</a><br>
				</td>
				<td>
					<table border="1">
						<tr>
							<td>Bin Range</td>
							<td>Bin Range Org</td>
						</tr>
						<tr>
							<form:form method="post" modelAttribute="range">
								<form:errors path="*" cssClass="error" />
 								<td>
									<form:input disabled="${binRangeEditable}" path="binRange" maxlength="${binRangeSize}"/>
									<form:errors path="binRange" cssClass="error"/>
								</td>
								<td>
									<form:select path="binOrg" multiple="false">
										<form:options items="${orgCodeList}"/>
									</form:select>
								</td>
								<td> <input type="submit" value="${binRangeEditAction}"/> </td>
								<td> <input type="button" value="Cancel" onclick="location.href='orgShowAllBins?orgId=${bin.binOrg}'"/> </td>
			 				</form:form>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>