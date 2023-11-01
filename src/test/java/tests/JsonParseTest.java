package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.Named;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.json.Object;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


/**
 * @author mateenkov
 */

public class JsonParseTest {

    private ClassLoader cl = JsonParseTest.class.getClassLoader();

    @Test
    void parseJson() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("object.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            Object object = objectMapper.readValue(isr, Object.class);
            Assertions.assertEquals("Ilya",object.getName() );
            Assertions.assertEquals(List.of("BMW","Audi","Fiat"), object.getCars());


        }
    }

}
