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
	private double scaler = 0;
	private double dampener = 10000;
	public static final boolean DEBUG =  false;
	public static boolean visualize = true;

	public static boolean log = false;
	private double[] errorCollection;
	private double[] velocityCollection;
	private int loggingIndex = 0;


	public OrbitController(Universe universe, int target, SimulationSettings settings)
	{
		super(universe, target);
		double optimumVelocity = linearClimbing(universe, target, settings);
		settings.setStartVelocity(optimumVelocity);
		trajectory = planRoute(settings, target, universe);
		if(visualize)
		{
			universe.addPermTrajectory(trajectory);
		}
	}

	private Vector3d[] planRoute(SimulationSettings settings, int target, Universe universe)
	{
		double[] masses = addMassToEnd(universe.masses, 700);
		ODEFunctionInterface funct = new NewtonGravityFunction(masses);

		Verlet solver = new Verlet();
		Vector3d[] trajectory = new Vector3d[settings.noOfSteps+1];

		int currentStep = settings.stepOffset;
		//TODO Adapt to fit time period wanted
		CelestialBody temp = universe.U[target][0];
		Vector3d currentPosition = temp.calculateTargetPoint();
		//TODO Adapt for first state of time period wanted
		trajectory[0] = currentPosition;
		//TODO Adapt for first state velocity
		Vector3d currentVelocity = (Vector3d) settings.probeStartVelocity;

		//TODO Alter finishing condition of loop
		while(currentStep < settings.noOfSteps)
		{
			double currentTime = currentStep * settings.stepSize;
			State currentState = addProbe(universe.getStateAt(currentStep), currentPosition, currentVelocity);
			State nextState = solver.step(funct, currentTime, currentState, settings.stepSize);

			CelestialBody targetPlanet = universe.U[target][currentStep];
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
				System.out.println("Impulse: "+ impulse.toString());
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
				System.out.println("Impulse: "+ impulse.toString());
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

	//TODO Logger to obtain route evaluation for logging and determine local maxima.
	//Values to obtain current orbit:
	// velocityLowerLimit = 1200.; change = 6000; number of steps = 80000; step size = 12; orbitalHeight = 450e3; Acceptable error = 100e3
	public double orbitalHillClimbing(Universe universe, int target, SimulationSettings settings)
	{
		double velocityLowerLimit = 1200.0;

		double change = 6000;

		double acceptableError = 10e3;
		double currentError = Double.MAX_VALUE;

		Vector3d[] route = new Vector3d[settings.noOfSteps+1];
		double orbitalHeight = getOrbitalHeight(universe, target);

		double lowerLimitError = routeEvaluation(velocityLowerLimit, settings, universe, target, orbitalHeight);

		double currentVelocity =  velocityLowerLimit + change;

		int temp = 20;

		while(temp>0)
		{
			currentError = routeEvaluation(currentVelocity, settings, universe, target, orbitalHeight);

			System.out.println("Current velocity: "+ currentVelocity);
			System.out.println("Lower limit error: "+ lowerLimitError);
			System.out.println("Current Error: "+ currentError);

			if(currentError > lowerLimitError)
			{
				change = change/2;
			}
			else
			{
				lowerLimitError = currentError;
				velocityLowerLimit = currentVelocity;
			}

			currentVelocity = velocityLowerLimit + change;
			temp--;
		}
		System.out.println("Optimum velocity is: "+ currentVelocity);
		return currentVelocity;
	}

	public double linearClimbing(Universe universe, int target, SimulationSettings settings)
	{
		double bestVelocity = 0;
		double currentVelocity = 8396;
		Vector3d[] route = new Vector3d[settings.noOfSteps+1];
		double orbitalHeight = getOrbitalHeight(universe, target);
		double error = Double.MAX_VALUE;

		int temp = 20;

		if(log)
		{
			errorCollection = new double[temp + 1];
			velocityCollection = new double[temp + 1];

			errorCollection[loggingIndex] = error;
			velocityCollection[loggingIndex] = currentVelocity;
			loggingIndex++;
		}

		while(temp>0)
		{
			double newError = routeEvaluation(currentVelocity, settings, universe, target, orbitalHeight);

			System.out.println("Current velocity " + currentVelocity + " Error: " + newError);
			if(newError < error)
			{
				System.out.println("New best velocity: " + currentVelocity + " Error: " + newError);
				bestVelocity = currentVelocity;
				error = newError;
			}

			if(log)
			{
				errorCollection[loggingIndex] = newError;
				velocityCollection[loggingIndex] = currentVelocity;
				loggingIndex++;
			}

			currentVelocity = currentVelocity + 0.05;
			temp--;
		}
		System.out.println("Optimum velocity is: "+ bestVelocity);
		return bestVelocity;
	}

	public double routeEvaluation(double velocity, SimulationSettings settings, Universe universe, int target, double orbitalHeight)
	{
		settings.setStartVelocity(velocity);
		Vector3d[] route = planRoute(settings, target, universe);

		double[] distanceMeasure = trajectoryToDistanceMeasure(route, universe, target);
		double routeError = trajectoryFitnessCalculation(orbitalHeight, distanceMeasure);
		return routeError;
	}

	public double trajectoryFitnessCalculation(double orbitalHeight, double[] distanceMeasure)
	{
		double sum = 0;
		for(int i= 0; i < distanceMeasure.length; i++)
		{
			sum += Math.abs(distanceMeasure[i] - orbitalHeight);
		}
		return sum / distanceMeasure.length;
	}

	//Generate distance measures for each state in the trajectory
	public double[] trajectoryToDistanceMeasure(Vector3d[] trajectory, Universe universe, int target)
	{
		double[] distanceMeasure = new double[trajectory.length];

		for(int i = 0; i< trajectory.length; i++)
		{
			distanceMeasure[i] = trajectory[i].dist(universe.U[target][i].location);
		}
		return distanceMeasure;
	}

	public double getOrbitalHeight(Universe universe, int target)
	{
		return universe.U[target][0].orbitalHeight;
	}

	public static void setLogActive()
	{
		log = true;
	}

	public double[] getErrorCollection()
	{
		return errorCollection;
	}

	public double[] getVelocityCollection()
	{
		return  velocityCollection;
	}

	public static void visualizerOff()
	{
		visualize = false;
	}
}