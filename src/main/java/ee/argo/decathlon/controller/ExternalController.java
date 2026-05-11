package ee.argo.decathlon.controller;

import ee.argo.decathlon.service.ExternalApiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("external")
@AllArgsConstructor

public class ExternalController {
    private final ExternalApiService externalApiService;

    // http://localhost:8080/external/judges
    @GetMapping("judges")
    public List<Object> getAllJudges() {
        return externalApiService.getJudges();
    }

    // http://localhost:8080/external/locations
    @GetMapping("locations")
    public List<Object> getAllLocations() {
        return externalApiService.getLocations();
    }
}
