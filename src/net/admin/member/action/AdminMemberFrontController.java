package net.admin.member.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminMemberFrontController extends HttpServlet {
	private static final long serialVersionUID = -4826023681180085537L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ForwardService forward = null;
		AdminMemberFrontService adminMemberFrontService = new AdminMemberFrontService();
		// 会員リスト画面
		if (command.equals("/AdminMemberList.admember")) {
			forward = adminMemberFrontService.AdminMemberListAction(request, response);
		}
		// 会員情報修正画面
		else if (command.equals("/AdminMemberDetail.admember")) {
				forward = adminMemberFrontService.AdminMemberDetailAction(request, response);
		}
		// 会員情報修正処理
		else if (command.equals("/AdminMembereModify.admember")) {
			try {
				forward = adminMemberFrontService.AdminMemberModifyAction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 会員情報削除処理
		else if (command.equals("/AdminMemberDelete.admember")) {
			forward = adminMemberFrontService.AdminMemberDeleteAction(request, response);
		}
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}
}