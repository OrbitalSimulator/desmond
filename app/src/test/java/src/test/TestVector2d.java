package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.peng.Vector2d;
import src.peng.Vector3d;

class TestVector2d {

	@Test
	void testGetX() {
		Vector2d v = new Vector2d(1.1, -1.2);
		assertEquals(1.1, v.getX());
	}
	
	@Test
	void testGetY() {
		Vector2d v = new Vector2d(1.1, -1.2);
		assertEquals(-1.2, v.getY());
	}
	
	@Test
	void testSetX() {
		Vector2d v = new Vector2d(1.1, -1.2);
		v.setX(2.1);
		assertEquals(2.1, v.getX());
	}
	
	@Test
	void testSetY() {
		Vector2d v = new Vector2d(1.1, -1.2);
		v.setY(-2.2);
		assertEquals(-2.2, v.getY());
	}
	
	@Test
	void testAdd() {
		Vector2d a = new Vector2d(1.1, -1.2);
		Vector2d b = new Vector2d(-1.3, 1.4);
		Vector2d r = a.add(b);
		assertEquals(1.1 - 1.3, r.getX());
		assertEquals(-1.2 + 1.4, r.getY());
	}

	@Test
	void testSub() {
		Vector2d a = new Vector2d(1.1, -1.2);
		Vector2d b = new Vector2d(-1.3, 1.4);
		Vector2d r = a.sub(b);
		assertEquals(1.1 - (-1.3), r.getX());
		assertEquals(-1.2 - 1.4, r.getY());
	}

	@Test
	void testMul() {
		Vector2d a = new Vector2d(1.1, -1.2);
		Vector2d r = a.mul(3.1);
		assertEquals(1,1 * 3.1, r.getX());
		assertEquals(-1.2 * 3.1, r.getY());
	}

	@Test
	void testAddMul() {
		Vector2d a = new Vector2d(1.1, -1.2);
		Vector2d b = new Vector2d(-1.3, 1.4);
		Vector2d r = a.addMul(0.5, b);
		assertEquals(1.1 + 0.5 * (-1.3), r.getX());
		assertEquals(-1.2 + 0.5 * 1.4, r.getY());
	}

	@Test
	void testNorm() {
		Vector2d v = new Vector2d(3.0, 4.0);
		assertEquals(5.0, v.norm());
	}

	@Test
	void testDist() {
		Vector2d a = new Vector2d(4.0, 6.0);
		Vector2d b = new Vector2d(1.0, 2.0);
		assertEquals(5.0, a.dist(b));
	}
	
	//unit vector
	@Test
	void testUnitVector() {
		Vector2d v = new Vector2d(3.0, 4.0);
		Vector2d unitVector = v.unitVector();
		assert(unitVector.equals(new Vector2d(3.0/v.norm(), 4.0/v.norm())));
	}
	
	//copy of
	@Test
	void testCopyOf() {
		Vector2d v = new Vector2d(3.0, 4.0);
		Vector2d copyVector = v.copyOf();
		assert(copyVector.equals(new Vector2d(3.0, 4.0)));
	}
	
	//orthogonal
	@Test
	void testReturnOrthogonal() {
		Vector2d v = new Vector2d(3, -1);
        Vector2d vOrthogonal = v.returnOrthogonal();
        double result = v.dotProduct(vOrthogonal);
		assertEquals(0, result);
	}
	
	//round
	@Test
	void testRound() {
		Vector2d v = new Vector2d(3.9, -1.2);
		Vector2d rounded = new Vector2d(v.getRoundedX(0),v.getRoundedY(0));
        System.out.println(v.getRoundedX(1)+v.getRoundedY(1));
//		assertEquals(0, result);
        assertEquals(new Vector2d(4.0, -1.0), rounded);
	}
	
	@Test
	void testDotProduct() {
		Vector2d a = new Vector2d(1.0, -1.0);
		Vector2d b = new Vector2d(2.0, 1.0);
		assertEquals(1.0, a.dotProduct(b));
	}
	
	@Test
	void testEquals() {
		Vector2d a = new Vector2d(1.1, -1.2);
		Vector2d b = new Vector2d(1.1, -1.2);
		Vector2d c = new Vector2d(-3.1, 3.2);
		assertEquals(true, a.equals(b));
		assertEquals(false, b.equals(c));
	}

	@Test
	void testToString() {
		Vector2d v = new Vector2d(1.1, -1.2);
		String expected = "(1.1,-1.2)";
		assertEquals(expected, v.toString());
	}
	
	@Test
	void testToCSV() {
		Vector2d v = new Vector2d(1.1, -1.2);
		String expected = "1.1,-1.2,";
		assertEquals(expected, v.toCSV());
	}
	
}
