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
    <script type="text/javascript" src="../js/jquery-3.5.1.js"></script>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="../modalPlugin/modal.css">
    <script src="../layui/layui.js"></script>
    <script src="../modalPlugin/modal.js"></script>
    <script src="../layui-excel/layui_exts/excel.js"></script>
    <title>Title</title>
    <style>
        .labledivsty{
            width: 30%;
            height: 100%;
            float: left;
            text-align: right;
            padding-top: 3.5%;
            padding-right: 4%;
            color: #7990A0;
        }
        .inputdivsty{
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
        .layui-table-cell {
            vertical-align: middle;
            height: auto;
            overflow: visible;
            text-overflow: inherit;
            white-space: normal;
        }

    </style>



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
            return '0.00';
        }
    </script>

    <script>
        layui.use(["form"], function () {
            $.post("../withdrawtype/queryAll.action", "", function (res) {
                var withdrawtype = $("#WithdrawType");
                withdrawtype.empty();
                withdrawtype.append('<option value="">请选择</option>')
                var withdrawtypes = res.data;
                for (const i in withdrawtypes) {
                    withdrawtype.append('<option value="' + withdrawtypes[i].id + '">' + withdrawtypes[i].name + '</option>')
                }
                var form = layui.form
                form.render('select')
            }, "json")
        });
        $(function (){
            var todw=$("#todw")
            var yesw=$("#yesw")
            var weekw=$("#weekw")
            $.post("../wdorder/withdrawordertoday.action",function (res){

                if (res.data.Amount!==null&&res.data.ThirdFee!=null){
                    todw.html("[ 今日支出: "+toDecimalString(res.data.Amount)+"/ 手续费: "+toDecimalString(res.data.ThirdFee)+" / 订单数量: "+res.data.Count+" ] ")
                }else {
                    todw.html("[ 今日支出: 0/ 手续费: 0 / 订单数量: "+res.data.Count+" ] ")
                }


            },"json")

            $.post("../wdorder/withdraworderyesterday.action",function (res){

                if (res.data.Amount!==null){
                    yesw.html("[ 昨日支出: "+toDecimalString(res.data.Amount)+"/ 手续费: "+toDecimalString(res.data.ThirdFee)+" / 订单数量: "+res.data.Count+" ] ")
                }else {
                    yesw.html("[ 昨日支出: 0/ 手续费: 0/ 订单数量: "+res.data.Count+" ] ")
                }
            },"json")
            layui.use(["form"], function(){

            });


            $("#search").click(function (){
                var tableins=layui.table
                var id=$("#Id").val();
                var orderid=$("#OrderId").val();
                var amount=$("#Amount").val();
                var type = $("#Type").val();
                var ConfirmStatus = $("#ConfirmStatus").val();
                var date=$("#pay-date").val();
                var WithdrawType=$("#WithdrawType").val()
                tableins.reload('table', {
                    where: { //设定异步数据接口的额外参数，任意设
                        Id:id
                        ,OrderId: orderid
                        ,Type: type
                        ,ConfirmStatus: ConfirmStatus
                        ,date:date
                        ,typeid:WithdrawType
                        //…
                    }
                    ,page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    ,url:'../wdorder/Allwithdraworder.action'
                }); //只重载数据
            })
        })

    </script>

    <script >

        function formatAmount(d){
                return toDecimalString(d)
        } function formatFeeFixAndFeeRate(d){
            var feerate=d.FeeRate;
            var str=Number(feerate*100).toFixed(1);
            str+="%";
            return str
        }
        function formatfee(d){
            var fee=d.PlatformFee+d.ThirdFee
            return  toDecimalString(fee)
        }
        function getStatusStatement(Status){
            // if (Status===1){
            //     return '处理中'
            // }
            if (Status===1){
                return '出款完成'
            }else if (Status===0){
                return '等待审核'
            }else if (Status===2){
                return '驳回'
            }
            // else if (Status===-2){
            //     return '失败'
            // }
        }
        function gettypesStatement(types){
            if (types===1){
                return '银行卡'
            }
            else if (types===2){
                return '电子钱包'
            }
            else {
                return 'UPI'
            }
        }
        // function getConfirmStatusStatement(ConfirmStatus){
        //     if (ConfirmStatus===0){
        //         return '待审核'
        //     }
        //     else if (ConfirmStatus===1){
        //         return '审核通过'
        //     }
        //     else {
        //         return '审核不通过'
        //     }
        // }
        function dateFormat(date){
            console.log(date)




        }
        function formatMoney(fen){
            var num = fen;
            num=fen*0.01;
            // num+='';
            // var reg = num.indexOf('.') >-1 ? /(\d{1,3})(?=(?:\d{3})+\.)/g : /(\d{1,3})(?=(?:\d{3})+$)/g;
            // num=num.replace(reg,'$1');

            return num+"元"



        }
        function exportexcel(data){
            var jsons = data[0]
            var index=0
            var str="{"
            for (var key in jsons)
            {
                str+='"'+key+'"'
                str+=":"
                str+='"'+key+'"'
                str+=","
            }

            str = str.substr(0, str.length - 1);
            console.log(str)
            str+="}"
            var str=JSON.parse(str)
            var arr=new Array()
            arr.push(str)
            for (var key in data)
            {
                arr.push(data[index])
                index+=1;
            }
            console.log(arr)
            LAY_EXCEL.exportExcel(arr, '提现记录.xlsx', 'xlsx')
        }
        function exportAll(){
            $.post("../wdorder/queryAll.action",function (res){
                var data=res.data;
                exportexcel(data)
            },"json")



        }


        function  operation(d){
            var status=d.ConfirmStatus;
            if (status==1){
                return ""
            }else if (status==0){
                return ""
            }else if (status==2) {
                var s = JSON.stringify(d);
                return "<button onclick='synccnt("+d.Id+");'   class='layui-btn layui-btn-normal layui-btn-sm'><i class='layui-icon'>再提交</i></button>"
            }






        }
        function synccnt(id){
            $.post("<%=request.getContextPath()%>/wdorder/updateStatusAndSynccnt.action?Id="+id+"&date="+Date.parse(new Date()),function (res){
                var tableins=layui.table
                tableins.reload('table', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    ,url:'../wdorder/Allwithdraworder.action'
                }); //只重载数据

            },"json")

        }


    </script>
    <script>
        layui.use('laydate', function () {
            var laydate = layui.laydate;
            //日期时间选择器
            laydate.render({
                elem: '#early'
                , type: 'datetime'
                , max: 0
                , format: 'yyyyMMdd' //可任意组合
            });

            laydate.render({
                elem: '#late'
                , type: 'datetime'
                , max: 0
                , format: 'yyyyMMdd' //可任意组合
            });
            //直接嵌套显示
        });
        layui.use('table', function(){
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
            table.render({
                elem: '#table',
                even: true
                , toolbar:true,
                    totalRow: true
                ,height: 'full'
                ,url:'../wdorder/Allwithdraworder.action'
                ,cols: [[
                    {field: 'Id', title: '订单号',width:80, fixed: 'left'}
                    ,{field: 'OrderId',width: 200, title: '外部订单号'}
                    ,{field: 'Amount', title: '提现金额', templet: function (d) {
                            return formatAmount(d.Amount);}}

                    ,{field: 'PlatformFee', title: '手续费用', templet: function (d) {
                            return formatAmount(d.PlatformFee);}}
                    ,{field: 'AppId', title: '提现用户Id'}
                    ,{field: 'BankCardNo', title: '银行卡号'}
                    ,{field: 'BankAccountName', title: '到账账户'}
                    ,{field: 'ConfirmStatus', title: '状态', templet: function (d) {
                            return getStatusStatement( d.ConfirmStatus);
                        }}
                    ,{field: 'CreateTime', title: '订单创建时间',templet:function(d){
                            return dateFormate(d.CreateTime);
                        }}
                    ,{field: '', title: '操作',templet: function (d) {


                            return operation(d);
                        }}
                    ,{field: 'SyncCnt', title: '提交申请<br>剩余次数'}
                ]]
                ,page: true
                ,done: function (res){
                    $('th').css({'height': 100});
                    var count =res.count
                    $("#count").empty();
                    $("#count").append("<h3 style='float: left'>共有</h3><h3 style='float: left;color: #f03e41'>"+count+"</h3><h3 style='float: left'>条数据</h3>")
                    var inftablediv=$("#ifotable")
                    var data=res.data;
                    var s =JSON.stringify(data)
                    $(".layui-table-main tr").each(function (index, val) {
                        $($(".layui-table-fixed-l .layui-table-body tbody tr")[index]).height($(val).height());
                        $($(".layui-table-fixed-r .layui-table-body tbody tr")[index]).height($(val).height());})

                }
            });
        });
    </script>




</head>
<body>
<div style="width: 96%;height: 100%;;margin-top: 1%">
    <div style="width: 100%;height: 16%;;margin-top: 1%;padding-top: 1%;padding-left: 1%;background-color: white">
        <form class="layui-form" action="">
        <div style="float: left;width: 82%;height: 100%;">
                <div  style="width: 100%;height: 36%;margin-top: 1%">
                    <div  class="divsty" >
                        <div class="labledivsty">
                            <label>订单号</label>
                        </div>
                        <div class="inputdivsty">
                            <input id="Id"  name="Id"  autocomplete="off" class="layui-input" placeholder="订单号">
                        </div>
                    </div>
                    <div  class="divsty">
                        <div class="labledivsty">
                            <label>商户订单号</label>
                        </div>
                        <div class="inputdivsty">
                            <input  id="OrderId"  name="OrderId" autocomplete="off" class="layui-input" placeholder="外部订单号">
                        </div>
                    </div>
                    <div  class="divsty">
                        <div class="labledivsty">
                            <label>订单状态</label>
                        </div>
                        <div class="inputdivsty">
                            <div class="layui-input-inline">
                                <select  id="ConfirmStatus" name="ConfirmStatus"  lay-verify="required" lay-search="">
                                    <option value="">请选择</option>
                                    <option value="2">驳回</option>
                                    <option value="0">等待审核</option>
                                    <option value="1">出款完成</option>
                                </select>
                            </div>
                        </div>
                    </div>

                </div>
                <div style="width: 100%;height: 36%;margin-top: 1%">
                    <div style="width: 100%;height: 36%;margin-top: 1%">
                        <div class="divsty" >
                            <div class="labledivsty">
                                <label>提现日期</label>
                            </div>
                            <div class="inputdivsty">
                                <div class="layui-input-inline">
                                    <input  id="pay-date"  name="Date"  autocomplete="off" class="layui-input" style="width: 240px" >
                                </div>
                            </div>
                        </div>


                    </div>



                </div>

        </div>
        <div style="float: left;width: 18%;height: 100%;">
            <div  style="margin-top: 24%">
            <div  style="float: left">
                <button id="search" type="button" class="layui-btn layui-btn-normal layui-btn-radius">查询
                </button>
            </div>
            <div style="float: left;margin-left: 2%">
                <button type="reset" class="layui-btn layui-btn-primary layui-btn-radius">清除</button>
            </div>
            </div>
        </div>
        </form>
    </div>
    <div id="withdrawlab" style="width: 96%;height: 3%;background-color: white">
        <label>交易支出</label> <label id="todw"></label> - <label id="yesw"></label> - <label id="weekw"></label>
    </div>
    <div style="width: 100%;height: 5%;padding-top: 2%;background-color: white">
        <h2>提现列表</h2>
    </div>
    <div style="width: 100%;height: 6%;padding-top: 2%;padding-left: 1%;background-color: white">
        <div id="count" style="float: left;width: 82%">

        </div>
        <div style="float: left;width: 18%" id="ifotable"  >
        </div>
    </div>
    <div class="layui-row" style=" ;width: 100%;margin-top:2%;background-color: white">
        <table id="table"></table>
    </div>
</div>
<script>
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
    });
</script>
</body>
<script>
    function  showdetails(data){
        var mModal1 = new mModal({
            title: "订单详情", // 标题，默认：提示
            width: "40%", // 弹出框宽度，默认 25%
            height: "60%",
            top: "15vh", // 距离可视区域顶部距离 CSS中 margin-top 值
            content: "<form id='addform'  action=''><table>" +
                "<tr>" +
                "<td > <label style='width: 100%' class=\"layui-form-label\">提现订单ID</label></td>" +
                "<td>" +
                "<div>\n" +
                "<label>"+data.Id+"</lable>"+
               // "<input  value='"+data.Id+"' id=\"Id\"  name=\"Id\"  autocomplete=\"off\" class=\"layui-input\" >\n" +
                "</div>" +
                "</td></tr>" +
                "<tr>" +
                "<td> <label style='width: 100%' class=\"layui-form-label\">提现金额</label></td>" +
                "<td>" +
                "<div >\n" +
                "<label>"+data.Amount+"</lable>"+
               // "<input value='"+data.Amount+"'  id=\"Amount\"  name=\"Amount\"  autocomplete=\"off\" class=\"layui-input\" >\n" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td> <label style='width: 100%' class=\"layui-form-label\">手续费</label></td>" +
                "<td>" +
                "<label>"+data.ThirdFee+"</lable>"+
             //   "<input value='"+data.ThirdFee+"' id=\"ThirdFee\"  name=\"ThirdFee\"  autocomplete=\"off\" class=\"layui-input\" >\n" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td> <label style='width: 100%' class=\"layui-form-label\">银行编号</label></td>" +
                "<td>" +
                "<div >\n" +
                "<label>"+data.BankCode+"</lable>"+
                //"<input   value='"+data.BankCode+"' id=\"BankCode\"  name=\"BankCode\"  autocomplete=\"off\" class=\"layui-input\" >\n" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td> <label style='width: 100%' class=\"layui-form-label\">银行卡号</label></td>" +
                "<td>" +
                "<div >\n" +
                "<label>"+data.BankCardNo+"</lable>"+
               // "<input   value='"+data.BankCardNo+"' id=\"BankCardNo\"  name=\"BankCardNo\"  autocomplete=\"off\" class=\"layui-input\" >\n" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td> <label style='width: 100%' class=\"layui-form-label\">账户名</label></td>" +
                "<td>" +
                "<div >\n" +
                "<label>"+data.BankCardName+"</lable>"+
              //  "<input  value='"+data.BankAccountName+"' id=\"BankAccountName\"  name=\"BankAccountName\"  autocomplete=\"off\" class=\"layui-input\" >\n" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td> <label style='width: 100%' class=\"layui-form-label\">提现订单创建时间</label></td>" +
                "<td>" +
                "<div >\n" +
                "<label>"+data.CreateTime+"</lable>"+
               // "<input  value='"+data.CreateTime+"'  id=\"CreateTime\"  name=\"CreateTime\"  autocomplete=\"off\" class=\"layui-input\" >\n" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td> <label style='width: 100%' class=\"layui-form-label\">审核时间</label></td>" +
                "<td>" +
                "<div >\n" +
                "<label>"+data.ConfirmTime+"</lable>"+
               // "<input value='"+data.ConfirmTime +"'  id=\"ConfirmTime\"  name=\"ConfirmTime\"  autocomplete=\"off\" class=\"layui-input\" >\n" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td> <label style='width: 100%' class=\"layui-form-label\">审核人</label></td>" +
                "<td>" +
                "<div >\n" +
                "<label>"+data.ConfirmUserId+"</lable>"+
               // "<input value='"+data.ConfirmUserId+"'  id=\"ConfirmUserId\"  name=\"ConfirmUserId\"  autocomplete=\"off\" class=\"layui-input\" >\n" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "<td> <label style='width: 100%' class=\"layui-form-label\">第三方系统订单号</label></td>" +
                "<td>" +
                "<div >\n" +
                "<label>"+data.TransactionNum+"</lable>"+
                //"<input value='"+data.TransactionNum+"'  id=\"TransactionNum\"  name=\"TransactionNum\"  autocomplete=\"off\" class=\"layui-input\" >\n" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "</table></form>", // 正文，默认：正文内容
            cancelText: "取 消", // 取消按钮文本
            confirmText: "确 定", // 确定按钮文本
            showCancelButton: true, // 是否显示取消按钮
            showConfirmButton: false, // 是否显示确定按钮
            showClose: true, // 是否显示关闭按钮
            modal: false, // 是否需要遮罩层
            customClass: "", // 自定义类名confirm
            confirm: function () {
            },
            /*confirm: function () {
                mModal1.close();
            },*/
            cancel: function () {

            }
        });
        mModal1.renderDom()

    }

</script>
</html>
