package src.traj;

import src.conf.SimulationSettings;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.Vector3d;
import src.solv.Verlet;
import src.univ.CelestialBody;
import src.univ.Universe;

public class OrbitController extends GuidanceController
{
	private double orbitalHeight;
	private int targetIndexInState;
	private double orbitalError = 50e3;
	private double scaler = 8000;

	public OrbitController(Universe universe, String target,  SimulationSettings settings)
	{
		super(universe, target);
		trajectory = planRoute(settings, target, universe);
	}

	private Vector3d[] planRoute(SimulationSettings settings, String target, Universe universe)
	{
		double[] masses = addMassToEnd(universe.masses, 700);
		ODEFunctionInterface funct = new NewtonGravityFunction(masses);

		Verlet solver = new Verlet();
		Vector3d[] trajectory = new Vector3d[settings.noOfSteps+1];
		trajectory[0] = (Vector3d) settings.probeStartPosition;

		int currentStep = 0;
		Vector3d currentPosition = (Vector3d) settings.probeStartPosition;
		Vector3d currentVelocity = (Vector3d) settings.probeStartVelocity;

		orbitalHeight = calculateOrbitalHeight(currentPosition, target, universe);

		while(currentStep < settings.noOfSteps)
		{
			double currentTime = currentStep * settings.stepSize;
			State currentState = addProbe(universe.getStateAt(currentStep), currentPosition, currentVelocity);
			State nextState = solver.step(funct, currentTime, currentState, settings.stepSize);

			Vector3d impulse = calculateImpulsionToRemainInOrbit(nextState);

			currentPosition = getProbePosition(nextState);
			currentVelocity = getProbeVelocity(nextState).add(impulse);
			currentStep++;
			trajectory[currentStep] = currentPosition;
		}
		return trajectory;
	}

	public Vector3d calculateImpulsionToRemainInOrbit(State nextState)
	{
		Vector3d probeNextPosition = getProbePosition(nextState);
		Vector3d targetNextPosition = nextState.position.get(targetIndexInState);
		double currentHeight = probeNextPosition.dist(targetNextPosition);
		System.out.println("Current height: "+ currentHeight+ "  orbitalHeight: "+orbitalHeight);
		Vector3d impulse = new Vector3d(0,0,0);

		if(Math.abs(orbitalHeight - currentHeight) >= orbitalError) 													//Orbital breech detected
		{
			if(currentHeight >= orbitalHeight)															//Case 1: Probe has hit outer boundary
			{
				System.out.println(" First Case: Outer");
				Vector3d directionVector = calculateDirectionToTarget(probeNextPosition, targetNextPosition);
				System.out.println(directionVector.toString());
				impulse = directionVector.mul(scaler);
			}
			else																										//Case 2: Probe has hit inner boundary
			{
				System.out.println(" Second case: Inner");
				Vector3d directionVector = calculateDirectionToTarget(probeNextPosition, targetNextPosition);
				directionVector = directionVector.mul(-1); 															    //Invert directionalVector
				System.out.println(directionVector.toString());
				impulse = directionVector.mul(scaler);
			}
			scaler = scaler - scaler/10000; 																							//Apply dampener
			orbitalError = orbitalError - 1000;
		}
		System.out.println("Impulse: " + impulse.toString());
		return impulse;
	}

	//orbital Height  - Method to determine height of orbit. Originating from probe initial position and target planet
	public double calculateOrbitalHeight(Vector3d initialPosition, String target, Universe universe)
	{
		Vector3d targetPlanetPosition = identifyTargetPlanet(universe, target).location;
		return  initialPosition.dist(targetPlanetPosition);
	}

	//Determine's target celestial body from universe
	//TODO Implement version capable of identifying position at start of orbit (traj + orbit cohesion)
	public CelestialBody identifyTargetPlanet(Universe universe, String target)
	{
		for(int i = 0; i< universe.universe.length; i++)
		{
			if(universe.universe[i][0].name.equalsIgnoreCase(target))
			{
				targetIndexInState = i;
				return universe.universe[i][0];
			}
		}
		throw new RuntimeException("Target planet was not identified");
	}

	public Vector3d calculateDirectionToTarget(Vector3d probePosition, Vector3d targetPosition)
	{
		Vector3d directionalVector = targetPosition.sub(probePosition);
		return directionalVector.unitVector();
	}

}