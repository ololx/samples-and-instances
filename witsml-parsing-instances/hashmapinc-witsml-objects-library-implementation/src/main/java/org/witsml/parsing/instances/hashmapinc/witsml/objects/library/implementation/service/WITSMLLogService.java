package org.witsml.parsing.instances.hashmapinc.witsml.objects.library.implementation.service;

import com.hashmapinc.tempus.WitsmlObjects.Util.WitsmlMarshal;
import com.hashmapinc.tempus.WitsmlObjects.v1411.ObjLogs;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.witsml.parsing.instances.commons.WITSMLConvertingService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @project witsml-parsing-instances
 * @created 2021-11-15 11:21
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service
public class WITSMLLogService implements WITSMLConvertingService<MultipartFile, ObjLogs> {

    @Override
    public ObjLogs convert(MultipartFile request) {
        ObjLogs WITSMLLog = new ObjLogs();

        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + request.getName());
            byte[] buffer = new byte[request.getInputStream().available()];
            request.getInputStream().read(buffer);
            OutputStream outStream = new FileOutputStream(convFile);
            outStream.write(buffer);

            JAXBContext jaxbContext = JAXBContext.newInstance(ObjLogs.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            WITSMLLog = (ObjLogs) JAXBIntrospector.getValue(jaxbUnmarshaller.unmarshal(request.getInputStream()));
            log.debug(WITSMLLog.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return WITSMLLog;
    }
}
