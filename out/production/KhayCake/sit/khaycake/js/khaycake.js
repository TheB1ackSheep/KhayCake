var K;
$(document).ready(function() {
    var dialog = $(".dialog");
    var dialogContent =  $('.section-price');
    var section = $('.section-price');

    var KhayCake = KhayCake || {
            params: [],
            getParam: function(name) {
                return this.params[name];
            },
            setParam: function(name, value) {
                this.params[name] = value;
            },
            isInteger: function(str) {
                return str ? str.toString().match(/^[0-9]+$/) !== null : false;
            },
            isFloat: function(str) {
                return str ? (this.isInteger(str.toString()) || str.toString().match(/^\.[0-9]+$/) || str.toString().match(/^[0-9]+\.[0-9]+$/)) !== null : false;
            }
        };

    KhayCake.home = function(){
        $(section).html('<div class="cake">'+
            '<div class="cake-box">'+
        '<div class="cake-box-img">'+
        '<img src="images/khaycake-cupcake.png"/>'+
        '</div>'+
        '<div class="cake-box-name font-lily">Cupcake</div>'+
        '<div class="cake-box-detail secondary-text">คัพเค้กนมสด คุณภาพล้นถ้วย อร่อยไปกับเนื้อเค้กนมสด และวิปครีมไขมันต่ำหลากรสให้ท่านได้เลือกสรร พร้อมเป็นของขวัญสำหรับคนที่คุณรัก</div>'+
        '<div class="cake-box-buy"><a href="#!/cakes/cupcake" class="btn btn-primary">BUY NOW</a></div>'+
        '</div>'+
        '<div class="cake-box">'+
        '<div class="cake-box-img">'+
        '<img src="images/khaycake-crabecake-white.png"/>'+
        '</div>'+
        '<div class="cake-box-name font-lily">Crapecake</div>'+
        '<div class="cake-box-detail secondary-text">สัมผัสกับความนุ่มละมุนของชั้นแป้งเนยสดสลับกับชั้นครีมหอมหวานละลานในปาก ราดด้วยสตอเบอร์รี่ไซรัปที่ตัดกับรสเค้กอย่างลงตัว</div>'+
        '<div class="cake-box-buy"><a href="#!/cakes/crapecake" class="btn btn-primary">BUY NOW</a></div>'+
        '</div>'+
        '<div class="cake-box">'+
        '<div class="cake-box-img">'+
        '<img src="images/khaycake-logo-mask.png"/>'+
        '</div>'+
        '<div class="cake-box-name font-lily">Brownie</div>'+
        '<div class="cake-box-detail secondary-text">บราวนี่อุ่นๆจากเตา หอมกลุ่น กลมกล่อมไปกับรสช็อกโกแลตเข้มข้นที่โรยหน้าด้วยอัลมอนด์คัดสรรพิเศษ พร้อมเสิร์ฟให้คุณได้ลิ้มลองทุกวัน</div>'+
        '<div class="cake-box-buy"><a href="#!/cakes/brownie" class="btn btn-primary">BUY NOW</a></div>'+
        '</div>'+
        '<div class="cake-box">'+
        '<div class="cake-box-img">'+
        '<img src="images/khaycake-logo-mask.png"/>'+
        '</div>'+
        '<div class="cake-box-name font-lily">Party Cake</div>'+
        '<div class="cake-box-detail secondary-text">ให้งานปาร์ตี้ของคุณหอมกรุ่นไปด้วยไอของขนมเค้กสุดพิเศษ ที่จะทำให้คุณและเพื่อน ๆ ในงานปาร์ตี้ของคุณไม่มีวันลืม ด้วยสีสันและรสชาติที่ไม่อาจลืม</div>'+
        '<div class="cake-box-buy"><a  href="#!/cakes/partycake" class="btn btn-primary">BUY NOW</a></div>'+
        '</div>'+
        '</div>');
    };

    KhayCake.cakes = function(cat,fn){
        $(section).html(KhayCake.container());
        KhayCake.loadingMask($("#cake-container"));
        var cakeList = '';

        console.log(cat);
        switch (cat){
            case 'cupcake':
                Product.cupcake(function(resp) {
                    if (resp.message && resp.message.length > 0) {
                        for(var idx in resp.message){
                            var product = resp.message[idx];
                            cakeList += Product.box(product);
                        }
                    } else {
                        cakeList += '<div class="col-sm-12"><div class="box">ไม่มีสินค้าในระบบ</div></div>';
                    }
                    if(typeof(fn) === "function")
                        fn(cakeList);
                });
                break;
            case 'crapecake':
                Product.crapecake(function(resp) {
                    if (resp.message && resp.message.length > 0) {
                        for(var idx in resp.message){
                            var product = resp.message[idx];
                            cakeList += Product.box(product);
                        }
                    } else {
                        cakeList += '<div class="col-sm-12"><div class="box">ไม่มีสินค้าในระบบ</div></div>';
                    }
                    if(typeof(fn) === "function")
                        fn(cakeList);
                });
                break;
            case 'brownie':
                Product.brownie(function(resp) {
                    if (resp.message && resp.message.length > 0) {
                        for(var idx in resp.message){
                            var product = resp.message[idx];
                            cakeList += Product.box(product);
                        }
                    } else {
                        cakeList += '<div class="col-sm-12"><div class="box">ไม่มีสินค้าในระบบ</div></div>';
                    }
                    if(typeof(fn) === "function")
                        fn(cakeList);
                });
                break;
            case 'partycake':
                Product.partycake(function(resp) {
                    if (resp.message && resp.message.length > 0) {
                        for(var idx in resp.message){
                            var product = resp.message[idx];
                            cakeList += Product.box(product);
                        }
                    } else {
                        cakeList += '<div class="col-sm-12"><div class="box">ไม่มีสินค้าในระบบ</div></div>';
                    }
                    if(typeof(fn) === "function")
                        fn(cakeList);
                });
                break;
            default:
                Product.all(function(resp) {
                    if (resp.message && resp.message.length > 0) {
                        for(var idx in resp.message){
                            var product = resp.message[idx];
                            cakeList += Product.box(product);
                        }
                    } else {
                        cakeList += '<div class="col-sm-12"><div class="box">ไม่มีสินค้าในระบบ</div></div>';
                    }
                    if(typeof(fn) === "function")
                        fn(cakeList);
                });
                break;
        }
    };
    KhayCake.container = function(){
        return '<div class="row">' +
            '<div class="col-md-2">' +
            '<ul class="nav nav-pills nav-stacked">' +
            '<li role="presentation" id="all"><a href="#!/cakes/all">All</a></li>' +
            '<li role="presentation" id="cupcake"><a href="#!/cakes/cupcake">Cupcake</a></li>' +
            '<li role="presentation" id="crapecake"><a href="#!/cakes/crapecake">Crape Cake</a></li>' +
            '<li role="presentation" id="brownie"><a href="#!/cakes/brownie">Brownie</a></li>' +
            '<li role="presentation" id="partycake"><a href="#!/cakes/partycake">Party Cake</a></li>' +
            '</ul>' +
            '</div>' +
            '<div class="col-md-10">' +
            '<div id="cake-container" class="row"></div>' +
            '</div>' +
            '</div>';
    }
    KhayCake.cakes.bind = function(){
        var hash = window.location.hash.substr(2);
        var resources = hash.split('/');
        var cat = null;
        if (resources.length >= 3)
            cat = resources[2];

        $(".section-price .nav li").removeClass("active");
        $(".section-price .nav li" + "#" + cat).addClass("active");

        $("#cake-container .cart-add").on("submit",function(){
            Cart.addItem($(this).parent());
            return false;
        });
    };

    KhayCake.login = function(){
        $(section).html(KhayCake.login.form());
    };
    KhayCake.login.form = function(){
        return '<form id="form-login">' +
            '<div class="form-group">' +
            '<label>Username :</label>' +
            '<input class="form-control" id="email" placholder="ninecake@khaycake.com"/>' +
            '</div>' +
            '<div class="form-group">' +
            '<label>Password :</label>' +
            '<input class="form-control" id="pwd" type="password"/>' +
            '</div>' +
            '<div class="form-group">' +
            '<button class="btn btn-primary">Login</button>' +
            '</div>' +
            '</form>';
    }

    KhayCake.onHashChanged = function() {
        var resources = window.location.hash.substr(2).split('/');
        var method = resources[1];

        switch (method) {
            case 'cakes':
                var cat = null;
                if (resources.length >= 3)
                    cat = resources[2];
                KhayCake.cakes(cat,function(cakeList){
                    $("#cake-container").html(cakeList);
                    KhayCake.cakes.bind();
                    KhayCake.loadedMask();

                });
                break;
            case 'login':
                KhayCake.login();
                break;
            default:
                KhayCake.home();
                break;
        }
    };

    KhayCake.loadingMask = function(el){
        $(el).html("<div class='col-sm-12' style='text-align: center;overflow: hidden'><span class='loader'><span class='loader-inner'></span></span></div>");
    };
    KhayCake.loadedMask = function(el){
        $(el).html("");
    };

    var Cart = Cart || {
            ID_CART: ".cart-button",
            ID_CART_ICON: ".cart-button .cart-open",
            ID_CART_CLOSE: ".order-item .cart-close",
            total: 0,
            isOpen: function() {
                return $(this.ID_CART).hasClass("open");
            },
            open: function() {
                $(this.ID_CART).addClass("open");
            },
            close: function() {
                $(this.ID_CART).removeClass("open");
            },
            addItem: function(cakeBox) {

                if ($(cakeBox).hasClass("box")) {
                    console.log(this.ID_CART_ICON);
                    var clone = $('<div id="clone"></div>');
                    var clientRect = $(cakeBox)[0].getBoundingClientRect();


                    var isHiding = false;

                    if ($(this.ID_CART).hasClass("inactive")) {
                        $(this.ID_CART).removeClass("inactive");
                        isHiding = true;
                    }

                    setTimeout(function() {
                        var targetRect = $(Cart.ID_CART_ICON)[0].getBoundingClientRect();
                        clone.html($(cakeBox).find(".box-img").clone()).appendTo("body");

                        clone.css({
                            top: clientRect.top,
                            left: clientRect.left,
                            width: clientRect.width,
                            opacity: 1
                        });

                        clone.animate({
                            top: targetRect.top - 60,
                            left: 0,
                            width: '53px'
                        }, 500, function() {
                            clone.animate({
                                top: targetRect.top,
                                opacity: 0
                            }, 300, function() {
                                clone.remove();
                            });

                        });
                    }, isHiding ? 0 : 0);

                }

            }
        };

    $(window).bind("hashchange", function() {
        KhayCake.onHashChanged();
    });

    if (window.location.hash) {
        KhayCake.onHashChanged();
    }

    $(".cake-box-buy a").click(function(el) {
        KhayCake.setParam(Dialog.PARAM_BEGIN_POS, this);
    });

    $(Cart.ID_CART_ICON).click(function() {
        Cart.open();
    });

    $(Cart.ID_CART_CLOSE).click(function() {
        Cart.close();
    });

    K = KhayCake;
});