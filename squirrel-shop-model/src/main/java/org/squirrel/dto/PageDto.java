package org.squirrel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author luobaosong
 * @date 2024-07-22 18:27
 */
@Data
public class PageDto {

    @Schema(description = "第几页")
    private int page;

    @Schema(description = "分页数量")
    private int pageSize = 20;
}
