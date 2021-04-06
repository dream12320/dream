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
//前台用户注销
var logoutUrl = '/user/logout';
function logout() {
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
                window.location.href = logoutUrl;
            }
        }
    });
}
//页面雪花效果
(function ($) {
    $.fn.snow = function (options) {
        var $flake = $('<div id="snowbox" />').css({
                'position': 'absolute',
                'z-index': '9999',
                'top': '-50px',
                'cursor': 'pointer'
            }).html('❄'),
            documentHeight = $(document).height(),
            documentWidth = $(document).width(),
            defaults = {
                minSize: 10,
                maxSize: 20,
                newOn: 1000,
                flakeColor: "#AFDAEF" /* 此处可以定义雪花颜色，若要白色可以改为#FFFFFF */
            },
            options = $.extend({}, defaults, options);
        var interval = setInterval(function () {
            var startPositionLeft = Math.random() * documentWidth - 100,
                startOpacity = 0.5 + Math.random(),
                sizeFlake = options.minSize + Math.random() * options.maxSize,
                endPositionTop = documentHeight - 200,
                endPositionLeft = startPositionLeft - 500 + Math.random() * 500,
                durationFall = documentHeight * 10 + Math.random() * 5000;
            $flake.clone().appendTo('body').css({
                left: startPositionLeft,
                opacity: startOpacity,
                'font-size': sizeFlake,
                color: options.flakeColor
            }).animate({
                top: endPositionTop,
                left: endPositionLeft,
                opacity: 0.2
            }, durationFall, 'linear', function () {
                $(this).remove()
            });
        }, options.newOn);
    };
})(jQuery);
$(function () {
    $.fn.snow({
        minSize: 5,
        /* 定义雪花最小尺寸 */
        maxSize: 50,
        /* 定义雪花最大尺寸 */
        newOn: 300 /* 定义密集程度，数字越小越密集 */
    });
});