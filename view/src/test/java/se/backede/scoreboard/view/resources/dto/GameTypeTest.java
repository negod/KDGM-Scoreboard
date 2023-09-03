/*
 */
package se.backede.scoreboard.view.resources.dto;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class GameTypeTest {

    public GameTypeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of values method, of class GameType.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        GameType[] expResult = null;
        GameType[] result = GameType.values();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of valueOf method, of class GameType.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String string = "";
        GameType expResult = null;
        GameType result = GameType.valueOf(string);
        assertEquals(expResult, result);

        System.out.println(GameType.NONE.name());

        // TODO review the generated test code and remove the default call to fail.
    }

}
