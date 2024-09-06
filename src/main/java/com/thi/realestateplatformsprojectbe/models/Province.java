package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "provinces")
public class Province {
    @Id
    private String code;
    private String name;

}
