package io.joyoungc.api.common.configuration.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MessageResourceLoader {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Charset encoding = StandardCharsets.UTF_8;
    private final TypeReference<HashMap<String, String>> typeReference = new TypeReference<>() {};

    public Map<String, String> getMessageMap(String location, String locale) {
        Resource resource = new ClassPathResource(location + "/messages_" + locale + ".json");
        try {
            return mapper.readValue(new InputStreamReader(resource.getInputStream(),
                    encoding.name()), typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
