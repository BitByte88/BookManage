package net.admin.order.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminOrderDAO;
import net.member.db.MemberBean;
import net.member.db.MemberDAO;
import net.order.db.OrderBean;

public class AdminOrderFrontService {
	// 注文リスト画面
	public ForwardService AdminOrderListAction(HttpServletRequest request,HttpServletResponse response) {		
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
		ForwardService forward=new ForwardService();
		forward.setPath("./adminOrder/admin_order_list.jsp");
		return forward;
	 } 	
	// 注文情報修正画面
	public ForwardService AdminOrderDetailAction(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
		ForwardService forward=new ForwardService();
		forward.setPath("./adminOrder/admin_order_modify.jsp");
		return forward;
	 } 
	// 注文情報修正処理
	public ForwardService AdminOrderModifyAction(HttpServletRequest request,HttpServletResponse response) throws Exception{
		AdminOrderDAO orderdao=new AdminOrderDAO();
		OrderBean order=new OrderBean();

		//パラメータチェック
		List<String> errorMsg = orderParameterCheck(request);	
		
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
		ForwardService forward=new ForwardService();
		forward.setRedirect(true);
		forward.setPath("./AdminOrderList.adorder");
		return forward;
	 }
	// 注文情報削除処理
	public ForwardService AdminOrderDeleteAction(HttpServletRequest request,HttpServletResponse response) {
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
		ForwardService forward=new ForwardService();
		forward.setRedirect(true);
		forward.setPath("./AdminOrderList.adorder");
		return forward;
	 } 
	//パラメータチェック（注文情報修正）
	public List<String> orderParameterCheck(HttpServletRequest request) {
		List<String> errorMsg = new ArrayList<String>();
		String memo = request.getParameter("memo");
		String status = request.getParameter("status");
		
		if (memo != null && memo.length() > 128) {//桁数チェック（メモー）
			errorMsg.add("メモーは128文字以内まで入力してください。");
		}
		
		if(status == null || status.isEmpty()){//必須チェック（注文ステータス）
			errorMsg.add("注文ステータスを選択してください。");
		}
		
		return errorMsg;
	}
}