package src.traj;

import src.conf.SimulationSettings;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.Vector3d;
import src.solv.Verlet;
import src.univ.CelestialBody;
import src.univ.Universe;

public class NewtonRaphson extends GuidanceController
{
    private SimulationSettings settings;
    private Universe universe;
    private int origin;
    private int target;
    private Vector3d launchPoint;
    private Vector3d targetPoint;

    public NewtonRaphson(Universe universe, int origin, int target, SimulationSettings settings)
    {
        super(universe, target);
        this.settings = settings;
        this.universe = universe;
        this.origin = origin;
        this.target = target;

        //Calculate V0 using NewtonRaphson
        //Plan final route using V0
    }

    public Vector3d[] planRoute(double launchTime, double targetTime, Vector3d initVelocity)
    {
        //TODO make time dynamic
        calculateLaunchAndTargetCoordinates(0, 1000);

        double[] masses = addMassToEnd(universe.masses, 700);
        ODEFunctionInterface funct = new NewtonGravityFunction(masses);

        Verlet solver = new Verlet();
        int currentStep = settings.stepOffset;
        Vector3d[] trajectory = new Vector3d[settings.noOfSteps+1 - currentStep];

        Vector3d currentPosition = launchPoint;
        Vector3d currentVelocity = initVelocity;
        trajectory[0] = currentPosition;

        while(currentStep < settings.noOfSteps)
        {
            double currentTime = currentStep * settings.stepSize;
            State currentState = addProbe(universe.getStateAt(currentStep), currentPosition, currentVelocity);
            State nextState = solver.step(funct, currentTime, currentState, settings.stepSize);

            currentPosition = getProbePosition(nextState);
            currentVelocity = getProbeVelocity(nextState);
            currentStep++;
            trajectory[currentStep] = currentPosition;
        }
        return trajectory;
    }

    private void calculateLaunchAndTargetCoordinates(double launchTime, double targetTime)
    {
        int targetPointIndex = (int) (targetTime / settings.stepSize);
        CelestialBody targetPlanet = universe.universe[target][targetPointIndex];
        targetPoint = targetPlanet.calculateTargetPoint();

        int launchPointIndex = (int) (launchTime / settings.stepSize);
        CelestialBody launchPlanet = universe.universe[origin][launchPointIndex];
        launchPoint = launchPlanet.closestLaunchPoint(targetPoint);
    }
}
