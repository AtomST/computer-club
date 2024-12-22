package ru.sfedu.DAL.repositories;

import ru.sfedu.DAL.models.dbResponse;
import ru.sfedu.models.enums.Status;

public interface IRepository<T>
{
    dbResponse saveRecord(T record);
    dbResponse getRecordById(String id);
    dbResponse deleteRecord(T object);
    dbResponse updateRecord(T object1, T object2);
}
