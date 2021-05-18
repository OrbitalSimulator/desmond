package src.traj;

import src.conf.SimulationSettings;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.Vector3d;
import src.solv.Verlet;
import src.univ.CelestialBody;
import src.univ.Universe;

//Working under assumption that probe will always approach target at offset 90 degrees to the planet.
public class OrbitController extends GuidanceController
{
	private double scaler = 3000;
	private double dampener = 5000;
	public static final boolean DEBUG = false;

	public OrbitController(Universe universe, int target, SimulationSettings settings)
	{
		super(universe, target);
		trajectory = planRoute(settings, target, universe);
	}

	private Vector3d[] planRoute(SimulationSettings settings, int target, Universe universe)
	{
		double[] masses = addMassToEnd(universe.masses, 700);
		ODEFunctionInterface funct = new NewtonGravityFunction(masses);

		Verlet solver = new Verlet();
		Vector3d[] trajectory = new Vector3d[settings.noOfSteps+1];
		trajectory[0] = (Vector3d) settings.probeStartPosition;

		int currentStep = settings.stepOffset;
		CelestialBody temp = universe.universe[target][0];
		Vector3d currentPosition = temp.determineOffsetTargetPosition(temp.location.getX(), temp.location.getY());
		//Vector3d currentPosition = (Vector3d) settings.probeStartPosition;
		Vector3d currentVelocity = (Vector3d) settings.probeStartVelocity;

		while(currentStep < settings.noOfSteps)
		{
			double currentTime = currentStep * settings.stepSize;
			State currentState = addProbe(universe.getStateAt(currentStep), currentPosition, currentVelocity);
			State nextState = solver.step(funct, currentTime, currentState, settings.stepSize);

			//TODO Access target Planet at the current state
			//TODO Use int target to access planet
			CelestialBody targetPlanet = universe.universe[target][currentStep];
			Vector3d impulse = calculateImpulsionToRemainInOrbit(nextState, targetPlanet);

			currentPosition = getProbePosition(nextState);
			currentVelocity = getProbeVelocity(nextState).add(impulse);
			currentStep++;
			trajectory[currentStep] = currentPosition;
		}
		return trajectory;
	}

	public Vector3d calculateImpulsionToRemainInOrbit(State nextState, CelestialBody target)
	{
		Vector3d probeNextPosition = getProbePosition(nextState); 														//Probe next position

		Vector3d impulse = new Vector3d(0,0,0);
		String probeOrbitalStatus = target.orbitalBoundaryBreech(probeNextPosition);

		if(probeOrbitalStatus.equalsIgnoreCase("Outer boundary"))
		{
			Vector3d directionVector = calculateProbeLinearDirectionVector(probeNextPosition);
			directionVector = directionVector.mul(-1);																	//Invert directionalVector
			impulse = directionVector.mul(scaler);
			//TODO delete DEBUG
			if(DEBUG)
			{
				System.out.println(" First Case: Outer");
				System.out.println("Direction Vector: " +directionVector.toString());
			}
		}
		else if(probeOrbitalStatus.equalsIgnoreCase("Inner boundary"))
		{
			Vector3d directionVector = calculateProbeLinearDirectionVector(probeNextPosition);
			impulse = directionVector.mul(scaler);
			//TODO delete DEBUG
			if(DEBUG)
			{
				System.out.println(" Second case: Inner");
				System.out.println("Direction Vector: " +directionVector.toString());
			}
		}

		if(!probeOrbitalStatus.equalsIgnoreCase("Neutral"))													//Apply dampener
		{
			scaler = scaler - scaler/dampener;
		}

		return impulse;
	}

	public Vector3d calculateProbeLinearDirectionVector(Vector3d probePosition)
	{
		return probePosition.unitVector();
	}

}