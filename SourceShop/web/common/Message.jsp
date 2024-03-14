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
                z-index: 9999;
                border-radius: 5px;
                box-shadow: 0 2px 4px rgba(0,0,0,.2);
                transition: opacity 0.5s ease-in-out, top 0.5s ease-in-out;
                opacity: 0;
                background-color: #28a745; /* Green for success */
                color: #fff;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .notification.error {
                background-color: #dc3545; /* Red for error */
            }

            .progress-bar {
                height: 5px;
                background-color: #fff;
                width: 100%;
                transition: width 5s linear;
            }
        </style>
    </head>
    <body>

        <c:if test="${messSuccess != null}">
            <div id="successNotification" class="notification">
                <h3>${messSuccess}</h3>
                <div class="progress-bar" id="successProgressBar"></div>
            </div>
        </c:if>

        <c:if test="${messError != null}">
            <div id="errorNotification" class="notification error">
                <h3>${messError}</h3>
                <div class="progress-bar" id="errorProgressBar"></div>
            </div>
        </c:if>

        <script>
            // Function to show and hide notifications
            function showNotification(notificationId, progressBarId) {
                var notification = document.getElementById(notificationId);
                var progressBar = document.getElementById(progressBarId);
                notification.style.opacity = "1";
                notification.style.top = "30px";
                progressBar.style.width = "0%"; // Start the progress bar
                setTimeout(function () {
                    notification.style.opacity = "0";
                    notification.style.top = "10px";
                }, 5000); // 5 seconds
            }

            // Show notifications on page load
            <c:if test="${not empty messSuccess}">
            showNotification('successNotification', 'successProgressBar');
                <c:set var="messSuccess" value="${null}" scope="session" />
            </c:if>

            <c:if test="${not empty messError}">
            showNotification('errorNotification', 'errorProgressBar');
                <c:set var="messError" value="${null}" scope="session" />
            </c:if>
        </script>

    </body>
</html>
