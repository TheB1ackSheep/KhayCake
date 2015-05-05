var HOST = 'http://jsp.falook.me/khaycake';
//var HOST = 'http://localhost:8080/khaycake';
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

var Ajax = Ajax || {};
Ajax.onError = function(resp) {
    //TODO : add error notification for ajax request
};
Ajax.encodeUrl = function(url) {
    var query = url.split("?");
    if (query.length > 1) {
        url = query[0];
    }
    url = HOST + url + ((sessionID) ? ";jsessionid=" + sessionID : "") + ((query[1]) ? "?" + query[1] : "");
    return url;
};
Ajax.do =
    function(method, url, data, success) {
        var ajax = $.ajax({
            type: method,
            data: data,
            url: Ajax.encodeUrl(url),
            success: function(resp) {
                if (resp.success) {
                    if (typeof(success) === "function")
                        success(resp);
                } else
                    Ajax.onError(resp);
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
    var ajax = $.ajax({
        type: "POST",
        url: Ajax.encodeUrl(url),
        data: data,
        processData: false,
        contentType: false,
        success: function(resp) {
            if (resp.success) {
                if (typeof(success) === "function")
                    success(resp);
            } else
                Ajax.onError(resp);
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

var Notify = Notify || {};
Notify.queue = [];
Notify.alert = function(text){
    Notify.queue.push(text);
    var box = $('<div class="notify"></div>');
    var len = Notify.queue.length;
    $(box).text(text);
    $(box).css({
       top:'-100px'
    });
    $("body").append(box);
    $(box).animate({
        top:'px'
    }, 350, function(){
        setTimeout(function(){
            $(box).animate({
                top:'100px',
                opacity:0
            }, 350,function(){
                $(box).remove();
            })
        },3000);
    });
};

var Product = Product || {
        URL: "/product"
    };
Product.all = function(fn) {
    Ajax.GET(this.URL, fn);
};
Product.find = function(id, fn) {
    if (id)
        if (isInteger(id))
            Ajax.GET(this.URL + "/" + id, fn);
        else
            Ajax.GET(this.URL + "?q=" + id, fn);
};
Product.byCategory = function(cat_id, fn) {
    if (isInteger(cat_id))
        Ajax.GET(this.URL + "?cat=" + cat_id, fn);
};
Product.cupcake = function(fn) {
    Product.byCategory(1, fn);
};
Product.crapecake = function(fn) {
    Product.byCategory(2, fn);
};
Product.brownie = function(fn) {
    Product.byCategory(3, fn);
};
Product.partycake = function(fn) {
    Product.byCategory(4, fn);
};
Product.save = function(form, fn) {
    if (form && form.serialize) {
        var data = form.serialize();
        Ajax.POST(this.URL, data, fn);
    }
};
Product.update = function(id, form, fn) {
    if (form && form.serialize) {
        var data = form.serialize();
        Ajax.POST(this.URL+"/"+id, data, fn);
    }
};
Product.delete = function(id, fn){
    Ajax.GET(this.URL+"/"+id+"/delete", fn);
};
Product.pictures = function(id, fn){
  Ajax.GET(this.URL+"/"+id+"/pictures",fn);
};
Product.sales = function(id, fn){
    Ajax.GET(this.URL+"/"+id+"/sales",fn);
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
    return '<div class="col-md-3 col-xs-12">' +
        '<div class="box inactive">' +
        '<div class="box-img">' +
        '<img id="img-'+cake.id+'" src="'+HOST+'/product/'+cake.id+'/picture" alt=""/>' +
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
    Ajax.GET(this.URL,fn);
};
Unit.box = function(fn){
    Ajax.GET(this.URL,function(resp){
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
    Ajax.GET(this.url,fn);
};
Category.box = function(fn){
    Ajax.GET(this.URL,function(resp){
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
        Ajax.POST(this.URL, form.serialize(), fn);
};
Cart.add = function(form,fn){
    if(form && form.serialize)
        Ajax.POST(this.URL+"/add", form.serialize(), fn);
};
Cart.all = function(fn){
    Ajax.GET(this.URL, fn);
};
Cart.form = function(cart){
    var items = cart.items;
    var html = ''
    var total = 0.0;
    if(items && items.length > 0){
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
Auth.auth = function(form, fn){
    if(form && form.serialize)
        Ajax.POST(this.URL, form.serialize(), fn);
};
Auth.register = Auth.auth;
Auth.get = function(fn){
    Ajax.GET(this.URL, fn);
};

var Tumbon = Tumbon || {
        URL: "/tumbon"
    };
Tumbon.find = function(q, fn){
    Ajax.GET(this.URL+(isInteger(q)?'/'+q:(q?'?q='+q:'')), fn);
};

var Amphur = Amphur ||{
      URL: "/amphur"
    };
Amphur.find = function(q, fn){
    Ajax.GET(this.URL+(isInteger(q)?'/'+q:(q?'?q='+q:'')), fn);
};

var Province = Province ||{
        URL: "/province"
    };
Province.find = function(q, fn){
    Ajax.GET(this.URL+(isInteger(q)?'/'+q:(q?'?q='+q:'')), fn);
};

var Shipment = Shipment || {};
Shipment.Address = Shipment.Address || {
      URL: "/shipment/address"
    };
Shipment.Address.find = function(id, fn){
  Ajax.GET(this.URL+"/"+id, fn);
};
Shipment.Address.all = function(fn){
  Ajax.GET(this.URL, fn);
};
Shipment.Address.add = function(form, fn){
    if(form && form.serialize)
        Ajax.POST(this.URL, form.serialize(), fn);
};
Shipment.Address.update = function(id, form, fn){
    if(form && form.serialize)
        Ajax.POST(this.URL+"/"+id, form.serialize(), fn);
};