package com.fishinglog.fishingapp.domain.dto.persisted;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for a user, encapsulating all relevant details.
 *
 * @since 2023-10-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private String email;
}
