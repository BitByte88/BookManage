package net.book.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.book.db.*;

public class BookDetailAction implements Action {
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		BookDAO bookdao = new BookDAO();
		
		List<String> imgList = new ArrayList<String>();
		int gr_book_no = 0;
		BookBean isnextpage = null;
		BookBean isprevpage = null;
		BookBean itemArray = null;
		BookBean next_Bean = null;
		BookBean prev_Bean = null;		

		gr_book_no = Integer.parseInt(request.getParameter("gr_book_no"));
		int nextpage = 0;
		int prevpage = 0;	
		
		
		if (request.getParameter("search") != null) {
			if (request.getParameter("search").equals("next")) {
				next_Bean = bookdao.findDetail(gr_book_no, "next");
				nextpage = next_Bean.getBOOK_NO();
				itemArray = bookdao.findDetailList(nextpage);
				isnextpage = bookdao.findDetail(nextpage, "next");
				isprevpage = bookdao.findDetail(nextpage, "prev");
			} else if (request.getParameter("search").equals("prev")) {
				prev_Bean = bookdao.findDetail(gr_book_no, "prev");
				prevpage = prev_Bean.getBOOK_NO();
				itemArray = bookdao.findDetailList(prevpage);
				isnextpage = bookdao.findDetail(prevpage, "next");
				isprevpage = bookdao.findDetail(prevpage, "prev");
			}
		} else {
			itemArray = bookdao.findDetailList(gr_book_no);


			isnextpage = bookdao.findDetail(gr_book_no, "next");
			isprevpage = bookdao.findDetail(gr_book_no, "prev");
		}
		
		String images = itemArray.getBOOK_IMAGE();
		StringTokenizer st = new StringTokenizer(images, ",");
		while (st.hasMoreTokens()) {
			imgList.add(st.nextToken());
		}	
		String mainImage = (String) imgList.get(0);
		List<String> contentImage = new ArrayList<String>();
		for (int i = 2; i < imgList.size(); i++) {
			contentImage.add(imgList.get(i));
		}
		
		request.setAttribute("itemArray", itemArray);
		request.setAttribute("mainImage", mainImage);
		request.setAttribute("contentImage", contentImage);
		request.setAttribute("prevpage", isprevpage);
		request.setAttribute("nextpage", isnextpage);
		
		forward.setRedirect(false);
		forward.setPath("./book/book_detail.jsp");
		return forward;
	}
}