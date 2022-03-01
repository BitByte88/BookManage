package net.admin.member.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminMemberFrontController extends javax.servlet.http.HttpServlet 
 	implements javax.servlet.Servlet {
	private static final long serialVersionUID = -4826023681180085537L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		   String RequestURI=request.getRequestURI();
		   String contextPath=request.getContextPath();
		   String command=RequestURI.substring(contextPath.length());
		   ActionForward forward=null;
		   Action action=null;
		   //会員リスト画面
		   if(command.equals("/AdminMemberList.admember")){
			   action  = new AdminMemberListAction();
			   try {
				   forward=action.execute(request, response );
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
		   }
		   //会員情報修正画面
		   else if(command.equals("/AdminMemberDetail.admember")){
			   action = new AdminMemberDetailAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   //会員情報修正処理
		   else if(command.equals("/AdminMembereModify.admember")){
			   action = new AdminMemberModifyAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   //会員情報削除処理
		   else if(command.equals("/AdminMemberDelete.admember")){
			   action = new AdminMemberDeleteAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
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
	 
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		 doProcess(request,response);
	 }  	
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		 doProcess(request,response);
	 }   	  	    
}