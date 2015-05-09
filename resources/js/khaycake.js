var K;
$(document).ready(function () {
    var dialog = $(".dialog");
    var section = $('.section-content');
   
    var KhayCake = KhayCake || {};

    KhayCake.url = {};
    KhayCake.url.parse = function(){
		var obj = {};
        var hash = window.location.hash.substr(2).split('?');
		obj.resources = hash[0].split('/');
		obj.params = {};
		if(hash[1]){
			var parameters = hash[1].split('&');
                for(var idx in parameters) {
                    var param = parameters[idx].split('=');
                    obj.params[param[0]] = param[1];
                }
		}
        return obj;
	};

    KhayCake.home = function () {
        $(section).html('<div class="cake">' +
        '<div class="cake-box">' +
        '<div class="cake-box-img">' +
        '<img src="images/khaycake-cupcake.png"/>' +
        '</div>' +
        '<div class="cake-box-name font-lily">Cupcake</div>' +
        '<div class="cake-box-detail secondary-text">คัพเค้กนมสด คุณภาพล้นถ้วย อร่อยไปกับเนื้อเค้กนมสด และวิปครีมไขมันต่ำหลากรสให้ท่านได้เลือกสรร พร้อมเป็นของขวัญสำหรับคนที่คุณรัก</div>' +
        '<div class="cake-box-buy"><a href="#!/cake/cupcake" class="btn btn-primary">BUY NOW</a></div>' +
        '</div>' +
        '<div class="cake-box">' +
        '<div class="cake-box-img">' +
        '<img src="images/khaycake-crabecake-white.png"/>' +
        '</div>' +
        '<div class="cake-box-name font-lily">Crapecake</div>' +
        '<div class="cake-box-detail secondary-text">สัมผัสกับความนุ่มละมุนของชั้นแป้งเนยสดสลับกับชั้นครีมหอมหวานละลานในปาก ราดด้วยสตอเบอร์รี่ไซรัปที่ตัดกับรสเค้กอย่างลงตัว</div>' +
        '<div class="cake-box-buy"><a href="#!/cake/crapecake" class="btn btn-primary">BUY NOW</a></div>' +
        '</div>' +
        '<div class="cake-box">' +
        '<div class="cake-box-img">' +
        '<img src="images/khaycake-logo-mask.png"/>' +
        '</div>' +
        '<div class="cake-box-name font-lily">Brownie</div>' +
        '<div class="cake-box-detail secondary-text">บราวนี่อุ่นๆจากเตา หอมกลุ่น กลมกล่อมไปกับรสช็อกโกแลตเข้มข้นที่โรยหน้าด้วยอัลมอนด์คัดสรรพิเศษ พร้อมเสิร์ฟให้คุณได้ลิ้มลองทุกวัน</div>' +
        '<div class="cake-box-buy"><a href="#!/cake/brownie" class="btn btn-primary">BUY NOW</a></div>' +
        '</div>' +
        '<div class="cake-box">' +
        '<div class="cake-box-img">' +
        '<img src="images/khaycake-logo-mask.png"/>' +
        '</div>' +
        '<div class="cake-box-name font-lily">Party Cake</div>' +
        '<div class="cake-box-detail secondary-text">ให้งานปาร์ตี้ของคุณหอมกรุ่นไปด้วยไอของขนมเค้กสุดพิเศษ ที่จะทำให้คุณและเพื่อน ๆ ในงานปาร์ตี้ของคุณไม่มีวันลืม ด้วยสีสันและรสชาติที่ไม่อาจลืม</div>' +
        '<div class="cake-box-buy"><a  href="#!/cake/partycake" class="btn btn-primary">BUY NOW</a></div>' +
        '</div>' +
        '</div>');
    };

    KhayCake.cake = function () {
        
        var fetchCake = Product.all;
        var url = KhayCake.url.parse();
		var page = url.params.page;
        var cat = url.resources[2];

        $(section).html(KhayCake.cake.container());

        KhayCake.loadingMask("#cake-container");
		
		$("li").removeClass("active");
		$("li#"+cat).addClass("active");
		
		var query = url.params.query;

        if(query)
            $("#form-search-cake").find("#keyword").val(query);

        if(cat == "cupcake")
            fetchCake = Product.cupcake;
        else if (cat == "crapecake")
            fetchCake = Product.crapecake;
        else if (cat == "brownie")
            fetchCake = Product.brownie;
        else if (cat == "partycake")
            fetchCake = Product.partycake;	
		else
			fetchCake = Product.all;	

        fetchCake(function (resp) {	
			var html = '';
            if (resp.message && resp.message.length > 0) {
                var cakes = pagination( resp.message,page,8);                
                for (var idx in cakes.data) {
                    var product = cakes.data[idx];
                    html += Product.box(product);
                }
                html += '<div class="page">'+cakes.html+'</div>'; 

            } else {
                html += '<div class="col-sm-12">ไม่มีสินค้าในระบบ</div>';
            }
			
            $("#cake-container").html(html);
            KhayCake.cake.bind();

        },query);

    };
    KhayCake.cake.container = function () {
        return '<div class="row">' +
            '<div class="col-sm-2">' +
            '<ul class="nav nav-pills nav-stacked">' +
            '<li role="presentation" id="all"><a href="#!/cake/all">ทั้งหมด</a></li>' +
            '<li role="presentation" id="cupcake"><a href="#!/cake/cupcake">คัพเค้ก</a></li>' +
            '<li role="presentation" id="crapecake"><a href="#!/cake/crapecake">เครปเค้ก</a></li>' +
            '<li role="presentation" id="brownie"><a href="#!/cake/brownie">บราวนี้</a></li>' +
            '<li role="presentation" id="partycake"><a href="#!/cake/partycake">ปาร์ตี้เค้ก</a></li>' +
            '</ul>' +
            '</div>' +
            '<div class="col-sm-10">' +
            '<form id="form-search-cake"><div class="input-group" style="margin-bottom: 18px;"><input type="text" name="q" id="keyword" class="form-control" placeholder="ค้นหาตามชื่อเค้ก, คำอธิบาย" required=""><span class="input-group-btn"><button class="btn btn-default" id="search-cake" type="submit">ค้นหา</button></span></div></form>'+
            '<div id="cake-container" class="row"></div>' +
            '</div>' +
            '</div>';
    };
    KhayCake.cake.bind = function () {
		var url = KhayCake.url.parse();
        var cat = url.resources[2];
		
        $(".section-price .nav li").removeClass("active");
        $(".section-price .nav li" + "#" + cat).addClass("active");

        $("#cake-container .cart-add").on("submit", function (ev) {
            ev.preventDefault();
            var id = $(this).serializeArray()[0].value;
            Cart.add($(this), function (resp) {
                if (resp.message) {
                    $("#cart-table").html(Cart.form(resp.message));
                    KhayCake.cart.bind();
                }
            });
            KhayCake.cart.add(id);
            return false;
        });

        $("#form-search-cake").submit(function(){
            var url = "/product";
            switch(cat){
                case 'cupcake':
					
                    window.location.hash = "#!/cake/cupcake?query="+$(this).find("#keyword").val();
                  break;
                case 'crapecake':
                    window.location.hash = "#!/cake/crapecake?query="+$(this).find("#keyword").val();
                    break;
                case 'brownie':
                    window.location.hash = "#!/cake/brownie?query="+$(this).find("#keyword").val();
                    break;
                case 'partycake':
                    window.location.hash = "#!/cake/partycake?query="+$(this).find("#keyword").val();
                    break;
                default:
                    window.location.hash = "#!/cake/all?query="+$(this).find("#keyword").val();
                    break;
            }
			
            return false;
        });
    };
	
    KhayCake.member = function(){
		var url = KhayCake.url.parse();
        var sect = url.resources[2];
		
        KhayCake.loadingMask(section);

        Auth.get(function(resp){
            if(resp.message){
                var user = resp.message;
                var isLogin = (user!=403);
                switch (sect){
                    case 'login':
                        if(isLogin){
                            window.location.hash = "#!/member";
                        }else{
                            $(section).html(KhayCake.member.login.form());
                        }
                        break;
                    case 'register':
                            $(section).html(KhayCake.member.register.form());
                        break;
                    default :
                        if(isLogin){
                            KhayCake.member.show();
                        }else{
                            window.location.hash = "#!/member/login";
                        }
                        break;
                }
            }
            KhayCake.member.bind();
        });

    };
    KhayCake.member.show = function(){
        var url = KhayCake.url.parse();
		var sect = url.resources[2];

        $(section).html(KhayCake.member.container());
        $("ul li").removeClass("active");

        switch (sect){
            case 'order':
                KhayCake.member.order();
                $("#user-order").addClass("active");
                break;
            case 'payment':
                KhayCake.member.payment();
                $("#user-payment").addClass("active");
                break;
            case 'logout':
                Auth.logout(function(resp){
                   if(resp.message){
                       window.location.hash = "#!/member";
                   }
                });
                break;
            default :
                KhayCake.member.main();
                $("#user-main").addClass("active");
                break;
        }
    };
    KhayCake.member.main = function(){
        KhayCake.member.set('<a href="#!/member/logout" class="btn btn-danger">Logout</a>');
    };
    KhayCake.member.container = function(){
        return '<div class="row">' +
            '<div class="col-md-2">' +
            '<ul class="nav nav-pills nav-stacked">' +
            '<li role="presentation" id="user-main" class="active">' +
            '<a href="#!/member">ข้อมูลทั่วไป</a>' +
            '</li>' +
            '<li role="presentation" id="user-order">' +
            '<a href="#!/member/order">รายการสั่งซื้อ</a>' +
            '</li>' +
            '<li role="presentation" id="user-payment">' +
            '<a href="#!/member/payment">รายการแจ้งชำระเงิน</a>' +
            '</li>' +
            '</ul></div>' +
            '<div class="col-md-10" id="member-container"></div>';
    };
    KhayCake.member.bind = function(){
        $("#form-login").submit(KhayCake.member.login);
        $("#form-register").submit(KhayCake.member.register);
    };
    KhayCake.member.login = function(){
        KhayCake.loadingMask($(section));
        var data = {email:$(this).find("#email").val()};
        Auth.auth($(this),function(resp){
            if(resp.message && resp.message.email){
                window.location.hash = "#!/member";
            }else{
                $(section).html( KhayCake.member.login.form(resp.message,data));
                KhayCake.member.bind();
            }
        });
        return false;
    };
    KhayCake.member.login.form =  function(message,data){
        if(!data)
            data = {};
        return '<form id="form-login">' +
            (message?'<div class="alert alert-warning" role="alert">'+message+'</div>':'')+
            '<div class="form-group">' +
            '<label>อีเมล์ :</label>' +
            '<input class="form-control" id="email" name="email" placholder="ninecake@khaycake.com" autocomplete="off" value="'+(data.email?data.email:'test@test.test')+'"/>' +
            '</div>' +
            '<div class="form-group">' +
            '<label>รหัสผ่าน :</label>' +
            '<input class="form-control" id="pwd" name="pwd" type="password" value="test"/>' +
            '</div>' +
            '<div class="form-group">' +
            '<button id="member-do-login" class="btn btn-primary">Login</button> <a href="#!/member/register">ฉันยังไม่มีรหัสผ่าน</a>' +
            '</div>' +
            '</form>';
    };
    KhayCake.member.register = function(){
        KhayCake.loadingMask($(section));
        var data = {email:$(this).find("#email").val()};
        Auth.register($(this),function(resp){
            if(resp.message && resp.message.email){
                window.location.hash = "#!/member";
            }else{
                $(section).html( KhayCake.member.register.form(resp.message,data));
                KhayCake.member.bind();
            }
        });
        return false;
    };
    KhayCake.member.register.form = function(message,data){
        if(!data)
            data = {};
        return '<form id="form-register">' +
            (message?'<div class="alert alert-warning" role="alert">'+message+'</div>':'')+
            '<div class="form-group">' +
            '<label>อีเมล์ :</label>' +
            '<input class="form-control" id="email" name="email" placholder="ninecake@khaycake.com" autocomplete="off" value="'+(data.email?data.email:'')+'"/>' +
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
            '<button id="member-do-register" class="btn btn-primary">สมัครสมาชิก</button> <a href="#!/member/login">ฉันมีรหัสผ่านแล้ว</a>' +
            '</div>' +
            '</form>';
    };
    KhayCake.member.set = function(html){
      $("#member-container").html(html);
        KhayCake.member.bind();
    };

    KhayCake.member.order = function(){

        KhayCake.loadingMask($("#member-container"));

        var url = KhayCake.url.parse();
		var page = url.params.page;
        var id = url.resources[3];
		var method = url.resources[4];

        if(!id){
            Auth.order.all(function(resp){
                if(resp) {
                    var orders = pagination( resp.message,page,8);
                    var html = KhayCake.member.order.table(orders.data);
                    if(orders.hasNext)
                        html += '<div class="page">'+orders.html+'</div>';
                    KhayCake.member.set(html);

                }
                KhayCake.member.order.bind();
            });
        }else{
            if(!method || (method && method != "confirm")) {
                Auth.order.getItem(id, function (resp) {
                    if (resp.message && resp.message.length > 0) {
                        var orderitems = resp.message;
                        var order = orderitems[0].order;
                        KhayCake.member.set(KhayCake.member.order.form(order, orderitems));
                    } else {
                        KhayCake.member.set("ไม่พบรายการสั่งซื้อ");
                    }
                    KhayCake.member.order.bind();
                });
            }else{
                Auth.order.find(id, function(resp){
                    if(resp.message) {
                        BankAccount.all(function(bankAccResp){
                            KhayCake.member.set(KhayCake.member.order.confirm.form(resp.message));
                            $("#bankaccount-container").html(KhayCake.member.bankaccount.form(bankAccResp.message));
                            KhayCake.member.order.bind();
                        });

                    }else
                        KhayCake.member.set("ไม่พบรายการสั่งซื้อ");

                });

            }
        }


    };
    KhayCake.member.order.bind = function(){
        $("#form-order-payment").submit(KhayCake.member.order.confirm);

        $('#date').datepicker({
            autoclose: true,
            format: "dd/mm/yyyy"
        });

        $("#time").timepicker({showMeridian:false,showSeconds:false});


        $(".page-prev").click(function(){
            KhayCake.member.order.page -= 1;
            KhayCake.onHashChanged();
            return false;
        });

        $(".page-button").click(function(){
            KhayCake.member.order.page = parseInt($(this).text());
            KhayCake.onHashChanged();
            return false;
        });

        $(".page-next").click(function(){
            KhayCake.member.order.page += 1;
            KhayCake.onHashChanged();
            return false;
        });
    };
    KhayCake.member.order.table = function(orders){
        var html = '<table class="table">';
        html += '<tr><th>เลขที่สั่งซื้อ</th><th>วันที่สั่งซื้อ</th><th>จำนวน (ชิ้น)</th><th>สถานะ</th><th class="text-right">ราคาทั้งหมด (บาท)</th></tr>';
        if(orders && orders.length > 0){
            for(var idx in orders) {
                var order = orders[idx];
                var color = '';
                if(order.status.id == 1 || order.status.id == 2 || order.status.id == 5)
                    color = "rgb(226, 163, 0)";
                else if( order.status.id == 3)
                    color = "green";
                else
                    color = "red";
                html += '<tr><td><a href="#!/member/order/'+order.id+'">#'+order.id+'</a></td><td>'+order.date+'</td>' +
                    '<td>'+order.totalQty+'</td><td><span style="color:'+color+'">'+order.status.name+' '+(order.status.id==3&&order.trackId?'เลขติดตาม '+order.trackId:'')+'</span></td><td  class="text-right">'+toMoney(order.totalPrice)+'</td></tr>';
            }

        }else{
            html += '<tr><td colspan="5">ไม่มีรายการสั่งซื้อ</td></tr>';
        }
        html += '</table>';
        return html;
    };
    KhayCake.member.order.form = function (order,orderitems) {
        var address = order.shipAddress;

        var html = '';
        if (address) {
            html += '<form class="order" id="form-order">' +
                '<h2>รายการสั่งซื้อ #'+order.id+'</h2><h3>ที่อยู่ในการจัดส่ง</h3>' +
                '<p>คุณ' + address.firstName + ' ' + address.lastName + '<br/>' +
                address.address + '<br/>' +
                address.tumbon.name + ', ' + address.tumbon.amphur.name + '<br/>' +
                address.tumbon.amphur.province.name + ' ' + address.tumbon.zipcode + '</p>';

        }

        html += '<h3>รายการสินค้า</h3>';

        html += '<form id="form-order"><table class="table"><tr><th>ชื่อ</th><th>จำนวน</th><th class="text-right">ราคา (บาท)</th></tr>';

        if (orderitems.length > 0) {
            var items = orderitems;
            var total = 0.0;
            for (var idx in items) {
                var item = items[idx];
                html += '<tr><td>' + item.product.name + '</td>' +
                    '<td>' + item.qty + ' ' + item.product.unit.name + '</td>' +
                    '<td class="text-right">' + toMoney(item.amount) + '</td></tr>';
                total += item.amount;
            }
            if(order.shipMethod) {
                html += '<tr><td>ค่าจัดส่ง (' + order.shipMethod.name + ')</td>' +
                    '<td></td>' +
                    '<td class="text-right">' + toMoney(order.shipMethod.price) + '</td></tr>';
                total += order.shipMethod.price;
            }
            html += '<tr><td colspan="2" class="text-right">รวม</td><td class="text-right" id="order-total">' + toMoney(total) + '</td></tr>';

        } else {
            html += '<tr><td colspan="3">ไม่มีสินค้าในตะกร้า</td></tr>';
        }
        html += "</table>";
        if(order.status.id == 1 || order.status.id == 5)
            html += '<div class="text-right"><a class="btn btn-primary" href="#!/member/order/'+order.id+'/confirm">แจ้งชำระเงิน</a></div>';
        html += '</form>';

        html += '</form>';
        return html;

    };
    KhayCake.member.order.confirm = function(){
        var form = $("#form-order-payment");
        KhayCake.loadingMask($("#member-container"));
        Payment.confirm(form,function(resp){
            if(resp.message.id){
                KhayCake.member.set(KhayCake.member.order.confirm.complete());
            }else{
                KhayCake.member.set(KhayCake.member.order.confirm.incomplete());
            }
        });
        return false;
    };
    KhayCake.member.order.confirm.form = function(order){
        var date = new Date();
        var html = '<form id="form-order-payment" class="form-horizontal">'+
            '<h3>แจ้งชำระเงิน รายการสั่งซื้อ #'+order.id+'</h3>'+
                '<input type="hidden" name="order_id" value="'+order.id+'">'+
            '<div class="form-group">' +
            '<label class="col-sm-2 control-label">เลขที่การสั่งซื้อ</label>' +
            '<div class="col-sm-10"><p class="form-control-static">'+order.id+'</p></div>' +
            '</div>'+
            '<div class="form-group">' +
            '<label class="col-sm-2 control-label">โอนเข้าบัญชี</label>' +
            '<div class="col-sm-10"><div id="bankaccount-container"></div></div>' +
            '</div>'+
            '<div class="form-group">' +
            '<label class="col-sm-2 control-label">วัน</label>' +
            '<div class="col-sm-10"><div class="input-group date"><input type="text" name="date" id="date" class="form-control" value="'+date.getDate()+'/'+date.getMonth()+'/'+date.getFullYear()+'"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span> </div></div>' +
            '</div>'+
            '<div class="form-group">' +
            '<label class="col-sm-2 control-label">เวลา (โดยประมาณ)</label>' +
            '<div class="col-sm-10"><div class="input-group time"><input type="text" name="time" id="time" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span> </div></div>' +
            '</div>'+
            '<div class="form-group">' +
            '<label class="col-sm-2 control-label">จำนวน (บาท)</label>' +
            '<div class="col-sm-10"><input name="amount" class="form-control" value="'+order.totalPrice.toFixed(2)+'"></div>' +
            '</div>';
            html += '<button class="btn btn-primary">ยืนยันการชำระเงิน</button>';
        html += '</form>';
        return html;
    };
    KhayCake.member.order.confirm.complete = function(){
      return '<div class="checkout-complete"><h2>เย่! การสั่งซื้อเสร็จสมบูรณ์</h2><div class="checkout-complete-img"><img src="images/spongebob.jpg"></div>' +
        '<h3>เตรียมรอรับของได้เลย</h3></div>';
    };
    KhayCake.member.order.confirm.incomplete = function(){
        return '<div class="checkout-complete"><h2>โอ้วไม่! มีบางอย่างผิดพลาด</h2><div class="checkout-complete-img"><img src="images/sad.png"></div>' +
            '<h3>กรุณาลองใหม่อีกครั้งภายหลัง</h3></div>';
    };
    KhayCake.member.payment = function(){
        var url = KhayCake.url.parse();
		var page = url.params.page;

        KhayCake.loadingMask($("#member-container"));

        Auth.payment.all(function(resp){
            var orders = pagination( resp.message,page,8);
                var html = KhayCake.member.payment.table(orders.data);
                if(orders.hasNext)
                    html += '<div class="page">'+orders.html+'</div>';
                KhayCake.member.set(html);
            KhayCake.member.payment.bind();
        });
    };
    KhayCake.member.payment.bind = function(){
        $(".page-prev").click(function(){
            KhayCake.member.payment.page -= 1;
            KhayCake.onHashChanged();
            return false;
        });

        $(".page-button").click(function(){
            KhayCake.member.payment.page = parseInt($(this).text());
            KhayCake.onHashChanged();
            return false;
        });

        $(".page-next").click(function(){
            KhayCake.member.payment.page += 1;
            KhayCake.onHashChanged();
            return false;
        });
    };
    KhayCake.member.payment.table = function(payments){
        var html = '<table class="table">';
        html += '<tr><th>เลขที่สั่งซื้อ</th><th>วันที่แจ้ง</th><th>โอนเข้าบัญชี</th><th>สถานะ</th><th class="text-right">ยอดที่แจ้ง (บาท)</th></tr>';
        if(payments && payments.length > 0){
            for(var idx in payments) {
                var payment = payments[idx];
                var color = '';
                if(payment.status.id == 1)
                    color = 'rgb(226, 163, 0)';
                else if(payment.status.id == 2)
                    color = 'green';
                else
                    color = 'red';
                html += '<tr><td><a href="#!/member/order/'+payment.order.id+'">'+payment.order.id+'</a></td><td><span style="font-size:12px">'+payment.dateTime+'</span></td>' +
                    '<td>'+payment.baac.accNo+' '+payment.baac.branch.bank.name+'</td>' +
                    '<td><span style="color:'+color+'">'+payment.status.name+'</span></td><td class="text-right">'+toMoney(payment.amount)+'</td></tr>';
            }
        }else{
            html += '<tr><td colspan="5">ไม่มีรายการแจ้งชำระเงิน</td></tr>';
        }
        html += '</table>';
        return html;
    }

    KhayCake.member.bankaccount = {};
    KhayCake.member.bankaccount.form = function(bankaccounts){
        var html = '<select class="form-control" name="bankacc_id">';
        for(var idx in bankaccounts){
            var bankacc = bankaccounts[idx];
            html += '<option value="'+bankacc.id+'">'+bankacc.accNo+' '+bankacc.branch.bank.name+'</option>';
        }
        html += '</select>';
        return html;
    };

    KhayCake.cart = function () {
        Cart.all(function (resp) {
            if (resp.message && resp.message.items && resp.message.items.length > 0) {
                $("#cart-table").html(Cart.form(resp.message));
                KhayCake.cart.bind();

                if ($(".cart-button").hasClass("inactive"))
                    $(".cart-button").removeClass("inactive");
            }
        });
    };
    KhayCake.cart.open = function () {
        $(".cart-button").addClass("open");
        KhayCake.loadingMask($("#cart-table"));
        Cart.all(function (resp) {
            var cart = resp.message;
            if(!cart)
                cart = {};
            $("#cart-table").html(Cart.form(cart));
            KhayCake.cart.bind();
        });
    };
    KhayCake.cart.close = function () {
        $(".cart-button").removeClass("open");
    };
    KhayCake.cart.add = function (id, fn) {

        var clone = $('<div id="clone"></div>');
        var clientRect = $("#box-img-" + id)[0].getBoundingClientRect();

        if ($(".cart-button").hasClass("inactive"))
            $(".cart-button").removeClass("inactive");


        var targetRect = $(".cart-button .cart-open")[0].getBoundingClientRect();

        clone.html($("#box-img-" + id).clone()).appendTo("body");

        clone.css({
            top: clientRect.top,
            left: clientRect.left,
            width: clientRect.width,
            height: clientRect.height,
            opacity: 1
        });

        clone.animate({
            top: targetRect.top - 80,
            left: 0,
            width: '53px',
            height: '53px'
        }, 500, function () {
            clone.animate({
                top: targetRect.top,
                height: '53px',
                opacity: 0
            }, 300, function () {
                clone.remove();
                if (typeof(fn) === "function")
                    fn();
            });

        });

    };
    KhayCake.cart.bind = function () {
        $("#cart-table .column-remove span").click(function () {
            var row = $(this).parent().parent();
            $(row).animate({
                height: 0
            }, 350, function () {
                $(row).remove();
                Cart.update($("#user-cart"), function (resp) {
                    if (resp.message) {
                        $("#cart-table").html(Cart.form(resp.message));
                        KhayCake.cart.bind();
                    }
                });
            });
            //

        });
    };

    KhayCake.checkout = function () {		
		var url = KhayCake.url.parse();
        var step = url.resources[2];       

        var html = KhayCake.checkout.header();
        $(section).html(html);

        $(".checkout-progressbar li").removeClass("active");
        for (var i = 1; i <= 5; i++)
            if (i <= step)
                $(".checkout-progressbar li:nth-child(" + i + ")").addClass("active");

        KhayCake.loadingMask($("#checkout-container"));

        Cart.all(function (cartResp) {
            Auth.get(function (authResp) {
                var items = null;
                var shipMethod = null;
                var shipAddress = null;
                var cart = cartResp.message;
                var user = authResp.message;

                if (cart){
                    items = cart.items;
                    shipMethod = cart.shipmentMethod;
                    shipAddress = cart.shipmentAddress;
                }

                var email = null;
                if (user)
                    email = user.email;


                if (step == 1) {
                    KhayCake.checkout.set(KhayCake.checkout.cart.form(cartResp.message));
                } else if (step == 2 && items && !email) {
                    KhayCake.checkout.set(KhayCake.checkout.login.form());
                } else if (step == 2 && items && email) {
                    window.location.hash = '#!/checkout/3';
                    return;
                } else if (step == 3 && items && email) {
                    Shipment.Address.all(function (shadResp) {
                        KhayCake.checkout.set(KhayCake.checkout.address.form(shadResp.message));
                    });

                } else if (step == 4 && items && email && shipAddress) {
                    Shipment.Method.all(function(shmeResp){
                        KhayCake.checkout.set(KhayCake.checkout.shipment.form(shmeResp.message, cartResp.message.shipmentAddress));
                    });
                } else if (step == 5 && items && email && shipAddress && shipMethod) {
                    KhayCake.checkout.set(KhayCake.checkout.order.form(cartResp.message));
                } else if (step == 6 && items && email && shipAddress && shipMethod) {
                    KhayCake.checkout.complete();
                }else {
                    window.location.hash = '#!/checkout/1';
                    return;
                }


            });

        });


    };
    KhayCake.checkout.set = function (html) {
        $("#checkout-container").html(html);
        KhayCake.loadedMask();
        KhayCake.checkout.bind();
    };
    KhayCake.checkout.bind = function () {

        $("#checkout-register").click(function () {
            KhayCake.checkout.set(KhayCake.checkout.register.form());
            return false;
        });

        $("#checkout-login").click(function () {
            KhayCake.checkout.set(KhayCake.checkout.login.form());
            return false;
        });

        $("#checkout-do-login").click(function () {
            KhayCake.checkout.login();
            return false;
        });

        $("#checkout-do-register").click(function () {
            KhayCake.checkout.register();
            return false;
        });

        $("#checkout-address").click(function () {
            KhayCake.checkout.address();

            return false;
        });

        $("#form-address").find("#tumbons").autocomplete({
            serviceUrl: HOST + "/tumbon",
            paramName: 'q',
            transformResult: function (response) {
                if (JSON.parse(response).message)
                    return {
                        suggestions: $.map(JSON.parse(response).message, function (dataItem) {
                            return {
                                value: dataItem.name + ", " + dataItem.amphur.name,
                                data: dataItem.id,
                                display: dataItem.name,
                                label: dataItem.amphur.name
                            };
                        })
                    };
                return {suggestions: []};
            },
            onSelect: function (suggestion) {
                var id = suggestion.data;
                Tumbon.find(id, function (resp) {
                    var tumbon = resp.message;
                    $("#tumb_id").val(id);
                    if (tumbon) {
                        $("#amphur").val(tumbon.amphur.name);
                        $("#province").val(tumbon.amphur.province.name);
                        $("#zipcode").val(tumbon.zipcode);
                    }
                });
            }
        });

        $("#form-address").find("#amphur").autocomplete({
            serviceUrl: HOST + "/amphur",
            paramName: 'q',
            transformResult: function (response) {
                if (JSON.parse(response).message)
                    return {
                        suggestions: $.map(JSON.parse(response).message, function (dataItem) {
                            return {value: dataItem.name, data: dataItem.id, label: dataItem.province.name};
                        })
                    };
                return {suggestions: []};
            },
            onSelect: function (suggestion) {
                var id = suggestion.data;
                Amphur.find(id, function (resp) {
                    var amphur = resp.message;
                    $("#amph_id").val(id);
                    if (amphur) {
                        $("#province").val(amphur.province.name);
                    }
                });
            }
        });

        $("#form-address").find("#province").autocomplete({
            serviceUrl: HOST + "/province",
            paramName: 'q',
            transformResult: function (response) {
                if (JSON.parse(response).message)
                    return {
                        suggestions: $.map(JSON.parse(response).message, function (dataItem) {
                            return {value: dataItem.name, data: dataItem.id};
                        })
                    };
                return {suggestions: []};
            },
            onSelect: function (suggestion) {
                var id = suggestion.data;
                Province.find(id, function (resp) {
                    var province = resp.message;
                    $("#prov_id").val(id);
                    if (province) {
                        $("#province").val(province.province.name);
                    }
                });
            }
        });

        $("#shipment-method").click(function(){
            Shipment.Method.set($("#form-shipment"), function(resp){
                if(resp.message) {
                    KhayCake.checkout.shipment.data = resp.message.shipmentMethod;
                    window.location.hash = "#!/checkout/5";
                }
            });
            return false;
        });

        $("#form-address").find("input[name=shad_id]").change(function(){

            if($(this).val() == -1)
                $("#new-address").attr('style','');
            else
                $("#new-address").attr('style','display:none');
        });

        $("#checkout-confirm").click(function(){
            KhayCake.loadingMask($("#checkout-container"));
            Cart.checkout(function(resp){
               if(resp.message)
                   KhayCake.checkout.complete(resp.message);
            });
            return false;
        });


    };
    KhayCake.checkout.header = function () {
        return '<section class="checkout"><ul class="checkout-progressbar">' +
            '<li class="active">รายการสินค้า</li>' +
            '<li class="active">ยืนยันตัวตน</li>' +
            '<li>ที่อยู่ในการจัดส่ง</li>' +
            '<li>วิธีการจัดส่ง</li><li>ยืนยันการสั่งซื้อ</li>' +
            '</ul>' +
            '<div id="checkout-container"></div>' +
            '</section>';
    };
    KhayCake.checkout.login = function () {
        var form = $("#form-login");
        Auth.auth(form, function (resp) {
            if (resp.message && resp.message.email) {
                window.location.hash = '#!/checkout/3';
            }
        });
    };
    KhayCake.checkout.login.form = function () {
        return '<form id="form-login">' +
            '<div class="form-group">' +
            '<label>อีเมล์ :</label>' +
            '<input class="form-control" id="email" name="email" placholder="ninecake@khaycake.com" autocomplete="off" value="test@test.test"/>' +
            '</div>' +
            '<div class="form-group">' +
            '<label>รหัสผ่าน :</label>' +
            '<input class="form-control" id="pwd" name="pwd" type="password" value="test"/>' +
            '</div>' +
            '<div class="form-group">' +
            '<button id="checkout-do-login" class="btn btn-primary">Login</button> <a id="checkout-register" href="#!/checkout">ฉันยังไม่มีรหัสผ่าน</a>' +
            '</div>' +
            '</form>';
    };
    KhayCake.checkout.register = function () {
        var form = $("#form-register");
        Auth.register(form, function (resp) {
            if (resp.message && resp.message.email) {
                window.location.hash = '#!/checkout/3';
            }
        });
    };
    KhayCake.checkout.register.form = function () {
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

    KhayCake.checkout.address = function () {
        var form = $("#form-address");
        Shipment.Address.add(form, function (resp) {
            if (resp.message) {
                window.location.hash = '#!/checkout/4';
            }
        });
    };
    KhayCake.checkout.address.form = function (addresses) {

        var html = '<form id="form-address"><h3 class="title">ที่อยู่ในของคุณ</h3>';
        html += '<table>';
        for (var idx in addresses) {
            var address = addresses[idx];
            html += '<tr><td><input type="radio" name="shad_id" style="float:left" value="' + address.id + '"/></td><td>คุณ' + address.firstName + ' ' + address.lastName + ' ' +
                address.address +' '+address.tumbon.name + ', ' + address.tumbon.amphur.name + ' ' +
                address.tumbon.amphur.province.name + ' ' + address.tumbon.zipcode + '</td></tr>';
        }

        html += '<tr><td><input type="radio" name="shad_id" id="new-address-radio" value="-1"/></td><td> เพิ่มที่อยู่ใหม่</td></tr>' +
            '<tr><td></td>' +
            '<td><div id="new-address" style="display:none"><div class="col-sm-6">' +
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
            '<textarea name="address" id="addr" cols="30" rows="8" class="form-control" placeholder="123 ซอย 13 หมู่บ้านขายเค้ก ถนนเค้กนุ่ม">' +
            '</textarea>' +
            '</div>' +
            '</div>' +
            '<div class="col-md-6">' +
            '<div class="form-group">' +
            '<label>ตำบล/แขวง</label><input list="tumbon-list" type="text" class="form-control" name="tumbons" id="tumbons"/>' +
            '<input type="hidden" name="tumb_id" id="tumb_id" value=""/>' +
            '</div>' +
            '</div>' +
            '<div class="col-md-6">' +
            '<div class="form-group">' +
            '<label>อำเภอ/เขต</label><input list="amphur-list" type="text" class="form-control" name="amphur" id="amphur"/>' +
            '<input type="hidden" name="amph_id" id="amph_id" value=""/>' +
            '</div>' +
            '</div>' +
            '<div class="col-md-6">' +
            '<div class="form-group">' +
            '<label>จังหวัด</label><input type="text" list="province-list" class="form-control" name="province" id="province"/>' +
            '<input type="hidden" name="prov_id" id="prov_id" value=""/>' +
            '</div>' +
            '</div>' +
            '<div class="col-md-6">' +
            '<div class="form-group">' +
            '<label>รหัสไปรษณีย์</label><input type="text" list="zipcode-list" class="form-control" name="zipcode" id="zipcode"/>' +
            '<datalist id="zipcode-list"></datalist>' +
            '</div>' +
            '</div></td></tr></table>' +
            '<div class="text-right"><button id="checkout-address" class="btn btn-primary">ถัดไป</button></div>' +
            '</form>';

        return html;
    }
    KhayCake.checkout.shipment = function(){};
    KhayCake.checkout.shipment.form = function(shipment,address){
        var html = '<form id="form-shipment"><h3>วิธีการจัดส่ง</h3><table class="table"><tr><th>วิธีจัดส่ง</th><th>ค่าขนส่ง (บาท)</th></tr>';
        for(var idx in shipment){
            var method = shipment[idx];
            if(method.id <= 4 && address.tumbon.amphur.province.id == 1 || method.id <= 3 && address.tumbon.amphur.province.id > 1 )
            html += '<tr><td><input type="radio" name="shme_id" value="'+method.id+'"/> '+method.name+'</td><td>'+method.price+'</td></tr>';
        }
        html += '</table><div class="text-right"><button id="shipment-method" class="btn btn-primary">ถัดไป</button></div></form>';
        return html;
    };
    KhayCake.checkout.cart = {};
    KhayCake.checkout.cart.form = function (cart) {

        var html = '<form id="form-cart"><table class="table"><tr><th>ชื่อ</th><th>จำนวน</th><th class="text-right">ราคา (บาท)</th></tr>';
        var hasNext = false;
        if (cart && cart.items && cart.items.length > 0) {
            hasNext = true;
            var items = cart.items;
            var total = 0.0;
            for (var idx in items) {
                var item = items[idx];
                html += '<tr><td>' + item.product.name + '</td>' +
                    '<td>' + item.qty + ' ' + item.product.unit.name + '</td>' +
                    '<td class="text-right">' + toMoney(item.total) + '</td></tr>';
                total += item.total;
            }
            if(cart.shipmentMethod) {
                html += '<tr><td>ค่าจัดส่ง (' + cart.shipmentMethod.name + ')</td>' +
                    '<td></td>' +
                    '<td class="text-right">' + toMoney(cart.shipmentMethod.price) + '</td></tr>';
                total += cart.shipmentMethod.price;
            }
            html += '<tr><td colspan="2" class="text-right">รวม</td><td class="text-right" id="order-total">' + toMoney(total) + '</td></tr>';

        } else {
            html += '<tr><td colspan="3">ไม่มีสินค้าในตะกร้า</td></tr>';
        }
        html += "</table>";
        if (hasNext)
            html += '<div class="text-right"><a class="btn btn-primary" id="checkout-next" href="#!/checkout/2">ถัดไป</a></div>';
        html += '</form>';
        return html;


    };
    KhayCake.checkout.order = function () {
    };
    KhayCake.checkout.order.form = function (cart) {
        var address = cart.shipmentAddress;

        var html = '';
        if (address) {
            html += '<form class="order" id="form-order">' +
                '<h3>ที่อยู่ในการจัดส่ง</h3>' +
                '<input type="hidden" name="addr_id" value="'+address.id+'"/>'+
                '<p>คุณ' + address.firstName + ' ' + address.lastName + '<br/>' +
                address.address + '<br/>' +
                address.tumbon.name + ', ' + address.tumbon.amphur.name + '<br/>' +
                address.tumbon.amphur.province.name + ' ' + address.tumbon.zipcode + '</p>';

        }

        html += '<h3>รายการสินค้า</h3>';

        html += '<form id="form-cart"><table class="table"><tr><th>ชื่อ</th><th>จำนวน</th><th class="text-right">ราคา (บาท)</th></tr>';

        if (cart && cart.items && cart.items.length > 0) {
            var items = cart.items;
            var total = 0.0;
            for (var idx in items) {
                var item = items[idx];
                html += '<tr><td>' + item.product.name + '</td>' +
                    '<td>' + item.qty + ' ' + item.product.unit.name + '</td>' +
                    '<td class="text-right">' + toMoney(item.total) + '</td></tr>';
                total += item.total;
            }
            if(cart.shipmentMethod) {
                html += '<tr><td>ค่าจัดส่ง (' + cart.shipmentMethod.name + ')</td>' +
                    '<td></td>' +
                    '<td class="text-right">' + toMoney(cart.shipmentMethod.price) + '</td></tr>';
                total += cart.shipmentMethod.price;
            }
            html += '<tr><td colspan="2" class="text-right">รวม</td><td class="text-right" id="order-total">' + toMoney(total) + '</td></tr>';

        } else {
            html += '<tr><td colspan="3">ไม่มีสินค้าในตะกร้า</td></tr>';
        }
        html += "</table>";
        html += '<div class="text-right"><button class="btn btn-primary" id="checkout-confirm">ยืนยันการสั่งซื้อ</button></div>';
        html += '</form>';

        html += '</form>';
        return html;

    };

    KhayCake.checkout.complete = function(order){
      var html = '<div class="checkout-complete"><h2>เย่! การสั่งซื้อเสร็จสมบูรณ์</h2><div class="checkout-complete-img"><img src="images/spongebob.jpg"></div>' +
          '<h3>กรุณาชำระเงินตามรายละเอียดด้านล่าง</h3><p>ท่านสามารถชำระเงินได้ โดยการโอนเงินเข้าบัญชีของทางร้านตามเลขบัญชีด้านล่าง หลังจากนั้น ท่านสามารถแจ้งชำระเงินได้ ในหน้า <a href="#!/member/order/'+order.id+'">สมาชิก</a> ครับ</p></div>';
        html += '<div class="flex flex-center">';
        BankAccount.all(function(resp){

            if(resp.message){
                for(var idx in resp.message){
                    var bankacc = resp.message[idx];
                    html += KhayCake.bankaccount.box(bankacc);
                }
            }
            html += '</div>';
            KhayCake.checkout.set(html);
        });



    };
    KhayCake.bankaccount = {};
    KhayCake.bankaccount.box = function(bankacc){
        return '<div class="bank-acc">'+
            '<div class="bank-acc-img"><img src="images/bank/'+bankacc.branch.bank.slug+'.png"></div>'+
            '<div class="bank-acc-text"><div class="title">สาขา</div>'+bankacc.branch.name+'</div>'+
            '<div class="bank-acc-text"><div class="title">ชื่อบัญชี</div>'+bankacc.accName+'</div>'+
            '<div class="bank-acc-text"><div class="title">เลขบัญชี</div>'+bankacc.accNo+'</div>'+
            '<div class="bank-acc-text"><div class="title">ประเภท</div>'+bankacc.type.name+'</div>'+
            '</div>';
    };

    KhayCake.onHashChanged = function () {
        var url = KhayCake.url.parse();
        var method = url.resources[1];
		
		 KhayCake.cart.close();

        switch (method) {
            case 'cake':               
                KhayCake.cake();
                break;
            case 'checkout':                
                KhayCake.checkout();
                break;
            case 'member':
                KhayCake.member();
                break;
            default:
                KhayCake.home();
                break;
        }
    };

    KhayCake.loadingMask = function (el) {
        $(el).html("<div class='col-sm-12' style='text-align: center;overflow: hidden'><span class='loader'><span class='loader-inner'></span></span></div>");
    };
    KhayCake.loadedMask = function (el) {
        $(el).html("");
    };

    $(window).bind("hashchange", function () {
        KhayCake.onHashChanged();
    });

    if (window.location.hash) {
        KhayCake.onHashChanged();
    }

    $(".cart-button .cart-open").click(function () {
        KhayCake.cart.open();
    });

    $(".order-item .cart-close").click(function () {
        KhayCake.cart.close();
    });

    KhayCake.cart();
});