import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class LibraryServlet_1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection conn = DriverManager.getConnection(
//                    "jdbc:sqlite:F:/IDEA_Projects/!Database/mybaseFor3.db"); // для компа на работе
                    "jdbc:sqlite:D:/java/DATABASES/mybaseFor3.db"); // для дома


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT title from books");

            while (rs.next()){
                pw.println(rs.getString("title"));
            }

            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e + "нет базы");
        }
    }

}