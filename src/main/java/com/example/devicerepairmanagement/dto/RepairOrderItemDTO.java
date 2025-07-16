package com.example.devicerepairmanagement.dto;

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
public class RepairOrderItemDTO {
    private Long id;

    @NotNull(message = "Đơn hàng không được để trống")
    private Long repairOrderId;

    @NotNull(message = "Thiết bị cần sửa chữa không được để trống")
    private Long deviceId;

    private String deviceName;

    private String note; 

    @NotNull(message = "Chi phí không được để trống")
    @PositiveOrZero(message = "Chi phí phải >= 0")
    private Double cost; 
}
