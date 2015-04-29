var A;
$(document).ready(function(){

    var loadingMask = $('.app-loading-mask');
    var host = "http://localhost:8080/khaycake";


    var actionBar = $(".app-content .action-bar");
    var content = $(".app-content .content");

    var error = {}, msg = null;
    var sessionID;


    var Admin = Admin || {
            xhr: [],
            isAjax: false,
            onHashChanged: function(){
                var hash = window.location.hash.substr(2);
                var section = hash.split('/')[1];
                var method = hash.split('/')[2];
                var param = hash.split('/')[3];

                switch(section){
                    case "cakes":
                        if(method%1===0){
                            this.loadCakeById(method);
                        }else{
                            if(method == "add")
                                this.loadCakeAddForm();
                            else if(method == "all")
                                this.loadCakeIndex(true);
                            else if(method == "search") {
                                if (param) {
                                    this.loadCakeIndex(param);
                                }
                            }else
                                this.loadCakeIndex();
                        }



                        break;
                    case "customers":
                        break;
                    default :
                        section = "dashboard";
                        break;
                }



                $(".app-nav a").removeClass("active");
                $(".app-nav a#"+section).addClass("active");



            },
            loadingMask: function(){
                loadingMask.html("<span class='loader'><span class='loader-inner'></span></span>");
                loadingMask.addClass("show");
            },
            loadedMask : function(){
                loadingMask.html("");
                loadingMask.removeClass("show");
            },
            toMoney : function(double){
                return double.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
            },
            showInfo : function(){

                if(error.error){
                    var result = '<div class="alert alert-danger" role="alert">';
                    for(var idx in error.message)
                        result += error.message[idx]+"<br/>"
                    result += '</div>';
                    $("#error").html(result);
                }

                if(msg){
                    var result = '<div class="alert alert-info" role="alert">'+msg+'</dov>';
                    $("#message").html(result);
                }

            },
            clearInfo : function(){
                $("#error").html('');
                $("#message").html('');
                error = [];
                msg = null;
            },
            cancleAllRequest : function(){
                for(var idx in Admin.xhr)
                    if(Admin.xhr[idx].abort) {
                        Admin.xhr[idx].abort();
                        Admin.xhr.splice(idx,1);
                    }
            },
            url: function(path){
                var query = path.split("?");
                if(query.length > 1) {
                    path = query[0];
                }
                return host+path+((sessionID)?";jsessionid="+sessionID:"")+((query[1])?"?"+query[1]:"");
            },
            get : function(path, fn, abort){
                if(abort)
                    Admin.cancleAllRequest();
                Admin.loadingMask();
                Admin.clearInfo();

                var ajax = $.ajax({
                    type: "GET",
                    url: Admin.url(path),
                    success: function(resp){
                        if(typeof(fn) === "function")
                            fn(resp);
                        if(resp.JSESSIONID)
                            sessionID=resp.JSESSIONID;
                        Admin.loadedMask();
                        Admin.showInfo();
                    },
                    error: function(resp){
                        error = "มีบางอย่างไม่ถูกต้อง";
                        Admin.loadedMask();
                        Admin.showInfo();
                    }
                });

                Admin.xhr.push(ajax);
            },
            post : function(path, data, fn){
                Admin.cancleAllRequest();
                Admin.loadingMask();
                Admin.clearInfo();



                var ajax = $.ajax({
                    type: "POST",
                    url: Admin.url(path),
                    data: data,
                    success: function(resp){
                        if(typeof(fn) === "function")
                            fn(resp);
                        if(resp.JSESSIONID)
                            sessionID=resp.JSESSIONID;
                        Admin.loadedMask();
                        Admin.showInfo();
                    },
                    error: function(resp){
                        error = "มีบางอย่างไม่ถูกต้อง";
                        Admin.loadedMask();
                        Admin.showInfo();
                    }
                });

                Admin.xhr.push(ajax);
            },
            upload: function(path, data, s,e){
                var ajax = $.ajax({
                    type: "POST",
                    url: Admin.url(path),
                    data: data,
                    cache: false,
                    processData: false,
                    contentType: false,
                    success: function(resp){
                        if(typeof(s) === "function")
                            s(resp);
                        if(resp.JSESSIONID)
                            sessionID=resp.JSESSIONID;
                        Admin.showInfo();
                    },
                    error: function(resp){
                        if(typeof(e) === "function")
                            e(resp);
                        Admin.showInfo();
                    }
                });

                Admin.xhr.push(ajax);
            },
            cakeForm: function(title){
              return '<div id="error"></div><div id="message"></div>' +
                  '<div class="small-padding">' +
                  '<form action="POST" id="form-cake">' +
                  '<div class="row">' +
                  '<div class="col-md-6" style="max-width: 400px">' +
                  '<h3 class="title">'+title+'</h3>' +
                  '<p class="subtitle">เพิ่มเค้กใหม่ลงหน้าร้าน</p>' +
                  '<div class="form-group">' +
                  '<label for="name">ชื่อเค้ก</label><input class="form-control" id="name" name="name" placeholder="คัพเค้กหน้าเป็ด, เค้กปาร์ตี้วันเกิด">' +
                  '</div>' +
                  '<div class="form-group">' +
                  '<label>รายละเอียด</label><textarea rows="5" class="form-control" name="detail" id="detail"></textarea>' +
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
                  '<label>ต้นทุน (บาท)</label><div class="input-group"><span class="input-group-addon">&#3647;</span><input type="number" id="cost" name="cost" class="form-control"/></div>' +
                  '</div>' +
                  '<div class="col-sm-6">' +
                  '<label>ราคาขายต่อหน่วย (บาท)</label><div class="input-group"><span class="input-group-addon">&#3647;</span><input type="number" id="price" name="price" class="form-control"/></div>' +
                  '</div>' +
                  '</div>' +
                  '</div>' +
                  '</div>' +
                  '<div class="col-md-6" style="max-width: 400px">' +
                  '<h3 class="title">ราคาพิเศษ</h3>' +
                  '<p class="subtitle">ราคาขายเมื่อลูกค้าซื้อตามจำนวนที่กำหนด</p>' +
                  '<div class="row" id="product_sale"></div>'+
                  '<div class="form-group">' +
                  '<label>&nbsp;</label><button class="btn btn-default form-control" id="add-sale">เพิ่มราคาพิเศษ</button>' +
                  '</div>'+
                  '</div>' +
                  '</div>' +
                  '</form>' +
                  '</div>';
            },
            cakeSaleForm: function(sale){
                if(!sale){
                    sale = {sale:{}};
                }
                return '<div class="form-group"><div class="col-md-4">' +
                    '<div class="form-group">' +
                    '<label>จำนวนชิ้น</label><input type="number" name="qty_sale" class="form-control" value="'+(sale.qty?sale.qty:"")+'"/>' +
                    '</div>' +
                    '</div>' +
                    '<div class="col-md-6">' +
                    '<div class="form-group">' +
                    '<label>ราคาขาย (บาท)</label>' +
                    '<div class="input-group"><span class="input-group-addon">&#3647;</span><input type="number" name="price_sale" class="form-control" value="'+(sale.price?sale.price:'')+'"/></div>' +
                    '</div></div><div class="col-md-2"><div class="form-group"><label>&nbsp;</label>'+
                    '<a '+ ((sale.id)?'id="sale-'+sale.id+'"':'')+
                    ' class="btn btn-default form-control"><span class="glyphicon glyphicon-trash"></span></a></div></div>';

            },
            loadCakeIndex: function(q){
                if(q) {
                    if (q === true){
                        q = "all";
                    }else
                        q = "q="+q;
                }

                actionBar.html('<div class="el"><button class="btn btn-default" id="refresh-cake">รีเฟรช</button></div><div class="el btn-group"><a href="#!/cakes/add" class="btn btn-primary" id="add-cake">เพิ่มเค้ก</a><button class="btn btn-danger disabled" id="delete-cake">ลบ</button></div><div class="el"><form id="form-search-cake"><div class="input-group" style="max-width: 400px"><input type="text" name="q" id="keyword" class="form-control" placeholder="ค้นหาตามชื่อเค้ก, คำอธิบาย" required><span class="input-group-btn"><button class="btn btn-default" id="search-cake" type="submit">ค้นหา</button></span></div></form></div>');

                $("#refresh-cake").click(function(){
                   Admin.onHashChanged();
                });

                $("#form-search-cake").submit(function(ev){
                    ev.preventDefault();

                    window.location.hash = "#!/cakes/search/"+($("#form-search-cake #keyword").val());
                    return false;
                });
                //get product table
                Admin.get("/product"+(q?"?"+q:""),function(resp){
                    if(!resp.error) {

                            var table = '<div id="error"></div><div id="message"></div><form id="form-cake"><table class="p6n-table">' +
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

                                if(resp.ProductList) {
                                    for (var idx in resp.ProductList) {
                                        var product = resp.ProductList[idx];
                                        table += '<tr>' +
                                            '<td>' +
                                            '<label class="ng-isolate-scope p6n-checkbox">' +
                                            '<input type="checkbox"  name="p_id" value="' + product.id + '" class="ng-valid ng-scope ng-dirty ng-valid-parse ng-touched">' +
                                            '</label>' +
                                            '</td>' +
                                            '<td><a href="#!/cakes/'+product.id+'">' + product.name + '</a></td>' +
                                            '<td>' + product.category.name + '</td>' +
                                            '<td class="text-right">' + Admin.toMoney(product.cost) + '</td>' +
                                            '<td class="text-right">' + Admin.toMoney(product.price) + '</td>' +
                                            '</tr>';
                                    }
                                }
                                else{
                                    table += '<tr><td colspan="5">ไม่พบเค้กใด ๆ ในระบบ</td></tr>';
                                }

                            table += '</table></form>';

                        if(q!="all")
                            table += '<a href="#!/cakes/all">ดูเต้กทั้งหมด</a>';

                        $(".content").html(table);

                            $("#delete-cake").click(function(){
                                Admin.post("/product/delete",$("#form-cake").serialize(),function(resp){
                                    if(!resp.error){
                                        msg = "ลบเค้กที่เลือกไว้เรียบร้อย";
                                        $("input[type=checkbox]:checked").parent().parent().parent().remove();
                                    }
                                    else
                                        error = resp;
                                });


                            });
                            $("input[type=checkbox]").on("change",function(ev){
                                var len = $("input[type=checkbox]:checked").length
                                if(len > 0)
                                    $("#delete-cake").removeClass("disabled");
                                else
                                    $("#delete-cake").addClass("disabled");

                            });

                    }
                }, true);

            },
            loadCakeAddForm: function(){
                actionBar.html('<div class="el"><a href="#!/cakes" class="btn btn-default">กลับ</a></div><div class="el"><button class="btn btn-primary" id="add-cake">บันทึก</button></div>');
                content.html(Admin.cakeForm("เพิ่มเค้ก"));

                $("#add-sale").click(function(ev){
                    ev.preventDefault();

                    var productSale = $("#product_sale");
                    var len = $("#product_sale>div").length;

                    if(len < 5 ) {
                        $(productSale).append(Admin.cakeSaleForm());
                        $("#product_sale>div a").click(function(){
                            $(this).parent().parent().parent().remove();
                        });
                    }

                    return false;
                });

                //submit button
                $("#add-cake").click(function(){
                    $("#form-cake").submit();
                });

                //submit form
                $("#form-cake").submit(function(){

                    Admin.post("/product",$("#form-cake").serialize(),function(resp){
                        if(!resp.error){
                            window.location.hash = "#!/cakes";
                            msg = "เพิ่ม "+resp.name+ " เรียบร้อย";

                        }else{
                            error = resp;
                        }
                        console.log(resp);
                    });


                    return false;
                })

                //upload picture
                $("#form-cake #pictures").on("change",function(ev){
                    var files = ev.target.files;
                    var data = new FormData();
                    $.each(files, function(key, value)
                    {
                        data.append("pictures", value);
                    });

                    Admin.upload("/picture",data,function(resp){
                        if(!resp.error){
                            if(resp.PictureList){
                                var result = '';
                                for(var idx in resp.PictureList){
                                    var picture = resp.PictureList[idx];
                                    result += '<div class="filename alert-dismissible" role="alert">'+picture.filename+'</div><input type="hidden" name="pic_id" value="'+picture.id+'"/>';
                                }
                                $("#form-cake #pic-list").html(result);
                            }

                        }
                    });
                });

                var categoryContainer = $("#category-container");
                var unitContainer = $("#unit-container");

                Admin.get("/category",function(resp){
                    if(!resp.error){
                        if(resp.CategoryList){
                            var select = '<select class="form-control" id="category" name="category">"';
                            for(var idx in resp.CategoryList){
                                var cat = resp.CategoryList[idx];

                                select += '<option value="'+cat.id+'">'+cat.name+'</option>';
                            }
                            select += "</select>";
                            categoryContainer.html(select);
                        }

                    }
                });

                Admin.get("/unit",function(resp){
                    if(!resp.error){
                        if(resp.UnitList){
                            var select = '<select class="form-control" id="unit" name="unit">"';
                            for(var idx in resp.UnitList){
                                var unit = resp.UnitList[idx];
                                select += '<option value="'+unit.id+'">'+unit.name+'</option>';
                            }
                            select += "</select>";
                            unitContainer.html(select);
                        }

                    }
                });




            },
            loadCakeById: function(id){
                Admin.get("/product/"+id,function(resp){
                    if(!resp.error && resp.Product){
                        actionBar.html('<div class="el"><a href="#!/cakes" class="btn btn-default">กลับ</a></div><div class="el"><button class="btn btn-primary" id="add-cake">บันทึก</button></div>');
                        content.html(Admin.cakeForm("แก้ไขเค้ก"));
                        $("#add-sale").click(function(ev){
                            ev.preventDefault();

                            var productSale = $("#product_sale");
                            var len = $("#product_sale>div").length;

                            if(len < 5 ) {
                                $(productSale).append(Admin.cakeSaleForm());
                                $("#product_sale>div a").click(function(){
                                    $(this).parent().parent().parent().remove();
                                });
                            }

                            return false;
                        });
                        $("#form-cake #pictures").on("change",function(ev){
                            var files = ev.target.files;
                            var data = new FormData();
                            $.each(files, function(key, value)
                            {
                                data.append("pictures", value);
                            });

                            Admin.upload("/picture",data,function(resp){
                                if(!resp.error){
                                    if(resp.PictureList){
                                        var result = '';
                                        for(var idx in resp.PictureList){
                                            var picture = resp.PictureList[idx];
                                            result += '<div class="filename alert-dismissible" role="alert">'+picture.filename+'</div><input type="hidden" name="pic_id" value="'+picture.id+'"/>';
                                        }
                                        $("#form-cake #pic-list").html(result);
                                    }

                                }
                            });
                        });
                        var categoryContainer = $("#category-container");
                        var unitContainer = $("#unit-container");
                        Admin.get("/category",function(resp){
                            if(!resp.error){
                                if(resp.CategoryList){
                                    var select = '<select class="form-control" id="category" name="category">"';
                                    for(var idx in resp.CategoryList){
                                        var cat = resp.CategoryList[idx];

                                        select += '<option value="'+cat.id+'">'+cat.name+'</option>';
                                    }
                                    select += "</select>";
                                    categoryContainer.html(select);
                                }

                            }
                        });
                        Admin.get("/unit",function(resp){
                            if(!resp.error){
                                if(resp.UnitList){
                                    var select = '<select class="form-control" id="unit" name="unit">"';
                                    for(var idx in resp.UnitList){
                                        var unit = resp.UnitList[idx];
                                        select += '<option value="'+unit.id+'">'+unit.name+'</option>';
                                    }
                                    select += "</select>";
                                    unitContainer.html(select);
                                }

                            }
                        });

                        //submit button
                        $("#add-cake").click(function(){
                            $("#form-cake").submit();
                        });

                        $("#form-cake").submit(function(){

                            Admin.post("/product/"+id,$("#form-cake").serialize(),function(resp){
                                if(!resp.error){
                                    window.location.hash = "#!/cakes";
                                    msg = "แก้ไข "+resp.name+ " เรียบร้อย";

                                }else{
                                    error = resp;
                                }
                                console.log(resp);
                            });


                            return false;
                        })

                        var product = resp.Product;
                        //Admin.loadCakeAddForm();
                        $("#form-cake #name").val(product.name);
                        $("#form-cake #detail").val(product.detail);
                        $("#form-cake #unit").val(product.unit.id);
                        $("#form-cake #category").val(product.category.id);
                        $("#form-cake #cost").val(product.cost);
                        $("#form-cake #price").val(product.price);
                        if(product.pictures)
                            for(var idx in product.pictures){
                                var picture = product.pictures[idx];
                                $("#form-cake #pic-list").html('<div class="filename alert-dismissible" role="alert">'+picture.filename+'</div><input type="hidden" name="pic_id" value="'+picture.id+'"/>');
                            }
                        if(product.sales) {
                            for(var idx in product.sales){
                                var sale = product.sales[idx];
                                $("#form-cake #product_sale").append(Admin.cakeSaleForm(sale));
                                $("#product_sale>div a").click(function(){
                                    $(this).parent().parent().parent().remove();
                                });
                            }


                        }

                    }
                },false);
            }
        };

    A = Admin;


    $(window).bind("hashchange",function(){
        Admin.onHashChanged();
    });

    if(window.location.hash){
        Admin.onHashChanged();
    }
});