package src.traj;

public class Matrix2d extends Matrix
{
    public Matrix2d()
    {
        super(2);
    }

    public Matrix2d(double[] row1, double[] row2)
    {
        super(2);
        setRow(row1, 0);
        setRow(row2, 1);
    }

    public double determinant()
    {
        return get(0,0)*get(1,1) - get(0, 1)*get(1, 0);
    }
}
