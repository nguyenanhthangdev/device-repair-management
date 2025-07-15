package com.example.devicerepairmanagement.dto;

public class DeviceTypeDTO {
    private Long id;
    private String name;

    public DeviceTypeDTO() {}

    public DeviceTypeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
