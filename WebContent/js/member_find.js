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