package src.peng;

import java.lang.Math;
import java.util.ArrayList;

public class ExponentialFunction implements ODEFunctionInterface
{
    public RateInterface call(double t, StateInterface y)
    {
        ArrayList<Vector3d> cv = new ArrayList<Vector3d>();
        Vector3d derivative = new Vector3d(0, Math.exp(t),0);
        cv.add(derivative);

        ArrayList<Vector3d> cp = new ArrayList<Vector3d>();
        cp.add(new Vector3d());

        return new Rate(cv, cp);
    }
}
