//发布帖子参数校验
var typeId="";
function checkMessageParam() {
    //帖子类型
    typeId=$("#postType").children('option:selected').val();
    var typeName=$("#postType").find('option:selected').text();
    if (typeName == '请选择帖子类型') {
        $('#typeIdErrorMsg').html("帖子类型为空.");
        $("#postType").focus();//获得焦点

        $("#typeIdErrorMsg").css('display','block');//显示
        $("#themeErrorMsg").css('display','none');//不显示
        $("#contentErrorMsg").css('display','none');//不显示
        return true;
    }
    //帖子题目
    if ($("#theme").val().length == 0) {
        $('#themeErrorMsg').html("帖子题目为空.");
        $("#theme").focus();//获得焦点

        $("#themeErrorMsg").css('display','block');//显示
        $("#typeIdErrorMsg").css('display','none');//不显示
        $("#contentErrorMsg").css('display','none');//不显示
        return true;
    }
    //帖子内容
    if ($("#content").val().length == 0) {
        $('#contentErrorMsg').html("帖子内容为空.");
        $("#content").focus();//获得焦点

        $("#contentErrorMsg").css('display','block');//显示
        $("#typeIdErrorMsg").css('display','none');//不显示
        $("#themeErrorMsg").css('display','none');//不显示
        return true;
    }else  if ($("#content").val().length <20) {
        $('#contentErrorMsg').html("帖子内容至少20字.");
        $("#content").focus();//获得焦点

        $("#contentErrorMsg").css('display','block');//显示
        $("#typeIdErrorMsg").css('display','none');//不显示
        $("#themeErrorMsg").css('display','none');//不显示
        return true;
    }else  if ($("#content").val().length >2000) {
        $('#contentErrorMsg').html("帖子内容不能超过2000字.");
        $("#content").focus();//获得焦点

        $("#contentErrorMsg").css('display','block');//显示
        $("#typeIdErrorMsg").css('display','none');//不显示
        $("#themeErrorMsg").css('display','none');//不显示
        return true;
    }
    return false;
}
//发布帖子
function post() {
    //校验
    if(checkMessageParam()){
        return false;
    }
    var param=$('#messages_form').serializeArray();
    typeId={name:"typeId",value:typeId};
    param.push(typeId);
    //把帖子内容的空格和换行转换以便保存数据库后回显
    var content=$("#content").val();
    var reg=new RegExp("\n","g");
    var regSpace=new RegExp(" ","g");
    content = content.replace(reg,"<br/>");
    content = content.replace(regSpace,"&nbsp;");
    var contents={name:"content",value:content};
    param.push(contents);
    $.ajax({
        type: "POST",//方法类型
        dataType: "text",//预期服务器返回的数据类型
        url: "/post/success" ,//url
        data: param,
        success: function (data) {
            if (data == "success") {
                bootbox.dialog({
                    title:'发布成功',
                    message:'<div style="line-height: 30px"><span>帖子发布成功，是否返回查看！</span></div>',
                    size: 'small',
                    buttons:{
                        cancel:{
                            label:'稍后返回',
                        },
                        confirm: {
                            label:'立即返回',
                            callback: function(){
                                window.location.href = '/post/getList/'+typeId.value+'/1';
                            }
                        }
                    }
                })
            }
        },
        error : function() {
            alert("发布帖子系统异常！");
            window.location.href = '/post/message';
        }
    });
}