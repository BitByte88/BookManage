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
		//会員情報削除
		result=memberdao.deleteMember(id);
		//会員情報削除結果が「失敗」の場合
		if(result==false){
			System.out.println("会員情報削除エラー");
			return null;
		}
		//会員リスト画面に遷移する。
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./AdminMemberList.admember");
		return forward;
	 } 
}