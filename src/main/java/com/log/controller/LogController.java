package com.log.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Log", description = "API สำหรับทดสอบการบันทึก Log / Log testing API")
public class LogController {

    Logger logger = LoggerFactory.getLogger(LogController.class);

    @RequestMapping("/log")
    @Operation(summary = "ทดสอบการบันทึก Log / Test logging", description = "บันทึกข้อความในทุกระดับ Log (TRACE, DEBUG, INFO, WARN, ERROR) / Logs messages at all levels")
    public String log() {
        logger.trace("Log level: TRACE");
        logger.info("Log level: INFO");
        logger.debug("Log level: DEBUG");
        logger.error("Log level: ERROR");
        logger.warn("Log level: WARN");
        return "Hey! You can check the output in the logs";
    }
}
