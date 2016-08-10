<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>窝窝团</title>
	<link rel="stylesheet" type="text/css" href="../../easyui/css/easyui.css">
	<link rel="stylesheet" type="text/css" href="../../easyui/css/icon.css">
	<link rel="stylesheet" type="text/css" href="../../easyui/css/demo.css">
	<link rel="stylesheet" type="text/css" href="../css/index.css">
	<script type="text/javascript" src="../../js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="../../easyui/js/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:110px;background:#B3DFDA;padding:10px">
		<img src="../img/logo.png"   id="logo" />
		<div class="navy_header" >
			<img src="../../images/zanwu.jpg"  id="photopic" />
			<div>
				<p id="welcome">当前用户: ${sessionScope.adminInfo.name }<span></span></p>
				<p id="change"> [ 注销 ] </p>
				
				<a href="javascript:void(0)" id="mb" class="easyui-menubutton"     
				        data-options="menu:'#mm'">个人中心</a>   
				<div id="mm" style="width:150px;">   
				    <div >修改密码</div>   
				    <div >联系我们</div>   
				    <div >退出</div>       
				</div>  	
			</div>
		</div>
	</div>
	<div data-options="region:'west',split:true,title:'菜单'" style="width:180px;padding:10px;">
		<ul class="easyui-tree">
				<li>
					<span>主菜单</span>
					<ul>
						<li>
							角色管理
						</li>
						<li>
							管理员信息
						</li>
						<li>
							会员信息
						</li>
						<li>
							商品类型信息
						</li>
						<li>
							<span>店铺信息</span>
							<ul>
								<li>审核店铺信息</li>
								<li>查看店铺信息</li>
								<li>管理店铺信息</li>
							</ul>
						</li>
						
					</ul>
				</li>
				<li>
					<span>商品信息</span>
					<ul>
						<li>添加商品信息</li>
						<li>查看商品信息</li>
						<li>修改商品信息</li>
					</ul>
				</li>
				<li>消息管理</li>
				<li>订单管理</li>
			</ul>
	
	
	</div>
	<div data-options="region:'east',split:true,collapsed:true,title:'帮助'" style="width:100px;padding:10px;">
	
	
	</div>
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">
	
		<center><p>指令汇科技 &copy; 版权所有</p></center>
	
	</div>
	<div data-options="region:'center',title:'内容'">
	
	
	
	
	
	
	
	</div>
</body>
</html>