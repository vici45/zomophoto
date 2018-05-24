package com.zomo.photo.VO;

import lombok.Data;

import java.sql.Date;
@Data
public class ProjectDetailVo {
    private Integer id;

    private Integer projectId;

    private Date createTime;

    private String imageHost;

}
