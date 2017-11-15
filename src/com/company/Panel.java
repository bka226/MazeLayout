
/*
Name: Binod Katwal
Name of the File: Panel.java
Called this file to Main class Maze.java

 */



package com.company;

import java.awt.*;
import java.awt.event.*; //Libraries
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public  class Panel extends JPanel { //Declaration of Class panel

    //Declaration of elements
    private JSlider speedSlider, rowsSlider, colsSlider; //declare Jslider
    private  JLabel time, done, visitedGrid; //declare label
    private JLabel speed, rowS, colS;
    private JButton clear, stop, solver; //button
    private JTextField text; //jtext
    private char emptybox = ' ', foundWall = 'X',cellBox = ' ';
    private int rows = 40; //fix row size of the table
    private int widthGrid, heightGrid; // dimension of output grid
    private int columns = 40; //fix col size of the table
    private int squareSize = 480/ rows; // size of the square into the table
    private int x, y;
    Cell beginingCell, currentCell; // Calls class Cell
    private int[][] currentGrid; //current two dimensional array
    boolean searchValid,searchDone; //boolean variable
    Timer timer; // timer declare
    private int delayMode =1000; //delay value
    int startTime =0; //starting timer


    // This will create random maze as well as  it contains different
    // functionality to support creating maze
    private class DisplayMaze {
        private Cell[][] fixCell; // 2d array of Cells
        private Random random = new Random(); // The random object
        private int valWidth;
        private int valHeight;

        private char[][] gridPanel; // output grid

        // constructor
        public DisplayMaze(int displayWidth, int displayHeight)
        {
            valWidth = displayWidth;
            valHeight = displayHeight;
            widthGrid = displayWidth * 2 + 1;
            heightGrid = displayHeight * 2 + 1;
            gridPanel = new char[widthGrid][heightGrid];
            mazeInitilzation();
            mazeGenerator();
        }
        //this  function will  create the cell
        private void mazeInitilzation () {

            fixCell = new Cell[valWidth][valHeight]; //new cell will contain displayed
            for (int val = 0; val < valWidth; val++) { //for loops
                for (int valY = 0; valY < valHeight; valY++) {
                    fixCell[val][valY] = new Cell(val, valY, false); //check and see if you can create cell
                }
            }
        }


        //this function will generate maze and this function is the starting from top most left
        private void mazeGenerator()
        {
            mazeGenerator(0, 0);
        }

        // this function will generate maze, but will use x, y coordinate to process
        private void mazeGenerator(int x, int y)
        {
            // it will generate
            mazeGenerator(printCell(x, y));
        }
        // this function will generate maze, briefing the function
        private void mazeGenerator(Cell beginFrom)
        {

            ArrayList<Cell> listCell = new ArrayList<>();
            if (beginFrom == null) // if starting is empty, just return
                return;
            beginFrom.open = false; // means if its not open.
            listCell.add(beginFrom);

            while (!listCell.isEmpty()) {   //dobe
                Cell cell;
                if (random.nextInt(10) == 0)
                {
                    cell = listCell.remove(random.nextInt(listCell.size())); // random
                }
                else
                    cell = listCell.remove(listCell.size() - 1);
                ArrayList<Cell> neighbors = new ArrayList<>();

                Cell[] relatedNeighbors = new Cell[]
                        {
                                printCell(cell.xCoordinate + 1, cell.yCoordinate),
                                printCell(cell.xCoordinate, cell.yCoordinate + 1),
                                printCell(cell.xCoordinate - 1, cell.yCoordinate),
                                printCell(cell.xCoordinate, cell.yCoordinate - 1)
                        };
                for (Cell other : relatedNeighbors)
                {

                    if (other == null || other.wall || !other.open) //if walls are open then continue
                        continue;
                    neighbors.add(other); // go to next one
                }
                if (neighbors.isEmpty())  // continue
                    continue;
                Cell getrandomCell= neighbors.get(random.nextInt(neighbors.size()));
                // add as neighbor
                getrandomCell.open = false; // indicate cell closed for generation
                cell.findNeighborNadd(getrandomCell);

                listCell.add(cell);
                listCell.add(getrandomCell);
            }
            updateCreateGrid();

        }

        // used to get a Cell at x, y; returns null out of bounds
        public Cell printCell(int width, int height) {
            try {                           //try
                return fixCell[width][height];
            }
            catch (ArrayIndexOutOfBoundsException e) // catch
            {
                return null; // Null
            }
        }

        // draw the maze
        public void updateCreateGrid()
        {

            // search  if empty '  '
            for (int x = 0; x < widthGrid; x++) {
                for (int y = 0; y < heightGrid; y++) {
                    gridPanel[x][y] = emptybox;
                }
            }
            // this part will basically helps to build walls
            for (int x = 0; x < widthGrid; x++) {
                for (int y = 0; y < heightGrid; y++) {
                    if (x % 2 == 0 || y % 2 == 0)
                        gridPanel[x][y] = foundWall; // calls
                }
            }
            //helps to build cels
            for (int x = 0; x < valWidth; x++) { // gives
                for (int y = 0; y < valHeight; y++) {
                    Cell current = printCell(x, y);
                    int gridX = x * 2 + 1, gridY = y * 2 + 1;
                    gridPanel[gridX][gridY] =cellBox ;
                    if (current.belowNeibhborCell()) {
                        gridPanel[gridX][gridY + 1] = cellBox;
                    }
                    if (current.lookForNeighboronRight()) {
                        gridPanel[gridX + 1][gridY] = cellBox; //gets cell
                    }
                }
            }

            creatingMaze(); // call the function

            for (int valx = 0; valx < widthGrid; valx++) {
                for (int valY = 0; valY < heightGrid; valY++) {
                    if (gridPanel[valx][valY] == foundWall && currentGrid[valx][valY] != 2 && currentGrid[valx][valY] != 3) {
                        currentGrid[valx][valY] = 1;
                    }
                }
            }
        }  //get the grid cell, wall
    }




    // this function basically creates new grid of the maze
    //maze going to have look  out number of columnss and rowss
    private void createSquareTable (Boolean mazeCreation)
    {
        rows = (int) (rowsSlider.getValue()); // int gets row value
        columns = (int) (colsSlider.getValue());// int get column value
        squareSize = 480 / (rows > columns ? rows : columns); // get the square

        if (mazeCreation && rows % 2 == 0) {
            rows -= 1;
        }
        if (mazeCreation && columns % 2 == 0) {
            columns -= 1;
        }
        currentGrid = new int[rows][columns];
        beginingCell = new Cell(rows - 2, 1);
        currentCell = new Cell(1, columns - 2);
        if (mazeCreation)
        {
            DisplayMaze maze = new DisplayMaze(rows / 2, columns / 2); //display if true
        } else {   // otherwise
            creatingMaze(); // starting creating maze
        }
    } //end of createSquareTable

    // this function will basically gets the value of speed, rows and column
    public class event implements ChangeListener {

        public void stateChanged(ChangeEvent evn) {
            int sped = speedSlider.getValue();
            int rw = rowsSlider.getValue();
            int col = colsSlider.getValue();
            speed.setText("Speed :" + sped);
            rowS.setText("ROWS :" + rw);
            colS.setText("COLS :" + col);

        }
    }
    // this function will initialize value in the mazeGrid
    public void creatingMaze() {

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                currentGrid[r][c] = 0;
            }
        }
        beginingCell = new Cell(rows - 2, 1);
        currentCell = new Cell(1, columns - 2);
        timer.stop();
        repaint();

    } //


    // this function will print the mazeGrid
    public void paintComponent(Graphics grahic) {

        super.paintComponent(grahic);

        grahic.setColor(Color.GREEN);
        grahic.fillRect(10, 10, columns * squareSize + 1, rows * squareSize + 1);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (currentGrid[r][c] == 0) {
                    grahic.setColor(Color.WHITE);
                }  else if (currentGrid[r][c] == 1) {
                    grahic.setColor(Color.orange);
                } else if (currentGrid[r][c] ==currentGrid[1][1]) {
                    grahic.setColor(Color.GREEN);
                } else if (currentGrid[r][c] == currentGrid[r][c]) {
                    grahic.setColor(Color.blue);
                }
                grahic.fillRect(11 + c * squareSize, 11 + r * squareSize, squareSize-2 , squareSize-2);
            }
        }


    }
    public Panel(int width, int height) // inside parameter of the variable height and width
    {
        //timer function
        //display timer

        setLayout(null);
        MouseHandler listener = new MouseHandler();// adding listener
        addMouseListener(listener); // adding lister to the mouselister
        addMouseMotionListener(listener); //adding listener to the mousemotionlister
        setPreferredSize(new Dimension(width, height));
        currentGrid = new int[rows][columns];

        //Initialization of  GUI Variables, Buttons, Labels,TextField,CheckBOx
        text = new JTextField();
        text.setEditable(false);
        //info of text into textinfo

        JTextField textInfo = new JTextField();
        textInfo.setBackground(Color.BLUE);
        textInfo.setEditable(false);
        //Time display
        time  = new JLabel("Time:00");
        time.setBounds(150, 530, 100, 40);
        time.setFont(new Font("Helvetica", Font.BOLD, 16));
        //Visited display
        visitedGrid  = new JLabel("Visit:0 %");

        visitedGrid.setBounds(300, 530, 100, 40);;
        JLabel start = new JLabel("START", JLabel.CENTER);
        start.setForeground(Color.green);


        JLabel end = new JLabel("END", JLabel.CENTER);
        end.setForeground(Color.red);

        //DONE Display
        done  = new JLabel("Done");
        done.setBounds(10, 530, 100, 40);
        done.setFont(new Font("Helvetica", Font.BOLD, 16));
        add(done);
        add(time);
        add(start);
        add(end);
        add(visitedGrid);

        //Introduction of the GUI elements
        JCheckBox generate = new JCheckBox("Show Generation");//displays checkbox
        JCheckBox showSolve = new JCheckBox("Show Solver"); //displays
        solver = new JButton("Solve"); //button
        clear = new JButton("Clear Maze");
        stop = new JButton("Stop");
        JButton strt = new JButton("Start");
        speed = new JLabel("Speed:20")  ;//label
        speedSlider = new JSlider(JSlider.HORIZONTAL, 0,100,20); //slider
        speedSlider.setMajorTickSpacing(20);
        speedSlider.setPaintTicks(true);
        delayMode= 1000 - speedSlider.getValue();
        speedSlider.addChangeListener((ChangeEvent e) -> {
            JSlider sped = (JSlider) e.getSource();
            if (!sped.getValueIsAdjusting()) {
                delayMode = 1000 - sped.getValue();
            }
        });

        rowS = new JLabel("Rows:")  ;
        rowsSlider = new JSlider(JSlider.HORIZONTAL, 10,50,43);//slider
        rowsSlider.setMajorTickSpacing(10);
        rowsSlider.setPaintTicks(true);
        colS = new JLabel("Cols:")  ;
        colsSlider = new JSlider(JSlider.HORIZONTAL, 10,50,43);//slider
        colsSlider.setMajorTickSpacing(10);
        colsSlider.setPaintTicks(true);
        JButton getRandom = new JButton("Generate Maze");//button
        getRandom.setBackground(Color.green);
        timer = new javax.swing.Timer(delayMode, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time.setText("Timer:" + startTime );
                startTime++;

            }

        });


//  Location -Bounds of the Graphics elements
        getRandom.setBounds(530, 10, 120, 60);
        generate.setBounds(650, 10, 170, 60);
        solver.setBounds(530, 90, 120, 60);
        showSolve.setBounds(650, 90, 170, 50);
        solver.setBounds(530, 110, 120, 60);
        speed.setBounds(570, 180, 200,50);
        speedSlider.setBounds(540,  200, 200,50);
        rowS.setBounds(570, 240, 200,50);
        rowsSlider.setBounds(540, 260, 200,50);
        colS.setBounds(570, 300, 200,50);
        colsSlider.setBounds(540, 330, 200,50);
        clear.setBounds(520, 370, 250, 80);
        stop.setBounds(520, 460, 100, 80);
        strt.setBounds(640, 460, 100, 80);
        start.setBounds(420, 530, 80, 15);
        end.setBounds(420, 560, 80, 15); //end =t

        textInfo.setBounds(-5, 510, 510, 80);


        //Added to the Graphic elements to the JFrame
        add(generate);
        add(solver);
        add(getRandom);
        add(speed);
        add(speedSlider);
        add(rowS);
        add(rowsSlider);
        add(colS);
        add(colsSlider);
        add(showSolve);
        add(stop);
        add(clear);
        add(strt);
        add(textInfo);

        add(text);
        creatingMaze(); // Get the maze created

        //Clear the maze and displays the grid
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                createSquareTable(false);
                timer.stop();
                time.setText("Timer:00" );
                startTime =0;
                getRandom.setEnabled(true);
                generate.setEnabled(true);
                solver.setEnabled(true); //able these buttons before generating maze
                showSolve.setEnabled(true);

            }
        });
        // displays random maze game
        getRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSquareTable(true); //create table
                generate.setAutoscrolls(false);
                time.setText("Timer:00" );

            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stop solving  then
                timer.stop(); //stop timer

            }
        });
        strt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               // solver will start again from where it stopped using DFS algorithm

                timer.start(); //start timer
            }
        });
        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                createSquareTable(true); //create table
                time.setText("Timer:00" );


            }
        });
        solver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTime =1;
                timer.start();
                //will solve using depth first search
                //speed will be connect to the solver function
                int speed = (int) (speedSlider.getValue()); // int gets row value




            }
        });
        showSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTime =1;
                //will solve using depth first search
                timer.start();//start


            }
        });


        event evn = new event();
        speedSlider.addChangeListener(evn);
        rowsSlider.addChangeListener(evn);
        colsSlider.addChangeListener(evn);

    }
    private class MouseHandler implements MouseListener, MouseMotionListener {
        int currentRow;
        int currentColumns;
        int currentValue;
        @Override
        public void mousePressed(MouseEvent events) {
            int eventRow = (events.getY() - 10) / squareSize; // gets row of the event
            int eventColumns = (events.getX() - 10) / squareSize; // gets  column of event  size mentioned

            if (eventRow >= 0 && eventRow< rows && eventColumns >= 0 && eventColumns < columns) { // if condition accepted

                creatingMaze(); // then create the maze
                currentRow = eventRow; //get row
                currentColumns = eventColumns; //get col value
                currentValue = currentGrid[eventRow][eventColumns]; // get current grid
            }
        }

        // those are not in use for the game yet
        @Override
        public void mouseDragged(MouseEvent evt) {
        }

        @Override
        public void mouseReleased(MouseEvent evt) {
        }

        @Override
        public void mouseEntered(MouseEvent evt) {
        }

        @Override
        public void mouseExited(MouseEvent evt) {
        }

        @Override
        public void mouseMoved(MouseEvent evt) {
        }

        @Override
        public void mouseClicked(MouseEvent evt) {
        }
    } // end nested class MouseHandler


}
//End of the class panels
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////