package net.book.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.book.db.*;

public class BookListAction implements Action{
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response){
		ActionForward forward=new ActionForward();
		BookDAO bookdao=new BookDAO();
		
		List itemList=null;
		String item=null;
		String price="";
		int count=1;
		int page=1;
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		item=request.getParameter("item");
		
		if (request.getParameter("searchprice")==null || 
				request.getParameter("searchprice").equals("")) {
			itemList= bookdao.item_List(item,page);
			count=bookdao.getCount(item);
		} else {
			price=request.getParameter("searchprice");
			itemList= bookdao.item_List(item,page,price);
			count=bookdao.getCount(item, price);
		}
		
		int pageSize=12;
		int pageCount=count/pageSize+(count % pageSize==0?0:1);
		int startPage=(int)((page-1)/10)*10+1;
		int endPage=startPage+10-1;
		if (endPage>pageCount) endPage=pageCount;
		
		request.setAttribute("itemList", itemList);
		request.setAttribute("category", item);
		request.setAttribute("count", count);
		request.setAttribute("price", price);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		forward.setRedirect(false);
		forward.setPath("./book/book_list.jsp");
		return forward;
	}
}