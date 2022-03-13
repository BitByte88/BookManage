<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.RoundingMode"%>
<%@ page import="net.book.db.*"%>
<%
@SuppressWarnings("unchecked") ArrayList<BookDTO> list = (ArrayList<BookDTO>) request.getAttribute("list");
    BookDTO abb = null;
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
function bookmodify(book_no){
	document.bookform.action="./BookModify.adbook?book_no="+book_no;
	document.bookform.submit();	
}
function bookdelete(book_no){
	document.bookform.action="./BookDelete.adbook?book_no="+book_no;
	document.bookform.submit();	
}
</script>
</head>
<body>
<div style="margin:30 0 50 0">
<table style="margin:auto; width:1000">
	<tr>
		<td colspan=2>
		<!-- 図書リスト -->
		<table style="width:100%">
			<tr>
				<td height="26" bgcolor="94d7e7">
					<p align="center">
						<font size=4>図書リスト</font>
					</p>
				</td>
			</tr>
			<tr>
				<td>
				<p align="right">
					<font size=2>
						<a href="./BookAdd.adbook">図書登録</a>
					</font>
				</p>
				</td>
			</tr>
			<tr>
			<td>
			<form name=bookform method="post">
			<table border="1" style="width:100%">
			<tr bgcolor="#EFEFEF">
				<td width="6%">
				<p align="center"><font size=2>図書番号</font></p>
				</td>
				<td width="15%">
				<p align="center"><font size=2>カテゴリー</font></p>
				</td>
				<td width="10%">
				<p align="center"><font size=2>画像</font></p>
				</td>
				<td width="24%">
				<p align="center"><font size=2>書名</font></p>
				</td>
				<td width="12.5%">
				<p align="center"><font size=2>価格</font></p>
				</td>
				<td width="12.5%">
				<p align="center"><font size=2>著者</font></p>
				</td>
				<td width="10%">
				<p align="center"><font size=2>発行日</font></p>
				</td>
				<td width="10%">
				<p align="center"><font size=2>&nbsp;</font></p>
				</td>
			</tr>
			<%
			for (int i = 0; i < list.size(); i++) {
						abb = (BookDTO) list.get(i);
			%>
			<tr>
			<td>
			<p align="center">
				<font size=2><%=abb.getBOOK_NO()%></font>
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
				<font size=2><%=abb.getBOOK_PRICE()%>円</font>
			</p>
			</td>
			<td>
			<p align="center">
				<font size=2><%=abb.getBOOK_WRITER()%></font>
			</p>
			</td>
			<td>
			<p align="center">
				<font size=2>
					<%=abb.getBOOK_PUBLISHING_DATE().toString().substring(0,10) %>
				</font>
			</p>
			</td>
			<td>
			<p align="center">
			<a href="javascript:bookmodify(<%=abb.getBOOK_NO()%>);">
				<font size=2>変更</font>
			</a>/
			<a href="javascript:bookdelete(<%=abb.getBOOK_NO()%>);">
				<font size=2>削除</font>
			</a>
			</p>
			</td>
			</tr>
			<%
				}
			%>
			</table>
			</form>
			</td>
			</tr>
		</table>
		<!-- 図書リスト -->
		</td>
	</tr>
</table>
</div>
</body>
</html>