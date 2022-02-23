package net.admin.member.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.member.db.AdminMemberDAO;
import net.member.db.MemberBean;

public class AdminMemberListAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{		
		AdminMemberDAO memberdao=new AdminMemberDAO();
		List<MemberBean> memberlist = new ArrayList<MemberBean>();
		int page=1;
		int limit=50;
		
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		int membercount=memberdao.getMemberCount();
		memberlist = memberdao.getMemberList(page,limit);
		
	   	int maxpage=(int)((double)membercount/limit+0.95);
	   	int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
	   	int endpage = maxpage;
	   	if (endpage>startpage+10-1) endpage=startpage+10-1;
	   	
	   	request.setAttribute("page", page);
	   	request.setAttribute("maxpage", maxpage);
	   	request.setAttribute("startpage", startpage);
	   	request.setAttribute("endpage", endpage);
		request.setAttribute("membercount", membercount);
		request.setAttribute("memberlist", memberlist);
		ActionForward forward=new ActionForward();
		forward.setPath("./adminMember/admin_member_list.jsp");
		return forward;
	 } 
}