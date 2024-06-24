package util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author luobaosong
 * @date 2024-06-11 22:15
 */
@Slf4j
@Component
public class OkHttpUtils {

    private final RestTemplate restTemplate;

    @Autowired
    public OkHttpUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public <T> T postForObject(String url, Object requestBody, Class<T> responseType) {
        try {
            log.info("postForObject url:{} requestBody:{}", url, JSONObject.toJSONString(requestBody));
            return restTemplate.postForObject(url, requestBody, responseType);
        } catch (Exception e) {
            log.error("Error in postForObject request", e);
            return null;
        }
    }

    public <T> T getForObject(String url, Class<T> responseType) {
        try {
            log.info("postForObject url:{} ", url);
            return restTemplate.getForObject(url, responseType);
        } catch (Exception e) {
            log.error("Error in getForObject request", e);
            return null;
        }
    }
}
