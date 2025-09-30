package com.example.RegistrationService.utility;

import com.example.RegistrationService.dto.EmailSendDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MailSenderUtility {

    private final Logger logger =  LoggerFactory.getLogger(MailSenderUtility.class);

    @Value("${mail-sender.url}")
    private static String MailSenderBaseUrl;

    public static void callMailSenderService(String authorization, String purpose, String email) {
        EmailSendDTO mailSenderObject = new EmailSendDTO();
        if (purpose.contains("Account blocked")) {
            mailSenderObject.setTo(email);
//            mailSenderObject.setCC(""); -- add CC Email
            mailSenderObject.setBody("Your Account is blocked due to multiple Invalid Attempts");
            mailSenderObject.setSubject("Account Blocked");
        }
        WebClient client = WebClient.builder()
                .baseUrl(MailSenderBaseUrl)
                .build();

        String response = callMainSenderService(client, authorization, mailSenderObject);
    }

    public static String callMainSenderService(WebClient webClient, String authorization, EmailSendDTO emailSenderDTO) {
        return webClient.post()
                .uri("/sendEmail")
                .header("Authorization", authorization)
                .bodyValue(emailSenderDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
