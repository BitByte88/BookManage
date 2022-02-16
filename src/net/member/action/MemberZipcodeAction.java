package net.member.action;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.member.db.MemberDAO;
public class MemberZipcodeAction implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		request.setCharacterEncoding("UTF-8");
		ActionForward forward=new ActionForward();
		MemberDAO memberdao=new MemberDAO();
		List<String> zipcodeList=new ArrayList<>();
		String searchZipcode=request.getParameter("zipcode");
		zipcodeList=memberdao.searchZipcode(searchZipcode);
		request.setAttribute("zipcodelist", zipcodeList);
		forward.setPath("./member/member_zipcode.jsp"); 
		return forward;
	}
}