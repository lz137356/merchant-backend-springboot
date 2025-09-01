package com.lxtx.pay.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;

public class VerificationCodeGenerator {

    public static VerificationCodeResult generateVerificationCode() {
        // 生成4位随机数字
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(random.nextInt(10));
        }
        String verificationCode = code.toString();

        // 创建验证码图片
        int width = 120;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 设置背景色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 设置字体
        g2d.setFont(new Font("Arial", Font.BOLD, 35));

        // 绘制验证码
        for (int i = 0; i < verificationCode.length(); i++) {
            // 随机设置字符颜色
            g2d.setColor(new Color(
                    random.nextInt(150) + 50,
                    random.nextInt(150) + 50,
                    random.nextInt(150) + 50
            ));

            // 绘制字符（稍微随机偏移位置）
            g2d.drawString(
                    String.valueOf(verificationCode.charAt(i)),
                    20 + i * 25 + random.nextInt(5),
                    25 + random.nextInt(10)
            );
        }

        // 添加干扰线
        for (int i = 0; i < 15; i++) {
            g2d.setColor(new Color(
                    random.nextInt(200),
                    random.nextInt(200),
                    random.nextInt(200)
            ));
            g2d.drawLine(
                    random.nextInt(width),
                    random.nextInt(height),
                    random.nextInt(width),
                    random.nextInt(height)
            );
        }

// 添加干扰点
        for (int i = 0; i < 50; i++) {
            g2d.setColor(new Color(
                    random.nextInt(255),
                    random.nextInt(255),
                    random.nextInt(255)
            ));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g2d.drawRect(x, y, 1, 1);
        }

        g2d.dispose();

        // 转换为Base64字符串
        String base64Image = "";
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", outputStream);
            base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new VerificationCodeResult(verificationCode, base64Image);
    }

    // 用于返回验证码和Base64编码的类
    public static class VerificationCodeResult {
        private String code;
        private String base64Image;

        public VerificationCodeResult(String code, String base64Image) {
            this.code = code;
            this.base64Image = base64Image;
        }

        public String getCode() {
            return code;
        }

        public String getBase64Image() {
            return base64Image;
        }
    }

    // 测试方法
    public static void main(String[] args) {
        VerificationCodeResult result = generateVerificationCode();

        System.out.println("验证码: " + result.getCode());
        System.out.println("Base64图片编码: ");
        System.out.println("data:image/png;base64," + result.getBase64Image());

        // 在实际使用中，您可以将base64字符串返回给前端
        // 前端可以使用 <img src="data:image/png;base64,这里是Base64编码"> 显示图片
    }
}
