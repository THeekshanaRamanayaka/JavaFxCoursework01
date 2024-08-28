package model;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private String id;
    private String title;
    private String name;
    private String address;
    private String phone;
    private LocalDate day;

    public Customer(String text, String value, String text1, String text2, String text3, LocalDate value1) {
    }
}