package ru.sfedu.repositories;

import org.junit.jupiter.api.*;
import ru.sfedu.DAL.entities.HistoryContent;
import ru.sfedu.DAL.entities.csvTestModel;
import ru.sfedu.DAL.models.dbResponse;
import ru.sfedu.DAL.repositories.CSVTestModelRepository;
import ru.sfedu.models.enums.Status;

import java.util.List;
import java.util.Map;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CSVHistoryRepositoryTests
{
    private static csvTestModel content1;
    private static csvTestModel content2;
    private static csvTestModel content3;
    private static CSVTestModelRepository rep;

    @BeforeAll
    public static void getCSVTest()
    {
        content1 = new csvTestModel(
                "1",
                "Vastya",
                "Is",
                14141144
        );

        content2 = new csvTestModel(
                "2",
                "V",
                "I",
                5
        );
        content3 = new csvTestModel(
                "3",
                "F",
                "B",
                3
        );


        rep = new CSVTestModelRepository();

    }

    @Test
    @Order(1)
    public void createRecordTest()
    {
        dbResponse<csvTestModel> firstResult = rep.saveRecord(content1);
        dbResponse<csvTestModel> secondResult = rep.saveRecord(content2);

        Assertions.assertEquals(Status.SUCCESS, firstResult.getStatus());
        Assertions.assertEquals(Status.SUCCESS, secondResult.getStatus());
    }

    @Test
    @Order(2)
    public void selectRecordTest()
    {
        String id = content1.getId();
        dbResponse<csvTestModel> response = rep.getRecordById(id);
        csvTestModel content = response.getObject();

        Assertions.assertEquals(id, content.getId());
        Assertions.assertEquals(content1.getFirstName(), content.getFirstName());
    }

    @Test
    @Order(4)
    public void deleteRecordTest()
    {
        dbResponse<csvTestModel> response = rep.deleteRecord(content1);
        dbResponse<csvTestModel> response2 = rep.deleteRecord(content2);

        Assertions.assertEquals(Status.SUCCESS, response.getStatus());
        Assertions.assertEquals(Status.SUCCESS, response2.getStatus());
    }

    @Test
    @Order(3)
    public void updateRecordTest()
    {
        dbResponse<csvTestModel> response = rep.updateRecord(content1,content3);
        dbResponse<csvTestModel> selectResponse = rep.getRecordById(content1.getId());

        Assertions.assertEquals(Status.SUCCESS, response.getStatus());
        Assertions.assertEquals(Status.SUCCESS, selectResponse.getStatus());

        Assertions.assertEquals(content3.getFirstName(), selectResponse.getObject().getFirstName());
        Assertions.assertEquals(content3.getLastName(), selectResponse.getObject().getLastName());
        Assertions.assertEquals(content3.getPhoneNumber(), selectResponse.getObject().getPhoneNumber());
    }
}
