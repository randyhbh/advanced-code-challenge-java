package com.statista.code.challenge.infra.mail;

import com.statista.code.challenge.domain.notification.Message;
import com.statista.code.challenge.domain.notification.Notification;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MailService implements Notification {

    public static final int NETWORK_ISSUES_FAILURE_CODE = 3;
    public static final int SMTP_SERVER_DOWNTIME_FAILURE_CODE = 4;
    public static final int INVALID_EMAIL_ADDRESS_FAILURE_CODE = 5;
    public static final int INVALID_AUTH_FAILURE_CODE = 6;
    public static final int RATE_LIMITING_FAILURE_CODE = 7;
    public static final int EMAIL_CONTENT_ISSUE_FAILURE_CODE = 8;
    public static final int SERVER_BLACK_LISTING_FAILURE_CODE = 9;
    private static final int TOTAL_FAILURE_SCENARIOS = 10;
    private final Logger logger;

    @Getter
    private final ConcurrentLinkedQueue<FailedEmail> failedEmails;

    @Setter
    private int failureScenarioCounter;

    public MailService(Logger logger) {
        this.logger = logger;
        failureScenarioCounter = 0;
        failedEmails = new ConcurrentLinkedQueue<>();
    }

    public void send(Message message) {
        try {
            if (failureScenarioCounter < TOTAL_FAILURE_SCENARIOS) {
                executeNextFailureScenario(message);
            }

            // If no failures, log the successful email
            logger.info("Mock Email Sent - To: {}, Subject: {}, Body: {}", message.to(), message.subject(), message.body());

        } catch (Exception e) {
            // Handle failures by storing the failed email
            logger.error("Failed to send email: To: {}, Subject: {}, Body: {}", message.to(), message.subject(), message.body());
            failedEmails.add(new FailedEmail(message.to(), message.subject(), message.body(), failureScenarioCounter));
        }
    }

    public void retryFailedEmails() {
        while (!failedEmails.isEmpty()) {
            var failedEmail = failedEmails.poll();

            logger.info(
                    "Retry Mock Email Sent - To: {}, Subject: {}, Body: {}",
                    failedEmail.to(),
                    failedEmail.subject(),
                    failedEmail.body()
            );
        }
    }

    private void executeNextFailureScenario(Message message) {
        switch (failureScenarioCounter) {
            case NETWORK_ISSUES_FAILURE_CODE:
                simulateNetworkIssues();
                break;
            case SMTP_SERVER_DOWNTIME_FAILURE_CODE:
                simulateSMTPServerDowntime();
                break;
            case INVALID_EMAIL_ADDRESS_FAILURE_CODE:
                simulateInvalidEmailAddress(message.to());
                break;
            case INVALID_AUTH_FAILURE_CODE:
                simulateAuthenticationFailure();
                break;
            case RATE_LIMITING_FAILURE_CODE:
                simulateRateLimiting();
                break;
            case EMAIL_CONTENT_ISSUE_FAILURE_CODE:
                simulateEmailContentIssues(message.body());
                break;
            case SERVER_BLACK_LISTING_FAILURE_CODE:
                simulateSMTPServerBlacklisting();
                break;
            // Add more failure scenarios as needed...
            default:
                // Do nothing
                break;
        }

        failureScenarioCounter++;
    }

    private void simulateNetworkIssues() {
        // Simulate network issues (e.g., no connectivity)
        throw new RuntimeException("Network issues: Unable to connect to the email server.");
    }

    private void simulateSMTPServerDowntime() {
        // Simulate SMTP server downtime
        throw new RuntimeException("SMTP Server downtime: Unable to send emails.");
    }

    private void simulateInvalidEmailAddress(String email) {
        // Simulate invalid email address
        if (!isValidEmailAddress(email)) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
    }

    private void simulateAuthenticationFailure() {
        // Simulate authentication failure
        throw new RuntimeException("Authentication failure: Unable to authenticate with the email server.");
    }

    private void simulateRateLimiting() {
        // Simulate rate limiting
        throw new RuntimeException("Rate limiting: Exceeded allowed rate for sending emails.");
    }

    private void simulateEmailContentIssues(String body) {
        // Simulate email content issues (e.g., incorrect structure)
        if (body.contains("invalid-content")) {
            throw new IllegalArgumentException("Invalid email content: " + body);
        }
    }

    private void simulateSMTPServerBlacklisting() {
        // Simulate SMTP server blacklisting
        throw new RuntimeException("SMTP server blacklisting: Emails may be rejected.");
    }

    private boolean isValidEmailAddress(String email) {
        // A simple email validation method
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    // Helper class to represent a failed email
    public record FailedEmail(String to, String subject, String body, int failureScenarioCode) {
    }
}
