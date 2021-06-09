package src.test;

public abstract class Error
{
    public  static double absolute(double a, double b)
    {
        return Math.abs(a-b);
    }

    public  static double relative(double a, double b)
    {
        return Math.abs((a-b)/a);
    }
}
