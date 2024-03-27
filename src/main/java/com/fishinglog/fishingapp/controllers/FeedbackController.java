package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.domain.dto.FeedbackDto;
import com.fishinglog.fishingapp.services.FeedbackService;
import com.fishinglog.fishingapp.services.auth.OwnershipService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling feedback submissions.
 *
 * @since 2024-03-19
 */
@RestController
@Log
public class FeedbackController {

    private final FeedbackService feedbackService;

    private final OwnershipService ownershipService;

    public FeedbackController(FeedbackService feedbackService, OwnershipService ownershipService) {
        this.feedbackService = feedbackService;
        this.ownershipService = ownershipService;
    }

    /**
     * Collects feedback from users.
     *
     * @param userId The ID of the user submitting the feedback.
     * @param feedbackDto The data transfer object containing the feedback details.
     * @param request The HTTP request object.
     * @return A response entity indicating the status of the feedback collection.
     */
    // POST /feedback?userId=123
    @PostMapping(path = "/feedback")
    public ResponseEntity<HttpStatus> collectFeedback(
            @RequestParam(value = "userId") Long userId,
            @RequestBody FeedbackDto feedbackDto,
            HttpServletRequest request)
    {
        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        boolean isFeedbackSaved = feedbackService.collectFeedback(feedbackDto.getFeedback());

        if(isFeedbackSaved) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
