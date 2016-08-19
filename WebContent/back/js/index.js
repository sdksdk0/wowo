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
					href="addshopping.html";
				}
			}else if(node.id=="index_goods2"){
				if (tabs.tabs("exists", "查看商品信息")) {
					// 如果存在则选中
					tabs.tabs("select", "查看商品信息");
					return;
				} else {
					title="查看商品信息";
					href="showshopping.html";
				}
			}else if(node.id=="index_goods3"){
				if (tabs.tabs("exists", "修改商品信息")) {
					// 如果存在则选中
					tabs.tabs("select", "修改商品信息");
					return;
				} else {
					title="修改商品信息";
					href="updateshopping.html";
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
