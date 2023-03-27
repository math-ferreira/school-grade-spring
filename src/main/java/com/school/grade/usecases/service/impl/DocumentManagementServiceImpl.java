package com.school.grade.usecases.service.impl;

import com.school.grade.entities.dto.document.DocumentClassDTO;
import com.school.grade.entities.dto.document.DocumentHolidayDTO;
import com.school.grade.entities.dto.document.DocumentScheduleDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import com.school.grade.entities.dto.grade.response.DisciplineScheduleResponseDTO;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;
import com.school.grade.usecases.service.DocumentManagementService;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

@Service
public class DocumentManagementServiceImpl implements DocumentManagementService {

    @Override
    public void createDocument(GradeResponseDTO gradeResponseDTO) {
        DocumentScheduleDTO documentationSchedule = buildDocumentClass(gradeResponseDTO);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Agenda de aulas");

        createTitle(workbook, sheet, gradeResponseDTO.getSemesterName(), gradeResponseDTO.getCourseName());
        createDates(workbook, sheet);
        createMonths(workbook, sheet);
        fillScheduleClasses(workbook, sheet, documentationSchedule);
        finishBorders(workbook, sheet);

        createLegend(workbook, sheet, gradeResponseDTO.getCourseName(), documentationSchedule);

        saveFileOnDisk(workbook);

    }

    private DocumentScheduleDTO buildDocumentClass(GradeResponseDTO gradeResponseDTO) {

        List<DocumentClassDTO> documentScheduleList = new ArrayList<>();

        for (DisciplineScheduleResponseDTO responseDTO : gradeResponseDTO.getDisciplineSchedule()) {

            for (int j = 0; j < responseDTO.getScheduleClasses().size(); j++) {
                ScheduleDTO scheduleDTO = responseDTO.getScheduleClasses().get(j);

                documentScheduleList.add(
                        new DocumentClassDTO(
                                scheduleDTO.getDateOfClass(),
                                scheduleDTO.getDawOfWeek(),
                                responseDTO.getDisciplineInitials(),
                                responseDTO.getDisciplineName(),
                                responseDTO.getWorkload(),
                                responseDTO.getDisciplineColor()
                        )
                );
            }
        }

        List<DocumentHolidayDTO> documentHolidayList = gradeResponseDTO.getHolidays().stream()
                .map(holiday ->
                        new DocumentHolidayDTO(
                                holiday.getHolidayName(),
                                holiday.getHolidayDate())
                )
                .toList();

        return new DocumentScheduleDTO(
                documentScheduleList,
                documentHolidayList
        );

    }

    private void createTitle(XSSFWorkbook workbook, XSSFSheet sheet, String semesterName, String courseName) {

        Font font = workbook.createFont();

        font.setFontHeightInPoints((short) 11);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBold(true);

        Row row0 = sheet.createRow(0);
        Cell cell0 = row0.createCell(0);
        cell0.setCellValue(semesterName);

        Row row1 = sheet.createRow(1);
        Cell cell1 = row1.createCell(0);
        cell1.setCellValue(courseName);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 32));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 32));

        CellStyle cellStyleZero = row0.getSheet().getWorkbook().createCellStyle();
        cellStyleZero.setFont(font);
        cellStyleZero.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleZero.setAlignment(HorizontalAlignment.CENTER);
        cellStyleZero.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cell0.setCellStyle(cellStyleZero);

        CellStyle cellStyleOne = row1.getSheet().getWorkbook().createCellStyle();
        cellStyleOne.setFont(font);
        cellStyleOne.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleOne.setAlignment(HorizontalAlignment.CENTER);
        cellStyleOne.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cell1.setCellStyle(cellStyleOne);

    }

    private void createDates(XSSFWorkbook workbook, XSSFSheet sheet) {

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBold(true);


        Object[] range = IntStream.rangeClosed(1, 32).boxed().map(Object::toString).toArray();

        Map<String, Object[]> data = new TreeMap<>();
        data.put("3", range);

        Row row = sheet.createRow(2);
        Object[] objArr = data.get("3");

        Cell dateCell = row.createCell(0);
        dateCell.setCellValue("DIA");

        CellStyle cellStyle = row.getSheet().getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        dateCell.setCellStyle(cellStyle);

        int cellnum = 1;
        for (Object obj : objArr) {

            Cell cell = row.createCell(cellnum++);
            cell.setCellValue((String) obj);
            cell.setCellStyle(cellStyle);
        }
        Cell datesCell = row.createCell(objArr.length);
        datesCell.setCellValue("DIAS");
        datesCell.setCellStyle(cellStyle);
    }

    private void createMonths(XSSFWorkbook workbook, XSSFSheet sheet) {

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBold(true);

        List<String> months = new ArrayList<>();
        months.add(getFormattedYearMonth(Month.JANUARY));
        months.add(getFormattedYearMonth(Month.FEBRUARY));
        months.add(getFormattedYearMonth(Month.MARCH));
        months.add(getFormattedYearMonth(Month.APRIL));
        months.add(getFormattedYearMonth(Month.MAY));
        months.add(getFormattedYearMonth(Month.JUNE));
        months.add(getFormattedYearMonth(Month.JULY));
        months.add(getFormattedYearMonth(Month.AUGUST));
        months.add(getFormattedYearMonth(Month.SEPTEMBER));
        months.add(getFormattedYearMonth(Month.OCTOBER));
        months.add(getFormattedYearMonth(Month.NOVEMBER));
        months.add(getFormattedYearMonth(Month.DECEMBER));

        int rowNum = 3;

        for (String month : months) {

            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 4, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 4, 32, 32));

            Row row = sheet.createRow(rowNum);

            CellStyle monthCellStyle = row.getSheet().getWorkbook().createCellStyle();
            monthCellStyle.setFont(font);
            monthCellStyle.setAlignment(HorizontalAlignment.CENTER);
            monthCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            monthCellStyle.setBorderBottom(BorderStyle.MEDIUM);
            monthCellStyle.setBorderTop(BorderStyle.MEDIUM);
            monthCellStyle.setBorderRight(BorderStyle.THIN);
            monthCellStyle.setBorderLeft(BorderStyle.MEDIUM);

            Cell monthCell = row.createCell(0);
            monthCell.setCellValue(month);
            monthCell.setCellStyle(monthCellStyle);

            rowNum = rowNum + 5;
        }

    }

    private void finishBorders(Workbook workbook, XSSFSheet sheet) {

        int rowNum = 3;

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBold(true);

        for (int i = 0; i < 12; i++) {

            Row row = sheet.getRow(rowNum);

            CellStyle cellStyle = row.getSheet().getWorkbook().createCellStyle();
            cellStyle.setFont(font);

            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);

            for (int j = 0; j < 32; j++) {
                Cell cell;
                if (row.getCell(j) == null) {
                    cell = row.createCell(j);
                } else {
                    cell = row.getCell(j);
                }
                cell.setCellStyle(cellStyle);
            }

            rowNum = rowNum + 5;
        }

    }

    private void fillScheduleClasses(XSSFWorkbook workbook, XSSFSheet sheet, DocumentScheduleDTO documentationSchedule) {

        Font dayOfWeekfont = workbook.createFont();
        dayOfWeekfont.setFontHeightInPoints((short) 9);
        dayOfWeekfont.setFontName(HSSFFont.FONT_ARIAL);
        dayOfWeekfont.setBold(true);

        int rowNum = 3;

        for (int i = 0; i < 12; i++) {

            int numberSchoolDates = 0;

            Row row = sheet.getRow(rowNum);

            YearMonth yearMonthObject = YearMonth.of(LocalDate.now().getYear(), i + 1);
            int daysInMonth = yearMonthObject.lengthOfMonth();

            for (int j = 1; j <= daysInMonth; j++) {

                LocalDate currentDate = LocalDate.of(yearMonthObject.getYear(), yearMonthObject.getMonth(), j);

                String dayOfWeek = getFormattedDayOfWeek(currentDate);

                CellStyle dayOfWeekStyle = row.getSheet().getWorkbook().createCellStyle();
                dayOfWeekStyle.setFont(dayOfWeekfont);
                dayOfWeekStyle.setAlignment(HorizontalAlignment.CENTER);

                Cell cell = row.createCell(j);
                cell.setCellValue(dayOfWeek);
                cell.setCellStyle(dayOfWeekStyle);

                boolean isCurrentDayFilled = false;

                for (int k = 0; k < documentationSchedule.getDocumentClassList().size(); k++) {
                    if (documentationSchedule.getDocumentClassList().get(k).getDateOfClass().isEqual(currentDate)) {
                        numberSchoolDates++;
                        isCurrentDayFilled = true;

                        Row rowDocOne;
                        Row rowDocTwo;
                        Row rowDocThree;
                        Row rowDocFour;

                        if (sheet.getRow(rowNum + 1) == null) {
                            rowDocOne = sheet.createRow(rowNum + 1);
                            rowDocTwo = sheet.createRow(rowNum + 2);
                            rowDocThree = sheet.createRow(rowNum + 3);
                            rowDocFour = sheet.createRow(rowNum + 4);
                        } else {
                            rowDocOne = sheet.getRow(rowNum + 1);
                            rowDocTwo = sheet.getRow(rowNum + 2);
                            rowDocThree = sheet.getRow(rowNum + 3);
                            rowDocFour = sheet.getRow(rowNum + 4);
                        }

                        CellStyle cellStyleOne = rowDocOne.getSheet().getWorkbook().createCellStyle();
                        cellStyleOne.setAlignment(HorizontalAlignment.CENTER);
                        cellStyleOne.setVerticalAlignment(VerticalAlignment.CENTER);
                        cellStyleOne.setBorderBottom(BorderStyle.THIN);
                        cellStyleOne.setBorderTop(BorderStyle.THIN);
                        cellStyleOne.setBorderRight(BorderStyle.THIN);
                        cellStyleOne.setBorderLeft(BorderStyle.THIN);
                        cellStyleOne.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cellStyleOne.setFillForegroundColor(documentationSchedule.getDocumentClassList().get(k).getDisciplineColor().getIndex());

                        CellStyle cellStyleTwo = rowDocTwo.getSheet().getWorkbook().createCellStyle();
                        cellStyleTwo.setAlignment(HorizontalAlignment.CENTER);
                        cellStyleTwo.setVerticalAlignment(VerticalAlignment.CENTER);
                        cellStyleTwo.setBorderBottom(BorderStyle.THIN);
                        cellStyleTwo.setBorderTop(BorderStyle.THIN);
                        cellStyleTwo.setBorderRight(BorderStyle.THIN);
                        cellStyleTwo.setBorderLeft(BorderStyle.THIN);
                        cellStyleTwo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cellStyleTwo.setFillForegroundColor(documentationSchedule.getDocumentClassList().get(k).getDisciplineColor().getIndex());

                        CellStyle cellStyleThree = rowDocThree.getSheet().getWorkbook().createCellStyle();
                        cellStyleThree.setAlignment(HorizontalAlignment.CENTER);
                        cellStyleThree.setVerticalAlignment(VerticalAlignment.CENTER);
                        cellStyleThree.setBorderBottom(BorderStyle.THIN);
                        cellStyleThree.setBorderTop(BorderStyle.THIN);
                        cellStyleThree.setBorderRight(BorderStyle.THIN);
                        cellStyleThree.setBorderLeft(BorderStyle.THIN);
                        cellStyleThree.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cellStyleThree.setFillForegroundColor(documentationSchedule.getDocumentClassList().get(k).getDisciplineColor().getIndex());

                        CellStyle cellStyleFour = rowDocFour.getSheet().getWorkbook().createCellStyle();
                        cellStyleFour.setAlignment(HorizontalAlignment.CENTER);
                        cellStyleFour.setVerticalAlignment(VerticalAlignment.CENTER);
                        cellStyleFour.setBorderBottom(BorderStyle.THIN);
                        cellStyleFour.setBorderTop(BorderStyle.THIN);
                        cellStyleFour.setBorderRight(BorderStyle.THIN);
                        cellStyleFour.setBorderLeft(BorderStyle.THIN);
                        cellStyleFour.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cellStyleFour.setFillForegroundColor(documentationSchedule.getDocumentClassList().get(k).getDisciplineColor().getIndex());

                        Cell classCellOne = rowDocOne.createCell(j);
                        classCellOne.setCellStyle(cellStyleOne);

                        Cell classCellTwo = rowDocTwo.createCell(j);
                        classCellTwo.setCellStyle(cellStyleTwo);

                        Cell classCellThree = rowDocThree.createCell(j);
                        classCellThree.setCellStyle(cellStyleThree);

                        Cell classCellFour = rowDocFour.createCell(j);
                        classCellFour.setCellStyle(cellStyleThree);

                        classCellOne.setCellValue("IP");
                        classCellTwo.setCellValue(documentationSchedule.getDocumentClassList().get(k).getDisciplineInitials());
                        classCellThree.setCellValue(documentationSchedule.getDocumentClassList().get(k).getDisciplineInitials());
                        classCellFour.setCellValue(documentationSchedule.getDocumentClassList().get(k).getDisciplineInitials());
                    }
                }

                if (!isCurrentDayFilled) {
                    Row rowDocOne;
                    Row rowDocTwo;
                    Row rowDocThree;
                    Row rowDocFour;
                    Row rowDocFive;

                    if (sheet.getRow(rowNum) == null) {
                        rowDocOne = sheet.createRow(rowNum);
                    } else {
                        rowDocOne = sheet.getRow(rowNum);
                    }

                    Cell classCellOne = rowDocOne.getCell(j);

                    CellStyle cellStyleOne = rowDocOne.getSheet().getWorkbook().createCellStyle();
                    cellStyleOne.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyleOne.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

                    cellStyleOne.setBorderBottom(BorderStyle.THIN);
                    cellStyleOne.setBorderTop(BorderStyle.THIN);
                    cellStyleOne.setBorderRight(BorderStyle.THIN);
                    cellStyleOne.setBorderLeft(BorderStyle.THIN);

                    classCellOne.setCellStyle(cellStyleOne);

                    if (sheet.getRow(rowNum + 1) == null) {
                        rowDocTwo = sheet.createRow(rowNum + 1);
                    } else {
                        rowDocTwo = sheet.getRow(rowNum + 1);
                    }

                    Cell classCellTwo = rowDocTwo.createCell(j);

                    CellStyle cellStyleTwo = rowDocTwo.getSheet().getWorkbook().createCellStyle();
                    cellStyleTwo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyleTwo.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

                    cellStyleTwo.setBorderBottom(BorderStyle.THIN);
                    cellStyleTwo.setBorderTop(BorderStyle.THIN);
                    cellStyleTwo.setBorderRight(BorderStyle.THIN);
                    cellStyleTwo.setBorderLeft(BorderStyle.THIN);

                    classCellTwo.setCellStyle(cellStyleTwo);

                    if (sheet.getRow(rowNum + 2) == null) {
                        rowDocThree = sheet.createRow(rowNum + 2);
                    } else {
                        rowDocThree = sheet.getRow(rowNum + 2);
                    }

                    Cell classCellThree = rowDocThree.createCell(j);

                    CellStyle cellStyleThree = rowDocThree.getSheet().getWorkbook().createCellStyle();
                    cellStyleThree.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyleThree.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

                    cellStyleThree.setBorderBottom(BorderStyle.THIN);
                    cellStyleThree.setBorderTop(BorderStyle.THIN);
                    cellStyleThree.setBorderRight(BorderStyle.THIN);
                    cellStyleThree.setBorderLeft(BorderStyle.THIN);

                    classCellThree.setCellStyle(cellStyleThree);

                    if (sheet.getRow(rowNum + 3) == null) {
                        rowDocFour = sheet.createRow(rowNum + 3);
                    } else {
                        rowDocFour = sheet.getRow(rowNum + 3);
                    }

                    Cell classCellFour = rowDocFour.createCell(j);

                    CellStyle cellStyleFour = rowDocFour.getSheet().getWorkbook().createCellStyle();
                    cellStyleFour.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyleFour.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

                    cellStyleFour.setBorderBottom(BorderStyle.THIN);
                    cellStyleFour.setBorderTop(BorderStyle.THIN);
                    cellStyleFour.setBorderRight(BorderStyle.THIN);
                    cellStyleFour.setBorderLeft(BorderStyle.THIN);

                    classCellFour.setCellStyle(cellStyleFour);

                    if (sheet.getRow(rowNum + 4) == null) {
                        rowDocFive = sheet.createRow(rowNum + 4);
                    } else {
                        rowDocFive = sheet.getRow(rowNum + 4);
                    }

                    Cell classCellFive = rowDocFive.createCell(j);

                    CellStyle cellStyleFive = rowDocFive.getSheet().getWorkbook().createCellStyle();
                    cellStyleFive.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyleFive.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

                    cellStyleFive.setBorderBottom(BorderStyle.THIN);
                    cellStyleFive.setBorderTop(BorderStyle.THIN);
                    cellStyleFive.setBorderRight(BorderStyle.THIN);
                    cellStyleFive.setBorderLeft(BorderStyle.THIN);

                    classCellFive.setCellStyle(cellStyleFive);

                }

                for (int k = 0; k < documentationSchedule.getDocumentHolidayList().size(); k++) {
                    if (documentationSchedule.getDocumentHolidayList().get(k).getHolidayDate().isEqual(currentDate)) {

                        Row rowDoc = sheet.getRow(rowNum + 1);

                        Font font = workbook.createFont();

                        font.setFontHeightInPoints((short) 7);
                        font.setFontName(HSSFFont.FONT_ARIAL);
                        font.setBold(true);
                        font.setColor(IndexedColors.WHITE.getIndex());

                        CellStyle cellStyleHoliday = rowDoc.getSheet().getWorkbook().createCellStyle();
                        cellStyleHoliday.setWrapText(true);
                        cellStyleHoliday.setFont(font);
                        cellStyleHoliday.setAlignment(HorizontalAlignment.CENTER);
                        cellStyleHoliday.setVerticalAlignment(VerticalAlignment.CENTER);
                        cellStyleHoliday.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cellStyleHoliday.setFillForegroundColor(IndexedColors.RED.getIndex());
                        cellStyleHoliday.setBorderBottom(BorderStyle.THIN);
                        cellStyleHoliday.setBorderTop(BorderStyle.THIN);
                        cellStyleHoliday.setBorderRight(BorderStyle.THIN);
                        cellStyleHoliday.setBorderLeft(BorderStyle.THIN);
                        cellStyleHoliday.setRotation((short) 90);

                        Cell classCellOne = rowDoc.createCell(j);
                        classCellOne.setCellValue(documentationSchedule.getDocumentHolidayList().get(k).getHolidayName().toUpperCase());
                        classCellOne.setCellStyle(cellStyleHoliday);

                        sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 4, j, j));

                    }
                }

                if (j == daysInMonth) {

                    for (int l = 0; l <= 4; l++) {

                        Row daysRow;

                        if (sheet.getRow(rowNum + l) == null) {
                            daysRow = sheet.createRow(rowNum + l);
                        } else {
                            daysRow = sheet.getRow(rowNum + l);
                        }

                        CellStyle daysCellStyle = daysRow.getSheet().getWorkbook().createCellStyle();
                        daysCellStyle.setAlignment(HorizontalAlignment.CENTER);
                        daysCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                        daysCellStyle.setBorderBottom(BorderStyle.MEDIUM);
                        daysCellStyle.setBorderTop(BorderStyle.MEDIUM);
                        daysCellStyle.setBorderLeft(BorderStyle.MEDIUM);
                        daysCellStyle.setBorderRight(BorderStyle.MEDIUM);

                        Cell daysCell = daysRow.createCell(32);
                        daysCell.setCellValue(numberSchoolDates);
                        daysCell.setCellStyle(daysCellStyle);

                    }

                }
            }


            rowNum += 5;
        }
    }

    private void createLegend(XSSFWorkbook workbook, XSSFSheet sheet, String courseName, DocumentScheduleDTO documentationSchedule) {

        List<DocumentClassDTO> documentClassDistinctByInitials = documentationSchedule.getDocumentClassList().stream().distinct().toList();
        documentationSchedule.setDocumentClassList(documentClassDistinctByInitials);

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBold(true);

        sheet.addMergedRegion(new CellRangeAddress(2, 2, 34, 40));

        Row courseTableRow = sheet.getRow(2);
        CellStyle courseTableCellStyle = courseTableRow.getSheet().getWorkbook().createCellStyle();
        courseTableCellStyle.setFont(font);
        courseTableCellStyle.setAlignment(HorizontalAlignment.CENTER);

        Cell courseTableCell = courseTableRow.createCell(34);
        courseTableCell.setCellStyle(courseTableCellStyle);
        courseTableCell.setCellValue(courseName);

        Row tablerow = sheet.getRow(3);

        Cell disciplineTitleCell = tablerow.createCell(34);
        sheet.autoSizeColumn(34);
        disciplineTitleCell.setCellStyle(courseTableCellStyle);
        disciplineTitleCell.setCellValue("DISCIPLINA");

        Cell legendTitleCell = tablerow.createCell(35);
        sheet.autoSizeColumn(35);
        legendTitleCell.setCellStyle(courseTableCellStyle);
        legendTitleCell.setCellValue("LEGENDA");

        Cell teacherTitleCell = tablerow.createCell(36);
        sheet.autoSizeColumn(36);
        teacherTitleCell.setCellStyle(courseTableCellStyle);
        teacherTitleCell.setCellValue("PROFESSOR");

        Cell registerTitleCell = tablerow.createCell(37);
        sheet.autoSizeColumn(37);
        registerTitleCell.setCellStyle(courseTableCellStyle);
        registerTitleCell.setCellValue("REGISTRO");

        Cell workloadTitleCell = tablerow.createCell(38);
        sheet.autoSizeColumn(38);
        workloadTitleCell.setCellStyle(courseTableCellStyle);
        workloadTitleCell.setCellValue("CH TOTAL");

        Cell presencialWorkloadTitleCell = tablerow.createCell(39);
        sheet.autoSizeColumn(39);
        presencialWorkloadTitleCell.setCellStyle(courseTableCellStyle);
        presencialWorkloadTitleCell.setCellValue("CH PRES");

        Cell remoteWorkloadTitleCell = tablerow.createCell(40);
        sheet.autoSizeColumn(40);
        remoteWorkloadTitleCell.setCellStyle(courseTableCellStyle);
        remoteWorkloadTitleCell.setCellValue("CH EAD");

        for (int i = 0; i < documentationSchedule.getDocumentClassList().size(); i++) {

            Font documentClassFont = workbook.createFont();
            documentClassFont.setFontHeightInPoints((short) 9);
            documentClassFont.setFontName(HSSFFont.FONT_ARIAL);
            documentClassFont.setBold(false);

            Row documentClassrow = sheet.getRow(i + 5);

            CellStyle documentClassCellStyle = documentClassrow.getSheet().getWorkbook().createCellStyle();
            documentClassCellStyle.setAlignment(HorizontalAlignment.CENTER);

            Cell currentDisciplineCell = documentClassrow.createCell(34);
            sheet.autoSizeColumn(34);
            currentDisciplineCell.setCellStyle(documentClassCellStyle);
            currentDisciplineCell.setCellValue(documentationSchedule.getDocumentClassList().get(i).getDisciplineName());

            Cell currentLegendTitleCell = documentClassrow.createCell(35);
            sheet.autoSizeColumn(35);

            CellStyle currentLegendTitleCellStyle = documentClassrow.getSheet().getWorkbook().createCellStyle();
            currentLegendTitleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            currentLegendTitleCellStyle.setFillForegroundColor(documentationSchedule.getDocumentClassList().get(i).getDisciplineColor().index);
            currentLegendTitleCell.setCellStyle(currentLegendTitleCellStyle);
            currentLegendTitleCell.setCellValue(documentationSchedule.getDocumentClassList().get(i).getDisciplineInitials());

            Cell currentWorkloadTitleCell = documentClassrow.createCell(38);
            sheet.autoSizeColumn(38);
            currentWorkloadTitleCell.setCellStyle(documentClassCellStyle);
            currentWorkloadTitleCell.setCellValue(documentationSchedule.getDocumentClassList().get(i).getWorkload());

        }

    }


    private void saveFileOnDisk(XSSFWorkbook workbook) {
        try {
            String path="arquivos_gerados";
            File file = new File(path);

            if (!file.exists()) {
                file.mkdir();
                System.out.println("Folder created: arquivos_gerados");
            }

            FileOutputStream out = new FileOutputStream("arquivos_gerados/school_grade_"
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

    private String getFormattedDayOfWeek(LocalDate localDate) {
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

    private String getFormattedYearMonth(Month month) {
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




