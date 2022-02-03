<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="net.cart.db.*"%>
<%@ page import="net.book.db.*" %>

<%
	List cartList = (ArrayList) request.getAttribute("cartlist");
	List bookList = (ArrayList) request.getAttribute("booklist");
	
	String item = request.getParameter("item");
	String gr_book_num = request.getParameter("gr_book_num");
	String isitem = request.getParameter("isitem");
%>

<html>
<head>
<title>쇼핑몰</title>
</head>
<body>
<table width="960" cellspacing="0" cellpadding="0" border="0"
	align="center">
	<tr>
		<td colspan=2><!-- 장바구니 -->
		<p align="center">
		<form action="./OrderStart.order" name="cartform" method="post">
		<input type="hidden" name="order" value="cart">
		<table width="80%">
			<tr align=center>
				<td><b>장 바 구 니</b></td>
			</tr>
		</table>
		<table width="80%" cellpadding="0" cellspacing="0">
			<tr height=26 bgcolor="94d7e7">
				<td height="3" colspan="7" align=right></td>
			</tr>
			<tr bgcolor="#f0f8ff" align="center">
				<td width="5%"><font size="2">번호</font></td>
				<td width="5%"><font size="2">사진</font></td>
				<td width="25%"><font size="2">제품명</font></td>
				<td width="8%"><font size="2">수량</font></td>
				<td width="8%"><font size="2">가격</font></td>
				<td width="8%"><font size="2">취소</font></td>
			</tr>
			<%
			if (cartList != null && cartList.size() != 0) {
				for (int i = 0; i < cartList.size(); i++) {
					CartBean dto = (CartBean) cartList.get(i);
					BookBean book=(BookBean) bookList.get(i);
			%>
			<tr align="center">
			<td><font size="2"><%=dto.getCART_NUM()%></font></td>
			<td><font size="2"><img 
				src="./upload/<%=book.getBOOK_IMAGE().split(",")[0] %>" 
				width=50 height=50></font></td>
			<td><font size="2"><%=book.getBOOK_NAME()%></font></td>
			<td><font size="2">
				<%=dto.getCART_BOOK_AMOUNT()%>
			</font></td>
			<td><font size="2"><%=book.getBOOK_PRICE()%></font></td>
			<td><font size="2">
			<a href="CartDelete.cart?num=<%=dto.getCART_NUM()%>"
				onclick="return confirm('취소하시겠습니까?')">취소</a>
			</font></td>
			</tr>
			<%
				}
			}else{
			%>
			<tr>
			<td colspan="7" align="center">
				<font size="2">장바구니에 담긴 상품이 없습니다.</font>
			</td>
			</tr>
			<%
			}
			%>
		</table>
		
		<table width="80%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="2" bgcolor="94d7e7"></td>
			</tr>
		</table>
		<br>
		
		<table width="80%" cellpadding="0" cellspacing="0">
			<tr>
			<td align="center">
			<%
			if (cartList != null && cartList.size() != 0) {
			%>
			<a href="javascript:cartform.submit()">
			[구매하기]
			</a>
			<%}else{%>
			<a href="#" onclick="javascript:alert('주문할 상품이 없습니다.')">
			[구매하기]
			</a>
			<%}
			if (item == null) {%>
			<a href="./BookList.book?item=new_item">
			[계속 쇼핑하기]</a>
			<%}else{%>
			<a href="./Book_Detail.book?item=<%=item %>
			&gr_book_num=<%=gr_book_num %>
			&isitem=<%=isitem %>">
			[계속 쇼핑하기]</a>
			<%}%>
			</td>
			</tr>
		</table>
		</form>
		<!-- 장바구니 -->
		</p>
		</td>
	</tr>
</table>
</body>
</html>