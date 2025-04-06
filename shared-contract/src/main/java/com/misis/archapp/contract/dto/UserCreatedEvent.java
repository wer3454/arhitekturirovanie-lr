package com.misis.archapp.contract.dto;

import java.util.UUID;

public record UserCreatedEvent(
    UUID userId,
    String email,
    String name
) {}
