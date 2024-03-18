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
        <style>
            .btn-group a {
                text-decoration: none;
                color: white;
            }

            th.sortable {
                cursor: pointer;
            }

            th.sortable::after {
                content: '\25B2';
                padding-left: 5px;
                opacity: 0.6;
            }

            th.asc::after {
                content: '\25B2';
                opacity: 1;
            }

            th.desc::after {
                content: '\25BC'; /* Unicode character for downward arrow */
                opacity: 1;
            }
            .thumbnail img {
                max-width: 30%;
                max-height: 30%;
                width: 30%;
                height: 30%;
                transition: transform 0.2s ease-in-out;
            }

            .thumbnail:hover img {
                transform: scale(1.1);
            }
        </style>



    </head>
    <body style="background-color: #E6F9E6;">

        <jsp:include page="header.jsp" />
        <jsp:include page="/common/Message.jsp" />

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
                        <th class="sortable" onclick="sortTable(0)">Picture</th>
                        <th class="sortable" onclick="sortTable(1)">Products</th>
                        <th class="sortable" onclick="sortTable(2)">Price</th>
                        <th class="sortable" onclick="sortTable(3)">Quantity</th>
                        <th>+</th> <!-- Change Add button to + -->
                        <th>-</th> <!-- Change Remove button to - -->
                        <th class="sortable" onclick="sortTable(5)">Amount</th>
                        <th>Update</th> <!-- New column for updating quantity -->
                        <th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Loop through the cart items and display each product -->
                    <c:forEach var="cartItem" items="${cart.items}">
                        <c:set var="userId" value="${cartItem.key}" />
                        <c:set var="a" value="${cartItem.value}" />
                        <c:if test="${userCart.userId == userId}">
                            <c:forEach var="productEntry" items="${a}">
                                <c:set var="productId" value="${productEntry.key}" />
                                <c:set var="quantity" value="${productEntry.value}" />
                                <tr>
                                    <!-- Now you can use productId, userId, and quantity as needed -->
                                    <td class="thumbnail"><img src="${ps.getProductById(productId).image}" alt="Product Image" class="img-thumbnail w-30 h-30"></td>
                                    <td>${ps.getProductById(productId).productName}</td>
                                    <td>${ps.getProductById(productId).productPrice}</td>
                                    <td>${quantity}</td>
                                    <c:if test="${type == 'Admin' && view}">
                                        <td></td>
                                        <td></td>
                                    </c:if>
                                    <c:if test="${(type == 'Admin' && !view) || type != 'Admin'}">
                                <form action="cart" method="POST" id="up">
                                    <input type="text" name="pid" value="${productId}" hidden=""/>
                                    <input type="text" name="updateItem" value="1" hidden=""/>
                                    <td><button type="button" class="btn btn-success btn-add" onclick="submitForm(this)">+</button></td>
                                </form>
                                <form action="cart" method="POST" id="down">
                                    <input type="text" name="pid" value="${productId}" hidden=""/>
                                    <input type="text" name="updateItem" value="-1" hidden=""/>
                                    <td><button type="button" class="btn btn-danger btn-remove" onclick="submitForm(this)">-</button></td>
                                </form>
                            </c:if>  
                            <td>${ps.getProductById(productId).productPrice * quantity}</td>
                            <c:if test="${type == 'Admin' && view}">
                                <td></td>
                            </c:if>
                            <c:if test="${(type == 'Admin' && !view) || type != 'Admin'}">
                                <form action="cart" method="POST" id="form-update-cart">
                                    <td>
                                        <input type="text" name="pid" value="${productId}" hidden=""/>
                                        <input type="number" class="form-control quantity-update" name="updateItem" value="${quantity}"  onchange="submitForm(this)"></td> 
                                </form>
                            </c:if>  
                            <td>
                                <form method="post" action="./cart">
                                    <input type="hidden" name="uid" value="${user.userId}">
                                    <input type="hidden" name="pid" value="${productId}">
                                    <input type="hidden" name="mode" value="cart">
                                    <c:if test="${cart.containsProduct(userCart.userId, productId) && userCart != null}">
                                        <button type="submit" name="action" value="removeFromCart" class="btn btn-danger">Remove From Cart</button>
                                    </c:if>
                                </form>
                            </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
            <input type="text" name="action" value="updateItem" hidden=""/>

            <p>Total Amount: ${total}</p>
            <a href="checkout" class="btn btn-success">Pay now</a>
        </div>

        <%@ include file="footer.jsp"%>
        <script>
            // Filter functionality
            document.getElementById("searchInput").addEventListener("keyup", function () {
                var input, filter, table, tr, td, i, txtValue;
                input = document.getElementById("searchInput");
                filter = input.value.toUpperCase();
                table = document.querySelector(".table");
                tr = table.getElementsByTagName("tr");

                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[1]; // Change index to match the column you want to filter
                    if (td) {
                        txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            });

            // Sorting functionality
            function sortTable(columnIndex) {
                var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
                table = document.querySelector(".table");
                switching = true;
                dir = "asc"; // Set the default direction to ascending

                while (switching) {
                    switching = false;
                    rows = table.getElementsByTagName("tr");

                    for (i = 1; i < (rows.length - 1); i++) {
                        shouldSwitch = false;
                        x = rows[i].getElementsByTagName("td")[columnIndex];
                        y = rows[i + 1].getElementsByTagName("td")[columnIndex];

                        if (dir === "asc") {
                            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                                shouldSwitch = true;
                                break;
                            }
                        } else if (dir === "desc") {
                            if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                                shouldSwitch = true;
                                break;
                            }
                        }
                    }

                    if (shouldSwitch) {
                        rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                        switching = true;
                        switchcount++;
                    } else {
                        if (switchcount === 0 && dir === "asc") {
                            dir = "desc";
                            switching = true;
                        }
                    }
                }

                // Add/remove classes to show sorting direction
                var headers = document.querySelectorAll(".sortable");
                headers.forEach(function (header) {
                    header.classList.remove("asc", "desc");
                });
                var clickedHeader = document.querySelector(".sortable:nth-child(" + (columnIndex + 1) + ")");
                clickedHeader.classList.toggle("asc", dir === "asc");
                clickedHeader.classList.toggle("desc", dir === "desc");
            }
        </script>
        <script>
            function submitForm(input) {
                var form = input.form;
                form.submit();
            }
        </script>

    </body>
</html>
