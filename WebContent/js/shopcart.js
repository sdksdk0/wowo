
  $(window).scroll(function () {
				 var t=$(this).scrollTop();

				if(t>100){
					$(".gotopBtn").css("visibility","visible");
				}else{
					$(".gotopBtn").css("visibility","none");
				}	
				
				
	});

  var numgid=null;
$(function(){
	

	  
	  
	  var table = document.getElementById('cartTable'); 
	  var tr = table.children[1].rows;
	  var selectedTotal = document.getElementById('selectedTotal');
	  var priceTotal = document.getElementById('priceTotal'); //总计
	  var selectInputs = document.getElementsByClassName('check');
	  var checkAllInputs = document.getElementsByClassName('check-all');
	  var selected = document.getElementById('selected');
	 
	  
	  
	  
		//更新总数和总价格
		  function getTotal() {
				var seleted = 0;
				var price = 0;
				for (var i = 0, len = tr.length; i < len; i++) {
					if (tr[i].getElementsByTagName('input')[0].checked) {
						tr[i].className = 'on';
						seleted += parseInt(tr[i].getElementsByTagName('input')[1].value);
						price += parseFloat(tr[i].cells[5].innerHTML);
					}
					else {
						tr[i].className = '';
					}
				}	
				selectedTotal.innerHTML = seleted;
				priceTotal.innerHTML = price.toFixed(2);
			
		}
	  

	  
	//为每行元素添加事
	  for (var i = 0; i < tr.length; i++) {
	      //将点击事件绑定到tr元素
	      tr[i].onclick = function (e) {
	    	 
	          var e = e || window.event;
	          var el = e.target || e.srcElement; //通过事件对象的target属性获取触发
	          var cls = el.className; //触发元素的class
	          var countInout = this.getElementsByTagName('input')[1]; // 数目input
	          var value = parseInt(countInout.value); //数目
	          //通过判断触发元素的class确定用户点击了哪个
	          switch (cls) {
	              case 'add': //点击了加
	            	  countInout.value = value + 1;
	                  getSubtotal(this);
	                  break;
	              case 'reduce': //点击了减
	                  if (value > 1) {
	                	  countInout.value = value - 1;
	                      getSubtotal(this);
	                  }
	                  break;
	          }
	          getTotal();
	      }
  
	  }
	  

	  // 计算单行价格
	    function getSubtotal(tr) {
	        var cells = tr.cells;
	        var price = cells[2]; //单价
	        var subtotal = cells[5]; //小计td
	        var countInput = tr.getElementsByTagName('input')[1]; //数目input
	        var span = tr.getElementsByTagName('span')[1]; //-
	        //写入HTML
	        changeNumber(countInput,numgid);
	        
	        subtotal.innerHTML = (parseInt(countInput.value) * parseFloat(price.innerHTML)).toFixed(2);
	        //如果数目只有一个，抿-号去掿
	        if (countInput.value == 1) {
	            span.innerHTML = '';
	        }else{
	            span.innerHTML = '-';
	        }
	    }

	  
	  
	  // 点击选择框
	    for(var i = 0; i < selectInputs.length; i++ ){
	        selectInputs[i].onclick = function () {
	            if (this.className.indexOf('check-all') >= 0) { //如果是全选，则吧所有的选择框选中
	                for (var j = 0; j < selectInputs.length; j++) {
	                    selectInputs[j].checked = this.checked;
	                }
	            }
	            if (!this.checked) { //只要有一个未勾选，则取消全选框的选中状怿
	                for (var i = 0; i < checkAllInputs.length; i++) {
	                    checkAllInputs[i].checked = false;
	                }
	            }
	            getTotal();//选完更新总计
	        }
	    }
	    
	    
	    // 点击全部删除
	    deleteAll.onclick = function () {
	        if (selectedTotal.innerHTML != 0) {
	            var con = confirm('确定删除所选商品吗＿'); //弹出确认桿
	            if (con) {
	                for (var i = 0; i < tr.length; i++) {
	                    // 如果被选中，就删除相应的行
	                    if (tr[i].getElementsByTagName('input')[0].checked) {
	                        tr[i].parentNode.removeChild(tr[i]); // 删除相应节点
	                        i--; //回退下标位置
	                    }
	                }
	            }
	        } else {
	            alert('请选择商品＿');
	        }
	        getTotal(); //更新总数
	    }

	    

		
	    
	    
	    
	    
	 
  
});
  
  
	//删除商品项
	function delOneItem(gid){
		var sure = window.confirm("确定要删除吗？");
		if(sure){
			$.post("servlet/goodsInfoServlet",{op:"delOneItem",gid:gid},function(data){
				 if(data>0){
					 location.href="shopcart.jsp";
				 } else{
					 alert("网络异常");
				 }
				  
			  });
		}
	}


	//改变数量
	
	function changeNumber(inputObj,gid){
		var value = inputObj.value;
		//验证值必须是自然整数
		if(!/^[1-9][0-9]*$/.test(value)){
			//改为1
			inputObj.value=1;
			this.focus();
			return;
		}
		
		$.post("servlet/goodsInfoServlet",{op:"changeNum",value:value,gid:gid},function(data){
			 if(data>0){
				 location.href="shopcart.jsp";
			 } else{
				 alert("网络异常");
			 }
			  
		  });
	}
	
	  function getGid(gid){
			numgid=gid;
		}
	
	
	
	
  
  //生成订单
  function genOrder(){
	  
		$.post("servlet/goodsInfoServlet",{op:"genOrder"},function(data){
			 if(data>0){
				 location.href="pay.jsp";
			 } else{
				 alert("请先登录");
				location.href="login.html";
			 }
			  
		  });

  }

  
  
  
  
  
  
  
  
