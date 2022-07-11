<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/27 0027
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link rel="stylesheet" href="../layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="../modalPlugin/modal.css">

    <title>Title</title>
</head>
<body>
<div style="width: 96%;height: 97%;;margin-top: 1%">
    <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
        <ul class="layui-tab-title">
            <li class="layui-this">登录日志</li>
            <li>操作日志</li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show"><table id="table"></table></div>
            <div class="layui-tab-item">
                <table class="layui-table">
                    <thead>
                    <tr>
                        <th>记录ID</th>
                        <th>登录IP</th>
                        <th>日志类型</th>
                        <th>其它内容</th>
                        <th>操作端口</th>
                        <th>创建时间</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
<script src="../layui/layui.js"></script>
<script>
    layui.use(['element','table'], function(){
        var table = layui.table;
        function dateFormate(date) {
            if(date){
                date = new Date(date)
            }else{
                return ''
            }
            var Y = date.getFullYear();
            var M = date.getMonth() + 1;
            M = M < 10 ? '0' + M : M;// 不够两位补充0
            var D = date.getDate();
            D = D < 10 ? '0' + D : D;
            var H = date.getHours();
            H = H < 10 ? '0' + H : H;
            var Mi = date.getMinutes();
            Mi = Mi < 10 ? '0' + Mi : Mi;
            var S = date.getSeconds();
            S = S < 10 ? '0' + S : S;
            return Y + '-' + M + '-' + D + ' ' + H + ':' + Mi + ':' + S;
        }
        //第一个实例
        table.render({
            elem: '#table'
            ,url: '<%=request.getContextPath()%>/log/page.action' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'Id', title: '记录ID', fixed: 'left'}
                ,{field: 'RemoteIp', title: '登录IP'}
                ,{field: 'LogType', title: '日志类型'}
                ,{field: 'Details', title: '其它内容'}
                ,{field: 'OperationTarget', title: '操作端口'}
                ,{field: 'CreateTime', title: '创建时间',templet:function(d){
                        return dateFormate(d.CreateTime);
                    }}
            ]],
            parseData: function(res){ //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.count, //解析数据长度
                    "data": res.data //解析数据列表
                };
            }
        });
        var element = layui.element;

        //一些事件监听
        element.on('tab(docDemoTabBrief)', function(data){
            console.log(data);
        });
    });
</script>
</html>
