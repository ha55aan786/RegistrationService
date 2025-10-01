package com.example.RegistrationService.utility;

import com.example.RegistrationService.dto.EmailSendDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MailSenderUtility {

    private final Logger logger = LoggerFactory.getLogger(MailSenderUtility.class);

    @Value("${mail-sender.url}")
    private String mailSenderBaseUrl;

    public void callMailSenderService(String authorization, String purpose, String toEmail) throws Exception {
        EmailSendDTO mailSenderObject = new EmailSendDTO();
        if (purpose.contains("Account blocked")) {
            mailSenderObject.setTo(toEmail);
            mailSenderObject.setCC(toEmail);  // add CC Email
            mailSenderObject.setBody("Your Account is blocked due to multiple Invalid Attempts");
            mailSenderObject.setSubject("Account Blocked");
        }

        WebClient client = WebClient.builder()
                .baseUrl(mailSenderBaseUrl)
                .build();

        callMainSenderService(client, authorization, mailSenderObject);
    }

    public String callMainSenderService(WebClient webClient, String authorization, EmailSendDTO emailSenderDTO) throws Exception {

        try {
            return webClient.post()
                    .uri("/sendEmail")
                    .header("Authorization", authorization)
                    .bodyValue(emailSenderDTO)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .map(errorBody -> new RuntimeException("API Error: " + errorBody))
                    )
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            if (e.getMessage().contains("Authorization")) {
                throw new Exception(e.getMessage() + " while hitting the Mail Sender API");
            }
            if (e.getMessage().contains("invalid body")) {
                throw new Exception(e.getMessage() + " while hitting the Mail Sender API");
            } else {
                throw new Exception("Unexpected error while calling Mail Sender API: " + e.getMessage(), e);
            }
        }
    }
}
