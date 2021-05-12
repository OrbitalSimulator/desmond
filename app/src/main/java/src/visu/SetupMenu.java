package src.visu;
import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.peng.TrajectoryPlanner;
import src.peng.Vector3d;
import src.peng.Vector3dInterface;
import src.univ.CelestialBody;
import src.univ.Universe;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
public class SetupMenu {

    JFrame mainFrame;
    JPanel newPanel;
    CelestialBody[] U;
    CelestialBody [] bodiesToReturn;
    int rowValueForLayout;
    int amountOfBodies;
    JPanel mainPanel;
    JPanel panelForDates;
    JPanel button;
    JPanel[] panelArray;
    Vector3dInterface startPoint;
    Vector3dInterface endPoint;
    JLabel[] titles;
    Vector3d[] newVelocities;
    Vector3d[] newLocations;
    LocalDateTime startTime;
    LocalDateTime endTime;

    JTextField[] fieldsRow1X;
    JTextField[] fieldsRow1Y;
    JTextField[] fieldsRow1Z;

    JTextField[] fieldsRow2X;
    JTextField[] fieldsRow2Y;
    JTextField[] fieldsRow2Z;

    JTextField[] fieldFor101;
    JTextField[] fieldFor102;
    JTextField[] fieldFor103;

    JTextField[] fieldFor104;
    JTextField[] fieldFor105;
    JTextField[] fieldFor106;

    JTextField[] fieldFor107;

    JTextField[] fieldsRow3;

    JComboBox[] comboBoxesForDates;
    JComboBox<String>[] comboBoxesForWaypoints;
    JButton[] buttons;
    JRadioButton[] arrayOfButtons;
    String[] waypoints;

    String[] waypointsToReturn;
    boolean[] selectValuesOfPlanets;

    SimulationSettings settings = SettingsFileManager.load();
    public SetupMenu(CelestialBody[]U) throws IOException {

        mainFrame = new JFrame("Setup Menu");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1850,750);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0,8));

        rowValueForLayout = U.length+1;
        titles = new JLabel[18];

        selectValuesOfPlanets = new boolean[U.length];
        fieldsRow3=new JTextField[U.length];
        this.U = U;

        startTime= settings.startTime;
        endTime=settings.endTime;
        comboBoxesForWaypoints = new JComboBox[2];

        buttons = new JButton[6];
        arrayOfButtons = new JRadioButton[U.length];
        comboBoxesForDates = new JComboBox[6];

        fieldFor101 = new JTextField[U.length];
        fieldFor102 = new JTextField[U.length];
        fieldFor103 = new JTextField[U.length];

        fieldFor104 = new JTextField[U.length];
        fieldFor105 = new JTextField[U.length];
        fieldFor106 = new JTextField[U.length];

        fieldFor107 = new JTextField[U.length];
        for(int i=0;i<comboBoxesForDates.length;i++){
            comboBoxesForDates[i]=new JComboBox();
        }

        for(int i = 0; i<comboBoxesForDates.length; i=i+3){
            for(int j=00;j<32;j++){
                comboBoxesForDates[0].setSelectedItem(startTime.getDayOfMonth());
                comboBoxesForDates[3].setSelectedItem(endTime.getDayOfMonth());
                comboBoxesForDates[i].addItem(j);
            }
        }

        for(int i=1;i<5;i=i+3){
            for(int j=00;j<13;j++){
                comboBoxesForDates[1].setSelectedItem(startTime.getMonthValue());
                comboBoxesForDates[4].setSelectedItem(endTime.getMonthValue());
                comboBoxesForDates[i].addItem(j);
            }
        }

        for(int i=2;i<6;i=i+3){
            for(int j=1990;j<2060;j++){
                comboBoxesForDates[2].setSelectedItem(startTime.getYear());
                comboBoxesForDates[5].setSelectedItem(endTime.getYear());
                comboBoxesForDates[i].addItem(j);
            }
        }

        for(int i=0;i< comboBoxesForWaypoints.length;i++){
            comboBoxesForWaypoints[i]=new JComboBox();
            for (CelestialBody celestialBody : U) {
                comboBoxesForWaypoints[i].addItem(celestialBody.name);
            }
        }

        titles[0] = new JLabel("Start Date");
        titles[1] = new JLabel("End Date");
        titles[2] = new JLabel("Waypoints");
        titles[3] = new JLabel("Name");
        titles[4] = new JLabel("Velocity X");
        titles[5] = new JLabel("x10^");
        titles[6] = new JLabel("Velocity Y");
        titles[7] = new JLabel("x10^");
        titles[8] = new JLabel("Velocity Z");
        titles[9] = new JLabel("x10^");
        titles[10] = new JLabel("Location X");
        titles[11] = new JLabel("x10^");
        titles[12] = new JLabel("Location Y");
        titles[13] = new JLabel("x10^");
        titles[14] = new JLabel("Location Z");
        titles[15] = new JLabel("x10^");
        titles[16] = new JLabel("Mass");
        titles[17] = new JLabel("x10^");

        comboBoxesForWaypoints[0].setSelectedItem(settings.waypoints[0]);
        comboBoxesForWaypoints[1].setSelectedItem("titan");
        panelForDates = new JPanel();
        panelForDates.setLayout(new GridLayout(0,11));
        panelForDates.add(titles[0]);

        for(int i=0;i<3;i++){
            panelForDates.add(comboBoxesForDates[i]);
        }

       panelForDates.add(titles[1]);
        for(int i=3;i<6;i++){
            panelForDates.add(comboBoxesForDates[i]);
        }

        panelArray = new JPanel[8];

        for(int i=0;i<panelArray.length;i++){
            panelArray[i] = new JPanel();
            panelArray[i].setLayout(new GridLayout(U.length+1,0));
        }
        mainFrame.add(mainPanel,BorderLayout.CENTER);
        mainFrame.add(panelForDates,BorderLayout.NORTH);

        newVelocities = new Vector3d[U.length];
        newLocations = new Vector3d[U.length];

        fieldsRow1X=new JTextField[U.length];
        fieldsRow1Y=new JTextField[U.length];
        fieldsRow1Z=new JTextField[U.length];

        fieldsRow2X=new JTextField[U.length];
        fieldsRow2Y=new JTextField[U.length];
        fieldsRow2Z=new JTextField[U.length];


        panelArray[0].add(titles[3]);
        panelArray[1].add(titles[4]);
        panelArray[2].add(titles[6]);
        panelArray[3].add(titles[8]);
        panelArray[4].add(titles[10]);
        panelArray[5].add(titles[12]);
        panelArray[6].add(titles[14]);
        panelArray[7].add(titles[16]);


        for(int i=0;i<U.length;i++){
            fieldFor101[i]=new JTextField("x 10^");
            fieldFor102[i]=new JTextField("x 10^");
            fieldFor103[i]=new JTextField("x 10^");
            fieldFor104[i]=new JTextField("x 10^");
            fieldFor105[i]=new JTextField("x 10^");
            fieldFor106[i]=new JTextField("x 10^");
            fieldFor107[i]=new JTextField("x 10^");
            arrayOfButtons[i] = new JRadioButton(U[i].name);
            panelArray[0].add(arrayOfButtons[i]);
            fieldsRow1X[i]= new JTextField(U[i].velocity.getX()+"");
            panelArray[1].add(fieldsRow1X[i]);

            fieldsRow1Y[i]= new JTextField(U[i].velocity.getY()+"");
            panelArray[2].add(fieldsRow1Y[i]);

            fieldsRow1Z[i]= new JTextField(U[i].velocity.getZ()+"");
            panelArray[3].add(fieldsRow1Z[i]);

            fieldsRow2X[i] = new JTextField(U[i].location.getX()+"");
            panelArray[4].add(fieldsRow2X[i]);

            fieldsRow2Y[i] = new JTextField(U[i].location.getY()+"");
            panelArray[5].add(fieldsRow2Y[i]);

            fieldsRow2Z[i] = new JTextField(U[i].location.getZ()+"");
            panelArray[6].add(fieldsRow2Z[i]);

            fieldsRow3[i] = new JTextField(U[i].mass+"");
            panelArray[7].add(fieldsRow3[i]);
        }
        button = new JPanel();
        button.setLayout(new GridLayout(0,6));
        buttons[0] = new JButton("Start Universe");
        buttons[0].setVisible(false);
        buttons[0].addActionListener(e -> start());

        buttons[1] = new JButton("Set Waypoints");
        buttons[1].addActionListener(e -> setWaypoints());

        buttons[2] = new JButton("Set Dates");
        buttons[2].addActionListener(e-> setDates());

        buttons[3] = new JButton("Change Values");
        buttons[3].addActionListener(e-> changeValues());

        buttons[4] = new JButton("Set Values");
        buttons[4].addActionListener(e-> setValues());
        buttons[4].setVisible(false);

        buttons[5] = new JButton("Undo Changes");
        buttons[5].addActionListener(e-> undo());

        for (JButton jButton : buttons) {
            button.add(jButton);
        }

        mainFrame.add(button,BorderLayout.SOUTH);
        for (int i=0;i<panelArray.length;i++) {
            mainPanel.add(panelArray[i]);
        }

        panelForDates.add(titles[2]);
        for(int i=0;i<comboBoxesForWaypoints.length;i++){
            panelForDates.add(comboBoxesForWaypoints[i]);
        }
        mainFrame.setVisible(true);
    }
    private void start(){
        mainFrame.setVisible(false);
        for(int i=0;i<U.length;i++){
            selectValuesOfPlanets[i]=arrayOfButtons[i].isSelected();
        }
        for(int i=0;i<selectValuesOfPlanets.length;i++){
            if(selectValuesOfPlanets[i]=true){
                for(int j=0;j<amountOfBodies;j++){
                    bodiesToReturn[j]=U[i];
                }
            }
        }
        for (CelestialBody celestialBody : U) {
            if (waypoints[0].equals(celestialBody.name)) {
                startPoint = celestialBody.location;
            }
            if (waypoints[1].equals(celestialBody.name)) {
                endPoint = celestialBody.location;
            }
        }
        waypointsToReturn = new String[2];
        waypointsToReturn[0] = waypoints[0];
        waypointsToReturn[1] = waypoints[1];
        SimulationSettings settingsToSimulate=new SimulationSettings(settings.celestialBodies,startPoint, settings.probeStartVelocity,settings.startTime,settings.endTime,settings.noOfSteps,settings.stepSize, waypointsToReturn);

        Universe universe =new Universe(settingsToSimulate);
        Visualiser visualiser = new Visualiser(universe.universe);
        visualiser.addTrajectory(TrajectoryPlanner.plot(universe, settings));
    }

    private void setWaypoints(){
        buttons[0].setVisible(true);
        waypoints = new String[2];
        for(int i=0;i< waypoints.length;i++){
            waypoints[i] = (String)comboBoxesForWaypoints[i].getSelectedItem();
            System.out.println(waypoints[i]);
        }
    }

    //Bug with datatype
    //ToDo
    private void setDates(){

        String startYear= Integer.toString((Integer) comboBoxesForDates[2].getSelectedItem());
        String startMonth= Integer.toString((Integer) comboBoxesForDates[1].getSelectedItem());
        String startDay= Integer.toString((Integer) comboBoxesForDates[0].getSelectedItem());
        String endYear= Integer.toString((Integer) comboBoxesForDates[5].getSelectedItem());
        String endMonth= Integer.toString((Integer) comboBoxesForDates[4].getSelectedItem());
        String endDay= (Integer.toString((Integer) comboBoxesForDates[3].getSelectedItem()));
        LocalDate startDate = LocalDate.parse(startYear+"-"+startMonth+"-"+startDay);
        LocalTime time = LocalTime.parse("00:00:01");
        startTime = LocalDateTime.of(startDate, time);
        LocalDate date = LocalDate.parse(endYear+"-"+endMonth+"-"+endDay);
        endTime = LocalDateTime.of(date, time);

    }

    private void changeValues(){
        mainPanel.setVisible(false);
        newPanel=new JPanel();
        buttons[4].setVisible(true);
        newPanel.setLayout(new GridLayout(0,15));
        panelArray = new JPanel[15];
        for(int i=0;i<panelArray.length;i++){
            panelArray[i] = new JPanel();
            panelArray[i].setLayout(new GridLayout(U.length+1,0));
        }
        panelArray[0].add(titles[3]);
        panelArray[1].add(titles[4]);
        panelArray[2].add(titles[5]);
        panelArray[3].add(titles[6]);
        panelArray[4].add(titles[7]);
        panelArray[5].add(titles[8]);
        panelArray[6].add(titles[9]);
        panelArray[7].add(titles[10]);
        panelArray[8].add(titles[11]);
        panelArray[9].add(titles[12]);
        panelArray[10].add(titles[13]);
        panelArray[11].add(titles[14]);
        panelArray[12].add(titles[15]);
        panelArray[13].add(titles[16]);
        panelArray[14].add(titles[17]);
        for(int i=0;i<U.length;i++){

            fieldFor101[i]=new JTextField("x 10^");
            fieldFor102[i]=new JTextField("x 10^");
            fieldFor103[i]=new JTextField("x 10^");
            fieldFor104[i]=new JTextField("x 10^");
            fieldFor105[i]=new JTextField("x 10^");
            fieldFor106[i]=new JTextField("x 10^");
            fieldFor107[i]=new JTextField("x 10^");
            arrayOfButtons[i] = new JRadioButton(U[i].name);
            panelArray[0].add(arrayOfButtons[i]);
            fieldsRow1X[i]= new JTextField(U[i].velocity.getX()+"");
            panelArray[1].add(fieldsRow1X[i]);
            panelArray[2].add(fieldFor101[i]);
            fieldsRow1Y[i]= new JTextField(U[i].velocity.getY()+"");
            panelArray[3].add(fieldsRow1Y[i]);
            panelArray[4].add(fieldFor102[i]);
            fieldsRow1Z[i]= new JTextField(U[i].velocity.getZ()+"");
            panelArray[5].add(fieldsRow1Z[i]);
            panelArray[6].add(fieldFor103[i]);
            fieldsRow2X[i] = new JTextField(U[i].location.getX()+"");
            panelArray[7].add(fieldsRow2X[i]);
            panelArray[8].add(fieldFor104[i]);
            fieldsRow2Y[i] = new JTextField(U[i].location.getY()+"");
            panelArray[9].add(fieldsRow2Y[i]);
            panelArray[10].add(fieldFor105[i]);
            fieldsRow2Z[i] = new JTextField(U[i].location.getZ()+"");
            panelArray[11].add(fieldsRow2Z[i]);
            panelArray[12].add(fieldFor106[i]);
            fieldsRow3[i] = new JTextField(U[i].mass+"");
            panelArray[13].add(fieldsRow3[i]);
            panelArray[14].add(fieldFor107[i]);
        }
        for (int i=0;i<panelArray.length;i++) {
            newPanel.add(panelArray[i]);
        }
        mainFrame.add(newPanel);
    }
    private void setValues(){
        for(int i=0;i<U.length;i++){
            U[i].mass= Math.pow(10,Double.parseDouble(fieldFor107[i].getText()))*Double.parseDouble(fieldsRow3[i].getText());
            newVelocities[i] = new Vector3d(Math.pow(10,Double.parseDouble(fieldFor101[i].getText()))*Double.parseDouble(fieldsRow1X[i].getText()),Math.pow(10,Double.parseDouble(fieldFor102[i].getText()))*Double.parseDouble(fieldsRow1Y[i].getText()),Math.pow(10,Double.parseDouble(fieldFor103[i].getText()))*Double.parseDouble(fieldsRow1Z[i].getText()));
            newLocations[i] = new Vector3d(Math.pow(10,Double.parseDouble(fieldFor104[i].getText()))*Double.parseDouble(fieldsRow2X[i].getText()),Math.pow(10,Double.parseDouble(fieldFor105[i].getText()))*Double.parseDouble(fieldsRow2Y[i].getText()),Math.pow(10,Double.parseDouble(fieldFor106[i].getText()))*Double.parseDouble(fieldsRow2Z[i].getText()));
            U[i].velocity=newVelocities[i];
            U[i].location=newLocations[i];
        }
    }
    private void undo(){
        buttons[4].setVisible(false);
        newPanel.setVisible(false);
        mainPanel.setVisible(true);


    }
}
