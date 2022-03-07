package net.order.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.book.db.BookBean;
import net.cart.db.CartBean;
import net.cart.db.CartDAO;
import net.member.db.MemberBean;
import net.member.db.MemberDAO;

public class OrderStartAction implements Action{
	@SuppressWarnings("unchecked")
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		ActionForward forward=new ActionForward();
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		//セッションにログイン情報が存在しない場合、ログイン画面に遷移する。
		if(id==null){
			String referer = request.getHeader("Referer");
			request.getSession().setAttribute("redirectURI", referer);
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;		
		}
		
		request.setCharacterEncoding("UTF-8");
		List<Object> orderinfo=new ArrayList<>();
		String orderType=request.getParameter("orderType");
		//図書詳細画面から遷移する場合
		if(orderType.equals("fromBookDetail")){

			//パラメータチェック
			List<String> errorMsg = orderStartParameterCheck(request);	
			
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
			
			//図書NO
			orderinfo.add(Integer.parseInt(request.getParameter("bookno")));
			//画像
			orderinfo.add(request.getParameter("bookimage"));
			//書名
			orderinfo.add(request.getParameter("bookname"));
			//数量
			orderinfo.add(Integer.parseInt(request.getParameter("amount")));
			//単価
			orderinfo.add(Integer.parseInt(request.getParameter("price")));
			//注文タイプ：図書詳細画面から遷移
			request.setAttribute("orderType", "fromBookDetail");
			//注文情報
			request.setAttribute("orderinfo", orderinfo);
			//合計金額 (数量＊単価)
			int totalPrice = Integer.parseInt(request.getParameter("amount")) * Integer.parseInt(request.getParameter("price"));
			request.setAttribute("totalPrice", totalPrice);
		}else{
			//カート画面から遷移する場合
			CartDAO cartdao=new CartDAO();
			HashMap<String, Object> map = new HashMap<String, Object>();
			//カート、図書情報を取得する。
			map = cartdao.getCartList(id);
			//カート情報
			List<CartBean> cartlist=(ArrayList<CartBean>)map.get("cartlist");
			//図書情報
			List<BookBean> booklist=(ArrayList<BookBean>)map.get("booklist");
			//注文タイプ：カート画面から遷移
			request.setAttribute("orderType", "fromCart");
			//カート情報
			request.setAttribute("cartlist", cartlist);
			//図書情報
			request.setAttribute("booklist", booklist);
			int totalPrice = 0;
			//合計金額 (数量＊単価)
			for(int i=0 ; i < cartlist.size() ; i++) {
				totalPrice += cartlist.get(i).getCART_COUNT() * booklist.get(i).getBOOK_PRICE();
			}
			request.setAttribute("totalPrice", totalPrice);
		}
		
		MemberDAO memberdao=new MemberDAO();
		//会員情報を取得
		MemberBean member=(MemberBean)memberdao.getMember(id);
		request.setAttribute("member", member);
		//注文詳細画面に遷移する。
		forward.setRedirect(false);
		forward.setPath("./bookOrder/book_buy.jsp");
		return forward;
	}
	
	public List<String> orderStartParameterCheck(HttpServletRequest request) {
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
		} catch (Exception e) {//形式チェック（数量
			errorMsg.add("数量は数字を入力してください。");
		}
		
		return errorMsg;
	}
}