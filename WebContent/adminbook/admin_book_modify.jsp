<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="net.admin.book.db.*"%>
<%  
	BookBean abb=(BookBean)request.getAttribute("abb");
%>
<html>
<head>
<title>쇼핑몰</title>
</head>
<body>
<table width="960" cellspacing="0" cellpadding="0" border="0"
	bordercolor="gray" align="center">
<tr>
	<td colspan=2>
	<!-- 상품 수정 -->
	<table border="0" width="80%">
	<form name="bookform" action="./BookModifyAction.adbook" method="post">
	<input type="hidden" name="book_num" value=<%=abb.getBOOK_NUM() %>>
	<tr>
		<td>
		<p align="center"><span style="font-size: 26pt;">상 품 수 정</span></p>
		</td>
	</tr>
		
	<tr>
		<td height="331">
		<table border="1" align="center" width="558">
		<tr>
			<td width="196" height="30">
			<p align="center"><font size=2>카테고리</font></p>
			</td>
			
			<td width="346" height="30">
			<select name="book_category" size="1" 
					value=<%=abb.getBOOK_CATEGORY() %>>
				<option value="outwear">아웃웨어</option>
				<option value="fulldress">정장/신사복</option>
				<option value="tshirts">티셔츠</option>
				<option value="shirts">와이셔츠</option>
				<option value="pants">팬츠</option>
				<option value="shoes">슈즈</option>
			</select>
			</td>
		</tr>
		<tr>
			<td>
			<p align="center"><font size=2>상품이름</font></p>
			</td>
			<td><input type="text" name="book_name" 
					value=<%=abb.getBOOK_NAME() %>></td>
		</tr>
		<tr>
			<td>
			<p align="center"><font size=2>판매가</font></p>
			</td>
			<td><input type="text" name="book_price" 
					value=<%=abb.getBOOK_PRICE() %>></td>
		</tr>
		<tr>
			<td>
			<p align="center"><font size=2>색깔</font></p>
			</td>
			<td><input type="text" name="book_color" 
					value=<%=abb.getBOOK_COLOR() %>></td>
		</tr>
		<tr>
			<td>
			<p align="center"><font size=2>수량</font></p>
			</td>
			<td><input type="text" name="book_amount" 
					value=<%=abb.getBOOK_AMOUNT() %>></td>
		</tr>
		<tr>
			<td>
			<p align="center"><font size=2>사이즈</font></p>
			</td>
			<td><input type="text" name="book_size" 
					value=<%=abb.getBOOK_SIZE() %>></td>
		</tr>
		<tr>
			<td>
			<p align="center"><font size=2>인기상품</font></p>
			</td>
			<td><input type="radio" name="book_best" value=1
				<%if(abb.getBOOK_BEST()==1){%>CHECKED<%}%>>
				<font size=2>예</font>
				<input type="radio" name="book_best" value=0 
				<%if(abb.getBOOK_BEST()==0){%>CHECKED<%}%>>
				<font size=2>아니오</font></td>
		</tr>
		<tr>
			<td width="196">
			<p align="center"><font size=2>제품정보</font></p>
			</td>
			<td width="346">
				<textarea name="book_content" cols=50 rows=15><%=abb.getBOOK_CONTENT() %></textarea>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td height="75">
		<p align="center"><input type="submit" value="수정">&nbsp;
		<input type="reset" value="다시쓰기"></p>
		</td>
	</tr>
	</form>
	</table>
	<!-- 상품 수정 -->
	</td>
</tr>
</table>
</body>
</html>