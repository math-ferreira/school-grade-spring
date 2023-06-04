package com.school.grade.entities.mapper;

import com.school.grade.entities.dto.csv.DisciplineCSVFile;
import com.school.grade.entities.dto.csv.HolidayCSVFile;
import com.school.grade.entities.dto.csv.SchoolDataCSVFile;
import com.school.grade.entities.dto.grade.request.DisciplineRequestDTO;
import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.request.HolidayRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDataRequestDTO;
import com.school.grade.entities.utils.DayOfWeekUtil;
import com.school.grade.entities.utils.ReaderUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.Reader;
import java.util.List;

public class GradeRequestMapper {

    private static final String SCHOOL_DATA_FILENAME = "dados_escolares.csv";
    private static final String SUBJECTS_FILENAME = "disciplinas.csv";
    private static final String HOLIDAYS_FILENAME = "feriados.csv";

    public static GradeRequestDTO from(MultipartFile[] files) {
        Reader schooldDatareader = ReaderUtil.getFileByName(files, SCHOOL_DATA_FILENAME);
        Reader disciplineReader = ReaderUtil.getFileByName(files, SUBJECTS_FILENAME);
        Reader holidayReader = ReaderUtil.getFileByName(files, HOLIDAYS_FILENAME);

        List<SchoolDataCSVFile> schoolDataList = ReaderUtil.readerToObject(schooldDatareader, SchoolDataCSVFile.class);
        List<DisciplineCSVFile> subjectList = ReaderUtil.readerToObject(disciplineReader, DisciplineCSVFile.class);
        List<HolidayCSVFile> holidayList = ReaderUtil.readerToObject(holidayReader, HolidayCSVFile.class);

        return new GradeRequestDTO(
                buildSchoolData(schoolDataList),
                buildDisciplines(subjectList),
                buildHolidays(holidayList)
        );
    }

    private static SchoolDataRequestDTO buildSchoolData(List<SchoolDataCSVFile> schoolDataList) {
        SchoolDataCSVFile firstElement = schoolDataList.get(0);
        return new SchoolDataRequestDTO(
                firstElement.getSemesterName(),
                firstElement.getCourseName(),
                firstElement.getBeginningSemester(),
                firstElement.getEndingSemester(),
                firstElement.getBeginningVacation(),
                firstElement.getEndingVacation(),
                DayOfWeekUtil.getDayOFWeek(firstElement.getBeginningDayOfWeek()),
                DayOfWeekUtil.getDayOFWeek((firstElement.getEndingDayOfWeek()))
        );
    }

    private static List<DisciplineRequestDTO> buildDisciplines(List<DisciplineCSVFile> disciplineList) {

        return disciplineList.stream().map(discipline ->
                new DisciplineRequestDTO(
                        discipline.getDisciplineName(),
                        discipline.getDisciplineInitials(),
                        discipline.getTeacherName(),
                        discipline.getWorkload(),
                        discipline.getPriorityOrder(),
                        discipline.getTimesPerWeek()
                )
        ).toList();
    }

    private static List<HolidayRequestDTO> buildHolidays(List<HolidayCSVFile> holidayList) {

        return holidayList.stream().map(holiday ->
                new HolidayRequestDTO(
                        holiday.getHolidayName(),
                        holiday.getHolidayDate()
                )
        ).toList();
    }
}
