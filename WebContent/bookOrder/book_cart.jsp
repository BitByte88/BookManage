<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="net.cart.db.*"%>
<%@ page import="net.book.db.*" %>

<%
@SuppressWarnings("unchecked") List<CartDTO> cartList = (ArrayList<CartDTO>) request.getAttribute("cartlist");
	@SuppressWarnings("unchecked") List<BookDTO> bookList = (ArrayList<BookDTO>) request.getAttribute("booklist");
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
</head>
<body>
<div style="margin:30 0 50 0">
<table style="width:960; margin:auto">
	<tr>
		<td colspan=2><!--カート -->
		<p align="center">
		<form action="./OrderStart.order" name="cartform" method="post">
		<input type="hidden" name="orderType" value="fromCart">
		<table style="width:90%">
			<tr align=center>
				<td><p align=left><b><font size=4>カート</font></b></p></td>
			</tr>
		</table>
		<table style="width:90%">
			<tr height=26 bgcolor="94d7e7">
				<td height="3" colspan="7" align=right></td>
			</tr>
			<tr bgcolor="#f0f8ff" align="center">
				<td width="5%"><font size="2">画像</font></td>
				<td width="25%"><font size="2">書名</font></td>
				<td width="8%"><font size="2">数量</font></td>
				<td width="8%"><font size="2">価格</font></td>
				<td width="8%"><font size="2">小計</font></td>
				<td width="8%"><font size="2">キャンセル</font></td>
			</tr>
			<%
			if (cartList != null && cartList.size() != 0) {
						for (int i = 0; i < cartList.size(); i++) {
							CartDTO dto = (CartDTO) cartList.get(i);
							BookDTO book=(BookDTO) bookList.get(i);
			%>
			<tr align="center">
			<td><font size="2"><img 
				src="./upload/<%=book.getBOOK_IMAGE().split(",")[0] %>" 
				width=50 height=50></font></td>
			<td style="text-align:left;padding-left:50"><font size="2"><%=book.getBOOK_NAME()%></font></td>
			<td><font size="2">
				<%=dto.getCART_COUNT()%>
			</font></td>
			<td><font size="2"><%=book.getBOOK_PRICE()%>円</font></td>
			<td><font size="2"><%=book.getBOOK_PRICE()*dto.getCART_COUNT()%>円</font></td>
			<td><font size="2">
			<a href="CartDelete.cart?num=<%=dto.getCART_NO()%>"
				onclick="return confirm('キャンセルしますか。?')">キャンセル</a>
			</font></td>
			</tr>
			<%
				}
			}else{
			%>
			<tr>
			<td colspan="7" align="center">
				<font size="2">カートに本がありません。</font>
			</td>
			</tr>
			<%
			}
			%>
		</table>
		
		<table style="width:90%">
			<tr>
				<td height="2" bgcolor="94d7e7"></td>
			</tr>
		</table>
		<br>
		
		<table style="width:90%">
			<tr>
			<td align="center">
			<%
			if (cartList != null && cartList.size() != 0) {
			%>
			<a href="javascript:cartform.submit()">
			[購入する]
			</a>
			<%}else{%>
			<a href="#" onclick="javascript:alert('注文する本がありません。')">
			[購入する]
			</a>
			<%}%>
			<a href="./BookList.book">
			[続けて買い物をする]
			</a>
			</td>
			</tr>
		</table>
		</form>
		<!-- カート -->
		</td>
	</tr>
</table>
</div>
</body>
</html>