package src.traj;

import java.util.Arrays;

public abstract class Matrix
{
    protected double[][] matrix;
    protected int matrixDimension;

    public Matrix(int dimension)
    {
        setDimension(dimension);
        matrix = new double[matrixDimension][matrixDimension];
    }

    public double get(int row, int column)
    {
        return matrix[row][column];
    }

    public void setRow(double[] row, int rowIndex)
    {
        for(int i = 0; i < matrixDimension; i++)
        {
            set(rowIndex, i, row[i]);
        }
    }

    public double[] getRow(int row)
    {
        return matrix[row];
    }

    public void set(int row, int column, double value)
    {
        matrix[row][column] = value;
    }

    public void setDimension(int dimension)
    {
        matrixDimension = dimension;
    }

    public int getDimension()
    {
        return matrixDimension;
    }

    public boolean equals(Matrix comparison)
    {
        for(int i = 0; i < matrixDimension; i++)
        {
            for(int j = 0; j < matrixDimension; j++)
            {
                if(matrix[i][j] != comparison.getMatrix()[i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }

    public String toString()
    {
        String result = "";
        for(int i = 0; i < matrixDimension; i++)
        {
            result += Arrays.toString(getRow(i)) + "\n";
        }
        return result;
    }

    public double[][] getMatrix()
    {
        return matrix;
    }
}

