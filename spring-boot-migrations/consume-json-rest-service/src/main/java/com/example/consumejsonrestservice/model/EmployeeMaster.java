package com.example.consumejsonrestservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "emp_master")
public class EmployeeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Integer empId;

    @Column(name = "emp_name")
    private String empName;

    @Column(name = "emp_sal")
    private Double empSal;

    @Column(name = "emp_status")
    private String empStatus;

    public Integer getEmpId() { return empId; }
    public void setEmpId(Integer empId) { this.empId = empId; }
    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }
    public Double getEmpSal() { return empSal; }
    public void setEmpSal(Double empSal) { this.empSal = empSal; }
    public String getEmpStatus() { return empStatus; }
    public void setEmpStatus(String empStatus) { this.empStatus = empStatus; }
}
