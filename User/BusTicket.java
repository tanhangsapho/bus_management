package Project_Final.User;

import Project_Final.Admin.AdminSwitching;
import Project_Final.Admin.Reporting;
import Project_Final.MySqlService.MySqlProvider;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class BusTicket extends JFrame {
    Font font=new Font("Arial", Font.PLAIN,16);
    protected JComboBox combo1,combo2;
    protected JDateChooser jdatepicker;
    Connection con= MySqlProvider.MysqlService();
    Statement statement=null;
    protected JButton btnAdd;
    final  int PANEL_WIDTH = 600;
    final  int PANEL_HEIGHT = 600;

    public BusTicket(String username,String phone)
    {
        super("Book Ticket");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(600,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        DisplayUI(username,phone);

    }
    public BusTicket(){
        super("Book Ticket");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(600,450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        DisplayUIS();
    }
    public void hidden()
    {
        setVisible(false);
    }
    public void display()
    {
        setVisible(true);
    }
   public void DisplayUI(String username,String phone) {
       String Distination[] = {
               "Select the Destination"
               ,"Kampot"
               , "Phnom Penh"
               , "Siem Reap"
               , "Preah Sihanouk"
               ,"Kirirom"
               };
       String origin[] = {
               "Select the Origin Place"
               ,"Kampot"
               , "Phnom Penh"
               , "Siem Reap"
               , "Preah Sihanouk"
               ,"Kirirom"
   };
       JLabel lblOrigin = new JLabel("Origin Place");
       lblOrigin.setBounds(20,30,140,30);
       lblOrigin.setFont(font);
       add(lblOrigin);

       combo2 = new JComboBox(origin);
       combo2.setBounds(150,30,300,30);
       combo2.setFont(font);
       add(combo2);

       JLabel lblDestination = new JLabel("Destination");
       lblDestination.setBounds(20, 80, 80, 30);
       lblDestination.setFont(font);
       add(lblDestination);
       combo1 = new JComboBox(Distination);
       combo1.setBounds(150, 80, 300, 30);
       combo1.setFont(font);
       add(combo1);

       JLabel lblDate = new JLabel("Date of Journey");
       lblDate.setBounds(20,130,140,30);
       lblDate.setFont(font);
       add(lblDate);

       jdatepicker = new JDateChooser();
       jdatepicker.setBounds(150,130,300,30);
       jdatepicker.setFont(font);
       jdatepicker.setDate(new Date());
       Date nowDate=new Date();
       String date = (1900+nowDate.getYear())+"-"+(1+nowDate.getMonth())+"-"+(6+nowDate.getDate());
       Date availableDate= null;
       try {
           availableDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
       } catch (ParseException e) {
           e.printStackTrace();
       }
       System.out.println(new Date());
       jdatepicker.setSelectableDateRange(new Date(),availableDate);
       add(jdatepicker);
       jdatepicker.getCalendarButton();
       String time[] =new String[24];
       for (int i=0; i<12; i++){
               time[i] = (1+i)+":00AM";
       }
       int j=1;
       for (int i=12; i<24; i++){
           time[i] = j+":00PM";
           j++;
       }
       JLabel lblDeparture = new JLabel("Departure");
       lblDeparture.setBounds(20,180,140,30);
       lblDeparture.setFont(font);
       add(lblDeparture);

       JComboBox comboBox = new JComboBox( time);
       comboBox.setBounds(150,180,300,30);
       comboBox.setFont(font);
       add(comboBox);

        //new update
       JButton btnChoose=new JButton("Click here to choose your seet");
       btnChoose.setBounds(150,240,300,30);
       btnChoose.setFont(font);
        add(btnChoose);
        //action
       btnChoose.addActionListener(e->{
           String from= (String) combo2.getSelectedItem();
           String to= (String) combo1.getSelectedItem();
           Date dt= jdatepicker.getDate();
           String tm=(String) comboBox.getSelectedItem();
           String mt=tm;
           tm=tm.replace(":00","");
           assert from != null;
           if(from.equalsIgnoreCase(to)){
                JOptionPane.showMessageDialog(null,"Please choose different position","Position",JOptionPane.ERROR_MESSAGE);
            }else{
               try {
                   statement=con.createStatement();
                   String command="SELECT * FROM chairinfo"+(dt.getDay())+" WHERE `from`='"+from+"' AND "+"`to`='"+to+"' AND time_start='"+tm+"' AND Status='free'";
                   System.out.println(command);
                   ResultSet result=statement.executeQuery(command);
                   ///////////////////////
                   HashMap<String,Integer[]> map=new HashMap<String,Integer[]>();
                   HashMap<String,String> mapName=new HashMap<String,String>();
                   HashMap<String,String> mapPhone=new HashMap<String, String>();
                   HashMap<String,String> mapPlate=new HashMap<String, String>();
                   ArrayList<String> mapBus=new ArrayList<String>();

                   while(result.next()){
                       Integer[] listChairs=new Integer[49];
                       mapName.put("Bus("+result.getString(1)+")",result.getString(2));
                       mapPhone.put("Bus("+result.getString(1)+")",result.getString(3));
                       mapPlate.put("Bus("+result.getString(1)+")",result.getString(1));
                       mapBus.add("Bus("+result.getString(1)+")");

                       for(int i=0;i<49;i++){
                           listChairs[i]=(result.getInt(i+7));
                       }
                       map.put("Bus("+result.getString(1)+")", listChairs);
                   }

                   /////////////////

                   ChooseSeet cs=new ChooseSeet(username,phone,from,to,dt,mt,2,map,mapName,mapPhone,mapPlate,mapBus);
                   this.hide();
               } catch (SQLException ex) {
                   ex.printStackTrace();
               }
           }

       });
   }
    public void DisplayUIS() {
        JLabel lblBack = new JLabel("Back");
        lblBack.setFont(font);
        lblBack.setBounds(5,2,70,30);
        add(lblBack);
        lblBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                hidden();
                new AdminSwitching();
            }
        });
        String Distination[] = {
                "Select the Destination"
                ,"Kampot"
                , "Phnom Penh"
                , "Siem Reap"
                , "Preah Sihanouk"
                ,"Kirirom"
        };
        String origin[] = {
                "Select the Origin Place"
                ,"Kampot"
                , "Phnom Penh"
                , "Siem Reap"
                , "Preah Sihanouk"
                ,"Kirirom"
        };
        //username
            JLabel lblUsername = new JLabel("Username");
            lblUsername.setBounds(20,30,100,30);
            lblUsername.setFont(font);
            JTextField jtfusername=new JTextField();
            jtfusername.setBounds(150,30,300,30);
            jtfusername.setFont(font);
            add(jtfusername);
            add(lblUsername);
        //phone number
            JTextField jtfPhone=new JTextField();
            JLabel lblPhone=new JLabel("Phone");
            lblPhone.setBounds(20,80,100,30);
            jtfPhone.setBounds(150,80,300,30);
            lblPhone.setFont(font);
            jtfPhone.setFont(font);
            add(jtfPhone);
            add(lblPhone);
        JLabel lblOrigin = new JLabel("Origin Place");
        lblOrigin.setBounds(20,130,120,30);
        lblOrigin.setFont(font);
        add(lblOrigin);

        combo2 = new JComboBox(origin);
        combo2.setBounds(150,130,300,30);
        combo2.setFont(font);
        add(combo2);

        JLabel lblDestination = new JLabel("Destination");
        lblDestination.setBounds(20, 180, 80, 30);
        lblDestination.setFont(font);
        add(lblDestination);
        combo1 = new JComboBox(Distination);
        combo1.setBounds(150, 180, 300, 30);
        combo1.setFont(font);
        add(combo1);

        JLabel lblDate = new JLabel("Date of Journey");
        lblDate.setBounds(20,230,140,30);
        lblDate.setFont(font);
        add(lblDate);

        jdatepicker = new JDateChooser();
        jdatepicker.setFont(font);
        jdatepicker.setBounds(150,230,300,30);
        jdatepicker.setDate(new Date());
        Date nowDate=new Date();
        String date = (1900+nowDate.getYear())+"-"+(1+nowDate.getMonth())+"-"+(6+nowDate.getDate());
        Date availableDate= null;
        try {
            availableDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(new Date());
        jdatepicker.setSelectableDateRange(new Date(),availableDate);
        add(jdatepicker);
        jdatepicker.getCalendarButton();
        String time[] =new String[24];
        for (int i=0; i<12; i++){
            time[i] = (1+i)+":00AM";
        }
        int j=1;
        for (int i=12; i<24; i++){
            time[i] = j+":00PM";
            j++;
        }
        JLabel lblDeparture = new JLabel("Departure");
        lblDeparture.setFont(font);
        lblDeparture.setBounds(20,280,120,30);
        add(lblDeparture);

        JComboBox comboBox = new JComboBox( time);
        comboBox.setFont(font);
        comboBox.setBounds(150,280,300,30);
        add(comboBox);

        //new update
        JButton btnChoose=new JButton("Click here to choose your seet");
        btnChoose.setBounds(150,340,300,30);
        add(btnChoose);
        //action
        btnChoose.addActionListener(e->{
            String from= (String) combo2.getSelectedItem();
            String to= (String) combo1.getSelectedItem();
            Date dt= jdatepicker.getDate();
            String tm=(String) comboBox.getSelectedItem();
            String mt=tm;
            tm=tm.replace(":00","");
            assert from != null;
            if(from.equalsIgnoreCase(to)){
                JOptionPane.showMessageDialog(null,"Please choose different position","Position",JOptionPane.ERROR_MESSAGE);
            }else{
                try {
                    statement=con.createStatement();
                    String command="SELECT * FROM chairinfo"+(dt.getDay())+" WHERE `from`='"+from+"' AND "+"`to`='"+to+"' AND time_start='"+tm+"' AND Status='free'";
                    System.out.println(command);
                    ResultSet result=statement.executeQuery(command);
                    ///////////////////////
                    HashMap<String,Integer[]> map=new HashMap<String,Integer[]>();
                    HashMap<String,String> mapName=new HashMap<String,String>();
                    HashMap<String,String> mapPhone=new HashMap<String, String>();
                    HashMap<String,String> mapPlate=new HashMap<String, String>();
                    ArrayList<String> mapBus=new ArrayList<String>();

                    while(result.next()){
                        Integer[] listChairs=new Integer[49];
                        mapName.put("Bus("+result.getString(1)+")",result.getString(2));
                        mapPhone.put("Bus("+result.getString(1)+")",result.getString(3));
                        mapPlate.put("Bus("+result.getString(1)+")",result.getString(1));
                        mapBus.add("Bus("+result.getString(1)+")");

                        for(int i=0;i<49;i++){
                            listChairs[i]=(result.getInt(i+7));
                        }
                        map.put("Bus("+result.getString(1)+")", listChairs);
                    }

                    /////////////////

                    ChooseSeet cs=new ChooseSeet(jtfusername.getText(),jtfPhone.getText(),from,to,dt,mt,1,map,mapName,mapPhone,mapPlate,mapBus);
                    this.hide();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });
}
}
