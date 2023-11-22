package com.example.enlaco.TimeTest;


import com.example.enlaco.Service.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
public class TimeTest {
    private StorageService storageService;

    @Test
    public void testtimeI() {
        String str = "2023-11-22 15:23:29";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date strToDate = null;

        try {
            strToDate = format.parse(str);
            System.out.println("strToDate :: " + strToDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //Date = "2023-05-01 00:59:29";

        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format3 = new SimpleDateFormat("HH");

        String fullStr = format2.format(strToDate);
        System.out.println("fullStr :: " + fullStr);

        String hourStr = format3.format(strToDate);
        System.out.println("hourStr :: " + hourStr);

        String str4 = "2019-05-01 15:45:00";
        SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultDate;

        Date strToDate2 = null;
        try {
            strToDate2 = format.parse(str4);
            System.out.println("strToDate :: " + strToDate2);

            //Date 타입에 9시간을 더합니다.
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(strToDate2); //시간 설정
            cal1.add(Calendar.HOUR, 9);
        } catch () {

        }

    }

    @Test
    public String timeTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        String todayString = now.format(formatter); //오늘 날짜 String화

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Storage storage : storages)
        String syutong =
    }




}
