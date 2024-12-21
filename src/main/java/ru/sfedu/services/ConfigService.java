package ru.sfedu.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Constants;
import ru.sfedu.models.enums.ConfigTypes;
import ru.sfedu.models.enums.Status;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class ConfigService
{
    private static final Logger log = LogManager.getLogger(ConfigService.class.getName());
    private static final Properties props = new Properties();

    public static Status setProperties(ConfigTypes configType)
    {
        String path = switch (configType)
        {
            case PROPERTIES -> Constants.CONFIG_PATH_PROPERTIES;
            case XML -> Constants.CONFIG_PATH_XML;
            case YML -> Constants.CONFIG_PATH_YML;
        };

        try
        {
            FileInputStream file = new FileInputStream(path);

            if(configType == ConfigTypes.XML)
                props.loadFromXML(file);
            else
                props.load(file);

            return Status.SUCCESS;
        }
        catch (FileNotFoundException e)
        {
            String warnMessage = "Конфигурационный файл не найден. " + "Путь: " + path;
            log.warn(warnMessage);
            return Status.FAULT;
        }
        catch (Exception e)
        {
            String errMessage = "При загрузке конфигурационного файла произошла ошибка:" + e.getMessage();
            log.error(errMessage);
            return Status.FAULT;
        }
    }

    public static String getProperty(String key)
    {
        return props.getProperty(key);
    }
}
