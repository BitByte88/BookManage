package net.admin.order.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminOrderDAO;
import net.order.db.OrderBean;

public class AdminOrderModifyAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		AdminOrderDAO orderdao=new AdminOrderDAO();
		OrderBean order=new OrderBean();

		//パラメータチェック
		List<String> errorMsg = orderParameterCheck(request);	
		
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
		
		boolean result=false;
		request.setCharacterEncoding("UTF-8");
		//注文NO
		order.setORDER_NO(Integer.parseInt(request.getParameter("num")));
		//メモー
		order.setORDER_MEMO(request.getParameter("memo"));
		//注文ステータス
		order.setORDER_STATUS(Integer.parseInt(request.getParameter("status")));
		//注文情報修正
		result=orderdao.modifyOrder(order);
		//注文情報修正結果が「失敗」場合
		if(result==false){
			System.out.println("注文情報修正エラー");
			return null;
		}
		//注文リスト画面に遷移する。
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./AdminOrderList.adorder");
		return forward;
	 }
	
	public List<String> orderParameterCheck(HttpServletRequest request) {
		List<String> errorMsg = new ArrayList<String>();
		String memo = request.getParameter("memo");
		String status = request.getParameter("status");
		
		if (memo != null && memo.length() > 128) {//桁数チェック（メモー）
			errorMsg.add("メモーは128文字以内まで入力してください。");
		}
		
		if(status == null || status.isEmpty()){//必須チェック（注文ステータス）
			errorMsg.add("注文ステータスを選択してください。");
		}
		
		return errorMsg;
	}
}