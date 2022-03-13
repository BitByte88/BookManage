package main.java.jp.co.bookmanage.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.jp.co.bookmanage.common.ForwardService;
import main.java.jp.co.bookmanage.service.MemberService;

public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 5048611743821593295L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ForwardService forward = null;
		MemberService memberService = new MemberService();
		// ログイン画面
		if (command.equals("/MemberLogin.member")) {
			forward = new ForwardService();
			forward.setPath("./member/member_login.jsp");
		}
		// 会員登録画面
		else if (command.equals("/MemberJoin.member")) {
			forward = new ForwardService();
			forward.setPath("./member/member_join.jsp");
		}
		// 会員情報探す画面
		else if (command.equals("/MemberFind.member")) {
			forward = new ForwardService();
			forward.setPath("./member/member_find.jsp");
		}
		// 会員退会画面
		else if (command.equals("/MemberOut.member")) {
			forward = new ForwardService();
			forward.setPath("./member/member_out.jsp");
		}
		// 郵便番号検索画面
		else if (command.equals("/Zipcode.member")) {
			forward = new ForwardService();
			forward.setPath("./member/member_zipcode.jsp");
		}
		// ログイン処理
		else if (command.equals("/MemberLoginAction.member")) {
			try {
				forward = memberService.MemberLoginAction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 会員登録処理
		else if (command.equals("/MemberJoinAction.member")) {
			try {
				forward = memberService.MemberJoinAction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 会員情報修正画面
		else if (command.equals("/MemberModifyAction_1.member")) {
			try {
				forward = memberService.MemberModifyAction_1(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 会員情報修正処理
		else if (command.equals("/MemberModifyAction_2.member")) {
			try {
				forward = memberService.MemberModifyAction_2(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 会員退会処理
		else if (command.equals("/MemberDeleteAction.member")) {
			try {
				forward = memberService.MemberDeleteAction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// アカウント確認処理
		else if (command.equals("/MemberIDCheckAction.member")) {
			try {
				forward = memberService.MemberIDCheckAction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 会員情報探す処理
		else if (command.equals("/MemberFindAction.member")) {
			try {
				forward = memberService.MemberFindAction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 郵便番号検索処理
		else if (command.equals("/MemberZipcodeAction.member")) {
			try {
				forward = memberService.MemberZipcodeAction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ログアウト
		else if (command.equals("/MemberLogout.member")) {
			HttpSession sesseion = request.getSession(true);
			sesseion.removeAttribute("id");
			sesseion.removeAttribute("memberType");
			sesseion.removeAttribute("member");
			forward = new ForwardService();
			forward.setPath("./member/member_login.jsp");
		}
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			}
		}
	}
}