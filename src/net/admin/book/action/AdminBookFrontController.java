package net.admin.book.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminBookFrontController extends HttpServlet {
	private static final long serialVersionUID = 4963860606433193242L;

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
		//図書情報登録処理
		if(command.equals("/BookAddAction.adbook")){
			action= new AdminBookAddAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		//図書リスト画面表示
		else if(command.equals("/BookList.adbook")){
			action=new AdminBookListAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		//図書登録画面表示
		else if(command.equals("/BookAdd.adbook")){
			forward=new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./adminBook/admin_book_write.jsp");
		}
		//図書情報削除処理
		else if(command.equals("/BookDelete.adbook")){
			action=new AdminBookDeleteAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//図書情報変更画面表示
		else if(command.equals("/BookModify.adbook")){
			action=new AdminBookModifyForm();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//図書情報変更処理
		else if(command.equals("/BookModifyAction.adbook")){
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
