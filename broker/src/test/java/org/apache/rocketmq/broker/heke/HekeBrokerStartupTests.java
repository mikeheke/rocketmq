package org.apache.rocketmq.broker.heke;

import org.apache.rocketmq.broker.BrokerStartup;
import org.apache.rocketmq.common.MixAll;

/**
 * @author heke
 * @since 2024-04-19
 */
public class HekeBrokerStartupTests {

    public static void main(String[] args) {
        System.setProperty("user.home", "D:\\rocketmq_home\\broker_home");
        System.setProperty(MixAll.ROCKETMQ_HOME_PROPERTY, "D:\\rocketmq_home\\broker_home");
        System.setProperty(MixAll.NAMESRV_ADDR_PROPERTY, "localhost:9876");

        BrokerStartup.main(null);
    }

}
