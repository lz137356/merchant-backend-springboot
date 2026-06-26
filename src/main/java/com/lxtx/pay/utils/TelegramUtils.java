package com.lxtx.pay.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TelegramUtils {

    public static void reply(String content, String tgId) {
        log.info("Telegram message to {}: {}", tgId, content);
        // 实际实现需要调用 Telegram API
    }

    public static void replyAsync(String content, String tgId) {
        new Thread(() -> reply(content, tgId)).start();
    }

    public static void replyAsyncWithoutResult(String content, String tgId) {
        replyAsync(content, tgId);
    }
}
