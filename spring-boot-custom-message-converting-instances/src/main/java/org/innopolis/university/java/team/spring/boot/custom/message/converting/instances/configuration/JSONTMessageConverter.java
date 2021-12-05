package org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @project spring-boot-custom-message-converting-instances
 * @created 2021-12-05 16:20
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Component
public class JSONTMessageConverter<T extends JSONTMessageConverter.JsonTObject> extends AbstractHttpMessageConverter<T> {

    public interface JsonTObject {}

    @FieldDefaults(
            level = AccessLevel.PRIVATE
    )
    @Component
    static final class Transliteration {

        static String transliterationResourcePathString = "transliteration.csv";

        static Map<String, String> ENtoRUMappingRules = new HashMap<>();

        static Map<String, String> RUToENMappingRules = new HashMap<>();

        Transliteration() throws IOException {
            URL resource = getClass().getClassLoader().getResource(transliterationResourcePathString);
            if (resource == null)
                throw new IllegalArgumentException("The resource '" + transliterationResourcePathString + "' not found!");

            BufferedReader br = new BufferedReader(new InputStreamReader(resource.openStream()));
            String symbolsPair;
            while ((symbolsPair = br.readLine()) != null) {
                String[] symbols = symbolsPair.split(";");
                RUToENMappingRules.put(symbols[0], symbols[1]);
                ENtoRUMappingRules.put(symbols[1], symbols[0]);
            }
        }

        String fromRUToEN(String originString) {
            return this.transliterate(originString, RUToENMappingRules, ENtoRUMappingRules).orElse(originString);
        }

        String fromENToRU(String originString) {
            return this.transliterate(originString, ENtoRUMappingRules, RUToENMappingRules).orElse(originString);
        }

        private Optional<String> transliterate(String originString, Map<String, String> fromRules, Map<String, String> toRules) {
            return Arrays.stream(originString.split(""))
                    .map(eachSymbol -> fromRules.getOrDefault(eachSymbol, eachSymbol))
                    .reduce(String::concat);
        }
    }

    public static final MediaType APPLICATION_JSONT = MediaType.valueOf("application/json");

    public static final String APPLICATION_JSONT_VALUE = "application/json";

    public static final MediaType APPLICATION_JSONT_UTF8 = MediaType.valueOf("application/json;charset=UTF-8");

    public static final String APPLICATION_JSONT_UTF8_VALUE = "application/json;charset=UTF-8";

    ObjectMapper mapper;

    Transliteration transliteration;

    @Autowired
    public JSONTMessageConverter(ObjectMapper mapper, Transliteration transliteration) throws IOException {
        super(APPLICATION_JSONT, APPLICATION_JSONT_UTF8);
        this.mapper = mapper;
        this.transliteration = transliteration;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return JsonTObject.class.isAssignableFrom(clazz);
    }

    @Override
    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage)  throws IOException, HttpMessageNotReadableException {
        Map<String, Object> originMessageMap = mapper.readValue(inputMessage.getBody(), Map.class);
        log.info("Convert input message into map - {}", originMessageMap);

        Map<String, Object> transliteratedMessageMap = new HashMap<>() {{
            originMessageMap.forEach((attribute, value) ->  {
                put(transliteration.fromENToRU(attribute), value);
            });
        }};
        log.info("Transliterate input message map from EN to RU - {}", transliteratedMessageMap);

        T transliteratedInputMessage = mapper.convertValue(transliteratedMessageMap, clazz);
        log.info("Convert transliterated input message map into object - {}", transliteratedInputMessage);

        return transliteratedInputMessage;
    }

    @Override
    protected void writeInternal(T outputJson, HttpOutputMessage outputMessage)
            throws HttpMessageNotWritableException,
            IOException {
        Map<String, Object> originMessageMap = mapper.convertValue(outputJson, Map.class);
        log.info("Convert output message into map - {}", originMessageMap);

        Map<String, Object> transliteratedMessageMap = new HashMap<>() {{
            originMessageMap.forEach((attribute, value) ->  {
                put(transliteration.fromRUToEN(attribute), value);
            });
        }};
        log.info("Transliterate output message map from EN to RU - {}", transliteratedMessageMap);

        JsonNode transliteratedJson = mapper.convertValue(transliteratedMessageMap, JsonNode.class);
        log.info("Convert transliterated output message map into object - {}", transliteratedJson);

        OutputStream outputStream = outputMessage.getBody();
        outputStream.write(transliteratedJson.toString().getBytes());
        outputStream.close();
    }
}
