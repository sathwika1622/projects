package com.example.eventmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    public void sendBookingConfirmation(String toEmail, String userName, String eventTitle, int seats) {
        if (mailSender == null) {
            System.out.println("MAIL SENDER NOT CONFIGURED: Would have sent email to " + toEmail + " for event " + eventTitle);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Booking Confirmation - " + eventTitle);
            message.setText("Dear " + userName + ",\n\n"
                    + "Your booking for " + seats + " seat(s) at '" + eventTitle + "' is confirmed.\n"
                    + "You have payed and booked successfully!\n\n"
                    + "Thank you for using SecureApp Event Booking.");

            mailSender.send(message);
            System.out.println("Confirmation email sent to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + toEmail + ": " + e.getMessage());
        }
    }

    public void sendBookingFailure(String toEmail, String userName, String eventTitle, String reason) {
        if (mailSender == null) {
            System.out.println("MAIL SENDER NOT CONFIGURED: Would have sent failure email to " + toEmail + " for event " + eventTitle);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Booking Failed - " + eventTitle);
            message.setText("Dear " + userName + ",\n\n"
                    + "Unfortunately, your booking for '" + eventTitle + "' could not be completed.\n"
                    + "Reason: " + reason + "\n\n"
                    + "Please try again later or contact support.\n"
                    + "Thank you for using SecureApp Event Booking.");

            mailSender.send(message);
            System.out.println("Failure email sent to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send failure email to " + toEmail + ": " + e.getMessage());
        }
    }

    public void sendCustomEmail(String toEmail, String subject, String body) {
        if (mailSender == null) {
            System.out.println("MAIL SENDER NOT CONFIGURED: Would have sent custom email to " + toEmail + "\nSubject: " + subject + "\nBody: " + body);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("Custom email sent to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send custom email to " + toEmail + ": " + e.getMessage());
            throw new RuntimeException("Failed to send email");
        }
    }
}
