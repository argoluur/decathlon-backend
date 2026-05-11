package ee.argo.decathlon.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ExternalApiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "https://6a0214120d92f63dd2534fa0.mockapi.io/api/andmed1";

    public List<Object> getJudges() {
        String url = BASE_URL + "/Judges";
        Object[] judges = restTemplate.getForObject(url, Object[].class);
        assert judges != null;
        return Arrays.asList(judges);
    }

    public List<Object> getLocations() {
        String url = BASE_URL + "/Location";
        Object[] locations = restTemplate.getForObject(url, Object[].class);
        assert locations != null;
        return Arrays.asList(locations);
    }
}
