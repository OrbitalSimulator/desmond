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
    private Vector3d targetPoint;
    private double delta = 0.00001;
    private Vector3d startingVelocity = new Vector3d(200, -300, 100);
    private double epsilon = 0.1;

    public NewtonRaphson(Universe universe, int origin, int target, SimulationSettings settings)
    {
        super(universe, target);
        this.settings = settings;
        this.universe = universe;
        this.origin = origin;
        this.target = target;

        trajectory = newtonRaphsonSteps(universe);
        universe.addPermTrajectory(trajectory);
    }

    public Vector3d[] newtonRaphsonSteps(Universe universe)
    {
        Vector3d[] trajectory = new Vector3d[settings.noOfSteps+1];
        int currentStep = 0;

        targetPoint = universe.universe[target][currentStep].location;
        Vector3d currentPosition = universe.universe[origin][0].closestLaunchPoint(targetPoint);
        Vector3d currentVelocity = newtonRaphsonIterativeMethod(startingVelocity, currentPosition, currentStep);

        double distance = currentPosition.dist(targetPoint);

        trajectory[currentStep] = currentPosition;
        universe.clearTempTrajectories();

        while(distance > epsilon)
        {
            System.out.println("STEP: " + currentStep);
            Vector3d[] tempTrajectory = planRoute(currentVelocity, currentPosition, currentStep);
            Vector3d nextPosition = tempTrajectory[1];
            Vector3d nextVelocity = newtonRaphsonIterativeMethod(currentVelocity, currentPosition, currentStep);

            distance = closestPointDistanceToTarget(nextPosition);

            currentPosition = nextPosition;
            currentVelocity = nextVelocity;
            trajectory[currentStep++] = currentPosition;
            targetPoint = universe.universe[target][currentStep].location;
            universe.clearTempTrajectories();
        }
        return trajectory;
    }

    public Vector3d newtonRaphsonIterativeMethod(Vector3d initVelocity, Vector3d startPosition, int step)
    {
        Vector3d[] trajectory = planRoute(initVelocity, startPosition, step);
        Vector3d closestPoint = calculateClosestPoint(trajectory);
        double distance = closestPointDistanceToTarget(closestPoint);

        while(distance > epsilon)
        {
            Vector3d nextVelocity = newtonRaphsonSingleStep(initVelocity, closestPoint, startPosition, step);

            initVelocity = nextVelocity;
            trajectory = planRoute(nextVelocity, startPosition, step);
            universe.addTempTrajectory(trajectory);

            closestPoint = calculateClosestPoint(trajectory);
            distance = closestPointDistanceToTarget(closestPoint);
            System.out.println("Velocity: " + initVelocity.toString());
            System.out.println("Distance: " + distance);
            System.out.println("------");
        }
        return initVelocity;
    }

    public Vector3d newtonRaphsonSingleStep(Vector3d initVelocity, Vector3d closestPoint, Vector3d startPosition, int step)
    {
        Matrix3d jacobian = calculateJacobian(initVelocity, closestPoint, startPosition, step);
        Matrix3d inverseJacobian = jacobian.calculateInverseMatrix();
        Vector3d componentDistanceMeasure = componentDistanceMeasure(closestPoint);
        Vector3d inverseJacobianAndDistanceCalculation = inverseJacobian.vectorMultiplication(componentDistanceMeasure);

        Vector3d nextVelocity = initVelocity.sub(inverseJacobianAndDistanceCalculation);
        return nextVelocity;
    }

    public Matrix3d calculateJacobian(Vector3d initVelocity, Vector3d closestPoint, Vector3d startPosition, int step)
    {
        Matrix3d jacobian = new Matrix3d();

        for(int i = 0; i< jacobian.getDimension(); i++)
        {
            for(int j = 0; j < jacobian.getDimension(); j++)
            {
                jacobian.set(i, j, calculatePartialDerivative(i, j, initVelocity, closestPoint, startPosition, step));
            }
        }
        return jacobian;
    }

    public double calculatePartialDerivative(int row, int column, Vector3d initVelocity, Vector3d closestPoint, Vector3d startPosition, int step)
    {
        /*Generate delta velocity*/
        double deltaValue = initVelocity.get(column) + delta;
        Vector3d velocityDelta = initVelocity.copyOf();
        velocityDelta.set(column, deltaValue);

        /*Determine distance in x,y,z components*/
        double individualComponentResult = individualComponentResult(closestPoint, row);

        /*Generate new trajectory from deltaValue and subsequent closest point*/
        Vector3d[] derivativeTrajectory = planRoute(velocityDelta, startPosition, step);
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
    public Vector3d[] planRoute(Vector3d initVelocity, Vector3d startPosition, int step)
    {
        double[] masses = addMassToEnd(universe.masses, 700);
        ODEFunctionInterface funct = new NewtonGravityFunction(masses);

        Verlet solver = new Verlet();
        int index = 0;
        Vector3d[] trajectory = new Vector3d[settings.noOfSteps+1 - step];

        Vector3d currentPosition = startPosition;
        Vector3d currentVelocity = initVelocity;
        trajectory[index] = currentPosition;

        while(index < trajectory.length - 1)
        {
            double currentTime = index * settings.stepSize;
            State currentState = addProbe(universe.getStateAt(index + step), currentPosition, currentVelocity);
            State nextState = solver.step(funct, currentTime, currentState, settings.stepSize);

            currentPosition = getProbePosition(nextState);
            currentVelocity = getProbeVelocity(nextState);
            index++;
            trajectory[index] = currentPosition;
        }
        return trajectory;
    }

    public Vector3d calculateClosestPoint(Vector3d[] trajectory)
    {
        int finalIndex = trajectory.length - 1;
        return trajectory[finalIndex];
    }
}
