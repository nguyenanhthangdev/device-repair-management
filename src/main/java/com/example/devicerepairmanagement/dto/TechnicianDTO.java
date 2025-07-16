package com.example.devicerepairmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianDTO {
    private Long id;

    @NotBlank(message = "Tên nhân viên sửa chữa không được để trống")
    private String name;

     @NotBlank(message = "Chuyên môn nhân viên sửa chữa không được để trống")
    private String specialization;
}
