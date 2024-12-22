package ru.sfedu.DAL.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import ru.sfedu.Constants;
import ru.sfedu.DAL.entities.HistoryContent;
import ru.sfedu.DAL.models.dbResponse;
import ru.sfedu.models.enums.Status;
import ru.sfedu.services.ConfigService;
import ru.sfedu.services.ConvertService;

import javax.print.Doc;

import static com.mongodb.client.model.Filters.eq;

public class HistoryRepository implements IRepository<HistoryContent>
{
    private MongoDatabase database;
    private MongoCollection<Document> historyCollection;

    private final Logger log = LogManager.getLogger(HistoryRepository.class.getName());


    public HistoryRepository(MongoDatabase database)
    {
        this.database = database;
        historyCollection = database.getCollection(ConfigService.getProperty(Constants.MONGODB_COLLECTIONS_HISTORY));
    }

    @Override
    public dbResponse<HistoryContent> saveRecord(HistoryContent record)
    {
        try
        {
            historyCollection.insertOne(
                    new Document(ConvertService.objectToJson(record)
                    ));
            log.info("Добавлена новая запись. ID:" + record.getId());
            return new dbResponse<>(Status.SUCCESS);
        }
        catch (Exception e)
        {
            log.error("При добавлении записи произошлка ошибка: " + e.getMessage());
            return new dbResponse<>(Status.FAULT);
        }
    }

    @Override
    public dbResponse<HistoryContent> getRecordById(String id)
    {
        try
        {
            Document record = historyCollection.find(eq("id", id)).first();
            if(record == null)
                return new dbResponse<>(Status.NOTFOUND);

            record.remove("_id");
            HistoryContent content = ConvertService.jsonToObject(record.toJson(), HistoryContent.class);
            return new dbResponse<>(content, Status.SUCCESS);
        }
        catch (Exception e)
        {
            log.error("При запросе записи с id:" + id + ", произошла ошибка: " + e.getMessage());
            return new dbResponse<>(Status.FAULT);
        }
    }

    @Override
    public dbResponse<HistoryContent> deleteRecord(HistoryContent object)
    {
        try
        {
            DeleteResult deleteResult = historyCollection.deleteOne(
                    new Document(ConvertService.objectToJson(object)));

            if (deleteResult.getDeletedCount() == 0)
            {
                log.error("При удаленни записи, ни одна из записей не была удалена. Id удаляемой записи: " + object.getId());
                return new dbResponse<>(Status.FAULT);
            }

            return new dbResponse<>(Status.SUCCESS);
        }
        catch (Exception e)
        {
            log.error("При удаленни записи, произошла ошибка: " + e.getMessage());
            return new dbResponse<>(Status.FAULT);
        }

    }

    @Override
    public dbResponse<HistoryContent> updateRecord(HistoryContent object1,HistoryContent object2)
    {
        try
        {
            Document doc1 = new Document(ConvertService.objectToJson(object1));
            Document doc2 = new Document(ConvertService.objectToJson(object2));
            doc2.remove("id");
            Document updates = new Document("$set", doc2);
            UpdateResult updateResult = historyCollection.updateOne(doc1,updates);

            if (updateResult.getModifiedCount() == 0)
            {
                log.error("При обновлении записи, ни одна из записей не была обновлена. Id обновляемой записи: " + object1.getId());
                return new dbResponse<>(Status.FAULT);
            }

            return new dbResponse<>(Status.SUCCESS);
        }
        catch (Exception e)
        {
            log.error("При обновлении записи, произошла ошибка: " + e.getMessage());
            return new dbResponse<>(Status.FAULT);
        }
    }
}
