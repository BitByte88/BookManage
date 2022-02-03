package net.member.action;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberFrontController extends HttpServlet{
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		 String RequestURI=request.getRequestURI();
		 String contextPath=request.getContextPath();
		 String command=RequestURI.substring(contextPath.length());
		 ActionForward forward=null;
		 Action action=null;		 
		 if(command.equals("/MemberLogin.member")){
			 forward=new ActionForward();
			 forward.setPath("./member/member_login.jsp");
		 }else if(command.equals("/MemberJoin.member")){
			 forward=new ActionForward();
			 forward.setPath("./member/member_join.jsp");
		 }else if(command.equals("/MemberFind.member")){
			 forward=new ActionForward();
			 forward.setPath("./member/member_find.jsp");
		 }else if(command.equals("/MemberOut.member")){
			 forward=new ActionForward();
			 forward.setPath("./member/member_out.jsp");
		 }else if(command.equals("/Zipcode.member")){
			 forward=new ActionForward();
			 forward.setPath("./member/member_zipcode.jsp");
		 }else if(command.equals("/MemberLoginAction.member")){
			 action  = new MemberLoginAction();
			 try {
				 forward=action.execute(request, response);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
		 }else if(command.equals("/MemberJoinAction.member")){
			 action  = new MemberJoinAction();
			 try {
				 forward=action.execute(request, response);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
		 }else if(command.equals("/MemberModifyAction_1.member")){
			 action  = new MemberModifyAction_1();
			 try {
				 forward=action.execute(request, response);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
		 }else if(command.equals("/MemberModifyAction_2.member")){
			 action  = new MemberModifyAction_2();
			 try {
				 forward=action.execute(request, response);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
		 }else if(command.equals("/MemberDeleteAction.member")){
			 action  = new MemberDeleteAction();
			 try {
				 forward=action.execute(request, response);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
		 }else if(command.equals("/MemberIDCheckAction.member")){
			 action  = new MemberIDCheckAction();
			 try {
				 forward=action.execute(request, response);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
		 }else if(command.equals("/MemberFindAction.member")){
			 action  = new MemberFindAction();
			 try {
				 forward=action.execute(request, response);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
		 }else if(command.equals("/MemberZipcodeAction.member")){
			 action  = new MemberZipcodeAction();
			 try {
				 forward=action.execute(request, response);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
		 }else if(command.equals("/MemberLogout.member")){
			 HttpSession sesseion=request.getSession(true);
			 sesseion.removeAttribute("id");
			 sesseion.removeAttribute("memberType");
			 sesseion.removeAttribute("member");
			 forward=new ActionForward();
			 forward.setPath("./member/member_login.jsp");
		 }
		 if(forward != null){
			 if(forward.isRedirect()){
				 response.sendRedirect(forward.getPath());
			 }else{
				 RequestDispatcher dispatcher=
					 request.getRequestDispatcher(forward.getPath());
				 dispatcher.forward(request, response);
			 }	 
		 }
	}
}