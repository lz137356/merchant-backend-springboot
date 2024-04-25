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
    <style>
        .labledivsty {
            width: 30%;
            height: 100%;
            float: left;
            text-align: right;
            padding-top: 3.5%;
            padding-right: 4%;
            color: #7990A0;
        }

        .inputdivsty {
            width: 50%;
            height: 100%;
            float: left;
            padding-top: 1%;
        }

        .divsty {
            width: 21%;
            height: 100%;
            margin-left: 1%;
            float: left;

        }

    </style>
    <title>Title</title>
    <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="../modalPlugin/modal.css">
    <script src="../layui/layui.js"></script>
    <script src="../modalPlugin/modal.js"></script>
    <script src="../layui-excel/layui_exts/excel.js"></script>

    <script>
        $(function () {
            var search=$("#search")

            search.click(function () {
                var sel=$("input[name='select']").val()
                var date=$("#pay-date").val()
                //ids=ids.replaceAll("%2C",",")
                // console.log("ids:::::"+ids)
                // var s=ids.split("=")



                var listTable=layui.table;
                listTable.reload('table',{
                    where: { //设定异步数据接口的额外参数，任意设
                        date:date
                        //…
                    }
                    ,url: '../cpstat/queryAll.action' //数据接口
                    , page: {
                        curr: 1 //重新从第 1 页开始
                    }
                }); //只重载数据
            })
        })
    </script>

    <script>
        var numberChars = "0123456789";
        function toDecimalString(value, place, hasDollarSign) {
            var n = 2;
            var ds = true;
            if (place != null && typeof (place) == 'number') {
                n = place;
            }
            if (hasDollarSign != null && typeof (hasDollarSign) == 'boolean') {
                ds = hasDollarSign;
            }
            if (value != null && typeof (value) == 'number') {
                var v=value*0.01
                v = v.toFixed(n);
                var str = v.toString();
                if (str != "") {
                    //用于判断千位符的数量变化
                    var pren = str.split(',').length - 1;
                    //合法Decimal格式字符串, 可以含千位符(,号)
                    var pattern = '^-?0(\\.\\d+)?$|^-?[1-9]\\d*(\\.\\d+)?$|^-?([1-9][0-9]{0,2},)(\\d{3},)*(\\d{3})(\\.\\d+)?$';
                    //合法字符集, 不包括,号
                    var pattern2 = '^[0-9\.-]*$';

                    var reg = new RegExp(pattern, 'g');
                    var reg2 = new RegExp(pattern2, 'g');
                    //转换之前, 去除,号
                    var temp = str.replace(/,/, "");
                    while (temp.indexOf(',') >= 0) {
                        temp = temp.replace(/,/, "");
                    }
                    var nstr = '';
                    if (reg2.test(temp)) {
                        //除(/)数和模(%)数
                        var k1 = 0, k2 = 0;
                        //转换开始和结束位置
                        var start = 0, end = 0;
                        //千位符(,号)
                        var pp = ',';
                        //计数(3的倍数)
                        var p = 0;
                        //判断前置的非数字符号(这里是-号)
                        for (; start < temp.length; start++) {
                            if (numberChars.indexOf(temp.substring(start, start + 1)) >= 0) {
                                break;
                            }
                            nstr = nstr.concat(temp.substring(start, start + 1));
                        }
                        //小数符号(.号)的位置
                        var pIndex = temp.indexOf('.');
                        //存在小数符(.号), 即以它的位置为结束位置, 否则以字符串结尾为结束位置
                        if (pIndex >= 0) {
                            end = pIndex;
                        } else {
                            end = temp.length;
                        }
                        k2 = (end - start) % 3;
                        k1 = parseInt((end - start) / 3);

                        for (var i = 0; i < k2; i++) {
                            nstr = nstr.concat(temp.substring(start + i, start + i + 1));
                        }
                        if (k1 > 0 && k2 > 0) {
                            nstr = nstr.concat(pp);
                        }
                        for (var i = k2 + start; i < end; i++) {
                            nstr = nstr.concat(temp.substring(i, i + 1));
                            p++;
                            if (p == 3 && i + 1 != end) {
                                p = 0;
                                nstr = nstr.concat(pp);
                            }
                        }
                        for (var i = end; i < temp.length; i++) {
                            nstr = nstr.concat(temp.substring(i, i + 1));
                        }
                    } else {
                        nstr = str;
                    }
                    if (ds) {
                        return '' + nstr;
                    } else {
                        return nstr;
                    }
                }
            }
            return 'Value is null or not a number.';
        }
        function formatAmount(d){
            var amount=toDecimalString(d)
                return amount

        }
        function getStatusStatement(Status){
            if (Status===0){
                return '初始'
            }
            else if (Status===1){
                return '正常'
            }else if (Status===-1){
                return '关闭'
            }
        }
    </script>
</head>
<body>


<div style="width: 96%;height: 100%;;margin-top: 1%">
    <div style="width: 100%;height: 8%;;margin-top: 1%;padding-top: 1%;padding-left: 1%;background-color: white">
        <form class="layui-form" action="#" id="searchform">
            <div style="float: left;width: 82%;height: 100%;">


                <div style="width: 100%;height: 36%;margin-top: 1%">

                </div>
                <div style="width: 100%;height: 36%;margin-top: 1%">
                    <div class="divsty"  >
                        <div class="labledivsty">
                            <label>日期</label>
                        </div>
                        <div class="inputdivsty">
                            <div class="layui-input-inline">
                                <input  id="pay-date"  name="Date"  autocomplete="off" class="layui-input" style="width: 240px" >
                            </div>
                        </div>
                    </div>


                </div>

            </div>

            <div style="float: left;width: 18%;height: 100%;">
                <div  style="margin-top: 24%">
                    <div  style="float: left">
                        <button id="search" type="button" class="layui-btn layui-btn-normal layui-btn-radius">筛选
                        </button>
                    </div>
                    <div style="float: left;margin-left: 2%">
                        <button type="reset" class="layui-btn layui-btn-primary layui-btn-radius">清除</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div style="width: 100%;height: 6%;padding-top: 2%;;background-color: white">
        <div style=" ;width: 100%;height: 100%;margin-top:4%;background-color: white">
            <table id="table"></table>
        </div>

    </div>
</div>


</body>
<script>




    layui.use('table', function(){
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#table'
            ,url: '../cpstat/queryAll.action' //数据接口
            ,page: true,
            toolbar:true,
            totalRow: true
            ,cols: [[ //表头
                {field: 'StatDay', title: '日期', sort: true}
                ,{field: 'PayAmount', title: '充值总额', templet: function (d) {
                        return formatAmount(d.PayAmount);
                    }}
                ,{field: 'PayFee', title: '充值手续费', templet: function (d) {
                        return formatAmount(d.PayFee);
                    }}
                ,{field: 'WithdrawAmount', title: '提现总额', sort: true, templet: function (d) {
                        return formatAmount(d.WithdrawAmount);
                    }}
                ,{field: 'WithdrawFee', title: '提现手续费', sort: true, templet: function (d) {
                        return formatAmount(d.WithdrawFee);
                    }}
            ]]
            ,done: function (res){
                $('th').css({'height': 100});
            }
        });

    });





    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form
            ,layer = layui.layer
            ,layedit = layui.layedit
            ,laydate = layui.laydate;

        //日期
        laydate.render({
            elem: '#pay-date'
            ,range:'~'
        });
        laydate.render({
            elem: '#date1'
        });

        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');

        //自定义验证规则
        form.verify({
            title: function(value){
                if(value.length < 5){
                    return '标题至少得5个字符啊';
                }
            }
            ,pass: [
                /^[\S]{6,12}$/
                ,'密码必须6到12位，且不能出现空格'
            ]
            ,content: function(value){
                layedit.sync(editIndex);
            }
        });

        //监听指定开关
        form.on('switch(switchTest)', function(data){
            layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
                offset: '6px'
            });
            layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
        });

        //监听提交
        form.on('submit(demo1)', function(data){
            layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            })
            return false;
        });

        //表单赋值
        layui.$('#LAY-component-form-setval').on('click', function(){
            form.val('example', {
                "username": "贤心" // "name": "value"
                ,"password": "123456"
                ,"interest": 1
                ,"like[write]": true //复选框选中状态
                ,"close": true //开关状态
                ,"sex": "女"
                ,"desc": "我爱 layui"
            });
        });

        //表单取值
        layui.$('#LAY-component-form-getval').on('click', function(){
            var data = form.val('example');
            alert(JSON.stringify(data));
        });

    });
</script>
</html>
