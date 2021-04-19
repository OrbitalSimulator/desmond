package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.StateInterface;
import src.peng.Vector3d;
import src.peng.EulerSolver;
import src.peng.NewtonGravityFunction;

class TestEulerSolver 
{
	static ArrayList<Vector3d> v = new ArrayList<Vector3d>();																					//Generate Initial state
	static ArrayList<Vector3d> p =  new ArrayList<Vector3d>();
    double[] masses = {1.988500e30, 3.302e23};																									//Masses of first two planets
	StateInterface initial = new State(v,p);																										//Instantiate state

	double tf = 5.0;																																//Time initialization
	double[] ts = {0, 10, 40, 100, 1000, 20000};
	double t = 1;
	double step = 1.0;

	@BeforeAll
	public static void init()
	{
		//Adding initial velocity vectors.
		v.add(new Vector3d(-1.420511669610689e+01, -4.954714716629277e+00,  3.994237625449041e-01));												//Sun v (Planet 1)
		v.add(new Vector3d( 3.892585189044652e+04,  2.978342247012996e+03, -3.327964151414740e+03));												//Mercury v (Planet 2)

		p.add(new Vector3d( -6.806783239281648e+08,   1.080005533878725e+09,   6.564012751690170e+06));												//Sun p (Planet 1)
		p.add(new Vector3d(  6.047855986424127e+06,  -6.801800047868888e+10,  -5.702742359714534e+09));												//Mercury p (Planet 2)
	}
	
	@Test
	public void testSingleStep() 
	{
		ODEFunctionInterface funct1 = new NewtonGravityFunction(masses);																			//Create ODEFunction object
	    EulerSolver test1 = new EulerSolver();
	    StateInterface results1 = test1.step(funct1, t, initial, step);
		State resultsConverted = (State)results1;
	    System.out.println("Single step: "+ results1.toString());																					//Display
	    System.out.println("\n==========================");

		assertEquals(new Vector3d(-14.205116696061488, -4.954714721197586,  0.39942376216744196), resultsConverted.velocity.get(0));				//Assertions for planets 1
		assertEquals(new Vector3d(-6.806783381332816E8, 1.0800055289240103E9, 6564013.151113932), resultsConverted.position.get(0));

		//Assertions for planet 2
		assertEquals(new Vector3d(38925.85161703165, 2978.3697578652873, -3327.961878297275), resultsConverted.velocity.get(1));
		assertEquals(new Vector3d(6086781.838314573, -6.8017997500346634E10, -5.702745687678685E9), resultsConverted.position.get(1));
	    
	}

	//TODOD (Travis Dawson) Final time Euler solver testing
	@Test
	public void testFunction2() 
	{
	    ODEFunctionInterface funct2 = new NewtonGravityFunction(masses);

	    EulerSolver test2 = new EulerSolver();
	    StateInterface[] results2 = test2.solve(funct2, initial,tf, step);
		State finalResultConverted = (State)results2[results2.length-1];
	    System.out.println("Test2: ");																												//Display
	    for(int i=0; i< results2.length; i++)
	    {
	        System.out.print(results2[i].toString() + ", ");
	    }
	    System.out.println("\n===========================");
	    
		//Determine final state of both planets for assertions
		assertEquals(new Vector3d(-14.205116695879855, -4.954714739470827, 0.399423760657591), finalResultConverted.velocity.get(0));				//Planet 1 (Sun)
		assertEquals(new Vector3d(-6.806783949537486E8, 1.0800055091051512E9, 6564014.748808979), finalResultConverted.position.get(0));
		assertEquals(5.0, finalResultConverted.time);
		assertEquals(new Vector3d(38925.850523216846, 2978.47980129011, -3327.952785811886), finalResultConverted.velocity.get(1));					//Planet 2 (Mercury)
		assertEquals(new Vector3d(6242485.243142055, -6.801798558670253E10, -5.702758999512559E9), finalResultConverted.position.get(1));
		assertEquals(5.0, finalResultConverted.time);
	}
	
	//TODO (Travis Dawson) Time array Euler Solver testing
	@Test
	public void testFunction3() 
	{
	    ODEFunctionInterface funct3 = new NewtonGravityFunction(masses);

	    EulerSolver test3 = new EulerSolver();
	    StateInterface[] results3 = test3.solve(funct3, initial,ts);
	    System.out.println("Test3: ");																												//Display
	    for(int i=0; i< results3.length; i++)
	    {
	        System.out.print(results3[i].toString() + ", ");
	    }
	    System.out.println("\n===========================");
		
		State intermimRes = (State)results3[3];																										//Assertions for planet 1 at time stamp 100 (Index = 3)
		assertEquals(new Vector3d(-14.20511669155974, -4.954715173460928,  0.3994237247979979), intermimRes.velocity.get(0));
		assertEquals(new Vector3d(-6.806797444398345E8, 1.080005038407241E9, 6564052.694065405), intermimRes.position.get(0));
		assertEquals(100.0, intermimRes.time);

		State finalRes = (State)results3[5];																										//Assertions for planet 2 at time stamp 20000 (Index = 5)
		assertEquals(new Vector3d(38920.087035855984, 3528.58897741389, -3282.4721719566264), finalRes.velocity.get(1));
		assertEquals(new Vector3d(7.845596461740923E8, -6.795790837453871E10, -5.769258240054541E9), finalRes.position.get(1));
		assertEquals(20000.00, finalRes.time);
	}

}