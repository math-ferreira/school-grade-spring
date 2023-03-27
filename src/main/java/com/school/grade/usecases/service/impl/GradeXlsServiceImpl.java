package com.school.grade.usecases.service.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import com.school.grade.entities.dto.csv.DisciplineCSVFile;
import com.school.grade.entities.dto.csv.HolidayCSVFile;
import com.school.grade.entities.dto.csv.SchoolDataCSVFile;
import com.school.grade.entities.dto.grade.request.DisciplineRequestDTO;
import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.request.HolidayRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDataRequestDTO;
import com.school.grade.usecases.service.GradeXlsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import static java.time.DayOfWeek.*;

@Service
public class GradeXlsServiceImpl implements GradeXlsService {

    @Override
    public GradeRequestDTO convertToGradeRequest(MultipartFile[] files) {

        MultipartFile schoolDataFile = Arrays.stream(files).filter(file -> file.getOriginalFilename().equals("dados_escolares.csv")).findFirst().get();
        MultipartFile disciplineFile = Arrays.stream(files).filter(file -> file.getOriginalFilename().equals("disciplinas.csv")).findFirst().get();
        MultipartFile holidayFile = Arrays.stream(files).filter(file -> file.getOriginalFilename().equals("feriados.csv")).findFirst().get();

        Reader schooldDatareader = null;
        Reader disciplinereader = null;
        Reader holidayreader = null;

        try {
            schooldDatareader = new InputStreamReader(schoolDataFile.getInputStream(), StandardCharsets.UTF_8);
            disciplinereader = new InputStreamReader(disciplineFile.getInputStream(), StandardCharsets.UTF_8);
            holidayreader = new InputStreamReader(holidayFile.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<SchoolDataCSVFile> schoolDataList = new CsvToBeanBuilder(schooldDatareader)
                .withType(SchoolDataCSVFile.class)
                .build()
                .parse()
                .stream().skip(1)
                .toList();

        List<DisciplineCSVFile> disciplineList = new CsvToBeanBuilder(disciplinereader)
                .withType(DisciplineCSVFile.class)
                .build()
                .parse()
                .stream().skip(1)
                .toList();

        List<HolidayCSVFile> holidayList = new CsvToBeanBuilder(holidayreader)
                .withType(HolidayCSVFile.class)
                .build()
                .parse()
                .stream().skip(1)
                .toList();

        return new GradeRequestDTO(
                buildSchoolData(schoolDataList),
                buildDisciplines(disciplineList),
                buildHolidays(holidayList)
        );
    }

    private SchoolDataRequestDTO buildSchoolData(List<SchoolDataCSVFile> schoolDataList) {
        return new SchoolDataRequestDTO(
                schoolDataList.get(0).getSemesterName(),
                schoolDataList.get(0).getCourseName(),
                schoolDataList.get(0).getBeginningSemester(),
                schoolDataList.get(0).getEndingSemester(),
                getDayOFWeek(schoolDataList.get(0).getBeginningDayOfWeek()),
                getDayOFWeek(schoolDataList.get(0).getEndingDayOfWeek())
        );
    }

    private List<DisciplineRequestDTO> buildDisciplines(List<DisciplineCSVFile> disciplineList) {

        return disciplineList.stream().map(discipline ->
                new DisciplineRequestDTO(
                        discipline.getDisciplineName(),
                        discipline.getDisciplineInitials(),
                        discipline.getWorkload(),
                        discipline.getPriorityOrder(),
                        discipline.getTimesPerWeek()
                )
        ).toList();
    }

    private List<HolidayRequestDTO> buildHolidays(List<HolidayCSVFile> holidayList) {

        return holidayList.stream().map(holiday ->
                new HolidayRequestDTO(
                        holiday.getHolidayName(),
                        holiday.getHolidayDate()
                )
        ).toList();
    }

    private DayOfWeek getDayOFWeek(String dayOfWeek) {
        return switch (dayOfWeek) {
            case "segunda" -> MONDAY;
            case "terca" -> TUESDAY;
            case "quarta" -> WEDNESDAY;
            case "quinta" -> THURSDAY;
            case "sexta" -> FRIDAY;
            case "sabado" -> SATURDAY;
            default -> DayOfWeek.SUNDAY;
        };
    }
}
