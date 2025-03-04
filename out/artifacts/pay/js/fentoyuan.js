var numberChars = "0123456789";
/* Convert to decimal string */
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