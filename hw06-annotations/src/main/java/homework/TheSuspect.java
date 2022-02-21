package homework;

import homework.tastus.After;
import homework.tastus.Before;
import homework.tastus.TastusFramework;
import homework.tastus.Test;

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
        TastusFramework.assertEquals("IVAN", qestions.get("FirstName").toUpperCase());
    }

    @Test
    public void AskMiddleName() {
        TastusFramework.assertEquals("IVANOVICH", qestions.get("MiddleName").toUpperCase());
    }

    @Test
    public void AskLastName() {
        TastusFramework.assertEquals("IVANOV", qestions.get("LastName").toUpperCase());
    }

    @After
    public void CleanupQuestions() {
        qestions.clear();
    }
}
