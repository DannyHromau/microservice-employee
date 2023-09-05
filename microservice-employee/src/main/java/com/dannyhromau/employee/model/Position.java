package com.dannyhromau.employee.model;

import com.dannyhromau.employee.core.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "position")
public class Position extends BaseEntity {
    @Column(name = "position", unique = true, nullable = false)
    private String position;
    @Column(name = "description", unique = true, nullable = false)
    private String description;
    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    private List<Employee> employeeList;

}
