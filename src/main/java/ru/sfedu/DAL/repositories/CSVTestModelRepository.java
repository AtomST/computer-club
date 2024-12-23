package ru.sfedu.DAL.repositories;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.opencsv.bean.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import ru.sfedu.Constants;
import ru.sfedu.DAL.entities.csvTestModel;
import ru.sfedu.DAL.models.dbResponse;
import ru.sfedu.models.enums.Status;
import ru.sfedu.services.ConfigService;

public class CSVTestModelRepository implements IRepository<csvTestModel>
{
    private final String path = ConfigService.getProperty(Constants.CSV_HISTORY);
    private final Logger log = LogManager.getLogger(CSVTestModelRepository.class.getName());

    private final ColumnPositionMappingStrategy<csvTestModel> strategy;
    private  final String[] columns = new String[]
            {"id", "firstName", "lastName", "phoneNumber"};

    public CSVTestModelRepository()
    {
        strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(csvTestModel.class);
        strategy.setColumnMapping(columns);
    }


    @Override
    public dbResponse<csvTestModel> saveRecord(csvTestModel record)
    {
        try
        {
            FileWriter fw = new FileWriter(path,true);
            var writer = new StatefulBeanToCsvBuilder<csvTestModel>(fw)
                    .withMappingStrategy(strategy)
                    .build();
            writer.write(record);
            fw.close();
            return new dbResponse<>(Status.SUCCESS);
        }
        catch (Exception e)
        {
            log.error("При добавлении записи произошла ошибка: " + e.getMessage());
            return new dbResponse<>(Status.FAULT);
        }
    }


    public dbResponse<List<csvTestModel>> getAllRecords()
    {
        try
        {
            if(Files.notExists(Path.of(path)))
                return new dbResponse<>(Status.NOTFOUND);

            FileReader fw = new FileReader(path);
            var reader = new CsvToBeanBuilder<csvTestModel>(fw)
                    .withMappingStrategy(strategy)
                    .build();

            List<csvTestModel> list = reader.parse();
            fw.close();
            return new dbResponse<>(list,Status.SUCCESS);
        }
        catch (Exception e)
        {
            log.error("При получении записей произошла ошибка: " + e.getMessage());
            return new dbResponse<>(Status.FAULT);
        }
    }


    @Override
    public dbResponse<csvTestModel> getRecordById(String id)
    {
        dbResponse<List<csvTestModel>> response = getAllRecords();
        if(response.getStatus() != Status.SUCCESS)
            return new dbResponse<>(response.getStatus());

        Optional<csvTestModel> model = response.getObject().stream().filter(c -> Objects.equals(c.getId(), id)).findFirst();
        if(model.isEmpty())
            return new dbResponse<>(Status.NOTFOUND);

        return new dbResponse<>(model.get(), Status.SUCCESS);

    }

    @Override
    public dbResponse<csvTestModel> deleteRecord(csvTestModel object)
    {
        dbResponse<List<csvTestModel>> response = getAllRecords();
        if(response.getStatus() != Status.SUCCESS)
            return new dbResponse<>(response.getStatus());

        List<csvTestModel> list = response.getObject();
        List<csvTestModel> updated = list.stream().filter(c -> !Objects.equals(c.getId(), object.getId())).toList();
        return saveRecord(updated,false);
    }

    @Override
    public dbResponse<csvTestModel> updateRecord(csvTestModel object1, csvTestModel object2)
    {
        dbResponse<csvTestModel> deleteResponse = deleteRecord(object1);
        if(deleteResponse.getStatus() != Status.SUCCESS)
            return deleteResponse;

        object2.setId(object1.getId());
        return saveRecord(object2);
    }

    public dbResponse<csvTestModel> saveRecord(List<csvTestModel> records, boolean append)
    {
        try
        {
            FileWriter fw = new FileWriter(path, append);
            var writer = new StatefulBeanToCsvBuilder<csvTestModel>(fw)
                    .withMappingStrategy(strategy)
                    .build();
            writer.write(records);
            fw.close();
            return new dbResponse<>(Status.SUCCESS);
        }
        catch (Exception e)
        {
            log.error("При добавлении списка записей произошла ошибка:" + e.getMessage());
            return new dbResponse<>(Status.FAULT);
        }
    }


}

