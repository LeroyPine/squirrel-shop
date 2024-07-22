package org.squirrel.dto;

import lombok.Data;

import java.util.List;

/**
 * @author luobaosong
 * @date 2024-07-22 18:59
 */
@Data
public class SquirrelPageDto<T> {

    private long page;
    private long pageSize;
    private long total;
    private List<T> records;


    public SquirrelPageDto() {
    }

    public SquirrelPageDto(long page, long pageSize, long total) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
    }

    public SquirrelPageDto(long page, long pageSize, long total, List<T> records) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records;
    }

}