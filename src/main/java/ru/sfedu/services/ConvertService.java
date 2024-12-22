package ru.sfedu.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertService
{
    private static Logger log = LogManager.getLogger(ConvertService.class.getName());

    public static  <T> List<T> jsonArrayToObjectList(List<Map<String,Object>> map, Class<T> tClass)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            CollectionType listType = mapper.getTypeFactory()
                    .constructCollectionType(ArrayList.class, tClass);
            List<T> objects = mapper.convertValue(map, listType);
            return objects;
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
            throw new ClassCastException(ex.getMessage());
        }
    }

    public static  <T> T jsonToObject(String objectJson, Class<T> tClass)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            T object = mapper.readValue(objectJson, tClass);
            return object;
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
            throw new ClassCastException(ex.getMessage());
        }
    }

    public static  <T> List<Map<String, Object>> objectListToJsonArray(List<T> objects) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> mapList = new ArrayList<>();

            for (T object : objects) {
                Map<String, Object> map = mapper.convertValue(object, new TypeReference<>(){});
                mapList.add(map);
            }
            return mapList;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ClassCastException(ex.getMessage());
        }
    }

    public static  <T> Map<String, Object> objectToJson(T object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map;
            map = mapper.convertValue(object, new TypeReference<>(){});
            return map;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ClassCastException(ex.getMessage());
        }
    }

}
