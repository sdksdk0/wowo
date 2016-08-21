
  $(window).scroll(function () {
				 var t=$(this).scrollTop();

				if(t>100){
					$(".gotopBtn").css("visibility","visible");
				}else{
					$(".gotopBtn").css("visibility","none");
				}	
				
				
	});

  
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
	              case 'delete': //点击了删陿
	                    var conf = confirm('确定删除此商品吗＿');
	                    if (conf) {
	                        this.parentNode.removeChild(this);
	                    }
	                    break;
	          }
	          getTotal();
	      }
	      // 给数目输入框绑定keyup事件
	        tr[i].getElementsByTagName('input')[0].onkeyup = function () {
	            var val = parseInt(this.value);
	            if (isNaN(val) || val <= 0) {
	                val = 1;
	            }
	            if (this.value != val) {
	                this.value = val;
	            }
	            getSubtotal(this.parentNode.parentNode); //更新小计
	            getTotal(); //更新总数
	        }
  
	  }
	  

	  
	// 计算单行价格
	  function getSubtotal(tr) {
	      var cells = tr.cells;
	      var price = cells[3]; //单价
	      var subtotal = cells[5]; //小计td
	      var countInput = tr.getElementsByTagName('input')[1]; //数目input
	      var span = tr.getElementsByTagName('span')[1]; //-叿
	      //写入HTML
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
  
  
  //生成订单
  function genOrder(){
	  
		$("servlet/goodsInfoServlet",{op:"genOrder"},function(data){
			 if(data>0){
				 location.href="pay.jsp";
			 } else{
				 alert("请先登录");
				location.href="login.html";
			 }
			  
		  });

  }

  
  
  
  
  
  
  
  
