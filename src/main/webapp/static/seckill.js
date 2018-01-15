/**
 * 存放主要交互逻辑的js代码
 * javascript交互逻辑模块化
 * */
var seckill = {
    /*封装秒杀相关ajax的url*/
    URL: {},
    //验证手机号
    validatePhone: function (phone) {
        //直接判断对象会看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            // 用户手机验证和登录，计时交互操作
            // 规划交互流程
            // 在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            //验证手机号 返回false 显示弹出层
            if (!seckill.validatePhone(killPhone)) {
                //绑定手机号
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show: true,// 显示弹出层
                    backdrop: 'static', //禁止位置关闭
                    keyboard: false // 关闭键盘事件
                });
                $('#killPhoneButton').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if (seckill.validatePhone(inputPhone)) {
                        // 电话号码写入cookie当中
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        //刷新页面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<lable class="label label-danger">手机号错误</lable>').show(300);
                    }
                });
            }
            //已经登录

        }
    }
}