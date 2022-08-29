package ru.plifis.job4j_client_passport.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
public class PassportDto {

    private Integer series;
    private Integer number;
    private Timestamp expired;
    private String surname;
    private String firstname;
    private String address;
}