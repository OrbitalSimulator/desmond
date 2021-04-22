package src.visu;
import src.univ.CelestialBody;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SetupMenu {
    //Frames
    JFrame mainFrame;
    JFrame addNewBody;
    CelestialBody[] U;
    CelestialBody[] firstU;
    //Panels
    int rowValueForLayout;
    JPanel mainPanel;
    JPanel panelForDates;
    JPanel button;
    JPanel panelForTitles;
    JPanel column1;
    JPanel column2;
    JPanel column3;
    JPanel column4;
    //Label for more title.
    JLabel labelForStart;
    JLabel labelForEnd;
    JLabel labelForNameTitle;
    JLabel labelForLocationTitle;
    JLabel labelForVelocityTitle;
    JLabel labelForMassTitle;
    //Font
    Font font = new Font("SansSerif", Font.ITALIC, 15);
    //Combo box for date picker.
    JComboBox boxForStartDay;
    JComboBox boxForStartMonth;
    JComboBox boxForStartYear;
    JComboBox boxForEndDay;
    JComboBox boxForEndMonth;
    JComboBox boxForEndYear;
    JComboBox startPoint =new JComboBox();
    JComboBox endPoint =new JComboBox();
    JComboBox wayPoint1=new JComboBox();
    JComboBox wayPoint2=new JComboBox();
    //Buttons
    JButton add;
    JButton reset;
    JButton startUniverse;
    JButton uploadFile;
    JButton deleteSelected;
    JButton setProbe;
    JRadioButton[] arrayOfButtons;

    JTextField nameOfNewBody;
    JTextField XLocationOfNewBody;
    JTextField YLocationOfNewBody;
    JTextField ZLocationOfNewBody;
    JTextField XVelocityOfNewBody;
    JTextField YVelocityOfNewBody;
    JTextField ZVelocityOfNewBody;
    JTextField MassOfNewBody;




    boolean[] selectValuesOfPlanets;

    
    public SetupMenu(CelestialBody[]U){
        mainFrame=new JFrame("Setup Menu");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1050,550);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());
        mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(0,4));
        mainFrame.add(mainPanel,BorderLayout.CENTER);
        rowValueForLayout=U.length+1;
        this.U=U;
        this.firstU=U;
        arrayOfButtons=new JRadioButton[U.length];
        //Combo box
        boxForStartDay=new JComboBox();
        boxForStartMonth=new JComboBox();
        boxForStartYear=new JComboBox();
        for(int i=0;i<32;i++){
            boxForStartDay.addItem(i);
        }
        for(int i=0;i<13;i++){
            boxForStartMonth.addItem(i);
        }
        for(int i=2000;i<2051;i++){
            boxForStartYear.addItem(i);
        }
        boxForEndDay=new JComboBox();
        boxForEndMonth=new JComboBox();
        boxForEndYear=new JComboBox();
        for(int i=0;i<32;i++){
            boxForEndDay.addItem(i);
        }
        for(int i=0;i<13;i++){
            boxForEndMonth.addItem(i);
        }
        for(int i=2000;i<2351;i++){
            boxForEndYear.addItem(i);
        }
        labelForStart=new JLabel("Start Date");
        labelForStart.setFont(font);

        labelForEnd=new JLabel("End Date");
        labelForEnd.setFont(font);
        //Date
        panelForDates=new JPanel();
        panelForDates.setLayout(new GridLayout(0,8));
        panelForDates.add(labelForStart);
        panelForDates.add(boxForStartDay);
        panelForDates.add(boxForStartMonth);
        panelForDates.add(boxForStartYear);
        panelForDates.add(labelForEnd);
        panelForDates.add(boxForEndDay);
        panelForDates.add(boxForEndMonth);
        panelForDates.add(boxForEndYear);
        mainFrame.add(panelForDates,BorderLayout.NORTH);

        //Title
        panelForTitles=new JPanel();
        panelForTitles.setLayout(new GridLayout(0,4));
        labelForNameTitle=new JLabel("Name");
        labelForMassTitle=new JLabel("Mass");
        labelForVelocityTitle=new JLabel("Velocity");
        labelForLocationTitle=new JLabel("Location");
        column1=new JPanel();
        column1.setLayout(new GridLayout(rowValueForLayout,0));
        column2=new JPanel();
        column2.setLayout(new GridLayout(rowValueForLayout,0));
        column3=new JPanel();
        column3.setLayout(new GridLayout(rowValueForLayout,0));
        column4=new JPanel();
        column4.setLayout(new GridLayout(rowValueForLayout,0));

        column1.add(labelForNameTitle);
        column4.add(labelForMassTitle);
        column3.add(labelForVelocityTitle);
        column2.add(labelForLocationTitle);



        for(int i=0;i<U.length;i++){
            arrayOfButtons[i]= new JRadioButton(U[i].name);
            column1.add(arrayOfButtons[i]);
            JLabel temp=new JLabel(U[i].velocity+"");
            column2.add(temp);
            temp=new JLabel(U[i].location+"");
            column3.add(temp);
            temp=new JLabel(U[i].mass+"");
            column4.add(temp);
        }

        //Button
        button=new JPanel();
        button.setLayout(new GridLayout(0,6));
        add=new JButton("Add new body");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rowValueForLayout++;
                column1.setLayout(new GridLayout(rowValueForLayout,0));
                column2.setLayout(new GridLayout(rowValueForLayout,0));
                column3.setLayout(new GridLayout(rowValueForLayout,0));
                column4.setLayout(new GridLayout(rowValueForLayout,0));
                addNewBody=new JFrame("Add New Body");
                addNewBody.setSize(550,150);
                addNewBody.setLayout(new BorderLayout());
                JPanel panelForFields=new JPanel();
                panelForFields.setLayout(new GridLayout(2,4));
                nameOfNewBody=new JTextField("Name of body");
                XLocationOfNewBody=new JTextField("X location of body");
                YLocationOfNewBody=new JTextField("Y location of body");
                ZLocationOfNewBody=new JTextField("Z location of body");
                XVelocityOfNewBody=new JTextField("X velocity of body");
                YVelocityOfNewBody=new JTextField("Y velocity of body");
                ZVelocityOfNewBody=new JTextField("Z velocity of body");
                MassOfNewBody=new JTextField("Mass of body");

                JButton setNewBody=new JButton("Set");


                setNewBody.addActionListener(new ActionListener() {
                    @Override

                    public void actionPerformed(ActionEvent e) {
                        int step=2;
                        JLabel nameOfNewBodyString= new JLabel(nameOfNewBody.getText());
                        JLabel LocationOfNewBodyString= new JLabel(XLocationOfNewBody.getText()+YLocationOfNewBody+ZLocationOfNewBody);
                        JLabel VelocityOfNewBodyString= new JLabel(XVelocityOfNewBody.getText()+YVelocityOfNewBody.getText()+ZVelocityOfNewBody.getText());
                        JLabel MassOfNewBodyString= new JLabel(MassOfNewBody.getText());
                        selectValuesOfPlanets=new boolean[U.length+1];


                        arrayOfButtons[U.length-1]= new JRadioButton(nameOfNewBody.getText());



                        column1.add(arrayOfButtons[U.length-1]);
                        column2.add(LocationOfNewBodyString);
                        column3.add(VelocityOfNewBodyString);
                        column4.add(MassOfNewBodyString);


                        addNewBody.setVisible(false);
                        mainFrame.setVisible(true);

                    }
                });
                addNewBody.add(setNewBody,BorderLayout.SOUTH);
                panelForFields.add(nameOfNewBody);
                panelForFields.add(XLocationOfNewBody);
                panelForFields.add(YLocationOfNewBody);
                panelForFields.add(ZLocationOfNewBody);
                panelForFields.add(XVelocityOfNewBody);
                panelForFields.add(YVelocityOfNewBody);
                panelForFields.add(ZVelocityOfNewBody);
                panelForFields.add(MassOfNewBody);
                addNewBody.add(panelForFields);
                addNewBody.setLocationRelativeTo(null);
                addNewBody.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                addNewBody.setVisible(true);
            }
        });
        reset=new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for(int i=0;i<arrayOfButtons.length;i++){
                    arrayOfButtons[i].setSelected(false);

                }

                boxForStartDay.setSelectedIndex(0);
                boxForStartMonth.setSelectedIndex(0);
                boxForStartYear.setSelectedIndex(0);
                boxForEndDay.setSelectedIndex(0);
                boxForEndMonth.setSelectedIndex(0);
                boxForEndYear.setSelectedIndex(0);
                mainFrame.setVisible(false);
                new SetupMenu(firstU);
            }
        });
        startUniverse =new JButton("Start Universe");
        startUniverse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectValuesOfPlanets=new boolean[U.length];
                for(int i=0;i<U.length;i++){
                    selectValuesOfPlanets[i]=arrayOfButtons[i].isSelected();

                }


            }
        });
        uploadFile=new JButton("Select from file");
        uploadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //ToDo
            }
        });
        deleteSelected=new JButton("Delete selected file");
        deleteSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //ToDo
            }
        });
        setProbe=new JButton("Set Probe");
        setProbe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameForProbe=new JFrame("Set Probe");
                frameForProbe.setLayout(new GridLayout(5,0));
                frameForProbe.setSize(350,350);
                frameForProbe.setLocationRelativeTo(null);
                frameForProbe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                for(int i=0;i<U.length;i++){
                    startPoint.addItem(U[i].name);
                    endPoint.addItem(U[i].name);
                    wayPoint1.addItem(U[i].name);
                    wayPoint2.addItem(U[i].name);
                }


                JButton set=new JButton("Set");
                frameForProbe.add(startPoint);
                frameForProbe.add(wayPoint1);
                frameForProbe.add(wayPoint2);
                frameForProbe.add(endPoint);
                frameForProbe.add(set);
                set.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frameForProbe.setVisible(false);
                    }
                });

                frameForProbe.setVisible(true);

            }
        });

        button.add(add);
        button.add(reset);
        button.add(startUniverse);
        button.add(uploadFile);
        button.add(deleteSelected);
        button.add(setProbe);
        mainFrame.add(button,BorderLayout.SOUTH);
        mainPanel.add(column1);
        mainPanel.add(column2);
        mainPanel.add(column3);
        mainPanel.add(column4);

        mainFrame.setVisible(true);

    }
    public void run(){
        new SetupMenu(U);
    }
}