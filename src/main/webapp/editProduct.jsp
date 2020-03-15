<%@page import="org.appServlet.model.User"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">

<!DOCTYPE html>
<html>
<head>
<meta charset="US-ASCII">
<title>Add/Edit Product Page</title>
</head>
<body>
<center>
        <h2>Products Managements</h2>
</center>
<%User user = (User) session.getAttribute("User"); %>
<h3>Hi <%=user.getName() %></h3>
<strong>Your Email</strong>: <%=user.getEmail() %><br>
<strong>Your Country</strong>: <%=user.getCountry() %><br>
<br>
<form action="LogoutServlet" method="post">
    <input type="submit" value="Logout" >
</form>
<br>
<center>
<c:choose>
  <c:when test="${product!=null}">
        <h3>Edit Product.</h3>
  </c:when>
  <c:otherwise>
        <h3>Add new Product.</h3>
  </c:otherwise>
</c:choose>
<form action="SaveProdductServlet" method="post">
<table>
    <c:choose>
        <c:when test="${product!=null}">
            <tr>
                <td><strong>Product ID</strong>:</td>
                <td><input type="text" name="id" value="${product.id }"></td>
            </tr>
        </c:when>
    </c:choose>
    <tr>
         <td><strong>Product Name</strong>:</td>
         <td><input type="text" name="name" value="${product.name }"></td>
    </tr>
    <tr>
          <td><strong>Description</strong>:</td>
          <td><input type="text" name="description" value="${product.description }"></td>
    </tr>
    <tr>
          <td><strong>Price</strong>:</td>
          <td><input type="text" name="price" value="${product.price }"></td>
    </tr>
    <tr>
          <td><strong>Stock</strong>:</td>
          <td><input type="text" name="stock" value="${product.stock }"></td>
    </tr>
    <tr>
           <td></td>
           <td><input type="submit" value="Save"></td>
    </tr>
</table>


</form>
</center>
</body>
</html>
