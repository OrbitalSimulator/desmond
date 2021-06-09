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
    private double launchTime;
    private double targetTime;
    private double delta = 0.001;

    public NewtonRaphson(Universe universe, int origin, int target, SimulationSettings settings, double launchTime, double targetTime)
    {
        super(universe, target);
        this.settings = settings;
        this.universe = universe;
        this.origin = origin;
        this.target = target;
        this.launchTime = launchTime;
        this.targetTime = targetTime;

        //Calculate V0 using NewtonRaphson
        //Plan final route using V0
    }

    public Matrix3d calculateJacobian(Vector3d initVelocity, Vector3d closestPoint)
    {
        Matrix3d jacobian = new Matrix3d();

        for(int i = 0; i< jacobian.getDimension(); i++)
        {
            for(int j = 0; j < jacobian.getDimension(); j++)
            {
                calculatePartialDerivative(i, j, initVelocity, closestPoint);
            }
        }
        return jacobian;
    }

    public double calculatePartialDerivative(int row, int column, Vector3d initVelocity, Vector3d closestPoint)
    {
        /*Generate delta velocity*/
        double deltaValue = initVelocity.get(column) + delta;
        Vector3d velocityDelta = initVelocity.copyOf();
        velocityDelta.set(column, deltaValue);

        /*Determine distance in x,y,z components*/
        double individualComponentResult = individualComponentResult(closestPoint, row);

        /*Generate new trajectory from deltaValue and subsequent closest point*/
        Vector3d[] derivativeTrajectory = planRoute(velocityDelta);
        Vector3d nextClosestPoint = calculateClosestPoint(derivativeTrajectory);
        double derivativeIndividualComponentResult = individualComponentResult(nextClosestPoint, row);

        /*derivative calculation*/
        double derivative = (derivativeIndividualComponentResult - individualComponentResult) / delta;
        return derivative;
    }

    public double individualComponentResult(Vector3d closestPoint, int row)
    {
        Vector3d componentDistanceMeasure = componentDistanceMeasure(closestPoint);
        return componentDistanceMeasure.get(row);
    }

    public Vector3d componentDistanceMeasure(Vector3d closestPoint)
    {
        return targetPoint.sub(closestPoint);
    }


    //TODO refactor into methods takeStep and planRoute
    public Vector3d[] planRoute(Vector3d initVelocity)
    {
        //TODO make time dynamic
        calculateLaunchAndTargetCoordinates(0,  4.73e7);

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

    public Vector3d calculateClosestPoint(Vector3d[] trajectory)
    {
        Vector3d closestPoint = trajectory[0];
        double distanceMeasure = closestPoint.dist(targetPoint);

        for(Vector3d point: trajectory)
        {
            if(point.dist(targetPoint) < distanceMeasure)
            {
                closestPoint = point;
            }
        }
        System.out.println("Closest distance to titan: " + closestPoint.dist(targetPoint));
        return closestPoint;
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

    public Vector3d getLaunchPoint()
    {
        return launchPoint;
    }
}
