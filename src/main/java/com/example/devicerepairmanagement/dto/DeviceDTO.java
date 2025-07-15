package com.example.devicerepairmanagement.dto;

import java.time.LocalDate;

public class DeviceDTO {
    private Long id;
    private String name;
    private Long typeId; 
    private String typeName;
    private String description;
    private LocalDate receivedDate;
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