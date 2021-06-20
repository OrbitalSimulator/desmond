package src.test;

import org.junit.jupiter.api.Test;
import src.test.Error;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestError
{
    @Test
    void testAbsolute()
    {
        assertEquals(1.99-1.01, Error.absolute(1.01,1.99));
    }

    @Test
    void testRelative()
    {
        assertEquals((1.99-1.01)/1.99, Error.relative(1.99,1.01));
    }
}
