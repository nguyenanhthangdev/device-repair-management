package com.example.devicerepairmanagement.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepairOrderDTO {
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String code;

    @NotNull(message = "Ngày sửa không được để trống")
    private LocalDate repairDate;

    @NotBlank(message = "Trạng thái không được để trống")
    private String status;

    @NotNull(message = "Chi phí không được để trống")
    @PositiveOrZero(message = "Chi phí phải >= 0")
    private Double cost;

    @NotNull(message = "Khách hàng không được để trống")
    private Long customerId;

    @NotNull(message = "Kỹ thuật viên không được để trống")
    private Long technicianId;
}
