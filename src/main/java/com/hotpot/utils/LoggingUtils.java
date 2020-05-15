package com.hotpot.utils;

import org.slf4j.Logger;

public class LoggingUtils {

    public static void logBeanName(Logger logger, Class<?> clazz) {
        logger.info("Configuration --- Wiring bean " + clazz.getSimpleName());
    }

}
