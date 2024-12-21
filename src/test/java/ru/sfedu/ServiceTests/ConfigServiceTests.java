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

        String prop1 = ConfigService.getProperty(Constants.TEST_PROP1);
        String prop2 = ConfigService.getProperty(Constants.TEST_PROP2);

        Assertions.assertNotNull(prop1);
        Assertions.assertNotNull(prop2);
        Assertions.assertEquals(456, Integer.parseInt(prop1));
        Assertions.assertEquals("sfedu", prop2);

    }

    @Test
    public void configFromXMLTest()
    {
        Status status = ConfigService.setProperties(ConfigTypes.XML);

        Assertions.assertEquals(status,Status.SUCCESS);

        String prop1 = ConfigService.getProperty(Constants.TEST_PROP1);
        String prop2 = ConfigService.getProperty(Constants.TEST_PROP2);

        Assertions.assertNotNull(prop1);
        Assertions.assertNotNull(prop2);
        Assertions.assertEquals(456, Integer.parseInt(prop1));
        Assertions.assertEquals("sfedu", prop2);
    }

    @Test
    public void configFromYMLTest()
    {
        Status status = ConfigService.setProperties(ConfigTypes.YML);

        Assertions.assertEquals(status,Status.SUCCESS);

        String prop1 = ConfigService.getProperty(Constants.TEST_PROP1);
        String prop2 = ConfigService.getProperty(Constants.TEST_PROP2);

        Assertions.assertNotNull(prop1);
        Assertions.assertNotNull(prop2);
        Assertions.assertEquals(456, Integer.parseInt(prop1));
        Assertions.assertEquals("sfedu", prop2);
    }

}
