import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditScreenServlet extends HttpServlet {
	
private final static String query = "select fname,lname,dob,gender,email,phn,subjects from user where id=?";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//printWriter
		PrintWriter pw=res.getWriter();
		
		//set Content type
		res.setContentType("text/html");
		
		//get the id
		int id=Integer.parseInt(req.getParameter("id"));
		
		
		//link the bootstrap
		pw.println("<link rel='stylesheet' href=\"css/bootstrap.css\"></link>");
		
		
		//load jdbc driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//generate the connection
		try (Connection con=DriverManager.getConnection("jdbc:mysql:///usermgmt","root","123456");
				//prepared statement
				PreparedStatement ps=con.prepareStatement(query);){	
			
			//set the value
			ps.setInt(1, id);
			
			//result set
			ResultSet rs=ps.executeQuery();
			rs.next();
			pw.println("<div style='margin:auto;width:500px;margin-top:100px;'>");
			pw.println("<form action='edit?id="+id+"' method='post'>");
			pw.println("<table class='table table-hover table-stripped'>");
			pw.println("<tr>");
			pw.println("<td>Fname</td>");
			pw.println("<td><input type='text' name='fname' value='"+rs.getString(1)+"'</td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Lname</td>");
			pw.println("<td><input type='text' name='lname' value='"+rs.getString(2)+"'</td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>DOB</td>");
			pw.println("<td><input type='text' name='dob' value='"+rs.getString(3)+"'</td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Gender</td>");
			pw.println("<td><input type='text' name='gender' value='"+rs.getString(4)+"'</td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Email</td>");
			pw.println("<td><input type='email' name='email' value='"+rs.getString(5)+"'</td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Phn</td>");
			pw.println("<td><input type='tel' name='phn' value='"+rs.getString(6)+"'</td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Subjects</td>");
			pw.println("<td><input type='text' name='subjects' value='"+rs.getString(7)+"'</td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td><button type='submit' class= 'btn btn-outline-success'>Edit</button></td>");
			pw.println("<td><button type='reset' class= 'btn btn-outline-danger'>Cancel</button></td>");
			pw.println("</tr>");
			pw.println("</table>");
			pw.println("</form>");
			
		} catch (SQLException se) {
			
			pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
			se.printStackTrace();
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		pw.println("<a href='form.html'><button class= 'btn btn-outline-success'>Home</button></a>");
		pw.println("</div>");
		//close the stream
		pw.close();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}


}
