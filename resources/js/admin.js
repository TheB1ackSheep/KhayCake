$(document).ready(function() {


    var loadingMask = $('.app-loading-mask');
    var host = "http://jsp.falook.me/khaycake";


    var actionBar = $(".app-content .action-bar");
    var content = $(".app-content .content");
    var message = $(".app-content .message");

    var error = {}, msg = null;
    var sessionID;


    //Overide Ajax.onError;
    Ajax.onError = function(resp) {
        var errorBox = '' /*'<div class="alert alert-danger" role="alert">'*/;
        if (!resp.responseText && resp.error) {

            if (resp.error.length > 0) {
                for (var idx in resp.error.message)
                    error += resp.error.message[idx] + '<br/>';
            } else {
                errorBox += resp.message;
            }
            //errorBox += '</div>';

        } else {
            errorBox += 'request return with status ' + resp.status + ' : ' + resp.statusText;
        }
        alert(errorBox);
        Admin.loadedMask();
    };


    var Admin = Admin || {
            xhr: [],
            isAjax: false,

            loadingMask: function() {
                loadingMask.html("<span class='loader'><span class='loader-inner'></span></span>");
                loadingMask.addClass("show");
            },
            loadedMask: function() {
                loadingMask.html("");
                loadingMask.removeClass("show");
            },
            toMoney: function(double) {
                return double.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
            }



        };

    Admin.onHashChanged = function() {
        var hash = window.location.hash.substr(2);
        var section = hash.split('/')[1];
        var method = hash.split('/')[2];
        var param = hash.split('/')[3];

        switch (section) {
            case "cakes":
                Admin.tabCake();
                break;
            case "customers":
                Admin.tabCustomer();
                break;
            case "bankaccount":
                Admin.tabBank();
                break;
            case "order":
                Admin.tabOrder();
                break;
            case "payment":
                Admin.tabPayment();
                break;
            case "shipment":
                Admin.tabShipment();
                break;
            default:
                section = "dashboard";
                Admin.tabDashboard();
                break;
        }

        $(".app-nav a").removeClass("active");
        $(".app-nav a#" + section).addClass("active");

    };

    Admin.tabDashboard = function(){
        Admin.loadingMask();

        var actionBarContent = '<form id="form-search-dashboard"><div class="input-group" style="max-width: 400px"><input type="text" name="q" id="keyword" class="form-control" placeholder="ค้นหาโดยเบอร์โทรศัพท์, อีเมล์, ชื่อ-นามสกุล" required=""><span class="input-group-btn"><button class="btn btn-default" id="search-dashboard" type="submit">ค้นหา</button></span></div></form>';
        var hash = window.location.hash.substr(2);
        var method = hash.split('/')[2];
        var param = hash.split('/')[3];


        Order.shipping(function(orderResp){
            Payment.approving(function(paymentResp){
                if(!orderResp.message)
                    orderResp.message = {};
                if(!paymentResp.message)
                    paymentResp.message = {};

                if(method == "q")
                    actionBarContent = '<div class="el"><a class="btn btn-default" href="#!/dashboard">กลับ</a></div><form id="form-search-dashboard"><div class="input-group" style="max-width: 400px"><input type="text" name="q" id="keyword" class="form-control" placeholder="ค้นหาโดยเบอร์โทรศัพท์, อีเมล์, ชื่อ-นามสกุล" required=""><span class="input-group-btn"><button class="btn btn-default" id="search-dashboard" type="submit">ค้นหา</button></span></div></form>';

                $(actionBar).html(actionBarContent);

                if(method == "q")
                    $("#form-search-dashboard").find("#keyword").val(param);

                var html = Admin.tabDashboard.shippingOrder.table(orderResp.message);
                html += Admin.tabDashboard.approvingPayment.table(paymentResp.message);
                $(content).html(html);
                Admin.tabDashboard.bind();
                Admin.loadedMask();
            },method=="q"?param:null);
        },method=="q"?param:null);
    };
    Admin.tabDashboard.bind = function(){
        $("#form-search-dashboard").submit(function(){
            window.location.hash = "#!/dashboard/q/"+$(this).find("#keyword").val();
            return false;
        })
    };
    Admin.tabDashboard.shippingOrder = function(){};
    Admin.tabDashboard.shippingOrder.table = function(shippingOrder){
        var html = '<h2>รายการสั่งซื้อที่ต้องจัดส่ง</h2><table class="p6n-table">';
        html += '<tr>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">เลขที่สั่งซื้อ</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">วันที่สั่ง</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">จำนวน (ชิ้น)</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name text-right">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">ราคาทั้งหมด (บาท)</a>' +
            '</th>' +
            '</tr>';
        if(shippingOrder.length > 0){
            for(var idx in shippingOrder) {
                var order = shippingOrder[idx];
                html += '<tr><td><a href="#!/shipment/'+order.id+'">#'+order.id+'</a></td><td>'+order.date+'</td><td>'+order.totalQty+'</td><td class="text-right">'+toMoney(order.totalPrice)+'</td></tr>';
            }
        }else{
            html += '<tr><td colspan="4">ไม่มีรายการสั่งซื้อที่ต้องจัดส่ง</td> </tr>';
        }
        html += '</table>';
        return html;
    };
    Admin.tabDashboard.approvingPayment = function(){};
    Admin.tabDashboard.approvingPayment.table = function(approvingPayment){
        var html = '<h2>รายการแจ้งชำระเงินที่รอการยืนยัน</h2><table class="p6n-table">';
        html += '<tr>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">เลขที่สั่งซื้อ</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">วันที่แจ้ง</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">โอนเข้าบัญชี</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name text-right">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">ยอดที่แจ้ง (บาท)</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name text-right">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">ยอดที่ต้องชำระ (บาท)</a>' +
            '</th>' +
            '</tr>';
        if(approvingPayment.length > 0){
            for(var idx in approvingPayment) {
                var payment = approvingPayment[idx];
                html += '<tr><td><a href="#!/payment/'+payment.id+'">#'+payment.order.id+'</a></td><td><span style="font-size:12px">'+payment.dateTime+'</div></td>' +
                    '<td>'+payment.baac.accNo+' '+payment.baac.branch.bank.name+'</td>' +
                    '<td class="text-right">'+toMoney(payment.amount)+'</td>' +
                    '<td class="text-right">'+toMoney(payment.order.totalPrice)+'</td></tr>';
            }
        }else{
            html += '<tr><td colspan="5">ไม่มีรายการแจ้งชำระที่ต้องยืนยัน</td> </tr>';
        }
        html += '</table>';
        return html;
    };

    Admin.tabOrder = function(){
        Admin.loadingMask();
        var actionBarContent = '<form id="form-search-order"><div class="input-group" style="max-width: 400px"><input type="text" name="q" id="keyword" class="form-control" placeholder="ค้นหาโดยเบอร์โทรศัพท์, อีเมล์, ชื่อ-นามสกุล" required=""><span class="input-group-btn"><button class="btn btn-default" id="search-order" type="submit">ค้นหา</button></span></div></form>';
        var hash = window.location.hash.substr(2);
        var method = hash.split('/')[2];
        var param = hash.split('/')[3];

        if(isInteger(method)){
            Order.find(method, function(orderResp){
                Order.items(method, function(itemResp){
                    if(!orderResp.message)
                        orderResp.message = {};
                    if(!itemResp.message)
                        itemResp.message = {};
                    $(actionBar).html('');
                    var html = Admin.tabOrder.form(orderResp.message,itemResp.message);
                    $(content).html(html);
                    Admin.loadedMask();
                });
            });
        }else{
            Order.all(function(resp){
                if(!resp.message)
                    resp.message = {};
                if(method == "q")
                    actionBarContent = '<div class="el"><a class="btn btn-default" href="#!/order">กลับ</a></div><form id="form-search-order"><div class="input-group" style="max-width: 400px"><input type="text" name="q" id="keyword" class="form-control" placeholder="ค้นหาโดยเบอร์โทรศัพท์, อีเมล์, ชื่อ-นามสกุล" required=""><span class="input-group-btn"><button class="btn btn-default" id="search-order" type="submit">ค้นหา</button></span></div></form>';

                $(actionBar).html(actionBarContent);

                if(method == "q")
                    $("#form-search-order").find("#keyword").val(param);

                var html = Admin.tabOrder.table(resp.message);
                $(content).html(html);
                Admin.tabOrder.bind();
                Admin.loadedMask();

            },method=="q"?param:null);
        }




    };
    Admin.tabOrder.bind = function(){
        $("#form-search-order").submit(function(){
            window.location.hash = "#!/order/q/"+$(this).find("#keyword").val();
            return false;
        });

    };
    Admin.tabOrder.table = function(orders){
        var html = '<table class="table">';
        html += '<tr><th>เลขที่</th><th>วันที่สั่งซื้อ</th><th>จำนวน (ชิ้น)</th><th>สถานะ</th><th class="text-right">ราคาทั้งหมด (บาท)</th></tr>';
        if(orders.length > 0){
            for(var idx in orders) {
                var order = orders[idx];
                var color = '';
                if(order.status.id == 1 || order.status.id == 2 || order.status.id == 5)
                    color = "rgb(226, 163, 0)";
                else if( order.status.id == 3)
                    color = "green";
                else
                    color = "red";
                html += '<tr><td><a href="#!/order/'+order.id+'">#'+order.id+'</a></td><td>'+order.date+'</td>' +
                    '<td>'+order.totalQty+'</td><td><span style="color:'+color+'">'+order.status.name+'</span></td><td  class="text-right">'+toMoney(order.totalPrice)+'</td></tr>';
            }

        }else{
            html += '<tr><td colspan="5">ไม่มีรายการสั่งซื้อ</td></tr>';
        }
        html += '</table>';
        return html;
    };
    Admin.tabOrder.form = function(order,orderitems){
        var address = order.shipAddress;

        var html = '';
        if (address) {
            html += '<form class="order" id="form-order" style="width:500px">' +
                '<h2>รายการสั่งซื้อ #'+order.id+'</h2><h3>ที่อยู่ในการจัดส่ง</h3>' +
                '<p>คุณ' + address.firstName + ' ' + address.lastName + '<br/>' +
                address.address + '<br/>' +
                address.tumbon.name + ', ' + address.tumbon.amphur.name + '<br/>' +
                address.tumbon.amphur.province.name + ' ' + address.tumbon.zipcode + '</p>';

        }

        var color = '';
        if(order.status.id == 1 || order.status.id == 2 || order.status.id == 5)
            color = "rgb(226, 163, 0)";
        else if( order.status.id == 3)
            color = "green";
        else
            color = "red";

        html += '<h3>สถานะ</h3>';
        html += '<p><span style="color:'+color+'">'+order.status.name+'</span></p>';

        html += '<h3>รายการสินค้า</h3>';

        html += '<table class="table"><tr><th>ชื่อ</th><th>จำนวน</th><th class="text-right">ราคา (บาท)</th></tr>';

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
        /*if(order.status.id == 1 || order.status.id == 5)
            html += '<div class="text-right"><a class="btn btn-primary" href="#!/member/order/'+order.id+'/confirm">แจ้งชำระเงิน</a></div>';*/
        html += '</form>';

        return html;
    };

    Admin.tabPayment = function(){
        Admin.loadingMask();
        var actionBarContent = '<form id="form-search-payment"><div class="input-group" style="max-width: 400px"><input type="text" name="q" id="keyword" class="form-control" placeholder="ค้นหาโดยเบอร์โทรศัพท์, อีเมล์, ชื่อ-นามสกุล" required=""><span class="input-group-btn"><button class="btn btn-default" id="search-payment" type="submit">ค้นหา</button></span></div></form>';
        var hash = window.location.hash.substr(2);
        var method = hash.split('/')[2];
        var param = hash.split('/')[3];

        if(isInteger(method)){
            $(actionBar).html('');
            if(!param) {
                Payment.find(method, function (resp) {
                    if (!resp.message)
                        resp.message = {};

                    Admin.tabPayment.form(resp.message);
                });
            }else{
                if(param=="accept"){
                    Payment.accept(method, function(resp){
                        if(resp.message) {
                            alert("เปลี่ยนสถานะการแจ้งชำระเรียบร้อย");
                            window.location.hash = "#!/payment";
                        }
                    });
                }else if(param=="deny"){
                    Payment.deny(method, function(resp){
                        if(resp.message) {
                            alert("เปลี่ยนสถานะการแจ้งชำระเรียบร้อย");
                            window.location.hash = "#!/payment";
                        }
                    });
                }else{
                    window.location.hash = "#!/payment/"+method;
                }
            }

        }else{
            Payment.all(function(resp){
                if(!resp.message)
                    resp.message = {};

                if(method == "q")
                    actionBarContent = '<div class="el"><a class="btn btn-default" href="#!/payment">กลับ</a></div><form id="form-search-payment"><div class="input-group" style="max-width: 400px"><input type="text" name="q" id="keyword" class="form-control" placeholder="ค้นหาโดยเบอร์โทรศัพท์, อีเมล์, ชื่อ-นามสกุล" required=""><span class="input-group-btn"><button class="btn btn-default" id="search-payment" type="submit">ค้นหา</button></span></div></form>';

                $(actionBar).html(actionBarContent);

                if(method == "q")
                    $("#form-search-payment").find("#keyword").val(param);

                var html = Admin.tabPayment.table(resp.message);
                $(content).html(html);
                Admin.tabPayment.bind();
                Admin.loadedMask();

            },method=="q"?param:null);
        }



    };
    Admin.tabPayment.bind = function(){
        $("#form-search-payment").submit(function(){
            window.location.hash = "#!/payment/q/"+$(this).find("#keyword").val();
            return false;
        });
    };
    Admin.tabPayment.table = function(payments){
        var html = '<table class="table">';
        html += '<tr><th>เลขที่สั่งซื้อ</th><th>วันที่แจ้ง</th><th>โอนเข้าบัญชี</th><th>สถานะ</th><th class="text-right">ยอดที่แจ้ง (บาท)</th></tr>';
        if(payments.length > 0){
            for(var idx in payments) {
                var payment = payments[idx];
                var color = '';
                if(payment.status.id == 1)
                    color = 'rgb(226, 163, 0)';
                else if(payment.status.id == 2)
                    color = 'green';
                else
                    color = 'red';
                html += '<tr><td><a href="#!/payment/'+payment.id+'">#'+payment.order.id+'</a></td><td><span style="font-size:12px">'+payment.dateTime+'</span></td>' +
                    '<td>'+payment.baac.accNo+' '+payment.baac.branch.bank.name+'</td>' +
                    '<td><span style="color:'+color+'">'+payment.status.name+'</span></td><td class="text-right">'+toMoney(payment.amount)+'</td></tr>';
            }
        }else{
            html += '<tr><td colspan="5">ไม่มีรายการแจ้งชำระเงิน</td></tr>';
        }
        html += '</table>';
        return html;
    };
    Admin.tabPayment.form = function(payment){
        var order = payment.order;
        var html = '<div class="payment"><div class="col-sm-6"><h2>รายการแจ้งชำระเลขที่ '+payment.id+"</h2>";
        html += '<h3>วันที่แจ้งชำระ</h3>';
        html += '<p>'+payment.dateTime+'</p>';
        html += '<h3>ช่องทางในการชำระ</h3>';
        html += '<p>เลขบัญชี : '+payment.baac.accNo+'<br/>ชื่อบัญชี : '+payment.baac.accName+'<br/>'+payment.baac.branch.bank.name+'' +
            ' สาขา'+payment.baac.branch.name+'</p>';
        html += '<h2 class="title">ยอดที่แจ้ง</h2>';
        html += '<p>'+toMoney(payment.amount)+' บาท</p>';

        var color = '';
        if(payment.status.id == 1)
            color = 'rgb(226, 163, 0)';
        else if(payment.status.id == 2)
            color = 'green';
        else
            color = 'red';

        html += '<h2 class="title">สถานะ</h2>';
        html += '<p><span style="color:'+color+'">'+payment.status.name+'</span></p>';
        if(payment.status.id == 1)
            html += '<div class="btn-group"><a href="#!/payment/'+payment.id+'/accept" class="btn btn-success">ยืนยันรายการนี้</a><a href="#!/payment/'+payment.id+'/deny" class="btn btn-danger">ปฎิเสธรายการนี้</a></div>';

        html += '</div>';

        var address = order.shipAddress;

        html += '<div class="col-sm-6"><form class="order" id="form-order" style="width:500px">' +
            '<h2>รายการสั่งซื้อ #'+order.id+'</h2><h3>ที่อยู่ในการจัดส่ง</h3>' +
            '<p>คุณ' + address.firstName + ' ' + address.lastName + '<br/>' +
            address.address + '<br/>' +
            address.tumbon.name + ', ' + address.tumbon.amphur.name + '<br/>' +
            address.tumbon.amphur.province.name + ' ' + address.tumbon.zipcode + '</p>';

        html += '<h3>รายการสินค้า</h3>';

        html += '<table class="table"><tr><th>ชื่อ</th><th>จำนวน</th><th class="text-right">ราคา (บาท)</th></tr>';

        Order.items(order.id, function(resp){
            if (resp.message && resp.message.length > 0) {
                var total = 0.0;
                for (var idx in resp.message) {
                    var item = resp.message[idx];
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
                html += '<tr><td colspan="3">ไม่มีสินค้า</td></tr>';
            }
            html += "</table>";
            html += "</form></div></div>";
            $(content).html(html);
            Admin.tabPayment.bind();
            Admin.loadedMask();
        });



    };

    Admin.tabShipment = function(){
        Admin.loadingMask();
        var actionBarContent = '<form id="form-search-shipment"><div class="input-group" style="max-width: 400px"><input type="text" name="q" id="keyword" class="form-control" placeholder="ค้นหาโดยเบอร์โทรศัพท์, อีเมล์, ชื่อ-นามสกุล" required=""><span class="input-group-btn"><button class="btn btn-default" id="search-shipment" type="submit">ค้นหา</button></span></div></form>';
        var hash = window.location.hash.substr(2);
        var method = hash.split('/')[2];
        var param = hash.split('/')[3];

        if(isInteger(method)){
            $(actionBar).html('');
            if(!param) {
                Order.find(method, function (orderResp) {
                    Order.items(method,function(itemResp){
                        if (!orderResp.message)
                            orderResp.message = {};
                        if (!itemResp.message)
                            itemResp.message = {};

                        $(actionBar).html('');
                        var html = Admin.tabShipment.form(orderResp.message,itemResp.message);
                        $(content).html(html);
                        Admin.tabShipment.bind();
                        Admin.loadedMask();
                    });

                });
            }

        }else{
            Order.shipping(function(resp){
                if(!resp.message)
                    resp.message = {};

                if(method == "q")
                    actionBarContent = '<div class="el"><a class="btn btn-default" href="#!/shipment">กลับ</a></div><form id="form-search-shipment"><div class="input-group" style="max-width: 400px"><input type="text" name="q" id="keyword" class="form-control" placeholder="ค้นหาโดยเบอร์โทรศัพท์, อีเมล์, ชื่อ-นามสกุล" required=""><span class="input-group-btn"><button class="btn btn-default" id="search-shipment" type="submit">ค้นหา</button></span></div></form>';

                $(actionBar).html(actionBarContent);

                if(method == "q")
                    $("#form-search-shipment").find("#keyword").val(param);

                var html = Admin.tabShipment.table(resp.message);
                $(content).html(html);
                Admin.tabShipment.bind();
                Admin.loadedMask();

            },method=="q"?param:null);
        }

    };
    Admin.tabShipment.bind = function(){
        $("#form-search-shipment").submit(function(){
            window.location.hash = "#!/shipment/q/"+$(this).find("#keyword").val();
            return false;
        });

        $("#form-shipped").submit(function(){
            Admin.loadingMask();
            var order_id = $(this).find("#order_id").val();
            var track_id = $(this).find("#track_id").val();
            Order.shipped(order_id,track_id,function(resp){
                if(resp.message){
                    alert("เปลี่ยนสถานะเป็นจัดส่งเรียบร้อย");
                    window.location.hash = "#!/shipment";
                }
            });
            return false;
        });
    };
    Admin.tabShipment.table = function(shippingOrder){
        var html = '<table class="p6n-table">';
        html += '<tr>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">เลขที่สั่งซื้อ</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">วันที่สั่ง</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">จำนวน (ชิ้น)</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name text-right">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">ราคาทั้งหมด (บาท)</a>' +
            '</th>' +
            '</tr>';
        if(shippingOrder.length > 0){
            for(var idx in shippingOrder) {
                var order = shippingOrder[idx];
                html += '<tr><td><a href="#!/shipment/'+order.id+'">#'+order.id+'</a></td><td>'+order.date+'</td><td>'+order.totalQty+'</td><td class="text-right">'+toMoney(order.totalPrice)+'</td></tr>';
            }
        }else{
            html += '<tr><td colspan="4">ไม่มีรายการสั่งซื้อที่ต้องจัดส่ง</td> </tr>';
        }
        html += '</table>';
        return html;
    };
    Admin.tabShipment.form = function(order,orderitems){
        var address = order.shipAddress;

        var html = '<div class="shipment"><div class="col-sm-6">';
        if (address) {
            html += '<form class="order" id="form-order" style="width:500px">' +
                '<h2>รายการสั่งซื้อ #'+order.id+'</h2><h3>ที่อยู่ในการจัดส่ง</h3>' +
                '<p>คุณ' + address.firstName + ' ' + address.lastName + '<br/>' +
                address.address + '<br/>' +
                address.tumbon.name + ', ' + address.tumbon.amphur.name + '<br/>' +
                address.tumbon.amphur.province.name + ' ' + address.tumbon.zipcode + '</p></form>';

        }

        var color = '';
        if(order.status.id == 1 || order.status.id == 2 || order.status.id == 5)
            color = "rgb(226, 163, 0)";
        else if( order.status.id == 3)
            color = "green";
        else
            color = "red";

        html += '<h3>สถานะ</h3>';
        html += '<p><span style="color:'+color+'">'+order.status.name+'</span></p>';

        if(order.status.id == 2) {
            html += '<form id="form-shipped">' +
                    '<input type="hidden" name="order_id" id="order_id" value="'+order.id+'"/>'+
                '<div class="input-group">' +
                '<input type="text" name="track_id" id="track_id" class="form-control" placeholder="เลขติดตามพัสดุ">' +
                '<span class="input-group-btn">' +
                '<button class="btn btn-success" id="do-shipped" type="submit">เปลี่ยนสถานะเป็นจัดส่งสินค้าเรียบร้อย</button></span></div></form>';
        }

        html += '</div>';

        html += '<div class="col-sm-6"><h3>รายการสินค้า</h3>';

        html += '<table class="table"><tr><th>ชื่อ</th><th>จำนวน</th><th class="text-right">ราคา (บาท)</th></tr>';

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
        /*if(order.status.id == 1 || order.status.id == 5)
         html += '<div class="text-right"><a class="btn btn-primary" href="#!/member/order/'+order.id+'/confirm">แจ้งชำระเงิน</a></div>';*/
        html += '</div></div>';

        return html;
    };

    Admin.tabCake = function() {
        var actionBarContent = '<div class="el"><button class="btn btn-default" id="refresh-cake">รีเฟรช</button></div><div class="el btn-group"><a href="#!/cakes/create" class="btn btn-primary" id="add-cake">เพิ่มเค้ก</a><button class="btn btn-danger disabled" id="delete-cake">ลบ</button></div><div class="el"><form id="form-search-cake"><div class="input-group" style="max-width: 400px"><input type="text" name="q" id="keyword" class="form-control" placeholder="ค้นหาตามชื่อเค้ก, คำอธิบาย" required><span class="input-group-btn"><button class="btn btn-default" id="search-cake" type="submit">ค้นหา</button></span></div></form></div>';

        var hash = window.location.hash.substr(2);
        var resources = hash.split('/');
        var method, param;
        if (resources) {
            if (resources.length >= 3)
                method = resources[2];
            if (resources.length >= 4)
                param = resources[3];
        }


        Admin.loadingMask();
        if (!(method)) { //list all cake

            Product.all(function(resp) {
                $(actionBar).html(actionBarContent);
                Admin.tabCake.table(resp.message);
                Admin.tabCake.bind();
                Admin.loadedMask();
            });
        } else if (method) {
            if (isInteger(method)) { //get cake by id
                Product.find(method,function(resp) {
                    var product = resp.message;
                    actionBarContent = '<div class="el"><a href="#!/cakes" class="btn btn-default">กลับ</a></div><div class="el"><button class="btn btn-primary" id="update-cake">บันทึก</button></div>';
                    $(actionBar).html(actionBarContent);
                    $(content).html(Product.form("แก้ไขเค้กใหม่",product));
                    Unit.box(function(unitBox){
                        $("#unit-container").html(unitBox);
                        $("#unit-container select").val(product.unit.id);
                        Category.box(function(catBox){
                            $("#category-container").html(catBox);
                            $("#category-container select").val(product.category.id);
                            Product.pictures(method,function(resp){
                                var html = '';
                                for(var idx in resp.message){
                                    var picture = resp.message[idx];
                                    html += (Product.form.picture(picture));
                                }
                                $("#form-cake #pic-list").html(html);


                                Product.sales(method,function(resp){
                                    var html = '';
                                    for(var idx in resp.message){
                                        var sale = resp.message[idx];
                                        html += Product.form.sale(sale);
                                    }
                                    $("#form-cake #product_sale").html(html);
                                    Admin.tabCake.bind(method);
                                })

                            });
                        });
                    });


                });

            } else {
                if (method === "create") {
                    actionBarContent = '<div class="el"><a href="#!/cakes" class="btn btn-default">กลับ</a></div><div class="el"><button class="btn btn-primary" id="save-cake">บันทึก</button></div>';
                    $(actionBar).html(actionBarContent);
                    $(content).html(Product.form("เพิ่มเค้กใหม่"));
                    Unit.box(function(unitBox){
                        $("#unit-container").html(unitBox);
                        Category.box(function(catBox){
                            $("#category-container").html(catBox);
                            Admin.tabCake.bind();
                        });
                    });



                } else if (method === "search") {
                    Product.find(param, function(resp) {
                        actionBarContent = '<div class="el"><a href="#!/cakes" class="btn btn-default">กลับ</a></div><div class="el btn-group"><a href="#!/cakes/create" class="btn btn-primary" id="add-cake">เพิ่มเค้ก</a><button class="btn btn-danger disabled" id="delete-cake">ลบ</button></div><div class="el"><form id="form-search-cake"><div class="input-group" style="max-width: 400px"><input type="text" name="q" id="keyword" class="form-control" placeholder="ค้นหาตามชื่อเค้ก, คำอธิบาย" required><span class="input-group-btn"><button class="btn btn-default" id="search-cake" type="submit">ค้นหา</button></span></div></form></div>';
                        $(actionBar).html(actionBarContent);
                        $("#form-search-cake #keyword").val(param);
                        Admin.tabCake.table(resp.message);
                        Admin.tabCake.bind();

                    });
                }
            }
        }

    };
    Admin.tabCake.table = function(cakes) {
        var cakeTable = '<div id="error"></div><div id="message"></div><form id="form-cake"><table class="p6n-table">' +
            '<tbody>' +
            '<tr>' +
            '<th style="width:13px">' +
            '<label class="ng-isolate-scope p6n-checkbox">' +
            '<input type="checkbox" class="ng-valid ng-scope ng-dirty ng-valid-parse ng-touched">' +
            '</label>' +
            '</th>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">ชื่อเค้ก</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name" >' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link p6n-sort-flip">ชนิด</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name text-right">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link p6n-sort-flip">ต้นทุน (บาท)</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name text-right">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link p6n-sort-flip">ขาย (บาท)</a>' +
            '</th>' +
            '</tr>';
        if (cakes && cakes.length > 0) {
            for (var idx in cakes) {
                var product = cakes[idx];
                cakeTable += '<tr>' +
                    '<td>' +
                    '<label class="ng-isolate-scope p6n-checkbox">' +
                    '<input type="checkbox"  name="p_id" value="' + product.id + '" class="ng-valid ng-scope ng-dirty ng-valid-parse ng-touched">' +
                    '</label>' +
                    '</td>' +
                    '<td><a href="#!/cakes/' + product.id + '">' + product.name + '</a></td>' +
                    '<td>' + product.category.name + '</td>' +
                    '<td class="text-right">' + Admin.toMoney(product.cost) + '</td>' +
                    '<td class="text-right">' + Admin.toMoney(product.price) + '</td>' +
                    '</tr>';
            }
        }
        else {
            cakeTable += '<tr><td colspan="5">ไม่มีเค้กใด ๆ ในระบบ</td></tr>';
        }
        cakeTable += '</table>';
        $(content).html(cakeTable);
    };
    Admin.tabCake.bind = function(id) {
        Admin.loadedMask();
        $("input[type=checkbox]").on("change", function(ev) {
            var pIds = $("#form-cake").serializeArray();
            if(pIds && pIds.length > 0)
                $("#delete-cake").removeClass("disabled");
            else
                $("#delete-cake").addClass("disabled");
        });

        $("#refresh-cake").click(function() {
            Admin.onHashChanged();
        });

        $("#form-search-cake").submit(function(ev) {
            window.location.hash = "#!/cakes/search/" + ($("#form-search-cake #keyword").val());
            return false;
        });

        $("#form-cake #add-sale").click(function(ev) {
            var productSale = $("#product_sale");
            var len = $("#product_sale>div").length;
            if (len < 5) {
                $(productSale).append(Product.form.sale());
                $("#product_sale>div a").click(function() {
                    $(this).parent().parent().parent().remove();
                });
            }
            return false;
        });

        $("#product_sale>div a").click(function() {
            $(this).parent().parent().parent().remove();
        });

        $(".action-bar #save-cake").click(function(ev){
            Admin.loadingMask();
            Product.save($("#form-cake"),function(resp){
                Admin.loadedMask();
                alert("เพิ่มข้อมูลเรียบร้อย");
                window.location.hash = "#!/cakes";
            });
        });

        $(".action-bar #update-cake").click(function(ev){
            Admin.loadingMask();
            Product.update(id,$("#form-cake"),function(resp){
                Admin.loadedMask();
                alert("แก้ไขข้อมูลเรียบร้อย");
                window.location.hash = "#!/cakes";
            });
        });

        $(".action-bar #delete-cake").click(function() {

            var pIds = $("#form-cake").serializeArray();
            if(pIds && pIds.length > 0){
                Admin.loadingMask();
                var length = pIds.length;
                var total = 0;
                for(var idx in pIds){
                    Product.delete(pIds[idx].value,function(){
                        Admin.loadedMask();
                        if(++total == length){
                            alert("ลบข้อมูลเรียบร้อย");
                            Admin.onHashChanged(); //refresh
                        }

                    });
                }

            }

        });

        $("#form-cake #pictures").on("change", function(ev) {
            $("#form-cake #pic-list").html('<p>กำลังอัพโหลดไฟล์...</p>');
            var files = ev.target.files;
            var data = new FormData();
            $.each(files, function(key, value) {
                data.append("pictures", value);
            });

            Ajax.UPLOAD("/picture", data, function(resp) {
                if (resp.message) {
                    var result = '';
                    for (var idx in resp.message) {
                        var picture = resp.message[idx];
                        result += Product.form.picture(picture);
                    }
                    $("#form-cake #pic-list").html(result);
                }
            });
        });
    };

    Admin.tabBank = function(){
        var actionBarContent = '<div class="el"><button class="btn btn-default" id="refresh-bank">รีเฟรช</button></div><div class="el btn-group"><a href="#!/bankaccount/create" class="btn btn-primary">เพิ่มบัญชี</a><button class="btn btn-danger disabled" id="delete-cake">ลบ</button></div><div class="el"><form id="form-search-bank"><div class="input-group" style="max-width: 400px"><input type="text" name="q" id="keyword" class="form-control" required><span class="input-group-btn"><button class="btn btn-default" id="search-bank" type="submit">ค้นหา</button></span></div></form></div>';

        var hash = window.location.hash.substr(2);
        var resources = hash.split('/');
        var method, param;
        if (resources) {
            if (resources.length >= 3)
                method = resources[2];
            if (resources.length >= 4)
                param = resources[3];
        }


        Admin.loadingMask();
        if (!(method)) { //list all cake
            BankAccount.all(function(resp){
                $(actionBar).html(actionBarContent);
                Admin.tabBank.table(resp.message);
                Admin.tabBank.bind();

            });
        } else if (method) {
            switch (method){
                case "create":
                    actionBarContent = '<div class="el"><a class="btn btn-default" href="#!/bankaccount">กลับ</a></div><div class="el btn-group"><button class="btn btn-primary" id="add-bank">เพิ่มบัญชี</button></div>';
                    $(actionBar).html(actionBarContent);
                    $(content).html(Admin.tabBank.form("เพิ่มบัญชีใหม่"));
                    BankType.all(function(bankTypeResp){
                        $("#banktype-container").html(Admin.tabBank.banktype.form(bankTypeResp.message));
                        Bank.all(function(bankResp){
                            $("#bank-container").html(Admin.tabBank.bank.form(bankResp.message));
                            Admin.tabBank.bind();
                        });

                    });


                    break;
            }
        }
    };
    Admin.tabBank.table = function(bankAccount) {
        var table = '<form id="form-cake"><table class="p6n-table">' +
            '<tbody>' +
            '<tr>' +
            '<th style="width:13px">' +
            '<label class="ng-isolate-scope p6n-checkbox">' +
            '<input type="checkbox" class="ng-valid ng-scope ng-dirty ng-valid-parse ng-touched">' +
            '</label>' +
            '</th>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link">ชื่อบัญชี</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name" >' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link p6n-sort-flip">เลขบัญชี</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link p6n-sort-flip">ประเภท</a>' +
            '</th>' +
            '<th class="p6n-acl-header-name">' +
            '<a href="javascript:;" class="p6n-clickable-link p6n-sort-link p6n-sort-flip">ธนาคาร</a>' +
            '</th>' +
            '</tr>';
        if (bankAccount && bankAccount.length > 0) {
            for (var idx in bankAccount) {
                var bank = bankAccount[idx];
                table += '<tr>' +
                    '<td>' +
                    '<label class="ng-isolate-scope p6n-checkbox">' +
                    '<input type="checkbox"  name="p_id" value="' + bank.id + '" class="ng-valid ng-scope ng-dirty ng-valid-parse ng-touched">' +
                    '</label>' +
                    '</td>' +
                    '<td><a href="#!/bankaccount/' + bank.id + '">' + bank.accName + '</a></td>' +
                    '<td>' + bank.accNo + '</td>' +
                    '<td>' + bank.type.name + '</td>' +
                    '<td>' + bank.branch.bank.name + ' สาขา'+ bank.branch.name + '</td>' +
                    '</tr>';
            }
        }
        else {
            table += '<tr><td colspan="5">ไม่มีบัญชีธนาคารใด ๆ ในระบบ</td></tr>';
        }
        table += '</table>';
        $(content).html(table);
    };
    Admin.tabBank.form = function(title, bankAccount){
      if(!bankAccount)
        bankAccount = {};
        return '<div class="small-padding">' +
            '<form action="POST" id="form-bankaccount">' +
            '<h2  class="title">' + title + '</h2>' +
            '<p class="subtitle">บัญชีที่เพิ่ม จะใช้ในการชำระเงินของลูกค้า</p>'+
            '<div class="row">' +
            '<div class="col-md-6" style="max-width: 400px">' +
            '<div class="form-group">' +
            '<label for="name">ชื่อบัญชี</label><input class="form-control" id="acc_name" name="acc_name" value="'+(bankAccount.acc_name?bankAccount.acc_name:'')+'" placeholder="นายขายเค้ก อร่อยดี">' +
            '</div>' +
            '<div class="form-group">' +
            '<label>เลขบัญชี</label><p class="subtitle hint">สามารถใส่เครื่องหมาย - ได้</p><input class="form-control" id="acc_no" name="acc_no" value="'+(bankAccount.acc_no?bankAccount.acc_no:'')+'">' +
            '</div>' +
            '<div class="form-group">' +
            '<label for="pictures">ประเภทบัญชี</label><div id="banktype-container"></div>' +
            '</div>' +
            '<div class="form-group">' +
            '<label for="pictures">ธนาคาร</label><div id="bank-container"></div>' +
            '</div>' +
            '<div class="form-group">' +
            '<label for="pictures">สาขา</label><input class="form-control" id="branch_name"><input type="hidden" id="branch_id" name="branch_id">' +
            '</div>' +
            '</form>' +
            '</div>';
    };
    Admin.tabBank.bank = {};
    Admin.tabBank.bank.form = function(banks){
        var html = '<select class="form-control" id="bank_id">';
        for(var idx in banks){
            var bank = banks[idx];
            html += '<option value="'+bank.id+'">'+bank.name+'</option>';
        }
        html += '<select>';
        return html;
    };
    Admin.tabBank.banktype = {};
    Admin.tabBank.banktype.form = function(banktype){
        var html = '<select class="form-control" id="type_id" name="type_id">';
        for(var idx in banktype){
            var type = banktype[idx];
            html += '<option value="'+type.id+'">'+type.name+'</option>';
        }
        html += '<select>';
        return html;
    };
    Admin.tabBank.bind = function(){
        Admin.loadedMask();

        $("#add-bank").click(function(){
            Admin.loadingMask();
            BankAccount.add($("#form-bankaccount"), function(resp){
                Admin.loadedMask();
                alert("เพิ่มข้อมูลเรียบร้อย");
                window.location.hash = "#!/bankaccount";
            });
            return false;
        });

        $("#branch_name").autocomplete({
            serviceUrl: HOST + "/bank/"+$("#bank_id").val()+"/branches",
            paramName: 'q',
            transformResult: function (response) {
                if (JSON.parse(response).message)
                    return {
                        suggestions: $.map(JSON.parse(response).message, function (dataItem) {
                            return {
                                value: dataItem.name,
                                data: dataItem.id
                            };
                        })
                    };
                return {suggestions: []};
            },
            onSelect: function (suggestion) {
                $("#form-bankaccount").find("#branch_id").val(suggestion.data);
            }
        });

        $("#bank_id").change(function(){
            $("#branch_name").autocomplete({
                serviceUrl: HOST + "/bank/"+$("#bank_id").val()+"/branches",
                paramName: 'q',
                transformResult: function (response) {
                    if (JSON.parse(response).message)
                        return {
                            suggestions: $.map(JSON.parse(response).message, function (dataItem) {
                                return {
                                    value: dataItem.name,
                                    data: dataItem.id
                                };
                            })
                        };
                    return {suggestions: []};
                },
                onSelect: function (suggestion) {
                    $("#form-bankaccount").find("#branch_id").val(suggestion.data);
                }
            });
        });

        $("#form-search-bank").submit(function(){
            return false;
        });
    };

    $(window).bind("hashchange", function() {
        Admin.onHashChanged();
    });

    if (window.location.hash) {
        Admin.onHashChanged();
    }
});