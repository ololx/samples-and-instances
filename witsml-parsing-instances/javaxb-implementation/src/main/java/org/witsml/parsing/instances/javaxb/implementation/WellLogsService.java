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
import org.witsml.parsing.instances.javaxb.implementation.model.DataSet;
import org.witsml.parsing.instances.javaxb.implementation.model.LogDetail;

import java.util.*;

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

    static Map<String, String> mnemonicsSpecs;

    static {
        mnemonicsSpecs = new HashMap<>(){{
            putAll(
                    Map.of(
                            "md", "MD",
                            "DateTime", "DateTime",
                            "HKLD",	"HKLD",
                            "MudPressure",	"PUMP",
                            "Pump_cnt1(2,3,..,n)",	"SP1(2,3,…,N)",
                            "table_torque",	"T_TRQ",
                            "rotor_speed", "RPM",
                            "tr_block_pos",	"BLOCK",
                            "wob",	"WOB",
                            "pipe_wrench_torque",	"P_TRQ"
                    )
            );
            putAll(
                    Map.of(
                            "Volume1(2,3,…,n)", "V1(2,3,..,n)",
                            "active_volume",	"A_V",
                            "slugging_volume",	"S_V",
                            "reception_volume", "R_V",
                            "mud_density_in", "MDI",
                            "mud_density_out", "MDO",
                            "mud_temp_in", "MTI",
                            "mud_temp_out",	"MTO",
                            "mud_cond_in",	"MCI",
                            "mud_cond_out",	"MCO"
                    )
            );
            putAll(
                    Map.of(
                            "circ_rate_ in",	"CRI",
                            "circ_rate_out",	"CRO",
                            "trip_rate",	"TRIP",
                            "drilling_rate",	"DR_RATE",
                            "drilling_time_log",	"DR_T_L",
                            "rop",	"ROP",
                            "akb_wrench_torque",	"A_TRQ",
                            "summ_int",	"SGI",
                            "summ_gaz",	"SG",
                            "summ_gaz3",	"SGO"
                    )
            );
            putAll(
                    Map.of(
                            "c1",	"C1",
                            "c2", "C2",
                            "c3",	"C3",
                            "c4",	"C4"
                    )
            );
        }};
    }

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

                List<DataSet> DataSetCollection = new ArrayList<>();
                for (int mnemonicIndex = 2; mnemonicIndex < mnemonicsArray.length; mnemonicIndex++) {

                    List<DataSet.DataSetPoint> dataSetPointsCollection = new ArrayList<>();
                    List<String> dataCollection = eachCsLogData.getData();
                    for (String data : dataCollection) {
                        if (data == null) continue;

                        String[] dataArray = data.split(",");
                        dataSetPointsCollection.add(new DataSet.DataSetPoint(dataArray[mnemonicIndex], dataArray[0]));
                    }

                    DataSetCollection.add(
                            DataSet.builder()
                                    .name(mnemonicsSpecs.getOrDefault(mnemonicsArray[mnemonicIndex], mnemonicsArray[mnemonicIndex]))
                                    .dataPoints(dataSetPointsCollection)
                                    .build()
                    );
                }

                add(
                        LogDetail.builder()
                                .dataSet(DataSetCollection)
                                .build()
                );
            }
        }};

        return result;
    }
}
