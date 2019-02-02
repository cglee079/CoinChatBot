/*** 데이터 그리드 공통 ****/
function getComboboxEditorValue(tg, index, fieldNm){
	var ed = tg.datagrid('getEditor', { index: index,  field: fieldNm });
	return $(ed.target).combobox('getValue');
}

function getRowIndex(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}

function editrow(target){
    $('#dg').datagrid('beginEdit', getRowIndex(target));
}

function alignCenter(value, row, index){
	return 'text-align:center';
}

function alignLeft(value, row, index){
	return 'text-align:left';
}

function alignRight(value, row, index){
	return 'text-align:right';
}


/* 사용자 ID */
function toUserIdStr(value, row, index){
	if(row.enabled || row.enabled == undefined){
		return "<a class='grid-valid-userid' onClick=\"sendMessageToOne('" + row.coinId + "', '" +  row.userId + "')\">"+ value+ "</a>";
	} else{
		return "<i class='grid-invalid-userid'>" + value + "</i>";
	}
}


/* 사용자 이름 */
function toUsernameStr(value, row, index){
	if(!value){
		value = '';
	}
	return "<a style='cursor : pointer;' onClick = 'searchUser(\"" + row.userId + "\")'>" + value + "</a>";
}

function toUsernameStr2(value, row, index){
	if(!value){
		value = '알수없음';
	}
	return "<a style='cursor : pointer;' onClick = 'searchUserMessage(\"" + row.userId + "\")'>" + value + "</a>";
}


/* 사용자 검색  */
function searchUser(userId){
	var c = confirm("해당 회원을 검색하시겠습니까?");
	if(c){
		location.href = getContextPath() + "/clients/" + userId;
	}
}

/* 사용자 메세지 검색 */
function searchUserMessage(userId){
	var c = confirm("해당 회원의 메세지를 검색하시겠습니까?");
	if(c){
		var openNewWindow = window.open("about:blank");
		openNewWindow.location.href = getContextPath() + "/client-messages/" + userId;
	}
}

/* 사용자에게 메세지 보내기 */
function sendMessageToOne(coinId, userId, userName){
	var c = confirm("해당 사용자에게 메세지를 보내시겠습니까?")
	if(c){
		var data = {
			"caseId" : "one",
			"coinId" : coinId,
			"userId" : userId,
		};
		
		var openNewWindow = window.open("about:blank");
		openNewWindow.location.href = getContextPath() +  "/messages/post" + encodeURIParam(data);
	}
}