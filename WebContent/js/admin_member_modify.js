function check(){
	var name=memberform.MEMBER_NAME.value;
	var nameKana=memberform.MEMBER_NAME_KANA.value;
	var tel=memberform.MEMBER_TEL.value;
	var mail=memberform.MEMBER_MAIL.value;
	var zipCode=memberform.MEMBER_ZIPCODE.value;
	var add_1=memberform.MEMBER_ADD_1.value;
	var add_2=memberform.MEMBER_ADD_2.value;
	var add_3=memberform.MEMBER_ADD_3.value;
	var memberType=memberform.MEMBER_TYPE.value;
	

	if(name.length == 0){//必須チェック（氏名）
		alert("氏名を入力してください。");
		memberform.MEMBER_NAME.focus();
		return false;
	} else if(name.length > 32){//桁数チェック（氏名）
		alert("氏名は32文字以内まで入力してください。");
		memberform.MEMBER_NAME.focus();
		return false;		
	}
	
	if(nameKana.length == 0){//必須チェック（氏名（カナ））
		alert("氏名（カナ）を入力してください。");
		memberform.MEMBER_NAME_KANA.focus();
		return false;
	} else if(nameKana.length > 32){//桁数チェック（氏名（カナ））
		alert("氏名（カナ）は32文字以内まで入力してください。");
		memberform.MEMBER_NAME_KANA.focus();
		return false;		
	}
	
	if(tel.length == 0){//必須チェック（TEL）
		alert("TELを入力してください。");
		memberform.MEMBER_TEL.focus();
		return false;
	} else if(tel.length > 16){//桁数チェック（TEL）
		alert("TELは16文字以内まで入力してください。");
		memberform.MEMBER_TEL.focus();
		return false;		
	}
	
	if(mail.length == 0){//必須チェック（メールアドレス）
		alert("メールアドレスを入力してください。");
		memberform.MEMBER_MAIL.focus();
		return false;
	} else if(mail.length > 128){//桁数チェック（メールアドレス）
		alert("メールアドレスは128文字以内まで入力してください。");
		memberform.MEMBER_MAIL.focus();
		return false;		
	}
		
	if((zipCode=="") || (zipCode.length <= 6)){//必須チェック（郵便番号）
		alert("郵便番号を入力してください。");
		memberform.MEMBER_ZIPCODE.focus();
        return false;
 	}
	
	if(add_1.length == 0){//必須チェック（都道府県）
		alert("都道府県を選択してください。");
		memberform.MEMBER_ADD_1.focus();
		return false;
	} else if(add_1.length > 128){//桁数チェック（都道府県）
		alert("都道府県は128文字以内まで入力してください。");
		memberform.MEMBER_ADD_1.focus();
		return false;		
	} 
	
	if(add_2.length == 0){//必須チェック（市区町村）
		alert("市区町村を入力してください。");
		memberform.MEMBER_ADD_2.focus();
		return false;
	} else if(add_2.length > 128){//桁数チェック（市区町村）
		alert("市区町村は128文字以内まで入力してください。");
		memberform.MEMBER_ADD_2.focus();
		return false;		
	} 
	
	if(add_3.length == 0){//必須チェック（丁目、番地、建物名）
		alert("丁目、番地、建物名を入力してください。");
		memberform.MEMBER_ADD_3.focus();
		return false;
	} else if(add_3.length > 128){//桁数チェック（丁目、番地、建物名）
		alert("丁目、番地、建物名は128文字以内まで入力してください。");
		memberform.MEMBER_ADD_3.focus();
		return false;		
	} 
	
	if(memberType.length == 0){//必須チェック（会員種別）
		alert("会員種別を選択してください。");
		memberform.MEMBER_TYPE.focus();
		return false;
	}
	
	return true;
}

function openzipcode(joinform){			
	var url="./Zipcode.member";	
	open(url, "confirm", "toolbar=no,location=no,status=no,menubar=no,"+
						 "scrollbars=yes,resizable=no,width=500,height=400");
}

function gNumCheck(){
	if(event.keyCode >=48 && event.keyCode <=57) {
		return true;
	}else{
		event.returnValue=false;	
	}
}

