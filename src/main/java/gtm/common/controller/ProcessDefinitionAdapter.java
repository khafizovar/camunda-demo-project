package gtm.common.controller;

import camunda.poc.engine.process.ProcessEndListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.runtime.StartProcessInstanceDto;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by ai.khafizov
 * on 28.07.2022
 */
@RestController
@RequestMapping("/extend")
@Tag(name = "Расширение")
public class ProcessDefinitionAdapter {

    @Operation(summary = "Тестовый метод проверки доступности сервиса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Операция успешна",
                         content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500",
                         description = "Внутренняя ошибка сервера",
                         content = {@Content(mediaType = "text/plain",
                                             schema = @Schema(implementation = String.class))})
    })
    @GetMapping(value = "/test", produces = MediaType.TEXT_PLAIN_VALUE)
    public String test() {
        return "test";
    }

    @Operation(summary = "Синхронный запуск бизнес-процесса с возвратом результата выполнения)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Операция успешна",
                         content = {@Content(mediaType = "application/json",
                                             schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "500",
                         description = "Внутренняя ошибка сервера",
                         content = {@Content(mediaType = "application/json",
                                             schema = @Schema(implementation = String.class))})
    })
    @PostMapping("/proc-def/{key}/start-sync")
    public ResponseEntity<Map<String, Object>> startProcessSync(@PathVariable String key, @RequestBody @Nullable StartProcessInstanceDto startProcessInstanceDto) throws ExecutionException, InterruptedException {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstantiationBuilder builder =
                (startProcessInstanceDto == null) ?
                        runtimeService.createProcessInstanceByKey(key) :
                        runtimeService.createProcessInstanceByKey(key)
                                .setVariables(VariableValueDto.toMap(startProcessInstanceDto.getVariables(), processEngine, new ObjectMapper()));
        final Map<String, Object> values = new HashMap<>();
        final FutureTask<Object> ft = new FutureTask<>(() -> {
        }, new Object());

        ProcessEndListener.registerCallBack(builder.executeWithVariablesInReturn().getId(), data -> {
            values.putAll(data);
            ft.run();
        });
        ft.get();
        return ResponseEntity.ok(values);
    }
}
