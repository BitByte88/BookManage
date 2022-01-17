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
		
		if(id==null){
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;
		}
		
		MemberDAO memberdao=new MemberDAO();
		MemberBean dto=new MemberBean();
		
		dto.setMEMBER_ID(id);
		dto.setMEMBER_PW(request.getParameter("MEMBER_PW"));
		dto.setMEMBER_NAME(request.getParameter("MEMBER_NAME"));
		dto.setMEMBER_NAME_KANA(request.getParameter("MEMBER_NAME_KANA"));
		dto.setMEMBER_TEL(request.getParameter("MEMBER_TEL"));
		dto.setMEMBER_MAIL(request.getParameter("MEMBER_MAIL1")+"@"+request.getParameter("MEMBER_MAIL2"));
		dto.setMEMBER_ZIPCODE(request.getParameter("MEMBER_ZIPCODE"));
		dto.setMEMBER_ADD_1(request.getParameter("MEMBER_ADD_1"));
		dto.setMEMBER_ADD_2(request.getParameter("MEMBER_ADD_2"));
		dto.setMEMBER_ADD_3(request.getParameter("MEMBER_ADD_3"));
		memberdao.updateMember(dto);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('ȸ������ ������ �����Ͽ����ϴ�.');");
		out.println("</script>");
		forward.setPath("./MemberModifyAction_1.member"); 			
		return forward;
	}
}