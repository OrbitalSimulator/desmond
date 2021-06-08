package src.solv;

public  class Error
{
    public  static double absoluteError(double a, double b)
    {
        return Math.abs(a-b);
    }

    public  static double relativeError(double a, double b)
    {
        return Math.abs((a-b)/a);
    }
}
