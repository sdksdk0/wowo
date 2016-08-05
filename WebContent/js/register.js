function checkname(obj,divid){
	var reg=/^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9_\u4e00-\u9fa5]{2,17}$/;
	if(reg.test(obj.value)){
		document.getElementById(divid).innerHTML="会员名格式效验成功";
		document.getElementById(divid).className="ok";
	}else{
		document.getElementById(divid).innerHTML="会员名格式效验失败";
		document.getElementById(divid).className="err";
	}
}

function checkmyname(obj,divid){
	var reg=/^[\u4e00-\u9fa5]{2,7}$/;
	if(reg.test(obj.value)){
		document.getElementById(divid).innerHTML="真实姓名格式效验成功";
		document.getElementById(divid).className="ok";
	}else{
		document.getElementById(divid).innerHTML="真实性名格式效验失败";
		document.getElementById(divid).className="err";
	}
}

function showpwd(obj,divid){
	var reg=/^\w{6,16}$/;
	if(reg.test(obj.value)){
		document.getElementById(divid).innerHTML="密码格式效验成功";
		document.getElementById(divid).className="ok";
	}else{
		document.getElementById(divid).innerHTML="密码格式效验失败";
		document.getElementById(divid).className="err";
	}
}

function checkrpwd(pwd,mypwd,divid){
	var pwd=document.getElementById(pwd).value;
	var repwd=document.getElementById(mypwd).value;
	if(pwd==repwd){
		document.getElementById(divid).innerHTML="密码格式验证成功";
		document.getElementById(divid).className="ok";
	}else{
		document.getElementById(divid).innerHTML="密码格式验证失败";
		document.getElementById(divid).className="err";
	}
}

function showsex(obj,divid){
	if(obj.value==null  ||  obj.value==""){
		document.getElementById(divid).innerHTML="请选择您的性别";
		document.getElementById(divid).className="ok";
	}else{
		document.getElementById(divid).innerHTML="您选择的性别是："+obj.value;
		document.getElementById(divid).className="ok";
	}
}

function checktypeid(obj,divid){
	var reg=/^\d{17}[\d|x]$|^\d{15}$/;
	if(reg.test(obj.value)){
		document.getElementById(divid).innerHTML="证件号效验成功";
		document.getElementById(divid).className="ok";
		return true;
	}else{
		document.getElementById(divid).innerHTML="证件号效验失败";
		document.getElementById(divid).className="err";
		return false;
	}
}

function checkemail(obj,divid){
	var reg=/^[\w+@\w+\.[a-z]+$/;
	if(reg.test(obj.value)){
		document.getElementById(divid).innerHTML="电子邮箱格式效验成功";
		document.getElementById(divid).className="ok";
	}else{
		document.getElementById(divid).innerHTML="电子邮箱格式效验失败";
		document.getElementById(divid).className="err";
	}
}

function checktel(obj,divid){
	var reg=/^\d{4}-\d{6}$/;
	if(reg.test(obj.value)){
		document.getElementById(divid).innerHTML="联系方式格式效验成功";
		document.getElementById(divid).className="ok";
		return true;
	}else{
		document.getElementById(divid).innerHTML="联系方式格式效验失败";
		document.getElementById(divid).className="err";
		return false;
	}
}



function createXmlDom(){
			//因为获取xmldom，IE浏览器和其他浏览器不一样，所以我们要做兼容
			var xmldom=null;
			//区分浏览器
			if(window.ActiveXObject){   //IE浏览器独有
				//创建IE浏览器下面的xmldom
				xmldom=createIeXmlDom();
			}else if(document.implementation.createDocument){
				//其他主流浏览器
				// 指定文档的命名空间   空间URL doc类型
				xmldom=document.implementation.createDocument("","",null);
			}else{
				alert("您的浏览器不支持XML DOM,请及时更新浏览器");	
			}
			return xmldom;
		}
		
function createIeXmlDom(){
	//这个函数专用用来创建IE浏览器里面的XMLDOM
	//每一个IE浏览器都必定会有一个浏览器 内核，每一个版本的浏览器，只有得到了自己的专有核心，才能够创建 xmldom

	var arr=["MSXML2.DOMDocument.6.0","MSXML2.DOMDocument.5.0","MSXML2.DOMDocument.4.0","MSXML2.DOMDocument.3.0","MSXML2.DOMDocument","Microsoft.XMLDOM"];
	for( var i=0;i<arr.length;i++){
		//如果浏览器版本不对，那么就会继续向下循环
		try{
			var ieXmlDom=new ActiveXObject(arr[i]); //创建一个IE的xml文档对象
			return ieXmlDom;	
		}catch(ex){
				alert("你的浏览器不支持MSXML组件，请更新浏览器");
		}
	}				
}

function init(){
	var xmldom=createXmlDom();
	xmldom.async=false;
	xmldom.load("city.xml");
	//开始解析
	var pro=document.getElementById("sheng");
	var city=document.getElementById("shi");
	
	//从省份开始
	var pros=xmldom.getElementsByTagName("province");
	for(var i=0;i<pros.length;i++){
		addOption(pros[i],pro);
	}
	
	//当省份改变时，城市也要跟着改变，所以，要添加一个onchange事件
	pro.onchange=function(){
		//获取到编号
		var code=pro.value;
		city.length=1;  //清空数据
		for(var i=0;i<pros.length;i++){ //读取 pros[i] 节点的属性名称为 postcode 的属性值
			if(pros[i].nodeType==1  &&  code==pros[i].getAttribute("postcode")){
				//找到了你点击的城市
				var cities=pros[i].childNodes;
				for(var j=0;j<cities.length;j++){
					if(cities[j].nodeType==1){
						addOption(cities[j],city);
					}
				}
				//找到了，就不需要继续向下面循环了。所以break掉
				break;
			}
		}
		
	}
}

function addOption(node,obj){
	if(node.nodeType==1){
		var opt=document.createElement("option");
		opt.appendChild(document.createTextNode( node.getAttribute("name")));
		opt.setAttribute("value",node.getAttribute("postcode"));
		obj.appendChild(opt);
	}
}

function showsheng(val,divid){
	if(val.value=="--请选择省份--"){
		document.getElementById(divid).innerHTML="请选择您所在的省份";
		document.getElementById(divid).className="err";
	}else{
		document.getElementById(divid).innerHTML="您选择的省份是"+val.options[val.options.selectedIndex].text;
		document.getElementById(divid).className="ok";
	}
}	

function showshi(val,divid){
	if(val.value=="--请选择城市--" || val.value==""  || val.value==null){
		document.getElementById(divid).innerHTML="请选择您所在的城市";
		document.getElementById(divid).className="err";
	}else{
		document.getElementById(divid).innerHTML="您选择的城市是"+val.options[val.options.selectedIndex].text;
		document.getElementById(divid).className="ok";
	}
}