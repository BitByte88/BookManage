<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="main.java.jp.co.bookmanage.dto.BookDTO"%>
<%@ page import="main.java.jp.co.bookmanage.dto.MemberDTO"%>
<%@ page import="main.java.jp.co.bookmanage.dto.CartDTO"%>
<%
MemberDTO member = (MemberDTO) request.getAttribute("member");
	String orderType = (String) request.getAttribute("orderType");
	@SuppressWarnings("unchecked") List<Object> orderinfo = (ArrayList<Object>)request.getAttribute("orderinfo");
	@SuppressWarnings("unchecked") List<CartDTO> cartlist = (ArrayList<CartDTO>)request.getAttribute("cartlist");
	@SuppressWarnings("unchecked") List<BookDTO> booklist= (ArrayList<BookDTO>)request.getAttribute("booklist");
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
function check(){
	var name=orderform.ORDER_RECEIVE_NAME.value;
	var nameKana=orderform.ORDER_RECEIVE_NAME_KANA.value;
	var tel=orderform.ORDER_RECEIVE_TEL.value;
	var mail=orderform.ORDER_RECEIVE_EMAIL.value;
	var zipcode=orderform.ORDER_RECEIVE_ZIPCODE.value;
	var add1=orderform.ORDER_RECEIVE_ADD_1.value;
	var add2=orderform.ORDER_RECEIVE_ADD_2.value;
	var add3=orderform.ORDER_RECEIVE_ADD_3.value;
	var memo=orderform.ORDER_MEMO.value;
	
	if(name.length == 0){//必須チェック（届け先_氏名）
		alert("届け先_氏名を入力してください。");
		orderform.ORDER_RECEIVE_NAME.focus();
		return false;
	} else if(name.length > 32){//桁数チェック（届け先_氏名）
		alert("届け先_氏名は32文字以内まで入力してください。");
		orderform.ORDER_RECEIVE_NAME.focus();
		return false;		
	}
	
	if(nameKana.length == 0){//必須チェック（届け先_氏名（カナ））
		alert("届け先_氏名（カナ）を入力してください。");
		orderform.ORDER_RECEIVE_NAME_KANA.focus();
		return false;
	} else if(namekana.length > 32){//桁数チェック（届け先_氏名（カナ））
		alert("届け先_氏名（カナ）は32文字以内まで入力してください。");
		orderform.ORDER_RECEIVE_NAME_KANA.focus();
		return false;		
	}
	
	if(tel.length == 0){//必須チェック（届け先_TEL）
		alert("届け先_TELを入力してください。");
		orderform.ORDER_RECEIVE_TEL.focus();
		return false;
	} else if(tel.length > 16){//桁数チェック（届け先_TEL）
		alert("届け先_TELは16文字以内まで入力してください。");
		orderform.ORDER_RECEIVE_TEL.focus();
		return false;		
	}
	
	if(mail.length == 0){//必須チェック（届け先_メールアドレス）
		alert("届け先_メールアドレスを入力してください。");
		orderform.ORDER_RECEIVE_MAIL.focus();
		return false;
	} else if(mail.length > 128){//桁数チェック（届け先_メールアドレス）
		alert("届け先_メールアドレスは128文字以内まで入力してください。");
		orderform.ORDER_RECEIVE_MAIL.focus();
		return false;		
	}
	
	if((zipcode=="") || (zipcode.length <= 6)){//必須チェック（郵便番号
		alert("届け先_郵便番号を入力してください。");
		orderform.ORDER_RECEIVE_ZIPCODE.focus();
        return false;
 	}
	
	if(add_1.length == 0){//必須チェック（届け先_都道府県）
		alert("届け先_都道府県を選択してください。");
		orderform.ORDER_RECEIVE_ADD_1.focus();
		return false;
	} else if(add_1.length > 128){//桁数チェック（届け先_都道府県）
		alert("届け先_都道府県は128文字以内まで入力してください。");
		orderform.ORDER_RECEIVE_ADD_1.focus();
		return false;		
	} 
	
	if(add_2.length == 0){//必須チェック（届け先_市区町村）
		alert("届け先_市区町村を入力してください。");
		orderform.ORDER_RECEIVE_ADD_2.focus();
		return false;
	} else if(add_2.length > 128){//桁数チェック（届け先_市区町村）
		alert("届け先_市区町村は128文字以内まで入力してください。");
		orderform.ORDER_RECEIVE_ADD_2.focus();
		return false;		
	} 
	
	if(add_3.length == 0){//必須チェック（届け先_丁目、番地、建物名）
		alert("届け先_丁目、番地、建物名を入力してください。");
		orderform.ORDER_RECEIVE_ADD_3.focus();
		return false;
	} else if(add_3.length > 128){//桁数チェック（届け先_丁目、番地、建物名）
		alert("届け先_丁目、番地、建物名は128文字以内まで入力してください。");
		orderform.ORDER_RECEIVE_ADD_3.focus();
		return false;		
	} 
	
	if(memo.length != 0 && memo.length > 128){//桁数チェック（メモー）
		alert("メモーは128文字以内まで入力してください。");
		orderform.ORDER_MEMO.focus();
		return false;
	}
	
	return true;
}

</script>
</head>
<body>
<div style="margin:30 0 50 0">
<table style="width:960; margin:auto">
<tr>
	<td colspan=2 align=right>
	<!-- 注文ページ -->
	<form action="./OrderAdd.order" method="post" name="orderform" onsubmit="return check()">
	<input type="hidden" name="orderType" value="<%=orderType%>"> 
	<%
 	if (orderType.equals("fromBookDetail")) {
 	%>
	<input type="hidden" name="bookno" value="<%=orderinfo.get(0)%>">
	<input type="hidden" name="bookname" value="<%=orderinfo.get(2)%>">
	<input type="hidden" name="amount" value="<%=orderinfo.get(3)%>">
	<input type="hidden" name="price" value="<%=orderinfo.get(4)%>">
	<%
	}
	%>
	<input type="hidden" name="memberid" value="<%=member.getMEMBER_ID()%>">
	
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
			src="./upload/<%=orderinfo.get(1)%>" width=50 height=50></td>
		<td style="font-family: Tahoma; font-size: 8pt;"><%=orderinfo.get(2)%></td>
		<td style="font-family: Tahoma; font-size: 8pt;"><%=orderinfo.get(3)%></td>
		<td style="font-family: Tahoma; font-size: 8pt;"><%=orderinfo.get(4)%>円</td>
		</tr>
		<tr>
			<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
		</tr>
		<tr height=10></tr>
		<tr align=right>
			<td style="font-family: Tahoma; font-size: 8pt; font-weight:bold;" colspan="4">合計金額 ： <%=request.getAttribute("totalPrice")%>円</td>
			
		</tr>
		<%
		} else {
				for (int i = 0; i < cartlist.size(); i++) {
					CartDTO cart = (CartDTO) cartlist.get(i);
					BookDTO book = (BookDTO) booklist.get(i);
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
		%>
		<tr height=10></tr>
		<tr align=right>
			<td style="font-family: Tahoma; font-size: 8pt; font-weight:bold;" colspan="4">合計金額 ： <%=request.getAttribute("totalPrice")%>円</td>
			
		</tr>
		<%
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