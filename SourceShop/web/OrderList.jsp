<%-- 
    Document   : OrderList
    Created on : Mar 10, 2024, 7:27:49 PM
    Author     : win
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/changes.css">
        <script

        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="css/styleAdminProduct.css">
    </head>
    <body style="background-color: #E6F9E6;">
        <%
            int pageNumber = (int)request.getAttribute("pageNumber");
            int totalPages = (int)request.getAttribute("totalPages");
        %>
        <jsp:include page="header.jsp" />
        <div class="filter" style="padding-top: 20px; padding-bottom: 20px; display: flex;">
            <div style="display: flex; padding-left: 50px;">
                <label style="padding: 10px 20px 0 0;">Status:</label>    
                <select  class="form-control" id="filter-status" style="width: 200px; height: 50px;text-align: center;" onchange="filterStatus()">
                    <option value="">-- Select Status --</option>
                    <c:forEach var="s" items="${status}">
                        <option value="${s.id}">${s.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div style="display: flex; padding-left: 50px;">
                <label style="padding: 10px 20px 0 0;">Saler:</label>    
                <select  class="form-control" id="filter-admin" style="width: 200px; height: 50px;text-align: center;" onchange="filterAdmin()">
                    <option value="">-- Select Saler --</option>
                    <c:forEach var="ad" items="${admins}">
                        <option value="${ad.userId}">${ad.name}</option>
                    </c:forEach>
                </select>
            </div>

        </div>
        <table class="table">
            <thead>
                <tr>
                    <th style="width: 10%;">TransactionId</th>
                    <th style="width: 10%;">Order Time</th>
                    <th style="width: 10%;">Total Money</th>
                    <th style="width: 10%;">Receiver</th>
                    <th style="width: 10%;">Mobile</th>
                    <th style="width: 10%;">Email</th>
                    <th style="width: 10%;">Address</th>
                    <th style="width: 10%;">Saler</th>
                    <th style="width: 10%;">Status</th>
                    <th style="width: 10%;"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${listO}">
                    <tr>
                        <td>
                            ${o.orderId}
                        </td>
                        <td>${o.orderDate}</td>
                        <td>
                            <fmt:formatNumber value="${o.total}" pattern="#,##0 VND"/>
                        </td>
                        <td>
                            ${o.receiver}
                        </td>

                        <td>${o.mobile}</td>
                        <td>${o.email}</td>
                        <td>${o.address}</td>
                        <td>${o.saler}</td>
                        <td>
                            <c:forEach var="st" items="${status}">
                                <c:if test="${st.id == o.status}">
                                    <div class="action-status active" id="table-status-${o.orderId}" onclick="changeStatus(${o.orderId})">${st.name}</div> 
                                </c:if>
                            </c:forEach>

                        </td>
                        <td>
                            <button id="openModalBtn" onclick="openModalOrder('${o.orderId}', '${o.saler}', '${o.status}')">Details</button>
                            <button style="background: linear-gradient(rgba(255, 0, 0, 0.5), rgba(255, 0, 0, 0.5)), #000;" onclick="CancelOrder(${o.orderId}, ${o.status})">Cancel</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <ul class="pagination">
            <%               
               for (int i = 1; i <= totalPages; i++) {
                   String cssClass = (i == pageNumber) ? "active" : "";
            %>
            <li onclick="getOrderPage(<%= i %>)"class="<%= cssClass %>"><a style="cursor: pointer;"><%= i %></a></li>
                <% } %>
        </ul>

        <div id="productDetailModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2 id="title-name" style="text-align: center;"></h2>
                <div style="width: 500px; border-bottom: 1px solid black; position: absolute; left: 230px"></div>
                <div class="order-infor">
                    <div>
                        <p id="orderId"></p>
                    </div>
                    <div>
                        <table class="table table-bordered" id="cartTable">
                            <thead>
                                <tr>
                                    <th>Picture</th>
                                    <th>Products</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Saler</th>
                                    <th>Status</th>
                                    <th>Amount</th>
                                </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="footer.jsp"%>
    </body>
    <script>
        let url = currentUrl = window.location.href;
        let listUrlParam = url.split('/');
        let listParam = listUrlParam[listUrlParam.length - 1].toString().split('?');
        listParam.forEach(function (param) {
            if (param.toString().includes('&')) {
                let urlSe = param.toString().split('&');
                urlSe.forEach(function (u) {
                    if (u.toString().includes('admin')) {
                        let cate = u.toString().split('=')[1];
                        document.getElementById('filter-admin').value = cate;
                    }
                    if (u.toString().includes('status')) {
                        let status = u.toString().split('=')[1];
                        document.getElementById('filter-status').value = status;
                    }
                });
            } else {
                if (param.toString().includes('admin')) {
                    let cate = param.toString().split('=')[1];
                    document.getElementById('filter-admin').value = cate;
                }
                if (param.toString().includes('status')) {
                    let status = param.toString().split('=')[1];
                    document.getElementById('filter-status').value = status;
                }
            }
        });

        document.getElementById('productDetailModal').addEventListener('click', function (event) {
            if (event.target === this) {
                document.getElementById('productDetailModal').style.display = 'none';
                var table = document.getElementById("cartTable");
                while (table.rows.length > 1) {
                    table.deleteRow(1);
                }
            }
        });

        document.getElementsByClassName('close')[0].addEventListener('click', function () {
            document.getElementById('productDetailModal').style.display = 'none';
            var table = document.getElementById("cartTable");
            while (table.rows.length > 1) {
                table.deleteRow(1);
            }
        });

        function openModalOrder(id, saler, status) {
            fetch('order-list?orderId=' + id, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.json();
                    })
                    .then(data => {
                        var map = JSON.parse(data.map);
                        var list = JSON.parse(data.list);
                        var mapS = JSON.parse(data.mapS);
                        list.forEach(function (od) {
                            var oid = od.orderDetailId.toString();
                            var p = map[oid];
                            var tr = document.createElement("tr");
                            // Tạo và thêm các ô dữ liệu vào hàng
                            var td1 = document.createElement("td");
                            var img = document.createElement("img");
                            img.src = p.image;
                            img.alt = "Product Image";
                            img.className = "img-thumbnail";
                            td1.appendChild(img);
                            tr.appendChild(td1);
                            var td2 = document.createElement("td");
                            td2.textContent = p.productName;
                            tr.appendChild(td2);
                            var td3 = document.createElement("td");
                            td3.textContent = p.productPrice;
                            tr.appendChild(td3);
                            var td4 = document.createElement("td");
                            td4.textContent = od.quantity;
                            tr.appendChild(td4);
                            var td5 = document.createElement("td");
                            td5.textContent = saler;
                            tr.appendChild(td5);
                            var td6 = document.createElement("td");
                            td6.textContent = mapS[status];
                            tr.appendChild(td6);

                            var td7 = document.createElement("td");
                            td7.textContent = od.quantity * p.productPrice;
                            tr.appendChild(td7);
                            // Thêm hàng vào bảng
                            document.getElementById("cartTable").appendChild(tr);

                        });
                    })
                    .catch(error => {
                        console.error('?ã có l?i x?y ra:', error);
                    });
            console.log(id);
            document.getElementById('productDetailModal').style.display = 'block';
        }

        function filterStatus() {
            let status = document.getElementById('filter-status').value;
            window.location.href = './order-list?status=' + status;
        }

        function filterAdmin() {
            let admin = document.getElementById('filter-admin').value;
            window.location.href = './order-list?admin=' + admin;
        }

        function getOrderPage(page) {
            let url = window.location.href;
            if (url.includes('page')) {
                url = url.replace(/page=\d+/g, 'page=' + page);
            } else {
                url += (url.includes('?') ? '&' : '?') + 'page=' + page;
            }
            window.location.href = url;
        }

        function CancelOrder(orderId, status) {
            let url = window.location.href;
            fetch('order-list?orderId=' + orderId + '&status=' + status, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        window.location.href = url;
                    })
                    .catch(error => {
                        console.error('?ã có l?i x?y ra:', error);
                    });
        }
        
    </script>
</html>
