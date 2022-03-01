package net.admin.order.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminOrderDAO;
import net.order.db.OrderBean;

public class AdminOrderListAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{		
		AdminOrderDAO orderdao=new AdminOrderDAO();
		List<OrderBean> orderlist = new ArrayList<OrderBean>();
		int page=1;
		//１ページに表示する注文件数
		int limit=50;
		
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		//すべての注文情報件数取得
		int ordercount=orderdao.getOrderCount();
		//注文リスト取得
		orderlist = orderdao.getOrderList(page,limit);
		//合計金額設定
		for(OrderBean order : orderlist) {
			order.setTOTAL_PRICE(orderdao.getTotalPrice(order.getORDER_NO()));
		}
		//ページング
	   	int maxpage=(int)((double)ordercount/limit+0.95);
	   	int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
	   	int endpage = maxpage;
	   	if (endpage>startpage+10-1) endpage=startpage+10-1;
	   	//現在ページ
	   	request.setAttribute("page", page);
	   	//最後のページ
	   	request.setAttribute("maxpage", maxpage);
	   	//ページングに最初に表示されるページ番号
	   	request.setAttribute("startpage", startpage);
	   	//ページングに最後に表示されるページ番号
	   	request.setAttribute("endpage", endpage);
	   	//すべての注文情報件数
		request.setAttribute("ordercount", ordercount);
		//注文リスト
		request.setAttribute("orderlist", orderlist);
		//注文リスト画面に遷移する。
		ActionForward forward=new ActionForward();
		forward.setPath("./adminOrder/admin_order_list.jsp");
		return forward;
	 } 
}