package net.admin.order.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminOrderDAO;
import net.member.db.MemberBean;
import net.member.db.MemberDAO;
import net.order.db.OrderBean;

public class AdminOrderDetailAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		MemberDAO memberdao=new MemberDAO();
		MemberBean member=new MemberBean();
		AdminOrderDAO orderdao=new AdminOrderDAO();
		List<OrderBean> orderList=new ArrayList<OrderBean>();
		
		String num=request.getParameter("num");
		//注文詳細情報を取得
		orderList = orderdao.getOrderDetail(Integer.parseInt(num));
		//会員情報取得
		member=memberdao.getMember(orderList.get(0).getORDER_MEMBER_ID());
		request.setAttribute("orderlist", orderList);
		request.setAttribute("ordermember", member);
		//注文情報修正画面に遷移する。
		ActionForward forward=new ActionForward();
		forward.setPath("./adminOrder/admin_order_modify.jsp");
		return forward;
	 } 
}