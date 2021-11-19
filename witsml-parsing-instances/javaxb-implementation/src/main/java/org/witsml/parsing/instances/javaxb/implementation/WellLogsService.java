package org.witsml.parsing.instances.javaxb.implementation;

import com.hashmapinc.tempus.WitsmlObjects.v1411.CsLogData;
import com.hashmapinc.tempus.WitsmlObjects.v1411.ObjLogs;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.witsml.parsing.instances.commons.WITSMLConvertingService;
import org.witsml.parsing.instances.javaxb.implementation.model.LogDetail;
import org.witsml.parsing.instances.javaxb.implementation.model.MnemonicDetail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @project witsml-parsing-instances
 * @created 2021-11-19 07:39
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
public class WellLogsService {

    @Qualifier("BasicWITSMLConverter")
    WITSMLConvertingService<MultipartFile, ObjLogs> witsmlConverter;

    public ObjLogs convertWellLogs(MultipartFile objLogsFile) {
        ObjLogs objLogs = this.witsmlConverter.convert(objLogsFile);

        return objLogs;
    }

    public List<CsLogData> convertWellLogsData(MultipartFile objLogsFile) {
        ObjLogs objLogs = this.witsmlConverter.convert(objLogsFile);
        if (objLogs == null) {
            return Collections.emptyList();
        }

        List<CsLogData> result = new ArrayList<>(){{
            objLogs.getLog().parallelStream()
                    .forEach(eachLog -> addAll(eachLog.getLogData()));
        }};

        return result;
    }

    public List<LogDetail> convertWellLogsDataMnemonics(MultipartFile objLogsFile) {
        List<CsLogData> csLogDataCollection = this.convertWellLogsData(objLogsFile);

        List<LogDetail> result = new ArrayList<>(){{
            for (CsLogData eachCsLogData : csLogDataCollection) {
                String mnemonics = eachCsLogData.getMnemonicList();
                if (mnemonics == null) continue;

                String[] mnemonicsArray = mnemonics.split(",");
                if (mnemonicsArray.length < 3) continue;

                List<MnemonicDetail> mnemonicsDetailCollection = new ArrayList<>();
                for (int mnemonicIndex = 2; mnemonicIndex < mnemonicsArray.length; mnemonicIndex++) {

                    List<List<String>> mnemonicValuesCollection = new ArrayList<>();
                    List<String> dataCollection = eachCsLogData.getData();
                    for (String data : dataCollection) {
                        if (data == null) continue;

                        String[] dataArray = data.split(",");
                        mnemonicValuesCollection.add(List.of(dataArray[1], dataArray[mnemonicIndex]));
                    }

                    mnemonicsDetailCollection.add(
                            MnemonicDetail.builder()
                                    .mnemonic(mnemonicsArray[mnemonicIndex])
                                    .values(mnemonicValuesCollection)
                                    .build()
                    );
                }

                add(
                        LogDetail.builder()
                                .mnemonicDetails(mnemonicsDetailCollection)
                                .build()
                );
            }
        }};

        return result;
    }
}
