package com.vijay.distributed.core.base;

import org.apache.log4j.Logger;

/**
 * Created by weiun on 2017/4/13.
 */
public class BaseFront {

    protected static Logger logger;

    public BaseFront() {
        logger = Logger.getLogger(this.getClass());
    }
}
