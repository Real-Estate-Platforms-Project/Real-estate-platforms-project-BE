package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications", schema = "real_estate_platform")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @CreatedDate
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Lob
    @Column(name = "contend", nullable = false)
    private String contend;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @NotNull
    @Lob
    @Column(name = "image", nullable = false)
    private String image;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Transient
    private String formattedCreateNotification;

}