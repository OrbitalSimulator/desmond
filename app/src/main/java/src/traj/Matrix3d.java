package src.traj;

import java.util.ArrayList;
import src.peng.Vector3d;

public class Matrix3d extends Matrix
{
    public Matrix3d()
    {
        super(3);
    }

    public Matrix3d(double[] row1, double[] row2, double[] row3)
    {
        super(3);
        setRow(row1, 0);
        setRow(row2, 1);
        setRow(row3, 2);
    }

    public Matrix3d calculateInverseMatrix()
    {
        if(!determineIfInvertable())
        {
            throw new RuntimeException("Matrix is not invertable");
        }

        double determinant = calculateDeterminant();
        Matrix3d adjugateMatrix = calculateMatrixOfCofactors().matrixTranspose();
        return adjugateMatrix.scalerMultiplication(1/determinant);
    }

    public Matrix3d calculateMatrixOfCofactors()
    {
        Matrix3d cofactorMatrix = new Matrix3d();

        for(int i = 0; i < matrixDimension; i++)
        {
            for (int j = 0; j < matrixDimension; j++)
            {
                Matrix2d subMatrix = subMatrix(i, j);
                double subMatrixDeterminant = subMatrix.determinant();
                double sign = determineAlternatingSign(i, j);
                cofactorMatrix.set(i, j, sign * subMatrixDeterminant);
            }
        }
        return cofactorMatrix;
    }

    public double calculateDeterminant()
    {
        double determinant = 0;
        int firstRowIndex = 0;
        for(int i = 0; i < matrixDimension; i++)
        {
            Matrix2d subMatrix = subMatrix(firstRowIndex, i) ;
            double sign = determineAlternatingSign(firstRowIndex, i);
            double scaler = matrix[firstRowIndex][i];
            double resultingDeterminant =  scaler * subMatrix.determinant() * sign;

            determinant += resultingDeterminant;
        }
        return determinant;
    }

    public boolean determineIfInvertable()
    {
        double determinant = calculateDeterminant();
        if(determinant != 0)
        {
            return true;
        }
        return false;
    }

    //Forming subMatrices based on the matrix value selected
    public Matrix2d subMatrix(int rowSelected, int columnSelected)
    {
        ArrayList<double[]> container = new ArrayList<double[]>();

        for(int i = 0; i < matrixDimension; i++)
        {
            //TODO Alter so magic number 2 is not here
            double[] row = new double[2];
            int index = 0;

            for(int j = 0; j < matrixDimension; j++)
            {
                if(i != rowSelected && j != columnSelected)
                {
                    row[index] = matrix[i][j];
                    index++;
                }
            }
            if(i != rowSelected)
            {
                container.add(row);
            }
        }

        return new Matrix2d(container.get(0),container.get(1));
    }

    public double determineAlternatingSign(int selectedRow, int selectedColumn)
    {
        if(selectedRow % 2 == 0 && selectedColumn % 2 == 0)
        {
            return 1;
        }
        else if(selectedRow % 2 == 1 && selectedColumn % 2 == 1)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public Matrix3d matrixTranspose()
    {
        Matrix3d transposeMatrix = new Matrix3d();

        for(int i = 0; i < matrixDimension; i++)
        {
            for(int j = 0; j < matrixDimension; j++)
            {
                transposeMatrix.set(j, i, get(i, j));
            }
        }
        return transposeMatrix;
    }

    public Vector3d vectorMultiplication(Vector3d vector)
    {
        Vector3d result = new Vector3d();

        for(int i = 0; i < matrixDimension; i++)
        {
            double sum = 0;
            for(int j = 0; j < matrixDimension; j ++)
            {
                sum += get(i, j) *  vector.get(j);
            }
            result.set(i, sum);
        }
        return result;
    }

    public Matrix3d scalerMultiplication(double scaler)
    {
        Matrix3d resultantMatrix = new Matrix3d();

        for(int i = 0; i < matrixDimension; i++)
        {
            for(int j = 0; j < matrixDimension; j++)
            {
                resultantMatrix.set(i, j, (get(i, j) * scaler));
            }
        }
        return resultantMatrix;
    }
}