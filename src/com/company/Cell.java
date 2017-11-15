
/*
Name: Binod Katwal
Name of the File: Cell
the file is use by class panels to generate and get cell
 */


package com.company;


import javax.swing.*; //Libraries
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Point;



/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
public class Cell extends JFrame
{
    ArrayList<Cell> neighbors = new ArrayList<Cell>();
    boolean wall = true;
    boolean open = true;
    int rowVal,colVal;
    int xCoordinate, yCoordinate; // coordinates

    public void Cell(int width, int height)
    {
        this.rowVal = width;
        this.colVal = height;
    }

    // this function will constructs the width and height fo the cell
    Cell(int width, int height)
    {
        this(width, height, true); // if true
    }


    // construct Cell at x, y and with whether it isWall
    Cell(int valx, int valY, boolean foundWall)
    {
        this.xCoordinate = valx; // value of width
        this.yCoordinate = valY;// value of height
        this.wall = foundWall;  // boolean variable if found wall
    }

    // add a neighbor to this cell, and this cell as a neighbor to the other
    void findNeighborNadd(Cell next)
    {
        // this will get its own form if next
        if (!this.neighbors.contains(next))
        {
            this.neighbors.add(next);
        }
        // if this , do below
        if (!next.neighbors.contains(this))
        {
            next.neighbors.add(this);
        }
    }
    // this function will check if other object is passed to argument is equal to method is invoked
    @Override
    public boolean equals(Object next)
    {
        Cell nextCell = (Cell)
                next;
        return (this.xCoordinate == nextCell.xCoordinate && this.yCoordinate== nextCell.yCoordinate);
    }

    // returns has code value for object on which the method is invoked
    // this function is override  with  the above function equals
    // random hash code method designed to be usually unique
    @Override
    public int hashCode()
    {

        return this.xCoordinate + this.yCoordinate * 256;
    }



    // ths function will be use by panel class on creatingGrid
    boolean belowNeibhborCell()
    {
        return this.neighbors.contains(new Cell(this.xCoordinate, this.yCoordinate + 1));
    }

    // this function helps to create grid of the maze// called to CreateupdateGrid
    boolean lookForNeighboronRight()
    {
        return this.neighbors.contains(new Cell(this.xCoordinate + 1, this.yCoordinate));
    }

}


/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////