
		
 var ue = UE.getEditor('editor');
 
 
 

 
 $(function(){
	 $.post("../../servlet/shoppingInfoServlet",{op:"findAlltypes"},function(data){
			var obj=$("#manager_shopping_tid");
		
			$.each(data,function(index,item){
				obj.append($("<option value='"+item.tid+"'>"+item.tname+"</option>"));
			});
		},"json");
	 
	 //查询是否已经开了店铺了，每个用户只能开一个店铺
	 $.post("../../servlet/shoppingInfoServlet",{op:"findshoppingByAid"},function(data){
		 data=parseInt(data);

		 if(data>0){
			 $.messager.show({title:'温馨提示',msg:'每个用户只能开一个店铺，如果需要开连锁店请联系管理员哦！',timeout:5000,showType:'slide'}); 
			 $("#add_shoppingInfo").css("background-color","gray");
			 $("#add_shoppingInfo").attr("disabled","disabled"); 
			 
			 
			 $.post("../../servlet/shoppingInfoServlet",{op:"findShopping"},function(data){
				 if(data!=null){
					 $("#manager_shopping_sname").val(data.sname);
					 $("#manager_shopping_tid  option:selected").text(data.tname);
					 $("#manager_shopping_prov  option:selected").text(data.prov);
					 $("#manager_shopping_city  option:selected").text(data.city);
					 $("#manager_shopping_area  option:selected").text(data.area);
					 $("#manager_shopping_addr").val(data.points);
					 $("#manager_shopping_tel").val(data.tel);
					 $("#manager_shopping_date").datebox("setValue",data.stime);
					 ue.setContent(data.info);
				 }else{
					 $.messager.alert('未知异常','初始化错误','error');   
				 }
			 },"json");
			 
			
		 }
		 
	 });
	 
	 
		 
 });
 
 //添加店铺信息
 function add_ShoppingInfo() {
	 
	 
		 var sname=$.trim($("#manager_shopping_sname").val());
		 var tid=$.trim($("#manager_shopping_tid").val());
		 var prov=$.trim($("#manager_shopping_prov  option:selected").text());
		 var city=$.trim($("#manager_shopping_city  option:selected").text());
		 var area=$.trim($("#manager_shopping_area  option:selected").text());
		 var points=$.trim($("#manager_shopping_addr").val());
		 var tel=$.trim($("#manager_shopping_tel").val());
		 var date=$.trim($("#manager_shopping_date").datebox("getValue"));
		 var info=ue.getContent();
		 
		
		 if(prov!="--请选择省份--"){
				if(city!="--请选择城市--"){
					
					if(area!="--请选择地区--"){
						
						if(sname!="" && tid!="" &&  points!="" &&  tel!="" && info!="" ){
							$.post("../../servlet/shoppingInfoServlet",{op:"addshopping",sname:sname,tid:tid,prov:prov,city:city,
								 area:area,points:points,tel:tel,date:date,info:info},function(data){
								 data=parseInt(data);
								 if(data>0){
									 $.messager.show({title:'温馨提示',msg:'添加成功',timeout:2000,showType:'slide'});
									 $("#add_shoppingInfo").css("background-color","gray");
									 $("#add_shoppingInfo").attr("disabled","disabled"); 
								 }else{
									 $.messager.alert('失败','添加失败','error');   
								 }			 
								 
							 });
							
						}	 
					}
				}
		 }
	 }
 
 
 	//修改店铺信息
 function update_ShoppingInfo() {
	 
	 
	 var sname=$.trim($("#manager_shopping_sname").val());
	 var tid=$.trim($("#manager_shopping_tid").val());
	 var prov=$.trim($("#manager_shopping_prov  option:selected").text());
	 var city=$.trim($("#manager_shopping_city  option:selected").text());
	 var area=$.trim($("#manager_shopping_area  option:selected").text());
	 var points=$.trim($("#manager_shopping_addr").val());
	 var tel=$.trim($("#manager_shopping_tel").val());
	 var date=$.trim($("#manager_shopping_date").datebox("getValue"));
	 var info=ue.getContent();
	 
	
	 if(prov!="--请选择省份--"){
			if(city!="--请选择城市--"){
				
				if(area!="--请选择地区--"){
					
					if(sname!="" && tid!="" &&  points!="" &&  tel!="" && info!="" ){
						 $.post("../../servlet/shoppingInfoServlet",{op:"updateshopping",sname:sname,tid:tid,prov:prov,city:city,
							 area:area,points:points,tel:tel,date:date,info:info},function(data){
							 data=parseInt(data);
							 if(data>0){
								 $.messager.show({title:'温馨提示',msg:'修改成功',timeout:2000,showType:'slide'});
							 }else{
								 $.messager.alert('失败','修改失败','error');   
							 }			 
							 
						 });
						 
					}
				}
			}
	 }
 }
 
 
 

 $(function(){

		var xmlDom;

		if(window.DOMParser && !window.ActiveXOBject){
			
			var xmlhttp=new XMLHttpRequest();
			xmlhttp.open("GET","../../city.xml",false);
			xmlhttp.send(null);
			if(xmlhttp.readyState==4){
				xmlDom=xmlhttp.responseXML.documentElement;
				var a=xmlDom.getElementsByTagName("province");
			}
		}else{
			
			xmlDom=createXmlDom();
			xmlDom.async=false; 
			xmlDom.load("../../city.xml");
		}
		
		
		var pro=$("#manager_shopping_prov");
		var city=$("#manager_shopping_city");
		var district=$("#manager_shopping_area");
		
		var pros=xmlDom.getElementsByTagName("province");
		
		for(var i=0;i<pros.length;i++){
			addOption(pros[i],pro);	
		}
		
		$("#manager_shopping_prov").bind({
			change:function(){
				var code=pro.val();
				$("#manager_shopping_city").empty();
				$("#manager_shopping_area").empty();
				var opInfo=$("<option value='-1'>--请选择城市--</option>");
				$("#manager_shopping_city").prepend(opInfo);
				var opInfo=$("<option value='-1'>--请选择地区--</option>");
				$("#manager_shopping_area").prepend(opInfo);
				
				for(var i=0;i<pros.length;i++){
					if(pros[i].nodeType==1 && pros[i].getAttribute("postcode")==code){
						var cities=pros[i].childNodes;
						for(var j=0;j<cities.length;j++){
							if(cities[j].nodeType==1){
								addOption(cities[j],city);	
							}
						}
					}
				}
			}
		});
		
		$("#manager_shopping_city").bind({
			change:function(){
				var selPro=pro.val();
				var code=city.val();
				$("#manager_shopping_area").empty();
				var opInfo=$("<option value='-1'>--请选择地区--</option>");
				$("#manager_shopping_area").prepend(opInfo);
				
				for(var i=0;i<pros.length;i++){
					if(pros[i].nodeType==1 && pros[i].getAttribute("postcode")==selPro){
						var cities=pros[i].childNodes;
						for(var j=0;j<cities.length;j++){
							if(cities[j].nodeType==1 && cities[j].getAttribute("postcode")==code){
								var area=cities[j].childNodes;
								for(var k=0;k<area.length;k++){
									if(area[k].nodeType==1){
										addOption(area[k],district);
									}
								}
							}
						}
					}
				}
			}
		});
	})
	
	function addOption(node,obj){
		var opInfo=$("<option value="+node.getAttribute("postcode")+">"+node.getAttribute("name")+"</option>");
		opInfo.appendTo(obj);
	}
	
	
	function createXmlDom(){
		var xmlDom=null;
		if(window.ActiveXObject){
			xmlDom=createIeXmlDom();	
		}else if(document.implementation.createDocument){
			xmlDom=document.implementation.createDocument("","",null);
		}else{
			alert("您的浏览器不支持xmlDom,请及时更新");	
		}
		return xmlDom;
	}
	
	function createIeXmlDom(){
		var arr=["MSXML2.DOMDocument.6.0","MSXML2.DOMDocument.5.0","MSXML2.DOMDocument.4.0","MSXML2.DOMDocument.3.0","MSXML2.DOMDocument","Microsoft.XMLDOM"];
		for(var i=0;i<arr.length;i++){
			try{
				var ieXmlDom=new ActiveXObject(arr[i]);
				return ieXmlDom;
			}catch(ex){
				alert("您的浏览器不支持MSXML组件，请及时更新浏览器");	
			}
		}
	}