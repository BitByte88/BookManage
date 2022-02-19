package net.admin.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.member.db.AdminMemberDAO;

public class AdminMemberDeleteAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		AdminMemberDAO memberdao=new AdminMemberDAO();
		
		boolean result=false;
		String id=request.getParameter("id");
		result=memberdao.deleteMember(id);
		
		if(result==false){
			System.out.println("会員情報削除エラー");
			return null;
		}
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./AdminMemberList.admember");
		return forward;
	 } 
}