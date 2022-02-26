package net.order.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.order.db.OrderDAO;
import net.order.db.OrderBean;

public class OrderListAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response){
		ActionForward forward=new ActionForward();
		
		HttpSession session = request.getSession();
		String id =(String)session.getAttribute("id");
		
		if(id==null){			
			String referer = request.getHeader("Referer");
			request.getSession().setAttribute("redirectURI", referer);
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;
		}
		
		int page=1;
		HashMap<Integer, Integer> totalPriceMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> itemCountMap = new HashMap<Integer, Integer>();
		OrderDAO orderdao=new OrderDAO();
		List<OrderBean> book_order_list=new ArrayList<OrderBean>();	
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		try{
			int ordercount=orderdao.getOrderCount(id);

			book_order_list = orderdao.getOrderList(id, page);
			if(book_order_list != null && book_order_list.size() != 0) {

				for(OrderBean book_order : book_order_list) {
					if(!totalPriceMap.containsKey(book_order.getORDER_NO())) {
						totalPriceMap.put(book_order.getORDER_NO(), 0);
						itemCountMap.put(book_order.getORDER_NO(), 0);
					}
				}
				Iterator<Integer> keys = totalPriceMap.keySet().iterator(); 
				while(keys.hasNext()){
					Integer key = keys.next(); 
					totalPriceMap.put(key, orderdao.getOrderTotalPrice(id,key));
					itemCountMap.put(key, orderdao.getItemCount(id,key));
					}
			}
			
			int pagesize=10;
			int maxpage=ordercount/pagesize+(ordercount % pagesize==0?0:1);
			int startpage=(int)((page-1)/10)*10+1;
			int endpage=startpage+10-1;
			if (endpage>maxpage) endpage=maxpage;
			
			
			request.setAttribute("page", page);
			request.setAttribute("maxpage", maxpage);
			request.setAttribute("startpage", startpage);
			request.setAttribute("endpage", endpage);
			request.setAttribute("ordercount", ordercount);
			request.setAttribute("totalPriceMap", totalPriceMap);
			request.setAttribute("itemCountMap", itemCountMap);
			request.setAttribute("book_order_list", book_order_list);
			
			forward.setRedirect(false);
			forward.setPath("./bookOrder/book_my_order.jsp");
			return forward;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	 } 
}