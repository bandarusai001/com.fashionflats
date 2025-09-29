<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Registration Successful</title>
  <link rel="stylesheet" href="signup.css">
</head>
<body>
  <div class="form-box">
    <h1>Welcome to Fashion Flats!</h1>
    <p><%= request.getAttribute("message") %></p>
    <a href="signin.jsp">Click here to sign in</a>
  </div>
</body>
</html>
