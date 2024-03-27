package com.fishinglog.fishingapp.services;

/**
 * Service interface for collecting user feedback.
 *
 * @since 2024-03-19
 */
public interface FeedbackService {

    /**
     * Collects feedback from a user.
     *
     * @param feedback The feedback text provided by the user.
     * @return True if the feedback is successfully collected, false otherwise.
     */
    boolean collectFeedback(String feedback);
}
