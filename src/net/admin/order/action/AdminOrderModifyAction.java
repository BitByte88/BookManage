package net.admin.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminOrderDAO;
import net.order.db.OrderBean;

public class AdminOrderModifyAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		AdminOrderDAO orderdao=new AdminOrderDAO();
		OrderBean order=new OrderBean();
		
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
}