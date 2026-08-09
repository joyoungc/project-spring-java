package io.joyoungc.api.mock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.joyoungc.api.BaseServerApiIntegrationTest;
import io.joyoungc.infrastructure.configuration.PersistenceConfig;
import io.joyoungc.infrastructure.constant.Profiles;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MockController.class)
@ActiveProfiles({Profiles.WEBMVC, Profiles.TEST})
class MockControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static final String API_ENDPOINT = "/api/mock";

    @Test
    void custom_formatter_executed_correctly() throws Exception {
        long date = 12314523556L;
        long localDateTime = 1012314523556L;

        MvcResult mvcResult = mockMvc.perform(get(API_ENDPOINT + "/custom-formatter")
                        .param("date", date + "")
                        .param("localDateTime", localDateTime + "")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.localDateTime").exists())
                .andReturn();

        TypeReference<HashMap<String, String>> typeRef = new TypeReference<>() {};
        Map<String, String> resultMap = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), typeRef);
        String localDateTimeString = resultMap.get("localDateTime");

        LocalDateTime resultLocalDateTime = LocalDateTime.parse(localDateTimeString,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        long epochMilli = resultLocalDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        assertThat(epochMilli).isEqualTo(localDateTime);
    }

}