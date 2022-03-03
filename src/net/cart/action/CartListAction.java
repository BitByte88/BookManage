package net.cart.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.book.db.BookBean;
import net.cart.db.CartBean;
import net.cart.db.CartDAO;

public class CartListAction implements Action{
	@SuppressWarnings("unchecked")
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response)
	throws Exception{
		CartDAO cartdao=new CartDAO();
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
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		//カート、図書情報を取得する。
		map = cartdao.getCartList(id);
		//カート情報
		List<CartBean> cartlist=(ArrayList<CartBean>)map.get("cartlist");
		//図書情報
		List<BookBean> booklist=(ArrayList<BookBean>)map.get("booklist");
		
		request.setAttribute("cartlist", cartlist);
		request.setAttribute("booklist", booklist);
		//カート画面に遷移する。
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./bookOrder/book_cart.jsp");
		
		return forward;
	}
}
