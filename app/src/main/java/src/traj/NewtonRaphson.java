package src.traj;

import src.conf.SimulationSettings;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.Vector3d;
import src.solv.Verlet;
import src.univ.CelestialBody;
import src.univ.Universe;
import src.visu.Visualiser;

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
    private double delta = 0.000001;
    private Vector3d startingVelocity = new Vector3d(200, -300, 100);
    private double epsilon = 0.005;

    public NewtonRaphson(Universe universe, int origin, int target, SimulationSettings settings, double launchTime, double targetTime)
    {
        super(universe, target);
        this.settings = settings;
        this.universe = universe;
        this.origin = origin;
        this.target = target;
        this.launchTime = launchTime;
        this.targetTime = targetTime;

        calculateLaunchAndTargetCoordinates();

        Vector3d optimalVelocity = newtonRaphsonIterativeMethod(startingVelocity);
        trajectory = planRoute(optimalVelocity);
    }

    public Vector3d newtonRaphsonIterativeMethod(Vector3d initVelocity)
    {
        Vector3d[] trajectory = planRoute(initVelocity);
        Vector3d closestPoint = calculateClosestPoint(trajectory);
        double distance = closestPointDistanceToTarget(closestPoint);

        while(distance > epsilon)
        {
            Vector3d nextVelocity = newtonRaphsonStep(initVelocity, closestPoint);

            initVelocity = nextVelocity;
            trajectory = planRoute(nextVelocity);
            universe.addTempTrajectory(trajectory);
            closestPoint = calculateClosestPoint(trajectory);
            distance = closestPointDistanceToTarget(closestPoint);
            System.out.println("Velocity: " + initVelocity.toString());
            System.out.println("Distance: " + distance);
            System.out.println("------");
        }
        universe.addPermTrajectory(trajectory);
        return initVelocity;
    }

    public Vector3d newtonRaphsonStep(Vector3d initVelocity, Vector3d closestPoint)
    {
        Matrix3d jacobian = calculateJacobian(initVelocity, closestPoint);
        Matrix3d inverseJacobian = jacobian.calculateInverseMatrix();
        Vector3d componentDistanceMeasure = componentDistanceMeasure(closestPoint);
        Vector3d inverseJacobianAndDistanceCalculation = inverseJacobian.vectorMultiplication(componentDistanceMeasure);

        Vector3d nextVelocity = initVelocity.sub(inverseJacobianAndDistanceCalculation);
        return nextVelocity;
    }

    public Matrix3d calculateJacobian(Vector3d initVelocity, Vector3d closestPoint)
    {
        Matrix3d jacobian = new Matrix3d();

        for(int i = 0; i< jacobian.getDimension(); i++)
        {
            for(int j = 0; j < jacobian.getDimension(); j++)
            {
                jacobian.set(i, j, calculatePartialDerivative(i, j, initVelocity, closestPoint));
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

    public double closestPointDistanceToTarget(Vector3d closestPoint)
    {
        return closestPoint.dist(targetPoint);
    }

    //TODO refactor into methods takeStep and planRoute
    public Vector3d[] planRoute(Vector3d initVelocity)
    {
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
        int finalIndex = trajectory.length - 1;
        return trajectory[finalIndex];
    }



    private void calculateLaunchAndTargetCoordinates()
    {
        int targetPointIndex = (int) (targetTime / settings.stepSize);
        CelestialBody targetPlanet = universe.U[target][targetPointIndex];
        targetPoint = targetPlanet.calculateTargetPoint();

        int launchPointIndex = (int) (launchTime / settings.stepSize);
        CelestialBody launchPlanet = universe.U[origin][launchPointIndex];
        launchPoint = launchPlanet.closestLaunchPoint(targetPoint);
    }

    public Vector3d getLaunchPoint()
    {
        return launchPoint;
    }
}
