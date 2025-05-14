package com.lxtx.pay.utils;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TelegramUtils {
    //wanda
//    private static String sendMsgUrl = "https://api.telegram.org/bot8179171938:AAE8UQVjOTU8gPt7BuuSdZktrfQw07cQHyg/sendMessage";
//    private static String groupId = "-1002441663065";
private static String sendMsgUrl = "https://api.telegram.org/bot7997170381:AAGfq--0NChOYxXWnfGYLCVTiJ1mAeNzhxM/sendMessage";
    private static String groupId = "-1002419019732";

    public static boolean reply(String content) {
        System.out.println("Telegram reply: " + content);
        try {
            // 清理 Markdown 内容中的下划线
            content = content.replace("_", "");

            // 构造参数
            StringBuilder postData = new StringBuilder();
            postData.append("chat_id=").append(URLEncoder.encode(groupId, "UTF-8"));
            postData.append("&text=").append(URLEncoder.encode(content, "UTF-8"));
            postData.append("&parse_mode=").append(URLEncoder.encode("Markdown", "UTF-8"));

            byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

            // 发起请求
            URL url = new URL(sendMsgUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

            try (OutputStream os = conn.getOutputStream()) {
                os.write(postDataBytes);
            }

            // 读取返回
            StringBuilder responseBuilder = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = in.readLine()) != null) {
                    responseBuilder.append(line);
                }
            }

            String response = responseBuilder.toString();
            System.out.println("Telegram response: " + response);

            JSONObject jo = JSONObject.fromObject(response);
            return jo.getBoolean("ok");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 示例调用
    public static void main(String[] args) {
        String apiUrl = "https://api.telegram.org/bot<YOUR_BOT_TOKEN>/sendMessage";
        boolean result = reply("Hello from bot");
        System.out.println("Message sent: " + result);
    }
}
