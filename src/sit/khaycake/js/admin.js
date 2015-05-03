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
            },


            loadCakeIndex: function(q) {



            },
            loadCakeAddForm: function() {




                //submit button
                $("#add-cake").click(function() {
                    $("#form-cake").submit();
                });

                //submit form
                $("#form-cake").submit(function() {

                    Admin.post("/product", $("#form-cake").serialize(), function(resp) {
                        if (!resp.error) {
                            window.location.hash = "#!/cakes";
                            msg = "เพิ่ม " + resp.message.name + " เรียบร้อย";

                        } else {
                            error = resp;
                        }
                        console.log(resp);
                    });


                    return false;
                });

                //upload picture


                var categoryContainer = $("#category-container");
                var unitContainer = $("#unit-container");

                Admin.get("/category", function(resp) {
                    if (!resp.error) {
                        if (resp.CategoryList) {
                            var select = '<select class="form-control" id="category" name="category">"';
                            for (var idx in resp.CategoryList) {
                                var cat = resp.CategoryList[idx];

                                select += '<option value="' + cat.id + '">' + cat.name + '</option>';
                            }
                            select += "</select>";
                            categoryContainer.html(select);
                        }

                    }
                });

                Admin.get("/unit", function(resp) {
                    if (!resp.error) {
                        if (resp.UnitList) {
                            var select = '<select class="form-control" id="unit" name="unit">"';
                            for (var idx in resp.UnitList) {
                                var unit = resp.UnitList[idx];
                                select += '<option value="' + unit.id + '">' + unit.name + '</option>';
                            }
                            select += "</select>";
                            unitContainer.html(select);
                        }

                    }
                });




            },
            loadCakeById: function(id) {
                Admin.get("/product/" + id, function(resp) {
                    console.log(resp);
                    if (resp.success && resp.message) {
                        actionBar.html('<div class="el"><a href="#!/cakes" class="btn btn-default">กลับ</a></div><div class="el"><button class="btn btn-primary" id="add-cake">บันทึก</button></div>');
                        content.html(Admin.cakeForm("แก้ไขเค้ก"));
                        $("#add-sale").click(function(ev) {
                            ev.preventDefault();

                            var productSale = $("#product_sale");
                            var len = $("#product_sale>div").length;

                            if (len < 5) {
                                $(productSale).append(Admin.cakeSaleForm());
                                $("#product_sale>div a").click(function() {
                                    $(this).parent().parent().parent().remove();
                                });
                            }

                            return false;
                        });
                        $("#form-cake #pictures").on("change", function(ev) {
                            var files = ev.target.files;
                            var data = new FormData();
                            $.each(files, function(key, value) {
                                data.append("pictures", value);
                            });

                            Admin.upload("/picture", data, function(resp) {
                                if (!resp.error) {
                                    if (resp.PictureList) {
                                        var result = '';
                                        for (var idx in resp.PictureList) {
                                            var picture = resp.PictureList[idx];
                                            result += '<div class="filename alert-dismissible" role="alert">' + picture.filename + '</div><input type="hidden" name="pic_id" value="' + picture.id + '"/>';
                                        }
                                        $("#form-cake #pic-list").html(result);
                                    }

                                }
                            });
                        });
                        var categoryContainer = $("#category-container");
                        var unitContainer = $("#unit-container");
                        Admin.get("/category", function(resp) {
                            if (!resp.error) {
                                if (resp.CategoryList) {
                                    var select = '<select class="form-control" id="category" name="category">"';
                                    for (var idx in resp.CategoryList) {
                                        var cat = resp.CategoryList[idx];

                                        select += '<option value="' + cat.id + '">' + cat.name + '</option>';
                                    }
                                    select += "</select>";
                                    categoryContainer.html(select);
                                }

                            }
                        });
                        Admin.get("/unit", function(resp) {
                            if (!resp.error) {
                                if (resp.UnitList) {
                                    var select = '<select class="form-control" id="unit" name="unit">"';
                                    for (var idx in resp.UnitList) {
                                        var unit = resp.UnitList[idx];
                                        select += '<option value="' + unit.id + '">' + unit.name + '</option>';
                                    }
                                    select += "</select>";
                                    unitContainer.html(select);
                                }

                            }
                        });

                        //submit button
                        $("#add-cake").click(function() {
                            $("#form-cake").submit();
                        });

                        $("#form-cake").submit(function() {

                            Admin.post("/product/" + id, $("#form-cake").serialize(), function(resp) {
                                if (!resp.error) {
                                    window.location.hash = "#!/cakes";
                                    msg = "แก้ไข " + resp.name + " เรียบร้อย";

                                } else {
                                    error = resp;
                                }
                                console.log(resp);
                            });


                            return false;
                        })

                        var product = resp.message;
                        //Admin.loadCakeAddForm();
                        $("#form-cake #name").val(product.name);
                        $("#form-cake #detail").val(product.detail);
                        $("#form-cake #unit").val(product.unit.id);
                        $("#form-cake #category").val(product.category.id);
                        $("#form-cake #cost").val(product.cost);
                        $("#form-cake #price").val(product.price);
                        if (product.pictures)
                            for (var idx in product.pictures) {
                                var picture = product.pictures[idx];
                                $("#form-cake #pic-list").html('<div class="filename alert-dismissible" role="alert">' + picture.filename + '</div><input type="hidden" name="pic_id" value="' + picture.id + '"/>');
                            }
                        if (product.sales) {
                            for (var idx in product.sales) {
                                var sale = product.sales[idx];
                                $("#form-cake #product_sale").append(Admin.cakeSaleForm(sale));
                                $("#product_sale>div a").click(function() {
                                    $(this).parent().parent().parent().remove();
                                });
                            }


                        }

                    }
                }, false);
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
            default:
                section = "dashboard";
                Admin.tabDashBoard();
                break;
        }



        $(".app-nav a").removeClass("active");
        $(".app-nav a#" + section).addClass("active");



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



        if (!(method)) { //list all cake
            Admin.loadingMask();
            Product.all(function(resp) {
                $(actionBar).html(actionBarContent);
                Admin.tabCake.table(resp.message);
                Admin.tabCake.bind();
                Admin.loadedMask();
            });
        } else if (method) {
            if (isInteger(method)) { //get cake by id
                Admin.loadingMask();
                Product.find(method,function(resp) {
                    var product = resp.message;
                    actionBarContent = '<div class="el"><a href="#!/cakes" class="btn btn-default">กลับ</a></div><div class="el"><button class="btn btn-primary" id="update-cake">บันทึก</button></div>';
                    $(actionBar).html(actionBarContent);
                    $(content).html(Product.form("แก้ไขเค้กใหม่",product));
                    Unit.box(function(unitBox){
                        $("#unit-container").html(unitBox);
                        $("#unit-container select").val(product.unit.id);
                    });
                    Category.box(function(catBox){
                        $("#category-container").html(catBox);
                        $("#category-container select").val(product.category.id);
                    });

                    Product.pictures(method,function(resp){
                        var html = '';
                        for(var idx in resp.message){
                            var picture = resp.message[idx];
                            html += (Product.form.picture(picture));
                        }
                        $("#form-cake #pic-list").html(html);

                    });

                    Admin.tabCake.bind(method);
                    Admin.loadedMask();
                });

            } else {
                if (method === "create") {
                    actionBarContent = '<div class="el"><a href="#!/cakes" class="btn btn-default">กลับ</a></div><div class="el"><button class="btn btn-primary" id="save-cake">บันทึก</button></div>';
                    $(actionBar).html(actionBarContent);
                    $(content).html(Product.form("เพิ่มเค้กใหม่"));
                    Unit.box(function(unitBox){
                        $("#unit-container").html(unitBox);
                    });
                    Category.box(function(catBox){
                        $("#category-container").html(catBox);
                    });

                    Admin.tabCake.bind();
                } else if (method === "search") {
                    Product.find(param, function(resp) {
                        $(actionBar).html(actionBarContent);
                        $("#form-search-cake #keyword").val(param);
                        Admin.tabCake.table(resp.message);
                        Admin.tabCake.bind();
                        Admin.loadedMask();
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
        $("input[type=checkbox]").on("change", function(ev) {
            var len = $("input[type=checkbox]:checked").length;
            if (len > 0)
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
            Admin.loadingMask();
            Product.delete($("#form-cake"),function(){
                Admin.loadedMask();
                alert("ลบข้อมูลเรียบร้อย");
                Admin.onHashChanged(); //refresh
            });
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

    $(window).bind("hashchange", function() {
        Admin.onHashChanged();
    });

    if (window.location.hash) {
        Admin.onHashChanged();
    }
});