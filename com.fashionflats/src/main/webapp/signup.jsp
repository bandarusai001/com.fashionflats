<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Fashion Flats - Sign Up</title>
  <link rel="stylesheet" href="signup.css">
</head>
<body>
  <div class="form-box">
    <h1>Create Account</h1>
    <form method="POST" action="Signup">
      <label for="name">Your Name</label>
      <input type="text" id="name" name="name" required>

      <label for="email">Email</label>
      <input type="email" id="email" name="email" required>

      <label for="pword">Password</label>
      <input type="password" id="pword" name="pword" minlength="6" required>

      <label for="confirm_password">Confirm Password</label>
      <input type="password" id="confirm_password" name="confirm_password" required>

      <input type="submit" value="Create Account">

      <p style="color:red;">
        <%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
      </p>

      <p>Already have an account? <a href="signin.jsp">Sign in</a></p>
    </form>
  </div>
</body>
</html>