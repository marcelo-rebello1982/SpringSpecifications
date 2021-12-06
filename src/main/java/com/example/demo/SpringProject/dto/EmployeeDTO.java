package com.example.demo.SpringProject.dto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
public class EmployeeDTO implements Serializable {
    @Id
    private Integer empId;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String empFirstName;

    @Size(max = 100)
    private String empLastName;

    @NotEmpty
    @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @NotNull
    @Pattern(
            regexp = "(^([+]\\d{2}([ ])?)?\\d{10}$)",
            message = "Number should be in format: {+91 1234567890, +911234567890, 1234567890}")
    private String empContactNumber;

    @NotNull
    private Integer pinCode;
    @NotEmpty
    private String city;
    @NotEmpty
    private String state;
    @NotEmpty
    private String country;
}