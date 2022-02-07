package net.admin.book.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.book.db.*;
public class AdminBookModifyAction implements Action {
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		ActionForward forward=new ActionForward();
		AdminBookDAO abookdao= new AdminBookDAO();
		BookBean bookbean=new BookBean();		
//		bookbean.setBOOK_NUM(Integer.parseInt(request.getParameter("book_num")));
//		bookbean.setBOOK_CATEGORY(request.getParameter("book_category"));
//		bookbean.setBOOK_NAME(request.getParameter("book_name"));
//		bookbean.setBOOK_CONTENT(request.getParameter("book_content"));
//		bookbean.setBOOK_SIZE(request.getParameter("book_size"));
//		bookbean.setBOOK_COLOR(request.getParameter("book_color"));
//		bookbean.setBOOK_AMOUNT(Integer.parseInt(request.getParameter("book_amount")));
//		bookbean.setBOOK_PRICE(Integer.parseInt(request.getParameter("book_price")));
//		bookbean.setBOOK_BEST(Integer.parseInt(request.getParameter("book_best")));		
//		int result=abookdao.modifyBook(bookbean);
//		if(result<=0){
//			System.out.println("��ǰ ���� ����");
//			return null;
//		}
		
		forward.setPath("./BookList.adbook");
		forward.setRedirect(true);
		return forward;
	}
}
