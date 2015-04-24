var q = function(el){
	return document.querySelectorAll(el);
}

var K = K || {
	parameters : {},
	setParameter : function(n,v){
		this.parameters[n] = v;
	},
	getParameter : function(n){
		return this.parameters[n];
	},
	doAjax : function() {
		var a = window.location.hash.substr(2);
		var b = a.split("/")[1];
		switch (b) {
			case "shop":
				this.openShop(this.parameters);
				break;
			default:
				this.closeShop(this.parameters);
				break
		}
	},
	classList : function(el){
			if(el && el.classList)
				return new(function(el){
					this.element = el;
					this.add = function(c){this.element.classList.add(c);};
					this.remove = function(c){this.element.classList.remove(c);};
					this.toggle = function(c){this.element.classList.toggle(c);};
					this.contains = function(c){return this.element.classList.contains(c);};
					return this;
				})(el);
			else if(el && el.className)
				return new (function(){
					this.add = function(c){
						var className = el.className;
						if(!this.contains(c))
							el.setAttribute('class',className+" "+c)
					};
					this.remove = function(c){
						var className = el.className;
						if(this.contains(c))
							className = className.replace(c,' ');
						el.setAttribute('class',className);
					};
					this.toggle = function(c){
						var className = el.className;
						if(className.indexOf(c)>0)
							this.remove(c);
						else
							this.add(c);
					};
					this.contains = function(c){return el.className.indexOf(c)>0};
					return this;
				})();

		},
	forEach : function(array,fn){
		[].forEach.call(array,fn);
	},
	openShop : function(){
		var cakeBoxs = q(".cake-box");
		var size = {top:cakeBoxs[0].getBoundingClientRect().top,
					left:cakeBoxs[0].getBoundingClientRect().left,
					bottom:cakeBoxs[0].getBoundingClientRect().bottom,
					right:cakeBoxs[cakeBoxs.length-1].getBoundingClientRect().right};
					
		var dialog = q(".dialog")[0];
		if(!K.classList(dialog).contains("open"))
		{
			dialog.setAttribute('style','');
			var param = K.getParameter('buy-button');
			var initSize;
			if(param){
				var size = param.getBoundingClientRect();
				var parent = param.parentNode.parentNode.parentNode.getBoundingClientRect();
				initSize = {top:(size.top-parent.top)+'px',left:(size.left-parent.left)+'px',width:size.width,height:size.height};
				dialog.style.backgroundColor = '#3E2733';
			}else
				initSize = {top:'50%',left:'50%',width:0,height:0};
				
				dialog.style.top = initSize.top;
				dialog.style.left = initSize.left;
				dialog.style.width = initSize.width+"px";
				dialog.style.height = initSize.height+"px";

			setTimeout(function(){
				dialog.style.transition = 'all 350ms';
				initSize = {top:0,left:0,width:100,height:100};
				dialog.style.top = initSize.top+'px';
				dialog.style.left = initSize.left+'px';
				dialog.style.width = initSize.width+"%";
				dialog.style.height = initSize.height+"%";
				dialog.style.backgroundColor = 'white';


				setTimeout(function(){
					K.classList(q(".dialog ul.inactive")[0]).remove("inactive");
					K.classList(q(".dialog .dialog-content.inactive")[0]).remove("inactive");

					var cartBox = q(".dialog .dialog-content .box");
					var idx = 0;
					(function anim(idx){
						if(idx >= cartBox.length) return;
						setTimeout(function(){
							K.classList(cartBox[idx++]).remove("inactive");
							anim(idx);
						},50);
					})(idx);

					K.classList(dialog).add("open");
				},350);
			},50);

				
		}

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
			var param = K.getParameter('buy-button');
			var initSize;
			if(param){
				var size = param.getBoundingClientRect();
				var parent = param.parentNode.parentNode.parentNode.getBoundingClientRect();
				initSize = {top:(size.top-parent.top)+'px',left:(size.left-parent.left)+'px',width:size.width,height:size.height};

			}else
				initSize = {top:'50%',left:'50%',width:0,height:0};

			var cartBox = q(".dialog .dialog-content .box");
			var idx = 0;
			(function anim(idx){
				if(idx >= cartBox.length) {return;};
				setTimeout(function(){
					K.classList(cartBox[idx++]).add("inactive");
					anim(idx);
				},50);
			})(idx);

			K.classList(q(".dialog ul")[0]).add("inactive");
			K.classList(q(".dialog .dialog-content")[0]).add("inactive");




			setTimeout(function(){
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
			},350);


		}
	}
};


document.addEventListener("DOMContentLoaded", function() {
	
    window.addEventListener("hashchange", function(e) {
        K.doAjax();
    });
	
    if (window.location.hash) {
        K.doAjax();
    }
	
	[].forEach.call(document.querySelectorAll(".cake-box .cake-box-buy .btn"), function(a) {
        a.addEventListener("click", function() {
            K.setParameter("buy-button", this);
        })
    });
	
});