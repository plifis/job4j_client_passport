package ru.plifis.job4j_client_passport.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.plifis.job4j_client_passport.model.PassportDto;

import java.util.List;

@Service
public class PassportClient {
    @Value("${api-url}")
    private String url;
    private final RestTemplate restTemplate = new RestTemplate();

    public PassportClient() {
    }

    public PassportDto createPassport(PassportDto passportDto) {
        return restTemplate.postForEntity(
                String.format("%s/save", url), passportDto, PassportDto.class).getBody();
    }

    public PassportDto updatePassport(Long id, PassportDto passportDto) {
        return restTemplate.exchange(
                String.format("%s/update?id=%s", url, id),
                HttpMethod.PUT, new HttpEntity<>(passportDto), PassportDto.class)
                .getBody();
    }

    public boolean deletePassport(Long id) {
        return restTemplate.exchange(
                String.format("%s/delete?id=%s", url, id),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        ).getStatusCode() == HttpStatus.OK;
    }

    public List<PassportDto> getPassportBySeries(Integer seria) {
        return restTemplate.exchange(
                String.format("%s/find?seria=%s", url, seria),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<PassportDto>>() {}
        ).getBody();
    }

    public List<PassportDto> getAllPassports() {
        return restTemplate.exchange(
                String.format("%s/find", url),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<PassportDto>>() {}
        ).getBody();
    }

    public List<PassportDto> getUnavailablePassports() {
        return restTemplate.exchange(
                String.format("%s/unavailable", url),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<PassportDto>>() {}
        ).getBody();
    }

    public List<PassportDto> getReplaceablePassports() {
        return restTemplate.exchange(
                String.format("%s/find-replaceable", url),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<PassportDto>>() {}
        ).getBody();
    }

    @Scheduled(fixedDelay = 2000)
    public void checkUnavailablePassports() {
        getUnavailablePassports();
    }
}