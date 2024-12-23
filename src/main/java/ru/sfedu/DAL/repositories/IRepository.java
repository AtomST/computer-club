package ru.sfedu.DAL.repositories;

import ru.sfedu.DAL.models.dbResponse;
import ru.sfedu.models.enums.Status;

public interface IRepository<T>
{
    dbResponse<T> saveRecord(T record);
    dbResponse<T> getRecordById(String id);
    dbResponse<T> deleteRecord(T object);
    dbResponse<T> updateRecord(T object1, T object2);
}
