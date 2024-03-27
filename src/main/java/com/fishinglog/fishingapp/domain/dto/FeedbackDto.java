package com.fishinglog.fishingapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for feedback submitted by user.
 *
 * @since 2024-03-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {

    private String feedback;
}
