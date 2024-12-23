package ru.sfedu.DAL.entities;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvDates;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class csvTestModel
{
    @CsvBindByName
    private String id;
    @CsvBindByName

    private String firstName;
    @CsvBindByName

    private String lastName;
    @CsvBindByName
    private int phoneNumber;

}
