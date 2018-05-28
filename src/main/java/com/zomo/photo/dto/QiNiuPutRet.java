package com.zomo.photo.dto;

import lombok.Data;

@Data
public final class QiNiuPutRet {
    private String key;
    private String hash;
    private String bucket;
    private Integer width;
    private Integer height;

}
