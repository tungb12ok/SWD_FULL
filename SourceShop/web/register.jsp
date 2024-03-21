<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Register</title>
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

        <div class="container">
            <div class="row"
                 style="margin-top: 5px; margin-left: 2px; margin-right: 2px;">
                <jsp:include page="/common/Message.jsp" />

                <form action="signUp" method="post" class="col-md-6 col-md-offset-3"
                      style="border: 2px solid black; border-radius: 10px; background-color: #FFE5CC; padding: 10px;"
                      name="registrationForm" onsubmit="return validateForm()">
                    <div style="font-weight: bold;" class="text-center">
                        <h2 style="color: green;">Registration Form</h2>

                        <p style="color: blue;">
                        </p>

                    </div>
                    <div></div>
                    <div class="row">
                        <div class="col-md-6 form-group">
                            <label for="first_name">Name</label> <input type="text"
                                                                            name="username" class="form-control" id="first_name"
                                                                            name="first_name" required>
                        </div>
                        <div class="col-md-6 form-group">
                            <label for="last_name">Email</label> <input type="email"
                                                                        name="email" class="form-control" id="last_name" name="last_name"
                                                                        required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="last_name">Address</label>
                        <textarea name="address" class="form-control" id="last_name"
                                  name="last_name" required></textarea>
                    </div>
                    <div class="row">
                        <div class="col-md-6 form-group">
                            <label for="last_name">Mobile</label> <input type="number"
                                                                         name="mobile" class="form-control" id="last_name"
                                                                         name="last_name" required>
                        </div>
                        <div class="col-md-6 form-group">
                            <label for="last_name">Pin Code</label> <input type="number"
                                                                           name="pincode" class="form-control" id="last_name"
                                                                           name="last_name" required>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col-md-6 form-group">
                            <label for="last_name">Password</label> <input type="password"
                                                                           name="password" class="form-control" id="last_name"
                                                                           name="last_name" required>
                        </div>
                        <div class="col-md-6 form-group">
                            <label for="last_name">Confirm Password</label> <input
                                type="password" name="confirmPassword" class="form-control"
                                id="last_name" name="last_name" required>
                        </div>
                    </div>
                    <div class="row text-center">
                        <div class="col-md-6" style="margin-bottom: 2px;">
                            <button type="Reset" class="btn btn-danger">Reset</button>
                        </div>
                        <div class="col-md-6">
                            <button type="submit" class="btn btn-success">Register</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>


        <%@ include file="footer.jsp"%>
        <script>
            function validateForm() {
                var username = document.forms["registrationForm"]["username"].value;
                var email = document.forms["registrationForm"]["email"].value;
                var address = document.forms["registrationForm"]["address"].value;
                var mobile = document.forms["registrationForm"]["mobile"].value;
                var pincode = document.forms["registrationForm"]["pincode"].value;
                var password = document.forms["registrationForm"]["password"].value;
                var confirmPassword = document.forms["registrationForm"]["confirmPassword"].value;

                // Basic validation, you can add more checks as needed
                if (username === "" || email === "" || address === "" || mobile === "" || pincode === "" || password === "" || confirmPassword === "") {
                    alert("All fields must be filled out");
                    return false;
                }

                if (password !== confirmPassword) {
                    alert("Passwords do not match");
                    return false;
                }

                return true;
            }
        </script>
    </body>
</html>