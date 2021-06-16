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
    private double delta = 0.001;
    private Vector3d startingVelocity;
    private double epsilon = 0.1;
    private int iterationLimit = 30;
    private int iteration = 0;
    private Vector3d velocityAtTarget;

    private static final boolean DEBUG = true;
    private static boolean visualize = true;

    public NewtonRaphson(Universe universe, int origin, int target, SimulationSettings settings,  Vector3d startingVelocity)
    {
        super(universe, target);
        this.settings = settings;
        this.universe = universe;
        this.origin = origin;
        this.target = target;
        this.startingVelocity = startingVelocity;

        calculateLaunchAndTargetCoordinates();
    }

    //TODO refactor into methods takeStep and planRoute
    public Vector3d[] planRoute(Vector3d initVelocity)
    {
        double[] masses = addMassToEnd(universe.masses, 700);
        ODEFunctionInterface funct = new NewtonGravityFunction(masses);

        Verlet solver = new Verlet();
        int currentStep = settings.stepOffset;
        int trajectoryIndex = 0;
        Vector3d[] trajectory = new Vector3d[settings.noOfSteps+1];

        Vector3d currentPosition = launchPoint;
        Vector3d currentVelocity = initVelocity;
        trajectory[trajectoryIndex] = currentPosition;

        while(trajectoryIndex < settings.noOfSteps)
        {
            double currentTime = currentStep * settings.stepSize;
            State currentState = addProbe(universe.getStateAt(currentStep), currentPosition, currentVelocity);
            State nextState = solver.step(funct, currentTime, currentState, settings.stepSize);

            currentPosition = getProbePosition(nextState);
            currentVelocity = getProbeVelocity(nextState);
            currentStep++;
            trajectoryIndex++;
            trajectory[trajectoryIndex] = currentPosition;
        }
        setVelocityAtTarget(currentVelocity);
        return trajectory;
    }

    public Vector3d newtonRaphsonIterativeMethod()
    {
        Vector3d[] trajectory = planRoute(startingVelocity);
        Vector3d closestPoint = calculateClosestPoint(trajectory);
        double distance = closestPointDistanceToTarget(closestPoint);
        if(DEBUG)
        {
            System.out.println("Iteration 0:");
            System.out.println(startingVelocity.toString());
            System.out.println("Distance: "+ distance);
        }
        if(visualize)
        {
            universe.addTempTrajectory(trajectory);
        }
        iteration++;

        while(distance > epsilon)
        {
            Vector3d nextVelocity = newtonRaphsonStep(startingVelocity, closestPoint);

            startingVelocity = nextVelocity;
            trajectory = planRoute(nextVelocity);
            closestPoint = calculateClosestPoint(trajectory);
            distance = closestPointDistanceToTarget(closestPoint);

            if(visualize)
            {
                universe.addTempTrajectory(trajectory);
            }

            if(DEBUG)
            {
                System.out.println("Iteration: " + iteration);
                System.out.println("Velocity: " + startingVelocity.toString());
                System.out.println("Distance: " + distance);
                System.out.println("------");
            }

            if(iteration > iterationLimit)
            {
                throw new RuntimeException("Newton Raphson did not converge");
            }

            iteration++;
        }

        if(visualize)
        {
            universe.addPermTrajectory(trajectory);
            universe.clearTempTrajectories();
        }

        return startingVelocity;
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

    public Vector3d calculateClosestPoint(Vector3d[] trajectory)
    {
        int finalIndex = trajectory.length - 1;
        return trajectory[finalIndex];
    }

    //TODO Method interferes with NR method, therefore will not be used. Possibly to determine probe velcotiy seperate from origin?
    public void calculateRelativeStartingVelocity(Vector3d startingVelocity)
    {
        Vector3d originVelocity = universe.U[origin][0].velocity;
        this.startingVelocity =  originVelocity.add(startingVelocity);
        System.out.println("Starting velocity: " + this.startingVelocity.toString());
    }

    private void calculateLaunchAndTargetCoordinates()
    {
        //TODO Check settings.noSteps doesn't give out of bounds error'
        int targetPointIndex = settings.stepOffset + settings.noOfSteps;
        CelestialBody targetPlanet = universe.U[target][targetPointIndex];
        targetPoint = targetPlanet.calculateTargetPoint();

        int launchPointIndex = settings.stepOffset;
        CelestialBody launchPlanet = universe.U[origin][launchPointIndex];
        launchPoint = launchPlanet.closestLaunchPoint(targetPoint);
    }

    public Vector3d getLaunchPoint()
    {
        return launchPoint;
    }

    public int getIteration()
    {
        return iteration;
    }

    public double getDelta()
    {
        return delta;
    }

    public void setVelocityAtTarget(Vector3d velocity)
    {

        velocityAtTarget = velocity;
        settings.probeStartVelocity = velocity;
    }

    public Vector3d getVelocityAtTarget()
    {
        return velocityAtTarget;
    }

    public void visualizerOff()
    {
        visualize = false;
    }
}
