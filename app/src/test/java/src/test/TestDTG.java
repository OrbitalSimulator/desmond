package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.univ.DTG;

class TestDTG 
{
	// TODO Break into separate test cases
	
	@Test	
	void testDTG() 
	{
		//Arrange
		DTG d1 = new DTG(0, 0, 0, 0, 0, 0);
		DTG d2 = new DTG(0, 0, 0, 1, 0, 0);
		DTG r1 = new DTG(0, 3, 14, 4, 0, 0); //d1.add(d2)
		
		DTG d3 = new DTG(2020, 7, 22, 0, 0, 0);
		DTG d4 = new DTG(0, 9, 15, 0, 0, 0);
		DTG r2 = new DTG(2021, 5, 5, 0, 0, 0); //d3.add(d4)
		
		DTG d5 = new DTG(2020, 6, 19, 0, 0, 0);
		DTG d6 = new DTG(192, 5, 20, 0, 0, 0);
		DTG r3 = new DTG(2212, 12, 8, 0, 0, 0); //d5.add(d6)
		
		DTG d7 = new DTG(2020, 4, 17, 0, 0, 0);
		DTG d8 = new DTG(20, 3, 14, 0, 0, 0);
		DTG r4 = new DTG(2040, 8, 1, 0, 0, 0); //d7.add(d8)
		
		DTG d17 = new DTG(0, 0, 0, 10, 0, 0);
		DTG d18 = new DTG(0, 0, 0, 20, 0, 0);
		DTG r9 = new DTG(0, 0, 1, 6, 0, 0); //d17.add(d18)
		
		
		DTG d9 = new DTG(12, 8, 9, 5, 11, 24);
		DTG d10 = new DTG(4, 6, 5, 3, 6, 10);
		DTG r5 = new DTG(8, 2, 4, 2, 5, 14); //d9.sub(d10)
		
		DTG d11 = new DTG(2, 0, 0, 3, 50, 40);
		DTG d12 = new DTG(1, 0, 0, 1, 10, 30);
		DTG r6 = new DTG(1, 0, 0, 2, 40, 10); //d11.sub(d12)
		
		DTG d13 = new DTG(0, 7, 5, 2, 10, 0);
		DTG d14 = new DTG(2, 4, 21, 0, 0, 0);
		DTG r7 = new DTG(0, 2, 12, 2, 10, 0); //d13.sub(d14)
		
		DTG d15 = new DTG(4, 3, 4, 4, 10, 22);
		DTG d16 = new DTG(1, 9, 21, 19, 26, 49);
		DTG r8 = new DTG(2, 5, 13, 8, 43, 33); //d15.sub(d16)
		
		//Act
		for(int i=0; i<2500; i++) {
			d1.add(d2);
			//System.out.println("h: " +d1.hr+" | d: "+d1.day+" | m: "+d1.mth+" | y: "+d1.yr);
		}
		//d1.add(d2);
		d3.add(d4);
		d5.add(d6);
		d7.add(d8);
		d17.add(d18);

	    	DTG act = d9.sub(d10);
	    	DTG act2 = d11.sub(d12);
	    	DTG act3 = d13.sub(d14);
	    	DTG act4 = d15.sub(d16);
		//Asset
		assertEquals(r4.yr, d7.yr);
		assertEquals(r4.mth, d7.mth);
		assertEquals(r4.day, d7.day);
		assertEquals(r4.hr, d7.hr);
		assertEquals(r4.min, d7.min);
		assertEquals(r4.sec, d7.sec);
		
		assertEquals(r1.yr, d1.yr);
		assertEquals(r1.mth, d1.mth);
		assertEquals(r1.day, d1.day);
		assertEquals(r1.hr, d1.hr);
		assertEquals(r1.min, d1.min);
		assertEquals(r1.sec, d1.sec);
		
		assertEquals(r2.yr, d3.yr);
		assertEquals(r2.mth, d3.mth);
		assertEquals(r2.day, d3.day);
		assertEquals(r2.hr, d3.hr);
		assertEquals(r2.min, d3.min);
		assertEquals(r2.sec, d3.sec);
		
		assertEquals(r3.yr, d5.yr);
		assertEquals(r3.mth, d5.mth);
		assertEquals(r3.day, d5.day);
		assertEquals(r3.hr, d5.hr);
		assertEquals(r3.min, d5.min);
		assertEquals(r3.sec, d5.sec);
		
		assertEquals(r9.yr, d17.yr);
		assertEquals(r9.mth, d17.mth);
		assertEquals(r9.day, d17.day);
		assertEquals(r9.hr, d17.hr);
		assertEquals(r9.min, d17.min);
		assertEquals(r9.sec, d17.sec);
		
		//Test Sub
		assertEquals(r5.sec, act.sec);
		assertEquals(r5.min, act.min);
		assertEquals(r5.hr, act.hr);
		assertEquals(r5.day, act.day);
		assertEquals(r5.mth, act.mth);
		assertEquals(r5.yr, act.yr);
		
		assertEquals(r6.sec, act2.sec);
		assertEquals(r6.min, act2.min);
		assertEquals(r6.hr, act2.hr);
		assertEquals(r6.day, act2.day);
		assertEquals(r6.mth, act2.mth);
		assertEquals(r6.yr, act2.yr);
		
		assertEquals(r7.sec, act3.sec);
		assertEquals(r7.min, act3.min);
		assertEquals(r7.hr, act3.hr);
		assertEquals(r7.day, act3.day);
		assertEquals(r7.mth, act3.mth);
		assertEquals(r7.yr, act3.yr);
		
		assertEquals(r8.sec, act4.sec);
		assertEquals(r8.min, act4.min);
		assertEquals(r8.hr, act4.hr);
		assertEquals(r8.day, act4.day);
		assertEquals(r8.mth, act4.mth);
		assertEquals(r8.yr, act4.yr);
	}
	
	@Test
	void testCompare()
	{
        //Create two DTG timestamps to compare
        //1st April 2021 from 1 second past midnight
        DTG ts1 = new DTG(2020, 3, 31, 23, 59, 58);
        //31s March, 1 second before 1t April 2021
        DTG ts2 = new DTG(2021, 3, 31, 23, 59, 59);

        System.out.println(ts1.timeComparison(ts2));
	}
}