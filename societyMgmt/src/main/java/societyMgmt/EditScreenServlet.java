package societyMgmt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditScreenServlet extends HttpServlet {
    private final static String query = "select name,email,mobile,dob,designation,gender,members from user where id=?";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set content type
        res.setContentType("text/html");

        //get the id
        //get the values
        int id = Integer.parseInt(req.getParameter("id"));
        //link the bootstrap
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        //load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(Exception e) {
            e.printStackTrace();
        }
        //generate the connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql:///societymgmt","root","gautam@123");
                PreparedStatement ps = con.prepareStatement(query);){
            //set value 
            ps.setInt(1, id);
            //resultSet
            ResultSet rs = ps.executeQuery();
            rs.next();
            pw.println("<div style='margin:auto;width:500px;margin-top:100px;'>");
            pw.println("<form action='edit?id="+id+"' method='post'>");
            pw.println("<table class='table table-hover table-striped'>");
            pw.println("<tr>");
            pw.println("<td>Name</td>");
            pw.println("<td><input type='text' name='name' value='"+rs.getString(1)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Email</td>");
            pw.println("<td><input type='email' name='email' value='"+rs.getString(2)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Mobile</td>");
            pw.println("<td><input type='text' name='mobile' value='"+rs.getString(3)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>DOB</td>");
            pw.println("<td><input type='date' name='dob' value='"+rs.getString(4)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Designation</td>");
            pw.println("<td><select name=\"designation\">\r\n"
            		+ "			<option value=\"Resident only\">Resident only</option>\r\n"
            		+ "			<option value=\"Committee Member\">Committee Member</option>\r\n"
            		+ "			<option value=\"Resident (tenants)\">Resident (tenants)</option>	\r\n"
            		+ "			<option value=\"Staff Member\">Staff Member</option>\r\n"
            		+ "		</select></td>");
            pw.println("</tr>");
            
            pw.println("<tr>");
            pw.println("<td>Gender</td>");
            pw.println("<td><input type=\"radio\" name=\"gender\" value=\"male\">Male &nbsp;\r\n"
            		+ "            <input type=\"radio\" name=\"gender\" value=\"female\">Female</td>");
            pw.println("</tr>");
            
            pw.println("<tr>");
            pw.println("<td>Number of Members</td>");
            pw.println("<td><select name=\"members\">\r\n"
            		+ "			<option value=\"1\">1</option>\r\n"
            		+ "			<option value=\"2\">2</option>\r\n"
            		+ "			<option value=\"3\">3</option>	\r\n"
            		+ "			<option value=\"4\">4</option>\r\n"
            		+ "			<option value=\"More than 4\">More than 4</option>\r\n"
            		+ "		</select></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td><button type='submit' class='btn btn-outline-success'>Edit</button></td>");
            pw.println("<td><button type='reset' class='btn btn-outline-danger'>Cancel</button></td>");
            pw.println("</tr>");
            pw.println("</table>");
            pw.println("</form>");
        }catch(SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
            se.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        pw.println("<a href='home.html'><button class='btn btn-outline-success'>Home</button></a>");
        pw.println("</div>");
        //close the stream
        pw.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}
