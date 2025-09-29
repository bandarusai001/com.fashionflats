<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
  <title>Welcome to Fashion Flats</title>
  <link rel="stylesheet" href="signin.css">
</head>
<body>
  <div class="form-box">
    <h1>Welcome, <%= session.getAttribute("username") %>!</h1>
    <p>You have successfully signed in.</p>
    <form method="POST" action="Logout">
      <input type="submit" value="Logout">
    </form>
  </div>
  <form method="POST" action="Logout">
  <input type="submit" value="Logout">
</form>
</body>
</html>