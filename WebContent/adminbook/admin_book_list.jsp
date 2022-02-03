<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="net.admin.book.db.*"%>
<%
	ArrayList list = (ArrayList) request.getAttribute("list");
    BookBean abb = null;
%>
<html>
<head>
	<link rel="icon" href="favicon/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="favicon/favicon.ico" type="image/x-icon"> 
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="favicon/favicon-144x144.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="favicon/favicon-72x72.png">
	<link rel="apple-touch-icon-precomposed" href="favicon/favicon-54x54.png">
	<jsp:include page="/menu/menu.jsp" />
	
<title>図書管理システム</title>
<script type="text/javascript">
function bookdelete(book_num){
	document.bookform.action="./BookDelete.adbook?book_num="+book_num;
	document.bookform.submit();	
}
function bookmodify(book_num){
	document.bookform.action="./BookModify.adbook?book_num="+book_num;
	document.bookform.submit();	
}
</script>
</head>
<body>
<div name="main" style="margin:30 0 50 0">
<table width="960" cellspacing="0" cellpadding="0" border="0"
	bordercolor="gray" align="center">
	<tr>
		<td colspan=2>
		<!-- 상품 목록 -->
		<table border="0" width="80%">
			<tr>
				<td height="22" bgcolor="#6699FF">
					<p align="center">
						<font size=2>상품목록</font>
					</p>
				</td>
			</tr>
			<tr>
				<td>
				<p align="right">
					<font size=2>
						<a href="./BookAdd.adbook">상품등록</a>
					</font>
				</p>
				</td>
			</tr>
			<tr>
			<td>
			<form name=bookform method="post">
			<table border="1">
			<tr>
				<td width="50">
				<p align="center"><font size=2>번호</font></p>
				</td>
				<td width="141">
				<p align="center"><font size=2>카테고리</font></p>
				</td>
				<td width="100">
				<p align="center"><font size=2>사진</font></p>
				</td>
				<td width="141">
				<p align="center"><font size=2>상품명</font></p>
				</td>
				<td width="141">
				<p align="center"><font size=2>단가</font></p>
				</td>
				<td width="80">
				<p align="center"><font size=2>수량</font></p>
				</td>
				<td width="141">
				<p align="center"><font size=2>등록일자</font></p>
				</td>
				<td width="100">
				<p align="center"><font size=2>&nbsp;</font></p>
				</td>
			</tr>
			<%
					for (int i = 0; i < list.size(); i++) {
					abb = (BookBean) list.get(i);
			%>
			<tr>
			<td>
			<p align="center">
				<font size=2><%=abb.getBOOK_NUM()%></font>
			</p>
			</td>
			<td>
			<p align="center">
				<font size=2>
					<%=abb.getBOOK_CATEGORY()%>
				</font>
			</p>
			</td>
			<td>
			<p align="center"><img
			src="./upload/<%=abb.getBOOK_IMAGE().split(",")[0] %>"
			width="50" height="50" border="0"></p>
			</td>
			<td>
			<p align="center">
				<font size=2><%=abb.getBOOK_NAME()%></font>
			</p>
			</td>
			<td>
			<p align="center">
				<font size=2><%=abb.getBOOK_PRICE()%></font>
			</p>
			</td>
			<td>
			<p align="center">
				<font size=2><%=abb.getBOOK_AMOUNT()%></font>
			</p>
			</td>
			<td>
			<p align="center">
				<font size=2>
					<%=abb.getBOOK_DATE().substring(0,10) %>
				</font>
			</p>
			</td>
			<td>
			<p align="center">
			<a href="javascript:bookmodify(<%=abb.getBOOK_NUM()%>);">
				<font size=2>수정</font>
			</a>/
			<a href="javascript:bookdelete(<%=abb.getBOOK_NUM()%>);">
				<font size=2>삭제</font>
			</a>
			</p>
			</td>
			</tr>
			<%
				}
			%>
			</table>
			</td>
			</tr>
			</form>
		</table>
		<!-- 상품 목록 -->
		</td>
	</tr>
</table>
</div>
</body>
</html>