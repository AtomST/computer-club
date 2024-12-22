package ru.sfedu.models.enums;

import lombok.Getter;

public enum Status
{
    SUCCESS(200),
    UPDATED(300),
    NOTFOUND(404),
    FAULT(500);

    @Getter
    private final int status;
    Status(int i)
    {
        this.status = i;
    }


}
