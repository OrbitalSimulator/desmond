package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import src.peng.Vector3d;
import src.peng.Vector3dInterface;

public class TestVector3d
{
    Vector3d a = new Vector3d(2,5,-4);
    Vector3d b = new Vector3d(-2,-3,-5);
    @Test void testGetX()
    {
        Vector3dInterface v = new Vector3d(-1.1, 0.1, 1.1);
        assertEquals(-1.1, v.getX());
    }

    @Test void testOrthogonal()
    {
        Vector3d v = new Vector3d(3, -1, 2);
        Vector3d vOrthogonal = v.returnOrthogonal(1, -1);
        double result = v.dotProduct(vOrthogonal);
        assertEquals(0, result);
    }

    @Test void testSetX()
    {
        Vector3dInterface v = new Vector3d();
        v.setX(-1.1);
        assertEquals(-1.1, v.getX());
    }

    @Test void testGetY()
    {
        Vector3dInterface v = new Vector3d(-1.1, 0.1, 1.1);
        assertEquals(0.1, v.getY());
    }

    @Test void testSetY() {
        Vector3dInterface v = new Vector3d();
        v.setY(0.1);
        assertEquals(0.1, v.getY());
    }

    @Test void testGetZ()
    {
        Vector3dInterface v = new Vector3d(-1.1, 0.1, 1.1);
        assertEquals(1.1, v.getZ());
    }

    @Test void testSetZ()
    {
        Vector3dInterface v = new Vector3d();
        v.setZ(1.0);
        assertEquals(1.0, v.getZ());
    }

    @Test void testAddVector3d()
    {
        Vector3dInterface a = new Vector3d(-1.1, 0.1, 1.1);
        Vector3dInterface b = new Vector3d( 0.5, 0.6, 0.7);
        Vector3dInterface ab = a.add(b);
        assertEquals(-1.1+0.5, ab.getX());
        assertEquals( 0.1+0.6, ab.getY());
        assertEquals( 1.1+0.7, ab.getZ());
    }

    @Test void testSub()
    {
        Vector3dInterface a = new Vector3d(-1.1, 0.1, 1.1);
        Vector3dInterface b = new Vector3d( 0.5, 0.6, 0.7);
        Vector3dInterface ab = a.sub(b);
        assertEquals(-1.1-0.5, ab.getX());
        assertEquals( 0.1-0.6, ab.getY());
        assertEquals( 1.1-0.7, ab.getZ());
    }

    @Test void testMul()
    {
        Vector3dInterface a = new Vector3d(-1.1, 0.1, 1.1);
        Vector3dInterface b = a.mul(0.5);
        assertEquals(-1.1*0.5, b.getX());
        assertEquals( 0.1*0.5, b.getY());
        assertEquals( 1.1*0.5, b.getZ());
    }

    @Test void testAddMul()
    {
        Vector3dInterface a = new Vector3d( 0.6, 0.7, 0.8);
        Vector3dInterface b = new Vector3d(-1.1, 0.1, 1.1);
        Vector3dInterface ab = a.addMul(0.5, b);
        assertEquals(0.6 + 0.5*(-1.1), ab.getX());
        assertEquals(0.7 + 0.5*0.1,    ab.getY());
        assertEquals(0.8 + 0.5*1.1,    ab.getZ());
    }

    @Test void testNorm()
    {
        Vector3dInterface v = new Vector3d(3.0, -2.0, 6.0);
        assertEquals(7.0, v.norm());
    }

    @Test void testDist()
    {
        Vector3dInterface a = new Vector3d(3.0, 4.0,  8.0);
        Vector3dInterface b = new Vector3d(0.5, 0.25, 0.5);
        assertEquals(8.75, a.dist(b));
    }

    @Test void testToString()
    {
        Vector3dInterface v = new Vector3d(-1.1, 2.1, -3.1);
        String stringV = "(-1.1,2.1,-3.1)";
        assertEquals(stringV, v.toString());
    }
    @Test
    void testEuclNorm()
    {
        assertEquals(6.708203932499369,a.norm());
    }
    @Test
    void testAddVector()
    {
        Vector3d q = a.add(b);
        assertEquals(0, q.getX());
        assertEquals(2, q.getY());
        assertEquals(-9, q.getZ());
    }
    @Test
    void testSubtractVector()
    {
        Vector3d q = a.sub(b);
        assertEquals(4, q.getX());
        assertEquals(8, q.getY());
        assertEquals(1, q.getZ());
    }
    @Test
    void testDotProduct()
    {
        assertEquals(1,a.dotProduct(b));
    }
    @Test
    void testUnitVector()
    {
        Vector3d q = a.unitVector();
        assertEquals(0.29814239699997197, q.getX());
        assertEquals(0.7453559924999299, q.getY());
        assertEquals(-0.5962847939999439, q.getZ());
    }
    @Test
    void testScalarMultiple()
    {
        Vector3d q = a.mul(5);
        assertEquals(10, q.getX());
        assertEquals(25, q.getY());
        assertEquals(-20, q.getZ());
    }
    @Test
    void testDistance()
    {
        assertEquals(9, a.dist(b));
    }
    
    @Test
    void testEquals()
    {
        Vector3d trial1 = new Vector3d(1, 2, 3);
        Vector3d trial2 = new Vector3d(1, 2, 3);
        assertEquals(true, trial1.equals(trial2));
    }
}



