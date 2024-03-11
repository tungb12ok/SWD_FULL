<%-- 
    Document   : orderHistory
    Created on : Mar 11, 2024, 11:04:06 AM
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
    <body>
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
                    <th style="width: 10%;">Status</th>
                    <th style="width: 20%;"></th>
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
                        <td>
                            <c:forEach var="st" items="${status}">
                                <c:if test="${st.id == o.status}">
                                    <div class="action-status active" id="table-status-${o.orderId}" onclick="changeStatus(${o.orderId})">${st.name}</div> 
                                </c:if>
                            </c:forEach>

                        </td>
                        <td>
                            <button id="openModalBtn" onclick="openModalOrder('${o.orderId}', '${o.saler}', '${o.status}')">Details</button>
                            <c:forEach var="st" items="${status}">
                                <c:if test="${st.name == 'New' && st.id == o.status}">
                                    <button style="background: linear-gradient(rgba(255, 0, 0, 0.5), rgba(255, 0, 0, 0.5)), #000;" onclick="cancelOrder('${o.orderId}')">Cancel</button>
                                </c:if>
                            </c:forEach>

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
                    if (u.toString().includes('status')) {
                        let status = u.toString().split('=')[1];
                        document.getElementById('filter-status').value = status;
                    }
                })
            } else {
                if (param.toString().includes('status')) {
                    let status = param.toString().split('=')[1];
                    document.getElementById('filter-status').value = status;
                }
            }
        });

        function filterStatus() {
            let status = document.getElementById('filter-status').value;
            window.location.href = './order-history?status=' + status;
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

        function cancelOrder(orderId) {
            fetch('order-history?orderId=' + orderId, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        window.location.href = './order-history'
                    })
                    .catch(error => {
                        console.error('?ã có l?i x?y ra:', error);
                    });
        }
    </script>
</html>
