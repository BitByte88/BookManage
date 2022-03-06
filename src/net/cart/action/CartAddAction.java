package net.cart.action;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.cart.db.CartDAO;

public class CartAddAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		CartDAO cartdao=new CartDAO();
		HttpSession session = request.getSession();
		String id=(String)session.getAttribute("id");
		//セッションにログイン情報が存在しない場合、ログイン画面に遷移する。
		if(id==null){
			String referer = request.getHeader("Referer");
			request.getSession().setAttribute("redirectURI", referer);
			ActionForward forward=new ActionForward();
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;			
		}
		//パラメータチェック
		List<String> errorMsg = cartAddParameterCheck(request);	
		
		//チェック結果が「エラー」の場合
		if(errorMsg.size() != 0) {
			String error = "";
			for(String msg : errorMsg) {
				error = error + msg + "\\n";
			}
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+ error +"')");
			out.println("history.back()");
			out.println("</script>");
			return null;				
		}
		
		int num=Integer.parseInt(request.getParameter("bookno"));
		int amount=Integer.parseInt(request.getParameter("amount"));
		//カート情報を登録する。
		cartdao.cartAdd(id,num,amount);
		//カート画面に遷移する。
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./CartList.cart");
		return forward;
	}
	
	public List<String> cartAddParameterCheck(HttpServletRequest request) {
		List<String> errorMsg = new ArrayList<String>();
		String amount = request.getParameter("amount");
		
		try {
			if (amount == null || amount.isEmpty()) {//必須チェック（数量）
				errorMsg.add("数量を入力してください。");
			} else {
				int parseAmount = Integer.parseInt(request.getParameter("amount"));
				if(parseAmount <= 0) {//入力する数量が０以下の場合
					errorMsg.add("数量は１冊以上を注文してください。");
				}
			}
		} catch (Exception e) {//形式チェック（数量）
			errorMsg.add("数量は数字を入力してください。");
		}
		
		return errorMsg;
	}
}