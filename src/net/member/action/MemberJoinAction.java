package net.member.action;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.member.db.MemberBean;
import net.member.db.MemberDAO;
public class MemberJoinAction implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
	throws Exception{
		request.setCharacterEncoding("UTF-8");		
		MemberDAO memberdao=new MemberDAO();
		MemberBean dto=new MemberBean();
		ActionForward forward=null;		
		//アカウント
		dto.setMEMBER_ID(request.getParameter("MEMBER_ID"));
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
		//会員情報登録処理
		memberdao.insertMember(dto);		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		//登録完了メッセージを表示し、ログイン画面に遷移する。
		out.println("<script>");
		out.println("alert('会員登録が完了しました。');");
		out.println("location.href='./MemberLogin.member';");
		out.println("</script>");			
		out.close();	
		return forward;
	}
}