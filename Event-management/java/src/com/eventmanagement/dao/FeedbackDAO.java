package com.eventmanagement.dao;

import com.eventmanagement.model.Feedback;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Data Access Object for Feedback.
 * Course: 25ITT32 Java Programming & 25ITT31 DBMS | Reg No: 31
 */
public class FeedbackDAO {
    private final DataStore store = DataStore.getInstance();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public boolean submitFeedback(int registrationId, int rating, String comments) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Feedback rating must be an integer between 1 and 5.");
        }

        // Check if feedback already exists for this registration
        for (Feedback f : store.feedbacks.values()) {
            if (f.getRegistrationId() == registrationId) {
                f.setRating(rating);
                f.setComments(comments);
                f.setSubmittedAt(LocalDateTime.now().format(FORMATTER));
                return true;
            }
        }

        int id = store.fbIdGen.incrementAndGet();
        Feedback fb = new Feedback(id, registrationId, rating, comments, LocalDateTime.now().format(FORMATTER));
        store.feedbacks.put(id, fb);
        return true;
    }

    public Optional<Feedback> getFeedbackByRegistrationId(int registrationId) {
        return store.feedbacks.values().stream()
                .filter(f -> f.getRegistrationId() == registrationId)
                .findFirst();
    }

    public List<Feedback> getAllFeedbacks() {
        return new ArrayList<>(store.feedbacks.values());
    }
}
