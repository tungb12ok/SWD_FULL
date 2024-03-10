<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <style>
            .notification {
                margin-top: 100px;
                position: fixed;
                top: 10px;
                right: 10px;
                padding: 10px;
                border-radius: 5px;
                transition: opacity 0.5s ease-in-out;
                opacity: 0;
                background-color: #28a745; /* Green for success */
                color: #fff;
            }

            .notification.error {
                background-color: #dc3545; /* Red for error */
            }
        </style>
    </head>
    <body>

        <c:if test="${messSuccess != null}">
            <div id="successNotification" class="notification">
                <h3>${messSuccess}</h3>
            </div>
        </c:if>

        <c:if test="${messError != null}">
            <div id="errorNotification" class="notification error">
                <h3>${messError}</h3>
            </div>
        </c:if>

        <script>
            // Function to show and hide notifications
            function showNotification(notificationId) {
                var notification = document.getElementById(notificationId);
                notification.style.opacity = "1";
                setTimeout(function () {
                    notification.style.opacity = "0";
                }, 5000); // 5 seconds
            }

            // Show notifications on page load
            <c:if test="${messSuccess != null}">
            showNotification('successNotification');
            </c:if>

            <c:if test="${messError != null}">
            showNotification('errorNotification');
            </c:if>
        </script>

    </body>
</html>
