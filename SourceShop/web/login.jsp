<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/changes.css">
        <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    </head>
    <body style="background-color: #E6F9E6;">

        <%@ include file="header.jsp"%>

        <%@ include file="common/Message.jsp"%>

        <div class="container">
            <div class="row"
                 style="margin-top: 5px; margin-left: 2px; margin-right: 2px;">
                <form action="signIn" method="post"
                      class="col-md-4 col-md-offset-4 col-sm-8 col-sm-offset-2"
                      style="border: 2px solid black; border-radius: 10px; background-color: #FFE5CC; padding: 10px;">
                    <div style="font-weight: bold;" class="text-center">
                        <h2 style="color: green;">Login Form</h2>
                    </div>
                    <div></div>
                    <div class="row">
                        <div class="col-md-12 form-group">
                            <label for="last_name">Email</label> <input type="email"
                                                                        placeholder="Enter Username" name="username" class="form-control"
                                                                        id="last_name" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 form-group">
                            <label for="last_name">Password</label> <input type="password"
                                                                           placeholder="Enter Password" name="password" class="form-control"
                                                                           id="last_name" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 form-group">
                            <label for="userrole">Login As</label> <select name="usertype"
                                                                           id="userrole" class="form-control" required>
                                <option value="2" selected>CUSTOMER</option>
                                <option value="1">ADMIN</option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <button type="submit" class="btn btn-success">Login</button>
                        </div>
                    </div>
                </form>

            </div>
        </div>

        <%@ include file="footer.html"%>

    </body>
</html>