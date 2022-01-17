package net.member.action;
import java.io.PrintWriter;
import java.sql.Timestamp;
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
		dto.setMEMBER_ID(request.getParameter("MEMBER_ID"));
		dto.setMEMBER_PW(request.getParameter("MEMBER_PW1"));
		dto.setMEMBER_NAME(request.getParameter("MEMBER_NAME"));
		dto.setMEMBER_NAME_KANA(request.getParameter("MEMBER_NAME_KANA"));
		dto.setMEMBER_TEL(request.getParameter("MEMBER_TEL"));
		dto.setMEMBER_MAIL(request.getParameter("MEMBER_MAIL1")+"@"+request.getParameter("MEMBER_MAIL2"));
		dto.setMEMBER_ZIPCODE(request.getParameter("MEMBER_ZIPCODE"));
		dto.setMEMBER_ADD_1(request.getParameter("MEMBER_ADD_1"));
		dto.setMEMBER_ADD_2(request.getParameter("MEMBER_ADD_2"));
		dto.setMEMBER_ADD_3(request.getParameter("MEMBER_ADD_3"));	
		memberdao.insertMember(dto);		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('会員登録が完了しました。');");
		out.println("location.href='./MemberLogin.member';");
		out.println("</script>");			
		out.close();	
		return forward;
	}
}