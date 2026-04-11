package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class Ad {
    private int author;
    private String title;
    private int pk;
    private int price;
    private String image;
}
