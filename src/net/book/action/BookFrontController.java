package net.book.action;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BookFrontController extends HttpServlet {
	private static final long serialVersionUID = 856523771629891116L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		this.doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String requestURI = request.getRequestURI();
		String contextpath = request.getContextPath();
		String command = requestURI.substring(contextpath.length());
		
		ForwardService forward = null;
		BookFrontService bookFrontService= new BookFrontService();
		//図書詳細画面
		if (command.equals("/Book_Detail.book")) {
			forward = bookFrontService.BookDetailAction(request,response);
		}
		//図書リスト画面
		else if (command.equals("/BookList.book")) {
			forward = bookFrontService.BookListAction(request,response);
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
