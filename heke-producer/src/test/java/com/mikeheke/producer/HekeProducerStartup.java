package com.mikeheke.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.log.ClientLogger;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * 同步发送消息 单元测试
 *
 * @author heke
 * @since 2024-04-19
 */
@Slf4j
public class HekeProducerStartup {

    public static void main(String[] args) throws MQClientException {
        System.setProperty(ClientLogger.CLIENT_LOG_USESLF4J, "true");
        System.setProperty(ClientLogger.CLIENT_LOG_LEVEL, "DEBUG");

        log.debug("heke producer startup.");
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer("producerGroup1"); //（1）
        // 设置NameServer地址
        producer.setNamesrvAddr("localhost:9876");  //（2）
        // 启动producer
        producer.start();
    }

}
