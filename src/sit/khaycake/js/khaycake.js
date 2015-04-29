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
            }
        };

    KhayCake.onHashChanged = function() {
        var section = window.location.hash.substr(2);
        var subSection = section.split('/')[1];

        switch (subSection) {
            case 'shop':
                Shop.open();
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
    Dialog.open = function() {
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
            });
        }
    };
    Dialog.close = function() {
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
            },100,function(){
                dialog.animate({
                    width: size.width,
                    height: size.height,
                    top: size.top,
                    left: size.left,
                    backgroundColor: size.backgroundColor
                },350,function(){
                    dialog.css({});
                });
            });

        }
    };

    var Shop = Shop || {
            CN_NAV: '.dialog .nav',
            CN_NAV_LIST: '.dialog .nav li',
            getContainer: function(){
                return	'<div class="row">'+
                    '<div class="col-md-2">'+
                    '<ul class="nav nav-pills nav-stacked">'+
                    '<li role="presentation" id="cupcake" class=""><a href="#!/shop/cupcake">Cupcake</a></li>'+
                    '<li role="presentation" id="crapecake" class="active"><a href="#!/shop/crapecake">Crape Cake</a></li>'+
                    '<li role="presentation" id="brownie" class=""><a href="#!/shop/brownie">Brownie</a></li>'+
                    '<li role="presentation" id="partycake"><a href="#!/shop/partycake">Party Cake</a></li>'+
                    '</ul>'+
                    '</div>'+
                    '<div class="col-md-10">'+
                    '<div id="cake-container" class="row">'+
                    '</div>'+
                    '</div>'+
                    '</div>';
            },
            getCakeBox: function(e){
                if(e.cake){
                    return	'<div class="col-md-3 col-sm-4 col-xs-6">'+
                        '<div class="box inactive">'+
                        '<div class="box-img">'+
                        '<img src="" alt="">'+
                        '</div>'+
                        '<div class="box-name">'+e.cake.name+'</div>'+
                        '<div class="box-button">'+
                        '<div class="price">'+e.cake.price+' ???/'+e.cake.unit.name+'</div>'+
                        '<button class="cart">Add to Cart</button>'+
                        '</div>'+
                        '</div>'+
                        '</div>'
                }
            }
        };
    Shop.open = function() {
        Dialog.open();
        Shop.tab();
    };
    Shop.close = function() {
        Dialog.close();
    };
    Shop.tab = function() {
        var section = window.location.hash.substr(2);
        var hash = section.split('/')[2];

        $(Shop.CN_NAV_LIST).removeClass("active");
        $(Shop.CN_NAV_LIST+ "#" + hash).addClass("active");
    };

    var Login = Login || {
            ID_FORM_NAME: 'form-login',
            getForm: function() {
                return '<form id="' + ID_FORM_NAME + '">' +
                    '<div class="form-group">' +
                    '<label>Username :<label>' +
                    '<input class="form-control" id="email" placholder="ninecake@khaycake.com"/>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label>Password :<label>' +
                    '<input class="form-control" id="pwd" type="password"/>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<button class="btn btn-primary">Login</button>' +
                    '</div>' +
                    '</form>';
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
});