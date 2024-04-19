package org.apache.rocketmq.client.heke;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Test;

/**
 * 同步发送消息 单元测试
 *
 * @author heke
 * @since 2024-04-19
 */
public class SyncProducerTests {

    @Test
    public void test() throws Exception {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer("producerGroup1"); //（1）
        // 设置NameServer地址
        producer.setNamesrvAddr("localhost:9876");  //（2）
        // 启动producer
        producer.start();
        // 创建一条消息，并指定topic、tag、body等信息，tag可以理解成标签，对消息进行再归类，RocketMQ可以在消费端对tag进行过滤
        Message msg = new Message("hekeTopicTest1" /* Topic */,
                "TagA" /* Tag */,
                ("Hello RocketMQ").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );   //（3）
        // 利用producer进行发送，并同步等待发送结果
        SendResult sendResult = producer.send(msg);   //（4）
        System.out.printf("%s%n", sendResult);

        Thread.sleep(30 * 1000);

        // 一旦producer不再使用，关闭producer
        producer.shutdown();
    }

}
