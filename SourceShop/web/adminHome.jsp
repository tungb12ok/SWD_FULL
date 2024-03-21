
<%@page import="model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
            <link rel="stylesheet" href="css/styleAdminProduct.css">
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
                    <h2>Product Management</h2>
                    <div style="display: flex;padding-bottom: 40px;">
                        <div class="btn-create" onclick="openModalProduct(-1)">Create New Product</div>
                        <div style="display: flex; padding-left: 50px;">
                            <label style="padding: 10px 20px 0 0;">Status:</label>
                            <select class="form-control" id="filter-status" style="width: 200px; height: 50px;text-align: center;" onchange="filterStatus()">
                                <c:forEach var="s" items="${status}">
                                    <option value="${s.id}">${s.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div style="display: flex; padding-left: 50px;">
                            <label  style="padding: 10px 20px 0 0;">Category:</label>
                            <select class="form-control" id="filter-cate" style="width: 200px; height: 50px;text-align: center;" onchange="filterCate()">
                                <c:forEach var="cate" items="${cates}">
                                    <option value="${cate.id}">${cate.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <table class="table">
                        <thead>
                            <tr>
                                <th style="width: 20%;">Product Images</th>
                                <th style="width: 15%;">Product Name</th>
                                <th style="width: 25%;">Product Information</th>
                                <th style="width: 10%;">Product Price</th>
                                <th style="width: 10%;">Sale</th>
                                <th style="width: 10%;">Status</th>
                                <th style="width: 10%;"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${products}">
                                <tr>
                                    <td>
                                        <img src="${product.image}" width="150px" height="100px" alt="alt"/>
                                    </td>
                                    <td>${product.productName}</td>
                                    <td>${product.productInfo}</td>
                                    <td>
                                        <fmt:formatNumber value="${product.productPrice}" pattern="#,##0 VND"/>
                                    </td>

                                    <td>${product.sale}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${product.status == status.get(0).getId()}">
                                                <div class="action-status active" id="table-status-${product.productId}" onclick="changeStatus(${product.productId}, '${product.status}')">${status.get(0).getName()}</div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="action-status" id="table-status-${product.productId}" onclick="changeStatus(${product.productId}, '${product.status}')">${status.get(1).getName()}</div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <button id="openModalBtn" onclick="openModalProduct(${product.productId})">Details</button>
                                    </td>
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

                <div id="productDetailModal" class="modal">
                    <div class="modal-content">
                        <span class="close">&times;</span>
                        <h2 id="title-name" style="text-align: center;"></h2>
                        <div style="width: 500px; border-bottom: 1px solid black; position: absolute; left: 230px"></div>
                        <div class="product-infor">
                            <div class="product-image" style="align-content: center;display: flex;">
                                <img id="pImage" src="" width="150px" height="100px" alt="alt" style="margin: 10px 0 10px 200px;"/>
                                <div style="padding-left: 50px;padding-top: 30px;">
                                    <label for="input-file" id="custom-button" style="cursor: pointer; display: inline-block; padding: 10px 20px; border: 1px solid #ccc; background-color: #f0f0f0; border-radius: 5px;">Choose Image</label>
                                </div>
                                <div style="padding-left: 50px;padding-top: 10px;">
                                    <img id="file-name" src="" width="150px" height="100px" style="display:none; margin-left: 10px;">
                                </div>

                                <input type="file" id="input-file" style="display: none;">                             
                            </div>
                            <div style="padding: 0 70px;">
                                <div style="padding-bottom: 20px;">
                                    <label class="form-check-label" style="padding-bottom: 8px;">Product Name</label></br>
                                    <input class="form-control" type="text" id="pName" style=""> 
                                </div>
                                <div style="padding-bottom: 20px;">
                                    <label class="form-check-label" style="padding-bottom: 8px;">Product Information</label></br>
                                    <textarea class="form-control" type="text" id="pInfor" rows="4" style=""> </textarea>
                                </div>
                                <div style="padding-bottom: 20px; display: flex;">
                                    <div style="width: 250px;">
                                        <label class="form-check-label" style="padding-bottom: 8px;">Product Quantity</label></br>
                                        <input class="form-control" type="text" id="pQuantity" style="">
                                    </div>
                                    <div style="padding-left: 20px;width: 250px;">
                                        <label class="form-check-label" style="padding-bottom: 8px;">Product Price</label></br>
                                        <input class="form-control" type="text" id="pPrice" style="" oninput="formatMoney(this)">
                                    </div>
                                    <div style="padding-left: 20px;width: 250px;">
                                        <label class="form-check-label" style="padding-bottom: 8px;">Product Category</label></br>
                                        <select class="form-control" id="cateid"></select>
                                    </div>
                                </div>
                                <div style="padding-bottom: 20px; display: flex;">
                                    <div style="width: 250px;">
                                        <label class="form-check-label" style="padding-bottom: 8px;">Product Sale</label></br>
                                        <input class="form-control" type="text" id="pSale" style="">
                                    </div>
                                    <div style="padding-left: 20px;width: 250px;">
                                        <label class="form-check-label" style="padding-bottom: 8px;">Product Status</label></br>
                                        <button id="pStatus" style="width: 100px !important;" class="btn-status">InActive</button>                                        
                                    </div>

                                    <div style="padding-left: 20px;width: 250px;">
                                        <label class="form-check-label" style="padding-bottom: 8px;">New Category</label></br>
                                        <input class="form-control" type="text" id="newCate" style="">                                        
                                    </div>
                                </div>
                                <div style="padding-bottom: 20px;">
                                    <button style="margin: 0 250px;" id="updateP">Save Information</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%@ include file="footer.jsp"%>
            <script>
                function getProductPage(page) {
                    let url = window.location.href;
                    if (url.includes('page')) {
                        url = url.replace(/page=\d+/g, 'page=' + page);
                    } else {
                        url += (url.includes('?') ? '&' : '?') + 'page=' + page;
                    }
                    window.location.href = url;
                }

                function openModalProduct(pid) {
                    fetch('ProductController?productId=' + pid, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                    })
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error('Network response was not ok');
                                }
                                return response.json();
                            })
                            .then(data => {
                                var productData = JSON.parse(data.product);
                                var categoryData = JSON.parse(data.categories);
                                var mapS = JSON.parse(data.mapS);
                                if (pid !== -1) {
                                    displayProductDetails(productData, categoryData, mapS);
                                } else {
                                    var select = document.getElementById('cateid');
                                    categoryData.forEach(function (item) {
                                        var option = document.createElement('option');
                                        option.value = item.id;
                                        option.textContent = item.name;
                                        select.appendChild(option);
                                    });
                                    document.getElementById('pImage').style.display = "none";
                                    document.getElementById('updateP').addEventListener('click', function () {
                                        updateProduct(0, mapS);
                                    });
                                    document.getElementById('title-name').textContent = 'Create New Product';
                                }
                                document.getElementById('productDetailModal').style.display = 'block';
                            })
                            .catch(error => {
                                console.error('?ã có l?i x?y ra:', error);
                            });
                }

                function changeStatus(pid, status) {
                    fetch('ProductController?pid=' + pid + '&status=' + status, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                    })
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error('Network response was not ok');
                                }
                                var tablbeStatus = document.getElementById("table-status-" + pid);


                                var isActive = tablbeStatus.classList.contains("active");
                                if (isActive) {
                                    tablbeStatus.textContent = "InActive";
                                } else {
                                    tablbeStatus.textContent = "Active";
                                }

                                tablbeStatus.classList.toggle("active");

                            })
                            .catch(error => {
                                console.error('?ã có l?i x?y ra:', error);
                            });
                }

                function displayProductDetails(data, categoryData,mapS) {
                    document.getElementById('pImage').src = data.image;
                    document.getElementById('pName').value = data.productName;
                    document.getElementById('pInfor').textContent = data.productInfo;
                    document.getElementById('pSale').value = data.sale;
                    document.getElementById('pPrice').value = formatNumber(data.productPrice);
                    document.getElementById('pQuantity').value = data.productQuantity;
                    if (data.status === mapS["Active"]) {
                        var toggleButton = document.getElementById("pStatus");
                        toggleButton.textContent = "Active";
                        toggleButton.classList.toggle("active");
                    }
                    var select = document.getElementById('cateid');
                    categoryData.forEach(function (item) {
                        var option = document.createElement('option');
                        option.value = item.id;
                        option.textContent = item.name;
                        select.appendChild(option);
                    });
                    document.getElementById('cateid').value = data.cateId;
                    document.getElementById('updateP').addEventListener('click', function () {
                        updateProduct(data.productId, mapS);
                    });
                    document.getElementById('title-name').textContent = 'Product Detail: ' + data.productName;
                }

                function formatMoney(input) {
                    let value = input.value;
                    value = value.replace(/[^\d.]/g, '');
                    let parts = value.split('.');
                    let integerPart = parts[0];
                    integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
                    input.value = integerPart;
                }

                function formatNumber(value) {
                    let money = Math.round(value);
                    value = money.toString().replace(/[^\d.]/g, '');
                    let parts = value.split('.');
                    let integerPart = parts[0];
                    integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
                    return integerPart;
                }

                function validateDetails() {
                    let productName = document.getElementById('pName').value;
                    let productInfo = document.getElementById('pInfor').value;
                    let productQuantity = document.getElementById('pQuantity').value;
                    let productPrice = document.getElementById('pPrice').value.replace(/,/g, '');
                    let sale = document.getElementById('pSale').value;
                    let oldImg = document.getElementById('pImage').src;
                    let newImg = document.getElementById('file-name').src;
                    if (productName === null || productInfo === null || productQuantity === null ||
                            productPrice === null || sale === null || (oldImg === null && newImg === null)) {
                        return false;
                    }
                    return true;
                }

                function updateProduct(productId, mapS) {
                    if (!validateDetails())
                        return;
                    let newCate = document.getElementById('newCate').value;
                    console.log( document.getElementById('cateid').value);
                    var product = {
                        productId: productId,
                        productName: document.getElementById('pName').value,
                        productInfo: document.getElementById('pInfor').value,
                        productQuantity: document.getElementById('pQuantity').value,
                        productPrice: document.getElementById('pPrice').value.replace(/,/g, ''),
                        sale: document.getElementById('pSale').value,
                        status: mapS[document.getElementById('pStatus').textContent],
                        image: document.getElementById('file-name').src.toString().includes('images') ? document.getElementById('file-name').src : document.getElementById('pImage').src,
                        categoryId: newCate === "" ? document.getElementById('cateid').value + ";old" : newCate + ";new"
                    };
                    console.log(product);
                    var queryParams = new URLSearchParams();
                    queryParams.append('product', JSON.stringify(product));

                    fetch('update-product?' + queryParams, {
                        method: 'GET'
                    })
                            .then(response => {
                                removeOption();
                                if (response.status === 200) {
                                    window.location.href = './ProductController';
                                }
                            })
                            .catch(error => {
                                console.error('Có l?i x?y ra:', error);
                            });
                }

                function removeOption() {
                    var select = document.getElementById('cateid');
                    while (select.firstChild) {
                        select.removeChild(select.firstChild);
                    }
                    document.getElementById('pImage').src = "";
                    document.getElementById('pName').value = "";
                    document.getElementById('pInfor').textContent = "";
                    document.getElementById('pSale').value = "";
                    document.getElementById('pPrice').value = "";
                    document.getElementById('pQuantity').value = "";
                    document.getElementById('file-name').src = "";
                }

                function filterStatus() {
                    let status = document.getElementById('filter-status').value;
                    window.location.href = './ProductController?status=' + status;
                }

                function filterCate() {
                    let cateId = document.getElementById('filter-cate').value;
                    window.location.href = './ProductController?cateId=' + cateId;
                }
            </script>
        </body>
        <script>
            let url = currentUrl = window.location.href;
            let listUrlParam = url.split('/');
            let listParam = listUrlParam[listUrlParam.length - 1].toString().split('?');
            listParam.forEach(function (param) {
                if (param.toString().includes('&')) {
                    let urlSe = param.toString().split('&');
                    urlSe.forEach(function (u) {
                        if (u.toString().includes('cateId')) {
                            let cate = u.toString().split('=')[1];
                            document.getElementById('filter-cate').value = cate;
                        }
                        if (u.toString().includes('status')) {
                            let status = u.toString().split('=')[1];
                            document.getElementById('filter-status').value = status;
                        }
                    });
                } else {
                    if (param.toString().includes('cateId')) {
                        let cate = param.toString().split('=')[1];
                        document.getElementById('filter-cate').value = cate;
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
                    removeOption();
                }
            });

            document.getElementsByClassName('close')[0].addEventListener('click', function () {
                document.getElementById('productDetailModal').style.display = 'none';
                removeOption();
            });

            var toggleButton = document.getElementById("pStatus");

            toggleButton.addEventListener("click", function () {
                var isActive = toggleButton.classList.contains("active");
                if (isActive) {
                    toggleButton.textContent = "InActive";
                } else {
                    toggleButton.textContent = "Active";
                }

                toggleButton.classList.toggle("active");
            });

//            document.getElementById('custom-button').addEventListener('click', function () {
//                document.getElementById('input-file').click();
//            });

            document.getElementById('input-file').addEventListener('change', function () {
                var fileInput = document.getElementById('input-file');
                var formData = new FormData();
                formData.append('file', fileInput.files[0]);

                fetch('update-product', {
                    method: 'POST',
                    body: formData
                })
                        .then(response => {
                            if (response.ok) {
                                return response.text();
                            }
                        })
                        .then(relativeImagePath => {
                            document.getElementById("file-name").src = relativeImagePath;
                            document.getElementById("file-name").style.display = "block";
                        })
                        .catch(error => {
                            console.error('L?i:', error);
                        });
            });

        </script>
    </html>