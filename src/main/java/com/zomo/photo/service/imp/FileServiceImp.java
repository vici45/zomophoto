package com.zomo.photo.service.imp;

import com.zomo.photo.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
@Slf4j
@Service
public class FileServiceImp implements IFileService {

    public String upload(MultipartFile file,String path){
        String fileName=file.getOriginalFilename();

        String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName=UUID.randomUUID().toString()+"."+fileExtensionName;
        log.info("begin upload file,uploadFile name:{},upload path:{}newFileName:{}",fileName,path,uploadFileName);

        File fileDir=new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile=new File(path,uploadFileName);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            log.error("upload error");
        }

        return targetFile.getName();
    }
}
