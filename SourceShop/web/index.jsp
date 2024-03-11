<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Ellison Electronics</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/changes.css">
    </head>
    <body style="background-color: #E6F9E6;">
        <jsp:include page="header.jsp" />

        <div class="text-center"
             style="color: black; font-size: 14px; font-weight: bold;"></div>
        <div class="text-center" id="message"
             style="color: black; font-size: 14px; font-weight: bold;"></div>
        <!-- Start of Product Items List -->
        <jsp:include page="/common/Message.jsp" />
        <div class="container">
            <div class="row text-center">
                <c:forEach var="i" items="${list}">
                    <div class="col-sm-4" style='height: 350px;'>
                        <div class="thumbnail">
                            <img src="${i.image}" alt="Product" style="height: 150px; max-width: 180px">
                            <p class="productname">${i.productName}</p>
                            <p class="productinfo">${i.productInfo}</p>
                            <p class="price">${i.productPrice}</p>
                            <form method="post" action="./cart">
                                <input type="hidden" name="uid" value="${user.userId}">
                                <input type="hidden" name="pid" value="${i.productId}">

                                <button type="submit" name="action" value="addToCart" class="btn btn-success">Add to Cart</button>
                                &nbsp;&nbsp;&nbsp;

                                <button type="submit" name="action" value="buyNow" class="btn btn-primary">Buy Now</button>
                                <c:if test="${cart.containsProduct(user.userId, i.productId)}">
                                    <button type="submit" name="action" value="removeFromCart" class="btn btn-danger">Remove From Cart</button>
                                </c:if>
                                &nbsp;&nbsp;&nbsp;

                                <button type="submit" name="action" value="checkout" class="btn btn-success">Pay Now</button>
                            </form>
                            <br />
                        </div>
                    </div>
                </c:forEach>


            </div>
        </div>
        <!-- ENd of Product Items List --
        <%@ include file="footer.jsp"%>

    </body>
</html>