package br.com.alura.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    private String description;

    private boolean status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "inactivated_at")
    private OffsetDateTime inactivatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now();
    }
}