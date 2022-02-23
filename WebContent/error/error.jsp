<%@ page contentType="text/html; charset=UTF-8" %>

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
	<div style="padding-left:500">
		<p>
			システムエラーが発生しました。
			<br>
			<br>
			お手数ですが、ホーム画面に戻って、最初からやり直してください。
			<br>
			もしくは、しばらく時間を空けてサイトにアクセスいただけますようお願いします。
		</p>
		<button onclick="location.href='../BookList.book'" style="margin-left:150">ホーム画面へ</button>
	</div>
</div>
</body>
</html>