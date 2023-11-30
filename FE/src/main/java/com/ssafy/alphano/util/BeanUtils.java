package com.ssafy.alphano.util;

import com.ssafy.alphano.common.config.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;

public class BeanUtils {
    public static Object getBean(String beanName) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBean(beanName);
    }
}
