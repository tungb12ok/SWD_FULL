<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Ellison Electronics</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/changes.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body style="background-color: #E6F9E6;">

        <!-- Navigation Bar -->
        <nav class="navbar navbar-default" style="margin-bottom: 0;">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="home"><span class="glyphicon glyphicon-home"></span> Shopping Center</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav">
                        <li><a href="home">Products</a></li>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Category <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="home?type=mobile">Mobiles</a></li>
                                <li><a href="home?type=tv">TVs</a></li>
                                <li><a href="home?type=laptop">Laptops</a></li>
                                <li><a href="home?type=camera">Camera</a></li>
                                <li><a href="home?type=speaker">Speakers</a></li>
                                <li><a href="home?type=tablet">Tablets</a></li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="cart"><i class="fa fa-shopping-cart"></i> Cart <span class="badge">${sessionScope.cart.items.size()}</span></a></li>
                                <c:choose>
                                    <c:when test="${user == null}">
                                <li><a href="signIn">Login</a></li>
                                <li><a href="signUp">Register</a></li>
                                </c:when>
                                <c:otherwise>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.name} <span class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Profile</a></li>
                                        <li><a href="#">Settings</a></li>
                                        <li><a href="signOut">Logout</a></li>
                                    </ul>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
        <!-- Company Header -->
        <div class="container-fluid text-center" style="background-color: #33cc33; color: white; padding: 10px;">
            <h2>Ellison Electronics</h2>
            <h6>We specialize in Electronics</h6>
            <form class="form-inline" action="home" method="get">
                <div class="input-group">
                    <input type="text" class="form-control" size="50" name="search" placeholder="Search Items" required>
                    <div class="input-group-btn">
                        <input type="submit" class="btn btn-danger" value="Search">
                    </div>
                </div>
            </form>
        </div>
        <!-- End of Navigation Bar -->

    </body>
</html>
