package camunda.poc.engine.process;

import java.util.Map;

/**
 * Интерфейс каллбэка для регистрации слушателя окончания выполнения процесса
 * Created by ai.khafizov
 * on 28.07.2022
 */
public interface ProcessEndListenerCallback  {
    void onActionSuccess(Map<String, Object> data);
}