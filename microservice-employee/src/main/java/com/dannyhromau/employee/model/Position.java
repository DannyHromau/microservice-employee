package com.dannyhromau.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private UUID id;
    @Column(name = "position", unique = true)
    private String position;
    @Column(name = "description", unique = true)
    private String description;
    @Column(name = "grade", unique = true)
    private double grade;
    @Column(name = "fulltime", unique = true)
    private boolean fulltime;
    @Column(name = "update_on", unique = true)
    private ZonedDateTime updateOn;
    @JsonIgnore
    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    private List<Employee> employeeList;

}
