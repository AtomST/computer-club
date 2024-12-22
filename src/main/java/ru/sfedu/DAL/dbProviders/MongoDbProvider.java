package ru.sfedu.DAL.dbProviders;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import ru.sfedu.Constants;
import ru.sfedu.services.ConfigService;

public class MongoDbProvider
{
    private MongoClient client;
    public MongoDatabase getMongoDb()
    {
            MongoClientSettings settings = MongoClientSettings
                    .builder()
                    .applyConnectionString(new ConnectionString(ConfigService.getProperty(Constants.MONGODB_CONNECTIONSTRING)))
                    .build();
            if(client == null)
                client = MongoClients.create(settings);
            MongoDatabase db = client.getDatabase("CmpClubDB");
            return db;
    }

    public void closeConnection()
    {
        client.close();
    }
}
