var KhayCake = KhayCake || {
        parameters : [],
        AJAX : function AJAX(method,url,params,fn){
            var httpRequest = (window.XMLHttpRequest)?new XMLHttpRequest():(window.ActiveXObject)?new ActiveXObject("Msxml2.XMLHTTP"):new ActiveXObject("Microsoft.XMLHTTP");
            httpRequest.onreadystatechange = function(){
                if (httpRequest.readyState === 4) {
                    if(httpRequest.status === 200){
                        if(typeof(fn) === "object" && fn.success)
                            fn.success(httpRequest.response);
                    }else{
                        if(typeof(fn) === "object" && fn.error)
                            fn.error(httpRequest.response);
                    }
                }
            }

            var q = "",c = 0;
            for(var k in params)
                q += k+"="+params[k]+((c++<Object.keys(params).length-1)?"&":"");

            httpRequest.open(method,url+((params && method === "GET")?"?"+q:""));
            if(method === "POST")
                httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            httpRequest.send((method==="POST")?q:null);

        },
        doAjax : function(){
            var hash = window.location.hash.substr(2); //hash will return as /XX/XX
            var section = hash.split("/")[1];
            switch(section){
                case "shop":
                    this.openShop(this.parameters);
                    this.filterShop();
                    break;
                default :
                    this.closeShop(this.parameters);
                    break;
            }

        },
        forEach : function(arrays,fn){
            [].forEach.call(arrays,fn);
        },
        setParameter : function(name,value){
            this.parameters[name] = value;
        },
        getParameter : function(name){
            return this.parameters[name];
        },
        classList : function(el){
          if(el && el.classList)
            return function(){
                this.add = function(c){el.classList.add(c);};
                this.remove = function(c){el.classList.remove(c);};
                this.toggle = function(c){el.classList.toggle(c);};
            };
          else if(el && el.className)
              return function(){
                  this.add = function(c){
                      var className = el.className;
                      if(!className.contains(c))
                        el.setAttribute('class',className+" "+c)
                  };
                  this.remove = function(c){
                      var className = el.className;
                      if(className.contains(c))
                          className.replace(c,' ');
                      el.setAttribute('class',className)
                  };
                  this.toggle = function(c){
                      var className = el.className;
                      if(className.contains(c))
                        this.remove(c);
                      else
                        this.add(c);
                  };
              };

        },
        openShop : function(options){
            document.querySelector(".modal.shop .row").innerHTML = "";

            var hash = window.location.hash; //return as /XX/XX
            var param = hash.substr(2).split("/")[2]; //cake name

            this.forEach(document.querySelectorAll(".modal.shop .tab a"),function(el){
                if(el.classList)
                    el.classList.remove("active");
            });

            var targetTab = document.querySelector(".modal.shop .tab a."+param);
            if(targetTab.classList)
                targetTab.classList.add("active");
            this.filterShop();

            if(!document.querySelector(".modal.shop").classList.contains("open")){
                var size;
                if(options && options.buyBtn)
                    size = options.buyBtn.getBoundingClientRect();
                else{
                    var client = document.body.getBoundingClientRect();
                    size ={
                        top: client.height/2,
                        right:client.width/2,
                        bottom:client.height/2,
                        left:client.width/2,
                        width: 0,
                        height:0 };
                }

                var modal = document.querySelector(".modal.shop");
                modal.classList.add("open");
                modal.style.top = size.top+"px";
                modal.style.right = size.right+"px";
                modal.style.bottom = size.bottom+"px";
                modal.style.left = size.left+"px";
                modal.style.height = size.height+"px";
                modal.style.width = size.width+"px";
                modal.style.backgroundColor = "#3E2723";
                modal.style.transition = "top 250ms,right 250ms,bottom 250ms,left 250ms,width 250ms,height 250ms";

                setTimeout(function() {
                    modal.style.top = "0px";
                    modal.style.right = "0px";
                    modal.style.bottom = "0px";
                    modal.style.left = "0px";
                    modal.style.height = "100%";
                    modal.style.width = "100%";
                    modal.querySelector(".container").classList.remove("hide");

                    setTimeout(function(){

                        K.forEach(modal.querySelectorAll(".tab a"),function(el){
                            el.classList.remove("hide");
                        });

                    },100);
                },100);

            }



        },
        filterShop : function(){
            var hash = window.location.hash; //return as /XX/XX
            var param = hash.substr(2).split("/")[2]; //cake name
            //filter cake
            K.forEach(document.querySelectorAll(".modal.shop .tab .row .box"),function(el){
                el.classList.add("hide");
                setTimeout(function(){
                    el.parentNode.remove(el);
                },350)
            });

            document.querySelector(".modal.shop .tab .row").innerHTML = "<span class='loader'><span class='loader-inner'></span></span>";

            setTimeout(function(){
                var cakes = null;
                if(param == ("cup-cake"))
                    cakes = K.getCupcake();
                else if(param == ("crape-cake"))
                    cakes = K.getCrapeCake();
                else if(param == ("brownie-cake"))
                    cakes = K.getBrownieCake();
                else if(param == ("party-cake"))
                    cakes = K.getPartyCake();
                else if(param == ("cart"))
                    cakes = K.getCheckout();

                if(cakes!=null){
                    var container = document.querySelector(".modal.shop .tab .row");
                    container.innerHTML = cakes;
                }

                var cake_list = document.querySelectorAll(".modal.shop .tab .box");
                var idx = 0;
                (function anum(idx){
                    if(idx < cake_list.length){
                        var el = cake_list[idx++];
                        setTimeout(function(){
                            el.classList.remove("hide");
                            anum(idx);
                        },100);
                    }
                })(idx);
            },1000);
        },
        closeShop : function(options){
            var modal = document.querySelector(".modal.shop");
            modal.querySelector(".container").classList.add("hide");
            modal.classList.remove("open");
            var size;
            if(options && options.buyBtn)
                size = options.buyBtn.getBoundingClientRect();
            else{
                var client = document.body.getBoundingClientRect();
                size ={
                    top: client.height/2,
                    right:client.width/2,
                    bottom:client.height/2,
                    left:client.width/2,
                    width: 0,
                    height:0 };
            }

            modal.style.top = size.top+"px";
            modal.style.right = size.right+"px";
            modal.style.bottom = size.bottom+"px";
            modal.style.left = size.left+"px";
            modal.style.height = size.height+"px";
            modal.style.width = size.width+"px";

            setTimeout(function(){
                modal.setAttribute('style','');
                K.forEach(document.querySelectorAll(".shop .tab a"),function(el){
                    if(el.classList)
                        el.classList.add("hide");
                });
            },500);
        },
        getCupcake : function(){
            return '<div class="box cake-box cup-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Crushed Peanut</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                ' <div class="box cake-box cup-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Crushed Peanut</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                ' <div class="box cake-box cup-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Crushed Peanut</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                ' <div class="box cake-box cup-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Crushed Peanut</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                ' <div class="box cake-box cup-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Crushed Peanut</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>';
        },
        getCrapeCake : function(){
            return '<div class="box cake-box crape-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Crape Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                '<div class="box cake-box crape-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Crape Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                '<div class="box cake-box crape-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Crape Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                '<div class="box cake-box crape-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Crape Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                '<div class="box cake-box crape-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Crape Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>';
        },
        getBrownieCake : function(){
            return '<div class="box cake-box brownie-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Brownie Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                '<div class="box cake-box brownie-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Brownie Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                '<div class="box cake-box brownie-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Brownie Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                '<div class="box cake-box brownie-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Brownie Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                '<div class="box cake-box brownie-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Brownie Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>';
        },
        getPartyCake : function(){
            return '<div class="box cake-box party-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Party Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                '<div class="box cake-box party-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Party Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                '<div class="box cake-box party-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Party Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                '<div class="box cake-box party-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Party Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>' +
                '<div class="box cake-box party-cake hide"><div class="img"><img src="images/cupcake.jpg" alt=""/></div><div class="title">Party Cake</div><div class="add-cart"><div class="price">&#3647;56</div><div>Add to Cart</div></div></div>';
        },
        getCheckout : function(){
            return '<div class="box checkout_box hide"><table><thead><tr><th></th><th>Your order</th><th>Price</th></tr></thead><tbody><tr><th>1x</th><td>Crushed Peanut</td><td>$56</td></tr><tr><th>2x</th><td>Your order</td><td>Price</td></tr><tr><th>1x</th><td>Your order</td><td>Price</td></tr></tbody><tfoot><tr><th></th><th class="text-right">Total</th><th class="text-right">$56</th></tr></tfoot></table></div>';
        }

    };

var K = KhayCake;

document.addEventListener("DOMContentLoaded",function() {

    window.addEventListener("hashchange", function () {
        K.doAjax();
    });

    if (window.location.hash) {
        K.doAjax();
    }

    [].forEach.call(document.querySelectorAll(".box .btn"), function (el) {
        el.addEventListener("click", function () {
            K.setParameter("buyBtn", this);
        });
    });

    document.querySelector(".checkout").addEventListener("click",function(){
       this.classList.toggle("active");
    });

});






