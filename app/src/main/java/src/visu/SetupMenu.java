package src.visu;
import src.conf.SimulationSettings;
import src.univ.CelestialBody;
import javax.swing.*;
import java.awt.*;
public class SetupMenu {
    JFrame mainFrame;
    CelestialBody[] U;
    int rowValueForLayout;
    JPanel mainPanel;
    JPanel panelForDates;
    JPanel button;
    JPanel panelForTitles;
    JPanel[] panelArray;
    JLabel[] titles=new JLabel[6];
    JComboBox[] comboBoxesForDates = new JComboBox[6];
    JComboBox<String>[] comboBoxesForWaypoints = new JComboBox[4];
    JButton[] buttons = new JButton[4];
    JRadioButton[] arrayOfButtons;
    String[] waypoints;
    boolean[] selectValuesOfPlanets;
    SimulationSettings settings;
    public SetupMenu(CelestialBody[]U, SimulationSettings settings){
        mainFrame=new JFrame("Setup Menu");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1050,550);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());
        mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(0,4));
        mainFrame.add(mainPanel,BorderLayout.CENTER);
        rowValueForLayout = U.length+1;
        this.U = U;
        CelestialBody[] firstU = new CelestialBody[0];
        this.settings = settings;
        arrayOfButtons = new JRadioButton[U.length];
        //Combo box
        for(int i=0;i<comboBoxesForDates.length;i=i+3){
            comboBoxesForDates[i]=new JComboBox();
            for(int j=0;j<32;j++){
                comboBoxesForDates[i].addItem(j);
            }
        }
        for(int i=1;i<5;i=i+3){
            comboBoxesForDates[i]=new JComboBox();
            for(int j=0;j<13;j++){
                comboBoxesForDates[i].addItem(j);
            }
        }
        for(int i=2;i<6;i=i+3){
            comboBoxesForDates[i]=new JComboBox();
            for(int j=1990;j<2060;j++){
                comboBoxesForDates[i].addItem(j);
            }
        }
        CelestialBody[] uploadedU = new CelestialBody[settings.celestialBodies.length];
        System.arraycopy(settings.celestialBodies, 0, U, 0, U.length);
        titles[0] = new JLabel("Start Date");
        titles[1] = new JLabel("End Date");
        //Date
        panelForDates = new JPanel();
        panelForDates.setLayout(new GridLayout(0,8));
        panelForDates.add(titles[0]);
        for(int i=0;i<3;i++){
            panelForDates.add(comboBoxesForDates[i]);
        }
        panelForDates.add(titles[1]);
        for(int i=3;i<6;i++){
            panelForDates.add(comboBoxesForDates[i]);
        }
        panelArray=new JPanel[4];
        for(int i=0;i<panelArray.length;i++){
            panelArray[i] = new JPanel();
            panelArray[i].setLayout(new GridLayout(rowValueForLayout,0));
        }
        mainFrame.add(panelForDates,BorderLayout.NORTH);
        //Title
        panelForTitles = new JPanel();
        panelForTitles.setLayout(new GridLayout(0,4));
        titles[2] = new JLabel("Name");
        titles[3] = new JLabel("Mass");
        titles[4] = new JLabel("Velocity");
        titles[5] = new JLabel("Location");
        panelArray[0].add(titles[2]);
        panelArray[3].add(titles[3]);
        panelArray[2].add(titles[4]);
        panelArray[1].add(titles[5]);
        for(int i=0;i<U.length;i++){
            arrayOfButtons[i] = new JRadioButton(U[i].name);
            panelArray[0].add(arrayOfButtons[i]);
            JLabel temp = new JLabel(U[i].velocity+"");
            panelArray[1].add(temp);
            temp = new JLabel(U[i].location+"");
            panelArray[2].add(temp);
            temp = new JLabel(U[i].mass+"");
            panelArray[3].add(temp);
        }
        //Button
        button = new JPanel();
        button.setLayout(new GridLayout(0,4));
        buttons[0]=new JButton("Reset");
        buttons[0].addActionListener(e -> {
            mainFrame.setVisible(false);
            new SetupMenu(firstU,settings);
        });
        buttons[1] = new JButton("Start Universe");
        buttons[1].addActionListener(e -> {
            selectValuesOfPlanets=new boolean[U.length];
            for(int i=0;i<U.length;i++){
                selectValuesOfPlanets[i] = arrayOfButtons[i].isSelected();
            }
        });
        buttons[2] = new JButton("Upload from file");
        buttons[2].addActionListener(e -> {
            new SetupMenu(uploadedU,settings);
            mainFrame.setVisible(false);
        });
        buttons[3]=new JButton("Set Probe");
        buttons[3].addActionListener(e -> {
            JFrame frameForProbe=new JFrame("Set Probe");
            frameForProbe.setLayout(new GridLayout(5,0));
            frameForProbe.setSize(350,350);
            frameForProbe.setLocationRelativeTo(null);
            frameForProbe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //Waypoint 0-> start point
            //Waypoint 1-> waypoint 1
            //Waypoint 2-> waypoint 2
            //Waypoint 3-> end point
            for(int i=0;i< comboBoxesForWaypoints.length;i++){
                comboBoxesForWaypoints[i]=new JComboBox();
                for (CelestialBody celestialBody : U) {
                    comboBoxesForWaypoints[i].addItem(celestialBody.name);
                }
            }
            JButton set=new JButton("Set");
            for (JComboBox<String> comboBoxesForWaypoint : comboBoxesForWaypoints) {
                frameForProbe.add(comboBoxesForWaypoint);
            }
            frameForProbe.add(set);
            set.addActionListener(e1 -> {
                waypoints=new String[4];
                for(int i=0;i< waypoints.length;i++){
                    waypoints[i]=(String)comboBoxesForWaypoints[i].getSelectedItem();
                }
                frameForProbe.setVisible(false);
            });
            frameForProbe.setVisible(true);
        });
        for (JButton jButton : buttons) {
            button.add(jButton);
        }
        mainFrame.add(button,BorderLayout.SOUTH);
        for (JPanel jPanel : panelArray) {
            mainPanel.add(jPanel);
        }
        mainFrame.setVisible(true);
    }
}
