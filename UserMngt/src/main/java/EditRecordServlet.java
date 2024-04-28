import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/edit")
public class EditRecordServlet extends HttpServlet {

	
private final static String query = "update user set fname=?,lname=?,dob=?,gender=?,email=?,phn=?,subjects=? where id=?";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//printWriter
		PrintWriter pw=res.getWriter();
		
		//set Content type
		res.setContentType("text/html");
		
		//link the bootstrap
		pw.println("<link rel='stylesheet' href=\"css/bootstrap.css\"></link>");
		
		//get the values
		int id=Integer.parseInt(req.getParameter("id"));
		String fname=req.getParameter("fname");
		String lname=req.getParameter("lname");
		String dob=req.getParameter("dob");
		String gender=req.getParameter("gender");
		String email=req.getParameter("email");
		String phn=req.getParameter("phn");
		String subjects=req.getParameter("subjects");
	
		
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
			
			//set the values
			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setString(3, dob);
			ps.setString(4, gender);
			ps.setString(5, email);
			ps.setString(6, phn);
			ps.setString(7, subjects);
			ps.setInt(8, id);
			
			//execute the query
			int count=ps.executeUpdate();
			
			pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
			
			if(count==1)
			{
				pw.println("<h2 class='bg-danger text-light text-center'>Record edited successfully</h2>");
			}
			else
			{
				pw.println("<h2 class='bg-danger text-light text-center'>Record not edited successfully</h2>");
			}
			
		} catch (SQLException se) {
			
			pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
			se.printStackTrace();
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		pw.println("<a href='form.html'><button class= 'btn btn-outline-success'>Home</button></a>");
		pw.println("&nbsp; &nbsp;");
		pw.println("<a href='showdata'><button class= 'btn btn-outline-success'>Show User</button></a>");
		pw.println("</div>");
		//close the stream
		pw.close();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
