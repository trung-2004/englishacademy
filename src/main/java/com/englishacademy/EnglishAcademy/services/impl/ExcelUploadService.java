package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.repositories.SessionRepository;
import com.englishacademy.EnglishAcademy.repositories.TestInputSessionRepository;
import com.englishacademy.EnglishAcademy.repositories.TestOfflineSessionRepository;
import com.englishacademy.EnglishAcademy.repositories.TestOnlineSessionRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExcelUploadService {
    private final SessionRepository sessionRepository;
    private final TestInputSessionRepository testInputSessionRepository;
    private final TestOnlineSessionRepository testOnlineSessionRepository;
    private final TestOfflineSessionRepository testOfflineSessionRepository;
    //private final

    public boolean isValidExcelFile(MultipartFile file){
        if (file != null) {
            return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
        }
        return false;
    }
    public List<QuestionTestInput> getCustomersDataFromExcel(InputStream inputStream, TestInput testInput){
        List<QuestionTestInput> questionTestInputList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("TestInput");// name sheet
            int rowIndex =0;
            int orderTop = 0;
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                //int cellIndex = 0;
                int totalQuestion = 0;
                QuestionTestInput questionTestInput = new QuestionTestInput();
                for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    switch (cellIndex){
                        case 0 -> questionTestInput.setTitle(cell.getStringCellValue().trim());
                        case 1 -> {
                            var id = cell.getNumericCellValue();
                            Session session = sessionRepository.findById((long)id)
                                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
                            TestInputSession testInputSessionExiting = testInputSessionRepository.findByTestInputAndSession(testInput, session);
                            if (testInputSessionExiting == null){
                                totalQuestion = 1;
                                TestInputSession testInputSession = TestInputSession.builder()
                                        .session(session)
                                        .testInput(testInput)
                                        .orderTop(orderTop)
                                        .totalQuestion(totalQuestion)
                                        .build();
                                testInputSessionRepository.save(testInputSession);
                                questionTestInput.setTestInputSession(testInputSession);
                                orderTop += 1;
                            } else {
                                questionTestInput.setTestInputSession(testInputSessionExiting);
                                testInputSessionExiting.setTotalQuestion(testInputSessionExiting.getTotalQuestion() + 1);
                            }

                        }
                        case 2 -> //questionTestInput.setAudiomp3(cell.getStringCellValue());
                        {
                            if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
                                questionTestInput.setAudiomp3(" ");
                            } else {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestInput.setAudiomp3(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestInput.setAudiomp3(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestInput.setAudiomp3(" ");
                                        break;
                                }
                            }
                        }
                        case 3 -> //questionTestInput.setImage(cell.getStringCellValue());
                        {
                            if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
                                questionTestInput.setImage(" ");
                            } else {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestInput.setImage(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestInput.setImage(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestInput.setImage(" ");
                                        break;
                                }
                            }
                        }

                        case 4 -> //questionTestInput.setParagraph(cell.getStringCellValue());
                        {
                            if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
                                questionTestInput.setParagraph(" ");
                            } else {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestInput.setParagraph(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestInput.setParagraph(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestInput.setParagraph(" ");
                                        break;
                                }
                            }
                        }
                        case 5 ->
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    questionTestInput.setOption1(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    questionTestInput.setOption1(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                    break;
                                default:
                                    questionTestInput.setOption1(null);
                                    break;
                            }
                        }
                        case 6 ->
                        {
                            {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestInput.setOption2(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestInput.setOption2(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestInput.setOption2(null);
                                        break;
                                }
                            }
                        }
                        case 7 ->
                        {
                            {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestInput.setOption3(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestInput.setOption3(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestInput.setOption3(null);
                                        break;
                                }
                            }
                        }
                        case 8 ->
                        {
                            {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestInput.setOption4(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestInput.setOption4(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestInput.setOption4(null);
                                        break;
                                }
                            }
                        }
                        case 9 ->
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    questionTestInput.setCorrectanswer(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    questionTestInput.setCorrectanswer(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                    break;
                                default:
                                    questionTestInput.setCorrectanswer(null);
                                    break;
                            }
                        }
                        case 10 -> questionTestInput.setType((int) cell.getNumericCellValue());
                        case 11 -> questionTestInput.setPart((int) cell.getNumericCellValue());
                        case 12 -> //questionTestInput.setOrderTop((int) cell.getNumericCellValue());
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    questionTestInput.setOrderTop(Integer.valueOf(cell.getStringCellValue()));
                                    break;
                                case NUMERIC:
                                    questionTestInput.setOrderTop(Integer.valueOf(NumberToTextConverter.toText(cell.getNumericCellValue())));
                                    break;
                                default:
                                    questionTestInput.setOrderTop(0);
                                    break;
                            }
                        }
                        default -> {
                        }
                    }
                }
                /*while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    //Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    switch (cellIndex){
                        case 0 -> questionTestInput.setTitle(cell.getStringCellValue().trim());
                        case 1 -> {
                            var id = cell.getNumericCellValue();
                            Session session = sessionRepository.findById((long)id)
                                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
                            TestInputSession testInputSessionExiting = testInputSessionRepository.findByTestInputAndSession(testInput, session);
                            if (testInputSessionExiting == null){
                                totalQuestion = 1;
                                orderTop = orderTop + 1;
                                TestInputSession testInputSession = TestInputSession.builder()
                                        .session(session)
                                        .testInput(testInput)
                                        .orderTop(orderTop)
                                        .totalQuestion(totalQuestion)
                                        .build();
                                testInputSessionRepository.save(testInputSession);
                                questionTestInput.setTestInputSession(testInputSession);


                            } else {
                                questionTestInput.setTestInputSession(testInputSessionExiting);
                                testInputSessionExiting.setTotalQuestion(testInputSessionExiting.getTotalQuestion() + 1);
                            }

                        }
                        case 2 -> //questionTestInput.setAudiomp3(cell.getStringCellValue());
                        {
                            if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
                                questionTestInput.setAudiomp3(" ");
                            } else {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestInput.setAudiomp3(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestInput.setAudiomp3(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestInput.setAudiomp3(" ");
                                        break;
                                }
                            }
                        }
                        case 3 -> //questionTestInput.setImage(cell.getStringCellValue());
                        {
                            if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
                                questionTestInput.setImage(" ");
                            } else {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestInput.setImage(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestInput.setImage(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestInput.setImage(" ");
                                        break;
                                }
                            }
                        }

                        case 4 -> //questionTestInput.setParagraph(cell.getStringCellValue());
                        {
                            if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
                                questionTestInput.setParagraph(" ");
                            } else {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestInput.setParagraph(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestInput.setParagraph(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestInput.setParagraph(" ");
                                        break;
                                }
                            }
                        }
                        case 5 ->
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    questionTestInput.setOption1(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    questionTestInput.setOption1(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                    break;
                                default:
                                    questionTestInput.setOption1(null);
                                    break;
                            }
                        }
                        case 6 ->
                        {
                            {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestInput.setOption2(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestInput.setOption2(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestInput.setOption2(null);
                                        break;
                                }
                            }
                        }
                        case 7 ->
                        {
                            {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestInput.setOption3(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestInput.setOption3(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestInput.setOption3(null);
                                        break;
                                }
                            }
                        }
                        case 8 ->
                        {
                            {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestInput.setOption4(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestInput.setOption4(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestInput.setOption4(null);
                                        break;
                                }
                            }
                        }
                        case 9 ->
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    questionTestInput.setCorrectanswer(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    questionTestInput.setCorrectanswer(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                    break;
                                default:
                                    questionTestInput.setCorrectanswer(null);
                                    break;
                            }
                        }
                        case 10 -> questionTestInput.setType((int) cell.getNumericCellValue());
                        case 11 -> questionTestInput.setPart((int) cell.getNumericCellValue());
                        case 12 -> //questionTestInput.setOrderTop((int) cell.getNumericCellValue());
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    questionTestInput.setOrderTop(Integer.valueOf(cell.getStringCellValue()));
                                    break;
                                case NUMERIC:
                                    questionTestInput.setOrderTop(Integer.valueOf(NumberToTextConverter.toText(cell.getNumericCellValue())));
                                    break;
                                default:
                                    questionTestInput.setOrderTop(0);
                                    break;
                            }
                        }
                        default -> {
                        }
                    }
                    cellIndex++;
                    totalQuestion++;
                }*/
                questionTestInputList.add(questionTestInput);
            }
            workbook.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

        return questionTestInputList;
    }

    public List<QuestionTestOnline> getCustomersDataFromExcelOnline(InputStream inputStream, TestOnline testOnline){
        List<QuestionTestOnline> questionTestOnlineList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("TestOnline");// name sheet
            int rowIndex =0;
            int orderTop = 0;
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                //int cellIndex = 0;
                int totalQuestion = 0;
                QuestionTestOnline questionTestOnline = new QuestionTestOnline();
                for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    switch (cellIndex){
                        case 0 -> questionTestOnline.setTitle(cell.getStringCellValue().trim());
                        case 1 -> {
                            var id = cell.getNumericCellValue();
                            Session session = sessionRepository.findById((long)id)
                                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
                            TestOnlineSession testOnlineSessionExiting = testOnlineSessionRepository.findByTestOnlineAndSession(testOnline, session);
                            if (testOnlineSessionExiting == null){
                                totalQuestion = 1;
                                TestOnlineSession testOnlineSession = TestOnlineSession.builder()
                                        .session(session)
                                        .testOnline(testOnline)
                                        .orderTop(orderTop)
                                        .totalQuestion(totalQuestion)
                                        .build();
                                testOnlineSessionRepository.save(testOnlineSession);
                                questionTestOnline.setTestOnlineSession(testOnlineSession);
                                orderTop += 1;
                            } else {
                                questionTestOnline.setTestOnlineSession(testOnlineSessionExiting);
                                testOnlineSessionExiting.setTotalQuestion(testOnlineSessionExiting.getTotalQuestion() + 1);
                            }

                        }
                        case 2 -> //questionTestInput.setAudiomp3(cell.getStringCellValue());
                        {
                            if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
                                questionTestOnline.setAudiomp3(" ");
                            } else {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestOnline.setAudiomp3(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestOnline.setAudiomp3(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestOnline.setAudiomp3(" ");
                                        break;
                                }
                            }
                        }
                        case 3 -> //questionTestInput.setImage(cell.getStringCellValue());
                        {
                            if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
                                questionTestOnline.setImage(" ");
                            } else {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestOnline.setImage(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestOnline.setImage(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestOnline.setImage(" ");
                                        break;
                                }
                            }
                        }

                        case 4 -> //questionTestInput.setParagraph(cell.getStringCellValue());
                        {
                            if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
                                questionTestOnline.setParagraph(" ");
                            } else {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestOnline.setParagraph(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestOnline.setParagraph(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestOnline.setParagraph(" ");
                                        break;
                                }
                            }
                        }
                        case 5 ->
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    questionTestOnline.setOption1(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    questionTestOnline.setOption1(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                    break;
                                default:
                                    questionTestOnline.setOption1(null);
                                    break;
                            }
                        }
                        case 6 ->
                        {
                            {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestOnline.setOption2(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestOnline.setOption2(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestOnline.setOption2(null);
                                        break;
                                }
                            }
                        }
                        case 7 ->
                        {
                            {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestOnline.setOption3(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestOnline.setOption3(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestOnline.setOption3(null);
                                        break;
                                }
                            }
                        }
                        case 8 ->
                        {
                            {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestOnline.setOption4(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestOnline.setOption4(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestOnline.setOption4(null);
                                        break;
                                }
                            }
                        }
                        case 9 ->
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    questionTestOnline.setCorrectanswer(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    questionTestOnline.setCorrectanswer(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                    break;
                                default:
                                    questionTestOnline.setCorrectanswer(null);
                                    break;
                            }
                        }
                        case 10 -> questionTestOnline.setType((int) cell.getNumericCellValue());
                        case 11 -> questionTestOnline.setPart((int) cell.getNumericCellValue());
                        case 12 -> //questionTestInput.setOrderTop((int) cell.getNumericCellValue());
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    questionTestOnline.setOrderTop(Integer.valueOf(cell.getStringCellValue()));
                                    break;
                                case NUMERIC:
                                    questionTestOnline.setOrderTop(Integer.valueOf(NumberToTextConverter.toText(cell.getNumericCellValue())));
                                    break;
                                default:
                                    questionTestOnline.setOrderTop(0);
                                    break;
                            }
                        }
                        default -> {
                        }
                    }
                }
                questionTestOnlineList.add(questionTestOnline);
            }
            workbook.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

        return questionTestOnlineList;
    }

    public List<QuestionTestOffline> getCustomersDataFromExcelOffline(InputStream inputStream, TestOffline testOffline){
        List<QuestionTestOffline> questionTestOfflineList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("TestOffline");// name sheet
            int rowIndex =0;
            int orderTop = 0;
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                //int cellIndex = 0;

                int totalQuestion = 0;
                QuestionTestOffline questionTestOffline = new QuestionTestOffline();
                for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    switch (cellIndex){
                        case 0 -> questionTestOffline.setTitle(cell.getStringCellValue().trim());
                        case 1 -> {
                            var id = cell.getNumericCellValue();
                            Session session = sessionRepository.findById((long)id)
                                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
                            TestOfflineSession testOfflineSessionExiting = testOfflineSessionRepository.findByTestOfflineAndSession(testOffline, session);
                            if (testOfflineSessionExiting == null){
                                totalQuestion = 1;
                                TestOfflineSession testOfflineSession = TestOfflineSession.builder()
                                        .session(session)
                                        .testOffline(testOffline)
                                        .orderTop(orderTop)
                                        .totalQuestion(totalQuestion)
                                        .build();
                                testOfflineSessionRepository.save(testOfflineSession);
                                questionTestOffline.setTestOfflineSession(testOfflineSession);
                                orderTop += 1;
                            } else {
                                questionTestOffline.setTestOfflineSession(testOfflineSessionExiting);
                                testOfflineSessionExiting.setTotalQuestion(testOfflineSessionExiting.getTotalQuestion() + 1);
                            }

                        }
                        case 2 -> //questionTestInput.setAudiomp3(cell.getStringCellValue());
                        {
                            if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
                                questionTestOffline.setAudiomp3(" ");
                            } else {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestOffline.setAudiomp3(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestOffline.setAudiomp3(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestOffline.setAudiomp3(" ");
                                        break;
                                }
                            }
                        }
                        case 3 -> //questionTestInput.setImage(cell.getStringCellValue());
                        {
                            if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
                                questionTestOffline.setImage(" ");
                            } else {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestOffline.setImage(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestOffline.setImage(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestOffline.setImage(" ");
                                        break;
                                }
                            }
                        }

                        case 4 -> //questionTestInput.setParagraph(cell.getStringCellValue());
                        {
                            if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
                                questionTestOffline.setParagraph(" ");
                            } else {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestOffline.setParagraph(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestOffline.setParagraph(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestOffline.setParagraph(" ");
                                        break;
                                }
                            }
                        }
                        case 5 ->
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    questionTestOffline.setOption1(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    questionTestOffline.setOption1(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                    break;
                                default:
                                    questionTestOffline.setOption1(null);
                                    break;
                            }
                        }
                        case 6 ->
                        {
                            {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestOffline.setOption2(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestOffline.setOption2(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestOffline.setOption2(null);
                                        break;
                                }
                            }
                        }
                        case 7 ->
                        {
                            {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestOffline.setOption3(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestOffline.setOption3(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestOffline.setOption3(null);
                                        break;
                                }
                            }
                        }
                        case 8 ->
                        {
                            {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        questionTestOffline.setOption4(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        questionTestOffline.setOption4(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                        break;
                                    default:
                                        questionTestOffline.setOption4(null);
                                        break;
                                }
                            }
                        }
                        case 9 ->
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    questionTestOffline.setCorrectanswer(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    questionTestOffline.setCorrectanswer(NumberToTextConverter.toText(cell.getNumericCellValue()));
                                    break;
                                default:
                                    questionTestOffline.setCorrectanswer(null);
                                    break;
                            }
                        }
                        case 10 -> questionTestOffline.setType((int) cell.getNumericCellValue());
                        case 11 -> questionTestOffline.setPart((int) cell.getNumericCellValue());
                        case 12 -> //questionTestInput.setOrderTop((int) cell.getNumericCellValue());
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    questionTestOffline.setOrderTop(Integer.valueOf(cell.getStringCellValue()));
                                    break;
                                case NUMERIC:
                                    questionTestOffline.setOrderTop(Integer.valueOf(NumberToTextConverter.toText(cell.getNumericCellValue())));
                                    break;
                                default:
                                    questionTestOffline.setOrderTop(0);
                                    break;
                            }
                        }
                        default -> {
                        }
                    }
                }
                questionTestOfflineList.add(questionTestOffline);
            }
            workbook.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

        return questionTestOfflineList;
    }
}
