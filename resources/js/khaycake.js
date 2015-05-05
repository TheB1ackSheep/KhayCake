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
        $(section).html(KhayCake.cakes.container());
        KhayCake.loadingMask($("#cake-container"));
        var cakeList = '';
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
    KhayCake.cakes.container = function(){
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

        $("#cake-container .cart-add").on("submit",function(ev){
            ev.preventDefault();
            var id = $(this).serializeArray()[0].value;
            Cart.add($(this), function(resp){
                if(resp.message) {
                    $("#cart-table").html(Cart.form(resp.message));
                    KhayCake.cart.bind();
                }
            });
            KhayCake.cart.add(id);
            return false;
        });
    };

    KhayCake.cart = function(){
        Cart.all(function(resp){
            if(resp.message && resp.message.items && resp.message.items.length > 0){
                $("#cart-table").html(Cart.form(resp.message));
                KhayCake.cart.bind();

                if ($(".cart-button").hasClass("inactive"))
                    $(".cart-button").removeClass("inactive");
            }
        });
    };
    KhayCake.cart.open = function(){
        $(".cart-button").addClass("open");
    };
    KhayCake.cart.close = function(){
        $(".cart-button").removeClass("open");
    };
    KhayCake.cart.add = function(id, fn){

        var clone = $('<div id="clone"></div>');
        var clientRect = $("#img-"+id)[0].getBoundingClientRect();

        if ($(".cart-button").hasClass("inactive"))
            $(".cart-button").removeClass("inactive");


            var targetRect = $(".cart-button .cart-open")[0].getBoundingClientRect();
            clone.html($("#img-"+id).clone()).appendTo("body");

            clone.css({
                top: clientRect.top,
                left: clientRect.left,
                width: clientRect.width,
                opacity: 1
            });

            clone.animate({
                top: targetRect.top - 80,
                left: 0,
                width: '53px'
            }, 500, function() {
                clone.animate({
                    top: targetRect.top,
                    opacity: 0
                }, 300, function() {
                    clone.remove();
                    if(typeof(fn) === "function")
                        fn();
                });

            });

        };
    KhayCake.cart.bind = function(){
        $("#cart-table .column-remove span").click(function(){
            var row =  $(this).parent().parent();
            $(row).animate({
                height:0
            },350,function(){
                $(row).remove();
                Cart.update($("#user-cart"),function(resp){
                    if(resp.message) {
                        $("#cart-table").html(Cart.form(resp.message));
                        KhayCake.cart.bind();
                    }
                });
            });
            //

        });
    };

    KhayCake.checkout = function(step){

        KhayCake.cart.close();
        var html = KhayCake.checkout.header();
        $(section).html(html);

        Cart.all(function(cartResp){
            Auth.get(function(authResp){
                var items = null;
                if(cartResp.message)
                    items = cartResp.message.items;
                var email = null;
                if(authResp.message)
                    email = authResp.message.email;
                if(step == 1) {
                    KhayCake.checkout.set(KhayCake.checkout.order.form(cartResp));
                }else if(step == 2 && items && !email) {
                    KhayCake.checkout.set(KhayCake.checkout.login.form());
                }else if(step == 2 && items && email) {
                    window.location.hash = '#!/checkout/3';
                    return;
                }else if(step == 3  && items && email) {
                    KhayCake.checkout.set(KhayCake.checkout.address.form(authResp.message));

                }else if(step == 4 && items && email) {

                }else if(step == 5 && items && email) {

                }else {
                    window.location.hash = '#!/checkout/1';
                    return;
                }

                $(".checkout-progressbar li").removeClass("active");
                for(var i=1;i<=5;i++)
                    if(i<=step)
                        $(".checkout-progressbar li:nth-child("+i+")").addClass("active");
            });

        });



    };
    KhayCake.checkout.set = function(html){
        $("#checkout-container").html(html);
        KhayCake.checkout.bind();
    };
    KhayCake.checkout.bind = function(){

        $("#checkout-register").click(function(){
            KhayCake.checkout.set(KhayCake.checkout.register.form());
            return false;
        });

        $("#checkout-login").click(function(){
            KhayCake.checkout.set(KhayCake.checkout.login.form());
            return false;
        });

        $("#checkout-do-login").click(function(){
            KhayCake.checkout.login();
            return false;
        });

        $("#checkout-do-register").click(function(){
            KhayCake.checkout.register();
            return false;
        });

        $("#checkout-address").click(function(){
           return false;
        });

        $("#form-address #tumbons").on("input",function(){
           var length = $(this).val().length;
            Tumbon.find($(this).val(), function(resp){
                if(resp.message && resp.message.length > 0){
                    var html = '';
                    for(var idx in resp.message){
                        var tumbon = resp.message[idx];
                        html += '<option label='+tumbon.amphur.name+' data-id='+tumbon.id+' value='+tumbon.name+'></option>';
                    }
                    $("#form-address #tumbon-list").html(html);
                }
            });
        });

    };
    KhayCake.checkout.header = function(){
        return '<section class="checkout"><ul class="checkout-progressbar">'+
                '<li class="active">รายการสินค้า</li>'+
                '<li class="active">ยืนยันตัวตน</li>'+
                '<li>ที่อยู่ในการจัดส่ง</li>'+
                '<li>ยืนยันการสั่งซื้อ</li><li>เสร็จสิ้น</li>'+
                '</ul>' +
                '<div id="checkout-container"></div>'+
                '</section>';
    };
    KhayCake.checkout.login = function(){
        var form = $("#form-login");
        Auth.auth(form, function(resp){
           if(resp.message && resp.message.email){
               window.location.hash = '#!/checkout/3';
           }
        });
    };
    KhayCake.checkout.login.form = function(){
        return '<form id="form-login">' +
            '<div class="form-group">' +
            '<label>อีเมล์ :</label>' +
            '<input class="form-control" id="email" name="email" placholder="ninecake@khaycake.com" autocomplete="off"/>' +
            '</div>' +
            '<div class="form-group">' +
            '<label>รหัสผ่าน :</label>' +
            '<input class="form-control" id="pwd" name="pwd" type="password"/>' +
            '</div>' +
            '<div class="form-group">' +
            '<button id="checkout-do-login" class="btn btn-primary">Login</button> <a id="checkout-register" href="#!/checkout">ฉันยังไม่มีรหัสผ่าน</a>' +
            '</div>' +
            '</form>';
    };
    KhayCake.checkout.register = function(){
        var form = $("#form-register");
        Auth.register(form, function(resp){
            if(resp.message && resp.message.email){
                window.location.hash = '#!/checkout/3';
            }
        });
    };
    KhayCake.checkout.register.form = function(){
        return '<form id="form-register">' +
            '<div class="form-group">' +
            '<label>อีเมล์ :</label>' +
            '<input class="form-control" id="email" name="email" placholder="ninecake@khaycake.com" autocomplete="off"/>' +
            '</div>' +
            '<div class="form-group">' +
            '<label>รหัสผ่าน :</label>' +
            '<input class="form-control" id="pwd" name="pwd" type="password"/>' +
            '</div>' +
            '<div class="form-group">' +
            '<label>ยืนยันรหัสผ่าน :</label>' +
            '<input class="form-control" id="confirm-pwd" name="confirm-pwd" type="password"/>' +
            '</div>' +
            '<div class="form-group">' +
            '<button id="checkout-do-register" class="btn btn-primary">Register</button> <a id="checkout-login" href="#!/checkout">ฉันมีรหัสผ่านแล้ว</a>' +
            '</div>' +
            '</form>';
    };
    KhayCake.checkout.address = {};
    KhayCake.checkout.address.form = function(user){
        return '<form id="form-address">' +
                '<div class="row">' +
                '<h3 class="title">ที่อยู่ในของคุณ</h3>'+
                '<p class="subtitle">หากคุณเคยกรอกที่อยู่ไว้แล้ว จะแสดงที่เลือกด้านล่าง</p>'+
                '<div id="address-container"></div>'+
                '<h3>เพิ่มที่อยู่ใหม่</h3>'+
                '<div class="col-sm-6">' +
                    '<div class="form-group">' +
                        '<label>ชื่อจริง</label><input type="text" name="fname" id="fname" class="form-control" autocomplete="off"/>' +
                    '</div>' +
                '</div>' +
                '<div class="col-sm-6">' +
                    '<div class="form-group">' +
                        '<label>นามสกุล</label><input type="text" name="lname" id="lname" class="form-control" autocomplete="off"/>' +
                    '</div>' +
                '</div>' +
                '<div class="col-md-12">' +
                    '<div class="form-group">' +
                        '<label>ที่อยู่</label>' +
                        '<textarea name="addr" id="addr" cols="30" rows="8" class="form-control" placeholder="123 ซอย 13 หมู่บ้านขายเค้ก ถนนเค้กนุ่ม">' +
                        '</textarea>' +
                    '</div>' +
                '</div>' +
                '<div class="col-md-6">' +
                    '<div class="form-group">'+
                        '<label>ตำบล/เขต</label><input list="tumbon-list" type="text" class="form-control" name="tumbons" id="tumbons"/>' +
                        '<datalist id="tumbon-list"></datalist>'+
                    '</div>' +
                '</div>'+
                '<div class="col-md-6">' +
                    '<div class="form-group">'+
                        '<label>อำเภอ/แขวง</label><input type="text" class="form-control" name="amphur" id="amphur"/>' +
                    '</div>' +
                '</div>'+
                '<div class="col-md-6">' +
                    '<div class="form-group">' +
                        '<label>จังหวัด</label><input type="text" class="form-control" name="province" id="province"/>'+
                    '</div>' +
                '</div>' +
                '<div class="col-md-6">' +
                    '<div class="form-group">' +
                        '<label>รหัสไปรษณีย์</label><input type="text" class="form-control" name="zipcode" id="zipcode"/>' +
                    '</div>' +
                '</div>'+
                '<div class="col-sm-12 text-right"><button id="checkout-address" class="btn btn-primary">ถัดไป</button></div>'+
                '</form>' +
            '</div>';
    }
    KhayCake.checkout.order = {};
    KhayCake.checkout.order.form = function(resp){

        var html = '<form id="form-cart"><table class="table"><tr><th>ชื่อ</th><th>จำนวน</th><th class="text-right">ราคา (บาท)</th></tr>';
        var hasNext = false;
        if(resp.message && resp.message.items && resp.message.items.length > 0){
            hasNext = true;
            var items = resp.message.items;
            var total = 0.0;
            for(var idx in items){
                var item = items[idx];
                html += '<tr><td>'+item.product.name+'</td>' +
                    '<td>'+item.qty+' '+item.product.unit.name+'</td>' +
                    '<td class="text-right">'+toMoney(item.total)+'</td></tr>';
                total += item.total;
            }
            html += '<tr><td colspan="2" class="text-right">รวม</td><td class="text-right">'+toMoney(total)+'</td></tr>';

        }else{
            html += '<tr><td colspan="3">ไม่มีสินค้าในตะกร้า</td></tr>';
        }
        html += "</table>";
        if(hasNext)
            html += '<div class="col-md-12 text-right"><a class="btn btn-primary" id="checkout-next" href="#!/checkout/2">ถัดไป</a></div>';
        html += '</form>';
        return html;


    };


    KhayCake.login = function(){
        $(section).html(KhayCake.login.form());
    };
    KhayCake.login.form = function(){

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
            case 'checkout':
                var step = null;
                if(resources.length >= 3)
                step = resources[2];
                KhayCake.checkout(step);
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

    $(window).bind("hashchange", function() {
        KhayCake.onHashChanged();
    });

    if (window.location.hash) {
        KhayCake.onHashChanged();
    }

    $(".cart-button .cart-open").click(function() {
        KhayCake.cart.open();
    });

    $(".order-item .cart-close").click(function() {
        KhayCake.cart.close();
    });

    KhayCake.cart();
});