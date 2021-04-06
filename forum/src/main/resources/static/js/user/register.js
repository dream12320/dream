//用户注册参数校验
function checkRegisterParam() {
    //昵称
    if ($("#nickname").val().length == 0) {
        $('#nicknameErrorMsg').html("用户昵称为空.");
        $("#nickname").focus();//获得焦点

        $("#nicknameErrorMsg").css('display','block');//显示
        $("#emailErrorMsg").css('display','none');//不显示
        $("#passwordErrorMsg").css('display','none');//不显示
        return true;
    }
    //账号邮箱
    var pattern=/^[A-Za-z0-9]+([-_.][A-Za-z0-9]+)*@([A-Za-z0-9]+[-.])+[A-Za-z0-9]{2,5}$/
    if ($("#email").val().length == 0) {
        $('#emailErrorMsg').html("用户账号为空.");
        $("#email").focus();//获得焦点

        $("#emailErrorMsg").css('display','block');//显示
        $("#nicknameErrorMsg").css('display','none');//不显示
        $("#passwordErrorMsg").css('display','none');//不显示
        return true;
    }else if((pattern.test($("#email").val()))==false){
        $('#emailErrorMsg').html("用户邮箱输入格式错误.");
        $("#email").focus();//获得焦点

        $("#emailErrorMsg").css('display','block');//显示
        $("#nicknameErrorMsg").css('display','none');//不显示
        $("#passwordErrorMsg").css('display','none');//不显示
        return true;
    }
    //密码
    if ($("#password").val().length == 0) {
        $('#passwordErrorMsg').html("用户密码为空.");
        $("#password").focus();//获得焦点

        $("#passwordErrorMsg").css('display','block');//显示
        $("#nicknameErrorMsg").css('display','none');//不显示
        $("#emailErrorMsg").css('display','none');//不显示
        return true;
    }else  if ($("#password").val().length <5) {
        $('#passwordErrorMsg').html("用户密码长度不小于六位.");
        $("#password").focus();//获得焦点

        $("#passwordErrorMsg").css('display','block');//显示
        $("#nicknameErrorMsg").css('display','none');//不显示
        $("#emailErrorMsg").css('display','none');//不显示
        return true;
    }
    return false;
}
//用户注册
function register() {
    //校验
    if(checkRegisterParam()){
        return false;
    }
    var param=$('#register_form').serializeArray();
    var image={name:"image",value:$("#image").val()};
    param.push(image);
    $.ajax({
        type: "POST",//方法类型
        dataType: "text",//预期服务器返回的数据类型
        url: "/user/backToLogin" ,//url
        data: param,
        success: function (data) {
            if (data == "success") {
                window.location.href = '/user/login';
            }else if(data=="fail"){
                bootbox.dialog({
                    title:'注册失败',
                    message:'<div style="line-height: 30px"><span>注册失败,此邮箱已经注册过！</span></div>',
                    size: 'small',
                    buttons:{
                        confirm: {
                            label:'确定',
                        }
                    }
                })
            }
        },
        error : function() {
            alert("系统异常！");
            window.location.href = '/user/login';
        }
    });
}
//注册重置按钮
function reset(){
    $("#nickname").val("");
    $("#email").val("");
    $("#password").val("");
    $("#image").val("");
    $("#signature").val("");
}