package com.example.demo.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussDTO {

	private Integer discussId;
	
	private String title;
	
	private String description;
	
	private String tag;

	private String youtubeVideoId;

	private Boolean isPublic; // true = 公開, false = 私人

	private LocalDateTime createdTime = LocalDateTime.now();

	private Integer userId;
	
}
