<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@page import="model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page
    import="com.shashi.service.impl.*, com.shashi.service.*,com.shashi.beans.*,java.util.*,java.io.*"%>
    <!DOCTYPE html>
    <html>
        <head>
            <title>Admin Home</title>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <link rel="stylesheet"
                  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
            <link rel="stylesheet" href="css/changes.css">
            <script
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
            <script
            src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
             
        </head>
        <body style="background-color: #E6F9E6;">
            <%
            /* Checking the user credentials */
    //	String userType = (String) session.getAttribute("usertype");
    //	String userName = (String) session.getAttribute("username");
    //	String password = (String) session.getAttribute("password");
    //
    //	if (userType == null || !userType.equals("admin")) {
    //
    //		response.sendRedirect("login.jsp?message=Access Denied, Login as admin!!");
    //
    //	}
    //
    //	else if (userName == null || password == null) {
    //
    //		response.sendRedirect("login.jsp?message=Session Expired, Login Again!!");

    //	}      
                int pageNumber = (int)request.getAttribute("pageNumber");
                int totalPages = (int)request.getAttribute("totalPages");             
                List<Product> listP = (List<Product>) request.getAttribute("products");            
            %>

            <jsp:include page="header.jsp" />

            <div class="products" style="background-color: #E6F9E6;">

                <div class="container">
                    <h2>Danh sách s?n ph?m</h2>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên s?n ph?m</th>
                                <th>Giá</th>
                                <th>Mô t?</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${products}">
                                <tr>
                                    <td>${product.productId}</td>
                                    <td>${product.productName}</td>
                                    <td>${product.productPrice}</td>
                                    <td>${product.productInfo}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <ul class="pagination">
                        <% 
                           String baseUrl = "adminHome.jsp?page=";
                           for (int i = 1; i <= totalPages; i++) {
                               String cssClass = (i == pageNumber) ? "active" : "";
                        %>
                        <li onclick="getProductPage(<%= i %>)"class="<%= cssClass %>"><a style="cursor: pointer;"><%= i %></a></li>
                            <% } %>
                    </ul>
                </div>

                <div class="tab" align="center">
                    <form>
                        <button type="submit" formaction="adminViewProduct.jsp">View
                            products</button>
                        <br>
                        <br>
                        <button type="submit" formaction="addProduct.jsp">Add
                            products</button>
                        <br>
                        <br>
                        <button type="submit" formaction="removeProduct.jsp">Remove
                            Products</button>
                        <br>
                        <br>
                        <button type="submit" formaction="updateProductById.jsp">Update
                            Products</button>
                        <br>
                        <br>
                    </form>
                </div>
            </div>

            <%@ include file="footer.html"%>
           <script>
                function getProductPage(page) {
                    console.log(page);
                    fetch('ProductController?page=' + page, {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                    })
                            .catch(error => {
                                console.error('?ã có l?i x?y ra:', error);
                            });
                }
            </script>
        </body>

    </html>