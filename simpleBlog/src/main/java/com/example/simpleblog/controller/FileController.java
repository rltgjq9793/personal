package com.example.simpleblog.controller;

import com.example.simpleblog.dto.upload.UploadResultDTO;
import org.springframework.core.io.Resource;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@Log4j2
public class FileController {

    @Value("${com.example.upload.path}")
    private String uploadPath;

    @Operation(summary = "Upload POST", description = "POST 방식으로 파일 등록")
    @PostMapping(value = "/upload",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(@RequestPart("files") List<MultipartFile> uploadFile) {

        if (uploadFile != null) {
            final List<UploadResultDTO> list = new ArrayList<>();

            uploadFile.forEach(multipartFile -> {
                String originalName = multipartFile.getOriginalFilename();

                String uuid = UUID.randomUUID().toString();

                Path savePath = Paths.get(uploadPath, uuid.toString() + "_" + originalName);

                try {
                    //이미지 파일의 종류라면
                    if (Files.probeContentType(savePath).startsWith("image")) {

                        multipartFile.transferTo(savePath);
                        File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalName);
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);

                        list.add(UploadResultDTO.builder()
                                .uuid(uuid)
                                .fileName(originalName)
                                .build());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return list;
        }
        return null;
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        HttpHeaders headers = new HttpHeaders();


        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
            log.info(resource.getFile().toPath());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @Operation(summary="removeFile", description ="DELETE 방식으로 파일 삭제")
    @DeleteMapping("/remove/{fileName}")
    public Map<String, Boolean> removeFile(@PathVariable String fileName){

        Map resultMap = new HashMap();
        boolean removed = false;

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
        File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);

        try {
            resource.getFile().delete();
            thumbnailFile.delete();
            removed = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        resultMap.put("result", removed);
        return resultMap;
    }
}

