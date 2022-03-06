package net.book.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.book.db.*;

public class BookListAction implements Action{
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		ActionForward forward=new ActionForward();
		BookDAO bookdao=new BookDAO();
		request.setCharacterEncoding("UTF-8");
		List<BookBean> itemList=null;
		int count=1;
		int page=1;
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		SearchBean searchBean = new SearchBean();
		//タイトル検索の入力値がある場合
		if(request.getParameter("title") != null) {
		searchBean.setBOOK_NAME(request.getParameter("title").trim());
		}
		//出版社検索の入力値がある場合
		if(request.getParameter("publisher") != null) {
		searchBean.setBOOK_PUBLISHER(request.getParameter("publisher").trim());
		}
		//発行日検索の入力値（開始）がある場合
		if(request.getParameter("startDate") != null) {
		searchBean.setSTART_DATE(request.getParameter("startDate").trim());
		}
		//タイトル検索の入力値（終了）がある場合
		if(request.getParameter("endDate") != null) {
		searchBean.setEND_DATE(request.getParameter("endDate").trim());
		}
		//発行日形式チェック
		SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy/MM/dd"); 
        dateFormatParser.setLenient(false); 
        try {
        	
        	Date date1 = null;
        	Date date2 = null;
        	
        	//発行日検索の入力値（開始）がある場合
        	if(searchBean.getSTART_DATE() != null && !searchBean.getSTART_DATE().isEmpty()) {
			date1 = dateFormatParser.parse(searchBean.getSTART_DATE());
        	}
        	//発行日検索の入力値（終了）がある場合
        	if(searchBean.getEND_DATE() != null && !searchBean.getEND_DATE().isEmpty()) {
			date2 = dateFormatParser.parse(searchBean.getEND_DATE());
        	}
        	
        	//発行日検索の入力値（開始）より発行日検索の入力値（終了）が以後の日時の場合
        	if(date1 !=null && date2 != null) {
        		if(date2.before(date1)) {
        			response.setContentType("text/html; charset=UTF-8");
        			PrintWriter out = response.getWriter();
        			out.println("<script>");
        			out.println("alert('発行日検索の入力値範囲を確認してください。');");
        			out.println("history.back();");
        			out.println("</script>");
        			out.close();
        			return null;
				}
        	}
        	
		} catch (ParseException e1) {
			//発行日の形式に問題がある場合、エラーメッセージを画面に表示する。
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('発行日の形式を確認してください。');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		} 
		//図書リスト取得
		itemList= bookdao.item_List(page,searchBean);
		//図書件数取得
		count=bookdao.getCount();
		//ページング
		int pageSize=12;
		int pageCount=count/pageSize+(count % pageSize==0?0:1);
		int startPage=(int)((page-1)/10)*10+1;
		int endPage=startPage+10-1;
		if (endPage>pageCount) endPage=pageCount;
		//図書リスト
		request.setAttribute("itemList", itemList);
		//図書件数
		request.setAttribute("count", count);
		//検索入力値
		request.setAttribute("search", searchBean);
		//ページ数
		request.setAttribute("pageCount", pageCount);
		//ページングに最初に表示されるページ番号
		request.setAttribute("startPage", startPage);
		//ページングに最後に表示されるページ番号
		request.setAttribute("endPage", endPage);
		//図書リスト画面に遷移する。
		forward.setRedirect(false);
		forward.setPath("./book/book_list.jsp");
		return forward;
	}
}