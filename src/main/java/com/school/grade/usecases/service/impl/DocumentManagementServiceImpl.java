package com.school.grade.usecases.service.impl;

import com.school.grade.entities.dto.document.DocumentClassDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import com.school.grade.entities.dto.grade.response.DisciplineScheduleResponseDTO;
import com.school.grade.usecases.service.DocumentManagementService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class DocumentManagementServiceImpl implements DocumentManagementService {

    @Override
    public void createDocument(List<DisciplineScheduleResponseDTO> gradeResponseList) {
        List<DocumentClassDTO> documentationSchedule = buildDocumentClass(gradeResponseList);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("TEC. X");

        createTitle(sheet);
        createDates(sheet);
        createMonths(sheet);
        createWeekDays(sheet, documentationSchedule);

        createFileOnDisk(workbook);

    }

    private List<DocumentClassDTO> buildDocumentHoliday(List<DisciplineScheduleResponseDTO> gradeResponseDTOList) {

        List<DocumentClassDTO> documentScheduleList = new ArrayList<>();

        for (DisciplineScheduleResponseDTO responseDTO : gradeResponseDTOList) {

            for (int j = 0; j < responseDTO.getScheduleClasses().size(); j++) {
                ScheduleDTO scheduleDTO = responseDTO.getScheduleClasses().get(j);

                documentScheduleList.add(
                        new DocumentClassDTO(
                                scheduleDTO.getDateOfClass(),
                                scheduleDTO.getDawOfWeek(),
                                responseDTO.getDisciplineInitials()
                        )
                );

            }
        }

        return documentScheduleList;

    }

    private List<DocumentClassDTO> buildDocumentClass(List<DisciplineScheduleResponseDTO> gradeResponseDTOList) {

        List<DocumentClassDTO> documentScheduleList = new ArrayList<>();

        for (DisciplineScheduleResponseDTO responseDTO : gradeResponseDTOList) {

            for (int j = 0; j < responseDTO.getScheduleClasses().size(); j++) {
                ScheduleDTO scheduleDTO = responseDTO.getScheduleClasses().get(j);

                documentScheduleList.add(
                        new DocumentClassDTO(
                                scheduleDTO.getDateOfClass(),
                                scheduleDTO.getDawOfWeek(),
                                responseDTO.getDisciplineInitials()
                        )
                );

            }
        }

        return documentScheduleList;

    }

    private void createTitle(XSSFSheet sheet) {

        Row row0 = sheet.createRow(0);
        Cell cell0 = row0.createCell(0);
        cell0.setCellValue("HORÁRIO TEÓRICO - 1º SEMESTRE DE 2023");

        Row row1 = sheet.createRow(1);
        Cell cell1 = row1.createCell(0);
        cell1.setCellValue("Téc. em Mecânica e Mecatrônica 22.1 N2/ 22.2 N2/ 23.1 N2");

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 32));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 32));
    }

    private void createDates(XSSFSheet sheet) {

        Object[] range = IntStream.rangeClosed(1, 32).boxed().map(Object::toString).toArray();

        Map<String, Object[]> data = new TreeMap<>();
        data.put("3", range);

        Row row = sheet.createRow(2);
        Object[] objArr = data.get("3");

        Cell dateCell = row.createCell(0);
        dateCell.setCellValue("DIA");

        int cellnum = 1;
        for (Object obj : objArr) {
            Cell cell = row.createCell(cellnum++);
            cell.setCellValue((String) obj);
        }
        Cell datesCell = row.createCell(objArr.length);
        datesCell.setCellValue("DIAS");
    }

    private void createMonths(XSSFSheet sheet) {

        List<String> months = new ArrayList<>();
        months.add(getYearMonthFormated(Month.JANUARY));
        months.add(getYearMonthFormated(Month.FEBRUARY));
        months.add(getYearMonthFormated(Month.MARCH));
        months.add(getYearMonthFormated(Month.APRIL));
        months.add(getYearMonthFormated(Month.MAY));
        months.add(getYearMonthFormated(Month.JUNE));
        months.add(getYearMonthFormated(Month.JULY));
        months.add(getYearMonthFormated(Month.AUGUST));
        months.add(getYearMonthFormated(Month.SEPTEMBER));
        months.add(getYearMonthFormated(Month.OCTOBER));
        months.add(getYearMonthFormated(Month.NOVEMBER));
        months.add(getYearMonthFormated(Month.DECEMBER));

        int rowNum = 3;

        for (String month : months) {

            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 4, 0, 0));

            Row row = sheet.createRow(rowNum);
            Cell cell = row.createCell(0);
            cell.setCellValue(month);
            rowNum = rowNum + 5;
        }

    }

    private void createWeekDays(XSSFSheet sheet, List<DocumentClassDTO> documentationSchedule) {

        int rowNum = 3;

        for (int i = 0; i < 12; i++) {

            Row row = sheet.getRow(rowNum);

            YearMonth yearMonthObject = YearMonth.of(LocalDate.now().getYear(), i + 1);
            int daysInMonth = yearMonthObject.lengthOfMonth();

            for (int j = 1; j <= daysInMonth; j++) {

                LocalDate currentDate = LocalDate.of(yearMonthObject.getYear(), yearMonthObject.getMonth(), j);

                String dayOfWeek = getDayOfWeekInitial(currentDate);

                Cell cell = row.createCell(j);
                cell.setCellValue(dayOfWeek);

                for (int k = 0; k < documentationSchedule.size(); k++) {
                    if (documentationSchedule.get(k).getDateOfClass().isEqual(currentDate)) {

                        Row rowDocOne;
                        Row rowDocTwo;
                        Row rowDocThree;

                        if (sheet.getRow(rowNum + 1) == null) {
                            rowDocOne = sheet.createRow(rowNum + 1);
                            rowDocTwo = sheet.createRow(rowNum + 2);
                            rowDocThree = sheet.createRow(rowNum + 3);
                        } else {
                            rowDocOne = sheet.getRow(rowNum + 1);
                            rowDocTwo = sheet.getRow(rowNum + 2);
                            rowDocThree = sheet.getRow(rowNum + 3);
                        }

                        Cell classCellOne = rowDocOne.createCell(j);
                        Cell classCellTwo = rowDocTwo.createCell(j);
                        Cell classCellThree = rowDocThree.createCell(j);

                        classCellOne.setCellValue(documentationSchedule.get(k).getDisciplineInitials());
                        classCellTwo.setCellValue(documentationSchedule.get(k).getDisciplineInitials());
                        classCellThree.setCellValue(documentationSchedule.get(k).getDisciplineInitials());
                    }
                }
            }
            rowNum += 5;
        }
    }

    private void createFileOnDisk(XSSFWorkbook workbook) {
        try {
            FileOutputStream out = new FileOutputStream("school_grade_"
                    + LocalDateTime.now()
                    .truncatedTo(ChronoUnit.SECONDS).toString()
                    .replace(":", "-")
                    + ".xlsx");
            workbook.write(out);
            out.close();
            System.out.println("xls file written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDayOfWeekInitial(LocalDate localDate) {
        return switch (localDate.getDayOfWeek()) {
            case SUNDAY -> "D";
            case MONDAY -> "S";
            case TUESDAY -> "T";
            case WEDNESDAY -> "Q";
            case THURSDAY -> "Q";
            case FRIDAY -> "S";
            default -> "S";
        };
    }

    private String getYearMonthFormated(Month month) {
        int currentYear = LocalDate.now().getYear();

        return switch (month) {
            case JANUARY -> "jan/" + currentYear;
            case FEBRUARY -> "fev/" + currentYear;
            case MARCH -> "mar/" + currentYear;
            case APRIL -> "abr/" + currentYear;
            case MAY -> "mai/" + currentYear;
            case JUNE -> "jun/" + currentYear;
            case JULY -> "jul/" + currentYear;
            case AUGUST -> "ago/" + currentYear;
            case SEPTEMBER -> "set/" + currentYear;
            case OCTOBER -> "out/" + currentYear;
            case NOVEMBER -> "nov/" + currentYear;
            default -> "dez/" + currentYear;
        };
    }
}




