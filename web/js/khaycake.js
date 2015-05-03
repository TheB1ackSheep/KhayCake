

$(document).ready(function(){
	var parameter = {};
	var dialog = $(".dialog");
	var dialogContent = $(".dialog-content");
	var section = $('.section-price');

	var Shop = Shop || {};
	Shop.open = function(){
		if(!section.hasClass("open")){
			var param = parameter['buy-button'];
			if(param){
				var b = param.getBoundingClientRect();
				var c = param.parentNode.parentNode.parentNode.getBoundingClientRect();
				dialog.css({
					top: b.top-c.top+'px',
					left: b.left-c.left+'px',
					backgroundColor: "#3E2733"
				});
			}else{
				dialog.css({
					top: '50%',
					left: '50%',
					backgroundColor: "#3E2733"
				});
			}

			dialogContent.css({
				opacity:0
			});


			dialog.animate({
				width: '100%',
				height: '100%',
				top: 0,
				left: 0,
				backgroundColor: "#fff"
			},350,function(){
				dialogContent.animate({
					opacity:1
				});
				section.addClass("open");

			});
		}
		Shop.tab();
	};
	Shop.close = function(){
		if(section.hasClass("open")){
			section.removeClass("open");

			var param = parameter['buy-button'];
			var size = {};
			if(param){
				var b = param.getBoundingClientRect();
				var c = param.parentNode.parentNode.parentNode.getBoundingClientRect();
				size = {width: b.width, height: b.height, top:  b.top-c.top+'px', left: b.left-c.left+'px', backgroundColor: "#3E2733"};
			}else
				size = {width: 0, height: 0, top:  '50%', left: '50%', backgroundColor: "#fff"};

			dialogContent.animate({
				opacity: 0
			},100,function(){
				dialog.animate({
					width: size.width,
					height: size.height,
					top:  size.top,
					left: size.left,
					backgroundColor: size.backgroundColor
				},350,function(){
					dialog.attr('style','');
				});
			});



		}


	};

	Shop.tab = function(){
		var section = window.location.hash.substr(2);
		var hash = section.split('/')[2];

		$(".dialog .nav li").removeClass("active");
		$(".dialog .nav li#"+hash).addClass("active");
	};

	var KhayCake = KhayCake || {};
	KhayCake.onHashChanged = function(){
		var section = window.location.hash.substr(2);
		var subSection = section.split('/')[1];

		switch(subSection){
			case 'shop' :
				Shop.open();
				break;
			default :
				Shop.close();
				break;
		}
	};

	$(window).bind("hashchange",function(){
		KhayCake.onHashChanged();
	});

	if(window.location.hash){
		KhayCake.onHashChanged();
	}

	$(".cake-box-buy a").click(function(el){
		parameter['buy-button'] = this;
	});
});

var q = function(el){
	return document.querySelectorAll(el);
};


function Shop(){
	var dialog = q(".dialog")[0];
	var size,parent,initSize,endSize;
	var initParameter = function(){
		var param = U.getParameter('buy-button');
		if(param){
			size = param.getBoundingClientRect();
			parent = param.parentNode.parentNode.parentNode.getBoundingClientRect();
			initSize = {backgroundColor:0x3E2733,top:(size.top-parent.top),left:(size.left-parent.left),width:size.width,height:size.height};
		}else{
			parent = q("section .cake")[0].getBoundingClientRect();
			initSize = {top:parent.width/2,left:parent.height/2,width:0,height:0};
		}
		endSize = {top:0,left:0,width:100,height:100};
	};

	this.isOpened = function(){ return U.classList(dialog).contains("open")};
	this.doTab = function(){
		var hash = window.location.hash.substr(2);
		var subsection = hash.split('/')[2];

		/*var allTab = q(".dialog .nav li");

		console.log(allTab);

		U.forEach(allTab,function(el){
			U.classList(el).remove("active");
		});

		var tab = q(".dialog .nav li#"+subsection);
		U.classList(tab[0]).add("active");*/
	};
	this.open = function(){
		if(!this.isOpened()){
			dialog.setAttribute('style','');
			initParameter();

			S.doTab();

			U.classList(dialog).add("open");

			U.animate([
				{
					time: 0.5,
					node: dialog,
					start: initSize,
					end: endSize,
					run: function(rate) {
						this.node.style
							.top = (rate*(this.end.top - this.start.top) + this.start.top)+'px';
						this.node.style
							.left = (rate*(this.end.left - this.start.left) + this.start.left)+'px';

						this.node.style
							.width = (rate*(this.end.width - this.start.width) + this.start.width)+'%';
						this.node.style
							.height = (rate*(this.end.height - this.start.height) + this.start.height)+'%';
					}
				},
				{
					time: 0,
					node: q(".dialog .box"),
					run: function() {
						var thatNode = this.node;
						var length = this.node.length;
						var idx = 0;
						(function anim(idx) {
							if(idx >= length) return;
							setTimeout(function(){
								U.animate([{
										time: 0.100,
										node: thatNode[idx],
										run : function(rate){
											this.node.style.opacity = (rate*(1));
											this.node.style.top = (rate*(10))-10+'px';
										}
									}]
								);
								anim(++idx)
							},100);
						})(idx);
					}
				}
			]);
		}
	};


	this.close = function(){
		if(this.isOpened()){
			initParameter();

			U.classList(dialog).remove("open");

			var param = U.getParameter('buy-button');

			if(param){
				var size = dialog.getBoundingClientRect();
				dialog.style.width = size.width+'px';
				dialog.style.height = size.height+'px';
			}

			console.log(dialog.style);

			U.animate([
				{
					time: 0,
					node: q(".dialog .box"),
					run: function() {
						var thatNode = this.node;
						var length = this.node.length;
						var idx = 0;
						(function anim(idx) {
							if(idx >= length) return;
							setTimeout(function(){
								U.animate([{
										time: 0.100,
										node: thatNode[idx],
										run : function(rate){
											this.node.style.opacity = (rate*(0-1));
											this.node.style.top = 1-(rate*(1))+'px';
										}
									}]
								);
								anim(++idx)
							},100);
						})(idx);
					}
				},
				{
					time: 5,
					node: dialog,
					start: endSize,
					end: initSize,
					run: function(rate) {
						var param = U.getParameter('buy-button');
						this.node.style
							.top = (rate*(this.end.top - this.start.top) + this.start.top)+'px';
						this.node.style
							.left = (rate*(this.end.left - this.start.left) + this.start.left)+'px';

						console.log(this.start);
						console.log(this.end);

						if(param){
							this.node.style
								.width = (rate*(this.end.width - this.start.width) + this.start.width)+'px';
							this.node.style
								.height = (rate*(this.end.height - this.start.height) + this.start.height)+'px';
						}else{
							this.node.style
								.width = (rate*(this.end.width - this.start.width) + this.start.width)+'%';
							this.node.style
								.height = (rate*(this.end.height - this.start.height) + this.start.height)+'%';
						}


					},
					onCompleted : function(){

						dialog.setAttribute('style','');
					}
				}
			]);
		}
	};
};




var K = K || {
	doAjax : function() {
		var a = window.location.hash.substr(2);
		var b = a.split("/")[1];
		switch (b) {
			case "shop":
				S.open();
				break;
			default:
				S.close();
				break
		}
	},
	shop : function Shop(){
		var dialog = q(".dialog")[0];
		this.isOpened = function(){ return Util.classList(dialog).contains("open")};
		this.open = function(){

			var dialog = q(".dialog")[0];
			//get buy button position that user clicked


			if(!this.isOpened){
				//reset style
				dialog.setAttribute('style','');
				U.animate([
					{
						time: 0.350,
						node: dialog,
						start: initSize,
						end: endSize,
						run: function(rate) {
							this.node.style
								.top = (rate*(this.end.top - this.start.top) + this.start.top)+'px';
							this.node.style
								.left = (rate*(this.end.left - this.start.left) + this.start.left)+'px';

							this.node.style
								.width = (rate*(this.end.width - this.start.width) + this.start.width)+'px';
							this.node.style
								.height = (rate*(this.end.height - this.start.height) + this.start.height)+'px';
						},
						ended : function(){
							this.doTab();
						}
					},
					{
						time: 0.350,
						node: [q(".dialog .dialog-menu")[0],q(".dialog .dialog-content")[0]],
						run: function(rate) {
							this.node[0].style.top = (rate*(0-(-100))+(-100))+"px";
						},
						ended : function(){
							K.classList(dialog).add("open");
						}
					}
				]);
			}
		}
		this.close = function(){


			//get buy button position that user clicked
			var param = K.getParameter('buy-button');
			if(param){
				var size = param.getBoundingClientRect();
				var parent = param.parentNode.parentNode.parentNode.getBoundingClientRect();
				var initSize = {backgroundColor:0x3E2733,top:(size.top-parent.top),left:(size.left-parent.left),width:size.width,height:size.height};
			}else{
				var parent = q("section .cake")[0].getBoundingClientRect();
				var initSize = {top:parent.width/2,left:parent.height/2,width:0,height:0};
			}
			var endSize = {top:0,left:0,width:parent.width,height:parent.height};

			if(this.isOpened){
				K.animate([
					{
						time: 0.350,
						node: dialog,
						start: endSize,
						end: initSize,
						run: function(rate) {
							this.node.style
								.top = (rate*(this.end.top - this.start.top) + this.start.top)+'px';
							this.node.style
								.left = (rate*(this.end.left - this.start.left) + this.start.left)+'px';

							this.node.style
								.width = (rate*(this.end.width - this.start.width) + this.start.width)+'px';
							this.node.style
								.height = (rate*(this.end.height - this.start.height) + this.start.height)+'px';
						},
						ended : function(){
							K.classList(dialog).remove("open");
						}
					}
				]);
			}
		}
		this.doTab = function(){

		}
	},
	SH : {

		isOpened : function(){ return U.classList(this.dialog).contains("open")},
		open : function(){
			console.log(U.getParameter('buy-button'));
		}
	},
	openShop : function(){


		var hash = window.location.hash.substr(2);
		var subsection = hash.split('/')[2];

		var allTab = q(".dialog .nav li");

		K.forEach(allTab,function(el){
			K.classList(el).remove("active");
		});

		var tab = q(".dialog .nav li."+subsection);
		K.classList(tab[0]).add("active");
	},
	closeShop : function(){
		var dialog = q(".dialog")[0];
		if(K.classList(dialog).contains("open"))
		{

			/*var cartBox = q(".dialog .dialog-content .box");
			var idx = 0;
			(function anim(idx){
				if(idx >= cartBox.length) {return;};
				setTimeout(function(){
					K.classList(cartBox[idx++]).add("inactive");
					anim(idx);
				},50);
			})(idx);*/

			K.classList(q(".dialog ul")[0]).add("inactive");
			K.classList(q(".dialog .dialog-content")[0]).add("inactive");






			/*setTimeout(function(){
				dialog.style.top = initSize.top;
				dialog.style.left = initSize.left;
				dialog.style.width = initSize.width+"px";
				dialog.style.height = initSize.height+"px";
				if(param)
					dialog.style.backgroundColor = '#3E2733';

				K.classList(dialog).remove("open");
				setTimeout(function(){
					dialog.setAttribute('style','');
				},350);
			},350);*/


		}
	}
};

var U,S;


document.addEventListener("DOMContentLoaded", function() {

	/*U = U || new Util();
	S = S || new Shop();

    window.addEventListener("hashchange", function(e) {
        K.doAjax();
    });

    if (window.location.hash) {
        K.doAjax();
    }

	[].forEach.call(document.querySelectorAll(".cake-box .cake-box-buy .btn"), function(a) {
        a.addEventListener("click", function() {
            U.setParameter("buy-button", this);
        })
    });*/
	
});