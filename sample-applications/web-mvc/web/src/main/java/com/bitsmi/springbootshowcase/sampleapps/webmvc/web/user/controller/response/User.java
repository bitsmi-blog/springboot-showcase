package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Builder(toBuilder = true, builderClassName = "Builder")
@Schema(description = "User response data")
public record User(
        @Schema(description = "ID of the user")
        Long id,
        @Schema(description = "Username of the user")
        String username,
        @Schema(description = "Complete name of the user")
        String completeName,
        @Schema(description = "True if the user is active. False if exists but is disabled")
        Boolean active,
        @Schema(description = "List of group names where the user is enrolled")
        Set<String> groups,
        @Schema(description = "Date-time in ISO format when the user was created")
        LocalDateTime creationDate,
        @Schema(description = "Date-time in ISO format when the user was last updated")
        LocalDateTime lastUpdated
) {
    public User {
        groups = Set.copyOf(groups);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .append("completeName", completeName)
                .append("active", active)
                .append("groups", groups)
                .append("creationDate", creationDate)
                .append("lastUpdated", lastUpdated)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || o instanceof User other
                && Objects.equals(username, other.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
