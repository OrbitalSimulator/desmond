package src.test;

import org.junit.jupiter.api.Test;
import src.land.ClosedLoopController;
import src.peng.Vector3d;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClosedLoopController {
    @Test
    void testgetAngle()
    {
        ClosedLoopController clp= new ClosedLoopController();
        Vector3d a = new Vector3d(1,2,3);
        Vector3d b = new Vector3d(-1, -4, 6);
        Vector3d c = new Vector3d(5 , 6 , 8);
        assertEquals(34.06718675903152,clp.getAngle(a,b,c));
    }
}
