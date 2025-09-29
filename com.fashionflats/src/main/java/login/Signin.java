package login;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/Signin")
public class Signin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection con;

    public void init(ServletConfig config) throws ServletException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:ORCL",
                "c##sai1",
                "bandaru"
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Database connection failed", e);
        }
    }

    public void destroy() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("message", "Please enter both email and password.");
            request.getRequestDispatcher("signin.jsp").forward(request, response);
            return;
        }

        String hashedPassword = hashPassword(password);

        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT name FROM customer WHERE email = ? AND password = ?")) {
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                HttpSession session = request.getSession();
                session.setAttribute("username", name);
                request.setAttribute("message", "Welcome, " + name + "!");
                request.getRequestDispatcher("welcome.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Invalid email or password.");
                request.getRequestDispatcher("signin.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    // SHA-256 password hashing
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }
}