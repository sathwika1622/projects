package com.example.eventmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.Booking;
import com.example.eventmanagement.repository.EventRepository;
import com.example.eventmanagement.repository.BookingRepository;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private BookingRepository bookingRepo;

    // 🔹 Get all events
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    // 🔹 Add new event (Admin)
    public Event addEvent(Event event) {
        event.setAvailableSeats(event.getTotalSeats()); // initialize seats
        return eventRepo.save(event);
    }

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EmailService emailService;

    // 🔥 MAIN LOGIC: Book Seats
    @Transactional
    public String bookSeats(com.example.eventmanagement.model.BookingRequest request) {

        Event event = eventRepo.findById(request.getEventId()).orElse(null);

        if (event == null) {
            return "Event not found";
        }

        if (event.getAvailableSeats() >= request.getSeats()) {
            // Process payment
            boolean paymentSuccess = paymentService.processPayment(request.getCardNumber(), request.getCvv());
            if (!paymentSuccess) {
                emailService.sendBookingFailure(request.getUserEmail(), request.getUserName(), event.getTitle(), "Payment Failed");
                return "Payment Failed ❌";
            }

            // Reduce seats
            event.setAvailableSeats(event.getAvailableSeats() - request.getSeats());
            eventRepo.save(event);

            // Save booking
            Booking booking = new Booking();
            booking.setEventId(request.getEventId());
            booking.setSeatsBooked(request.getSeats());
            booking.setUserName(request.getUserName());
            booking.setUserEmail(request.getUserEmail());
            booking.setPaymentStatus("COMPLETED");

            bookingRepo.save(booking);

            // Send Email
            emailService.sendBookingConfirmation(request.getUserEmail(), request.getUserName(), event.getTitle(), request.getSeats());

            return "Payed and booked successfully ✅";
        } else {
            emailService.sendBookingFailure(request.getUserEmail(), request.getUserName(), event.getTitle(), "Not enough seats available");
            return "Not enough seats ❌";
        }
    }

    // 🆕 DELETE EVENT
    public String deleteEvent(Long id) {

        if (!eventRepo.existsById(id)) {
            return "Event not found ❌";
        }

        eventRepo.deleteById(id);
        return "Event deleted successfully ✅";
    }
}