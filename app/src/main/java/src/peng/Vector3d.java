package src.peng;

import java.lang.Math;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Vector3d implements Vector3dInterface
{
    protected double x;
    protected double y;
    protected double z; 

    public Vector3d()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    
    public Vector3d(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    
    /**
     * Scalar x vector multiplication, followed by an addition
     * 
    * @param scalar the double used in the multiplication step
    * @param other  the vector used in the multiplication step
    * @return the result of the multiplication step added to this vector,
    * Should yield a+ h*b
    */
    public Vector3d addMul(double scaler, Vector3dInterface other)
    {
        return (this.add(other.mul(scaler)));
    }

    /**
     * The length of the current vector in Euclidean distance
     * @return The scaler quantity representing the Euclidean distance
     */
    public double norm()
    {
        return Math.sqrt(Math.pow(this.x, 2)+ Math.pow(this.y, 2)+ Math.pow(this.z,2));
    }

    /**
     * Calculate the magnitude between two points (Slightly different from standard magnitude)
     * @param Scaler of magnitude between two points.
     */
    public double dist(Vector3dInterface other)
    {
        return Math.sqrt(Math.pow(other.getX() - this.x, 2) + Math.pow(other.getY() - this.y, 2)+ Math.pow(other.getZ() - this.z,2));
    }

    /**
     * Vector normalization
     * @return New normalized vector
     */
    public Vector3d unitVector()
    {
        double magnitude = this.norm();
        return new Vector3d(this.x/magnitude, this.y/magnitude, this.z/magnitude);
    }

    /**
     * Copy of the parameterized vector
     * @return An identical, but new Vector3d object
     */
    public Vector3d copyOf()
    {
        return new Vector3d(this.x, this.y, this.z);
    }
    
    /**
     * Vector addition
     * @param other vector to add
     */
    public Vector3d add(Vector3dInterface other)
    {
        return new Vector3d(this.x + other.getX(), this.y + other.getY(), this.z + other.getZ());
    }

    public Vector3d returnOrthogonal(double xApproximate, double zApproximate)
    {
        double yCalculated = (-this.x * xApproximate - this.z * zApproximate)/ this.y;
        return new Vector3d(xApproximate, yCalculated, zApproximate);
    }

    /**
     * Vector subtraction
     * @param other vector to subtract
     */
    public Vector3d sub(Vector3dInterface other)
    {
        return new Vector3d(this.x - other.getX(), this.y - other.getY(), this.z - other.getZ());
    }

    /**
     * Vector multiplication
     * @param scaler Vector to multiply with.
     */
    public Vector3d mul(double scaler)
    {
        return new Vector3d(this.x * scaler, this.y * scaler, this.z * scaler);
    }

    /**
     * Dot product operation
     * @param other Vector to perform dot product with
     * @return Scaler quantity representing dot product
     */
    public double dotProduct(Vector3dInterface other)
    {
        return (this.x*other.getX()) + (this.y*other.getY() + (this.z*other.getZ()));
    }

    @Override
    /**
     * Method to overide the equals method of Object class, to provide equality checks between vectors.
     * @param comparison Vector object on which to perform comparisons
     */
    public boolean equals(Object o)
    {
        Vector3d v = (Vector3d) o;
    	if((v.getX() == x) && (v.getY() == y) && (v.getZ() == z))
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
        return "("+ this.x + ","+this.y + ","+this.z+")";
    }
    
    /**
     * Represent the vector in String format
     * @return String representing the vector.
     */
    public String toCSV() 
    {
        return this.x + "," + this.y + ","+ this.z + ",";
    }
    
    public double getRoundedX(int digit) {return round(x,digit);}
    public double getRoundedY(int digit) {return round(y,digit);}
    public double getRoundedZ(int digit) {return round(z,digit);}
    
    public double getX() {return x;}
    public double getY() {return y;}
    public double getZ() {return z;}

    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setZ(double z) {this.z = z;}
    public double get(int index)
    {
        switch (index)
        {
            case 0:
                return getX();
            case 1:
                return getY();
            case 2:
                return getZ();
            default:
                throw new RuntimeException("Vector index not found");
        }
    }

    public void set(int index, double value)
    {
        switch (index)
        {
            case 0:
                setX(value);
                break;
            case 1:
                setY(value);
                break;
            case 2:
                setZ(value);
                break;
            default:
                throw new RuntimeException("Vector index not found");
        }
    }
}
