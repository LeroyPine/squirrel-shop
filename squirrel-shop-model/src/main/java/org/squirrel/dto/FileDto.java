package org.squirrel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luobaosong
 * @date 2024-07-27 16:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private String fileName;

    private String fullFileName;
}
