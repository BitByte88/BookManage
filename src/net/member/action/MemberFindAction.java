package net.member.action;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.member.db.MemberBean;
import net.member.db.MemberDAO;
public class MemberFindAction implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		request.setCharacterEncoding("UTF-8");		
		ActionForward forward=new ActionForward();
		MemberDAO memberdao=new MemberDAO();
		MemberBean member=new MemberBean();		
		String name=request.getParameter("MEMBER_NAME");
		String nameKana=request.getParameter("MEMBER_NAME_KANA");
		String tel=request.getParameter("MEMBER_TEL");
		String mail=request.getParameter("MEMBER_MAIL");
		member= memberdao.findId(name, nameKana, tel, mail);		
		if(member!=null){
			request.setAttribute("id", member.getMEMBER_ID());
			request.setAttribute("passwd", member.getMEMBER_PW());			
			forward.setRedirect(false);
			forward.setPath("./member/member_find_ok.jsp"); 			
		}else{
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�Է��� ������ ��ġ���� �ʽ��ϴ�.');");
			out.println("history.book(-1);");
			out.println("</script>");
			out.close();
			forward=null;
		}		 
		return forward;
	}
}