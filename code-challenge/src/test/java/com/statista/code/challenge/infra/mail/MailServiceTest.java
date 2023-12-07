package com.statista.code.challenge.infra.mail;

import com.statista.code.challenge.domain.notification.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(OutputCaptureExtension.class)
class MailServiceTest {

    private final Logger logger = LoggerFactory.getLogger(MailService.class);
    private MailService mailService;

    @BeforeEach
    void setUp() {
        mailService = new MailService(logger);
    }

    @AfterEach
    void tearDown() {
        mailService.retryFailedEmails();
    }

    @Test
    void send_SuccessfulEmail(CapturedOutput output) {
        var message = new Message("valid@email.com", "Test Subject", "Test Body");

        assertDoesNotThrow(() -> mailService.send(message));
        assertThat(output.getOut()).contains("Mock Email Sent - To: " + message.to() + ", Subject: " + message.subject() + ", Body: " + message.body());
    }

    @Test
    void sendMailThrowsNetworkIssues(CapturedOutput output) {
        var message = new Message("valid@email.com", "Test Subject", "Test Body");
        mailService.setFailureScenarioCounter(MailService.NETWORK_ISSUES_FAILURE_CODE);

        mailService.send(message);

        assertThat(mailService.getFailedEmails().size()).isEqualTo(1);
        assertThat(output.getOut()).contains("Failed to send email: To: " + message.to() + ", Subject: " + message.subject() + ", Body: " + message.body());
    }

    @Test
    void sendMailThrowsInvalidEmailAddress(CapturedOutput output) {
        var invalidEmail = "invalid-email";
        var message = new Message(invalidEmail, "Test Subject", "Test Body");
        mailService.setFailureScenarioCounter(MailService.INVALID_EMAIL_ADDRESS_FAILURE_CODE);

        mailService.send(message);

        assertThat(mailService.getFailedEmails().size()).isEqualTo(1);
        assertThat(output.getOut()).contains("Failed to send email: To: " + invalidEmail + ", Subject: " + message.subject() + ", Body: " + message.body());
    }

    @Test
    void retryFailedEmailsWillSuccessfullyRetry(CapturedOutput output) {
        var message = new Message("valid@email.com", "Test Subject", "Test Body");
        mailService.setFailureScenarioCounter(MailService.NETWORK_ISSUES_FAILURE_CODE);

        mailService.send(message);
        mailService.retryFailedEmails();

        assertThat(mailService.getFailedEmails()).isEmpty();
        assertThat(output.getOut()).contains("Retry Mock Email Sent - To: " + message.to() + ", Subject: " + message.subject() + ", Body: " + message.body());
    }

    @Test
    void retryFailedEmailsWithNoFailedEmailsIsSuccessful(CapturedOutput output) {
        mailService.retryFailedEmails();

        assertThat(output.getOut()).isEmpty();
        assertThat(mailService.getFailedEmails()).isEmpty();
    }
}