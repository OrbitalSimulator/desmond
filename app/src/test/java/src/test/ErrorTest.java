package src.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static src.solv.Error.absoluteError;
import static src.solv.Error.relativeError;

class ErrorTest
{
    @Test
    void testAbsolute()
    {
        assertEquals(1.99-1.01, absolute(1.01,1.99));
    }

    @Test
    void testRelative()
    {
        assertEquals((1.99-1.01)/1.99, relative(1.99,1.01));
    }
}
