function checkCommentParam() {
    //帖子评论参数
    var comment=$("#comment").val();
    if (comment.length == 0) {
        $('#commentErrorMsg').html("帖子评论为空.");
        $("#comment").focus();//获得焦点
        $("#commentErrorMsg").css('display','block');//显示
        return true;
    }else if (comment.length < 5) {
        $('#commentErrorMsg').html("帖子评论至少5个字.");
        $("#comment").focus();//获得焦点
        $("#commentErrorMsg").css('display','block');//显示
        return true;
    }else if (comment.length > 500) {
        $('#commentErrorMsg').html("帖子评论最多500字.");
        $("#comment").focus();//获得焦点
        $("#commentErrorMsg").css('display','block');//显示
        return true;
    }
    return false;
}
//在帖子详情页面（填写评论）
function postComment(flag) {
    //校验
    if(checkCommentParam()){
        return false;
    }
    var param=$('#comment_form').serializeArray();
    //把帖子评论内容的空格和换行转换以便保存数据库后回显
    var comment=$("#comment").val();
    var reg=new RegExp("\n","g");
    var regSpace=new RegExp(" ","g");
    comment = comment.replace(reg,"<br/>");
    comment = comment.replace(regSpace,"&nbsp;");
    var comments={name:"commentContent",value:comment};
    param.push(comments);
    var postId=$("#postId").val();
    $.ajax({
        type: "POST",//方法类型
        dataType: "text",//预期服务器返回的数据类型
        url: "/post/comment" ,//url
        data: param,
        success: function (data) {
            if(data=="success"){
                window.location.href = '/post/getDetail/'+flag+'/'+postId +'/1';
            }
        },
        error : function() {
            alert("评论帖子系统异常！");
            window.location.href = '/post/getDetail/'+flag+'/'+postId +'/1';
        }
    });
}
//改变偶数行背景色
$(function () {
    $("table tr:even td").css("background-color", "WhiteSmoke");
})