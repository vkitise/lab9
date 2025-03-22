package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/student-marks")
public class StudentMarksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ✅ Allow GET requests by calling doPost()
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connect = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String USN = request.getParameter("usn"); // Get USN from URL or form
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // ✅ Replace with your actual MySQL credentials
            String url = "jdbc:mysql://localhost:3306/employee";
            String user = "root";
            String password = "Sheethal@2005";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, password);

            // ✅ Query Execution
            String query = "SELECT * FROM StudentMarks WHERE USN = ?";
            stmt = connect.prepareStatement(query);
            stmt.setString(1, USN);
            rs = stmt.executeQuery();

            // ✅ HTML Response
            out.println("<html><body>");
            if (rs.next()) {
                out.println("<h1>Memorandum of Marks</h1>");
                out.println("<p>USN: " + rs.getString("USN") + "</p>");
                out.println("<p>Name: " + rs.getString("Name") + "</p>");
                out.println("<p>Total Marks: " + rs.getInt("TotalMarks") + "</p>");
            } else {
                out.println("<p>No student found with USN: " + USN + "</p>");
            }
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error occurred: " + e.getMessage() + "</p>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connect != null) connect.close();
            } catch (SQLException ignored) {}
        }
    }
}
