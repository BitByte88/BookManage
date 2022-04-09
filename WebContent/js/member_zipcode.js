function setZipcode(zipcode,add_1,add_2,add_3){
	opener.document.forms[0].MEMBER_ZIPCODE.value=zipcode;
	opener.document.forms[0].MEMBER_ADD_1.value=add_1;
	opener.document.forms[0].MEMBER_ADD_2.value=add_2;
	opener.document.forms[0].MEMBER_ADD_3.value=add_3;
	self.close();
}