package camunda.poc.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.DefaultHttpLogWriter;
import org.zalando.logbook.DefaultSink;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.json.JsonHttpLogFormatter;

import javax.servlet.ServletContext;

import static org.zalando.logbook.Conditions.exclude;
import static org.zalando.logbook.Conditions.requestTo;

/**
 * Created by ai.khafizov
 * on 30.09.2022
 */
@Configuration
@AllArgsConstructor
public class LoggbookBeansConfiguration {

    private final ServletContext servletContext;

    @Bean
    public Logbook logbook() {
        return Logbook.builder()
                .condition(exclude(requestTo(servletContext.getContextPath() + "/actuator/*")))
                .sink(new DefaultSink(new JsonHttpLogFormatter(), new DefaultHttpLogWriter()))
                .build();
    }
}
