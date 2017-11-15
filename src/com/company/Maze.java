/*
Name : Binod Katwal
Class: CS 335
Name of the Program: Maze.java

 */


package com.company;

import javax.swing.*;
import java.awt.*;

///////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
public class Maze extends JFrame { //Maze class

    private int width = 790; //fixed size of the main board
    private int height = 610;

    //Main Function of the Program//
    public static void main(String[] args) { //Main function
        new Maze(); //Calls the functions inside the Maze class


    }
    public Maze() {
        super("Maze Game"); //Display title of the program
        // getInfo(this);
        displayGUI(this); // display Gui

    }

    public void displayGUI(Maze display) { //Display Gui
        display.setSize(width, height); //JFrame size
        display.setResizable(false); //Can't resize
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.setBackground(Color.DARK_GRAY);
        display.setContentPane(new Panel(width,height)); //Calls class Panel
        display.setVisible(true); // displays and make available
    }

}

/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////