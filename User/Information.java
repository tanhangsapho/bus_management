package Project_Final.User;

import Project_Final.MySqlService.MySqlProvider;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Information extends JFrame{


    Font font=new Font("Arial",Font.PLAIN,15);
    Connection con= MySqlProvider.MysqlService();
    protected  JButton btnBuy;
    public Information(String username, String phone, String from, String to, Date dt, String tm, String driverName, String driverPhone, String busPlate, String seat, double price, ArrayList<String> list, int option, HashMap<String,Integer[]> map, HashMap<String,String> mapName, HashMap<String,String> mapPhone, HashMap<String,String> mapPlate, ArrayList<String> mapBus) throws SQLException {
        super("Driver Infomation");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setSize(800,550);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //back
        JLabel btnBack = new JLabel("Back");
        btnBack.setBounds(730,5,60,20);
        btnBack.setBackground(Color.white);
        btnBack.setFont(font);
        add(btnBack);
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    setVisible(false);
                    //username,phone,result,from, to, dt,tm,option,map,mapName,mapPhone,mapPlate,mapBus
                    new ChooseSeet(username,phone,from,to,dt,tm,option,map,mapName,mapPhone,mapPlate,mapBus);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                super.mouseClicked(e);
            }
        });
        //checkout
        Button btnPayment=new Button("Checkout");
        btnPayment.setBounds(320,180,135,30);
        btnPayment.setFont(new Font("Arial", Font.BOLD,20));
        btnPayment.setBackground(Color.BLACK);
        btnPayment.setForeground(Color.WHITE);
        add(btnPayment);
        btnPayment.addActionListener(e->{
            list.forEach(data->{
                try {
                    Statement statement=con.createStatement();
                    String commandUpdateSeat="UPDATE chairinfo"+dt.getDay()+" SET chair"+data+"=1 WHERE plate='"+busPlate+"';";
                    //System.out.println(commandUpdateSeat);
                   statement.executeUpdate(commandUpdateSeat);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            try {
                Statement statement=con.createStatement();
                SimpleDateFormat dcn = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                String dates = dcn.format(dt);

                String commandInsertData="INSERT INTO `datatabel` (`name`, `phone`, `from`, `to`, `date`, `time`, `seat`, `total`,`bus_plate`) VALUES ('"+username+"', '"+phone+"', '"+from+"', '"+to+"', '"+dates+"', '"+tm+"', '"+seat+"', '"+price+"','"+busPlate+"')";
                statement.executeUpdate(commandInsertData);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,"Booking successfully","Checkout",JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            if(option==1){
                new BusTicket().display();
            }else{
                new BusTicket(username,phone).display();
            }

        });

        userUI(username,phone,seat,price);
        driverUI(driverName,driverPhone,busPlate,from,to,dt,tm);
        setVisible(true);
    }

    public void hidden()
    {
        setVisible(false);
    }
    public void display()
    {
        setVisible(true);
    }
    private void userUI(String username,String phone,String seat,double price){
        JPanel panel=new JPanel();

        JLabel lblName=new JLabel("Name : "+username);
        JLabel lblPhone=new JLabel("Phone : "+phone);
        JLabel lblSeat=new JLabel("Your seat : "+seat);
        JLabel lblPrice=new JLabel("Total : "+price+"$");

        panel.setBounds(0,0,800,250);
        panel.setLayout(null);
        panel.setBackground(Color.cyan);
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/Project_Final/Image/man.png").getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(imageIcon);
        label.setBounds(20,20,100,100);
        //lblName
        lblName.setBounds(200,30,200,30);
        lblName.setFont(font);
        panel.add(lblName);
        //lblPhone
        lblPhone.setBounds(200,60,200,30);
        lblPhone.setFont(font);
        panel.add(lblPhone);
        //lblSeat
        lblSeat.setBounds(200,90,500,30);
        lblSeat.setFont(font);
        panel.add(lblSeat);
        //lblPrice
        lblPrice.setBounds(200,120,200,30);
        lblPrice.setFont(font);
        panel.add(lblPrice);

        panel.add(label);
        add(panel);
    }
    private void driverUI(String driverName,String driverPhone,String busPlate,String from,String to,Date date,String time)
    {
        JPanel panel = new JPanel();
        panel.setBounds(0,250,800,265);
        panel.setLayout(null);
        panel.setBackground(Color.orange);
        final String dir = System.getProperty("user.dir");
        System.out.println(dir);

        ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/Project_Final/Image/Bus_driver.jpg").getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(imageIcon);
        label.setBounds(20,50,150,150);
        panel.add(label);



        ImageIcon imageSeat = new ImageIcon(new ImageIcon("src/Project_Final/Image/bus_sr_pp_seeat.jpg").getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH));
        JLabel labalSeat = new JLabel(imageSeat);

        labalSeat.setBounds(150,270,150,150);
        panel.add(labalSeat);

        ImageIcon imageBus = new ImageIcon(new ImageIcon("src/bus_sr_pp.png").getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH));
        JLabel lblBus = new JLabel(imageBus);
        lblBus.setBounds(450,270,150,150);
        lblBus.setFont(font);
        panel.add(lblBus);


        JLabel lblname = new JLabel("Name: " + driverName);
        lblname.setBounds(200,30,250,30);
        lblname.setFont(font);
        panel.add(lblname);

        JLabel lblContact = new JLabel("Contact: " + driverPhone);
        lblContact.setBounds(200,70,250,30);
        lblContact.setFont(font);
        panel.add(lblContact);

        JLabel lblPlatnum = new JLabel("PlatNumber: " + busPlate);
        lblPlatnum.setBounds(200,110,250,30);
        lblPlatnum.setFont(font);
        panel.add(lblPlatnum);
        SimpleDateFormat dcn = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String datef = dcn.format(date);
        JLabel lblStart=new JLabel("Start: "+datef+" , "+time);
        lblStart.setFont(font);
        lblStart.setBounds(200,190,300,30);
        panel.add(lblStart);
        JLabel lblDestination = new JLabel("Destination: " + from + " - " + to);
        lblDestination.setBounds(200,150,450,30);
        lblDestination.setFont(font);
        panel.add(lblDestination);
        add(panel);

    }


}


