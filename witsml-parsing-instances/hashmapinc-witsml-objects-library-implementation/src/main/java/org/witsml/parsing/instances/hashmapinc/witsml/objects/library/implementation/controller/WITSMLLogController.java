package org.witsml.parsing.instances.hashmapinc.witsml.objects.library.implementation.controller;

import com.hashmapinc.tempus.WitsmlObjects.v1411.ObjLogs;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.witsml.parsing.instances.commons.WITSMLConvertingService;

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
@RequestMapping(value = "/witsml/log")
@RestController
public class WITSMLLogController {

    @Qualifier("BasicProcessingStateService")
    WITSMLConvertingService<MultipartFile, ObjLogs> logService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            path = "",
            consumes = "multipart/form-data",
            produces = "application/json",
            headers = "content-type=application/json, multipart/form-data"
    )
    public ObjLogs createLog(@RequestBody MultipartFile witsmlFile) {
        log.info("Получили запрос - {}", witsmlFile);
        ObjLogs response = this.logService.convert(witsmlFile);
        log.info("Возвращаем овтет - {}", response);

        return response;
    }
}
