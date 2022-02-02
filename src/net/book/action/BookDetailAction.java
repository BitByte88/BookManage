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
		
		List imgList = new ArrayList();
		int gr_book_num = 0;
		BookBean isnextpage = null;
		BookBean isprevpage = null;
		BookBean itemArray = null;
		BookBean next_Bean = null;
		BookBean prev_Bean = null;
		String item = null;
		String price = "no";

		gr_book_num = Integer.parseInt(
				request.getParameter("gr_book_num"));
		item = request.getParameter("item");
		
		int nextpage = 0;
		int prevpage = 0;
		
		if (request.getParameter("price") != null &&
			!request.getParameter("price").equals("")){
			price = request.getParameter("price");
		}
		if (request.getParameter("search")!= null){
			if (request.getParameter("search").equals("next")) {
				next_Bean = bookdao.findDetail(
						gr_book_num, item, price,"next");
				nextpage = next_Bean.getBOOK_NO();
				itemArray = bookdao.findDetailList(nextpage, item);
				isnextpage = bookdao.findDetail(
						nextpage, item, price,"next");
				isprevpage = bookdao.findDetail(
						nextpage, item, price,"prev");
			}else if (request.getParameter("search").equals("prev")){
				prev_Bean = bookdao.findDetail(
						gr_book_num, item,price,"prev");
				prevpage = prev_Bean.getBOOK_NO();
				itemArray = bookdao.findDetailList(prevpage, item);
				isnextpage = bookdao.findDetail(
						prevpage, item, price,"next");
				isprevpage = bookdao.findDetail(
						prevpage, item, price,"prev");
			}
		}else {
			itemArray = bookdao.findDetailList(gr_book_num, item);
			
			if (request.getParameter("isitem").equals("new")) {
				item = "new_item";
			}else if (request.getParameter("isitem").equals("hit")) {
				item = "hit_item";
			}
			
			isnextpage = bookdao.findDetail(
					gr_book_num, item, price,"next");
			isprevpage = bookdao.findDetail(
					gr_book_num, item, price,"prev");
		}
		
		String images = itemArray.getBOOK_IMAGE();
		StringTokenizer st = new StringTokenizer(images, ",");
		while (st.hasMoreTokens()) {
			imgList.add(st.nextToken());
		}
		
		String mainImage = (String) imgList.get(0);
		List contentImage = new ArrayList();
		for (int i = 2; i < imgList.size(); i++) {
			contentImage.add(imgList.get(i));
		}
		
		request.setAttribute("itemArray", itemArray);
		request.setAttribute("item", item);
		request.setAttribute("mainImage", mainImage);
		request.setAttribute("contentImage", contentImage);
		request.setAttribute("prevpage", isprevpage);
		request.setAttribute("nextpage", isnextpage);
		request.setAttribute("price", price);
		
		forward.setRedirect(false);
		forward.setPath("./book/book_detail.jsp");
		return forward;
	}
}