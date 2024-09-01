package com.gustavolyra.music_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

public record AlbumDtoRequest(@Positive(message = "number can't be negative") Long id,
                              @NotBlank(message = "albumName cant't be null") String albumName,
                              @PastOrPresent(message = "date cant't be future") OffsetDateTime releaseDate,
                              @Positive(message = "number can't be negative") Long artistId) {
}
