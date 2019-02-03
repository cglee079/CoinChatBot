$(document).ready(function(){
	onClickMenu(1);

	$('.search-opt input[type=text]').on('keydown', function(e) {
	    if (e.which == 13) {
	    	searchClient();
	    }
	});
	
	$("#openDate").datebox();
	$("#msgDate1").datebox();
	$("#msgDate2").datebox();
	
	fn_onInitDataGrid();
});

function parseData(data){
	if(!data){
		data = {};
	}
	
	data.coinId 	= $("#coinId").val(),
	data.userId 	= $("#userId").val(),
	data.username 	= $("#username").val(),
	data.language	= $("#language").val(),
	data.marketId	= $("#marketId").val(),
	data.stateId	= $("#stateId").val(),
	data.enabled 	= $("#enabled").val(),
	data.openDate 	= $("#openDate").val(),
	data.msgDate1 	= $("#msgDate1").val(),
	data.msgDate2	= $("#msgDate2").val()
	
	return data;
}
function sendMessageToAll(){
	var c = confirm("해당 조건의 모든 사용자들에게 메세지를 보내시겠습니까?")
	if(c){
		var data = parseData({"caseId" : "all"})
		
		var openNewWindow = window.open("about:blank");
		openNewWindow.location.href = getContextPath() +  "/messages/post" + encodeURIParam(data);
	}
}

function searchClient(){
	$("#dg").datagrid("load", parseData());
}

function fn_onInitDataGrid(){
	/** Datagrid Combobox Define **/
	var langs	 	= [];
	var stateIds 	= [];
	var marketIds 	= [];
	var errCnts 	= [];
	var dayloops 	= [];
	var timeloops 	= [];
	var options;
	var temp;
	
	//Langs 정의
	var options = $("#language option");
	for(var i = 1; i < options.length; i++){
		temp = $(options[i]);
		langs.push({"id" : temp.text(), "text" : temp.text()});
	}
	
	//State 정의
	var options = $("#stateId option");
	for(var i = 1; i < options.length; i++){
		temp = $(options[i]);
		stateIds.push({"id" : temp.text(), "text" : temp.text()});
	}
	
	//MarketIds 정의
	options = $("#marketId option");
	for(var i = 1; i < options.length; i++){
		temp = $(options[i]);
		marketIds.push({"id" : temp.text(), "text" : temp.text()});
	}
	
	//ErrCnts 정의
	for(var i = 0; i <= 5; i++){
		errCnts.push({"id" : i, "text" : i});
	}
	
	//Dayloops 데이터 정의
	for(var i = 0; i <= 7; i++){
		dayloops.push({"id" : i, "text" : "매 " +  i + "일"});
	}
	
	//Timeloops 데이터 정의
	for(var i = 0; i <= 12; i++){
		timeloops.push({"id" : i, "text" : "매 " +  i + "시간"});
	}
	
	var enableds = [
		{"id": "1", "text" : "true"},
		{"id": "0", "text" : "false"}
	];

	$('#dg').datagrid({
		url: getContextPath() + '/clients/records',
		title : "사용자 리스트",
		method: 'get',
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		pagination: true,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns:[[
			{field:'action', title: '편집', width:'100px', halign:'center' , styler : alignCenter, formatter:function(value,row,index){
                if (row.editing){
                    var s = '<a href="javascript:void(0)" onclick="saverow(this)" class="grid-btn-save">저장</a> ';
                    var c = '<a href="javascript:void(0)" onclick="cancelrow(this)" class="grid-btn-cancle">취소</a>';
                    return s+c;
                } else {
                    var e = '<a href="javascript:void(0)" onclick="editrow(this)" class="grid-btn-modify">수정</a> ';
                    var d = '<a href="javascript:void(0)" onclick="deleterow(this)" class="grid-btn-delete">삭제</a>';
                    return e+d;
                }
			}},
			{field:'row', title:'번호', width:'50px', halign:'center' , styler : alignCenter, formatter: function (value, row, index){
				var option = $("#dg").datagrid("options");
				return (option.pageNumber - 1) * option.pageSize + index + 1;
				}
			},
			{field:'coinId', title:'코인ID', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'userId', title:'유저ID', width:'100px', halign:'center', sortable : "true", styler : alignCenter, formatter : toUserIdStr},
			{field:'lang', title:'언어', width:'100px', halign:'center', sortable : "true", styler : alignCenter,
				 editor:{
	                    type:'combobox',
	                    options:{
	                        valueField:'id',
	                        textField:'text',
	                        data: langs,
	                        required:true
	                    }
            }},
			{field:'localTime', title:'시차', width:'100px', halign:'center', styler : alignCenter},
			{field:'username', title:'유저명', width:'150px', halign:'center', sortable : "true", styler : alignCenter, formatter : toUsernameStr2},
			{field:'marketId', title:'거래소', width:'150px', halign:'center', sortable : "true", styler : alignCenter, 
		         editor:{
                    type:'combobox',
                    options:{
                        valueField:'id',
                        textField:'text',
                        data: marketIds,
                        required:true
                    }
                }
			},
			{field:'stateId', title:'메뉴', width:'150px', halign:'center', sortable : "true", styler : alignCenter,
				 editor:{
                    type:'combobox',
                    options:{
                        valueField:'id',
                        textField:'text',
                        data: stateIds,
                        required:true
                    }
				 }
			},
			{field:'dayloop', title:'일일주기', width:'100px', halign:'center', sortable : "true", styler : alignCenter, formatter : toDayloopStr, 
				 editor:{
                    type:'combobox',
                    options:{
                        valueField:'id',
                        textField:'text',
                        data: dayloops,
                        required:true
                    }
				 }
	        },
			{field:'timeloop', title:'시간주기', width:'100px', halign:'center', sortable : "true", styler : alignCenter, formatter : toTimeloopStr,
				 editor:{
                    type:'combobox',
                    options:{
                        valueField:'id',
                        textField:'text',
                        data: timeloops,
                        required:true
                    }
                }
	        },
			{field:'invest', title:'투자금', width:'120px', halign:'center', sortable : "true", styler : alignRight, formatter : toMoneyStr},
			{field:'coinCnt', title:'코인개수', width:'120px', halign:'center', sortable : "true",  styler : alignRight, formatter : toCoinCntStr},
			{field:'avgPrice', title:'평단가', width:'120px', halign:'center', styler : alignRight, formatter : toAvgCoinPriceStr},
			{field:'enabled', title:'유효', width:'70px', halign:'center', sortable : "true", styler : alignCenter,
				 editor:{
                    type:'combobox',
                    options:{
                        valueField:'id',
                        textField:'text',
                        data: enableds,
                        required:true
                    }
                }
			},
			{field:'errCnt', title:'에러', width:'70px', halign:'center', sortable : "true", styler : alignCenter, 
				editor:{
                    type:'combobox',
                    options:{
                        valueField:'id',
                        textField:'text',
                        data: errCnts,
                        required:true
                    }
				}
            },
			{field:'openDate', title:'시작일', width:'200px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'reopenDate', title:'재시작일', width:'200px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'closeDate', title:'종료일', width:'200px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'laMsgDate', title:'최종메세지', width:'200px', halign:'center', sortable : "true", styler : alignCenter},
		]],
		queryParams : parseData(),
		onEndEdit:function(index, row){
			var tg = $("#dg");
			var lang = getComboboxEditorValue(tg, index, "lang");
			var marketId = getComboboxEditorValue(tg, index, "marketId");
			var stateId = getComboboxEditorValue(tg, index, "stateId");
			var dayloop = getComboboxEditorValue(tg, index, "dayloop");
			var timeloop = getComboboxEditorValue(tg, index, "timeloop");
			var enabled = getComboboxEditorValue(tg, index, "enabled");
			var errCnt = getComboboxEditorValue(tg, index, "errCnt");
			
			$.ajax({
				type: "POST",
				url: getContextPath() + "/clients/" + row.coinId + "/" + row.userId,
				data: {
					"_method"	: "PUT",
					"lang" 		: lang,
					"marketId"	: marketId,
					"stateId"	: stateId,
					"dayloop"	: dayloop,
					"timeloop"	: timeloop,
					"enabled"	: enabled,
					"errCnt"	: errCnt
				},
				dataType: 'JSON',
				async : false,
				success:function(json){
					alert("수정 완료하였습니다.");
				},
				error:function(request,status,error){
			        console.log("code:"  + request.status + "\n"+"message:" + request.responseText + "\n"+"error:" + error);
				}
			});
		},
		onBeforeEdit:function(index,row){
		    row.editing = true;
		    $(this).datagrid('refreshRow', index);
		},
		onAfterEdit:function(index,row){
		    row.editing = false;
		    $(this).datagrid('refreshRow', index);
		},
		onCancelEdit:function(index,row){
		    row.editing = false;
		    $(this).datagrid('refreshRow', index);
		}
	});
	
	$('#dg').datagrid({
		pageList: [100, 200, 300, 400, 500],
	  	pageSize: 100,
	});
	
	var pager = $('#dg').datagrid('getPager');    // get the pager of datagrid
	pager.pagination({ 
		displayMsg : '{total} 중 {from}-{to} 사용자',
		beforePageText : '',
		afterPageText : '페이지 ',
	});
}
	
/** DataGrid Formatter **/
function toMoneyStr(value, row, index){
	if(value){
		if(true){ //TODO KRW
			return value.toLocaleString() + " 원";
		}
		
		if(row.marketId.startsWith("20")){ //USD
			return "$ " + value.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")
		}
	}
}

function toCoinCntStr(value, row, index){
	if(value){
		return value + " 개"
	}
}

function toAvgCoinPriceStr(value, row, index){
	if(row.invest != 0 && row.coinCnt != 0){
		var avgPrice = parseInt(row.invest / row.coinCnt);
		return toMoneyStr(avgPrice, row,index);
	}else{
		return "";
	}
}

function toDayloopStr(value, row, index){
	if(value == 0){ return "X"; }
	return "매 " + value + "일";
}

function toTimeloopStr(value, row, index){
	if(value == 0){ return "X";	}
	return "매 " + value + "시간";
}

/**** *****/

function deleterow(target){
	var row = $("#dg").datagrid('getRows')[getRowIndex(target)];
    var c = confirm("정말로 삭제 하시곘습니까?");
    if (c){
    	$.ajax({
			type: "DELETE",
			url: getContextPath() + "/clients/" + row.coinId + "/" + row.userId,
			dataType: 'JSON',
			async : false,
			success:function(json){
				$('#dg').datagrid('deleteRow', getRowIndex(target));
				alert("삭제 완료하였습니다.");
			},
			error:function(request,status,error){
		        alert("code:"  + request.status + "\n"+"message:" + request.responseText + "\n"+"error:" + error);
			}
		});
    }
}

function saverow(target){
	var c= confirm("정말로 수정하시곘습니까?");
	if(c){
		$('#dg').datagrid('endEdit', getRowIndex(target));
	}
}

function cancelrow(target){
    $('#dg').datagrid('cancelEdit', getRowIndex(target));
}

