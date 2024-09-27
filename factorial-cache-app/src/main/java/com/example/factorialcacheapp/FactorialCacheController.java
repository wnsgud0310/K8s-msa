package com.example.factorialcacheapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class FactorialCacheController {

    /**
     * K8S ConfigMap 객체에서 설정된 language 환경변수
     */
    @Value("${msa.language}")
    private String language;

    /**
     * K8S secret 객체 생성 명령문을 참조
     */
    @Value("${msa.api-key}")
    private String apiKey;

    private FactorialCalculateService calculateService;

    public FactorialCacheController(FactorialCalculateService calculateService) {
        this.calculateService = calculateService;
    }

    // http://localhost/factorial/10
    // http://localhost/factorial/11
    // localhost/factorial/11?key=abcd-1234-5678
    @GetMapping("/factorial/{n}")
    public String calculateFactorial(@PathVariable("n") int number,
                                     @RequestParam(value="key", required=false) String key) {

        if (number > 10) {
            if (!apiKey.equals(key)) {
                throw new IncorrectApiKeyException(
                        "Incorrect API Key: " + apiKey
                );
            }
        }

        /**
         * factorial 계산 service 사용
         * => K8S 클러스터 상에 있는 factorial-app-service 를 사용하기 위함.
         */
        BigDecimal result = calculateService.getCalculateResult(number);

        return switch (language) {
            case "ko" -> number + " 팩토리얼은 " + result + "입니다.";
            case "en" -> "the factorial of " + number + " is " + result;
            default -> "Unknown language.";
        };
    }
}
