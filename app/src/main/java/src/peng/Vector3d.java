package src.peng;

import java.lang.Math;

//TODO (Travis Dawson) Clean up Vector3d
public class Vector3d implements Vector3dInterface
{
    protected double x;
    protected double y;
    protected double z;
    
    /**
     * Constructor of vector3d
     * @param x x-value
     * @param y y-value
     * @param z z-value
     */
    public Vector3d(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Accessor method
     * @return x-value
     */
    public double getX()
    {
        return this.x;
    }

    /**
     * Accessor method
     * @return y-value
     */
    public double getY()
    {
        return this.y;
    }

    /**
     * Accessor method
     * @return z-value
     */
    public double getZ()
    {
        return this.z;
    }

    /**
     * Setter method
     * @param x Set x-value
     */
    public void setX(double x)
    {
        this.x = x;
    }

    /**
     * Setter method
     * @param y Set y-value
     */
    public void setY(double y)
    {
        this.y = y;
    }

    /**
     * Setter method
     * @param z Set z-value
     */
    public void setZ(double z)
    {
        this.z = z;
    }

    /**
     * Vector addition
     * @param other vector to add
     */
    public Vector3d add(Vector3dInterface other)
    {
        return new Vector3d(this.x + other.getX(), this.y + other.getY(), this.z + other.getZ());
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

    /**
     * Scalar x vector multiplication, followed by an addition
     * 
    * @param scalar the double used in the multiplication step
    * @param other  the vector used in the multiplication step
    * @return the result of the multiplication step added to this vector,
    * Should yeild a+ h*b
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
     * Represent the vector in String format
     * @return String representing the vector.
     */
    public String toString() 
    {
        return "X: "+ this.x + " y: "+this.y + " z: "+this.z;
    }

    /**
     * Copy of the parameterized vector
     * @return An identical, but new Vector3d object
     */
    public Vector3d copyOf()
    {
        return new Vector3d(this.x, this.y, this.z);
    }

    @Override
    /**
     * Method to overide the equals method of Object class, to provide equality checks between vectors.
     * @param comparison Vector object on which to perform comparisons
     */
    public boolean equals(Object comparison)
    {
        Vector3d comp = (Vector3d) comparison;                                              //Cast into vector3D
        if(comp.getX() == this.x && comp.getY() == this.y && comp.getZ() == this.getZ())
        {
            return true;
        }
        return false;
    }
}
