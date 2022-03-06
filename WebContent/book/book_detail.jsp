<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.book.db.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	BookBean itemArray = (BookBean) request.getAttribute("itemArray");
	BookBean prevpage = (BookBean) request.getAttribute("prevpage");
	BookBean nextpage = (BookBean) request.getAttribute("nextpage");
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
<script>
function isBuy(bookform) {
	var amount= document.bookform.amount.value;
	if (amount.length == 0) {//必須チェック（数量）
		alert("数量を入力してください。");
		return;
	} else if(isNaN(amount)){//形式チェック（数量）
		alert("数量は数字を入力してください。");
		return;
	} else if(parseInt(amount) <= 0){//入力する数量が０以下の場合
		alert("数量は１冊以上を注文してください。");
		return;
	}
	var isbuy=confirm("購入しますか");
	if(isbuy==true) {
		bookform.action="OrderStart.order";
		bookform.submit();
	} else {
		return;
	}
}

function isCart(cartform) {
	var amount = document.bookform.amount.value;
	if (amount.length == 0) {//必須チェック（数量）
		alert("数量を入力してください。");
		return;
	} else if(isNaN(amount)){//形式チェック（数量）
		alert("数量は数字を入力してください。");
		return;
	} else if(parseInt(amount) <= 0){//入力する数量が０以下の場合
		alert("数量は１冊以上を注文してください。");
		return;
	}
	
	var isbuy=confirm("カートに入れますか");
	
	if(isbuy==true) {
		cartform.action="CartAdd.cart";
		cartform.submit();
	} else {
		return;
	}
}

function count_change(temp){
	var test=document.bookform.amount.value;
	if(temp==0){
		test++;
	}else if(temp==1){
		if(test> 1) test--;
	}
	
	document.bookform.amount.value=test;
}
</script>
</head>
<body>
<div style="margin:30 0 50 0">
<table style="width:960; margin:auto">
	<tr>
		<td colspan=2 align=center><!-- 商品内容 -->
		<form name="bookform" action="#" method="post">
		<input type="hidden" name="bookno" 
			value="${itemArray.BOOK_NO }">
		<input type="hidden" name="orderType" value="fromBookDetail">
		<input type="hidden" name="price" value="<%=itemArray.getBOOK_PRICE() %>">
		<input type="hidden" name="bookname" value="<%=itemArray.getBOOK_NAME() %>"> 
		<input type="hidden" name="bookimage" value="${fn:trim(mainImage)}">
		<table style="width:600; margin:auto">
		<tr>
			<td height="60" colspan="2">詳細</td>
		</tr>
		<tr>
			<td width="303" height="223" align="center" valign="middle">
			<img src="./upload/${fn:trim(mainImage)}" 
				width="300" height="300"/>
			</td>
			<td width="381" align="center" valign="middle">
			<table style="width:300; height:200">
				<tr>
					<td colspan="4" align="center" height="50">
						<b>${itemArray.BOOK_NAME}</b>
					</td>
				</tr>
				<tr>
					<td>カテゴリー &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</td>
					<td colspan="3">${itemArray.BOOK_CATEGORY}</td>
				</tr>
				<tr>
					<td>著者 　　　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</td>
					<td colspan="3">${itemArray.BOOK_WRITER}</td>
				</tr>
				<tr>
					<td>出版社 　　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</td>
					<td colspan="3">${itemArray.BOOK_PUBLISHER}</td>
				</tr>
				<tr>
				<tr>
					<td>発行日　 　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</td>
					<td colspan="3">${itemArray.BOOK_PUBLISHING_DATE}</td>
				</tr>
				<tr>
					<td>販売価格 　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</td>
					<td colspan="3">${itemArray.BOOK_PRICE} 円</td>
				</tr>
				<tr>
					<td rowspan="2">数量　 　　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</td>
					<td width="58" rowspan="2">
						<input name="amount" type="text" 
							style="text-align: right"
							value="1"	size="4" />
					</td>
					<td valign="bottom">
						<div align="center">
							<a href="JavaScript:count_change(0)">▲</a>
						</div>
					</td>
					<td width="69" rowspan="2" align="left">冊</td>
				</tr>
				<tr>
					<td valign="top">
						<div align="center">
							<a href="JavaScript:count_change(1)">▼</a>
						</div>
					</td>
				</tr>
				<tr>
					<td height="50" colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4">
						<a href="javascript:isBuy(bookform);">
						[購入する]
						</a> 
						<a href="javascript:isCart(bookform);">
						[カートに入れる]
						</a>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td align="center"></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">
			<table style="margin:auto">
				<tr>
					<td>
					<%
					if (prevpage.getBOOK_NO() != 0) {
					%> 
						<a href="Book_Detail.book?search=prev&book_no=<%=itemArray.getBOOK_NO()%>">
						[前商品] 
						</a>
					</td>
					<td width="100" align="left">
					<div align="center">
						<img src="./upload/<%=prevpage.getBOOK_IMAGE()%>" width="70" height="50" border="0">
						<br><%=prevpage.getBOOK_NAME()%>
					</div>
					</td>
					<%
					}
					%>
					<td width="100" align="right">
					<%
					if (nextpage.getBOOK_NO() != 0) {
					%>
						<div align="center">
						<img src="./upload/<%=nextpage.getBOOK_IMAGE()%>" width="70" height="50" border="0">
						<br><%=nextpage.getBOOK_NAME()%>
					</div>
					</td>
					<td>
						<a href="Book_Detail.book?search=next&book_no=<%=itemArray.getBOOK_NO()%>">
						[次商品] 
						</a>
					</td>
					<%
					}
					%>
				</tr>
			</table>
			</td>
		</tr>
		</table>
		</form>
		<!-- 商品内容 -->
		<table style="width:700; height:50; margin:auto">
			<tr>
				<td align="center">
				<div>${itemArray.BOOK_CONTENT}</div>
				</td>
			</tr>
		</table>
		<br><br>
		<table  style="width:200; margin:auto">
			<tr align="left">
				<td colspan="3">
					<c:forEach var="itemimg" items="${requestScope.contentImage}">
						<img src="./upload/${fn:trim(itemimg)}" />
					</c:forEach>
				</td>
			</tr>
		</table>
		<!-- 商品内容 --></td>
	</tr>
</table>
</div>
</body>
</html>