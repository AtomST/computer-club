package ru.sfedu.DAL.models;

import lombok.Getter;
import lombok.Setter;
import ru.sfedu.models.enums.Status;

@Getter
@Setter
public class dbResponse<T>
{
    private T object;
    private Status status;

    public dbResponse(Status status)
    {
        this.status = status;
    }
    public dbResponse(T object, Status status)
    {
        this.object = object;
        this.status = status;
    }
}


