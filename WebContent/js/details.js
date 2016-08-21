  $(window).scroll(function () {
				 var t=$(this).scrollTop();

				if(t>300){
					$(".gotopBtn").css("visibility","visible");
				}else{
					$(".gotopBtn").css("visibility","none");
				}
				
			  });
			  
  
  $(function(){
	  
	  var url=document.location.href;
	  var gid=url.substring(url.indexOf("?")+1);
	  
	  if(gid>0){
		  $.post("servlet/goodsInfoServlet",{op:"findGoodsDetal",gid:gid},function(data){
			
			  if(data.length>0){
				  $.each(data,function(index,item){
					  
					  $("#detailsTitle").html("  <h2 class=\"details-h2\">["+item.area+"]"+item.gname+"</h2>"
							  +"<p class=\"details-p\">"+item.des+"</p>");
					  $("#wowoprice").html("<strong class=\"wowoprice\"   id=\"wowoprice\">¥"+item.price+"</strong>");
					  $("#detailimg").html("<img width=\"456\" height=\"304\" border=\"0\" src="+item.pic+"   id=\"detailsimg\">");
					  $("#detildes").html("<p>"+item.des+"</p>");
					  $("#zk").html("<span>价值</span> <span>折扣</span> <span>节省</span> <span class=\"ari\">¥"+2*item.price+"</span><span class=\"ari\">5折</span><span class=\"ari\">¥"+item.price+"</span>");
					  $("#peopersum").html("<strong class=\"ari\" data=\"salenum\"   id=\"peopersum\">"+2*item.gid+"</strong>")
					  $("#cr").html("<li> 价值<br><del class=\"num\">¥"+2*item.price+"</del></li><li> 折扣<br><span class=\"num\">5折</span> </li><li>节省<br><span class=\"num\">¥"+item.price+"</span></li>");
					  $("#titname").html(" <img width=\"128\" height=\"96\" src=\""+item.pic+"\"><span class=\"tit\">"+item.sname+"</span>");
					  $("#py").html("<span class=\"py\"   id=\"py\">¥"+item.price+"</span>");
					  $("#buysubmit").html("<a class=\"butbtn\" data=\"buySubmit\" rel=\"nofollow\" href=\"javascript:showCart("+item.gid+")\">确认购买</a>");
				  });  
			  }else{
				  alert("数据异常，请检查网络");
			  }
			  
		  },"json");
	  }else{
		  alert("初始化错误，请返回首页");
		  location.href="index.html";
	  }
  });
			
  
  
  function  showCart(gid){
	  if(gid>0){
		  
		  $.post("servlet/goodsInfoServlet",{op:"addGoods",gid:gid},function(data){
			  data=parseInt(data);
			  if(data>0){
				  location.href="shopcart.jsp";
			  }else{
				  alert("添加购物车失败");
			  }
			  		
		  });

		  
	  }
	  
	  
  }
  
  
  
  
  
  
  
  
  
  
  
  
  