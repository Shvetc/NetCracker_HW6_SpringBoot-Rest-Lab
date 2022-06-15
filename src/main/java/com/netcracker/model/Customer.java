package com.netcracker.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Pattern(regexp = "^[a-zа-яA-ZА-Я]+(?:[\\s-][a-zа-яA-ZА-Я]+)*$")
    private String region;

    private Double discount;


}
