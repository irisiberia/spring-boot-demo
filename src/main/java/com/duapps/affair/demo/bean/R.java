package com.duapps.affair.demo.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class R {

    public static final ObjectMapper SHARED_MAPPER = new ObjectMapper();

    public static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    static {
        SHARED_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SHARED_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        SHARED_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        SHARED_MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        SHARED_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    // 计划线程
    public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(CORE_SIZE - 1);

    public static final String EMPTY_CACHE_VALUE_FLAG = "###+--**-+###";
}
