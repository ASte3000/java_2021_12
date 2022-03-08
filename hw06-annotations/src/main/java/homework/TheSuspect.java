package homework;

import homework.tastus.*;

import java.util.HashMap;
import java.util.Map;

public class TheSuspect {

    private Map<String, String> qestions;

    @Before
    public void PrepareQuestions() {
        qestions = new HashMap<>();
        qestions.put("FirstName", "Ivan");
        qestions.put("LastName", "Kozlov");
    }

    @Test
    public void AskFirstName() {
        TastusAssert.assertEquals("IVAN", qestions.get("FirstName").toUpperCase());
    }

    @Test
    public void AskMiddleName() {
        TastusAssert.assertEquals("IVANOVICH", qestions.get("MiddleName").toUpperCase());
    }

    @Test
    public void AskLastName() {
        TastusAssert.assertEquals("IVANOV", qestions.get("LastName").toUpperCase());
    }

    @After
    public void CleanupQuestions() {
        qestions.clear();   //makes no sense, but just to have something here
    }
}
