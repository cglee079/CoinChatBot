var limit = 1000;

$(document).ready(function(){
	onClickMenu(2);
	$('.search-opt input[type=text]').on('keydown', function(e) {
	    if (e.which == 13) {
	    	doSearch();
	    }
	});
	
	$("#startDate").datebox();
	$("#endDate").datebox();
	
	fn_onInitDataGrid();
});


function doSearch(){
	$("#dg").datagrid("load",{
		coinId : $("#coinId").val(),
		userId :$("#userId").val(),
		username :$("#username").val(),
		startDate:$("#startDate").val(),
		endDate:$("#endDate").val(),
		contents:$("#contents").val()
	})
}

function fn_onInitDataGrid(){
	$('#dg').datagrid({
		url: getContextPath() + '/client-messages/records',
		title : "사용자 메세지 리스트",
		method: 'get',
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		pagination: true,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns:[[
			{field:'row', title:'번호', width:'5%', halign:'center' , styler : alignCenter, formatter: function (value, row, index){
				var option = $("#dg").datagrid("options");
				return (option.pageNumber - 1) * option.pageSize + index + 1;
				}
			},
			{field:'coinId', title:'코인ID', width:'5%', halign:'center', styler : alignCenter},
			{field:'seq', title:'시퀀스', width:'5%', halign:'center', styler : alignCenter},
			{field:'userId', title:'유저ID', width:'10%', halign:'center', styler : alignCenter, formatter : toUserIdStr },
			{field:'username', title:'유저명', width:'15%', halign:'center', styler : alignCenter, formatter : toUsernameStr},
			{field:'contents', title:'내용', width:'45%', halign:'center', styler : alignLeft},
			{field:'date', title:'날짜', width:'15%', halign:'center', styler : alignCenter}
		]],
		queryParams: {
			coinId : $("#coinId").val(),
			userId :$("#userId").val(),
			username :$("#username").val(),
			startDate:$("#startDate").val(),
			endDate:$("#endDate").val(),
			contents:$("#contents").val(),
		}
	});
	
	$('#dg').datagrid({
		pageList: [limit, limit * 2, limit * 3, limit * 4, limit * 5],
	  	pageSize: limit
	});
	
	var pager = $('#dg').datagrid('getPager');
	pager.pagination({ 
		displayMsg : '{total} 중 {from}-{to} 사용자 메시지',
		beforePageText : '',
		afterPageText : '페이지 ',
	});
}
