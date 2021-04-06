$(function() {
    //时间设置
    function currentTime() {
        var d = new Date();
        var str = '';
        str += d.getFullYear() + '年';
        str += d.getMonth() + 1 + '月';
        str += d.getDate() + '日';
        str += d.getHours() + '时';
        str += d.getMinutes() + '分';
        str += d.getSeconds() + '秒';
        return str;
    }

    setInterval(function()
    {
        $('#nowTime').html(currentTime)
    }, 1000);
});
/*后台用户逐注销*/
var bgLogoutUrl = '/user/bg/logout';
function bgLogout() {
    bootbox.confirm({
        title: '登出确认',
        message: '您是否确认登出系统？',
        size: 'small',
        buttons: {
            cancel: {
                label: '返回'
            },
            confirm: {
                label: '确认'
            }
        },
        callback: function (result) {
            if(result){
                window.location.href = bgLogoutUrl;
            }
        }
    });
}