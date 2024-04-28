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

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	
private final static String query = "delete from user where id = ?";
	
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
			ps.setInt(1, id);
			
			//execute the query
			int count=ps.executeUpdate();
			
			pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
			
			if(count==1)
			{
				pw.println("<h2 class='bg-danger text-light text-center'>Record deleted successfully</h2>");
			}
			else
			{
				pw.println("<h2 class='bg-danger text-light text-center'>Record not deleted successfully</h2>");
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
