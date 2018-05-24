package com.zomo.photo.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_sit")
    private String projectSit;

    @Column(name = "project_time")
    private Date projectTime;

    @Column(name = "project_banner_host")
    private String projectBannerHost;

    @Column(name = "project_key_image_host")
    private String projectKeyImageHost;

    @Column(name = "project_content")
    private String projectContent;

    @Column(name = "project_create_user_id")
    private Integer projectCreateUserId;

    @Column(name = "project_create_time")
    private Date projectCreateTime;

    @Column(name = "project_update_time")
    private Date projectUpdateTime;
}
