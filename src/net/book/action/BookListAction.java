package net.book.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		String item=null;
		String price="";
		int count=1;
		int page=1;
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		SearchBean searchBean = new SearchBean();
		searchBean.setBOOK_NAME(request.getParameter("title"));
		searchBean.setBOOK_PUBLISHER(request.getParameter("publisher"));
		searchBean.setSTART_DATE(request.getParameter("startDate"));
		searchBean.setEND_DATE(request.getParameter("endDate"));

		//発行日形式チェック
		SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy/MM/dd"); 
        dateFormatParser.setLenient(false); 
        try {
        	if(searchBean.getSTART_DATE() != null && !searchBean.getSTART_DATE().isEmpty()) {
			dateFormatParser.parse(searchBean.getSTART_DATE());
        	}
        	if(searchBean.getEND_DATE() != null && !searchBean.getEND_DATE().isEmpty()) {
			dateFormatParser.parse(searchBean.getEND_DATE());
        	}
		} catch (ParseException e1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('発行日の形式を確認してください。');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		} 
		
		itemList= bookdao.item_List(page,searchBean);
		count=bookdao.getCount(item);
		
		int pageSize=12;
		int pageCount=count/pageSize+(count % pageSize==0?0:1);
		int startPage=(int)((page-1)/10)*10+1;
		int endPage=startPage+10-1;
		if (endPage>pageCount) endPage=pageCount;
		
		request.setAttribute("itemList", itemList);
		request.setAttribute("category", item);
		request.setAttribute("count", count);
		request.setAttribute("price", price);
		request.setAttribute("search", searchBean);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		forward.setRedirect(false);
		forward.setPath("./book/book_list.jsp");
		return forward;
	}
}