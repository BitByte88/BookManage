function check(){
	var name=orderform.ORDER_RECEIVE_NAME.value;
	var nameKana=orderform.ORDER_RECEIVE_NAME_KANA.value;
	var tel=orderform.ORDER_RECEIVE_TEL.value;
	var mail=orderform.ORDER_RECEIVE_EMAIL.value;
	var zipcode=orderform.ORDER_RECEIVE_ZIPCODE.value;
	var add_1=orderform.ORDER_RECEIVE_ADD_1.value;
	var add_2=orderform.ORDER_RECEIVE_ADD_2.value;
	var add_3=orderform.ORDER_RECEIVE_ADD_3.value;
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
	} else if(nameKana.length > 32){//桁数チェック（届け先_氏名（カナ））
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
