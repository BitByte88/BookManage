package net.admin.member.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.member.db.AdminMemberDAO;
import net.member.db.MemberBean;

public class AdminMemberDetailAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		AdminMemberDAO memberdao=new AdminMemberDAO();
		MemberBean member =new MemberBean();
		
		String id=request.getParameter("id");
		member = memberdao.getMemberDetail(id);
		request.setAttribute("member", member);
		
		ActionForward forward=new ActionForward();
		forward.setPath("./adminMember/admin_member_modify.jsp");
		return forward;
	 } 
}