<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Payments</title>
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

        <jsp:include page="header.jsp" />

        <div class="container">
            <div class="row"
                 style="margin-top: 5px; margin-left: 2px; margin-right: 2px;">
                <div class="col-md-6">
                    <form action="checkout" method="post"
                          class="col-md-6 col-md-offset-3"
                          style="border: 2px solid black; border-radius: 10px; background-color: #FFE5CC; padding: 10px;">
                        <div style="font-weight: bold;" class="text-center">
                            <div class="form-group">
                                <img src="images/profile.jpg" alt="Payment Proceed" height="100px" />
                                <h2 style="color: green;">Receiver Infomation</h2>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 form-group">
                                <label for="last_name">Receiver</label> <input
                                    type="text" placeholder="Enter name"
                                    name="Receiver" class="form-control" id="last_name" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 form-group">
                                <label for="last_name">Email</label> <input
                                    type="text" placeholder="xxx@xxx.xxx format" name="Email"
                                    class="form-control" id="last_name" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 form-group">
                                <label for="last_name">Mobile</label> <input
                                    type="number" placeholder="0xxxxxxxxx" name="Mobile"
                                    class="form-control" id="last_name" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 form-group">
                                <label for="last_name">Address</label> <input
                                    type="text" placeholder="Address" name="Address"
                                    class="form-control" id="last_name" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 form-group">
                                <input type="radio" name="payment" value="COD" checked=""/>
                                <label for="last_name">COD</label>
                                <br>
                                <input type="radio" name="payment" value="Visa" />
                                <label for="last_name">Visa</label>
                                <br>
                                <input type="radio" name="payment" value="VNPost Payment" />
                                <label for="last_name">VNPost Payment</label>

                            </div>
                        </div>
                        <div class="row text-center">
                            <div class="col-md-12 form-group">
                                <input class="btn btn-success" type="submit" value="Checkout" />
                                <a href="cart">
                                    <button class="btn btn-primary" type="button">Update</button>
                                </a>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="col-md-6">
                    <h2>Your Shopping Cart</h2>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="productEntry" items="${listCart}">
                                <c:set var="productId" value="${productEntry.key}" />
                                <c:set var="quantity" value="${productEntry.value}" />
                                <tr>
                                    <td>${ps.getProductById(productId).productName}</td>
                                    <td>${quantity}</td>
                                    <td>${ps.getProductById(productId).productPrice}</td>
                                    <td>${ps.getProductById(productId).productPrice * quantity}</td>
                                <tr>
                                </c:forEach>
                            </tr>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="3" style="text-align: right;"><strong>Order (${totalProducts} Prpducts):</strong></td>
                                <td><strong>${total}</strong></td>
                            </tr>
                            <tr>
                                <td colspan="3" style="text-align: right;"><strong>Shipping fee:</strong></td>
                                <td><strong>$40</strong></td>
                            </tr>
                            <tr>
                                <td colspan="3" style="text-align: right;"><strong>Total:</strong></td>
                                <td><strong>${total + 40}</strong></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>

            </div>
        </div>

        <!-- ENd of Product Items List -->


        <%@ include file="footer.jsp"%>

    </body>
</html>