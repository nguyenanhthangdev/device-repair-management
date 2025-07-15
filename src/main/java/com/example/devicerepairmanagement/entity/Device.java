package com.example.devicerepairmanagement.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    private List<RepairOrderItem> repairItems;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private DeviceType type; // Loại thiết bị

    private String description;

    private LocalDate receivedDate;

    private String status; // ví dụ: "Đang sửa", "Đã sửa xong", "Đã trả"
}
