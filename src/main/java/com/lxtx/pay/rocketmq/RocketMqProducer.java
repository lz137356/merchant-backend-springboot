package com.lxtx.pay.rocketmq;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.logging.org.slf4j.Logger;
import org.apache.rocketmq.logging.org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class RocketMqProducer {

    private final static Logger logger = LoggerFactory.getLogger(RocketMqProducer.class);

    private static DefaultMQProducer defaultMQProducer;
    private String producerGroup;
    private String namesrvAddr;

    /**
     * Spring bean init-method
     */
    public void init() throws MQClientException {
        // 参数信息
        logger.info("DefaultMQProducer initialize!");
        logger.info(producerGroup);
        logger.info(namesrvAddr);
        // 初始化
        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
        defaultMQProducer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQProducer.start();
        logger.info("DefaultMQProudcer start success!");
    }

    /**
     * Spring bean destroy-method
     */
    public void destroy() {
        defaultMQProducer.shutdown();
    }
    public DefaultMQProducer getDefaultMQProducer() {
        return defaultMQProducer;
    }
    // ---------------setter -----------------

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    /**
     * rocketmq发送消息方法
     *
     * @param topic 组名
     * @param tagName 同一个topic下的不同 分支,同一个消费者只能取一个组的下的不同的tag分支
     * @param key 保持唯一
     * @param msgBody
     * @return
     */
    public static void sendMsgIntime(String topic, String tagName, String key, String msgBody) {
        Message msg = null;
        try {
            msg = new Message(topic,tagName,key,msgBody.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            String result = defaultMQProducer.send(msg).toString();
            logger.info("send rockmq topic:" + topic + " " + result);
        } catch (Exception e) {
            logger.info("send rockmq error topic:" + topic + msgBody, e);
        }
    }

}
