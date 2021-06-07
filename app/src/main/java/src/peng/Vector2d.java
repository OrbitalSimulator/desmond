package src.peng;

import java.lang.Math;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Vector2d
{
    protected double x;
    protected double y;

    public Vector2d()
    {
        this.x = 0;
        this.y = 0;
    }
    
    public Vector2d(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Scalar x vector multiplication, followed by an addition
     * 
    * @param scalar the double used in the multiplication step
    * @param other  the vector used in the multiplication step
    * @return the result of the multiplication step added to this vector,
    * Should yield a+ h*b
    */
    public Vector2d addMul(double scaler, Vector2d other)
    {
        return (this.add(other.mul(scaler)));
    }

    /**
     * The length of the current vector in Euclidean distance
     * @return The scaler quantity representing the Euclidean distance
     */
    public double norm()
    {
        return Math.sqrt(Math.pow(this.x, 2)+ Math.pow(this.y, 2));
    }

    /**
     * Calculate the magnitude between two points (Slightly different from standard magnitude)
     * @param Scaler of magnitude between two points.
     */
    public double dist(Vector2d other)
    {
        return Math.sqrt(Math.pow(other.getX() - this.x, 2) + Math.pow(other.getY() - this.y, 2));
    }

    /**
     * Vector normalization
     * @return New normalized vector
     */
    public Vector2d unitVector()
    {
        double magnitude = this.norm();
        return new Vector2d(this.x/magnitude, this.y/magnitude);
    }

    /**
     * Copy of the parameterized vector
     * @return An identical, but new Vector3d object
     */
    public Vector2d copyOf()
    {
        return new Vector2d(this.x, this.y);
    }
    
    /**
     * Vector addition
     * @param other vector to add
     */
    public Vector2d add(Vector2d other)
    {
        return new Vector2d(this.x + other.getX(), this.y + other.getY());
    }

    public Vector2d returnOrthogonal(double xApproximate)
    {
        double yCalculated = (-this.x * xApproximate)/ this.y;
        return new Vector2d(xApproximate, yCalculated);
    }

    /**
     * Vector subtraction
     * @param other vector to subtract
     */
    public Vector2d sub(Vector2d other)
    {
        return new Vector2d(this.x - other.getX(), this.y - other.getY());
    }

    /**
     * Vector multiplication
     * @param scaler Vector to multiply with.
     */
    public Vector2d mul(double scaler)
    {
        return new Vector2d(this.x * scaler, this.y * scaler);
    }

    /**
     * Dot product operation
     * @param other Vector to perform dot product with
     * @return Scaler quantity representing dot product
     */
    public double dotProduct(Vector2d other)
    {
        return (this.x*other.getX()) + (this.y*other.getY());
    }

    @Override
    /**
     * Method to overide the equals method of Object class, to provide equality checks between vectors.
     * @param comparison Vector object on which to perform comparisons
     */
    public boolean equals(Object o)
    {
        Vector3d v = (Vector3d) o;
    	if((v.getX() == x) && (v.getY() == y))
        {
            return true;
        }
        return false;
    }
    
    private static double round(double value, int places) 
    {
        if (places < 0) 
        	throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    /**
     * Represent the vector in String format
     * @return String representing the vector.
     */
    public String toString() 
    {
        return "(" + this.x + "," + this.y + ")";
    }
    
    /**
     * Represent the vector in String format
     * @return String representing the vector.
     */
    public String toCSV() 
    {
        return this.x + "," + this.y + ",";
    }
    
    public double getRoundedX(int digit) {return round(x,digit);}
    public double getRoundedY(int digit) {return round(y,digit);}
    
    public double getX() {return x;}
    public double getY() {return y;}

    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
}
