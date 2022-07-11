//日期格式化
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

//金额格式化
function moneyFormate(money){
    if(!money){
        money=0;
    }
    return new Number(money/100).toFixed(2);
}
//获取项目根路径
function getRootPath(){
    //获取当前网址
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/“的项目名
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName+"/");
}
if(axios){
    axios.defaults.baseURL = getRootPath();
}
