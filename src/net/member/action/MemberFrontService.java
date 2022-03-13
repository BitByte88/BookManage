package net.member.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDTO;
import net.member.db.MemberDAO;

class ForwardService {
	private boolean isRedirect=false;
	private String path=null;
	
	public boolean isRedirect(){
		return isRedirect;
	}
	
	public String getPath(){
		return path;
	}
	
	public void setRedirect(boolean b){
		isRedirect=b;
	}
	
	public void setPath(String string){
		path=string;
	}
}

public class MemberFrontService {
	// ログイン処理
	public ForwardService MemberLoginAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		ForwardService forward = new ForwardService();
		MemberDAO memberdao = new MemberDAO();

		// パラメータチェック
		List<String> errorMsg = loginParameterCheck(request);

		// チェック結果が「エラー」の場合
		if (errorMsg.size() != 0) {
			String error = "";
			for (String msg : errorMsg) {
				error = error + msg + "\\n";
			}

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + error + "')");
			out.println("history.back()");
			out.println("</script>");
			return null;
		}

		String id = request.getParameter("MEMBER_ID");
		String pass = request.getParameter("MEMBER_PW");
		// ログイン情報チェック
		int check = memberdao.userCheck(id, pass);
		// チェック結果が「成功」の場合
		if (check == 1) {
			session.setAttribute("id", id);
			// 会員種別が「０:管理者」の場合
			if (memberdao.isAdmin(id)) {
				// 図書リスト（管理者）画面に遷移する。
				session.setAttribute("memberType", 0); // 管理者
				forward.setRedirect(true);
				forward.setPath("./BookList.adbook");
				return forward;
			} else {
				// 会員種別が「１:一般ユーザー」の場合
				session.setAttribute("memberType", 1); // 一般ユーザー

				String redirectURL = (String) session.getAttribute("redirectURI");
				// 遷移元がない場合
				if (redirectURL == null) {
					forward.setRedirect(true);
					forward.setPath("./BookList.book");
				} else {
					// 遷移元がある場合、遷移元に遷移する。
					forward.setRedirect(true);
					redirectURL = redirectURL.substring(redirectURL.lastIndexOf("/"));
					forward.setPath("." + redirectURL);
					session.removeAttribute("redirectURI");
				}
				return forward;
			}
		}
		// チェック結果が「失敗：パスワード未一致」の場合、エラーメッセージを表示
		else if (check == 0) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('パスワードが一致していません。');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} else {
			// チェック結果が「失敗：一致するアカウントが無い」の場合、エラーメッセージを表示
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('アカウントが登録されていません。');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		return null;
	}
	// 会員登録処理
	public ForwardService MemberJoinAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		MemberDAO memberdao = new MemberDAO();
		MemberDTO dto = new MemberDTO();

		// パラメータチェック
		List<String> errorMsg = joinParameterCheck(request);

		// チェック結果が「エラー」の場合
		if (errorMsg.size() != 0) {
			String error = "";
			for (String msg : errorMsg) {
				error = error + msg + "\\n";
			}

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + error + "')");
			out.println("history.back()");
			out.println("</script>");
			return null;
		}

		ForwardService forward = null;
		// アカウント
		dto.setMEMBER_ID(request.getParameter("MEMBER_ID"));
		// パスワード
		dto.setMEMBER_PW(request.getParameter("MEMBER_PW1"));
		// 氏名
		dto.setMEMBER_NAME(request.getParameter("MEMBER_NAME"));
		// 氏名（カナ）
		dto.setMEMBER_NAME_KANA(request.getParameter("MEMBER_NAME_KANA"));
		// 電話番号
		dto.setMEMBER_TEL(request.getParameter("MEMBER_TEL"));
		// メールアドレス
		dto.setMEMBER_MAIL(request.getParameter("MEMBER_MAIL1") + "@" + request.getParameter("MEMBER_MAIL2"));
		// 郵便番号
		dto.setMEMBER_ZIPCODE(request.getParameter("MEMBER_ZIPCODE"));
		// 都道府県
		dto.setMEMBER_ADD_1(request.getParameter("MEMBER_ADD_1"));
		// 市区町村
		dto.setMEMBER_ADD_2(request.getParameter("MEMBER_ADD_2"));
		// 丁目、番地、建物名
		dto.setMEMBER_ADD_3(request.getParameter("MEMBER_ADD_3"));
		// 会員情報登録処理
		memberdao.insertMember(dto);
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 登録完了メッセージを表示し、ログイン画面に遷移する。
		out.println("<script>");
		out.println("alert('会員登録が完了しました。');");
		out.println("location.href='./MemberLogin.member';");
		out.println("</script>");
		out.close();
		return forward;
	}
	// 会員情報修正画面
	public ForwardService MemberModifyAction_1(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForwardService forward = new ForwardService();
		HttpSession sesseion = request.getSession(true);
		String id = (String) sesseion.getAttribute("id");
		// セッションにログイン情報が存在しない場合、ログイン画面に遷移する。
		if (id == null) {
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;
		}

		MemberDAO memberdao = new MemberDAO();
		// 会員情報取得
		MemberDTO dto = memberdao.getMember(id);
		// 会員情報修正画面に遷移する。
		request.setAttribute("member", dto);
		forward.setPath("./member/member_info.jsp");
		return forward;
	}
	// 会員情報修正処理
	public ForwardService MemberModifyAction_2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");

		ForwardService forward = new ForwardService();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		// セッションにログイン情報が存在しない場合、ログイン画面に遷移する。
		if (id == null) {
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;
		}

		// パラメータチェック
		List<String> errorMsg = modifyMemberParameterCheck(request);

		// チェック結果が「エラー」の場合
		if (errorMsg.size() != 0) {
			String error = "";
			for (String msg : errorMsg) {
				error = error + msg + "\\n";
			}

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + error + "')");
			out.println("history.back()");
			out.println("</script>");
			return null;
		}

		MemberDAO memberdao = new MemberDAO();
		MemberDTO dto = new MemberDTO();
		// アカウント
		dto.setMEMBER_ID(id);
		// パスワード
		dto.setMEMBER_PW(request.getParameter("MEMBER_PW1"));
		// 氏名
		dto.setMEMBER_NAME(request.getParameter("MEMBER_NAME"));
		// 氏名（カナ）
		dto.setMEMBER_NAME_KANA(request.getParameter("MEMBER_NAME_KANA"));
		// 電話番号
		dto.setMEMBER_TEL(request.getParameter("MEMBER_TEL"));
		// メールアドレス
		dto.setMEMBER_MAIL(request.getParameter("MEMBER_MAIL1") + "@" + request.getParameter("MEMBER_MAIL2"));
		// 郵便番号
		dto.setMEMBER_ZIPCODE(request.getParameter("MEMBER_ZIPCODE"));
		// 都道府県
		dto.setMEMBER_ADD_1(request.getParameter("MEMBER_ADD_1"));
		// 市区町村
		dto.setMEMBER_ADD_2(request.getParameter("MEMBER_ADD_2"));
		// 丁目、番地、建物名
		dto.setMEMBER_ADD_3(request.getParameter("MEMBER_ADD_3"));
		// 会員情報修正
		memberdao.updateMember(dto);
		// 修正完了メッセージを表示し、会員情報修正画面に遷移する。
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('会員情報を変更しました。');");
		out.println("location.href='./MemberModifyAction_1.member';");
		out.println("</script>");
		out.close();
		return forward;
	}
	// 会員退会処理
	public ForwardService MemberDeleteAction(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		ForwardService forward=new ForwardService();
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		//セッションにログイン情報が存在しない場合、ログイン画面に遷移する。
		if(id==null){
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;
		}
		MemberDAO memberdao=new MemberDAO();
		String pass=request.getParameter("MEMBER_PW");
		
		try{
			//会員情報削除
			int check=memberdao.deleteMember(id, pass);
			//会員情報削除成功
			if(check == 1){
				session.invalidate();
				forward.setPath("./member/member_out_ok.jsp"); 
			}
			//会員情報削除失敗、エラーメッセージを表示
			else{
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('パスワードが一致していません。');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				forward=null;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return forward;
	}
	// アカウント確認処理
	public ForwardService MemberIDCheckAction(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		ForwardService forward=new ForwardService();
		String id=request.getParameter("MEMBER_ID");
		MemberDAO memberdao=new MemberDAO();
		//ID存在チェック
		int check=memberdao.confirmId(id);	
		request.setAttribute("id", id);
		request.setAttribute("check", check);
		//アカウント確認画面に遷移する。
		forward.setPath("./member/member_idchk.jsp"); 			
		return forward;
	}
	// 会員情報探す処理
	public ForwardService MemberFindAction(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		request.setCharacterEncoding("UTF-8");		
		ForwardService forward=new ForwardService();
		MemberDAO memberdao=new MemberDAO();
		MemberDTO member=new MemberDTO();
		
		//パラメータチェック
		List<String> errorMsg = memberFindParameterCheck(request);	
		
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
		
		//氏名
		String name=request.getParameter("MEMBER_NAME");
		//氏名（カナ）
		String nameKana=request.getParameter("MEMBER_NAME_KANA");
		//電話番号
		String tel=request.getParameter("MEMBER_TEL");
		//メールアドレス
		String mail=request.getParameter("MEMBER_MAIL1")+"@"+request.getParameter("MEMBER_MAIL2");
		//会員情報を取得
		member= memberdao.findId(name, nameKana, tel, mail);	
		//会員情報が存在する場合
		if(member!=null){
			//アカウント確認画面に遷移する。
			request.setAttribute("id", member.getMEMBER_ID());
			request.setAttribute("passwd", member.getMEMBER_PW());			
			forward.setRedirect(false);
			forward.setPath("./member/member_find_ok.jsp");
		}else{
			//会員情報が存在しない場合
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('一致するアカウントが存在していません。');");
			out.println("history.book();");
			out.println("</script>");
			out.close();
			forward=null;
		}		 
		return forward;
	}
	// 郵便番号検索処理
	public ForwardService MemberZipcodeAction(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		request.setCharacterEncoding("UTF-8");
		ForwardService forward=new ForwardService();
		MemberDAO memberdao=new MemberDAO();
		List<String> zipcodeList=new ArrayList<>();
		String searchZipcode=request.getParameter("zipcode");
		//郵便番号情報を取得
		zipcodeList=memberdao.searchZipcode(searchZipcode);
		//郵便番号検索画面に遷移する。
		request.setAttribute("zipcodelist", zipcodeList);
		forward.setPath("./member/member_zipcode.jsp"); 
		return forward;
	}
	//パラメータチェック（ログイン処理）
	public List<String> loginParameterCheck(HttpServletRequest request) {
		List<String> errorMsg = new ArrayList<String>();
		String id = request.getParameter("MEMBER_ID");
		String password = request.getParameter("MEMBER_PW");

		if (id == null || id.isEmpty()) {// 必須チェック（アカウント）
			errorMsg.add("アカウントを入力してください。");
		}

		if (password == null || password.isEmpty()) {// 必須チェック（パスワード）
			errorMsg.add("パスワードを入力してください。");
		}

		return errorMsg;
	}
	//パラメータチェック（ 会員登録処理）	
	public List<String> joinParameterCheck(HttpServletRequest request) {
		List<String> errorMsg = new ArrayList<String>();
		String id = request.getParameter("MEMBER_ID");
		String password1 = request.getParameter("MEMBER_PW1");
		String password2 = request.getParameter("MEMBER_PW2");
		String name = request.getParameter("MEMBER_NAME");
		String nameKana = request.getParameter("MEMBER_NAME_KANA");
		String tel = request.getParameter("MEMBER_TEL");
		String mail = request.getParameter("MEMBER_MAIL1") + "@" + request.getParameter("MEMBER_MAIL2");
		String zipCode = request.getParameter("MEMBER_ZIPCODE");
		String add1 = (request.getParameter("MEMBER_ADD_1"));
		String add2 = request.getParameter("MEMBER_ADD_2");
		String add3 = request.getParameter("MEMBER_ADD_3");

		if (id == null || id.isEmpty()) {// 必須チェック（アカウント）
			errorMsg.add("アカウントを入力してください。");
		} else if (id.length() > 32) {// 桁数チェック（アカウント）
			errorMsg.add("アカウントは32文字以内まで入力してください。");
		}

		if (password1 == null || password1.isEmpty()) {// 必須チェック（パスワード）
			errorMsg.add("パスワードを入力してください。");
		} else if (password1.length() > 64) {// 桁数チェック（パスワード）
			errorMsg.add("パスワードは64文字以内まで入力してください。");
		}

		if (password2 == null || password2.isEmpty()) {// 必須チェック（パスワード確認）
			errorMsg.add("パスワード確認を入力してください。");
		} else if (password2.length() > 64) {// 桁数チェック（パスワード確認）
			errorMsg.add("パスワード確認は64文字以内まで入力してください。");
		}

		if (password1 != null && password2 != null && !password1.equals(password2)) {// 一致チェック（パスワード、パスワード確認）
			errorMsg.add("パスワードが一致していません。");
		}

		if (name == null || name.isEmpty()) {// 必須チェック（氏名）
			errorMsg.add("氏名を入力してください。");
		} else if (name.length() > 32) {// 桁数チェック（氏名）
			errorMsg.add("氏名は32文字以内まで入力してください。");
		}

		if (nameKana == null || nameKana.isEmpty()) {// 必須チェック（氏名（カナ））
			errorMsg.add("氏名（カナ）を入力してください。");
		} else if (nameKana.length() > 32) {// 桁数チェック（氏名（カナ））
			errorMsg.add("氏名（カナ）は32文字以内まで入力してください。");
		}

		if (tel == null || tel.isEmpty()) {// 必須チェック（電話番号）
			errorMsg.add("電話番号を入力してください。");
		} else if (tel.length() > 16) {// 桁数チェック（電話番号）
			errorMsg.add("電話番号は16文字以内まで入力してください。");
		}

		if (mail == null || mail.isEmpty()) {// 必須チェック（メールアドレス）
			errorMsg.add("メールアドレスを入力してください。");
		} else if (mail.length() > 128) {// 桁数チェック（メールアドレス）
			errorMsg.add("メールアドレスは128文字以内まで入力してください。");
		}

		if (zipCode == null || zipCode.length() <= 6) {// 必須チェック（郵便番号）
			errorMsg.add("郵便番号を入力してください。");
		}

		if (add1 == null || add1.isEmpty()) {// 必須チェック（都道府県）
			errorMsg.add("都道府県を入力してください。");
		} else if (add1.length() > 128) {// 桁数チェック（都道府県）
			errorMsg.add("都道府県は128文字以内まで入力してください。");
		}

		if (add2 == null || add2.isEmpty()) {// 必須チェック（市区町村）
			errorMsg.add("市区町村を入力してください。");
		} else if (add2.length() > 128) {
			errorMsg.add("市区町村は128文字以内まで入力してください。");
		}

		if (add3 == null || add3.isEmpty()) {// 必須チェック（丁目、番地、建物名）
			errorMsg.add("丁目、番地、建物名を入力してください。");
		} else if (add3.length() > 128) {// 桁数チェック（丁目、番地、建物名）
			errorMsg.add("丁目、番地、建物名は128文字以内まで入力してください。");
		}

		return errorMsg;
	}
	//パラメータチェック（会員情報修正処理）	
	public List<String> modifyMemberParameterCheck(HttpServletRequest request) {
		List<String> errorMsg = new ArrayList<String>();
		String password1 = request.getParameter("MEMBER_PW1");
		String password2 = request.getParameter("MEMBER_PW2");
		String name = request.getParameter("MEMBER_NAME");
		String nameKana = request.getParameter("MEMBER_NAME_KANA");
		String tel = request.getParameter("MEMBER_TEL");
		String mail = request.getParameter("MEMBER_MAIL1") + "@" + request.getParameter("MEMBER_MAIL2");
		String zipCode = request.getParameter("MEMBER_ZIPCODE");
		String add1 = (request.getParameter("MEMBER_ADD_1"));
		String add2 = request.getParameter("MEMBER_ADD_2");
		String add3 = request.getParameter("MEMBER_ADD_3");

		if (password1 == null || password1.isEmpty()) {// 必須チェック（パスワード）
			errorMsg.add("パスワードを入力してください。");
		} else if (password1.length() > 64) {// 桁数チェック（パスワード）
			errorMsg.add("パスワードは64文字以内まで入力してください。");
		}

		if (password2 == null || password2.isEmpty()) {// 必須チェック（パスワード確認）
			errorMsg.add("パスワード確認を入力してください。");
		} else if (password2.length() > 64) {// 桁数チェック（パスワード確認）
			errorMsg.add("パスワード確認は64文字以内まで入力してください。");
		}

		if (password1 != null && password2 != null && !password1.equals(password2)) {// 一致チェック（パスワード、パスワード確認）
			errorMsg.add("パスワードが一致していません。");
		}

		if (name == null || name.isEmpty()) {// 必須チェック（氏名）
			errorMsg.add("氏名を入力してください。");
		} else if (name.length() > 32) {// 桁数チェック（氏名）
			errorMsg.add("氏名は32文字以内まで入力してください。");
		}

		if (nameKana == null || nameKana.isEmpty()) {// 必須チェック（氏名（カナ））
			errorMsg.add("氏名（カナ）を入力してください。");
		} else if (nameKana.length() > 32) {// 桁数チェック（氏名（カナ））
			errorMsg.add("氏名（カナ）は32文字以内まで入力してください。");
		}

		if (tel == null || tel.isEmpty()) {// 必須チェック（電話番号）
			errorMsg.add("電話番号を入力してください。");
		} else if (tel.length() > 16) {// 桁数チェック（電話番号）
			errorMsg.add("電話番号は16文字以内まで入力してください。");
		}

		if (mail == null || mail.isEmpty()) {// 必須チェック（メールアドレス）
			errorMsg.add("メールアドレスを入力してください。");
		} else if (mail.length() > 128) {// 桁数チェック（メールアドレス）
			errorMsg.add("メールアドレスは128文字以内まで入力してください。");
		}

		if (zipCode == null || zipCode.length() <= 6) {// 必須チェック（郵便番号）
			errorMsg.add("郵便番号を入力してください。");
		}

		if (add1 == null || add1.isEmpty()) {// 必須チェック（都道府県）
			errorMsg.add("都道府県を選択してください。");
		} else if (add1.length() > 128) {// 桁数チェック（都道府県）
			errorMsg.add("都道府県は128文字以内まで入力してください。");
		}

		if (add2 == null || add2.isEmpty()) {// 必須チェック（市区町村）
			errorMsg.add("市区町村を選択してください。");
		} else if (add2.length() > 128) {// 桁数チェック（市区町村）
			errorMsg.add("市区町村は128文字以内まで入力してください。");
		}

		if (add3 == null || add3.isEmpty()) {// 必須チェック（丁目、番地、建物名）
			errorMsg.add("丁目、番地、建物名を選択してください。");
		} else if (add3.length() > 128) {// 桁数チェック（丁目、番地、建物名）
			errorMsg.add("丁目、番地、建物名は128文字以内まで入力してください。");
		}

		return errorMsg;
	}
	//パラメータチェック（会員情報探す処理）	
	public List<String> memberFindParameterCheck(HttpServletRequest request) {
		List<String> errorMsg = new ArrayList<String>();
		String name = request.getParameter("MEMBER_NAME");
		String nameKana = request.getParameter("MEMBER_NAME_KANA");
		String tel = request.getParameter("MEMBER_TEL");
		String mail1 = request.getParameter("MEMBER_MAIL1");
		String mail2 = request.getParameter("MEMBER_MAIL2");
		
		if(name == null || name.isEmpty()){//必須チェック（氏名
			errorMsg.add("氏名を入力してください。");
		}
		
		if(nameKana == null || nameKana.isEmpty()){//必須チェック（氏名（カナ））
			errorMsg.add("氏名（カナ）を入力してください。");
		}
		
		if(tel == null || tel.isEmpty()){//必須チェック（電話番号）
			errorMsg.add("TELを入力してください。");
		}
		
		if(mail1 == null || mail1.isEmpty() || mail2 == null || mail2.isEmpty() ){//必須チェック（メールアドレス）
			errorMsg.add("メールアドレスを入力してください。");
		}
		
		return errorMsg;
	}
}