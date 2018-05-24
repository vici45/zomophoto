package com.zomo.photo.VO;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class ProjectVo {

    private Integer id;

    private String projectName;

    private String projectSit;

    private Date projectTime;

    private String projectBannerHost;

    private String projectKeyImageHost;

    private String projectContent;

    private Integer projectCreateUserId;

    private Date projectCreateTime;

    private Date projectUpdateTime;

    private List<ProjectDetailVo> projectDetailVoList;
}
