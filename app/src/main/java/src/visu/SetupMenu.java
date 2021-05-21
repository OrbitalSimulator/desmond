package src.visu;


import src.conf.SimulationSettings;
import src.peng.Vector3d;
import src.peng.Vector3dInterface;
import src.univ.CelestialBody;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SetupMenu {

    private JFrame mainFrame;
    private JPanel newPanel;
    private CelestialBody[] U;
    private CelestialBody [] bodiesToReturn;
    private int rowValueForLayout;
    private int amountOfBodies;

    private JPanel panelForBodies;
    private JPanel panelForWaypoints;


    private JPanel panelForDates;
    private JPanel button;

    private JPanel[] panelArray;

    private Vector3dInterface startPoint;
    private Vector3dInterface endPoint;
    private JLabel[] titles;

    private Vector3d[] newVelocities;
    private Vector3d[] newLocations;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    SimulationSettings settings;

    private JTextField[] fieldForVelocityX;
    private JTextField[] fieldForVelocityY;
    private JTextField[] fieldForVelocityZ;

    private JTextField[] fieldForLocationX;
    private JTextField[] fieldForLocationY;
    private JTextField[] fieldForLocationZ;
    private JTextField[] fieldForMass;
    private JTextField[] fieldForPowerof10VelocityX;
    private JTextField[] fieldForPowerof10VelocityY;
    private JTextField[] fieldForPowerof10VelocityZ;

    private JTextField[] fieldForPowerof10LocationX;
    private JTextField[] fieldForPowerof10LocationY;
    private JTextField[] fieldForPowerof10LocationZ;

    private JTextField[] fieldForPowerof10Mass;

    private JComboBox<Integer>[] comboBoxesForDates;

    private JComboBox<String>[] comboBoxesForWaypoints;

    private JButton[] buttons;
    private JRadioButton[] arrayOfButtons;
    private String[] waypoints;

    private JRadioButton[] startPointArray;
    private JRadioButton[] wayPoint1Array;
    private JRadioButton[] wayPoint2Array;
    private JRadioButton[] endPointArray;

    private String[] waypointsToReturn;
    private boolean[] selectValuesOfPlanets;
    private boolean inputComplete = false;

    private int intValueOfStartDay;
    private int intValueOfStartMonth;


    private int intValueOfEndDay;
    private int intValueOfEndMonth;

    private String startDay;
    private String startMonth;
    private String endDay;
    private String endMonth;
    private JPanel[] waypointPanel;

    //Alp  ToDo
    //- Waypoint boxes on setup menu -> make easier for user interaction
    //- Vector information -> slimmer boxes (potentially held in a panel)
    public SetupMenu(SimulationSettings settings) throws IOException
    {
        this.U = settings.celestialBodies;
        this.settings = settings;

        mainFrame = new JFrame("Setup Menu");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1601,619);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());

        panelForBodies = new JPanel();
        panelForBodies.setLayout(new BoxLayout(panelForBodies,BoxLayout.LINE_AXIS));
        panelForWaypoints = new JPanel();
        panelForWaypoints.setLayout(new BoxLayout(panelForWaypoints,BoxLayout.Y_AXIS));
        rowValueForLayout = U.length+1;
        titles = new JLabel[21];
        titles[0] = new JLabel("Start Date");
        titles[1] = new JLabel("End Date");
        titles[2] = new JLabel("Start Point");
        titles[3] = new JLabel("Waypoint 1");
        titles[4] = new JLabel("Waypoint 2");
        titles[5] = new JLabel("End Point");
        titles[6] = new JLabel("Name");
        titles[7] = new JLabel("Velocity X");
        titles[8] = new JLabel("x10^");
        titles[9] = new JLabel("Velocity Y");
        titles[10] = new JLabel("x10^");
        titles[11] = new JLabel("Velocity Z");
        titles[12] = new JLabel("x10^");
        titles[13] = new JLabel("Location X");
        titles[14] = new JLabel("x10^");
        titles[15] = new JLabel("Location Y");
        titles[16] = new JLabel("x10^");
        titles[17] = new JLabel("Location Z");
        titles[18] = new JLabel("x10^");
        titles[19] = new JLabel("Mass");
        titles[20] = new JLabel("x10^");
        this.U = settings.celestialBodies;
        selectValuesOfPlanets = new boolean[U.length];
        fieldForMass =new JTextField[U.length];

        startTime = settings.startTime;
        endTime =settings.endTime;


        buttons = new JButton[5];
        arrayOfButtons = new JRadioButton[U.length];
        comboBoxesForDates = new JComboBox[6];

        fieldForPowerof10VelocityX = new JTextField[U.length];
        fieldForPowerof10VelocityY = new JTextField[U.length];
        fieldForPowerof10VelocityZ = new JTextField[U.length];

        fieldForPowerof10LocationX = new JTextField[U.length];
        fieldForPowerof10LocationY = new JTextField[U.length];
        fieldForPowerof10LocationZ = new JTextField[U.length];

        fieldForPowerof10Mass = new JTextField[U.length];

        comboBoxesForWaypoints=new JComboBox[4];
        generateBoxes(mainFrame);
        waypointPanel=new JPanel[4];
        startPointArray=new JRadioButton[U.length];
        wayPoint1Array=new JRadioButton[U.length];
        wayPoint2Array=new JRadioButton[U.length];
        endPointArray=new JRadioButton[U.length];

        for(int i=0;i< waypointPanel.length;i++){
            waypointPanel[i]=new JPanel();
            waypointPanel[i].setLayout(new BoxLayout(waypointPanel[i],BoxLayout.X_AXIS));
        }
        panelArray = new JPanel[10];

        for(int i=0;i< panelArray.length;i++){
            panelArray[i] = new JPanel();
            panelArray[i].setLayout(new GridLayout(U.length+1,0));
        }

        mainFrame.add(panelForWaypoints,BorderLayout.EAST);
        mainFrame.add(panelForBodies);
        newVelocities = new Vector3d[U.length];
        newLocations = new Vector3d[U.length];

        fieldForVelocityX =new JTextField[U.length];
        fieldForVelocityY =new JTextField[U.length];
        fieldForVelocityZ =new JTextField[U.length];

        fieldForLocationX =new JTextField[U.length];
        fieldForLocationY =new JTextField[U.length];
        fieldForLocationZ =new JTextField[U.length];

        generateTitles(panelForBodies);

        generateFields(panelForBodies);

        generateButtons(mainFrame);


        generateWaypointChooser();


        mainFrame.setVisible(true);
    }

    private void start()
    {
        for (int i = 0; i < U.length; i++) {
            settings.celestialBodies[i].mass =  Double.parseDouble(fieldForMass[i].getText());
            newVelocities[i] = new Vector3d(Double.parseDouble(fieldForVelocityX[i].getText()),  Double.parseDouble(fieldForVelocityY[i].getText()),  Double.parseDouble(fieldForVelocityZ[i].getText()));
            newLocations[i] = new Vector3d(Double.parseDouble(fieldForLocationX[i].getText()), Double.parseDouble(fieldForLocationY[i].getText()), Double.parseDouble(fieldForLocationZ[i].getText()));
            settings.celestialBodies[i].velocity = newVelocities[i];
            settings.celestialBodies[i].location = newLocations[i];
        }
        mainFrame.setVisible(false);
        for (int i = 0; i < U.length; i++) {
            selectValuesOfPlanets[i] = arrayOfButtons[i].isSelected();
        }
        for (int i = 0; i < selectValuesOfPlanets.length; i++) {
            if (selectValuesOfPlanets[i] = true) {
                for (int j = 0; j < amountOfBodies; j++) {
                    bodiesToReturn[j] = U[i];
                }
            }
        }
        for (CelestialBody celestialBody : U) {
            if (waypoints[0].equals(celestialBody.name)) {
                startPoint = celestialBody.location;
            }
            if (waypoints[3].equals(celestialBody.name)) {
                endPoint = celestialBody.location;
            }
        }
        waypointsToReturn = new String[waypoints.length];
        for(int i=0;i< waypointsToReturn.length;i++){
            waypointsToReturn[i]=waypoints[i];
        }
        inputComplete = true;

    }

    private void setWaypoints()
    {
        waypoints = new String[4];
        for(int i=0;i< waypoints.length;i++)
        {
            waypoints[i] = (String)comboBoxesForWaypoints[i].getSelectedItem();
        }
        buttons[0].setEnabled(true);

    }

    private void setDates()
    {
        intValueOfStartDay=(Integer)comboBoxesForDates[0].getSelectedItem();
        intValueOfStartMonth=(Integer)comboBoxesForDates[1].getSelectedItem();
        intValueOfEndDay=(Integer)comboBoxesForDates[3].getSelectedItem();
        intValueOfEndMonth=(Integer)comboBoxesForDates[4].getSelectedItem();
        if(intValueOfStartDay<10){
            startDay = "0"+Integer.toString((Integer) intValueOfStartDay);
        }
        else{
            startDay = Integer.toString((Integer) intValueOfStartDay);
        }
        if(intValueOfStartMonth<10){
            startMonth = "0"+Integer.toString((Integer) intValueOfStartMonth);
        }
        else{
            String startMonth = Integer.toString((Integer) intValueOfStartMonth);
        }
        if(intValueOfStartDay<10){
            endDay = "0"+Integer.toString((Integer) intValueOfEndDay);
        }
        else{
            endDay = Integer.toString((Integer) intValueOfEndDay);
        }
        if(intValueOfStartDay<10){
            endMonth = "0"+Integer.toString((Integer) intValueOfEndMonth);
        }
        else{
            endMonth = Integer.toString((Integer) intValueOfEndMonth);
        }
        String startYear = Integer.toString((Integer) comboBoxesForDates[2].getSelectedItem());
        String endYear = Integer.toString((Integer) comboBoxesForDates[5].getSelectedItem());
        LocalDate startDate = LocalDate.parse(startYear+"-"+startMonth+"-"+startDay);
        LocalTime time = LocalTime.parse("00:00:01");
        startTime = LocalDateTime.of(startDate, time);
        LocalDate date = LocalDate.parse(endYear+"-"+endMonth+"-"+endDay);
        endTime = LocalDateTime.of(date, time);
    }

    private void editVelocityLocationMass()
    {
        panelForBodies.setVisible(false);
        newPanel=new JPanel();
        buttons[4].setVisible(true);
        newPanel.setLayout(new GridLayout(0,15));
        panelArray = new JPanel[15];
        for(int i=0;i<panelArray.length;i++){
            panelArray[i] = new JPanel();
            panelArray[i].setLayout(new GridLayout(U.length+1,0));
        }
        for(int i=0;i< panelArray.length;i++)
        {

            panelArray[i].add(titles[i+6]);

        }
        for(int i=0;i<U.length;i++)
        {

            fieldForPowerof10VelocityX[i]=new JTextField("x 10^");
            fieldForPowerof10VelocityY[i]=new JTextField("x 10^");
            fieldForPowerof10VelocityZ[i]=new JTextField("x 10^");
            fieldForPowerof10LocationX[i]=new JTextField("x 10^");
            fieldForPowerof10LocationY[i]=new JTextField("x 10^");
            fieldForPowerof10LocationZ[i]=new JTextField("x 10^");
            fieldForPowerof10Mass[i]=new JTextField("x 10^");
            arrayOfButtons[i] = new JRadioButton(U[i].name);
            panelArray[0].add(arrayOfButtons[i]);
            fieldForVelocityX[i]= new JTextField(U[i].velocity.getX()+"");
            panelArray[1].add(fieldForVelocityX[i]);
            panelArray[2].add(fieldForPowerof10VelocityX[i]);
            fieldForVelocityY[i]= new JTextField(U[i].velocity.getY()+"");
            panelArray[3].add(fieldForVelocityY[i]);
            panelArray[4].add(fieldForPowerof10VelocityY[i]);
            fieldForVelocityZ[i]= new JTextField(U[i].velocity.getZ()+"");
            panelArray[5].add(fieldForVelocityZ[i]);
            panelArray[6].add(fieldForPowerof10VelocityZ[i]);
            fieldForLocationX[i] = new JTextField(U[i].location.getX()+"");
            panelArray[7].add(fieldForLocationX[i]);
            panelArray[8].add(fieldForPowerof10LocationX[i]);
            fieldForLocationY[i] = new JTextField(U[i].location.getY()+"");
            panelArray[9].add(fieldForLocationY[i]);
            panelArray[10].add(fieldForPowerof10LocationY[i]);
            fieldForLocationZ[i] = new JTextField(U[i].location.getZ()+"");
            panelArray[11].add(fieldForLocationZ[i]);
            panelArray[12].add(fieldForPowerof10LocationZ[i]);
            fieldForMass[i] = new JTextField(U[i].mass+"");
            panelArray[13].add(fieldForMass[i]);
            panelArray[14].add(fieldForPowerof10Mass[i]);
        }
        for (int i=0;i<panelArray.length;i++)
        {
            newPanel.add(panelArray[i]);
        }
        mainFrame.add(newPanel);
        buttons[4].setEnabled(true);
    }

    private void setValues()
    {


        for (int i = 0; i < U.length; i++) {
            U[i].mass = Math.pow(10, Double.parseDouble(fieldForPowerof10Mass[i].getText())) * Double.parseDouble(fieldForMass[i].getText());
            newVelocities[i] = new Vector3d(Math.pow(10, Double.parseDouble(fieldForPowerof10VelocityX[i].getText())) * Double.parseDouble(fieldForVelocityX[i].getText()), Math.pow(10, Double.parseDouble(fieldForPowerof10VelocityY[i].getText())) * Double.parseDouble(fieldForVelocityY[i].getText()), Math.pow(10, Double.parseDouble(fieldForPowerof10VelocityZ[i].getText())) * Double.parseDouble(fieldForVelocityZ[i].getText()));
            newLocations[i] = new Vector3d(Math.pow(10, Double.parseDouble(fieldForPowerof10LocationX[i].getText())) * Double.parseDouble(fieldForLocationX[i].getText()), Math.pow(10, Double.parseDouble(fieldForPowerof10LocationY[i].getText())) * Double.parseDouble(fieldForLocationY[i].getText()), Math.pow(10, Double.parseDouble(fieldForPowerof10LocationZ[i].getText())) * Double.parseDouble(fieldForLocationZ[i].getText()));
            U[i].velocity = newVelocities[i];
            U[i].location = newLocations[i];
        }

    }

    public SimulationSettings getSettings()
    {
        settings=new SimulationSettings(settings.celestialBodies,
                startPoint,
                settings.probeStartVelocity,
                startTime,
                endTime,
                settings.noOfSteps,
                settings.stepSize,
                waypointsToReturn);
        return settings;
    }

    public boolean inputComplete()
    {
        return inputComplete;
    }

    private void generateTitles(JPanel panel)
    {

        panelArray[0].add(titles[6]);
        panelArray[1].add(titles[7]);
        panelArray[2].add(titles[9]);
        panelArray[3].add(titles[11]);
        panelArray[4].add(titles[13]);
        panelArray[5].add(titles[15]);
        panelArray[6].add(titles[17]);
        panelArray[7].add(titles[19]);
        for(int i=0;i< panelArray.length;i++){
            panel.add(panelArray[i]);
        }
    }

    private void generateFields(JPanel panel)
    {
        for(int i=0;i<U.length;i++){
            fieldForPowerof10VelocityX[i]=new JTextField("x 10^");
            fieldForPowerof10VelocityY[i]=new JTextField("x 10^");
            fieldForPowerof10VelocityZ[i]=new JTextField("x 10^");
            fieldForPowerof10LocationX[i]=new JTextField("x 10^");
            fieldForPowerof10LocationY[i]=new JTextField("x 10^");
            fieldForPowerof10LocationZ[i]=new JTextField("x 10^");
            fieldForPowerof10Mass[i]=new JTextField("x 10^");
            arrayOfButtons[i] = new JRadioButton(U[i].name);
            panelArray[0].add(arrayOfButtons[i]);
            fieldForVelocityX[i]= new JTextField(U[i].velocity.getX()+"");
            panelArray[1].add(fieldForVelocityX[i]);
            fieldForVelocityY[i]= new JTextField(U[i].velocity.getY()+"");
            panelArray[2].add(fieldForVelocityY[i]);
            fieldForVelocityZ[i]= new JTextField(U[i].velocity.getZ()+"");
            panelArray[3].add(fieldForVelocityZ[i]);
            fieldForLocationX[i] = new JTextField(U[i].location.getX()+"");
            panelArray[4].add(fieldForLocationX[i]);
            fieldForLocationY[i] = new JTextField(U[i].location.getY()+"");
            panelArray[5].add(fieldForLocationY[i]);
            fieldForLocationZ[i] = new JTextField(U[i].location.getZ()+"");
            panelArray[6].add(fieldForLocationZ[i]);
            fieldForMass[i] = new JTextField(U[i].mass+"");
            panelArray[7].add(fieldForMass[i]);
        }
        for (JPanel jPanel : panelArray) {
            panel.add(jPanel);
        }
    }

    private void generateBoxes(JFrame frame)
    {
        for(int i=0;i<comboBoxesForDates.length;i++){
            comboBoxesForDates[i]=new JComboBox();
        }
        for(int i = 0; i<comboBoxesForDates.length; i=i+3){
            for(int j = 0; j<32; j++){
                comboBoxesForDates[0].setSelectedItem(startTime.getDayOfMonth());
                comboBoxesForDates[3].setSelectedItem(endTime.getDayOfMonth());
                comboBoxesForDates[i].addItem(j);
            }
        }
        for(int i=1;i<5;i=i+3){
            for(int j = 0; j<13; j++){
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
        panelForDates = new JPanel();
        panelForDates.setLayout(new BoxLayout(panelForDates,BoxLayout.X_AXIS));
        panelForDates.add(titles[0]);

        for(int i=0;i<3;i++){
            panelForDates.add(comboBoxesForDates[i]);
        }

        panelForDates.add(titles[1]);
        for(int i=3;i<6;i++){
            panelForDates.add(comboBoxesForDates[i]);
        }
        frame.add(panelForDates,BorderLayout.NORTH);
    }

    private void generateButtons(JFrame frame)
    {
        button = new JPanel();
        button.setLayout(new GridLayout(0,5));
        buttons[0] = new JButton("Start Universe");
        buttons[0].setEnabled(false);
        buttons[0].addActionListener(e -> start());

        buttons[1] = new JButton("Set Waypoints");
        buttons[1].addActionListener(e -> setWaypoints());

        buttons[2] = new JButton("Set Dates");
        buttons[2].addActionListener(e-> setDates());

        buttons[3] = new JButton("Change Values");
        buttons[3].addActionListener(e-> editVelocityLocationMass());

        buttons[4] = new JButton("Set Values");
        buttons[4].addActionListener(e-> setValues());
        buttons[4].setEnabled(false);


        for (JButton jButton : buttons) {
            button.add(jButton);
        }
        frame.add(button,BorderLayout.SOUTH);
    }

    private void generateWaypointChooser()
    {
        for(int i=0;i< comboBoxesForWaypoints.length;i++)
        {
            comboBoxesForWaypoints[i]=new JComboBox<String>();
            for(int j=0;j<U.length;j++){
                comboBoxesForWaypoints[i].addItem(U[j].name);
            }
        }

        waypointPanel[0].add(titles[2]);
        waypointPanel[1].add(titles[3]);
        waypointPanel[2].add(titles[4]);
        waypointPanel[3].add(titles[5]);
        waypointPanel[0].add(comboBoxesForWaypoints[0]);
        waypointPanel[1].add(comboBoxesForWaypoints[1]);
        waypointPanel[2].add(comboBoxesForWaypoints[2]);
        waypointPanel[3].add(comboBoxesForWaypoints[3]);

        for(int i=0;i< waypointPanel.length;i++){
            panelForWaypoints.add(waypointPanel[i]);
        }
    }
}
