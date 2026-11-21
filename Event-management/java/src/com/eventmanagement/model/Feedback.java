package com.eventmanagement.model;

/**
 * Model representing student Feedback.
 * Course: 25ITT32 Java Programming | Reg No: 31
 */
public class Feedback {
    private int feedbackId;
    private int registrationId;
    private int rating; // 1 to 5
    private String comments;
    private String submittedAt;

    public Feedback() {}

    public Feedback(int feedbackId, int registrationId, int rating, String comments, String submittedAt) {
        this.feedbackId = feedbackId;
        this.registrationId = registrationId;
        this.rating = rating;
        this.comments = comments;
        this.submittedAt = submittedAt;
    }

    public int getFeedbackId() { return feedbackId; }
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }

    public int getRegistrationId() { return registrationId; }
    public void setRegistrationId(int registrationId) { this.registrationId = registrationId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public String getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(String submittedAt) { this.submittedAt = submittedAt; }

    public String getStarDisplay() {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rating; i++) stars.append("★");
        for (int i = rating; i < 5; i++) stars.append("☆");
        return stars.toString();
    }

    @Override
    public String toString() {
        return String.format("FeedbackID: %d | Rating: %s (%d/5) | Comment: \"%s\"",
                feedbackId, getStarDisplay(), rating, comments);
    }
}
