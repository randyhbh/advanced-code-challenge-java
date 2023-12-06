package com.statista.code.challenge.infra.mail;

import com.statista.code.challenge.domain.booking.Booking;
import com.statista.code.challenge.domain.notification.Message;

public final class MailMessageRender {

    public static Message fromBooking(Booking booking) {
        String email = booking.email();
        String subject = "A new booking request was created";
        String body = "We received a booking with this description " + booking.description();
        return new Message(email, subject, body);
    }
}
