package net.member.action;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.member.db.MemberDAO;

public class MemberLoginAction implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session=request.getSession();
		ActionForward forward=new ActionForward();
		MemberDAO memberdao=new MemberDAO();
		
		//パラメータチェック
		List<String> errorMsg = loginParameterCheck(request);	
		
		//チェック結果が「エラー」の場合
		if(errorMsg.size() != 0) {
			String error = "";
			for(String msg : errorMsg) {
				error = error + msg + "\\n";
			}
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+ error +"')");
			out.println("history.back()");
			out.println("</script>");
			return null;				
		}
		
		String id=request.getParameter("MEMBER_ID");
		String pass=request.getParameter("MEMBER_PW");
		//ログイン情報チェック
		int check=memberdao.userCheck(id, pass);
		//チェック結果が「成功」の場合
		if(check == 1){
			session.setAttribute("id", id);
			//会員種別が「０:管理者」の場合
  			if(memberdao.isAdmin(id)){
  				//図書リスト（管理者）画面に遷移する。
  				session.setAttribute("memberType", 0); //管理者
				forward.setRedirect(true);
				forward.setPath("./BookList.adbook"); 
				return forward;
			}else{
				//会員種別が「１:一般ユーザー」の場合
				session.setAttribute("memberType", 1); //一般ユーザー
				
				String redirectURL = (String)session.getAttribute("redirectURI");
				//遷移元がない場合
				if(redirectURL == null) {
					forward.setRedirect(true);
					forward.setPath("./BookList.book"); 
				} else {
					//遷移元がある場合、遷移元に遷移する。
					forward.setRedirect(true);
					redirectURL =redirectURL.substring(redirectURL.lastIndexOf("/"));
					forward.setPath("."+redirectURL);
					session.removeAttribute("redirectURI");
				}
				return forward;
			}
		}
		//チェック結果が「失敗：パスワード未一致」の場合、エラーメッセージを表示
		else if(check == 0){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('パスワードが一致していません。');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}else{
			//チェック結果が「失敗：一致するアカウントが無い」の場合、エラーメッセージを表示
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
	
	public List<String> loginParameterCheck(HttpServletRequest request) {
		List<String> errorMsg = new ArrayList<String>();
		String id = request.getParameter("MEMBER_ID");
		String password = request.getParameter("MEMBER_PW");
		
		if (id == null || id.isEmpty()) {//必須チェック（アカウント）
			errorMsg.add("アカウントを入力してください。");
		}
		
		if (password == null || password.isEmpty()) {//必須チェック（パスワード）
			errorMsg.add("パスワードを入力してください。");
		}
		
		return errorMsg;
	}
}