package com.example.demo.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Integer commentId;
    private String content;
    private String userName;
    private LocalDateTime createdTime;
    
	// 自訂日期格式 (年/月/日/分)
	public String getFormattedCreatedTime() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	    return createdTime.format(formatter);
	}
}