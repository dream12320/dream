//发布商品参数校验
var typeId = "";

function checkGoodsParam() {
    //商品类型
    typeId = $("#goodsType").children('option:selected').val();
    var typeName = $("#goodsType").find('option:selected').text();
    if (typeName == '请选择商品类型') {
        $('#typeIdErrorMsg').html("商品类型为空.");
        $("#goodsType").focus();//获得焦点

        $("#typeIdErrorMsg").css('display', 'block');//显示
        $("#goodsNameErrorMsg").css('display', 'none');//不显示
        $("#goodsPriceErrorMsg").css('display', 'none');//不显示
        $("#goodsPictureErrorMsg").css('display', 'none');//不显示
        $("#goodsDescribeErrorMsg").css('display', 'none');//不显示
        return true;
    }
    //商品昵称
    if ($("#goodsName").val().length == 0) {
        $('#goodsNameErrorMsg').html("商品昵称为空.");
        $("#goodsName").focus();//获得焦点

        $("#goodsNameErrorMsg").css('display', 'block');//显示
        $("#typeIdErrorMsg").css('display', 'none');//不显示
        $("#goodsPriceErrorMsg").css('display', 'none');//不显示
        $("#goodsPictureErrorMsg").css('display', 'none');//不显示
        $("#goodsDescribeErrorMsg").css('display', 'none');//不显示
        return true;
    }
    //商品价格
    if ($("#goodsPrice").val().length == 0) {
        $('#goodsPriceErrorMsg').html("商品价格为空.");
        $("#goodsPrice").focus();//获得焦点

        $("#goodsPriceErrorMsg").css('display', 'block');//显示
        $("#typeIdErrorMsg").css('display', 'none');//不显示
        $("#goodsNameErrorMsg").css('display', 'none');//不显示
        $("#goodsPictureErrorMsg").css('display', 'none');//不显示
        $("#goodsDescribeErrorMsg").css('display', 'none');//不显示
        return true;
    }
    //宝贝图片
    if ($("#goodsPicture").val().length == 0) {
        $('#goodsPictureErrorMsg').html("宝贝图片为空.");
        $("#goodsPicture").focus();//获得焦点

        $("#goodsPictureErrorMsg").css('display', 'block');//显示
        $("#typeIdErrorMsg").css('display', 'none');//不显示
        $("#goodsNameErrorMsg").css('display', 'none');//不显示
        $("#goodsPriceErrorMsg").css('display', 'none');//不显示
        $("#goodsDescribeErrorMsg").css('display', 'none');//不显示
        return true;
    }
    //商品描述
    if ($("#content").val().length == 0) {
        $('#goodsDescribeErrorMsg').html("商品描述为空.");
        $("#content").focus();//获得焦点

        $("#goodsDescribeErrorMsg").css('display', 'block');//显示
        $("#typeIdErrorMsg").css('display', 'none');//不显示
        $("#goodsNameErrorMsg").css('display', 'none');//不显示
        $("#goodsPictureErrorMsg").css('display', 'none');//不显示
        $("#goodsPriceErrorMsg").css('display', 'none');//不显示
        return true;
    } else if ($("#content").val().length < 10) {
        $('#goodsDescribeErrorMsg').html("商品描述内容至少10字.");
        $("#content").focus();//获得焦点

        $("#goodsDescribeErrorMsg").css('display', 'block');//显示
        $("#typeIdErrorMsg").css('display', 'none');//不显示
        $("#goodsNameErrorMsg").css('display', 'none');//不显示
        $("#goodsPictureErrorMsg").css('display', 'none');//不显示
        $("#goodsPriceErrorMsg").css('display', 'none');//不显示
        return true;
    } else if ($("#content").val().length > 500) {
        $('#goodsDescribeErrorMsg').html("商品描述内容不超过500字.");
        $("#content").focus();//获得焦点

        $("#goodsDescribeErrorMsg").css('display', 'block');//显示
        $("#typeIdErrorMsg").css('display', 'none');//不显示
        $("#goodsNameErrorMsg").css('display', 'none');//不显示
        $("#goodsPictureErrorMsg").css('display', 'none');//不显示
        $("#goodsPriceErrorMsg").css('display', 'none');//不显示
        return true;
    }
    return false;
}

//发布商品
function publish() {
    //校验
    if (checkGoodsParam()) {
        return false;
    }
    var param = $('#publish_form').serializeArray();
    var goodsPicture = {name: "goodsPicture", value: $("#goodsPicture").val()};
    var pictureUrl = {name: "pictureUrl", value: $('#pictureUrl').attr('src')};
    typeId = {name: "typeId", value: typeId};
    param.push(goodsPicture);
    param.push(typeId);
    param.push(pictureUrl);
    $.ajax({
        type: "POST",//方法类型
        dataType: "text",//预期服务器返回的数据类型
        url: "/goods/success",//url
        data: param,
        success: function (data) {
            if (data == "success") {
                bootbox.dialog({
                    title: '发布成功',
                    message: '<div style="line-height: 30px"><span>商品发布成功，是否返回查看！</span></div>',
                    size: 'small',
                    buttons: {
                        cancel: {
                            label: '稍后返回',
                        },
                        confirm: {
                            label: '立即返回',
                            callback: function () {
                                window.location.href = '/goods/getList/' + typeId.value + '/1';
                            }
                        }
                    }
                })
            }
        },
        error: function () {
            alert("发布商品系统异常！");
            window.location.href = '/goods/publish';
        }
    });
}