package ru.skypro.homework.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateOrUpdateComment {
    @NotBlank
    @Size(min = 8, max = 64)
    private String text;
}
