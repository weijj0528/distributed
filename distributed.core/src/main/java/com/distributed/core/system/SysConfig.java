package com.distributed.core.system;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.distributed.core.base.BaseFront;
import org.springframework.core.io.Resource;

/**
 * 系统启动时加载
 */
public class SysConfig extends BaseFront {

    // 本地资源
    Resource location;

    public void setLocation(Resource location) {
        this.location = location;
    }

    // 信息键值对
    private static Properties sysConfig;

    public void init() throws Exception {
        logger.info("——————————————初始化系统参数——————————————");
        if (loadSysConfig()) {
            logger.info("|资源初始化成功");
        } else {
            logger.info("|资源初始化失败");
        }
        logger.info("|版本:" + getProperty("project.name") + "  [" + ("release".equals(getProperty("project.version")) ? "生产" : "测试") + "]");
        logger.info("——————————————初始化系统完成——————————————");
    }

    /**
     * 加载 properties 配置文件
     *
     * @return
     * @date 2015年9月11日 下午4:08:32
     */
    private boolean loadSysConfig() {
        boolean result = true;
        try {
            sysConfig = new Properties();
            sysConfig.load(location.getInputStream());
        } catch (FileNotFoundException e) {
            logger.error(e);
            result = false;
        } catch (IOException e) {
            logger.error(e);
            result = false;
        }
        return result;
    }

    /**
     * 获取配置文件配置
     *
     * @return
     * @date 2015年9月11日 下午4:08:32
     */
    public static String getProperty(String key) {
        return sysConfig.getProperty(key);
    }

}
