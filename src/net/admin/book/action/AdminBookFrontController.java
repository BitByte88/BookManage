package net.admin.book.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminBookFrontController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.processRequest(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		ActionForward forward=null;
		Action action=null;
		if(command.equals("/BookAddForm.adbook")){
			forward = new ActionForward();
			forward.setPath("./admingoods/admin_goods_write.jsp");
		}				
		if(command.equals("/BookAddAction.adbook")){
			action= new AdminBookAddAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}		
		else if(command.equals("/BookList.adbook")){
			action=new AdminBookListAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		else if(command.equals("/BookAdd.adbook")){
			forward=new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./adminbook/admin_book_write.jsp");
		}else if(command.equals("/BookDelete.adbook")){
			action=new AdminBookDeleteAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BookModify.adbook")){
			action=new AdminBookModifyForm();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BookModifyAction.adbook")){
			action=new AdminBookModifyAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}						
		if(forward != null){
		if(forward.isRedirect()){			
			response.sendRedirect(forward.getPath());			
		}else{			
			RequestDispatcher dispatcher=
				request.getRequestDispatcher(forward.getPath());
			dispatcher.forward(request, response);			
		}
		}
	}
}
