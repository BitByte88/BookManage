<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<link rel="icon" href="favicon/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="favicon/favicon.ico" type="image/x-icon"> 
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="favicon/favicon-144x144.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="favicon/favicon-72x72.png">
	<link rel="apple-touch-icon-precomposed" href="favicon/favicon-54x54.png">
	<script>
	function formSubmit(){
		var form = document.getElementById("findform");
		
		if ((form.MEMBER_NAME.value=="") ||
			(form.MEMBER_NAME.value.length<=1)){//必須チェック（氏名）
			alert("氏名を入力してください。");
			form.MEMBER_NAME.focus();
	        return false;
		}else if((form.MEMBER_NAME_KANA.value=="") ||
				(form.MEMBER_NAME_KANA.value.length<=1)){//必須チェック（氏名（カナ））
			alert("氏名（カナ）を入力してください。");
	   		form.MEMBER_NAME_KANA.focus();
	        return false;
	 	}else if((form.MEMBER_TEL.length==0)){//必須チェック（電話番号）
	 		alert("電話番号を入力してください。");
	      	form.MEMBER_TEL.focus();
	        return false;
		}else if((form.MEMBER_MAIL1.length==0)||
	 			(form.MEMBER_MAIL2.length==0)){//必須チェック（メールアドレス）
			alert("メールアドレスを入力してください。");
	      	form.MEMBER_MAIL1.focus();
	      	form.MEMBER_MAIL2.focus();
	        return false;
		}
		
		return true;
	}
	</script>
</head>
<body>
<table style="width:450px; height:20px">
	<tr>
		<td align="left">
			<b>アカウント／パスワードを探す</b>
		</td>
	</tr>
</table>	
<form action="./MemberFindAction.member" method="post" id="findform" 
	onSubmit="return formSubmit();">
<table style="width:500px">
<thead>
<tr>
<td colspan=2>
	<font size="2">				
	&nbsp;&nbsp;&nbsp;&nbsp;
	氏名、氏名（カナ）、電話番号、メールアドレスを入力してください。
	<br/><br/><br/><br/></font>
</td>
</tr>
</thead>
<tr>
	<td height="29" bgcolor="#F3F3F3">
		<font size="2">氏名</font>
	</td>
	<td>
		&nbsp;
		<input type="text" name="MEMBER_NAME" maxlength="20" size="20" placeholder="図書　太郎">
	</td>
</tr>
<tr>
	<td height="29" bgcolor="#F3F3F3">
		<font size="2">氏名（カナ）</font>
	</td>
	<td>
		&nbsp;
		<input type="text" name="MEMBER_NAME_KANA" maxlength="20" size="20" placeholder="トショ　タロウ">
	</td>
</tr>
<tr>
	<td height="29" bgcolor="#F3F3F3">
		<font size="2">電話番号</font>
	</td>
	<td>
		&nbsp;
		<input type="text" name="MEMBER_TEL" maxlength="11" size="11" placeholder="0120000000">
	</td>
</tr>
<tr>
	<td height="29" bgcolor="#F3F3F3">
		<font size="2">メールアドレス</font>
	</td>
	<td>
		&nbsp;
		<input type="text" name="MEMBER_MAIL1" size="15" placeholder="tosyo"/>@
		<input type="text" name="MEMBER_MAIL2" size="20" placeholder="nuvo.co.jp"/> 
	</td>
</tr>
<tr>
	<td colspan="2" style="padding:10px 0 0 0" align="center">
		<input type="submit" value="確認">
	</td>
</tr>				
</table>
</form>	
</body>
</html>