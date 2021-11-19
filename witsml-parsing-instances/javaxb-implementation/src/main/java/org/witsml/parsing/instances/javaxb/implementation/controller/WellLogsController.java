package org.witsml.parsing.instances.javaxb.implementation.controller;

import com.hashmapinc.tempus.WitsmlObjects.v1411.CsLogData;
import com.hashmapinc.tempus.WitsmlObjects.v1411.ObjLogs;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.witsml.parsing.instances.javaxb.implementation.WellLogsService;
import org.witsml.parsing.instances.javaxb.implementation.model.LogDetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @project witsml-parsing-instances
 * @created 2021-11-15 11:32
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Validated
@CrossOrigin(origins = "/**")
@RequestMapping(value = "/well/logs")
@RestController
public class WellLogsController {

    WellLogsService wellLogsService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
            path = "",
            consumes = "multipart/form-data",
            produces = "application/json",
            headers = "content-type=application/json, multipart/form-data"
    )
    public ObjLogs parseLogs(@RequestBody MultipartFile objLogsFile) {
        log.info("Получили запрос - {}", objLogsFile);
        ObjLogs response = this.wellLogsService.convertWellLogs(objLogsFile);
        log.info("Возвращаем овтет - {}", response);

        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
            path = "/data",
            consumes = "multipart/form-data",
            produces = "application/json",
            headers = "content-type=application/json, multipart/form-data"
    )
    public List<CsLogData> parseLogsData(@RequestBody MultipartFile objLogsFile) {
        log.info("Получили запрос - {}", objLogsFile);
        List<CsLogData> result = this.wellLogsService.convertWellLogsData(objLogsFile);
        log.info("Возвращаем овтет - {}", result);

        return result;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
            path = "/data/mnemonics",
            consumes = "multipart/form-data",
            produces = "application/json",
            headers = "content-type=application/json, multipart/form-data"
    )
    public List<LogDetail> parseLogsDataMnemonics(@RequestBody MultipartFile objLogsFile) {
        log.info("Получили запрос - {}", objLogsFile);
        List<LogDetail> result = this.wellLogsService.convertWellLogsDataMnemonics(objLogsFile);
        log.info("Возвращаем овтет - {}", result);

        return result;
    }
}
