var rname=null;

$(function() {
	$.post("../../servlet/adminInfoServlet", {
		op : "getLoginInfo"
	}, function(data) {
		if (data == "" || data == null) {
			location.href = "../login.html";
		} else {
			$("#index_loginuser").text(data.aname);
			$("#photopic").attr("src", "../../" + data.photos);
			rname=data.rname;
		
			
			if(rname=="admin"){
				$('#index_menu_tree').tree({
					url : "/wowo/back/json/admin.json"
				});
			}else if(rname=="管理员"){
				$('#index_menu_tree').tree({
					url : "/wowo/back/json/generalAdmin.json"
				});
			}else if(rname=="卖家"){
				$('#index_menu_tree').tree({
					url : "/wowo/back/json/seller.json"
				});
			}
		}

	}, "json");

	$('#index_content_info').tabs('add', {
		title : '指令汇科技',
		closable : true,
		fit : true,
		href : "welcome.html"

	});
	


	$('#index_menu_tree').tree({
		onClick : function(node) {
			var tabs = $('#index_content_info');
			var title="指令汇科技";
			var href="welcome.html";
			
			
			
			if (node.id == "index_role") {

				if (tabs.tabs("exists", "角色管理")) {
					// 如果存在则选中
					tabs.tabs("select", "角色管理");
					return;	
				} else {
					title="角色管理";
					href="roles.html";
				}
			} else if (node.id == "index_admin") {

				if (tabs.tabs("exists", "管理员信息")) {
					// 如果存在则选中
					tabs.tabs("select", "管理员信息");
					return;
				} else {
					title="管理员信息";
					href="admin.html";
				}

			}else if(node.id=="index_shopping3"){
				if (tabs.tabs("exists", "店铺信息管理")) {
					// 如果存在则选中
					tabs.tabs("select", "店铺信息管理");
					return;
				} else {
					title="店铺信息管理";
					href="manaagershopping.html";
				}

				
			}else if(node.id=="index_goods1"){
				if (tabs.tabs("exists", "添加商品信息")) {
					// 如果存在则选中
					tabs.tabs("select", "添加商品信息");
					return;
				} else {
					title="添加商品信息";
					href="addgoods.html";
				}
			}else if(node.id=="index_goods2"){
				if (tabs.tabs("exists", "查看商品信息")) {
					// 如果存在则选中
					tabs.tabs("select", "查看商品信息");
					return;
				} else {
					title="查看商品信息";
					href="showgoods.html";
				}
			}else if(node.id=="index_goodstype"){
				if (tabs.tabs("exists", "商品类型管理")) {
					// 如果存在则选中
					tabs.tabs("select", "商品类型管理");
					return;
				} else {
					title="商品类型管理";
					href="goodstype.html";
				}
			}else if(node.id=="index_shopping2"){
				if (tabs.tabs("exists", "查看店铺信息")) {
					// 如果存在则选中
					tabs.tabs("select", "查看店铺信息");
					return;
				} else {
					title="查看店铺信息";
					href="showShopping.html";
				}
			}else if(node.id=="index_shopping1"){
				if (tabs.tabs("exists", "审核店铺信息")) {
					// 如果存在则选中
					tabs.tabs("select", "审核店铺信息");
					return;
				} else {
					title="审核店铺信息";
					href="checkStatus.html";
				}
			}else if(node.id=="index_user"){
				if (tabs.tabs("exists", "会员信息")) {
					// 如果存在则选中
					tabs.tabs("select", "会员信息");
					return;
				} else {
					title="会员信息";
					href="user.html";
				}
			}else if(node.id=="index_order"){
				if (tabs.tabs("exists", "订单管理")) {
					// 如果存在则选中
					tabs.tabs("select", "订单管理");
					return;
				} else {
					title="订单管理";
					href="order.html";
				}
			}else{
				return;
			}
			
			
			
			
			tabs.tabs("add", {
				title : title,
				closable : true,
				fit : true,
				href : href
			});
			

		}
	});

});

function headexit() {

	$.post("../../servlet/adminInfoServlet", {
		op : "LoginOut"
	}, function(data) {
		if (data!=null){
			location.href="../login.html";	
		}
	});
}




		var dayofweek=new Array();
		dayofweek[0]="星期天";
		dayofweek[1]="星期一";
		dayofweek[2]="星期二";
		dayofweek[3]="星期三";
		dayofweek[4]="星期四";
		dayofweek[5]="星期五";
		dayofweek[6]="星期六";
		
	window.setInterval("show()",1000);
	
	function show(){
		var now=new Date();
		var year=now.getFullYear();
		var month=now.getMonth()+1;
		var day=now.getDate();
		
		var week=dayofweek[now.getDay()];
		
		var h=now.getHours();
		if(h<=9){
			h="0"+h;	
		}
		var m=now.getMinutes();
		if(m<=9){
			m="0"+m;	
		}
		var s=now.getSeconds();
		if(s<=9){
			s="0"+s;	
		}
	
		document.getElementById("time").innerHTML=year+"年"+month+"月"+day+"日"+week+" "+h+":"+m+":"+s;
	}












