package com.school.grade.entities.utils;

import com.opencsv.bean.CsvToBeanBuilder;
import com.school.grade.web.exception.handler.ElementNotFoundException;
import com.school.grade.web.exception.handler.UnprocessableEntityException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class ReaderUtil {

    public static Reader getFileByName(MultipartFile[] files, String fileName) {
        MultipartFile multipartFile = Arrays.stream(files)
                .filter(file -> file.getOriginalFilename().equals(fileName))
                .findFirst()
                .orElseThrow(() ->
                        new ElementNotFoundException("You should provide the file: " + fileName + " in the request")
                );

        try {
            return new InputStreamReader(multipartFile.getInputStream(), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new UnprocessableEntityException("Unprocessable entity file: " + fileName + " to Reader");
        }
    }

    public static <T> List<T> readerToObject(Reader reader, Class<T> genericsObject) {

        return new CsvToBeanBuilder(reader)
                .withType(genericsObject)
                .build()
                .parse()
                .stream().skip(1)
                .toList();
    }

}
