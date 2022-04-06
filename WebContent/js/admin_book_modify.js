window.onload = function() {
	document.querySelector('#book_category').value = "${abb.getBOOK_CATEGORY()}";
}
function check(){
	var category=bookform.book_category.value;
	var name=bookform.book_name.value;
	var writer=bookform.book_writer.value;
	var publisher=bookform.book_publisher.value;
	var publishingDate=bookform.book_publishing_date.value;
	var price=bookform.book_price.value;
	var isbn=bookform.book_isbn.value;
	var content=bookform.book_content.value;
	
	
	if(category.length == 0){//必須チェック（カテゴリー）
		alert("カテゴリーを入力してください。");
		bookform.book_category.focus();
		return false;
	} else if (category.length > 32){//桁数チェック（カテゴリー）
		alert("カテゴリーは32文字以内まで入力してください。");
		bookform.book_category.focus();
		return false;		
	}
	
	if(name.length == 0){//必須チェック（書名）
		alert("書名を入力してください。");
		bookform.book_name.focus();
		return false;
	} else if (name.length > 32){//桁数チェック（書名）
		alert("書名は32文字以内まで入力してください。");
		bookform.book_name.focus();
		return false;		
	} 
	
	if(writer.length == 0){//必須チェック（著者）
		alert("著者を入力してください。");
		bookform.book_writer.focus();
		return false;
	} else if (writer.length > 32){//桁数チェック（著者）
		alert("著者は32文字以内まで入力してください。");
		bookform.book_writer.focus();
		return false;		
	} 
	
	if(publisher.length == 0){//必須チェック（出版社）
		alert("出版社を入力してください。");
		bookform.book_publisher.focus();
		return false;
	} else if (writer.length > 32){//桁数チェック（出版社）
		alert("出版社は32文字以内まで入力してください。");
		bookform.book_publisher.focus();
		return false;		
	} 
	
	if(publishingDate.length == 0){//必須チェック（発行日）
		alert("発行日を入力してください。");
		bookform.book_publishing_date.focus();
		return false;
	} else {
		var regex = RegExp(/^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/);
		if(!regex.test(publishingDate)){//形式チェック（発行日）
			alert("発行日の形式を確認してください。");
			bookform.book_publishing_date.focus();
			return false;			
		} else {//入力する発行日が今日以後の日時の場合
			var today = new Date();
			var date = new Date(publishingDate);
			if(date > today){
				alert("発行日は今日からそれ以前の日付のみ入力できます。");
				bookform.book_publishing_date.focus();
				return false;			
			}
		}
	}
	
	if(price.length == 0){//必須チェック（販売価格）
		alert("販売価格を入力してください。");
		bookform.book_price.focus();
		return false;
	} else if (isNaN(price)){//形式チェック（販売価格）
		alert("販売価格は数字を入力してください。");
		bookform.book_price.focus();
		return false;		
	}
	
	if(isbn.length == 0){//必須チェック（ISBN）
		alert("ISBNコードを入力してください。");
		bookform.book_isbn.focus();
		return false;
	}else if(isbn.length != 10 && isbn.length != 13){//桁数チェック（ISBN）
		alert("ISBNコードは１０桁または１３桁で入力してください。");
		bookform.book_isbn.focus();
		return false;
	}
	
	if(content.length > 128){//桁数チェック（図書内容）
		alert("図書内容は128文字以内まで入力してください。");
		bookform.book_content.focus();
		return false;		
	}
	
	return true;
}