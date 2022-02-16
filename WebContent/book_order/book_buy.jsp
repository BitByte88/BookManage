<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="net.member.db.MemberBean"%>
<%@ page import="net.cart.db.CartBean"%>
<%@ page import="net.book.db.BookBean" %>
<%
	MemberBean member = (MemberBean) request.getAttribute("member");
	String orderType = (String) request.getAttribute("orderType");
	List<Object> orderinfo = null;
	List<CartBean> cartlist = null;
	List<BookBean> booklist=null;
	
	if (orderType.equals("fromBookDetail")) {
		orderinfo = (ArrayList<Object>) request.getAttribute("orderinfo");
	} else {
		cartlist = (ArrayList<CartBean>) request.getAttribute("cartlist");
		booklist = (ArrayList<BookBean>) request.getAttribute("booklist");
	}
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
	<td colspan=2 align=right>
	<!-- 注文ページ -->
	<form action="./OrderAdd.order" method="post" name="orderform">
	<input type="hidden" name="orderType" value="<%=orderType%>"> 
	<%if (orderType.equals("fromBookDetail")) {%>
	<input type="hidden" name="bookno" value="<%=orderinfo.get(0)%>">
	<input type="hidden" name="bookname" value="<%=orderinfo.get(1)%>">
	<input type="hidden" name="amount" value="<%=orderinfo.get(2)%>">
	<input type="hidden" name="price" value="<%=orderinfo.get(3)%>">
	<%}%>
	<input type="hidden" name="memberid" value="<%=member.getMEMBER_ID() %>">
	
	<!-- 注文詳細内容 -->
	<table style="width:90%; border-spacing:1">
		<tr>
		<td>
			<p align=left><b><font size=4>注文詳細内容</font></b></p>
		</td>
		</tr>
		
		<tr align=center height=20 bgcolor="f7f7f7">
		<td style="font-family: Tahoma; font-size: 8pt; font-weight: bold;">画像</td>
		<td style="font-family: Tahoma; font-size: 8pt; font-weight: bold;">書名</td>
		<td style="font-family: Tahoma; font-size: 8pt; font-weight: bold;">数量</td>
		<td style="font-family: Tahoma; font-size: 8pt; font-weight: bold;">価格</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<%
			if (orderType.equals("fromBookDetail")) {
		%>
		<tr align=center height=20>
		<td style="font-family: Tahoma; font-size: 7pt;"><img
			src="./upload/<%=orderinfo.get(4) %>" width=50 height=50></td>
		<td style="font-family: Tahoma; font-size: 8pt;"><%=orderinfo.get(1)%></td>
		<td style="font-family: Tahoma; font-size: 8pt;"><%=orderinfo.get(2)%></td>
		<td style="font-family: Tahoma; font-size: 8pt;"><%=orderinfo.get(3)%>円</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<%
			} else {
				for (int i = 0; i < cartlist.size(); i++) {
					CartBean cart = (CartBean) cartlist.get(i);
					BookBean book = (BookBean) booklist.get(i);
		%>
		<tr align=center height=20>
		<td style="font-family: Tahoma; font-size: 7pt;">
			<img src="./upload/<%=book.getBOOK_IMAGE().split(",")[0] %>" 
				width=50 height=50>
		</td>
		<td style="font-family: Tahoma; font-size: 8pt;">
			<%=book.getBOOK_NAME()%>
		</td>
		<td style="font-family: Tahoma; font-size: 8pt;">
			<%=cart.getCART_COUNT()%>
		</td>
		<td style="font-family: Tahoma; font-size: 8pt;">
			<%=book.getBOOK_PRICE()%>円
		</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<%
				}
			}
		%>
	</table>

	<table style="width:90%; border-spacing:1">
		<tr>
			<td height=10>
			<td>
		</tr>
		<tr>
			<td height=10>
			<td>
		</tr>
		<tr>
			<td><b><font size=2>注文者情報</font></b></td>
		</tr>
		<tr>
			<td style="font-family: Tahoma; font-size: 8pt;" width=80 height=24
				bgcolor="f7f7f7">名前</td>
			<td width=320 height=24>
				<font size=2><%=member.getMEMBER_NAME()%></font>
			</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<tr>
			<td style="font-family: Tahoma; font-size: 8pt;" height=24
				bgcolor="f7f7f7">TEL</td>
			<td width=320 height=24>
				<font size=2><%=member.getMEMBER_TEL()%></font>
			</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<tr>
			<td style="font-family: Tahoma; font-size: 8pt;" height=24
				bgcolor="f7f7f7">メールアドレス</td>
			<td width=320 height=24>
				<font size=2><%=member.getMEMBER_MAIL()%></font>
			</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
	</table>

	<table style="width:90%; border-spacing:1">
		<tr>
			<td height=10>
			<td>
		</tr>
		<tr>
			<td height=10>
			<td>
		</tr>
		<tr>
			<td><b><font size=2>お届け先情報</font></b></td>
		</tr>
		<tr height=20>
			<td style="font-family: Tahoma; font-size: 8pt;" width=170 height=24
				bgcolor="f7f7f7">届け先_氏名</td>
			<td style="font-family: Tahoma; font-size: 8pt;">
				<input type="text" name="ORDER_RECEIVE_NAME" size=12
				value="<%=member.getMEMBER_NAME() %>">
			</td>
		</tr>
		<tr height=20>
			<td style="font-family: Tahoma; font-size: 8pt;" width=170 height=24
				bgcolor="f7f7f7">届け先_氏名（カナ）</td>
			<td style="font-family: Tahoma; font-size: 8pt;">
				<input type="text" name="ORDER_RECEIVE_NAME_KANA" size=12
				value="<%=member.getMEMBER_NAME_KANA() %>">
			</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<tr height=20>
			<td style="font-family: Tahoma; font-size: 8pt;" width=170 height=24
				bgcolor="f7f7f7">届け先_TEL</td>
			<td style="font-family: Tahoma; font-size: 8pt;">
				<input type="text" name="ORDER_RECEIVE_TEL" size=15
				value="<%=member.getMEMBER_TEL() %>">
			</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<tr height=20>
			<td style="font-family: Tahoma; font-size: 8pt;" width=170 height=24
				bgcolor="f7f7f7">届け先_メールアドレス</td>
			<td style="font-family: Tahoma; font-size: 8pt;">
				<input type="text" name="ORDER_RECEIVE_EMAIL" size=15
				value="<%=member.getMEMBER_MAIL() %>">
			</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<tr height=20>
			<td style="font-family: Tahoma; font-size: 8pt;" width=170 height=24
				bgcolor="f7f7f7">届け先_郵便番号</td>
			<td style="font-family: Tahoma; font-size: 8pt;">
				<input type="text" name="ORDER_RECEIVE_ZIPCODE" size=7
				value="<%=member.getMEMBER_ZIPCODE() %>">
			</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<tr height=20>
			<td style="font-family: Tahoma; font-size: 8pt;" width=170 height=24
				bgcolor="f7f7f7">届け先_都道府県</td>
			<td style="font-family: Tahoma; font-size: 8pt;">
				<input type="text" name="ORDER_RECEIVE_ADD_1" size=50
				value="<%=member.getMEMBER_ADD_1() %>"></td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<tr height=20>
			<td style="font-family: Tahoma; font-size: 8pt;" width=170 height=24
				bgcolor="f7f7f7">届け先_市区町村</td>
			<td style="font-family: Tahoma; font-size: 8pt;">
				<input type="text" name="ORDER_RECEIVE_ADD_2" size=50
				value="<%=member.getMEMBER_ADD_2() %>">
			</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<tr height=20>
			<td style="font-family: Tahoma; font-size: 8pt;" width=170 height=24
				bgcolor="f7f7f7">届け先_丁目、番地、号、建物名</td>
			<td style="font-family: Tahoma; font-size: 8pt;">
				<input type="text" name="ORDER_RECEIVE_ADD_3" size=50
				value="<%=member.getMEMBER_ADD_3() %>">
			</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<tr height=20>
			<td style="font-family: Tahoma; font-size: 8pt;" width=170 height=24
				bgcolor="f7f7f7">メモー</td>
			<td style="font-family: Tahoma; font-size: 8pt;">
			<textarea name="ORDER_MEMO" cols=60 rows=12></textarea>
			</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
	</table>

	<table style="width:90%; border-spacing:1">
		<tr>
			<td style="height: 10px;" colspan=6>
		</tr>
		<tr>
			<td align=center style="height: 1px;"
				colspan=6>
				<input type=submit value="注文">&nbsp;
				<input type=button onclick="javascript:history.back();"value="キャンセル">
			</td>
		</tr>
	</table>
	</form>
	<!-- 注文ページ -->
	</td>
</tr>
</table>
</div>
</body>
</html>