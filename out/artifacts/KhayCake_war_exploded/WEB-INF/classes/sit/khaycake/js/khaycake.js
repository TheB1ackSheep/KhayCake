var K;
$(document).ready(function() {
    var dialog = $(".dialog");
    var dialogContent = $(".dialog-content");
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

    KhayCake.onHashChanged = function() {
        var section = window.location.hash.substr(2);
        var subSection = section.split('/')[1];

        switch (subSection) {
            case 'shop':
                Shop.open();
                break;
            case 'login':
                Login.open();
                break;
            default:
                Shop.close();
                break;
        }
    };



    var Dialog = Dialog || {
            PARAM_BEGIN_POS: 'begin-pos',
            getDialog: function() {
                return $(".dialog");
            },
            getBeginPos: function() {
                return KhayCake.getParam(this.PARAM_BEGIN_POS);
            }
        };

    Dialog.isOpen = function() {
        return section.hasClass("open");
    };
    Dialog.open = function(fn) {
        if (!this.isOpen()) {
            section.addClass("open");

            var beginPos = this.getBeginPos();
            if (beginPos) {
                var b = beginPos.getBoundingClientRect();
                var c = beginPos.parentNode.parentNode.parentNode.getBoundingClientRect();
                dialog.css({
                    top: b.top - c.top + 'px',
                    left: b.left - c.left + 'px',
                    backgroundColor: "#3E2733"
                });
            } else {
                dialog.css({
                    top: '50%',
                    left: '50%',
                    backgroundColor: "#3E2733"
                });
            }

            dialogContent.css({
                opacity: 0
            });

            dialog.animate({
                width: '100%',
                height: '100%',
                top: 0,
                left: 0,
                backgroundColor: "#fff"
            }, 200, function() {
                dialogContent.animate({
                    opacity: 1
                });
                if (typeof(fn) === "function")
                    fn();
            });
        }
    };
    Dialog.setContent = function(content){
        dialogContent.html(content);
    };
    Dialog.close = function(fn) {
        if (this.isOpen()) {
            section.removeClass("open");

            var beginPos = this.getBeginPos();
            var size;
            if (beginPos) {
                var b = beginPos.getBoundingClientRect();
                var c = beginPos.parentNode.parentNode.parentNode.getBoundingClientRect();
                size = {
                    width: b.width,
                    height: b.height,
                    top: b.top - c.top + 'px',
                    left: b.left - c.left + 'px',
                    backgroundColor: "#3E2733"
                };
            } else {
                size = {
                    width: 0,
                    height: 0,
                    top: '50%',
                    left: '50%',
                    backgroundColor: "#fff"
                };
            }

            dialogContent.animate({
                opacity: 0
            }, 100, function() {
                dialog.animate({
                    width: size.width,
                    height: size.height,
                    top: size.top,
                    left: size.left,
                    backgroundColor: size.backgroundColor
                }, 350, function() {
                    dialog.attr('style', '');
                    if (typeof(fn) === "function")
                        fn();
                });
            });

        }
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

    var Shop = Shop || {
            CN_NAV: '.dialog .nav',
            CN_NAV_LIST: '.dialog .nav li',
            ID_CAKE_CONTAINER: '#cake-container',
            getContainer: function(cakes) {
                var cakeList = "";
                for (var idx in cakes) {
                    cakeList += this.getCakeBox(cakes[idx]);
                }
                return '<div class="row">' +
                    '<div class="col-md-2">' +
                    '<ul class="nav nav-pills nav-stacked">' +
                    '<li role="presentation" id="cupcake"><a href="#!/shop/cupcake">Cupcake</a></li>' +
                    '<li role="presentation" id="crapecake"><a href="#!/shop/crapecake">Crape Cake</a></li>' +
                    '<li role="presentation" id="brownie"><a href="#!/shop/brownie">Brownie</a></li>' +
                    '<li role="presentation" id="partycake"><a href="#!/shop/partycake">Party Cake</a></li>' +
                    '</ul>' +
                    '</div>' +
                    '<div class="col-md-10">' +
                    '<div id="cake-container" class="row">' + cakeList + '</div>' +
                    '</div>' +
                    '</div>';
            },
            getCakeBox: function(cake) {
                return '<div class="col-md-3 col-sm-4 col-xs-6">' +
                    '<div class="box inactive">' +
                    '<div class="box-img">' +
                    '<img src="'+HOST+'/product/'+cake.id+'/picture" alt=""/>' +
                    '</div>' +
                    '<div class="box-name">' + cake.name + '</div>' +
                    '<div class="box-button">' +
                    '<div class="price">' + cake.price + ' บาท/' + cake.unit.name + '</div>' +
                    '<button class="cart">Add to Cart</button>' +
                    '</div>' +
                    '</div>' +
                    '</div>';
            }
        };

    Shop.open = function() {
        var hash = window.location.hash.substr(2);
        var resources = hash.split('/');
        var cat = null;
        if (resources.length >= 3)
            cat = resources[2];

        var cakes = null;
        switch (cat) {
            case 'cupcake':
                Product.cupcake(function(resp) {
                    if (resp.success) {
                        if (resp.message && resp.message.length > 0) {
                            Dialog.open();
                            Dialog.setContent(Shop.getContainer(resp.message));
                            Shop.tab();
                        } else {
                            //There are no cakes
                        }
                    } else {
                        //On Error 500
                    }
                });
                break;
            case 'crapecake':
                Product.crapecake(function(resp) {
                    if (resp.success) {
                        if (resp.message && resp.message.length > 0) {
                            Dialog.open();
                            Dialog.setContent(Shop.getContainer(resp.message));
                            Shop.tab();
                        } else {
                            //There are no cakes
                        }
                    } else {
                        //On Error 500
                    }
                });
                break;
            case 'brownie':
                Product.brownie(function(resp) {
                    if (resp.success) {
                        if (resp.message && resp.message.length > 0) {
                            Dialog.open();
                            Dialog.setContent(Shop.getContainer(resp.message));
                            Shop.tab();
                        } else {
                            //There are no cakes
                        }
                    } else {
                        //On Error 500
                    }
                });
                break;
            case 'partycake':
                Product.partycake(function(resp) {
                    if (resp.success) {
                        if (resp.message && resp.message.length > 0) {
                            Dialog.open();
                            Dialog.setContent(Shop.getContainer(resp.message));
                            Shop.tab();
                        } else {
                            //There are no cakes
                        }
                    } else {
                        //On Error 500
                    }
                });
                break;
            default:
                Product.all(function(resp) {
                    if (resp.success) {
                        if (resp.message && resp.message.length > 0) {
                            Dialog.open();
                            Dialog.setContent(Shop.getContainer(resp.message));
                            Shop.tab();
                        } else {
                            //There are no cakes
                        }
                    } else {
                        //On Error 500
                    }
                });
                break;
        }






    };
    Shop.close = function() {
        Dialog.close();
    };
    Shop.tab = function(){
        var hash = window.location.hash.substr(2);
        var resources = hash.split('/');
        var cat = null;
        if (resources.length >= 3)
            cat = resources[2];

        $(Shop.CN_NAV_LIST).removeClass("active");
        $(Shop.CN_NAV_LIST + "#" + cat).addClass("active");
    };

    var Login = Login || {
            ID_FORM_NAME: 'form-login',
            getForm: function() {
                return '<form id="' + this.ID_FORM_NAME + '">' +
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
        };
    Login.open = function() {
        Dialog.open(Login.getForm());
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