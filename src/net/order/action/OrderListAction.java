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
		//セッションにログイン情報が存在しない場合、ログイン画面に遷移する。
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
			//注文件数を取得
			int ordercount=orderdao.getOrderCount(id);
			//注文リストを取得
			book_order_list = orderdao.getOrderList(id, page);
			if(book_order_list != null && book_order_list.size() != 0) {
				//注文件数分、以下の処理を行う。
				for(OrderBean book_order : book_order_list) {
					//注文NO単位で、合計金額、アイテム件数を計算する。
					if(!totalPriceMap.containsKey(book_order.getORDER_NO())) {
						//（key：注文NO、value：0)で合計金額MAPを設定
						totalPriceMap.put(book_order.getORDER_NO(), 0);
						//（key：注文NO、value：0)でアイテム件数MAPを設定
						itemCountMap.put(book_order.getORDER_NO(), 0);
					}
				}
				Iterator<Integer> keys = totalPriceMap.keySet().iterator(); 
				while(keys.hasNext()){
					Integer key = keys.next(); 
					//（key：注文NO、value：DBから取得した合計金額)で合計金額MAPを設定
					totalPriceMap.put(key, orderdao.getOrderTotalPrice(id,key));
					//（key：注文NO、value：DBから取得したアイテム件数)でアイテム件数MAPを設定
					itemCountMap.put(key, orderdao.getItemCount(id,key));
					}
			}
			//ページング
			int pagesize=10;
			int maxpage=ordercount/pagesize+(ordercount % pagesize==0?0:1);
			int startpage=(int)((page-1)/10)*10+1;
			int endpage=startpage+10-1;
			if (endpage>maxpage) endpage=maxpage;
			
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
			//合計金額MAP
			request.setAttribute("totalPriceMap", totalPriceMap);
			//アイテム件数MAP
			request.setAttribute("itemCountMap", itemCountMap);
			//注文リスト
			request.setAttribute("book_order_list", book_order_list);
			//注文履歴画面に遷移する。
			forward.setRedirect(false);
			forward.setPath("./bookOrder/book_my_order.jsp");
			return forward;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	 } 
}