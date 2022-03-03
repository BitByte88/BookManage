package net.member.action;
import java.io.PrintWriter;
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
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('会員情報を変更しました。');");
		out.println("location.href='./MemberModifyAction_1.member';");
		out.println("</script>");			
		out.close();				
		return forward;
	}
}