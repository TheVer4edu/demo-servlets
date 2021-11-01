package com.example.demoservlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Directory {

    public static List<FileInfo> listDirectory(String filepath) {
        Path pathObject = Paths.get(filepath);
        List<FileInfo> result = new ArrayList<>();
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(pathObject)) {
            for(Path file : stream) {
                result.add(new FileInfo(file.getFileName().toString(), Files.isDirectory(file), Files.getLastModifiedTime(file), Files.size(file)));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getParent(String filepath) {
        if (filepath.equals("/")) return null;
        String parent = new File(filepath).getAbsoluteFile().getParent();
        return parent.equals("/") ? parent : parent + "/";
    }

}
