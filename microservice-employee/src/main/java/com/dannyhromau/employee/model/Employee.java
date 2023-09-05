package com.dannyhromau.employee.model;

import com.dannyhromau.employee.core.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "employee")
public class Employee extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "position_id", nullable = false)
    private UUID positionId;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "reg_date")
    private ZonedDateTime regDate;
    @Column(name = "is_fired")
    private boolean isFired;
    @Column(name = "fired_on")
    private ZonedDateTime firedOn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", insertable = false, updatable = false)
    private Position position;
    @Column(name = "is_fulltime")
    private boolean isFullTime;

}
