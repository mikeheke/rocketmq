package org.apache.rocketmq.namesrv.heke;

import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.namesrv.NamesrvStartup;

import java.util.Properties;

/**
 * @author heke
 * @since 2024-04-19
 */
public class NamesrvStartupTests {

    public static void main(String[] args) {
        System.setProperty("user.home", "D:\\rocketmq_home\\namesrv_home");
        System.setProperty(MixAll.ROCKETMQ_HOME_PROPERTY, "D:\\rocketmq_home\\namesrv_home");

        Properties properties = System.getProperties();
        for ( String propertyName : properties.stringPropertyNames() ) {
            System.out.println(propertyName + " : " + properties.getProperty(propertyName));
        }

        NamesrvStartup.main0(null);
    }

}
