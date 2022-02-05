package net.member.action;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.member.db.MemberDAO;

public class MemberLoginAction implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session=request.getSession();
		ActionForward forward=new ActionForward();
		MemberDAO memberdao=new MemberDAO();		
		String id=request.getParameter("MEMBER_ID");
		String pass=request.getParameter("MEMBER_PW");		
		int check=memberdao.userCheck(id, pass);
		if(check == 1){
			session.setAttribute("id", id);
  			if(memberdao.isAdmin(id)){
  				session.setAttribute("memberType", 0); //管理者
				forward.setRedirect(true);
				forward.setPath("./BookList.adbook"); 
				return forward;
			}else{
				session.setAttribute("memberType", 1); //一般ユーザー
				
				String redirectURL = (String)session.getAttribute("redirectURI");
				if(redirectURL == null) {
					forward.setRedirect(true);
					forward.setPath("./BookList.book"); 
				} else {
					forward.setRedirect(true);
					redirectURL =redirectURL.substring(redirectURL.lastIndexOf("/"));
					forward.setPath("."+redirectURL);
					session.removeAttribute("redirectURI");
				}
				return forward;
			}
		}else if(check == 0){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('パスワードが一致していません。');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}else{
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('アカウントが登録されていません。');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		return null;
	}
}