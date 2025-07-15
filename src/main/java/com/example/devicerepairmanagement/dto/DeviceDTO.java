package com.example.devicerepairmanagement.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DeviceDTO {
    private Long id;

    @NotBlank(message = "Tên thiết bị không được để trống")
    private String name;

    private Long typeId; 

    private String typeName;

    private String description;

    @NotNull(message = "Ngày tiếp nhận là bắt buộc")
    private LocalDate receivedDate;

    @NotNull(message = "Trạng thái là bắt buộc")
    private String status;

    public DeviceDTO(Long id, String name, Long typeId, String typeName, String description, LocalDate receivedDate, String status) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.typeName = typeName;
        this.description = description;
        this.receivedDate = receivedDate;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getTypeId() { return typeId; }
    public void setTypeId(Long typeId) { this.typeId = typeId; }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getReceivedDate() { return receivedDate; }
    public void setReceivedDate(LocalDate receivedDate) { this.receivedDate = receivedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}