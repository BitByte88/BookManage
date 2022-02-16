package net.admin.order.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminOrderFrontController extends javax.servlet.http.HttpServlet 
 	implements javax.servlet.Servlet {
	private static final long serialVersionUID = -4826023681180085537L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		   String RequestURI=request.getRequestURI();
		   String contextPath=request.getContextPath();
		   String command=RequestURI.substring(contextPath.length());
		   ActionForward forward=null;
		   Action action=null;
		   
		   if(command.equals("/AdminOrderList.adorder")){
			   action  = new AdminOrderListAction();
			   try {
				   forward=action.execute(request, response );
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
		   }else if(command.equals("/AdminOrderDetail.adorder")){
			   action = new AdminOrderDetailAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }else if(command.equals("/AdminOrderModify.adorder")){
			   action = new AdminOrderModifyAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }else if(command.equals("/AdminOrderDelete.adorder")){
			   action = new AdminOrderDeleteAction();
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