package com.codewitharpan.blog.services.impl;

import com.codewitharpan.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {



    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //File Name
        String name=file.getOriginalFilename();

        //random name generate file
        String randomId= UUID.randomUUID().toString();
        String fileName=randomId.concat(name.substring(name.lastIndexOf(".")));

        //Fullpath
        String filePath=path+ File.separator + fileName;

        System.out.println(filePath);

        File f = new File(path);

       // create Folder if not created
        if(!f.exists()) {
            f.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));


        return fileName;

    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {


        String fullPath=path +File.separator +filename;

        InputStream is=new FileInputStream(fullPath);


        return is;
    }
}
