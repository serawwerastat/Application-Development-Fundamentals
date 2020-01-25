<%@ page import="com.api_dev_fundamentals.APIDevFundamentals.models.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new user</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Application Development Fundamentals</h1>
</div>

<div class="w3-container w3-padding">
    <%
        String defaultPage = "<div class=\"w3-container w3-center w3-green\">\n"
                + "            <h2>User info</h2>\n"
                + "        </div>" +"<form method=\"get\" class=\"w3-selection w3-light-grey w3-padding\">\n"
                +
                "                <input type=\"text\" name=\"userName\" class=\"w3-input w3-animate-input w3-border w3-round-large\" style=\"width: 30%\"\n"
                + "                       value='username'><br />\n"
                + "            </label>\n"
                + "            <button type=\"submit\" class=\"w3-btn w3-green w3-round-large w3-margin-bottom\">Submit</button>\n"
                + "        </form>"
                + "</div>";
        if(request.getAttribute("status") != null){
            out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                    "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                    "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                    "   <h5>User" + request.getAttribute("status") + " was updated </h5>\n" +
                    "</div>");
        }
        else if (request.getAttribute("error") != null) {
            out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                    "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                    "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                    "   <h5>ERROR! '" + request.getAttribute("error") + "' </h5>\n" +
                    "</div>" + defaultPage);
        }
        else if (request.getAttribute("userName") != null) {
            out.println("<div class=\"w3-container w3-center w3-green\">\n"
                    + "            <h2>User info</h2>\n"
                    + "        </div>" +"<form method=\"post\" class=\"w3-selection w3-light-grey w3-padding\">\n"
                    + "            <label>ID:\n"
                    +
                    "                <input type=\"hidden\" name=\"userId\" class=\"w3-input w3-animate-input w3-border w3-round-large\" style=\"width: 30%\" value='"+request.getAttribute("userId")+"'>"
                    + request.getAttribute("userId")+"</label>\n"
                    +"<h3></h3>"
                    + "            </label>\n"
                    + "            <label>Name:\n"
                    + "                <input type=\"text\" name=\"userName\" class=\"w3-input w3-animate-input w3-border w3-round-large\" style=\"width: 30%\"\n"
                    + "                       value='"+request.getAttribute("userName")+"'><br />\n"
                    + "            </label>\n"
                    + "            <label>Password:\n"
                    + "                <input type=\"password\" name=\"userPassword\" class=\"w3-input w3-animate-input w3-border w3-round-large\" style=\"width: 30%\"\n"
                    + "                       value='"+ request.getAttribute("userPassword") +"'><br />\n"
                    + "            </label>\n"
                    + "            <button type=\"submit\" class=\"w3-btn w3-green w3-round-large w3-margin-bottom\">Submit</button>\n"
                    + "        </form>"
                    + "</div>");
        }
        else {
            out.println(defaultPage);
        }
    %>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">Back to main</button>
</div>
</body>
</html>