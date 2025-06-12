package com.example.demo.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
Entity name: Behavior
Table name: behavior
+-------------+-------+------------+----------+---------+--------+-------------+------+--------------+
| behavior_id | date  | start_time | end_time | subject | action | temperature | note | created_time |
+-------------+-------+------------+----------+---------+--------+-------------+------+--------------+
|      1      |       |   ........ | .....    | .....   |  ....  |    ......   | .... |    ........  | 
|      2      |       |   .......  | .....    | .....   |  ....  |    ......   | .... |    ........  |
+-------------+-------+------------+----------+---------+--------+-------------+------+--------------+
*/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "behavior")
public class Behavior {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自動生成 id
	@Column(name = "behavior_id")
	private Integer behaviorId;
	
	@Column(name = "date", nullable = false)
    private LocalDate date;
	
	@Column(name = "start_time", nullable = false)
    private LocalTime startTime;
	
	@Column(name = "end_time", nullable = false)
    private LocalTime endTime;

	@Column(name = "subject", nullable = false)
    private String subject;
	
	@Column(name = "action", nullable = false)
    private String action;
	
	@Column(name = "food")
	private String food;
	
	@Column(name = "temperature")
    private Float temperature;
	
	@Column(name = "note", length = 100)
    private String note;
	
	@Column(name = "created_time")
	private LocalDateTime createdTime = LocalDateTime.now();
	
	@ManyToOne(fetch = FetchType.LAZY) // 多對一預設為 Eager
	@JoinColumn(name = "discuss_id") // 資料庫欄位名
	private Discuss discuss;
	
	@ManyToOne(fetch = FetchType.LAZY) // 多對一預設為 Eager
	@JoinColumn(name = "user_id")
	private User user;

}
