package net.member.action;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.member.db.MemberBean;
import net.member.db.MemberDAO;
public class MemberModifyAction_2 implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		request.setCharacterEncoding("UTF-8");
		
		ActionForward forward=new ActionForward();
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		//セッションにログイン情報が存在しない場合、ログイン画面に遷移する。	
		if(id==null){
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;
		}
		
		//パラメータチェック
		List<String> errorMsg = modifyMemberParameterCheck(request);	
		
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
		
		MemberDAO memberdao=new MemberDAO();
		MemberBean dto=new MemberBean();
		//アカウント
		dto.setMEMBER_ID(id);
		//パスワード
		dto.setMEMBER_PW(request.getParameter("MEMBER_PW1"));
		//氏名
		dto.setMEMBER_NAME(request.getParameter("MEMBER_NAME"));
		//氏名（カナ）
		dto.setMEMBER_NAME_KANA(request.getParameter("MEMBER_NAME_KANA"));
		//電話番号
		dto.setMEMBER_TEL(request.getParameter("MEMBER_TEL"));
		//メールアドレス
		dto.setMEMBER_MAIL(request.getParameter("MEMBER_MAIL1")+"@"+request.getParameter("MEMBER_MAIL2"));
		//郵便番号
		dto.setMEMBER_ZIPCODE(request.getParameter("MEMBER_ZIPCODE"));
		//都道府県
		dto.setMEMBER_ADD_1(request.getParameter("MEMBER_ADD_1"));
		//市区町村
		dto.setMEMBER_ADD_2(request.getParameter("MEMBER_ADD_2"));
		//丁目、番地、建物名
		dto.setMEMBER_ADD_3(request.getParameter("MEMBER_ADD_3"));
		//会員情報修正
		memberdao.updateMember(dto);
		//修正完了メッセージを表示し、会員情報修正画面に遷移する。
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('会員情報を変更しました。');");
		out.println("location.href='./MemberModifyAction_1.member';");
		out.println("</script>");			
		out.close();				
		return forward;
	}
	
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
		
		if(password1 == null || password1.isEmpty()){//必須チェック（パスワード）
			errorMsg.add("パスワードを入力してください。");
		} else if (password1.length() > 64) {//桁数チェック（パスワード）
			errorMsg.add("パスワードは64文字以内まで入力してください。");
		}
		
		if(password2 == null || password2.isEmpty()){//必須チェック（パスワード確認）
			errorMsg.add("パスワード確認を入力してください。");
		} else if (password2.length() > 64) {//桁数チェック（パスワード確認）
			errorMsg.add("パスワード確認は64文字以内まで入力してください。");
		}
		
		if(password1 != null && password2 != null && !password1.equals(password2)) {//一致チェック（パスワード、パスワード確認）
			errorMsg.add("パスワードが一致していません。");
		}
		
		if(name == null || name.isEmpty()){//必須チェック（氏名）
			errorMsg.add("氏名を入力してください。");
		} else if (name.length() > 32) {//桁数チェック（氏名）
			errorMsg.add("氏名は32文字以内まで入力してください。");
		}
		
		if(nameKana == null || nameKana.isEmpty()){//必須チェック（氏名（カナ））
			errorMsg.add("氏名（カナ）を入力してください。");
		} else if (nameKana.length() > 32) {//桁数チェック（氏名（カナ））
			errorMsg.add("氏名（カナ）は32文字以内まで入力してください。");
		}
		
		if(tel == null || tel.isEmpty()){//必須チェック（電話番号）
			errorMsg.add("電話番号を入力してください。");
		} else if (tel.length() > 16) {//桁数チェック（電話番号）
			errorMsg.add("電話番号は16文字以内まで入力してください。");
		}
		
		if(mail == null || mail.isEmpty()){//必須チェック（メールアドレス）
			errorMsg.add("メールアドレスを入力してください。");
		} else if (mail.length() > 128) {//桁数チェック（メールアドレス）
			errorMsg.add("メールアドレスは128文字以内まで入力してください。");
		}
		
		if(zipCode == null || zipCode.length() <= 6){//必須チェック（郵便番号）
			errorMsg.add("郵便番号を入力してください。");
		}
		
		if(add1 == null || add1.isEmpty()){//必須チェック（都道府県）
			errorMsg.add("都道府県を選択してください。");
		} else if (add1.length() > 128) {//桁数チェック（都道府県）
			errorMsg.add("都道府県は128文字以内まで入力してください。");
		}
		
		if(add2 == null || add2.isEmpty()){//必須チェック（市区町村）
			errorMsg.add("市区町村を選択してください。");
		} else if (add2.length() > 128) {//桁数チェック（市区町村）
			errorMsg.add("市区町村は128文字以内まで入力してください。");
		}
		
		if(add3 == null || add3.isEmpty()){//必須チェック（丁目、番地、建物名）
			errorMsg.add("丁目、番地、建物名を選択してください。");
		} else if (add3.length() > 128) {//桁数チェック（丁目、番地、建物名）
			errorMsg.add("丁目、番地、建物名は128文字以内まで入力してください。");
		}
		
		return errorMsg;
	}
}