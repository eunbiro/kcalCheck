function fn_Check() {
	var fn = document.frm;
	
	if (fn.member_no.value == "") {
		alert("회원번호를 입력해주세요");
		fn.member_no.focus();
		return false;
	}
	
	if (fn.eat_date.value == "") {
		alert("날짜를 입력해주세요");
		fn.eat_date.focus();
		return false;
	}
	
	if (fn.time.value == "") {
		alert("식사시간을 선택해주세요");
		fn.time.focus();
		return false;
	}
	
	if (fn.food.value == "") {
		alert("음식을 선택해주세요");
		fn.food.focus();
		return false;
	}
	
	fn.submit();
}

function chkDelete(food_no, member_no) {
	const result = confirm("정말 삭제하시겠습니까?");
	
	if (result) {
		const url = location.origin;
		location.href = url + "/kcalCheck/delete?food_no=" + food_no + "&member_no=" + member_no;
	} else {
		return false;
	}
}

function chk_cancle(member_no) {
		const url = location.origin;
		location.href = url + "/kcalCheck/alonelist?&member_no=" + member_no;
}