package controller;

import java.io.IOException;
import java.rmi.ServerException;

import javax.persistence.AssociationOverride;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.mydao;
import dto.Customer;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet("/Login")
public class Loginpage extends HttpServlet {
	protected void doPos(HttpServletRequest req, HttpServletResponse resp)throws ServerException {
			String email=req.getParameter("emph");
			String password = req.getParameter("password");
			System.out.println(password);
			System.out.println(email);
	
			// Verify if email exists
			mydao dao= new MyDao();
			if (email.equals("admin@jsp.com") && password .equals("admin")) {
				resp.getWriter().print("<h1 style='color:green'>Login Success</h1>");
				//This is Logic to send to next page
				req.getRequestDispatcher("AdminHome.html").include(req, resp);
			}
			else {
				Customer customer=dao.fetchByEmail(email);
				if(customer==null) {
					resp.getWriter().print("<h1 style='color:red'>Invalid Email</h1>");
					req.getRequestDispatcher("Login.html").include(req, resp);
				}
				else {
					if (password.equals(customer.getPassword())) {
						resp.getWriter().print("<h1 style='color:green'>Login Success</h1>");
						//This is Logic to send to next page
						req.getRequestDispatcher("home.html").include(req, resp);
					}
					else {
						resp.setContentType("text/html");
						resp.getWriter().print("<h1 style='color:darkgreen'>Invalid Password </h1>");
						req.getRequestDispatcher("login.html").include(req, resp);
					}
				}
			}
			
			
			
			
	}
	
}
