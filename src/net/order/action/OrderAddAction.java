package net.order.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.book.db.BookBean;
import net.cart.db.CartBean;
import net.cart.db.CartDAO;
import net.order.db.OrderBean;
import net.order.db.OrderDAO;

public class OrderAddAction implements Action{
	@SuppressWarnings("unchecked")
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session=request.getSession();
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
		
		
		OrderDAO orderdao=new OrderDAO();
		OrderBean order=new OrderBean();
		
		List<CartBean> cartlist=new ArrayList<>();
		List<BookBean> booklist=new ArrayList<>();
		BookBean book=new BookBean();
		CartDAO cartdao=new CartDAO();
		CartBean cart=new CartBean();
		//アカウント
		order.setORDER_MEMBER_ID(request.getParameter("memberid"));
		//届け先_氏名
		order.setORDER_RECEIVE_NAME(request.getParameter("ORDER_RECEIVE_NAME"));
		//届け先_氏名（カナ）
		order.setORDER_RECEIVE_NAME_KANA(request.getParameter("ORDER_RECEIVE_NAME_KANA"));
		//届け先_メールアドレス
		order.setORDER_RECEIVE_EMAIL(request.getParameter("ORDER_RECEIVE_EMAIL"));
		//届け先_電話番号
		order.setORDER_RECEIVE_TEL(request.getParameter("ORDER_RECEIVE_TEL"));
		//届け先_郵便番号
		order.setORDER_RECEIVE_ZIPCODE(request.getParameter("ORDER_RECEIVE_ZIPCODE"));
		//届け先_都道府県
		order.setORDER_RECEIVE_ADD_1(request.getParameter("ORDER_RECEIVE_ADD_1"));
		//届け先_市区町村
		order.setORDER_RECEIVE_ADD_2(request.getParameter("ORDER_RECEIVE_ADD_2"));
		//届け先_丁目、番地、建物名
		order.setORDER_RECEIVE_ADD_3(request.getParameter("ORDER_RECEIVE_ADD_3"));
		//メモー
		order.setORDER_MEMO(request.getParameter("ORDER_MEMO"));
		//決済方法
		order.setORDER_TRADE_TYPE("オンライン入金");
		
		String orderType=request.getParameter("orderType");
		//図書詳細画面から遷移する場合
		if(orderType.equals("fromBookDetail")){
			//図書NO
			cart.setCART_BOOK_NO(Integer.parseInt(request.getParameter("bookno")));
			//数量
			cart.setCART_COUNT(Integer.parseInt(request.getParameter("amount")));
			//図書NO
			book.setBOOK_NO(Integer.parseInt(request.getParameter("bookno")));
			//書名
			book.setBOOK_NAME(request.getParameter("bookname"));
			//単価
			book.setBOOK_PRICE(Integer.parseInt(request.getParameter("price")));
			cartlist.add(cart);
			booklist.add(book);

		}
		//カート画面から遷移する場合
		else{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map = cartdao.getCartList(id);
			cartlist=(ArrayList<CartBean>)map.get("cartlist");
			booklist=(ArrayList<BookBean>)map.get("booklist");
		}
		//注文情報を登録する。
		orderdao.addOrder(order, cartlist, booklist);
		//カートをクリアする。
		cartdao.cartClear(id);
		//注文完了画面に遷移する。
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./OrderOk.order");
		return forward;
	}
}