package com.example.factorialapp.healthcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * probe 3개를 추가
 */
@RestController
@RequestMapping("/probe")
public class HealthcheckController {

    /**
     * K8S 가 호출하는 것임. 사용자가 호출하는 API 가 아님.
     */

    private final Logger logger =
            LoggerFactory.getLogger(HealthcheckController.class);

    /**
     * K8S 의 startupProbe 가 호출하는 endPoint.
     */
    @GetMapping("/startup")
    public String startupCheck() {
        /**
         * K8S 에서 중요하게 생각하는 것은
         * return 되는 문자열이 중요한 것이 아니라,
         * 200 OK 로 반환되는 것이 더 중요함.
         */
        logger.info("Starting probe check");

        return "Good to go";
    }

    /**
     * K8S 의 readinessProbe 가 호출하는 endPoint.
     */
    @GetMapping("/ready")
    public String readinessCheck() {
        logger.info("Readiness probe check");
        return "Ready";
    }

    /**
     * K8S 의 livenessProbe 가 호출하는 endPoint.
     */
    @GetMapping("/live")
    public String livenessCheck() {
        logger.info("liveness probe check");
        return "OK";
    }
}
