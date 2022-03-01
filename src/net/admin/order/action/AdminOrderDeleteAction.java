package net.admin.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminOrderDAO;

public class AdminOrderDeleteAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		AdminOrderDAO orderdao=new AdminOrderDAO();
		
		boolean result=false;
		String num=request.getParameter("num");
		//注文情報削除
		result=orderdao.deleteOrder(Integer.parseInt(num));
		//注文情報削除結果が「失敗」場合
		if(result==false){
			System.out.println("注文情報削除エラー");
			return null;
		}
		//注文リスト画面に遷移する。
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./AdminOrderList.adorder");
		return forward;
	 } 
}