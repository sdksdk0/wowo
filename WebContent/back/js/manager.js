
		
 var ue = UE.getEditor('editor');
 
 
 
 function add_ShoppingInfo() {
	 
	 UE.getEditor('editor').execCommand('insertHtml','<p>指令汇科技欢迎您的使用!</p>');
	 
     ue.getContent();
     
     $("#manager_shopping_date").datebox("setValue","17/08/2016");
 }
 
 $(function(){
	
	 
	 
 });
 
 
 
 
 

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