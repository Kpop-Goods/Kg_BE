package hello.kpop.global.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JsonRequestReader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, String> readJsonFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        String jsonBody = stringBuilder.toString();
        log.info("Received JSON body: {}", jsonBody);

        // JSON 문자열을 Map으로 파싱
        Map<String, String> jsonMap = new HashMap<>();
        if (!jsonBody.isEmpty()) {
            jsonMap = objectMapper.readValue(jsonBody, HashMap.class);
        }

        return jsonMap;
    }
}