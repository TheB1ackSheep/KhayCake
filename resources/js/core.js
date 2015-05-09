//var HOST = 'http://jsp.falook.me/khaycake';
var HOST = 'http://localhost:8080/khaycake';
var sessionID = '';

function isInteger(str) {
    return str ? str.toString().match(/^[0-9]+$/) !== null : false;
}

function isFloat(str) {
    return str ? (isInteger(str.toString()) || str.toString().match(/^\.[0-9]+$/) || str.toString().match(/^[0-9]+\.[0-9]+$/)) !== null : false;
}

function toMoney(baht){
    return baht.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
}

function urlParam(name,value){
    if(name && value) {
        var url = window.location.hash;
        var fragment = url.split("?");
        var inUrl = fragment[0];
        var params = fragment[1];
        if (params) {
            console.log("params");
            params = params.split("&");
            var hasParam = false;
            for (var idx in params)
                if (params[idx].indexOf(name) >= 0) {
                    hasParam = true;
                    params[idx] = name + "=" + value;
                }
            if(!hasParam)
                params.push(name+"="+value);
            params = params.join("&");
        } else {
            console.log("no params");
            params = name + "=" + value;
        }
        return inUrl + "?" + params;
    }else return window.location.hash;
}

function getParameter(name){
    var url = window.location.hash;
    var fragment = url.split("?");
    var params = fragment[1];
    if(params){
        params = params.split("&");
        for(var idx in params)
            if(params[idx].indexOf(name) >= 0)
            {
                return params[idx].split("=")[1];
            }
    }
}

function pagination(array,page,size,name){
	console.log(name?name:"page");
    var ren = {html: '', hasNext: false, data: array};
    if(isInteger(page)) {
        page = parseInt(page);

        if (array && array.slice) {
            var startAt = ((page - 1) * size);
            var endAt = startAt + size;
            ren.total = Math.ceil(array.length / size);
            ren.hasNext = (ren.total != 1) && (page <= ren.total);

            if (ren.hasNext) {
                ren.html = '<div class="page"><nav>' +
                    '<ul class="pagination">';
                if (page != 1)
                    ren.html += '<li>' +
                    '<a class="page-prev" href="'+urlParam(name?name:"page",page-1)+'" aria-label="Previous">' +
                    '<span aria-hidden="true">&laquo;</span>' +
                    '</a>' +
                    '</li>';
                for (var i = 1; i <= ren.total; i++) {
                    ren.html += '<li '+(page==i?'class="active"':'')+'><a href="'+urlParam(name?name:"page",i)+'" class="page-button">' + i + '</a></li>';
                }
                if (page < ren.total)
                    ren.html += '<li>' +
                        '<a href="'+urlParam(name?name:"page",page+1)+'" class="page-next" aria-label="Next">' +
                        '<span aria-hidden="true">&raquo;</span>' +
                        '</a>' +
                        '</li>';
                ren.html += '</ul>' +
                    '</nav></div>';
            }
            ren.data = array.slice(startAt, endAt);
        }
        return ren;
    }else{
        return pagination(array,1,size, name);
    }
}

function urlParse(){
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
}

function isCookieEnable(){
    var cookieEnabled=(navigator.cookieEnabled)? true : false

    //if not IE4+ nor NS6+
    if (typeof navigator.cookieEnabled=="undefined" && !cookieEnabled){
        document.cookie="t"
        cookieEnabled=(document.cookie.indexOf("t")!=-1)? true : false
    }

    return cookieEnabled;
}

var Ajax = Ajax || {};
Ajax.onError = function(resp) {
    //TODO : add error notification for ajax request
};
Ajax.encodeUrl = function(url) {
    var query = url.split("?");
    if (query.length > 1)
        url = query[0];
    url = HOST + url + ((sessionID && !isCookieEnable()) ? ";jsessionid=" + sessionID : "") + ((query[1]) ? "?" + query[1] : "");
    return url;
};
Ajax.do = function(method, url, data, success) {
        $.ajax({
            type: method,
            data: data,
            url: Ajax.encodeUrl(url),
            success: function(resp) {
                if (typeof(success) === "function")
                    success(resp);
                if (resp.sessionId)
                    sessionID = resp.sessionId;
            },
            error: function(resp) {
                if (resp.sessionId)
                    sessionID = resp.sessionId;
                Ajax.onError(resp);
            }
        });
    };
Ajax.GET = function(url, success) {
    Ajax.do("GET", url, null, success);
};
Ajax.POST = function(url, data, success) {
    Ajax.do("POST", url, data, success);
};
Ajax.UPLOAD = function(url, data, success) {
    $.ajax({
        type: "POST",
        url: Ajax.encodeUrl(url),
        data: data,
        processData: false,
        contentType: false,
        success: function(resp) {
            if (typeof(success) === "function")
                success(resp);
            if (resp.sessionId)
                sessionID = resp.sessionId;
        },
        error: function(resp) {
            if (resp.sessionId)
                sessionID = resp.sessionId;
            Ajax.onError(resp);
        }
    });
};


var Product = Product || {
        URL: "/product"
    };
Product.all = function(fn,q) {
    if(!q)
    	Ajax.GET(Product.URL, fn);
    else
    	Ajax.GET(Product.URL + "?q=" + q, fn);
};
Product.find = function(id, fn) {
    if (id)
        if (isInteger(id))
            Ajax.GET(this.URL + "/" + id, fn);
        else
            Ajax.GET(this.URL + "?q=" + id, fn);
};
Product.byCategory = function(cat_id, q, fn) {
    if (isInteger(cat_id))
        Ajax.GET(Product.URL + "?cat=" + cat_id +(q?"&q="+q:""), fn);
};
Product.cupcake = function(fn, q) {
	Product.byCategory(1, q, fn);
};
Product.crapecake = function(fn, q) {
    Product.byCategory(2, q, fn);
};
Product.brownie = function(fn, q) {
    Product.byCategory(3, q, fn);
};
Product.partycake = function(fn, q) {
    Product.byCategory(4, q, fn);
};
Product.save = function(form, fn) {
    if (form && form.serialize) {
        var data = form.serialize();
        Ajax.POST(Product.URL, data, fn);
    }
};
Product.update = function(id, form, fn) {
    if (form && form.serialize) {
        var data = form.serialize();
        Ajax.POST(Product.URL+"/"+id, data, fn);
    }
};
Product.delete = function(id, fn){
    Ajax.GET(Product.URL+"/"+id+"/delete", fn);
};
Product.pictures = function(id, fn){
  Ajax.GET(Product.URL+"/"+id+"/pictures",fn);
};
Product.sales = function(id, fn){
    Ajax.GET(Product.URL+"/"+id+"/sales",fn);
};
Product.form = function(title,cake) {
    if(!cake)
        cake = {};
    return '<div class="small-padding">' +
        '<form action="POST" id="form-cake">' +
        '<h2>' + title + '</h2>' +
        '<div class="row">' +
        '<div class="col-md-6" style="max-width: 400px">' +
        '<h3 class="title">ข้อมูลทั่วไป</h3>' +
        '<p class="subtitle">ข้อมูลทั่วไปเกี่ยวกับเค้ก</p>'+
        '<div class="form-group">' +
        '<label for="name">ชื่อเค้ก</label><input class="form-control" id="name" name="name" value="'+(cake.name?cake.name:'')+'" placeholder="คัพเค้กหน้าเป็ด, เค้กปาร์ตี้วันเกิด">' +
        '</div>' +
        '<div class="form-group">' +
        '<label>รายละเอียด</label><textarea rows="5" class="form-control" name="detail" id="detail">'+(cake.detail?cake.detail:'')+'</textarea>' +
        '</div>' +
        '<div class="form-group">' +
        '   <label for="pictures">รูปภาพ</label><div id="pic-list"></div><input type="file" accept="image/*" multiple name="pictures" id="pictures"/>' +
        '</div>' +
        '<div class="form-group">' +
        '<div class="row">' +
        '<div class="col-sm-6">' +
        '<label>หน่วยนับ</label><div id="unit-container"></div>' +
        '</div>' +
        '<div class="col-sm-6">' +
        '<label>ชนิด</label>' +
        '<div id="category-container"></div>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '<div class="form-group">' +
        '<div class="row">' +
        '<div class="col-sm-6">' +
        '<label>ต้นทุน (บาท)</label><div class="input-group"><span class="input-group-addon">&#3647;</span><input type="number"  value="'+(cake.cost?cake.cost:'')+'" id="cost" name="cost" class="form-control"/></div>' +
        '</div>' +
        '<div class="col-sm-6">' +
        '<label>ราคาขายต่อหน่วย (บาท)</label><div class="input-group"><span class="input-group-addon">&#3647;</span><input type="number"  value="'+(cake.price?cake.price:'')+'" id="price" name="price" class="form-control"/></div>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '<div class="col-md-6" style="max-width: 400px">' +
        '<h3 class="title">ราคาพิเศษ</h3>' +
        '<p class="subtitle">ราคาขายเมื่อลูกค้าซื้อตามจำนวนที่กำหนด</p>' +
        '<div class="row" id="product_sale"></div>' +
        '<div class="form-group">' +
        '<label>&nbsp;</label><button type="button" class="btn btn-default form-control" id="add-sale">เพิ่มราคาพิเศษ</button>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</form>' +
        '</div>';
};
Product.form.sale = function(sale) {
    if(!sale)
        sale = {};
    return '<div class="form-group"><div class="col-md-4">' +
        '<div class="form-group">' +
        '<label>จำนวนชิ้น</label><input type="number" name="sale_qty" class="form-control" value="' + (sale.qty ? sale.qty : "") + '"/>' +
        '</div>' +
        '</div>' +
        '<div class="col-md-6">' +
        '<div class="form-group">' +
        '<label>ราคาขาย (บาท)</label>' +
        '<div class="input-group"><span class="input-group-addon">&#3647;</span><input type="number" name="sale_price" class="form-control" value="' + (sale.price ? sale.price : '') + '"/></div>' +
        '</div></div><div class="col-md-2"><div class="form-group"><label>&nbsp;</label>' +
        '<a ' + (sale.id ? 'id="sale-' + sale.id + '"' : '') +
        ' class="btn btn-default form-control"><span class="glyphicon glyphicon-trash"></span></a></div></div>';
};
Product.form.picture = function(picture){
    if(!picture)
        picture = {};
    return '<div class="filename alert-dismissible" role="alert">' + (picture.filename?picture.filename:'') + '</div><input type="hidden" name="pic_id" value="' + (picture.id?picture.id:'') + '"/>';
};
Product.box = function(cake){
    if(!cake)
        cake = {};
    return '<div class="col-md-3 col-sm-4 col-xs-6">' +
        '<div class="box inactive">' +
        '<div class="box-img">' +
            '<div id="box-img-'+cake.id+'" style="background-image:url(\''+HOST+'/product/'+cake.id+'/picture\')"></div>'+
        '</div>' +
        '<div class="box-name">' + cake.name + '</div>' +
        '<form class="cart-add" action="#!">' +
        '<input type="hidden" name="p_id" value="'+cake.id+'"/>'+
        '<input type="hidden" name="qty" value="1"/>'+
        '<div class="box-button">' +
        '<div class="price">' + cake.price + ' บาท/' + cake.unit.name + '</div>' +
        '<button class="cart">Add to Cart</button>' +
        '</div>' +
        '</form>' +
        '</div>' +
        '</div>';
};

var Unit = Unit || {
        URL: "/unit"
    };
Unit.all = function(fn){
    Ajax.GET(Unit.URL,fn);
};
Unit.box = function(fn){
    Ajax.GET(Unit.URL,function(resp){
        var select = '<select class="form-control" name="unit_id">"';
        for (var idx in resp.message) {
            var unit = resp.message[idx];
            select += '<option value="' + unit.id + '">' + unit.name + '</option>';
        }
        select += "</select>";
        if(typeof(fn) === "function")
            fn(select);
    });
};

var Category = Category || {
        URL: "/category"
    };
Category.all = function(fn){
    Ajax.GET(Category.url,fn);
};
Category.box = function(fn){
    Ajax.GET(Category.URL,function(resp){
        var select = '<select class="form-control" name="cat_id">"';
        for (var idx in resp.message) {
            var category = resp.message[idx];
            select += '<option value="' + category.id + '">' + category.name + '</option>';
        }
        select += "</select>";
        if(typeof(fn) === "function")
            fn(select);
    });
};

var Cart = Cart || {
        URL: "/cart"
    };
Cart.update = function(form,fn){
    if(form && form.serialize)
        Ajax.POST(Cart.URL, form.serialize(), fn);
};
Cart.add = function(form,fn){
    if(form && form.serialize)
        Ajax.POST(Cart.URL+"/add", form.serialize(), fn);
};
Cart.all = function(fn){
    Ajax.GET(Cart.URL, fn);
};
Cart.checkout = function(fn){
    Ajax.POST(Cart.URL +"/checkout", null, fn);
};
Cart.form = function(cart){

    var html = '';
    var total = 0.0;
    if(cart.items && cart.items.length > 0){
        var items = cart.items;
        html += '<form id="user-cart"><div class="header row">'+
            '<div class="cell column-order">YOUR ORDER</div>'+
            '<div class="cell column-price">PRICE (&#3647;)</div>'+
            '</div>';
        for(var idx in items){
            var item = items[idx];
            total += item.total;
            html += Cart.item.form(item, idx);
        }
        html += '<div class="footer row" style="width:280px;transition-delay:'+((items.length+1)*10)+'ms">'+
            '<div class="cell column-order text-right">รวม</div>'+
            '<div class="cell column-price">'+toMoney(total)+'</div>'+
            '</div>';
        html += '<div class="footer row"  style="width:280px;transition-delay:'+((items.length+2)*10)+'ms">'+
            '<div class="checkout cell text-right">'+
            '<a class="btn btn-default" href="#!/checkout">Check Out</a>'+
            '</div>'+
            '</div>';
        html += "</form>";
    }else{

        html += '<div class="header row">'+
            '<div class="cell column-order">ไม่มีสินค้าในตะกร้า</div>'+
            '</div>';
    }

    return html;
};
Cart.item = {};
Cart.item.form = function(item, idx){
    return '<div class="body row" style="transition-delay:'+(idx*10)+'ms">'+
        '<input type="hidden" name="p_id" value="'+item.product.id+'"/>'+
        '<input type="hidden" name="qty" value="'+item.qty+'"/>'+
        '<div title="'+item.product.name+'" class="cell column-order">'+item.product.name+'</div>' +
        '<div title="'+item.qty+' '+item.product.unit.name+'" class="cell column-qty">'+item.qty+' '+item.product.unit.name+'</div>'+
        '<div class="cell column-remove">'+
        '<span class="glyphicon glyphicon-remove"></span>'+
        '</div>'+
        '<div class="cell column-price">'+toMoney(item.total)+'</div>'+
        '</div>';
};

var Auth = Auth || {
        URL: "/auth"
    };
Auth.logout = function(fn){
    Ajax.GET(Auth.URL+"/logout", fn);
};
Auth.auth = function(form, fn){
    if(form && form.serialize)
        Ajax.POST(Auth.URL, form.serialize(), fn);
};
Auth.register = Auth.auth;
Auth.get = function(fn){
    Ajax.GET(Auth.URL, fn);
};
Auth.order = {
    URL: '/auth/order'
};
Auth.order.all = function(fn){
  Ajax.GET(Auth.order.URL, fn);
};
Auth.order.find = function(id, fn){
    Ajax.GET(Auth.order.URL+"/"+id, fn);
};
Auth.order.getItem = function(id, fn){
  Ajax.GET(Auth.order.URL+"/"+id+"/item", fn);
};
Auth.payment = {
    URL: '/auth/payment'
};
Auth.payment.all = function(fn){
    Ajax.GET(Auth.payment.URL, fn);
};
Auth.payment.find = function(id, fn){
    Ajax.GET(Auth.payment.URL+"/"+id, fn);
};

var Tumbon = Tumbon || {
        URL: "/tumbon"
    };
Tumbon.find = function(q, fn){
    Ajax.GET(Tumbon.URL+(isInteger(q)?'/'+q:(q?'?q='+q:'')), fn);
};

var Amphur = Amphur ||{
      URL: "/amphur"
    };
Amphur.find = function(q, fn){
    Ajax.GET(Amphur.URL+(isInteger(q)?'/'+q:(q?'?q='+q:'')), fn);
};

var Province = Province ||{
        URL: "/province"
    };
Province.find = function(q, fn){
    Ajax.GET(Province.URL+(isInteger(q)?'/'+q:(q?'?q='+q:'')), fn);
};

var Shipment = Shipment || {};
Shipment.Address = Shipment.Address || {
      URL: "/shipment/address"
    };
Shipment.Address.find = function(id, fn){
  Ajax.GET(Shipment.Address.URL+"/"+id, fn);
};
Shipment.Address.all = function(fn){
  Ajax.GET(Shipment.Address.URL, fn);
};
Shipment.Address.add = function(form, fn){
    if(form && form.serialize)
        Ajax.POST(Shipment.Address.URL, form.serialize(), fn);
};
Shipment.Address.update = function(id, form, fn){
    if(form && form.serialize)
        Ajax.POST(Shipment.Address.URL+"/"+id, form.serialize(), fn);
};
Shipment.Method = Shipment.Method || {
      URL: "/shipment/method"
    };
Shipment.Method.set = function(form, fn){
    if(form && form.serialize)
        Ajax.POST(Shipment.Method.URL, form.serialize(), fn);
};
Shipment.Method.find = function(id, fn){
    Ajax.GET(Shipment.Method.URL+"/"+id, fn);
};
Shipment.Method.all = function(fn){
    Ajax.GET(Shipment.Method.URL, fn);
};


var Bank = Bank || {
        URL: "/bank"
    };
Bank.all = function(fn){
    Ajax.GET(Bank.URL, fn);
};
Bank.find = function(id, fn){
    Ajax.GET(Bank.URL+"/"+id, fn);
};

var BankType = BankType || {
        URL:"/bankaccounttype"
    };
BankType.all = function(fn){
    Ajax.GET(BankType.URL, fn);
};
BankType.find = function(id, fn){
    Ajax.GET(BankType.URL+"/"+id, fn);
};

var BankAccount = BankAccount || {
        URL: "/bankaccount"
    };
BankAccount.all = function(fn){
    Ajax.GET(BankAccount.URL, fn);
};
BankAccount.find = function(id, fn){
    if(isInteger(id))
        Ajax.GET(BankAccount.URL+"/"+id, fn);
    else
        Ajax.GET(BankAccount.URL+"/q/"+id, fn);
};
BankAccount.add = function(form, fn){
    if(form && form.serialize)
        Ajax.POST(BankAccount.URL, form.serialize(), fn);
};

var Payment = Payment || {
        URL:"/payment"
    };
Payment.confirm = function(form, fn){
    if(form && form.serialize)
        Ajax.POST(Payment.URL, form.serialize(), fn);
};
Payment.approving = function(fn, q){
    if(!q)
        Ajax.GET(Payment.URL+"/approving", fn);
    else
        Ajax.GET(Payment.URL+"/approving?q="+q, fn);
};
Payment.find = function(id, fn){
    Ajax.GET(Payment.URL+"/"+id, fn);
};
Payment.all = function(fn, q){
    if(!q)
        Ajax.GET(Payment.URL, fn);
    else
        Ajax.GET(Payment.URL+"?q="+q, fn);
};
Payment.accept = function(id, fn){
    Ajax.GET(Payment.URL+"/"+id+"/accept", fn);
};
Payment.deny = function(id, fn){
    Ajax.GET(Payment.URL+"/"+id+"/deny", fn);
};

var Order = Order || {
        URL: "/order"
    };
Order.find = function(id, fn){
  Ajax.GET(Order.URL+"/"+id, fn);
};
Order.items = function(id, fn){
    Ajax.GET(Order.URL+"/"+id+"/item", fn);
};
Order.all = function(fn, q){
    if(!q)
        Ajax.GET(Order.URL, fn);
    else
        Ajax.GET(Order.URL+"?q="+q, fn);
};
Order.shipping = function(fn, q){
    if(!q)
        Ajax.GET(Order.URL+"/shipping", fn);
    else
        Ajax.GET(Order.URL+"/shipping?q="+q, fn);
};
Order.shipped = function(id, track_id, fn){
    Ajax.GET(Order.URL+"/"+id+"/shipped"+(track_id?'?track_id='+track_id:''), fn);
};
Order.paid = function(fn, q){
    if(!q)
        Ajax.GET(Order.URL+"/paid", fn);
    else
        Ajax.GET(Order.URL+"/paid?q="+q, fn);
};

