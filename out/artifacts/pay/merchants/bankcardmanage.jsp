<%@ taglib prefix="c" uri="/struts-tags" %>
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
    <title>Title</title>
    <script type="text/javascript" src="../js/jquery-3.5.1.js"></script>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="../modalPlugin/modal.css">
    <script src="../layui/layui.js"></script>
    <script src="../modalPlugin/modal.js"></script>
    <script src="../layui-excel/layui_exts/excel.js"></script>
    <script>
        $(function (){
            $("#add").click(function () {
                var add = 'add'
                openModal("",add);
            })
        })
    </script>

    <script>

        function operation(data) {
            var json=JSON.stringify(data)
            return "<button style='float: left' id='open' onclick='deleteCpinfo("+data.Id+")' type=\"button\" class=\"layui-btn layui-btn-normal layui-btn-sm\"><i class=\"layui-icon\">删除</i></button>"
                +"<button style='float: left' id='close' onclick='edit("+json+")' type=\"button\" class=\"layui-btn layui-btn-normal layui-btn-sm\"><i class=\"layui-icon\">修改</i></button>"

        }

        function deleteCpinfo(Id) {
            var mModal1 = new mModal({

                // title: "", // 标题，默认：提示
                width: "25%", // 弹出框宽度，默认 25%
                height: "25%",
                top: "15vh", // 距离可视区域顶部距离 CSS中 margin-top 值
                content: "请确定是否删除", // 正文，默认：正文内容
                cancelText: "取 消", // 取消按钮文本
                confirmText: "确 定", // 确定按钮文本
                showCancelButton: true, // 是否显示取消按钮
                showConfirmButton: true, // 是否显示确定按钮
                showClose: true, // 是否显示关闭按钮
                modal: false, // 是否需要遮罩层
                customClass: "", // 自定义类名confirm
                confirm: function () {
                    $.post("../cpbank/deletcprbank.action?Id="+Id,function (res) {
                        var tableins = layui.table
                        tableins.reload('table', {
                            page: {

                                curr: 1 //重新从第 1 页开始}
                                , url: '../cpbank/querycpbank.action'
                            }}); //只重载数据
                    }, "json")
                    mModal1.close();
                },
                cancel: function () {

                }
            });
            mModal1.renderDom()

        }
        function getStatusStatement(TxType){
            if (TxType===1){
                return '银行卡'
            }
            else if (TxType===2){
                return 'UPI'
            }

        }
        function getBankCardStatement(BankCode){

            return BankCode
        }
        function edit(data) {

            openModal(data, "edit")
        }
        function openModal(res, op) {






            var mModal1;
            if (op==="add"){
                layer.open({
                    type:1,
                    offset:'100px',
                    title:"查看详情",
                    area: ['500','400'],
                    closeBtn: 1,
                    shadeClose: true,
                    btn: ['确定','取消'],
                    content:$("#addform"),

                    // 弹层外区域关闭

                    success:function (index,layero){
                        var TxType=$("#TxType").val(res.TxType)
                    },
                    yes: function(index, layero){
                        var flag=true;
                        var ty=$("#TxType").val()
                        var bcode=$("#BankCode").val()
                        var cardno=$("#BankCardNo").val()
                        var bcname=$("#BankCardName").val()
                        if (ty==null||ty==""){
                            flag=false;
                            layer.msg("请添加银行卡信息。")

                        }if (ty=="1"){
                            if (bcode==null||bcode==""){
                                flag=false;
                                layer.msg("请选择银行。")
                            }

                            var regular= /^([1-9]{1})(\d{14}|\d{18})$/;
                            if (!regular.test(cardno)){
                                flag=false;
                                layer.msg("卡号错误，请重新输入银行卡号。")
                            }
                            if (bcname==null||bcname==""){
                                flag=false;
                                layer.msg("请重新输入银行卡名称。")
                            }


                        }else if (ty=="2"){
                            if (cardno==null||cardno==""){
                                flag=false;
                                layer.msg("请输入提现帐号。")

                            }


                        }
                        if(flag){
                            var form = $("#addform")
                            var paramStr = form.serialize()
                            console.log(paramStr)
                            url="../cpbank/addcpbank.action"
                            $.post(url, paramStr, function (res) {
                                layer.close(index)
                                var tableins = layui.table
                                tableins.reload('table', {
                                    page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                    , url: '../cpbank/querycpbank.action'
                                }); //只重载数据
                            }, "json")


                        }

                    },
                    cancel:function (index,layero){

                    }
                    // 正文，默认：正文内容
                });

            }else if(op==="edit"){
                layer.open({
                    type:1,
                    offset:'100px',
                    title:"查看详情",
                    area: ['500','350'],
                    closeBtn: 1,
                    shadeClose: true,
                    btn: ['确定','取消'],
                    content:  $("#editform"),

                    // 弹层外区域关闭

                    success:function (index,layero){
                        var TxType=res.TxType
                        var BankCode=res.BankCode
                        var bc1=$("#bc1");
                        var BankCardNameinput=$("#BankCardNameinput");
                        var BankCardNamelab=$("#BankCardNamelab");
                        if (TxType===1){
                            $('#TxTypeedit').find('option[value='+2+']').attr('selected',false);
                            $('#TxTypeedit').find('option[value='+TxType+']').attr('selected','selected');
                            $("#BankCardNoedit").val(res.BankCardNo)
                            $("#BankCardNameedit").val(res.BankCardName)
                            $('#BankCodeedit').find('option[value='+BankCode+']').attr('selected','selected');
                            bc1.css("display","")
                        }else if (TxType===2){
                            $('#TxTypeedit').find('option[value='+1+']').attr('selected',false);
                            $('#TxTypeedit').find('option[value='+TxType+']').attr('selected','selected');
                            $("#BankCardNoedit").val(res.BankCardNo)
                            $("#BankCardNameedit").val(res.BankCardName)
                            bc1.css("display","none")
                        }
                        layui.form.render()
                    },
                    yes: function(index, layero){
                        var form = $("#editform")


                        var paramStr = form.serialize()
                        url="../cpbank/update.action"
                        $.post(url, paramStr+"&Id="+res.Id, function (res) {
                            layer.close(index)
                            var tableins = layui.table
                            tableins.reload('table', {
                                page: {
                                    curr: 1 //重新从第 1 页开始
                                }
                                , url: '../cpbank/querycpbank.action'
                            }); //只重载数据
                        }, "json")

                    },
                    cancel:function (index,layero){

                    }
                    // 正文，默认：正文内容
                });
            }

        }


    </script>
</head>
<body>


<div style="width: 96%;height: 100%;;margin-top: 1%">


    <div style="width: 100%;height: 5%;padding-top: 2%;background-color: white">
        <h2>银行卡管理</h2>
    </div>
    <div style="width: 100%;height: 6%;padding-top: 2%;;background-color: white">
        <div style="float: left">

        </div>
        <div id="count" style="float: left">

        </div>
        <div style="float: right">
            <button id="add" type="button" class="layui-btn layui-btn-primary layui-btn-radius"
                    style="float: right;margin-left: 90%">添加银行卡
            </button>
        </div>
        <div style=" ;width: 100%;height: 100%;margin-top:2%;background-color: white">
            <table id="table"></table>
        </div>

    </div>




    <form id='addform'  class="layui-form" style="display: none;" action=''  >
        <table>
            <tr>
                <td ><label style='margin-left: 64px;font-size: 14px;color: rgb(121,144,160)' class=  "layui-form-label">到账类型:</label></td>
                <td>
                    <div  class=  "layui-input-inline"   >
                        <select style='width:270px;height: 35px;border-radius: 6px' id=  "TxType"   name=  "TxType"   lay-filter='sel'   >
                            <option value="">请选择</option>
                            <option value="1"  >银行卡</option>
                            <option value="2" >UPI</option>
                        </select>
                    </div>
                </td></tr>

            <tr >
                <td >
                    <label style='margin-left: 64px;font-size: 14px;color: rgb(121,144,160)' class=  "layui-form-label">账号:</label>
                </td>
                <td >
                    <div  class="layui-input-inline">
                        <input style='width:270px;height: 35px;border-radius: 6px'  id="BankCardNo" name="BankCardNo" autocomplete=\"off\" class="layui-input">
                    </div>
                </td>
            </tr>
            <tr id='bno' style="display: none">

                <td >
                    <label style='margin-left: 64px;font-size: 14px;color: rgb(121,144,160)' class=  "layui-form-label">银行:</label>
                </td>
                <td >
                    <div  class="layui-input-inline">
                        <select style='width:270px;height: 35px;border-radius: 6px' id="BankCode"   name=  "BankCode"   lay-filter='sel'   >
                            <option value="">请选择</option>
                            <option value="ICIC"  >ICIC</option>
                            <option value="AXIS" >AXIS</option>
                            <option value="HDFC" >HDFC</option>
                            <option value="StateBankOfIndia" >StateBankOfIndia</option>
                            <option value="PunjabNationalBank" >PunjabNationalBank</option>
                            <option value="BankOflndia" >BankOflndia</option>
                            <option value="BankOfBaroda" >BankOfBaroda</option>
                            <option value="CanaraBank" >CanaraBank</option>
                            <option value="ReserveBankOfIndia" >ReserveBankOfIndia</option>
                            <option value="UnitedBankOfIndia" >UnitedBankOfIndia</option>
                            <option value="IndianOverseasBank" >IndianOverseasBank</option>
                            <option value="KotakMahindraBank" >KotakMahindraBank</option>
                        </select>
                    </div>
                </td>

            </tr>
<%--            <tr id='ifsc' style="display: none">--%>
<%--                <td >--%>
<%--                    <label style='margin-left: 64px;font-size: 14px;color: rgb(121,144,160)' class=  "layui-form-label">开户支行:</label>--%>
<%--                </td>--%>
<%--                <td >--%>
<%--                    <div  class="layui-input-inline">--%>
<%--                        <input style='width:270px;height: 35px;border-radius: 6px'   autocomplete=\"off\" class="layui-input">--%>
<%--                    </div>--%>
<%--                </td>--%>

<%--            </tr>--%>
            <tr >
                <td> <label style='margin-left: 64px;font-size: 14px;color: rgb(121,144,160)' class=  "layui-form-label"  >到账用户:</label></td>
                <td>
                    <div class=  "layui-input-inline" >
                        <input style='width:270px;height: 35px;border-radius: 6px'  id= "BankCardName"    name= "BankCardName"  autocomplete=  "off "  class="layui-input"   >
                    </div>
                </td>
            </tr>
            </tr>
<%--            <tr>--%>
<%--                <td> <label style='margin-left: 64px;font-size: 14px;color: rgb(121,144,160)' class=  "layui-form-label"  >谷歌令牌:</label></td>--%>
<%--                <td>--%>
<%--                    <div class=  "layui-input-inline" >--%>
<%--                        <input style='width:270px;height: 35px;border-radius: 6px'  id= ""     name=   ""   autocomplete=  "off "  class=  "layui-input"   >--%>
<%--                    </div>--%>
<%--                </td>--%>
<%--            </tr>--%>
            </tr>
        </table></form>
    <form id='editform'  class="layui-form" style="display: none;" action=''  ><table style="border-collapse:separate; border-spacing:0px 10px;">" +
        <tr >
            <td ><label style='margin-left: 64px;font-size: 14px;color: rgb(121,144,160)' >到账类型：</label></td>
            <td>
                <div   class="layui-input-inline" style='width: 270px;height: 40px'>
                    <div  class=  "layui-input-inline"   >
                        <select style='width:270px;height: 35px;border-radius: 6px' id=  "TxTypeedit"   name=  "TxType"    lay-filter='sel2'    >
                            <option value="">请选择</option>
                            <option value="1">银行卡</option>
                            <option value="2" >UPI</option>
                        </select>
                    </div>
                </div>
            </td></tr>
        <tr>
            <td> <label style='margin-left: 64px;font-size: 14px;color: rgb(121,144,160)' >账号：</label></td>
            <td>
                <div >
                    <input   style='width:238px;height: 35px;border-radius: 6px;border: 1px solid rgb(230,230,230)'    id="BankCardNoedit"  name="BankCardNo"   autocomplete=\"off\"  >
                </div>
            </td>
        </tr >



        <tr id='bc1'>
            <td> <label style='margin-left: 64px;font-size: 14px;color: rgb(121,144,160)' >银行信息：</label></td>
            <td>
                <div  class="layui-input-inline">
                    <select style='width:270px;height: 35px;border-radius: 6px' id="BankCodeedit"    name=  "BankCode"   lay-filter='selbc' >
                        <option value="">请选择</option>
                        <option value="ICIC"  >ICIC</option>
                        <option value="AXIS" >AXIS</option>
                        <option value="HDFC" >HDFC</option>
                        <option value="StateBankOfIndia" >StateBankOfIndia</option>
                        <option value="PunjabNationalBank" >PunjabNationalBank</option>
                        <option value="BankOflndia" >BankOflndia</option>
                        <option value="BankOfBaroda" >BankOfBaroda</option>
                        <option value="CanaraBank" >CanaraBank</option>
                        <option value="ReserveBankOfIndia" >ReserveBankOfIndia</option>
                        <option value="UnitedBankOfIndia" >UnitedBankOfIndia</option>
                        <option value="IndianOverseasBank" >IndianOverseasBank</option>
                        <option value="KotakMahindraBank" >KotakMahindraBank</option>
                    </select>
                </div>
            </td>
        </tr>
        <tr>
            <td> <label id='BankCardNamelab' style='margin-left: 64px;font-size: 14px;color: rgb(121,144,160)'>到账用户：</label></td>
            <td>
                <div >
                    <input  style='width:238px;height: 35px;border-radius: 6px;border: 1px solid rgb(230,230,230)'    id="BankCardNameedit"  name="BankCardName"  autocomplete=\"off\" class=\"layui-input\" >
                </div>
            </td>
        </tr>
        </tr>
        <tr>
    </table></form>
    <script>

        layui.use('form', function () {
            var form = layui.form;

            //自定义表单验证（金额）
            form.verify({
                validateMoney: [
                    /^[+]{0,1}(\d+)$/  //正则表达式
                    , '金额只能为正整数'  //提示信息
                ],
                BankCardNo: [
                    /^([1-9]{1})(\d{14}|\d{18})$/
                    , '请填写正确的银行卡号'
                ]

            });
        });

        layui.use('table', function(){
            var table = layui.table;

            //第一个实例
            table.render({
                elem: '#table'
                ,url: '../cpbank/querycpbank.action' //数据接口
                ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                    layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                    //,curr: 5 //设定初始在第 5 页
                    ,groups: 1 //只显示 1 个连续页码
                    ,first: false //不显示首页
                    ,last: false //不显示尾页
                }
                ,cols: [[ //表头
                    {field: 'Id', width: 200,title: 'ID'}
                    ,{field: 'TxType', title: '到账类型', templet: function (d) {
                            return getStatusStatement(d.TxType);
                        },}

                    ,{field: 'BankCardNo', width: 200,title: '到账账号'}
                    ,{field: 'BankCode', width: 200,title: '银行卡信息 ',templet:function (d) {
                            return getBankCardStatement(d.BankCode)
                        },}
                    ,{field: 'BankCardName', width: 200,title: '到账用户'}
                    ,{field: "",title: "操作", templet: function (d) {
                            return operation(d);
                        }}

                ]]
                ,done: function (res){
                    $('th').css({'height': 100});
                    var count =res.count
                    $("#count").empty();
                    $("#count").append("<h3 style='float: left'>共有</h3><h3 style='float: left;color: #f03e41'>"+count+"</h3><h3 style='float: left'>条数据</h3>")
                }
            });

        });


        layui.use(['form', 'layedit', 'laydate'], function(){
            var form = layui.form
                ,layer = layui.layer
                ,layedit = layui.layedit
                ,laydate = layui.laydate;
            form.render();
            form.on('select(sel)',function (data){
                console.log(data)
                var val=data.value;
                var acc=$("#acc")
                var bno=$("#bno")
                var ifsc=$("#ifsc")
                if (val==="1"){
                    bno.show()
                    ifsc.show()

                    form.render();
                }else if(val==="2"){
                    bno.hide()
                    ifsc.hide()
                    form.render();
                }else {

                }
            })
            form.on('select(sel2)',function (data) {
                console.log(data)
                var val = data.value;
                var bc1 = $("#bc1")
                var bno = $("#bno")
                var ifsc = $("#ifsc")
                if (val === "1") {
                    bc1.show()
                    ifsc.show()
                    form.render();
                } else if (val === "2") {
                    bc1.hide()
                    ifsc.hide()
                    form.render();
                } else {

                }
            })
            //日期
            laydate.render({
                elem: '#date'
            });
            laydate.render({
                elem: '#date1'
            });
            layui.use(['table','form'], function () {
                layui.form.on('switch(switchTest)', function (obj) {
                    var status = this.checked ? '1' : '0';
                    console.log("status::::"+status)
                    console.log("value::::"+this.value)
                    $.post("../cpinfo/update.action",{AppId:this.value,Status:status},function (res){
                        console.log("open::::::"+res)
                        if (res===1) {
                            layer.msg("状态修改成功");
                        } else {
                            layer.msg("状态修改失败");
                        }
                    },"json")
                });



            });


        });

    </script>
</div>


</body>
</html>

