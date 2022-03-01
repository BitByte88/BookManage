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
		//会員情報を取得する。
		member = memberdao.getMemberDetail(id);
		request.setAttribute("member", member);
		//会員情報修正画面に遷移する。
		ActionForward forward=new ActionForward();
		forward.setPath("./adminMember/admin_member_modify.jsp");
		return forward;
	 } 
}