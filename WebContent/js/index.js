		
			  $(window).scroll(function () {
				 var t=$(this).scrollTop();

				if(t>300){
					$(".gotopBtn").css("visibility","visible");
				}else{
					$(".gotopBtn").css("visibility","none");
				}
				
			  });
			  
			  
			  $(function() {
				  
				//得到用户登录信息
					$.post("servlet/UserInfoServlet", {
						op : "getLoginInfo"
					}, function(data) {
						if (data == "" || data == null) {
							$("#index_loginuser").html("您好！请 [<a class=\"yellowd1\" rel=\"nofollow\" href=\"login.html\" target=\"_blank\">登录</a>]  <b class=\"borderdc\">|</b>   [<a class=\"yellowd1\" rel=\"nofollow\" href=\"reg.html\" target=\"_blank\">注册</a>]   <b class=\"borderdc p_0_10\">|</b>");
						} else {
							$("#index_loginuser").text("欢迎您"+data.uname);
						}

					}, "json");
					
					
			  });
			  
			  
			  //首页加载商品信息
			  var pageNo=1;
				var flag=true;
				$(function(){
					pageNo=1;
					flag=true;
					findGoodsIndex(pageNo,4);				
				});
			  
			  
				
			  function loadMore(){
					if(flag){
						findGoodsIndex(++pageNo,4);
					}	
				}
				
				var str="";
			  function  findGoodsIndex(pageNo,pageSize){
				  
				  $.post("servlet/goodsInfoServlet",{op:"findGoodsIndex",pageNo:pageNo,pageSize:pageSize},function(data){
						
					  if(data.length>0){
							
							
							$.each(data,function(index,item){
								str+="<li class=\"good-list\"> "
								+"<h2 class=\"good-title\">"
								+"<a class=\"biaoa\"  target=\"_blank\" href=\"#\">"
								+"<strong>["+item.area+"],"+item.gname+"</strong>"
								+item.des+"</a></h2>"
								+"<div class=\"index-smalllogo\">"
								+"<a class=\"yuy\" target=\"_blank\" href=\"#\">免预约</a></div>"
								+"<a class=\"picture\" target=\"_blank\" href=\"#\">"
								+"<img width=\"348\" height=\"232\" rel=\"nofollow\" src=\""+item.pic+"\"></a>"
								+"<div class=\"buy-boxInd clearfix\">"
								+"<a class=\"bh buy_a\" rel=\"nofollow\" href=\"details.html\" target=\"_blank\" status=\"0\">去看看</a>"
								+"<span class=\"num\">¥"+item.price+"</span>"
								+"<span class=\"past\">价值 ¥196</span></div></li>";
							});
							
							$("#findGoodsIndex").html(str+" <center><li class=\"loadMoreInfo\"   id=\"loadMore\"   onclick=\"loadMore()\" >加载更多</li></center>");

						}else{
							flag=false;
							$("#loadMore").text("没有更多了");
						}
					},"json");
			  }
		
			
			  
			  
			  
			  
			  
			  
