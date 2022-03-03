package net.cart.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 public class CartFrontController extends javax.servlet.http.HttpServlet 
 		implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   protected void doProcess(HttpServletRequest request, HttpServletResponse response) 
   throws ServletException, IOException {
	   String RequestURI=request.getRequestURI();
	   String contextPath=request.getContextPath();
	   String command=RequestURI.substring(contextPath.length());
	   ActionForward forward=null;
	   Action action=null;
	   //カート画面
	   if(command.equals("/CartList.cart")){
		   action  = new CartListAction();
		   try {
			   forward=action.execute(request, response );
		   } catch (Exception e) {
			   e.printStackTrace();
		   }
	   }
	   //カート情報登録処理
	   else if(command.equals("/CartAdd.cart")){
		   action  = new CartAddAction();
		   try {
			   forward=action.execute(request, response );
		   } catch (Exception e) {
			   e.printStackTrace();
		   }
	   }
	   //カート情報削除処理
	   else if(command.equals("/CartDelete.cart")){
		   action=new CartDeleteAction();
		   try {
			   forward=action.execute(request, response );
		   } catch (Exception e) {
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