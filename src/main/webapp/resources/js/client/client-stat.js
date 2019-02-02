$(document).ready(function(){
	onClickMenu(4);
	fn_onInitDataGrid1();
	fn_onInitDataGrid2();
})

/** 일일 신규사용자 데이터 **/
function fn_onInitDataGrid1(){
	$('#dg1').datagrid({
		"data": newClientByDay,
		title : "일별 신규사용자",
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns:[[
			{field:'date', title:'날짜(일)', width:'200px', halign:'center', styler : alignCenter, formatter : function (value){ return "<strong>" +  value +"</strong>";}},
			{field:'count', title:'신규사용자', width:'200px', halign:'center', styler : alignRight, formatter : function (value){ return value + " 명";}}
		]]
	});
}

/** 월별 신규사용자 데이터 **/
function fn_onInitDataGrid2(){
	$('#dg2').datagrid({
		"data": newClientByMonth,
		title : "월별 신규사용자",
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns:[[
			{field:'date', title:'날짜(월)', width:'200px', halign:'center', styler : alignCenter, formatter : function (value){ return "<strong>" +  value +"</strong>";}},
			{field:'count', title:'신규사용자', width:'200px', halign:'center', styler : alignRight, formatter : function (value){ return value + " 명";}}
		]]
	});
}

