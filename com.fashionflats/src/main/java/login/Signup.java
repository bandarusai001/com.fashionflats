package login;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/Signup")
public class Signup extends HttpServlet {
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

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("pword");
        String confirmPassword = request.getParameter("confirm_password");

        // Basic validation
        if (name == null || name.isEmpty() ||
            email == null || email.isEmpty() ||
            password == null || password.length() < 6 ||
            confirmPassword == null || !password.equals(confirmPassword)) {

            request.setAttribute("message", "Please fill all fields correctly and ensure passwords match.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        // Hash the password
        String hashedPassword = hashPassword(password);

        try {
            // Check if email already exists
            try (PreparedStatement checkStmt = con.prepareStatement(
                    "SELECT COUNT(*) FROM customer WHERE email = ?")) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    request.setAttribute("message", "Email already registered!");
                    request.getRequestDispatcher("signup.jsp").forward(request, response);
                    return;
                }
            }

            // Insert new user
            try (PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO customer (name, email, password) VALUES (?, ?, ?)")) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, hashedPassword);
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                    request.setAttribute("message", "You have registered successfully!");
                    request.getRequestDispatcher("success.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "Registration failed. Please try again.");
                    request.getRequestDispatcher("signup.jsp").forward(request, response);
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    // SHA-256 hashing
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