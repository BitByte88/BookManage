package main.java.jp.co.bookmanage.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import main.java.jp.co.bookmanage.common.ForwardService;
import main.java.jp.co.bookmanage.common.Action;
import main.java.jp.co.bookmanage.service.ErrorService;

public class ErrorController extends HttpServlet {
	private static final long serialVersionUID = 1553268921934183699L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ForwardService forward = null;
		Action action = new ErrorService();
		try {
			forward = action.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request
						.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}
}