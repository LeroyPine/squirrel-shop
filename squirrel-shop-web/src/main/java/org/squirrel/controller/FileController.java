package org.squirrel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.squirrel.constant.Constants;
import org.squirrel.dto.FileDto;
import org.squirrel.exception.BizException;

import java.io.File;
import java.io.IOException;

/**
 * @author luobaosong
 * @date 2024-07-27 14:24
 */
@Slf4j
@RestController
@RequestMapping("/file")
@Tag(name = "文件管理")
public class FileController {


    @Operation(summary = "上传文件", description = "上传文件")
    @PostMapping("/upload")
    public FileDto handleFileUpload(@Parameter(description = "File to upload", required = true)
                                    @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上床文件不能为空!");
        }
        // 上传文件的存储目录
        String uploadDir = "/Users/luobaosong/file/squirrel-shop";
        String filePath = uploadDir + "/" + file.getOriginalFilename();
        try {
            File dest = new File(filePath);
            file.transferTo(dest);
            log.info("Successfully uploaded file:{}", file.getOriginalFilename());
        } catch (Exception e) {
            log.error("handleFileUpload error:{}", file.getOriginalFilename(), e);
        }

        return FileDto.builder()
                .fileName(file.getOriginalFilename())
                .fullFileName(Constants.FILE_PREFIX + file.getOriginalFilename())
                .build();
    }

}
