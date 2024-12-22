package ru.sfedu.DAL.entities;

import org.bson.types.ObjectId;
import ru.sfedu.models.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;


@Getter
@Setter
public class HistoryContent
{
    private String id;
    private String className;
    private Date createDate;
    private String actor;
    private String methodName;
    private Map<String,Object> object;
    private Status status;

    public HistoryContent()
    {
        this.createDate = new Date();
    }

    public HistoryContent(
            String className,
            String methodName,
            Map<String,Object> object,
            Status status)
    {
        this.id = new ObjectId().toHexString();
        this.className = className;
        this.createDate = new Date();
        this.actor = "system";
        this.methodName = methodName;
        this.object = object;
        this.status = status;
    }
}


