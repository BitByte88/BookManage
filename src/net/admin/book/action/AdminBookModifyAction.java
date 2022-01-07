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
		BookBean abb=new BookBean();		
		abb.setBOOK_NUM(Integer.parseInt(request.getParameter("book_num")));
		abb.setBOOK_CATEGORY(request.getParameter("book_category"));
		abb.setBOOK_NAME(request.getParameter("book_name"));
		abb.setBOOK_CONTENT(request.getParameter("book_content"));
		abb.setBOOK_SIZE(request.getParameter("book_size"));
		abb.setBOOK_COLOR(request.getParameter("book_color"));
		abb.setBOOK_AMOUNT(Integer.parseInt(request.getParameter("book_amount")));
		abb.setBOOK_PRICE(Integer.parseInt(request.getParameter("book_price")));
		abb.setBOOK_BEST(Integer.parseInt(request.getParameter("book_best")));		
		int result=abookdao.modifyBook(abb);
		if(result<=0){
			System.out.println("��ǰ ���� ����");
			return null;
		}
		
		forward.setPath("./BookList.adbook");
		forward.setRedirect(true);
		return forward;
	}
}
