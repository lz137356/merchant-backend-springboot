package com.lxtx.pay.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class QRCodeUtil {


    public static void main(String[] args) {
        String format = "https://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s";
        String format1 = String.format(format, "123", "456", "789");
        System.out.println(format1);
    }
}
