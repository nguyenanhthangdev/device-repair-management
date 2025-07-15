package com.example.devicerepairmanagement.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class DeviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Ví dụ: Laptop, Máy in, Ấm nước

    // Optional: nếu bạn muốn hiển thị loại cùng danh sách thiết bị
    @OneToMany(mappedBy = "type")
    private List<Device> devices;
}
