package ru.sfedu.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.*;
import ru.sfedu.Constants;
import ru.sfedu.DAL.dbProviders.MongoDbProvider;
import ru.sfedu.DAL.entities.HistoryContent;
import ru.sfedu.DAL.models.dbResponse;
import ru.sfedu.DAL.repositories.HistoryRepository;
import ru.sfedu.models.enums.Status;
import ru.sfedu.services.ConvertService;

import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HistoryRepositoryTests
{
    private static MongoDatabase database;
    private static MongoDbProvider dbProvider;
    private static HistoryRepository repository;

    private static HistoryContent content1;
    private static HistoryContent content2;
    @BeforeAll
    public static void getDbTest()
    {
        Assertions.assertDoesNotThrow(() -> dbProvider = new MongoDbProvider());
        Assertions.assertDoesNotThrow(() ->  database = dbProvider.getMongoDb());
        Assertions.assertDoesNotThrow(() ->  repository = new HistoryRepository(database));
        Assertions.assertNotNull(repository);

        content1 = new HistoryContent(
            "TestClass",
            "createTestClass",
            Map.of("test1", "1", "test2","2"),
            Status.SUCCESS);

        content2 = new HistoryContent(
                "TestClassUpd",
                "createTestClassUpd",
                null,
                Status.UPDATED
        );

    }
    @Test
    @Order(1)
    public void createRecordTest()
    {
        dbResponse<HistoryContent> response = repository.saveRecord(content1);
        Assertions.assertEquals(Status.SUCCESS, response.getStatus());
    }

    @Test
    @Order(2)
    public void selectRecordTest()
    {
        dbResponse<HistoryContent> response = repository.getRecordById(content1.getId());
        Assertions.assertEquals(Status.SUCCESS, response.getStatus());
        Assertions.assertNotNull(response.getObject());
        HistoryContent context = response.getObject();

        Assertions.assertEquals(content1.getMethodName(), context.getMethodName());
        Assertions.assertEquals(content1.getCreateDate(), context.getCreateDate());
    }
    @Test
    @Order(4)
    public void deleteRecordTest()
    {
        dbResponse<HistoryContent> response = repository.deleteRecord(content1);
        Assertions.assertEquals(response.getStatus(), Status.SUCCESS);

        dbResponse<HistoryContent> selectResult = repository.getRecordById(content1.getId());
        Assertions.assertNull(selectResult.getObject());
        Assertions.assertEquals(Status.NOTFOUND, selectResult.getStatus());

        Assertions.assertDoesNotThrow(() -> dbProvider.closeConnection());
    }
    @Test
    @Order(3)
    public void updateRecordTest()
    {
        dbResponse<HistoryContent> response = repository.updateRecord(content1, content2);
        Assertions.assertEquals(response.getStatus(), Status.SUCCESS);

        dbResponse<HistoryContent> selectResult = repository.getRecordById(content1.getId());
        content1 = selectResult.getObject();
        Assertions.assertNotNull(selectResult.getObject());
        Assertions.assertEquals(content2.getClassName(), content1.getClassName());
        Assertions.assertEquals(content2.getMethodName(), content1.getMethodName());
    }
}
