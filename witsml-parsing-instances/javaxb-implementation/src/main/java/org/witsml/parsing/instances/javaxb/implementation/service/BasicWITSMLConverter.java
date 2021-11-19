package org.witsml.parsing.instances.javaxb.implementation.service;

import com.hashmapinc.tempus.WitsmlObjects.v1411.ObjLogs;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.witsml.parsing.instances.commons.WITSMLConvertingService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Unmarshaller;

/**
 * @project witsml-parsing-instances
 * @created 2021-11-18 11:21
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Component
public class BasicWITSMLConverter implements WITSMLConvertingService<MultipartFile, ObjLogs> {

    @Override
    public ObjLogs convert(MultipartFile request) {
        ObjLogs wellLogs = new ObjLogs();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjLogs.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            wellLogs = (ObjLogs) JAXBIntrospector.getValue(jaxbUnmarshaller.unmarshal(request.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.trace("Convert XML to JSON - {}", wellLogs.toString());

        return wellLogs;
    }
}
