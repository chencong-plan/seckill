<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/15
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/head.jsp" %>
<%@ include file="common/tag.jsp" %>
<html>
<head>
    <title>秒杀详情页</title>
</head>
<body>

<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${seckill.name}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <%--显示time图标--%>
                <span class="glyphicon glyphicon-time"></span>
                <%--展示倒计时--%>
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>
</div>


<%--弹出层--%>
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone">填写手机号</span>
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey" placeholder="填手机号(*╹▽╹*)"
                               class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <%--验证信息--%>
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneButton" class="btn">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>
        </div>
    </div>
</div>


<script src="${basePath}/static/jquery-3.2.1.js"></script>
<script src="${basePath}/static/bootstrap/bootstrap.js"></script>

<%--使用cdn获得公用插件--%>
<%--jquery cookie插件--%>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--jquery倒计时插件--%>
<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>

<%--开始交互逻辑--%>
<script src="${basePath}/static/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        // 使用EL表达式传入参数
        seckill.detail.init({
            seckillId:${seckill.seckillId},
            startTime: ${seckill.startTime.time}, //转换毫秒
            endTime: ${seckill.endTime.time}
        });
    });
</script>
</body>
</html>
