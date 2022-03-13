package main.java.jp.co.bookmanage.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.jp.co.bookmanage.dto.BookDTO;
import main.java.jp.co.bookmanage.dto.SearchDTO;
import main.java.jp.co.bookmanage.common.ForwardService;
import main.java.jp.co.bookmanage.dao.BookDAO;

public class BookService {
	//図書リスト画面
	public ForwardService BookListAction(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ForwardService forward=new ForwardService();
		BookDAO bookdao=new BookDAO();
		request.setCharacterEncoding("UTF-8");
		List<BookDTO> itemList=null;
		int count=1;
		int page=1;
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		SearchDTO searchdto = new SearchDTO();
		//タイトル検索の入力値がある場合
		if(request.getParameter("title") != null) {
		searchdto.setBOOK_NAME(request.getParameter("title").trim());
		}
		//出版社検索の入力値がある場合
		if(request.getParameter("publisher") != null) {
		searchdto.setBOOK_PUBLISHER(request.getParameter("publisher").trim());
		}
		//発行日検索の入力値（開始）がある場合
		if(request.getParameter("startDate") != null) {
		searchdto.setSTART_DATE(request.getParameter("startDate").trim());
		}
		//タイトル検索の入力値（終了）がある場合
		if(request.getParameter("endDate") != null) {
		searchdto.setEND_DATE(request.getParameter("endDate").trim());
		}
		//発行日形式チェック
		SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy/MM/dd"); 
        dateFormatParser.setLenient(false); 
        try {
        	
        	Date date1 = null;
        	Date date2 = null;
        	
        	//発行日検索の入力値（開始）がある場合
        	if(searchdto.getSTART_DATE() != null && !searchdto.getSTART_DATE().isEmpty()) {
			date1 = dateFormatParser.parse(searchdto.getSTART_DATE());
        	}
        	//発行日検索の入力値（終了）がある場合
        	if(searchdto.getEND_DATE() != null && !searchdto.getEND_DATE().isEmpty()) {
			date2 = dateFormatParser.parse(searchdto.getEND_DATE());
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
		itemList= bookdao.item_List(page,searchdto);
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
		request.setAttribute("search", searchdto);
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
	//図書詳細画面
	public ForwardService BookDetailAction(HttpServletRequest request,HttpServletResponse response) {
		ForwardService forward = new ForwardService();
		BookDAO bookdao = new BookDAO();
		
		List<String> imgList = new ArrayList<String>();
		int book_no = 0;
		BookDTO isnextpage = null;
		BookDTO isprevpage = null;
		BookDTO itemArray = null;
		BookDTO next_Bean = null;
		BookDTO prev_Bean = null;		
		//図書NO
		book_no = Integer.parseInt(request.getParameter("book_no"));
		int nextpage = 0;
		int prevpage = 0;	
		
		if (request.getParameter("search") != null) {
			//「次商品」ボタンを押す場合
			if (request.getParameter("search").equals("next")) {
				//次の図書の情報を取得する。
				next_Bean = bookdao.findDetailNextPrev(book_no, "next");
				nextpage = next_Bean.getBOOK_NO();
				//次の図書の詳細情報を取得する。
				itemArray = bookdao.findDetail(nextpage);
				//取得した図書の次図書の情報を取得する。
				isnextpage = bookdao.findDetailNextPrev(nextpage, "next");
				//取得した図書の前図書の情報を取得する。
				isprevpage = bookdao.findDetailNextPrev(nextpage, "prev");
			} 
			//「前商品」ボタンを押す場合
			else if (request.getParameter("search").equals("prev")) {
				//前の図書の情報を取得する。
				prev_Bean = bookdao.findDetailNextPrev(book_no, "prev");
				prevpage = prev_Bean.getBOOK_NO();
				//前の図書の詳細情報を取得する。
				itemArray = bookdao.findDetail(prevpage);
				//取得した図書の次図書の情報を取得する。
				isnextpage = bookdao.findDetailNextPrev(prevpage, "next");
				//取得した図書の前図書の情報を取得する。
				isprevpage = bookdao.findDetailNextPrev(prevpage, "prev");
			}
		} else {
			//図書の詳細情報を取得する。
			itemArray = bookdao.findDetail(book_no);

			//取得した図書の次図書の情報を取得する。
			isnextpage = bookdao.findDetailNextPrev(book_no, "next");
			//取得した図書の前図書の情報を取得する。
			isprevpage = bookdao.findDetailNextPrev(book_no, "prev");
		}
		//図書イメージ
		String images = itemArray.getBOOK_IMAGE();
		StringTokenizer st = new StringTokenizer(images, ",");
		while (st.hasMoreTokens()) {
			imgList.add(st.nextToken());
		}
		String mainImage = "";
		if(imgList.size() != 0) {
		mainImage = (String) imgList.get(0);
		}
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