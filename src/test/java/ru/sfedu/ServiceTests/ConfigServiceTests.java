package ru.sfedu.ServiceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sfedu.Constants;
import ru.sfedu.models.enums.ConfigTypes;
import ru.sfedu.models.enums.Status;
import ru.sfedu.services.ConfigService;

public class ConfigServiceTests
{
    @Test
    public void configFromPropertiesTest()
    {
        Status status = ConfigService.setProperties(ConfigTypes.PROPERTIES);

        Assertions.assertEquals(status,Status.SUCCESS);
        Assertions.assertEquals(456, Integer.parseInt(ConfigService.getProperty(Constants.TEST_PROP1)));
        Assertions.assertEquals("sfedu", ConfigService.getProperty(Constants.TEST_PROP2));

    }

    @Test
    public void configFromXMLTest()
    {
        Status status = ConfigService.setProperties(ConfigTypes.XML);

        Assertions.assertEquals(status,Status.SUCCESS);
        Assertions.assertEquals(456, Integer.parseInt(ConfigService.getProperty(Constants.TEST_PROP1)));
        Assertions.assertEquals("sfedu", ConfigService.getProperty(Constants.TEST_PROP2));
    }

    @Test
    public void configFromYMLTest()
    {
        Status status = ConfigService.setProperties(ConfigTypes.YML);

        Assertions.assertEquals(status,Status.SUCCESS);
        Assertions.assertEquals(456, Integer.parseInt(ConfigService.getProperty(Constants.TEST_PROP1)));
        Assertions.assertEquals("sfedu", ConfigService.getProperty(Constants.TEST_PROP2));
    }

}
