package main.java.jp.co.bookmanage.service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.jp.co.bookmanage.dto.BookDTO;
import main.java.jp.co.bookmanage.dto.CartDTO;
import main.java.jp.co.bookmanage.dto.MemberDTO;
import main.java.jp.co.bookmanage.dto.OrderDTO;
import main.java.jp.co.bookmanage.dao.MemberDAO;
import main.java.jp.co.bookmanage.dao.OrderDAO;
import main.java.jp.co.bookmanage.common.ForwardService;
import main.java.jp.co.bookmanage.dao.CartDAO;

public class OrderService {
	//注文詳細画面
	@SuppressWarnings("unchecked")
	public ForwardService OrderStartAction(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ForwardService forward=new ForwardService();
		
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
			List<CartDTO> cartlist=(ArrayList<CartDTO>)map.get("cartlist");
			//図書情報
			List<BookDTO> booklist=(ArrayList<BookDTO>)map.get("booklist");
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
		MemberDTO member=(MemberDTO)memberdao.getMember(id);
		request.setAttribute("member", member);
		//注文詳細画面に遷移する。
		forward.setRedirect(false);
		forward.setPath("./bookOrder/book_buy.jsp");
		return forward;
	}
	//注文履歴画面
	public ForwardService OrderListAction(HttpServletRequest request,HttpServletResponse response) {
		ForwardService forward=new ForwardService();
		
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
		List<OrderDTO> book_order_list=new ArrayList<OrderDTO>();	
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
				for(OrderDTO book_order : book_order_list) {
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
	//注文登録処理
	@SuppressWarnings("unchecked")
	public ForwardService OrderAddAction(HttpServletRequest request,HttpServletResponse response) 	throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		//セッションにログイン情報が存在しない場合、ログイン画面に遷移する。
		if(id==null){
			String referer = request.getHeader("Referer");
			request.getSession().setAttribute("redirectURI", referer);
			ForwardService forward=new ForwardService();
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;
		}
		
		//パラメータチェック
		List<String> errorMsg = orderAddParameterCheck(request);	
		
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
		
		OrderDAO orderdao=new OrderDAO();
		OrderDTO order=new OrderDTO();
		
		List<CartDTO> cartlist=new ArrayList<>();
		List<BookDTO> booklist=new ArrayList<>();
		BookDTO book=new BookDTO();
		CartDAO cartdao=new CartDAO();
		CartDTO cart=new CartDTO();
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
			cartlist=(ArrayList<CartDTO>)map.get("cartlist");
			booklist=(ArrayList<BookDTO>)map.get("booklist");
		}
		//注文情報を登録する。
		orderdao.addOrder(order, cartlist, booklist);
		//カートをクリアする。
		cartdao.cartClear(id);
		//注文完了画面に遷移する。
		ForwardService forward=new ForwardService();
		forward.setRedirect(true);
		forward.setPath("./OrderOk.order");
		return forward;
	}
	//パラメータチェック（注文詳細）
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
	//パラメータチェック（注文登録）
	public List<String> orderAddParameterCheck(HttpServletRequest request) {
		List<String> errorMsg = new ArrayList<String>();
		String name = request.getParameter("ORDER_RECEIVE_NAME");
		String nameKana = request.getParameter("ORDER_RECEIVE_NAME_KANA");
		String tel = request.getParameter("ORDER_RECEIVE_TEL");
		String mail = request.getParameter("ORDER_RECEIVE_EMAIL");
		String zipCode = request.getParameter("ORDER_RECEIVE_ZIPCODE");
		String add1 = (request.getParameter("ORDER_RECEIVE_ADD_1"));
		String add2 = request.getParameter("ORDER_RECEIVE_ADD_2");
		String add3 = request.getParameter("ORDER_RECEIVE_ADD_3");
		String memo = request.getParameter("ORDER_MEMO");
		
		if(name == null || name.isEmpty()){//必須チェック（届け先_氏名）
			errorMsg.add("届け先_氏名を入力してください。");
		} else if (name.length() > 32) {//桁数チェック（届け先_氏名）
			errorMsg.add("届け先_氏名は32文字以内まで入力してください。");
		}
		
		if(nameKana == null || nameKana.isEmpty()){//必須チェック（届け先_氏名（カナ））
			errorMsg.add("届け先_氏名（カナ）を入力してください。");
		} else if (nameKana.length() > 32) {//桁数チェック（届け先_氏名（カナ））
			errorMsg.add("届け先_氏名（カナ）は32文字以内まで入力してください。");
		}
		
		if(tel == null || tel.isEmpty()){//必須チェック（届け先_TEL）
			errorMsg.add("届け先_TELを入力してください。");
		} else if (tel.length() > 16) {//桁数チェック（届け先_TEL）
			errorMsg.add("届け先_TELは16文字以内まで入力してください。");
		}
		
		if(mail == null || mail.isEmpty()){//必須チェック（届け先_メールアドレス）
			errorMsg.add("届け先_メールアドレスを入力してください。");
		} else if (mail.length() > 32) {//桁数チェック（届け先_メールアドレス）
			errorMsg.add("届け先_メールアドレスは128文字以内まで入力してください。");
		}
		
		if(zipCode == null || zipCode.length() <= 6){//必須チェック（郵便番号
			errorMsg.add("届け先_郵便番号を入力してください。");
		}
		
		if(add1 == null || add1.isEmpty()){//必須チェック（届け先_都道府県）
			errorMsg.add("届け先_都道府県を選択してください。");
		} else if (add1.length() > 128) {//桁数チェック（届け先_都道府県）
			errorMsg.add("届け先_都道府県は128文字以内まで入力してください。");
		}
		
		if(add2 == null || add2.isEmpty()){//必須チェック（届け先_市区町村）
			errorMsg.add("届け先_市区町村を選択してください。");
		} else if (add2.length() > 128) {//桁数チェック（届け先_市区町村）
			errorMsg.add("届け先_市区町村は128文字以内まで入力してください。");
		}
		
		if(add3 == null || add3.isEmpty()){//必須チェック（届け先_丁目、番地、建物名）
			errorMsg.add("届け先_丁目、番地、建物名を選択してください。");
		} else if (add3.length() > 128) {//桁数チェック（届け先_丁目、番地、建物名）
			errorMsg.add("届け先_丁目、番地、建物名は128文字以内まで入力してください。");
		}
		
		if(memo != null && memo.length() > 128){//桁数チェック（メモー）
			errorMsg.add("メモーは128文字以内まで入力してください。");
		}
		
		return errorMsg;
	}
	
	
}