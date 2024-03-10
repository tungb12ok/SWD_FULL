<%-- 
    Document   : OrderList
    Created on : Mar 10, 2024, 7:27:49 PM
    Author     : win
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.shashi.service.impl.*, com.shashi.service.*,com.shashi.beans.*,java.util.*,java.io.*"%>
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
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
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
                            <div class="action-status active" id="table-status-${o.orderId}" onclick="changeStatus(${o.orderId})">${o.status}</div>   
                        </td>
                        <td>
                            <button id="openModalBtn" onclick="openModalOrder('${o.orderId}')">Details</button>
                            <button style="background: linear-gradient(rgba(255, 0, 0, 0.5), rgba(255, 0, 0, 0.5)), #000;" onclick="">Cancel</button>
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
            <li onclick="getProductPage(<%= i %>)"class="<%= cssClass %>"><a style="cursor: pointer;"><%= i %></a></li>
                <% } %>
        </ul>

        <div id="productDetailModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2 id="title-name" style="text-align: center;"></h2>
                <div style="width: 500px; border-bottom: 1px solid black; position: absolute; left: 230px"></div>
                <div class="product-infor">


                </div>
            </div>
        </div>
        <%@ include file="footer.html"%>
    </body>
    <script>
        let url = currentUrl = window.location.href;
        let listUrlParam = url.split('/');
        let listParam = listUrlParam[listUrlParam.length - 1].toString().split('?');
        listParam.forEach(function (param) {
            if (param.toString().includes('cateId')) {
                let cate = param.toString().split('=')[1];
                document.getElementById('filter-cate').value = cate;
            }
            if (param.toString().includes('status')) {
                let status = param.toString().split('=')[1];
                document.getElementById('filter-status').value = status;
            }
        });

        document.getElementById('productDetailModal').addEventListener('click', function (event) {
            if (event.target === this) {
                document.getElementById('productDetailModal').style.display = 'none';              
            }
        });

        document.getElementsByClassName('close')[0].addEventListener('click', function () {
            document.getElementById('productDetailModal').style.display = 'none';            
        });

        function openModalOrder(id){
            console.log(id);
             document.getElementById('productDetailModal').style.display = 'block';
        }
    </script>
</html>
