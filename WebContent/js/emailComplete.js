

(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as anonymous module.
        define(['jquery'], factory);
    } else if (typeof exports === 'object') {
        // Node / CommonJS
        factory(require('jquery'));
    } else {
        // Browser globals.
        factory(jQuery);
    }
})(function ($) {
    'use strict';

    var Fidding = function (element, options) {
        var _this = this;
        // integration config
        _this.options = $.extend({}, Fidding.defaults, options);
        _this.init(element);
    }

    /**
     * [plugin params]
     */
    Fidding.version = '3.0.0';//version
    Fidding.pluginName = 'emailComplete';// plugin name
    Fidding.dataName = "emailDataName";// dataName cache the data in dom
    Fidding.options = {};// config
    Fidding.active = -1;// active item index

    /**
     * [plugin element]
     */
    Fidding.$element = null;// email element
    Fidding.$container = null;// email container
    Fidding.$containerItem = null;// email container item

    /**
     * [plugin defaults config]
     */
    Fidding.defaults = {
		opacity : 1, // email container opacity
		radius: 0, // email container border-radius (px)
		data : ['tianfang1314.cn','qq.com','163.com','126.com','sina.com','sohu.com'], // email suffix
		callback : $.noop // callback

    }

    /**
     * [plugin prototype function]
     */
    Fidding.prototype = {
        // init
		init : function (element) {
            var _this = this;
            // assignment objects
            _this.$element = $(element);
            _this.$parent = _this.$element.parent();
			_this.$element.after('<div class="complete-container" style="border-radius:'+_this.options.radius+'px;opacity:'+_this.options.opacity+'"></div>');
            _this.$container = _this.$element.next( ".complete-container" );
			_this.$containerItem = _this.$container.find('div.complete-item');
            // input event
		    _this.$element.on('input', function (e) {
			    if(_this.$element.val() == ''){
				    _this.hidden();
			    }else{
				    _this.onInput(e);
			    }
		    });
            // keyup event
		    _this.$element.on('keyup', function (e) {
			    if(_this.$element.val() == ''){
				    _this.hidden();
			    }else{
				    _this.onKeyup(e);
			    }
		    });
            // focus event
		    _this.$element.on('focus', function () {
			    _this.onFocus($(this));
		    });
            // blur event
		    _this.$element.on('blur', function () {
			    _this.onBlur();
		    });
            // item mouse event
            _this.$parent.on('mouseover', '.complete-item', function(e){
			    _this.onMouseOver($(this));
		    });
            // item mouse event
		    _this.$parent.on('mousedown', '.complete-item', function(e){
			    _this.onMouseDown($(this));
		    });
            // prevent form submit
            _this.preventForm();
		},
        // item active
        addActive: function (item) {
            item.addClass('complete-active');
        },
        // remove item active
        removeActive: function (item) {
            item.removeClass('complete-active');
        },
        // get email position
		getPos : function () {
            var _this = this;
			var width = _this.$element.outerWidth(),
			    height = _this.$element.outerHeight(),
			    top = _this.$element.position().top+height+3,
			    left = _this.$element.position().left;
			return { top: top, left: left, width: width };
		},
        // set email position
		setPos : function(data){
            var _this = this;
			_this.$container.css({ top: data.top, left: data.left, width: data.width });
		},
        // show email container
		show : function(){
            var _this = this;
            _this.setPos(_this.getPos());//set container position
			_this.$container.css({'visibility':'visible'});
		},
        // hidden email container
		hidden : function(){
            var _this = this;
			_this.$container.css({'visibility':'hidden'}).empty();
            _this.$containerItem = _this.$container.find('div.complete-item');
			_this.active = -1;
		},
        // email text change
		onInput : function(e){
            var _this = this;
			_this.active = -1;
			_this.$container.empty();
			var completeItem ='';
			var index = _this.$element.val().indexOf('@');// weather has @
			if(index != -1){// has @
				var startVal = _this.$element.val().substring(0, index);// get the string before @
				var endVal = _this.$element.val().substring(index+1, _this.$element.val().length);// get the string after @
				if(endVal == ''){
                    //add all options.data
					$.each(_this.options.data, function (n, value) {
						completeItem += '<div class="complete-item">'+startVal+'@'+value+'</div>';
					});
				}else{
					var isHas = 0;
					$.each(_this.options.data, function (n, value) {
						if(value.indexOf(endVal) == 0){// there are compliant mail suffix
							var itemContent = startVal+'@'+value;
							if(isHas == 0){
								completeItem += '<div class="complete-item complete-active" >'+itemContent+'</div>';
								_this.active = 0;
							}else{
								completeItem += '<div class="complete-item" >'+itemContent+'</div>';
							}
							isHas++;
						}
					});
					if(isHas == 0){// if no compliant mail suffix to hide email container
						_this.hidden();
						return true;
					}
				}
			}else{// no @
				$.each(_this.options.data, function (n,value) {
					completeItem += '<div class="complete-item">'+_this.$element.val()+'@'+value+'</div>';
				});
			}
			_this.$container.append(completeItem);
			_this.$containerItem = _this.$container.find('div.complete-item');
			_this.show();// show email
		},
        // keyboard operation event
		onKeyup : function (e) {
            var _this = this;
			if(_this.$element.is(':visible')){
				var itemLength = _this.$containerItem.length;
				switch (e.keyCode) {
				case 13||10 :// enter
					if(_this.active == -1){
					}else{
						_this.$element.val(_this.$containerItem.eq(_this.active).html());
						_this.hidden();
						_this.onCallBack();
					}
					break;
				case 38 :// up arrow
					if(_this.active == -1){
                        _this.addActive(_this.$containerItem.eq(itemLength-1));
						_this.active = itemLength-1;
					}else{
						if(_this.active-1 < 0){
                            _this.removeActive(_this.$containerItem.eq(_this.active));
							_this.active = -1;
						}else{
                            _this.removeActive(_this.$containerItem.eq(_this.active));
                            _this.addActive(_this.$containerItem.eq(_this.active-1));
							_this.active--;
						}
					}
					break;
				case 40 :// down arrow
					if(this.active == -1){
                        _this.addActive(_this.$containerItem.eq(0));
						_this.active = 0;
					}else{
                        _this.removeActive(_this.$containerItem.eq(_this.active));
						if(_this.active+1 == itemLength){
							_this.active = -1;
						}else{
                            _this.addActive(_this.$containerItem.eq(_this.active+1));
							_this.active++;
						}
					}
					break;
				case 27 :// esc
					_this.hidden();
					break;
				default :
					break;
				}
			}
		},
        // email input blur event
		onBlur : function(){
            var _this = this;
			_this.active = -1;
            _this.hidden();
		},
        // email input focus event
		onFocus : function(obj){
            var _this = this;
			if(!_this.$containerItem.length){
                _this.hidden();
                return true;
			}
            _this.show();
		},
        // item hover event
		onMouseOver : function(obj){
            var _this = this;
			_this.$containerItem.each(function(e){
                _this.removeActive($(this));
			});
            _this.addActive(obj);
			_this.active = obj.index();
		},
        // item hover down event
		onMouseDown : function(obj){
            var _this = this;
			_this.$element.val(obj.html());
			_this.hidden();
			_this.onCallBack();
		},
        // callback function
		onCallBack : function(){
            var _this = this;
			if(typeof _this.options.callback == 'function'){
				_this.options.callback();
			}else{
				console.log(_this.options.callback);
			}
		},
        // prevent form submit function
        preventForm: function () {
            var _this = this;
			if($('form').length){ // has form table
				$('form').submit(function(value){
					if(_this.active != -1){
						_this.$element.val(_this.$containerItem.eq(_this.active).html());
						_this.hidden();
						_this.onCallBack();
						return false;
					}
				});
			}
        },
	}

	$.fn[Fidding.pluginName] = function (options) {
		return this.each(function (e) {
			var _this = $(this);
            var data = _this.data(Fidding.dataName);
            //var options = typeof option == 'object' && option;
            // cache data object
            if (!data) {
                _this.data(Fidding.dataName, (data = new Fidding(this, options)));
            }
            //If the name of the plug-parameter is a string, then directly call widget method for this string
            if (typeof option == 'string') data[option]();
		});
	}

    $.fn[Fidding.pluginName].Constructor = Fidding;

    // data-role='emailComplete'
    $(document).ready(function () {
        $('[data-role="'+Fidding.pluginName+'"]').each(function () {
            var _this = $(this);
            var data = _this.data();
            $.fn[Fidding.pluginName].call(_this, data);
        });
    });
})
