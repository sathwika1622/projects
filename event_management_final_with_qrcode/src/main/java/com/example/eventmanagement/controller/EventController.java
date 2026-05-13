
package com.example.eventmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin
public class EventController {

    @Autowired
    private EventService eventService;

    // 🔹 Get all events
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // 🔹 Add event (Admin)
    @PostMapping
    public Event addEvent(@RequestBody Event event) {
        return eventService.addEvent(event);
    }

    // 🔥 Book seats
    @PostMapping("/book")
    public String bookSeats(@RequestBody com.example.eventmanagement.model.BookingRequest request) {
        return eventService.bookSeats(request);
    }

    // 🆕 DELETE EVENT
    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable Long id) {
        return eventService.deleteEvent(id);
    }

    @Autowired
    private com.example.eventmanagement.service.EmailService emailService;

    // ✉️ Send Custom Email (Admin)
    @PostMapping("/send-email")
    public String sendCustomEmail(@RequestBody com.example.eventmanagement.model.EmailRequest request) {
        try {
            emailService.sendCustomEmail(request.getToEmail(), request.getSubject(), request.getMessage());
            return "Email sent successfully";
        } catch (Exception e) {
            return "Failed to send email: " + e.getMessage();
        }
    }
}