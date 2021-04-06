//前台用户登录
function login() {
    $.ajax({
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: "/user/validate" ,//url登陆验证
        data: $('#login_form').serialize(),
        success: function (data) {
            if (data.result == "success") {
                window.location.href = '/user/redirect';
            }else if(data.result == "userNameIsNull"){
                $('#errorMsg').html("请输入用户名.");
                $("#errorMsg").css('display','block');//显示
            }else if(data.result == "passwordIsNull"){
                $('#errorMsg').html("请输入密码.");
                $("#errorMsg").css('display','block');//显示
            }else{
                $("#errorMsg").html("用户账号或者密码有误.");
                $("#errorMsg").css('display','block');//显示
            }
        },
        error : function() {
            alert("系统异常！");
        }
    });
}
//后台用户登录
function bgLogin() {
    $.ajax({
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: "/user/bg/validate" ,//url登陆验证
        data: $('#login_form').serialize(),
        success: function (data) {
            if (data.result == "success") {
                window.location.href = '/user/bg/redirect';
            }else if(data.result == "userNameIsNull"){
                $('#errorMsg').html("请输入用户名.");
                $("#errorMsg").css('display','block');//显示
            }else if(data.result == "passwordIsNull"){
                $('#errorMsg').html("请输入密码.");
                $("#errorMsg").css('display','block');//显示
            }else{
                $("#errorMsg").html("用户账号或者密码有误.");
                $("#errorMsg").css('display','block');//显示
            }
        },
        error : function() {
            alert("系统异常！");
        }
    });
}