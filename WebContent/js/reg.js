
var flag1=false;
var flag2=false;
var flag3=false;
var flag4=false;
var flag5=false;
var flag6=false;




$(function () {

    $('#regemail').emailComplete({
        opacity: 1,
	     radius: 4
    });
})


			  $(window).scroll(function () {
				 var t=$(this).scrollTop();

				if(t>300){
					$(".gotopBtn").css("visibility","visible");
				}else{
					$(".gotopBtn").css("visibility","none");
				}
				
			  });
			  
			function checkInfo(){
			
				
				$("#username_msg").css("color","red");
				$("#username_msg").css("font-size","14px");
				
				$("#password_msg").css("color","red");
				$("#password_msg").css("font-size","14px");
				
				$("#reppassword_msg").css("color","red");
				$("#reppassword_msg").css("font-size","14px");
				
				$("#tel_msg").css("color","red");
				$("#tel_msg").css("font-size","14px");
				
				$("#regemail_msg").css("color","red");
				$("#regemail_msg").css("font-size","14px");
				
				$("#regemail").bind({
					blur:function(){
						var val=$("#regemail").val();
						var reg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/; 
						
						var tdInfo="";
				
						if(val==""){
							tdInfo="*请输入邮箱";
						}else if(!reg.test(val)){
							tdInfo="*请输入正确的邮箱";	
						}else{
							tdInfo="";	
							flag4=true;
						}
						
						$("#regemail_msg").text(tdInfo);
					},
					focus:function(){
						$("#regemail_msg").text("");
					}
				});

				$("#username").bind({
					blur:function(){
						var val=$("#username").val();
						var reg=/^[a-zA-Z\u4e00\u9fa5][a-zA-Z0-9_\u4e00\u9fa5]{2,17}$/;
						
						var tdInfo="";
				
						if(val==""){
							tdInfo="*请输入用户名";
							flag1=false;
						}else if(!reg.test(val)){
							tdInfo="*2-17位的中文、字母、数字和下划线";	
							flag1=false;
						}else{
							tdInfo="";	
							flag1=true;
						}
						
						$("#username_msg").text(tdInfo);
					},
					focus:function(){
						$("#username_msg").text("");
					}
				});
				
				
				$("#password").bind({
					blur:function(){
						var val=$("#password").val();
						var reg=/^\w{6,16}$/;
						
						var tdInfo="";
				
						if(val==""){
							tdInfo="*请输入密码";
							flag2=false;
						}else if(!reg.test(val)){
							tdInfo="*由6到16位的字母数字或下划线组成";	
							flag2=false;
						}else{
							tdInfo="";	
							flag2=true;
						}
						
						$("#password_msg").text(tdInfo);
						
					},
					focus:function(){
						$("#password_msg").text("");
					},
					change:function(){
						var val3=$("#password").val();
						AuthPasswd(val3);
					}
				});
				
				$("#reppassword").bind({
					blur:function(){
						var val=$("#password").val();
						var reval=$("#reppassword").val();
				
						if(reval==""){
							tdInfo="*密码不能为空";
							flag6=false;
						}else if(val!=reval){
							tdInfo="*两次密码不一致";	
							flag6=false;
						}else{
							tdInfo="";	
							flag6=true;
						}
						
						$("#reppassword_msg").text(tdInfo);
					},
					focus:function(){
						$("#reppassword_msg").text("");
					}
				});
				
				$("#tel").bind({
					blur:function(){
						var val=$("#tel").val();
						var reg=/^([1][3|4|5|7|8][0-9]{9})$|^(0[0-9]{2,3}\-?\d{7,8})$/;
						
						var tdInfo="";
				
						if(val==""){
							tdInfo="*请输入联系电话";
						}else if(!reg.test(val)){
							tdInfo="*请输入正确的联系电话";
							flag3=false;
						}else{
							tdInfo="";	
							flag3=true;
						}
						
						$("#tel_msg").text(tdInfo);
					},
					focus:function(){
						$("#tel_msg").text("");
					}
				});
			}

		function createXmlDom(){
				var xmldom=null;
				if(window.ActiveXObject){  //IE浏览器独有的
						xmldom=createIeXmlDom();
				}else  if(document.implementation.createDocument){
						xmldom=document.implementation.createDocument("","",null);
				}else{	
					alert("您的浏览器不支持xmlDom,请及时更新浏览器");
				}
				return xmldom;
			}
		
			//用来创建ie浏览器里面的xmldom
			function createIeXmlDom(){
				var arr=["MSXML2.DOMDocument.6.0","MSXML2.DOMDocument.5.0","MSXML2.DOMDocument.4.0",  	"MSXML2.DOMDocument.3.0","MSXML2.DOMDocument","Microsoft.XMLDOM"];
				
				for(var i=0;i<arr.length;i++){
					
					//如果ie浏览器发现版本不对，那么就会继续往下循环
					
					try{
						var ieXmlDom=new ActiveXObject(arr[i]);
						return ieXmlDom;
					}catch(ex){
						alert("您的浏览器不支持不MSXML，请更新浏览器");	
					}
				}	
			}
			
			window.onload=function(){
				
				checkInfo();
				
				var xmldom;
				if(window.DOMParser && !window.ActiveXOBject){
					
					var xmlhttp=new XMLHttpRequest();
					xmlhttp.open("GET","city.xml",false);
					xmlhttp.send(null);
					if(xmlhttp.readyState==4){
						xmldom=xmlhttp.responseXML.documentElement;
						var a=xmldom.getElementsByTagName("province");
					}
				}else{
					
					xmldom=createXmlDom();
					xmldom.async=false; 
					xmldom.load("city.xml");
				}
					
					//开始解析xml
					var pro=document.getElementById("province");
					var city=document.getElementById("city");
					var area=document.getElementById("district");
					
					var pros=xmldom.getElementsByTagName("province");
					for(var i=0;i<pros.length;i++){
						addOption(pros[i],pro);	
					}
					
					//省份改变时
					pro.onchange=function(){
						//获取到编号
						var code=pro.value;	
						city.length=1;
						for(var i=0;i<pros.length;i++){
							if(pros[i].nodeType==1 && pros[i].getAttribute("postcode")==code){
								//找到点击的省份
								var cities=pros[i].childNodes;
								for(var j=0;j<cities.length;j++){
									
									if(cities[j].nodeType==1){
										addOption(cities[j],city);	
									}
								}
							}
						}
					}
					
					//当城市改变的时候
					city.onchange=function(){
						var selPro=pro.value;
						var code=city.value;
						area.length=1;
						for(var i=0;i<pros.length;i++){
							if(pros[i].nodeType==1 && pros[i].getAttribute("postcode")==selPro){
								//找到点击的省份
								var cities=pros[i].childNodes;
								for(var j=0;j<cities.length;j++){
									if(cities[j].nodeType==1 && cities[j].getAttribute("postcode")==code){
										
										//找到了点击的城市
										var areas=cities[j].childNodes;
										for(var k=0;k<areas.length;k++){
											if(areas[k].nodeType==1){
												addOption(areas[k],area);
											}
										}
									}
								}
							}
						}		
					}
			}
			
			function addOption(node,obj){
				var opt=document.createElement("option");
				opt.appendChild(document.createTextNode(node.getAttribute("name")));
				opt.setAttribute("value",node.getAttribute("postcode"));
				obj.appendChild(opt);	
			}
			
	

	
function AuthPasswd(string) {
    if(string.length >=6) {
        if(/[a-zA-Z]+/.test(string) && /[0-9]+/.test(string) && /\W+\D+/.test(string)) {
            noticeAssign(1);
        }else if(/[a-zA-Z]+/.test(string) || /[0-9]+/.test(string) || /\W+\D+/.test(string)) {
            if(/[a-zA-Z]+/.test(string) && /[0-9]+/.test(string)) {
                noticeAssign(-1);
            }else if(/\[a-zA-Z]+/.test(string) && /\W+\D+/.test(string)) {
                noticeAssign(-1);
            }else if(/[0-9]+/.test(string) && /\W+\D+/.test(string)) {
                noticeAssign(-1);
            }else{
                noticeAssign(0);
            }
        }
    }else{
        noticeAssign(null); 
    }
}
 
function noticeAssign(num) {
    if(num == 1) {
        $('#weak').css({backgroundColor:'#009900'});
        $('#middle').css({backgroundColor:'#009900'});
        $('#strength').css({backgroundColor:'#009900'});
        $('#strength').html('很强');
       
    }else if(num == -1){
        $('#weak').css({backgroundColor:'#ffcc33'});
        $('#middle').css({backgroundColor:'#ffcc33'});
        $('#strength').css({backgroundColor:''});
        
        $('#middle').html('中');
       
    }else if(num ==0) {
        $('#weak').css({backgroundColor:'#dd0000'});
        $('#middle').css({backgroundColor:''});
        $('#strength').css({backgroundColor:''});
        $('#weak').html('弱');
  
    }else{
        $('#weak').html('&nbsp;');
        $('#middle').html('&nbsp;');
        $('#strength').html('&nbsp;');
        $('#weak').css({backgroundColor:''});
        $('#middle').css({backgroundColor:''});
        $('#strength').css({backgroundColor:''});
    }
}





var countdown=60; 
function settime(obj) { 

    if (countdown == 0) { 
        obj.removeAttribute("disabled");    
        obj.value="免费获取验证码"; 
        countdown = 100; 
        return;
    } else { 
        obj.setAttribute("disabled", true); 
        obj.value="重新发送(" + countdown + ")"; 
        countdown--; 
    } 
      
setTimeout(function() { 
    settime(obj) }
    ,1000) 
}


function sendEmail(obj){
	var email=$.trim($("#regemail").val());

	var reg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/; 
	if(reg.test(email)){
		settime(obj);
		
		$.post("servlet/UserInfoServlet",{op:"sendEmail",email:email},function(data){
			
		});	
		
	}
}



$(function() {

	$("#regsafecode").blur(function(){
		var rcode=$.trim($("#regsafecode").val());
		
		if(rcode.length<=0){
			$("#regsafecode").css("border-color","red");
			$("#regsafecode_msg").text("验证码不能为空");
			$("#regsafecode_msg").css("color","red");
			
		}else {
			$.post("servlet/UserInfoServlet",{op:"chenkoutDate",rcode:rcode},function(data){
				
				data=$.trim(data);
				if(data==1){
					$("#regsafecode").css("border-color","green");
					$("#regsafecode_msg").text("");
					flag5=true;	
				}else  if(data==2){
					$("#regsafecode").css("border-color","red");
					$("#regsafecode_msg").text("验证码超时，请重新发送");
					$("#regsafecode_msg").css("color","red");
					flag5=false;
				}else{
					$("#regsafecode").css("border-color","red");
					$("#regsafecode_msg").text("验证码错误");
					$("#regsafecode_msg").css("color","red");
					flag5=false;
				}
				
			});				
		}
	});
	
	
	
	$("#regsafecode").focus(function(){
		$("#regsafecode").css("border-color","#eee");
		$("#regsafecode_msg").text("");
	});
	
	
	
	
	
	
	
});



	function registUserInfo(){
		
		var username=$.trim($("#username").val());
		var pwd=hex_md5($.trim($("#password").val()));
		var tel=$.trim($("#tel").val());
		var email=$.trim($("#regemail").val());
		var prov=$.trim($("#province  option:selected").text());
		var city=$.trim($("#city option:selected").text());
		var area=$.trim($("#district  option:selected").text());
		
		
		var argee=$(":checkbox:checked").size();
		
		if(prov!="--请选择省份--"){
			
			
			if(city!="--请选择城市--"){
				
				if(area!="--请选择地区--"){
					
					if(flag1==true && flag2==true && flag3==true && flag5==true   && flag6==true &&   argee>0 ){
						
						$.post("servlet/UserInfoServlet",{op:"registUser",username:username,pwd:pwd,tel:tel,email:email,prov:prov,city:city,area:area},function(data){
							data=$.trim(data);
							if(data>0){
								location.href="regok.html";
							}else{
								alert("注册失败!");
							}		
						});
					}
					
				}
			}
		}

	}




