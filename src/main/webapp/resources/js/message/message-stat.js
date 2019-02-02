var colums 	= [];
$(document).ready(function(){
	onClickMenu(5);
	
	coins.sort(function(x, y){
		return ((x == y) ? 0 : ((x > y) ? 1 : -1 ));
	});
    
	var cols = [];
	cols.push({"field" : "date", "title" : "날짜", "halign": 'center', "width" : "150px", "styler" : alignCenter, "formatter" : toDateStr});
	cols.push({"field" : "total", "title" : "합계", "halign": 'center', "width" : "80px", "styler" : alignCenter, "formatter" : toSumStr});
	for(var i = 0 ; i < coins.length; i++){
		var col = {
			"field" : coins[i],
			"title" : coins[i],
			"width" : "80px",
			"halign": 'center', 
			"styler" : alignCenter,
			"formatter" : toCoinStr
		}
		cols.push(col);
	}
	
	colums.push(cols);
	
	fn_onInitDataGrid1();
	fn_onInitDataGrid2();
	fn_onInitDataGrid3();
})


/** 일일 사용자 메세지 데이터 **/
function fn_onInitDataGrid1(){
	$('#dg1').datagrid({
		"data": msgCntByDay,
		title : "일별  메세지 수",
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns: colums
	});
}

/** 월별 사용자 메세지 데이터 **/
function fn_onInitDataGrid2(){
	$('#dg2').datagrid({
		"data": msgCntByMonth,
		title : "월별 메세지 수",
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns: colums
	});
}

/** 일별 메세지를 보낸 사용자 수 **/
function fn_onInitDataGrid3(){
	$('#dg3').datagrid({
		"data": msgUserCntByDay,
		title : "일별 메세지를 보낸 사용자 수",
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns: colums
	});
}

function toDateStr(value){
	return "<strong>"+ value + "</strong>";
}

function toSumStr(value){
	return "<b style='color : blue'>"+ value + "</b>";
}

function toCoinStr(value){
	if(value < 5){
		return "<b style='color : red'>" + value + "</b>";
	} else{
		return value;
	}
}

function doClearMyMessage(){
    var c = confirm("정말로 삭제 하시곘습니까?");
    if (c){
    	$.ajax({
			type: "DELETE",
			url: getContextPath() + "/client-messages/my",
			dataType: 'JSON',
			async : false,
			success:function(json){
				alert(json.cnt + " 개 삭제 완료하였습니다.");
				window.location.reload();
			},
			error:function(request,status,error){
		        console.log("code:"  + request.status + "\n"+"message:" + request.responseText + "\n"+"error:" + error);
			}
		});
    }
}

