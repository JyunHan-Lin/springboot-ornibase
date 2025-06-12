package com.example.demo.model.dto;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDTO {
	private Integer userId; // 使用者ID
	private String username;// 使用者名稱
	private String email;	// 使用者Email
	private Boolean active;	// 
	private String role;	// 角色權限
	private List<DiscussDTO> discusses;
} // 如果講求一點, entity 跟 dto 兩邊命名會用不一樣的, 不然前端可以從命名反推程式(資安: 前端混淆)
