<%@page import="org.appServlet.model.User"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Home Page</title>
</head>
<body>
<%User user = (User) session.getAttribute("User"); %>
<h3>Hi <%=user.getName() %></h3>
<strong>Your Email</strong>: <%=user.getEmail() %><br>
<strong>Your Country</strong>: <%=user.getCountry() %><br>
<form action="LogoutServlet" method="post">
    <input type="submit" value="Logout" >
</form>

<h3>Product List</h3>
	<c:set var="total" value="0"></c:set>
      <a href="editProduct.jsp"><img src="images/add.png" alt="Edit product" width="30px" height="30px"/></a>
	<table border="1" cellpadding="2" cellspacing="2">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Description</th>
			<th>Price</th>
			<th>Stock</th>
			<th>Action</th>
		</tr>
		<c:forEach var="product" items="${products }">
			<tr>
				<td>${product.id}</td>
				<td>${product.name }</td>
				<td>${product.description }</td>
				<td>${product.price}</td>
				<td>${product.stock }</td>
                    <td>
                    <table>
                        <tr>
                            <td><a href="EditProductServlet?id=${product.id}"><img src="images/edit.png" alt="Edit product" width="30px" height="30px"/></a></td>
                            <td><a href="DeleteProductServlet?id=${product.id}"><img src="images/delete.png" alt="Delete product" width="30px" height="30px"/></a></td>
                        </tr>
                    </table>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
