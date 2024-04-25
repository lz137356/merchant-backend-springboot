package com.lxtx.pay.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomUtils {

    public static String getRandom (){

        Random random=new Random();
        String res="TS";
        for (int i = 0; i <6 ; i++) {
            res+=random.nextInt(10);
        }
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(date);
        return format+res;
    }

}
