package RNCP.TrocSkillHub.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDTO (
    Long id,
    String firstName,
    String lastName,
    String email,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password,
    String address,
    String city,
    String country,
    String phoneNumber,
    String description
) {}
