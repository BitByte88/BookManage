<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	int nowpage=1;
	if(request.getParameter("page")!=null){
		nowpage=Integer.parseInt(request.getParameter("page"));
	}
	int count=((Integer)(request.getAttribute("count"))).intValue();
	int pageCount=((Integer)(
			request.getAttribute("pageCount"))).intValue();
	int startPage=((Integer)(
			request.getAttribute("startPage"))).intValue();
	int endPage=((Integer)(
			request.getAttribute("endPage"))).intValue();
%>

<html>
<head>
<title>図書管理システム</title>
<script>
function check(){
	var title=searchform.title.value;
	var publisher=searchform.publisher.value;
	var startDate=searchform.startDate.value;
	var endDate=searchform.endDate.value;
	
	if(title.length == 0 && publisher.length == 0 && startDate.length == 0 && endDate.length == 0){
		alert("入力内容が存在しません。");
		return false;
	}
	
	return true;
}
</script>
</head>
<body>		
	<table width="960" cellspacing="0" cellpadding="0" border="0" 
		align="center">
	<tr>
	<td>
		<form action="./BookList.book" method=post name=searchform onsubmit="return check()" accept-charset="UTF-8">
			<table width="700" cellspacing="0" cellpadding="0" border="0" align="center">
			<tr>
			<td>タイトル：</td>
			<td><input type="text" name="title" maxlength="20" size="20" value="${search.BOOK_NAME}"></td>
			</tr>
			<tr>
			<td>出版社 ：</td>
			<td><input type="text" name="publisher" maxlength="20" size="20" value="${search.BOOK_PUBLISHER}"></td>
			</tr>
			<tr>
			<td>出版日時：</td>
			<td><input type="text" name="startDate" placeholder="2022/01/01" maxlength="10" size="10" value="${search.START_DATE}"> - <input type="text" placeholder="2022/01/31" name="endDate" maxlength="10" size="10" value="${search.END_DATE}">			</td>
			</tr>
			<tr>
			<td></td>
			<td><input style="float: right;" type="submit" value="検索"></td>
			</tr>
			</table>
		</form>
	</td>
	</tr>
	<tr>
	<td colspan=2>
	<table width=700 height="460" border="0" align="center">	
		<tr>
		<td valign="top">
		<!-- 図書リスト -->
		<table border="0">
		<tr>	   
		   <c:choose> 
				<c:when test=
					"${requestScope.itemList[0].BOOK_NO==null}">
					<tr>
					<td width="700" height="300" align="center">
					登録された本がありません。
					</td>
					</tr>
				</c:when>
				
				<c:otherwise> 
				<c:forEach var="item" items="${requestScope.itemList}">
				<td width="180" valign="top" >
				<br>
				<div align="center">
				<a href="Book_Detail.book?&gr_book_no=${item.BOOK_NO}">
			 	<img src="./upload/${fn:trim(item.BOOK_IMAGE)}" 
			 		width="130" height="130" border="0"/>
			 	<br/>${item.BOOK_NAME}<br/>
				</a>
				<br/><b>${item.BOOK_PRICE}円</b>
				</div>
				<br>
				</td>
				</c:forEach>
				</c:otherwise>
			</c:choose>
		</tr>
		</table>
		<!-- 図書リスト -->
		</td>
		</tr>
		<tr>
		<td height="20" colspan="4" align="center">		
		<!-- 페이징 -->
		<%
		if (count>0) {
			if (startPage>10) { %>
			<a href="BookList.book?page=<%=startPage-1%>">[前へ]</a>
			<% }
			for (int i=startPage;i<=endPage ; i++) { 
				if (i==nowpage) {   %>
				<font color=red>[<%=i%>]</font>
				<% } else { %>  
					<a href="BookList.book?page=<%=i%>">[<%=i%>]</a>
					<%
			 		 }
				}
			if (endPage<pageCount) { %>
			<a href="BookList.book?page=<%=endPage+1%>">[次へ]</a>
			<%
			}
		}
		%>			
		<!-- ページング -->
		</td>
		</tr>
		</table>
		</td>
	</tr>
	</table>
</body>
</html>