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
		//1ページに表示する会員数
		int limit=50;
		
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		//すべての会員情報件数取得
		int membercount=memberdao.getMemberCount();
		//会員リスト取得
		memberlist = memberdao.getMemberList(page,limit);
		//ページング
	   	int maxpage=(int)((double)membercount/limit+0.95);
	   	int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
	   	int endpage = maxpage;
	   	if (endpage>startpage+10-1) endpage=startpage+10-1;
	   	//現在ページ
	   	request.setAttribute("page", page);
	   	//最後のページ
	   	request.setAttribute("maxpage", maxpage);
	   	//ページングに最初に表示されるページ番号
	   	request.setAttribute("startpage", startpage);
	   	//ページングに最後に表示されるページ番号
	   	request.setAttribute("endpage", endpage);
	   	//すべての会員情報件数
		request.setAttribute("membercount", membercount);
		//会員リスト
		request.setAttribute("memberlist", memberlist);
		//会員リスト画面に遷移する。
		ActionForward forward=new ActionForward();
		forward.setPath("./adminMember/admin_member_list.jsp");
		return forward;
	 } 
}