package com.dannyhromau.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private UUID id;
    @Column(name = "first_name", unique = true)
    private String firstName;
    @Column(name = "last_name", unique = true)
    private String lastName;
    @Column(name = "position_id", unique = true)
    private UUID positionId;
    @Column(name = "birth_date", unique = true)
    private LocalDate birthDate;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "reg_date", unique = true)
    private ZonedDateTime regDate;
    @Column(name = "update_on", unique = true)
    private ZonedDateTime updateOn;
    @Column(name = "is_fired", unique = true)
    private boolean isFired;
    @Column(name = "fired_on", unique = true)
    private ZonedDateTime firedOn;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", insertable = false, updatable = false)
    private Position position;

}
