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
import org.witsml.parsing.instances.commons.WITSMLConvertingService;

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

    WITSMLConvertingService<MultipartFile, ObjLogs> wellLogConvertingService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
            path = "",
            consumes = "multipart/form-data",
            produces = "application/json",
            headers = "content-type=application/json, multipart/form-data"
    )
    public ObjLogs parseLogs(@RequestBody MultipartFile witsmlFile) {
        log.info("Получили запрос - {}", witsmlFile);
        ObjLogs response = this.wellLogConvertingService.convert(witsmlFile);
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
    public List<CsLogData> parseLogsData(@RequestBody MultipartFile witsmlFile) {
        log.info("Получили запрос - {}", witsmlFile);
        ObjLogs objLogs = this.wellLogConvertingService.convert(witsmlFile);
        if (objLogs == null || objLogs.getLog().isEmpty()) {
            return Collections.emptyList();
        }

        List<CsLogData> result = objLogs.getLog().get(0).getLogData();
        log.info("Возвращаем овтет - {}", result);

        return result;
    }
}
