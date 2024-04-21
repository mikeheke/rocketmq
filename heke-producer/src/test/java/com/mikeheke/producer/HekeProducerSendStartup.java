package com.mikeheke.producer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.log.ClientLogger;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 同步发送消息 单元测试
 *
 * @author heke
 * @since 2024-04-19
 */
@Slf4j
public class HekeProducerSendStartup {

    public static void main(String[] args) throws MQClientException {
        System.setProperty(ClientLogger.CLIENT_LOG_USESLF4J, "true");
        System.setProperty(ClientLogger.CLIENT_LOG_LEVEL, "DEBUG");

        log.debug("test sync send message.");
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer("producerGroup1"); //（1）
        // 设置NameServer地址
        producer.setNamesrvAddr("localhost:9876");  //（2）
        // 启动producer
        producer.start();

        ProducerUI producerUI = new ProducerUI();
        producerUI.setProducer(producer);
        producerUI.initUI();
    }

    @Slf4j
    @Data
    static class ProducerUI implements ActionListener {
        private JFrame jFrame = new JFrame();
        private JTextArea areaShow = new JTextArea(14, 35);
        private JTextArea areaTip = new JTextArea(5, 35);
        private JTextArea areaInput = new JTextArea(3, 35);
        DefaultMQProducer producer;

        private void initAreaTip() {
            areaTip.append("【command】\n");
            areaTip.append("【同步发送】sync  [msg]\n");
            areaTip.append("【异步发送】async  [msg]\n");
            areaTip.append("【单向发送】oneway  [msg]\n");
        }

        private void send(String msg) {
            try {
                String command = msg.split(" ")[0];
                String content = msg.split(" ")[1];
                if ( "sync".equals(command) ) {
                    // 创建一条消息，并指定topic、tag、body等信息，tag可以理解成标签，对消息进行再归类，RocketMQ可以在消费端对tag进行过滤
                    Message message = new Message(
                            "hekeTopicTest1" /* Topic */,
                            "TagA" /* Tag */,
                            content.getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                    );   //（3）
                    // 利用producer进行发送，并同步等待发送结果
                    SendResult sendResult = producer.send(message);   //（4）
                    log.debug("sendResult: {}", sendResult);
                    areaShow.append(sendResult.getSendStatus().name() + "\n");
                } else if ( "async".equals(command) ) {

                } else if ( "oneway".equals(command) ) {

                }
            } catch (Exception e) {

            }
        }

        public void initUI() {
            Color backgroundColor = new Color(43,43,43);
            Color foregroundColor = new Color(159, 177, 193);
            Font font = new Font("Serif", Font.PLAIN, 15);

            jFrame.setTitle("rpc-client");
            jFrame.setSize(420, 530);
            jFrame.getContentPane().setBackground(new Color(60,63,65));

            areaShow.setBackground(backgroundColor);
            areaShow.setForeground(foregroundColor);
            areaTip.setBackground(backgroundColor);
            areaTip.setForeground(foregroundColor);
            areaInput.setBackground(backgroundColor);
            areaInput.setForeground(foregroundColor);
            areaInput.setCaretColor(foregroundColor);

            areaShow.setFont(font);
            areaTip.setFont(font);
            areaInput.setFont(new Font("Serif", Font.PLAIN, 15));

            // 创建一个JScrollPane，并将JTextArea添加到其中
            JScrollPane scrollPane = new JScrollPane(areaShow);

            FlowLayout flowLayout = new FlowLayout();
            jFrame.setLayout(flowLayout);
            areaShow.setEditable(false);
            areaShow.setLineWrap(true); // 启用自动换行
            areaShow.setWrapStyleWord(true); // 设置为真，以在单词边界处换行
            areaTip.setEditable(false);
            jFrame.add(scrollPane);
            jFrame.add(areaTip);
            jFrame.add(areaInput);

            scrollPane.setBackground(backgroundColor);
            scrollPane.setForeground(foregroundColor);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            JButton sendBtn = new JButton("send");
            sendBtn.setBackground(backgroundColor);
            sendBtn.setForeground(foregroundColor);
            jFrame.add(sendBtn);
            jFrame.setDefaultCloseOperation(3);
            jFrame.setVisible(true);
            sendBtn.addActionListener(this);

            initAreaTip();

            // 输入框 监听回车事件
            areaInput.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                        String msg = areaInput.getText().trim();
                        areaShow.append(msg + "\n");
                        areaInput.setText("");
                        new Thread(() -> send(msg)).start();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) { }

                @Override
                public void keyReleased(KeyEvent e) { }
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String msg = areaInput.getText().trim();
            areaShow.append(msg + "\n");
            areaInput.setText("");
            new Thread(() -> send(msg)).start();
        }

    }

}
