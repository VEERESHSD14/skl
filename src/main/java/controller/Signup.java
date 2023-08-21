
package controller;



import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.mydao;
import dto.Customer;

//This is to Map the action URL to this class(Should be Same as action - Case sensitive)
@WebServlet("/signup")
//To receive image we need to use this-enctype
@MultipartConfig
//This is to make Class as Servlet Class
public class Signup extends HttpServlet { 
	
	@Override
	// When there is form and we want data to be secured so doPost
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Logic to Receive Values from Front End
		String FullName = req.getParameter("name");
		String password = req.getParameter("pass");
		long mobile = Long.parseLong(req.getParameter("mob"));
		String email = req.getParameter("mail");
		String gender = req.getParameter("gen");
		String country = req.getParameter("con");
		LocalDate dob = LocalDate.parse(req.getParameter("date"));//
		int age = Period.between(dob, LocalDate.now()).getYears();

		//Logic to Receive image and convert to byte[]
		Part pic = req.getPart("picture");
		byte[] picture=null;
		picture=new byte[pic.getInputStream().available()];
		pic.getInputStream().read(picture);
		
		System.out.println(FullName);
		System.out.println(password );
		System.out.println(mobile);
		System.out.println(email);
		System.out.println(gender);
		System.out.println(country);
		System.out.println(dob);
		
		
		mydao dMydao=null;
		mydao d = null;
		//mydao d= new MyDao();
		if(d.fetchByEmail(email)==null && d.fetchByMobile(mobile)==null)
		{
			Customer customer = new Customer();
			customer.setAge(age);
			customer.setCounrty(country);
			customer.setDob(dob);
			customer.setEmail(email);
			customer.setFullName(FullName);
			customer.setGender(gender);
			customer.setMobile(mobile);
			customer.setPassword(password);
			customer.setPicture(picture);
			
			//Mydao d=new MyDao();
			//Persisting
			d.save(customer);
			
			if(customer==null) {
			
			resp.getWriter().print("<h1 style='color:red'> Account created successfully</h1>");
			req.getRequestDispatcher(" login.html").include(req, resp);
			
		}
		else {
			
			if(password.equals(Customer.getPassword())) {
				
			}
			resp.getWriter().print("<h1 style' color :red'> Account already exist<h1> ");
			//this is logic tosend next page
			req.getRequestDispatcher("CustomerHome.html").include(req, resp);
		}
		else {
			
			resp.getWriter().print("<h1 style' color :red'> Invalid Password<h1> ");
			req.getRequestDispatcher("Login.html").include(req, resp);
		
		}
			
		}
	
	}



