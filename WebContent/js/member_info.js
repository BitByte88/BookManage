function check(form){
	var password1=form.MEMBER_PW1.value;
	var password2=form.MEMBER_PW2.value;
	var tel=form.MEMBER_TEL.value;
	var mail1=form.MEMBER_MAIL1.value;
	var mail2=form.MEMBER_MAIL2.value;
	var add_1=form.MEMBER_ADD_1.value;
	var add_2=form.MEMBER_ADD_2.value;
	var add_3=form.MEMBER_ADD_3.value;
	
	if(password1.length == 0){//必須チェック（パスワード）
		alert("パスワードを入力してください。");
		form.MEMBER_PW1.focus();
		return false;
	} else if (password1.length > 64){//桁数チェック（パスワード）
		alert("パスワードは64文字以内まで入力してください。");
		form.MEMBER_PW1.focus();
		return false;		
	}
	
	if(password2.length == 0){//必須チェック（パスワード確認）
		alert("パスワード確認を入力してください。");
		form.MEMBER_PW2.focus();
		return false;
	} else if (password2.length > 64){//桁数チェック（パスワード確認）
		alert("パスワード確認は64文字以内まで入力してください。");
		form.MEMBER_PW2.focus();
		return false;		
	}
	
	if(password1 != password2){//一致チェック（パスワード、パスワード確認）
		alert("パスワードが一致していません。");
		form.MEMBER_PW1.value="";
		form.MEMBER_PW2.value="";
		form.MEMBER_PW1.focus();
		form.MEMBER_PW2.focus();
		return false;
	}
	
	if ((form.MEMBER_NAME.value=="")||(form.MEMBER_NAME.value.length<=1)){//必須チェック（氏名）
		alert("氏名を入力してください。");
		form.MEMBER_NAME.focus();
		return false;
	} else if(form.MEMBER_NAME.value.length > 32){//桁数チェック（氏名）
		alert("氏名は32文字以内まで入力してください。");
		form.MEMBER_NAME.focus();
		return false;		
	}
	
	if ((form.MEMBER_NAME_KANA.value=="")||(form.MEMBER_NAME_KANA.value.length<=1)){//必須チェック（氏名（カナ））
		alert("氏名（カナ）を入力してください。");
		form.MEMBER_NAME_KANA.focus();
		return false;
	} else if(form.MEMBER_NAME_KANA.value.length > 32){//桁数チェック（氏名（カナ））
		alert("氏名（カナ）は32文字以内まで入力してください。");
		form.MEMBER_NAME_KANA.focus();
		return false;		
	}
	
	if(tel.length == 0){//必須チェック（電話番号）
		alert("電話番号を入力してください。");
		form.MEMBER_TEL.focus();
		return false;
	} else if(tel.length > 16){//桁数チェック（電話番号）
		alert("電話番号は16文字以内まで入力してください。");
		form.MEMBER_TEL.focus();
		return false;		
	}
	
	if(mail1.length == 0 || mail2.length ==0){//必須チェック（メールアドレス）
		alert("メールアドレスを入力してください。");
		form.MEMBER_MAIL1.focus();
		form.MEMBER_MAIL2.focus();
		return false;
	} else if((mail1.length + mail2.length) > 127){//桁数チェック（メールアドレス）
		alert("メールアドレスは128文字以内まで入力してください。");
		form.MEMBER_MAIL1.focus();
		form.MEMBER_MAIL2.focus();
		return false;		
	} 
	
	if((zipCode=="") || (zipCode.length <= 6)){//必須チェック（郵便番号）
		alert("郵便番号を入力してください。");
      	joinform.MEMBER_ZIPCODE.focus();
        return false;
 	}
	
	if(add_1.length == 0){//必須チェック（都道府県）
		alert("都道府県を選択してください。");
		form.MEMBER_ADD_1.focus();
		return false;
	} else if(add_1.length > 128){//桁数チェック（都道府県）
		alert("都道府県は128文字以内まで入力してください。");
		form.MEMBER_ADD_1.focus();
		return false;		
	} 
	
	if(add_2.length == 0){//必須チェック（市区町村）
		alert("市区町村を入力してください。");
		form.MEMBER_ADD_2.focus();
		return false;
	} else if(add_2.length > 128){//桁数チェック（市区町村）
		alert("市区町村は128文字以内まで入力してください。");
		form.MEMBER_ADD_2.focus();
		return false;		
	} 
	
	if(add_3.length == 0){//必須チェック（丁目、番地、建物名）
		alert("丁目、番地、建物名を入力してください。");
		form.MEMBER_ADD_3.focus();
		return false;
	} else if(add_3.length > 128){//桁数チェック（丁目、番地、建物名）
		alert("丁目、番地、建物名は128文字以内まで入力してください。");
		form.MEMBER_ADD_3.focus();
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

function out(){
	var Answer = confirm("退会しますか");
 		if (Answer == true){ 
 		location.href = "./MemberOut.member";
 	}
 }