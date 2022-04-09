package main.java.jp.co.bookmanage.service;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import main.java.jp.co.bookmanage.dto.BookDTO;
import main.java.jp.co.bookmanage.common.ForwardService;
import main.java.jp.co.bookmanage.dao.AdminBookDAO;

public class AdminBookService{
		
	//図書リスト画面表示
	public ForwardService AdminBookListAction(HttpServletRequest request, HttpServletResponse response) {
		AdminBookDAO abookdao=new AdminBookDAO();
		
		ForwardService forward=new ForwardService();
		//図書リスト取得
		List<BookDTO> list=abookdao.getBookList();
		//取得した図書リストを設定
		request.setAttribute("list",list);
		//図書リスト画面に遷移する。
		forward.setPath("./adminBook/admin_book_list.jsp");
		return forward;
	}

	//図書情報を登録する。
	public ForwardService AdminBookAddAction(HttpServletRequest request, HttpServletResponse response) {
		ForwardService forward = new ForwardService();
		AdminBookDAO abookdao= new AdminBookDAO();
		BookDTO bookdto = new BookDTO();
		int bookNo = abookdao.getBookNo();
		String realPath = "";
		String savePath = "upload";
		//最大サイズ指定
		int maxSize = 5 * 1024 * 1024;
		//アップロードファイルの指定ファイル名Mapを設定
		HashMap<String, String> map = new HashMap<String, String>();
		//サムネイル用画像
		map.put("file4", "Thumbnail");
		//メイン画像
		map.put("file3", "MainImage");
		//詳細画像1
		map.put("file2", "DetailImage1");
		//詳細画像2
		map.put("file1", "DetailImage2");
		//サーバ上の物理的なアップロードパスを取得
		realPath = request.getServletContext().getRealPath(savePath);
		List<String> savefiles=new ArrayList<>();
		try {
			MultipartRequest multi = null;
			//画面から登録された情報を取得（ファイルアップロード含め）
			multi = new MultipartRequest(request, realPath, maxSize, "UTF-8",
					new DefaultFileRenamePolicy());
			
			//パラメータチェック
			List<String> errorMsg = bookParameterCheck(multi);
			
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
			//アップロードファイル取得
			Enumeration<?> files=multi.getFileNames();
			//アップロードファイル件数分、以下の処理を行う。
			while(files.hasMoreElements()){
				String element = (String)files.nextElement();
				//ファイル名取得
				String fileName = multi.getFilesystemName(element);
				if(fileName == null) {
					continue;
				}
				//ファイルパス設定
	            String filepath  = realPath +"/" + fileName ; 
	            Path f = Paths.get(filepath);
	    		String Extension = "";
	    		//拡張子取得
	    		int i = fileName.lastIndexOf('.');
	    		if (i > 0) {
	    			Extension = fileName.substring(i+1);
	    		}
	    		//指定ファイル名取得
	    		String imageName = map.get(element);
	    		String newImage = imageName + "-" + bookNo + "." + Extension;
                //アップロードファイルを指定ファイル名に変換
	    		Files.move(f, f.resolveSibling(realPath + "/" + imageName + "-" + bookNo + "." + Extension ),StandardCopyOption.REPLACE_EXISTING);                  
				if(files.hasMoreElements()){
					//取得した要素が最後ではない場合
					savefiles.add(newImage+",");
				}else{
					//取得した要素が最後の場合
					savefiles.add(newImage);
				}
			}
			StringBuffer fl=new StringBuffer();
			for(int i=0;i<savefiles.size();i++){
				fl.append(savefiles.get(i));	
			}
			//カテゴリー
			bookdto.setBOOK_CATEGORY(multi.getParameter("book_category"));
			//書名
			bookdto.setBOOK_NAME(multi.getParameter("book_name"));
			//著者
			bookdto.setBOOK_WRITER(multi.getParameter("book_writer"));
			//出版社
			bookdto.setBOOK_PUBLISHER(multi.getParameter("book_publisher"));
			//発行日
			bookdto.setBOOK_PUBLISHING_DATE(Date.valueOf(multi.getParameter("book_publishing_date")));
			//販売価格
			bookdto.setBOOK_PRICE(Integer.parseInt(multi.getParameter("book_price")));
			//ISBNコード
			bookdto.setBOOK_ISBN(multi.getParameter("book_isbn"));
			//図書内容
			bookdto.setBOOK_CONTENT(multi.getParameter("book_content"));
			//イメージ
			bookdto.setBOOK_IMAGE(fl.toString());
			//図書情報登録
			int result = abookdao.insertBook(bookdto, bookNo);
			//図書情報登録が「失敗」の場合、エラーメッセージを図書登録画面に戻って表示する。
			if (result <= 0){
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('登録を失敗しました。')");
				out.println("history.back()");
				out.println("</script>");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//図書情報登録が「成功」の場合、図書リスト画面に遷移する。
		forward.setRedirect(true);
		forward.setPath("BookList.adbook");
		return forward;
	}
	
	//図書情報修正画面を表示する。
	public ForwardService AdminBookModifyForm(HttpServletRequest request, HttpServletResponse response) {
		ForwardService forward=new ForwardService();		
		AdminBookDAO abookdao=new AdminBookDAO();
		BookDTO bookdto=new BookDTO();	
		//図書NO取得
		String num=request.getParameter("book_no");
		//図書情報取得
		bookdto=abookdao.getBook(Integer.parseInt(num));
		//イメージ名区分（区分字「,」）
		String[] imageStr = bookdto.getBOOK_IMAGE().split(",");
		HashMap<String, String> imageMap = new HashMap<String, String>();
		HashMap<String, String> imageNameMap = new HashMap<String, String>();
		for(String imageName : imageStr) {
			//サムネイル用画像を設定
			if(imageName.contains("Thumbnail")) {
				imageNameMap.put("thumbnail",imageName);
				imageMap.put("thumbnail","./upload/" + imageName);
			}
			//メイン画像を設定
			if(imageName.contains("MainImage")) {
				imageNameMap.put("mainImage",imageName);
				imageMap.put("mainImage","./upload/" + imageName);
			}
			//詳細画像1を設定
			if(imageName.contains("DetailImage1")) {
				imageNameMap.put("detailImage1",imageName);
				imageMap.put("detailImage1","./upload/" + imageName);
			}
			//詳細画像2を設定
			if(imageName.contains("DetailImage2")) {
				imageNameMap.put("detailImage2",imageName);
				imageMap.put("detailImage2","./upload/" + imageName);
			}
		}
		request.setAttribute("abb", bookdto);	
		request.setAttribute("imageMap", imageMap);
		request.setAttribute("imageNameMap", imageNameMap);
		//図書情報変更画面に遷移する。
		forward.setPath("./adminBook/admin_book_modify.jsp");
		return forward;
	}
	
	//図書情報を修正する。
	public ForwardService AdminBookModifyAction(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		ForwardService forward=new ForwardService();
		BookDTO bookdto = new BookDTO();
		AdminBookDAO abookdao= new AdminBookDAO();
		int bookNo = Integer.parseInt(request.getParameter("bookNo"));
		String realPath = "";
		String savePath = "upload";
		//最大サイズ指定
		int maxSize = 5 * 1024 * 1024;
		//アップロードファイルの指定ファイル名Mapを設定
		HashMap<String, String> map = new HashMap<String, String>();
		//サムネイル用画像
		map.put("file4", "Thumbnail");
		//メイン画像
		map.put("file3", "MainImage");
		//詳細画像1
		map.put("file2", "DetailImage1");
		//詳細画像2
		map.put("file1", "DetailImage2");
		//サーバ上の物理的なアップロードパスを取得
		realPath = request.getServletContext().getRealPath(savePath);
		List<String> savefiles=new ArrayList<>();
		try {
			MultipartRequest multi = null;
			//画面から登録された情報を取得（ファイルアップロード含め）
			multi = new MultipartRequest(request, realPath, maxSize, "UTF-8",
					new DefaultFileRenamePolicy());
			
			//パラメータチェック
			List<String> errorMsg = bookParameterCheck(multi);	
			
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
			
			//アップロードファイル取得
			Enumeration<?> files=multi.getFileNames();
			//アップロードファイル件数分、以下の処理を行う。
			while(files.hasMoreElements()){
				String element = (String)files.nextElement();
				//既存に登録されているファイル名を取得する。
				String originalName = multi.getParameter(element + "Name");
	         	String newImage = "";
		    	String Extension = "";
				//ファイル名取得
				String fileName = multi.getFilesystemName(element);
				//登録されているファイルがなく変更するファイルもない場合
				if(originalName == null && fileName == null) {
					//次のファイルを続ける
					continue;
				} else if (fileName == null) {//登録されているファイルがあり、変更ファイルがない場合
					//登録されているファイルを維持する。
					newImage = originalName;
				} else {//変更ファイルがある場合
				//ファイルパス設定
	            String filepath  = realPath +"/" + fileName ; 
	            Path f = Paths.get(filepath);
	    		//拡張子取得
	    		int i = fileName.lastIndexOf('.');
	    		if (i > 0) {
	    			Extension = fileName.substring(i+1);
	    		}
	    		//指定ファイル名取得
	    		String imageName = map.get(element);
	    		newImage = imageName + "-" + bookNo + "." + Extension;
                Files.move(f, f.resolveSibling(realPath + "/" + imageName + "-" + bookNo + "." + Extension ),StandardCopyOption.REPLACE_EXISTING);                  
				}
				
                //アップロードファイルを指定ファイル名に変換
				if(files.hasMoreElements()){
					//取得した要素が最後ではない場合
					savefiles.add(newImage+",");
				}else{
					//取得した要素が最後の場合
					savefiles.add(newImage);
				}
			}
			StringBuffer fl=new StringBuffer();
			for(int i=0;i<savefiles.size();i++){
				fl.append(savefiles.get(i));	
			}
			//カテゴリー
			bookdto.setBOOK_CATEGORY(multi.getParameter("book_category"));
			//書名
			bookdto.setBOOK_NAME(multi.getParameter("book_name"));
			//著者
			bookdto.setBOOK_WRITER(multi.getParameter("book_writer"));
			//出版社
			bookdto.setBOOK_PUBLISHER(multi.getParameter("book_publisher"));
			//発行日
			bookdto.setBOOK_PUBLISHING_DATE(Date.valueOf(multi.getParameter("book_publishing_date")));
			//販売価格
			bookdto.setBOOK_PRICE(Integer.parseInt(multi.getParameter("book_price")));
			//ISBNコード
			bookdto.setBOOK_ISBN(multi.getParameter("book_isbn"));
			//図書内容
			bookdto.setBOOK_CONTENT(multi.getParameter("book_content"));
			//イメージ
			bookdto.setBOOK_IMAGE(fl.toString());
			//図書情報変更
			int result = abookdao.modifyBook(bookdto, bookNo);
			//図書情報変更が「失敗」の場合、エラーメッセージを図書登録画面に戻って表示する
			if (result <= 0){
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('修正を失敗しました。')");
				out.println("history.back()");
				out.println("</script>");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//図書情報変更が「成功」の場合、図書リスト画面に遷移する。
		forward.setRedirect(true);
		forward.setPath("BookList.adbook");
		return forward;
	}
	
	//図書情報を削除する。
	public ForwardService AdminBookDeleteAction(HttpServletRequest request,	HttpServletResponse response) {	
		ForwardService forward=new ForwardService();
		AdminBookDAO abookdao=new AdminBookDAO();
		BookDTO bookdto= new BookDTO();	
		bookdto.setBOOK_NO(
				Integer.parseInt(request.getParameter("book_no")));		
		//図書情報削除
		int check=abookdao.deleteBook(bookdto);
		//図書情報削除が「成功」の場合、図書リスト画面に遷移する。
		if(check>0){
			forward.setRedirect(true);
			forward.setPath("BookList.adbook");
		}		
		return forward;
	}
	
	//パラメータチェック（図書情報登録、　図書情報修正）
	public List<String> bookParameterCheck(MultipartRequest multi) {
		List<String> errorMsg = new ArrayList<String>();
		String category = multi.getParameter("book_category");
		String name = multi.getParameter("book_name");
		String writer = multi.getParameter("book_writer");
		String publisher = multi.getParameter("book_publisher");
		String publishingDate = multi.getParameter("book_publishing_date");
		String price = multi.getParameter("book_price");
		String isbn = multi.getParameter("book_isbn");
		String content = multi.getParameter("book_content");
		
		if(category == null || category.isEmpty()){//必須チェック（カテゴリー）
			errorMsg.add("カテゴリーを入力してください。");
		} else if (category.length() > 32) {//桁数チェック（カテゴリー）
			errorMsg.add("カテゴリーは32文字以内まで入力してください。");
		}
		
		if(name == null || name.isEmpty()){//必須チェック（書名）
			errorMsg.add("書名を入力してください。");
		} else if (name.length() > 32) {//桁数チェック（書名）
			errorMsg.add("書名は32文字以内まで入力してください。");
		}
		
		if(writer == null || writer.isEmpty()){//必須チェック（著者）
			errorMsg.add("著者を入力してください。");
		} else if (writer.length() > 32) {//桁数チェック（著者）
			errorMsg.add("著者は32文字以内まで入力してください。");
		}
		
		if(publisher == null || publisher.isEmpty()){//必須チェック（出版社）
			errorMsg.add("出版社を入力してください。");
		} else if (publisher.length() > 32) {//桁数チェック（出版社）
			errorMsg.add("出版社は32文字以内まで入力してください。");
		}
		
		if(publishingDate == null || publishingDate.isEmpty()){//必須チェック（発行日）
			errorMsg.add("発行日を入力してください。");
		} else {
			try {
				SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy-MM-dd");
				dateFormatParser.setLenient(false);
				java.util.Date inputDate = dateFormatParser.parse(publishingDate);
				java.util.Date now = new java.util.Date();
				if(now.before(inputDate)) {//入力する発行日が今日以後の日時の場合
					errorMsg.add("発行日は今日からそれ以前の日付のみ入力できます。");
				}
			} catch (ParseException e) {//形式チェック（発行日）
				errorMsg.add("発行日の形式を確認してください。");
			}	
		}
		
		if(price == null || price.isEmpty()){//必須チェック（販売価格）
			errorMsg.add("販売価格を入力してください。");
		} else {
			try {
				Integer.parseInt(price);
			} catch(Exception e) {//形式チェック（販売価格）
				errorMsg.add("販売価格は数字を入力してください。");
			}
		}
		
		if(isbn == null || isbn.isEmpty()){//必須チェック（ISBN）
			errorMsg.add("ISBNコードを入力してください。");
		} else if (isbn.length() != 10 && isbn.length() != 13) {//桁数チェック（ISBN
			errorMsg.add("ISBNコードは１０桁または１３桁で入力してください。");
		}
		
		if (content != null && content.length() > 128) {//桁数チェック（図書内容）
			errorMsg.add("図書内容は128文字以内まで入力してください。");
		}
		
		return errorMsg;
	}

}