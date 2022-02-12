<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="net.order.db.*"%>
<%@ page import="net.member.db.*"%>
<%
	OrderBean order=(OrderBean)request.getAttribute("order");
	MemberBean ordermember=(MemberBean)request.getAttribute("ordermember");
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
<div name="main" style="margin:30 0 50 0">
<table width="960" cellspacing="0" cellpadding="0" border="0"
	align="center">
<tr>
<td colspan=2 align=center>
<!-- 注文情報修正(管理者) -->
<form action="./AdminOrderModify.adorder" name="orderform" method="post">
<input type="hidden" name="num" value="<%=order.getORDER_NO() %>">
<table width=90% border=0 cellpadding="0" cellspacing="1">
	<tr>
		<p align=left><b><font size=4>注文情報変更</font></b></p>
	</tr>
	<tr><td height=10></td></tr>
	<tr><td height=10></td></tr>
	<tr>
	<td style="font-family: Tahoma; font-size: 10pt; font-weight: bold;"
		width=210 height=24 bgcolor="f7f7f7">送り状書留番号</td>
		<td style="font-family: Tahoma; font-size: 10pt; font-weight: bold;">
		<%=order.getORDER_TRANS_NO() %></td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr>
		<td style="font-family: Tahoma; font-size: 10pt; font-weight: bold;"
			width=210 height=24 bgcolor="f7f7f7">注文番号</td>
		<td style="font-family: Tahoma; font-size: 10pt; font-weight: bold;">
		<%=order.getORDER_NO() %></td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=20></tr>
	<tr height=20>
		<td style="font-family: Tahoma; font-size: 10pt; font-weight: bold;"
			width=210 height=24 colspan=2>商品情報</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=20>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">書名</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=order.getBOOK_NAME() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=23>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">単価</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=order.getBOOK_PRICE() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=23>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">本数</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=order.getORDER_COUNT() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=20></tr>
	<tr height=20>
		<td style="font-family: Tahoma; font-size: 10pt; font-weight: bold;"
			width=210 height=24 colspan=2>お届け先情報</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=20>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">届け先_氏名</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=order.getORDER_RECEIVE_NAME() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=20>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">届け先_氏名（カナ）</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=order.getORDER_RECEIVE_NAME_KANA() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=23>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">届け先_TEL</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=order.getORDER_RECEIVE_TEL() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=23>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">届け先_メールアドレス</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=order.getORDER_RECEIVE_EMAIL() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=23>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">届け先_郵便番号</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=order.getORDER_RECEIVE_ZIPCODE() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=20>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">届け先_都道府県</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=order.getORDER_RECEIVE_ADD_1() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=20>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">届け先_市区町村</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=order.getORDER_RECEIVE_ADD_2() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=20>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">届け先_丁目、番地、号、建物名</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=order.getORDER_RECEIVE_ADD_3() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=20></tr>
	<tr height=20>
		<td style="font-family: Tahoma; font-size: 10pt; font-weight: bold;"
			width=210 height=24 colspan=2>注文者情報</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=20>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">ID</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=order.getORDER_MEMBER_ID() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=20>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">メールアドレス</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=ordermember.getMEMBER_MAIL() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=23>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">TEL</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
			<%=ordermember.getMEMBER_TEL() %>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr height=20>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">メモー</td>
		<td style="font-family: Tahoma; font-size: 10pt;">
		<textarea name="memo" cols=60 rows=12><%=order.getORDER_MEMO() %>
		</textarea>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
</table>

<table width=90% border=0 cellpadding="0" cellspacing="1">
	<tr><td height=10></td></tr>
	<tr><td height=10></td></tr>
	<tr>
		<td><b><font size=2>決済情報</font></b></td>
	</tr>
	<tr>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">注文合計金額</td>
		<td width=650 height=24>
			<font size=2><%=order.getTOTAL_PRICE() %>円</font>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">注文日時</td>
		<td width=650 height=24>
			<font size=2><%=order.getORDER_DATE() %></font>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">決済方法</td>
		<td width=650 height=24>
			<font size=2><%=order.getORDER_TRADE_TYPE() %></font>
		</td>
	</tr>
	<tr>
		<td style="background-color: #F0F0F0; height: 1px;" colspan=6>
	</tr>
	<tr>
		<td style="font-family: Tahoma; font-size: 10pt;" width=210 height=24
			bgcolor="f7f7f7">注文ステータス</td>
		<td width=650 height=24>
		<select name="status">
			<option value="0" <%if(order.getORDER_STATUS()==0){%> selected
				<%}%>>注文受付</option>
			<option value="1" <%if(order.getORDER_STATUS()==1){%> selected
				<%}%>>発送準備中</option>
			<option value="2" <%if(order.getORDER_STATUS()==2){%> selected
				<%}%>>発送済み</option>
			<option value="3" <%if(order.getORDER_STATUS()==3){%> selected
				<%}%>>配送中</option>
			<option value="4" <%if(order.getORDER_STATUS()==4){%> selected
				<%}%>>配送済み</option>
			<option value="5" <%if(order.getORDER_STATUS()==5){%> selected
				<%}%>>注文キャンセル</option>
		</select>
		</td>
	</tr>
	<tr><td style="background-color: #F0F0F0; height: 1px;" colspan=6></tr>
	<tr><td style="background-color: #F0F0F0; height: 1px;" colspan=6></tr>
	<tr>
		<td align=center style="height: 1px;"
			colspan=6>
			<input type=submit value="変更">&nbsp; 
			<input type=button onclick="javascript:history.back();" value="戻る">
		</td>
	</tr>
</table>
</form>
<!-- 注文情報修正 -->
</td>
</tr>
</table>
</div>
</body>
</html>