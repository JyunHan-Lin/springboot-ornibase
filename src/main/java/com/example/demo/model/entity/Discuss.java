package com.example.demo.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
Entity name: Discuss
Table name: discuss
+------------+--------+-------------+--------+---------------------+--------------+
| discuss_id | title  | description |	tag	 |  youtube_video_id   | created_time | 
+------------+--------+-------------+--------+---------------------+--------------+
|     1      |  ..    |   ........  |   ...  |      ...........    |  ..........  |
|     2      |  ...   |   ........  |   ...  |      ...........    |  ..........  |
+------------+--------+-------------+--------+---------------------+--------------+
*/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "discuss")
public class Discuss {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "discuss_id")
	private Integer discussId;
	
	@Column(name = "title", nullable = false, length = 100)
	private String title;
	
	@Column(name = "description", nullable = false, length = 300)
	private String description;
	
	@Column(name = "tag", nullable = false, length = 50)
	private String tag;
	
	@Column(name = "youtube_video_id",nullable = false, length = 50)
	private String youtubeVideoId;
	
	@Column(name = "is_public")	// 是否公開
	private Boolean isPublic; // true = 公開, false = 私人

	@Column(name = "created_time")
	private LocalDateTime createdTime = LocalDateTime.now();

	@ManyToOne(fetch = FetchType.LAZY) // 多對一預設為 Eager
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "discuss")
	private List<Behavior> behaviors;
}