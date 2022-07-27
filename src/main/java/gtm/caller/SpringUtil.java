package gtm.caller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by ai.khafizov
 * on 26.07.2022
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * Finds the Spring bean using the application context
     * @param beanClass class name of the Spring bean
     * @return Spring managed bean instance of the given class type or null
     */
    public static <T extends Object> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringUtil.context = context;
    }
}