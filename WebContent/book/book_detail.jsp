<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.book.db.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	BookBean itemArray = (BookBean) request.getAttribute("itemArray");
	String category = (String) request.getAttribute("item");
	String price = (String) request.getAttribute("price");

	BookBean prevpage = (BookBean) request.getAttribute("prevpage");
	BookBean nextpage = (BookBean) request.getAttribute("nextpage");
%>

<html>
<head>
<title>쇼핑몰</title>
<script>
function isBuy(bookform) {
	if (document.bookform.size.value=="") {
		alert("사이즈를 선택해주세요.")
		return;
	} else if (document.bookform.color.value=="") {
		alert("색상을 선택해주세요.")
		return;
	}

	var amount=document.bookform.amount.value;
	var size=document.bookform.size.value;;
	var type=document.bookform.color.value;;
	
	var isbuy=confirm("구매하시겠습니까?");
	if(isbuy==true) {
		bookform.action="OrderStart.order";
		bookform.submit();
	} else {
		return;
	}
}

function isCart(cartform) {
	if (document.bookform.size.value=="") {
		alert("사이즈를 선택해주세요.")
		return;
	} else if (document.bookform.color.value=="") {
		alert("색상을 선택해주세요.")
		return;
	}
	
	var amount=document.bookform.amount.value;
	var size=document.bookform.size.value;;
	var type=document.bookform.color.value;;
	
	var isbuy=confirm("장바구니에 저장하시겠습니까?");
	
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
<table width="960" cellspacing="0" cellpadding="0" border="0"
	align="center">
	<tr>
		<td colspan=2 align=center><!-- 상품 내용 -->
		<form name="bookform" action="#" method="post">
		<input type="hidden" name="booknum" 
			value="${itemArray.BOOK_NUM }">
		<input type="hidden" name="item"
			value="<%=request.getParameter("item") %>"> 
		<input type="hidden" name="gr_book_num"
			value="<%=request.getParameter("gr_book_num") %>">
		<input type="hidden" name="isitem"
			value="<%=request.getParameter("isitem") %>">
		<input type="hidden" name="order" value="book">
		<input type="hidden" name="price" 
			value="<%=itemArray.getBOOK_PRICE() %>">
		<input type="hidden" name="bookname" 
			value="<%=itemArray.getBOOK_NAME() %>"> 
		<input type="hidden" name="bookimage"
			value="${fn:trim(mainImage)}">
		
		<table width="600" border="0" align="center">
		<tr>
			<td height="60" colspan="2">상 세 보 기</td>
		</tr>
		<tr>
			<td width="303" height="223" align="center" valign="middle">
			<img src="./upload/${fn:trim(mainImage)}" 
				width="300" height="300"/>
			</td>
			<td width="381" align="center" valign="middle">
			<table width="300" height="200" border="0">
				<tr>
					<td colspan="4" align="center" height="50">
						<b>${itemArray.BOOK_NAME}</b>
					</td>
				</tr>
				<tr>
					<td>판매가격 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</td>
					<td colspan="3">${itemArray.BOOK_PRICE} 원</td>
				</tr>
				<tr>
					<td rowspan="2">수량
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
					</td>
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
					<td width="69" rowspan="2" align="left">개</td>
				</tr>
				<tr>
					<td valign="top">
						<div align="center">
							<a href="JavaScript:count_change(1)">▼</a>
						</div>
					</td>
				</tr>
				<tr>
					<td align="left" colspan="4" height="30" 
						valign="middle">
						남은수량(${itemArray.BOOK_AMOUNT })개
					</td>
				</tr>
				<tr>
					<td colspan="4">크기&nbsp;&nbsp;&nbsp;&nbsp; 
						<select name="size" size="1">
							<option value="">크기를 선택하세요</option>
							<option value="">-----------------</option>

							<c:forTokens var="size" 
							items="${itemArray.BOOK_SIZE}"	delims=",">
								<option value=${fn:trim(size)}>
									${fn:trim(size)}
								</option>
							</c:forTokens>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="4">색깔&nbsp;&nbsp;&nbsp;&nbsp;
						<select name="color" size="1">
							<option value="">색깔을 선택하세요</option>
							<option value="">-----------------</option>
							<c:forTokens var="color" 
								items="${itemArray.BOOK_COLOR}"
								delims=",">
								<option value="${fn:trim(color)}">
									${fn:trim(color)}
								</option>
							</c:forTokens>
						</select>
					</td>
				</tr>
				<tr>
					<td height="50" colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4">
						<a href="javascript:isBuy(bookform);">
						[구매하기]
						</a> 
						<a href="javascript:isCart(bookform);">
						[장바구니 담기]
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
			<table align="center">
				<tr>
					<td>
					<%
					if (prevpage.getBOOK_NUM() != 0) {
						if (price.equals("no")) {
					%>
						<a href="Book_Detail.book?search=prev
						&gr_book_num=<%=itemArray.getBOOK_NUM()%>
						&item=<%=category%>">
					<%
						} else {
					%> 
						<a href="Book_Detail.book?search=prev
						&gr_book_num=<%=itemArray.getBOOK_NUM()%>
						&item=<%=category%>&price=<%=price%>">
					<%
						}
					%>
						[이전상품] 
						</a>
					</td>
					<td width="100" align="left">
					<div align="center">
						<img 
						src="./upload/<%=prevpage.getBOOK_IMAGE()%>"
						width="70" height="50" border="0">
						<br><%=prevpage.getBOOK_NAME()%>
					</div>
					</td>
					<%
					}
					%>
					<td width="100" align="right">
					<%
					if (nextpage.getBOOK_NUM() != 0) {
					%>
						<div align="center">
						<img
						src="./upload/<%=nextpage.getBOOK_IMAGE()%>"
						width="70" height="50" border="0">
						<br><%=nextpage.getBOOK_NAME()%>
					</div>
					</td>
					<td>
					<%
						if (price.equals("no")) {
					%>
						<a href="Book_Detail.book?search=next
						&gr_book_num=<%=itemArray.getBOOK_NUM()%>
						&item=<%=category%>">
					<%
						} else {
					%>
						<a href="Book_Detail.book?search=next
						&gr_book_num=<%=itemArray.getBOOK_NUM()%>
						&item=<%=category%>&price=<%=price%>">
					<%
						}
					%> [다음상품] 
						</a>
					<%
 					}
 					%>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		</table>
		</form><!-- 상품 구매 메뉴 끝 -->
		<!-- 상품 내용 -->
		<table width="700" height="300" align="enter">
			<tr>
				<td align="center">
				<div>${itemArray.BOOK_CONTENT }</div>
				</td>
			</tr>
		</table>
		<br><br>
		<table width="200" border="0" align="center">
			<tr align="left">
				<td colspan="3"><c:forEach var="itemimg"
					items="${requestScope.contentImage}">
					<img src="./upload/${fn:trim(itemimg)}" />
				</c:forEach></td>
			</tr>
		</table>
		<!-- 상품 내용 끝 --></td>
	</tr>
</table>
</body>
</html>