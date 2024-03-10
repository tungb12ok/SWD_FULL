<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cart Details</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/changes.css">
        <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body style="background-color: #E6F9E6;">

        <jsp:include page="header.jsp" />

        <div class="container mt-4">
            <h2>Cart Details</h2>

            <!-- Search bar -->
            <div class="row mb-3">
                <div class="col-md-6">
                    <input type="text" class="form-control" placeholder="Search products" id="searchInput">
                </div>
            </div>

            <!-- Cart details table -->
            <table class="table table-bordered" id="cartTable">
                <thead>
                    <tr>
                        <th>Picture</th>
                        <th>Products</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Add</th>
                        <th>Remove</th>
                        <th>Amount</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Loop through the cart items and display each product -->
                    <c:forEach var="cartItem" items="${cart.items}">
                        <tr>
                            <td><img src="${cartItem.product.image}" alt="Product Image" class="img-thumbnail"></td>
                            <td>${cartItem.product.productName}</td>
                            <td>${cartItem.product.productPrice}</td>
                            <td>${cartItem.quantity}</td>
                            <td><a href="cart?action=addToCart&pid=${cartItem.product.productId}" class="btn btn-success">Add</a></td>
                            <td><a href="cart?action=removeFromCart&pid=${cartItem.product.productId}" class="btn btn-danger">Remove</a></td>
                            <td>${cartItem.quantity * cartItem.product.productPrice}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Display total cart amount or any other relevant information -->
            <p>Total Amount: ${cart.totalAmount}</p>
        </div>


        <%@ include file="footer.html"%>

    </body>
</html>