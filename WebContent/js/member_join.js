function check(){
	var id=joinform.MEMBER_ID.value;
	var password1=joinform.MEMBER_PW1.value;
	var password2=joinform.MEMBER_PW2.value;
	var name=joinform.MEMBER_NAME.value;
	var nameKana=joinform.MEMBER_NAME_KANA.value;
	var tel=joinform.MEMBER_TEL.value;
	var mail1=joinform.MEMBER_MAIL1.value;
	var mail2=joinform.MEMBER_MAIL2.value;
	var zipCode=joinform.MEMBER_ZIPCODE.value;
	var add_1=joinform.MEMBER_ADD_1.value;
	var add_2=joinform.MEMBER_ADD_2.value;
	var add_3=joinform.MEMBER_ADD_3.value;
	
	var forms = document.getElementById("joinform");

	if(id.length == 0){//必須チェック（アカウント）
		alert("アカウントを入力してください。");
		joinform.MEMBER_ID.focus();
		return false;
	} else if (id.length > 32){//桁数チェック（アカウント）
		alert("アカウントは32文字以内まで入力してください。");
		joinform.MEMBER_ID.focus();
		return false;		
	}
	
	if(password1.length == 0){//必須チェック（パスワード）
		alert("パスワードを入力してください。");
		joinform.MEMBER_PW1.focus();
		return false;
	} else if (password1.length > 64){//桁数チェック（パスワード）
		alert("パスワードは64文字以内まで入力してください。");
		joinform.MEMBER_PW1.focus();
		return false;		
	}
	
	if(password2.length == 0){//必須チェック（パスワード確認）
		alert("パスワード確認を入力してください。");
		joinform.MEMBER_PW2.focus();
		return false;
	} else if (password2.length > 64){//桁数チェック（パスワード確認）
		alert("パスワード確認は64文字以内まで入力してください。");
		joinform.MEMBER_PW2.focus();
		return false;		
	}
	
	if(password1 != password2){//一致チェック（パスワード、パスワード確認）
		alert("パスワードが一致していません。");
		joinform.MEMBER_PW1.value="";
		joinform.MEMBER_PW2.value="";
		joinform.MEMBER_PW1.focus();
		joinform.MEMBER_PW2.focus();
		return false;
	}
	
	if ((name=="")||(name.length<1)){//必須チェック（氏名）
		alert("氏名を入力してください。");
		joinform.MEMBER_NAME.focus();
		return false;
	} else if(name.length > 32){//桁数チェック（氏名）
		alert("氏名は32文字以内まで入力してください。");
		joinform.MEMBER_NAME.focus();
		return false;		
	}
	
	if ((nameKana=="")||(nameKana.length<1)){//必須チェック（氏名（カナ））
		alert("氏名（カナ）を入力してください。");
		joinform.MEMBER_NAME_KANA.focus();
		return false;
	} else if(nameKana.length > 32){//桁数チェック（氏名（カナ））
		alert("氏名（カナ）は32文字以内まで入力してください。");
		joinform.MEMBER_NAME_KANA.focus();
		return false;		
	}
	
	if(tel.length == 0){//必須チェック（電話番号）
		alert("電話番号を入力してください。");
		joinform.MEMBER_TEL.focus();
		return false;
	} else if(tel.length > 16){//桁数チェック（電話番号）
		alert("電話番号は16文字以内まで入力してください。");
		joinform.MEMBER_TEL.focus();
		return false;		
	}
	
	if(mail1.length == 0 || mail2.length ==0){//必須チェック（メールアドレス）
		alert("メールアドレスを入力してください。");
		joinform.MEMBER_MAIL1.focus();
		joinform.MEMBER_MAIL2.focus();
		return false;
	} else if((mail1.length + mail2.length) > 127){//桁数チェック（メールアドレス）
		alert("メールアドレスは128文字以内まで入力してください。");
		joinform.MEMBER_MAIL1.focus();
		joinform.MEMBER_MAIL2.focus();
		return false;		
	} 
	
	if((zipCode=="") || (zipCode.length <= 6)){//必須チェック（郵便番号）
		alert("郵便番号を入力してください。");
      	joinform.MEMBER_ZIPCODE.focus();
        return false;
 	}
	
	if(add_1.length == 0){//必須チェック（都道府県）
		alert("都道府県を選択してください。");
		joinform.MEMBER_ADD_1.focus();
		return false;
	} else if(add_1.length > 128){//桁数チェック（都道府県）
		alert("都道府県は128文字以内まで入力してください。");
		joinform.MEMBER_ADD_1.focus();
		return false;		
	} 
	
	if(add_2.length == 0){//必須チェック（市区町村）
		alert("市区町村を入力してください。");
		joinform.MEMBER_ADD_2.focus();
		return false;
	} else if(add_2.length > 128){//桁数チェック（市区町村）
		alert("市区町村は128文字以内まで入力してください。");
		joinform.MEMBER_ADD_2.focus();
		return false;		
	} 
	
	if(add_3.length == 0){//必須チェック（丁目、番地、建物名）
		alert("丁目、番地、建物名を入力してください。");
		joinform.MEMBER_ADD_3.focus();
		return false;
	} else if(add_3.length > 128){//桁数チェック（丁目、番地、建物名）
		alert("丁目、番地、建物名は128文字以内まで入力してください。");
		joinform.MEMBER_ADD_3.focus();
		return false;		
	} 
	
	return true;
}

function openConfirmId(joinform){			
	var id=joinform.MEMBER_ID.value;
	var url="./MemberIDCheckAction.member?MEMBER_ID="+joinform.MEMBER_ID.value;
	
	if(id.length == 0){
		alert("アカウントを入力してください。");
		joinform.MEMBER_ID.focus();
		return false;
	}
	open(url, "confirm", "toolbar=no,location=no,status=no,menubar=no,"+
						 "scrollbars=no,resizable=no,width=500,height=200");
}

function openZipcode(joinform){			
	var url="./Zipcode.member"
	open(url, "confirm", "toolbar=no,location=no,"
						+"status=no,menubar=no,"
						+"scrollbars=yes,resizable=no,"
						+"width=500,height=400");
}	

function gNumCheck(){
	if(event.keyCode >=48 && event.keyCode <=57) {
		return true;
	}else{
		event.returnValue=false;	
	}
}	