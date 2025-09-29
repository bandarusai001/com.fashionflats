<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Fashion Flats - Sign In</title>
  <link rel="stylesheet" href="signin.css">
</head>
<body>
  <div class="form-box">
    <h1>Sign In</h1>
    <form method="POST" action="Signin">
      <label for="email">Email</label>
      <input type="email" id="email" name="email" required>

      <label for="password">Password</label>
      <input type="password" id="password" name="password" required>

      <input type="submit" value="Sign In">

      <p style="color:red;">
        <%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
      </p>

      <p>New to Fashion Flats? <a href="signup.jsp">Create an account</a></p>
    </form>
  </div>
</body>
</html>