package net.admin.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.member.db.AdminMemberDAO;
import net.member.db.MemberBean;

public class AdminMemberModifyAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		AdminMemberDAO memberdao=new AdminMemberDAO();
		MemberBean member=new MemberBean();
		
		boolean result=false;
		request.setCharacterEncoding("UTF-8");
		member.setMEMBER_ID(request.getParameter("id"));
		member.setMEMBER_NAME(request.getParameter("MEMBER_NAME"));
		member.setMEMBER_NAME_KANA(request.getParameter("MEMBER_NAME_KANA"));
		member.setMEMBER_TEL(request.getParameter("MEMBER_TEL"));
		member.setMEMBER_MAIL(request.getParameter("MEMBER_MAIL"));
		member.setMEMBER_ZIPCODE(request.getParameter("MEMBER_ZIPCODE"));
		member.setMEMBER_ADD_1(request.getParameter("MEMBER_ADD_1"));
		member.setMEMBER_ADD_2(request.getParameter("MEMBER_ADD_2"));
		member.setMEMBER_ADD_3(request.getParameter("MEMBER_ADD_3"));
		member.setMEMBER_TYPE(Integer.parseInt(request.getParameter("MEMBER_TYPE")));
		result=memberdao.modifyMember(member);
		if(result==false){
			System.out.println("会員情報修正エラー");
			return null;
		}
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./AdminMemberList.admember");
		return forward;
	 } 
}