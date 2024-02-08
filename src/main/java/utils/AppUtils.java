package utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class AppUtils {
    public String extractJson(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String json;
        while ((json = reader.readLine()) != null) {
            sb.append(json);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
