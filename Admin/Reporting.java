package Project_Final.Admin;

import Project_Final.MySqlService.MySqlProvider;
import Project_Final.Window;
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
import java.util.Date;

public class Reporting extends Window {
    Font font=new Font("Arial", Font.PLAIN,14);
    Connection con= MySqlProvider.MysqlService();
    public Reporting(){
        setSize(500,220);
        setLocationRelativeTo(null);
        JLabel busPlate=new JLabel("Bus Plate");
        JComboBox<String> comboBox=new JComboBox<String>();
        JComboBox<String> cbFinish=new JComboBox<String>();
        Button btnStart=new Button("Start");
        JDateChooser datePicker=new JDateChooser();
        JLabel lblFinish=new JLabel("Bus Plate");
        Button btnFinish=new Button("Finish");
        datePicker.setBounds(120,20,200,25);
        datePicker.setFont(font);
        Date dt=new Date();
        String strdate=(dt.getYear()+1900)+"-"+(1+dt.getMonth())+"-"+(dt.getDate()-6);
        Date dateFormate=null;

        try {
            dateFormate=new SimpleDateFormat("yyyy-MM-dd").parse(strdate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        datePicker.setSelectableDateRange(dateFormate,new Date());
        //bus plate
        busPlate.setFont(font);
        busPlate.setBounds(20,70,100,25);
        Date dates=new Date();
        add(busPlate);
        //combox for bus plate
        comboBox.setBounds(120,70,200,30);
        comboBox.setFont(font);
        try {
            Statement statement=con.createStatement();
            String commandSelect="SELECT plate FROM chairinfo"+dates.getDay()+" where Status='free' ORDER BY `plate`";
            ResultSet result=statement.executeQuery(commandSelect);
            while (result.next()){
                comboBox.addItem(result.getString(1));
                //System.out.println(result.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement=con.createStatement();
            String commandSelect="SELECT plate FROM chairinfo"+dates.getDay()+" where Status='running' ORDER BY `plate`";
            ResultSet result=statement.executeQuery(commandSelect);
            while (result.next()){
                cbFinish.addItem(result.getString(1));
                //System.out.println(result.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        add(comboBox);
        add(datePicker);
        //button finished
        btnStart.setFont(font);
        btnStart.setBounds(350,70,100,30);
        add(btnStart);
        btnStart.addActionListener(e->{
            String commandUpdate;
            try {
                Statement statement=con.createStatement();
                for(int i=1;i<=49;i++){
                    commandUpdate="UPDATE chairinfo"+datePicker.getDate().getDay()+" SET `Status`='running' WHERE plate='"+comboBox.getSelectedItem()+"';";
                    if(i==1){
                        statement.executeUpdate(commandUpdate);
                    }
                    commandUpdate="UPDATE chairinfo"+datePicker.getDate().getDay()+" SET chair"+i+"=0 WHERE plate='"+comboBox.getSelectedItem()+"';";
                    statement.executeUpdate(commandUpdate);
                }
                JOptionPane.showMessageDialog(null,"Report successfully","Report",JOptionPane.INFORMATION_MESSAGE);
//                cbFinish.addItem((String) comboBox.getSelectedItem());
//                comboBox.removeItemAt(comboBox.getSelectedIndex());
                cbFinish.removeAllItems();
                comboBox.removeAllItems();
                try {
                    Statement statements=con.createStatement();
                    String commandSelect="SELECT plate FROM chairinfo"+datePicker.getDate().getDay()+" where Status='running' ORDER BY `plate`";
                    ResultSet result=statements.executeQuery(commandSelect);
                    while (result.next()){
                        cbFinish.addItem(result.getString(1));
                        //System.out.println(result.getString(1));
                    }

                } catch (SQLException ed) {
                    ed.printStackTrace();
                }
                try {
                    Statement statements=con.createStatement();
                    String commandSelect="SELECT plate FROM chairinfo"+datePicker.getDate().getDay()+" where Status='free' ORDER BY `plate`";
                    ResultSet result=statements.executeQuery(commandSelect);
                    while (result.next()){
                        comboBox.addItem(result.getString(1));
                        //System.out.println(result.getString(1));
                    }

                } catch (SQLException ed) {
                    ed.printStackTrace();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        btnFinish.addActionListener(e->{
            String commandUpdate="UPDATE chairinfo"+datePicker.getDate().getDay()+" SET `Status`='free' WHERE plate='"+cbFinish.getSelectedItem()+"';";
            try {
                Statement statement=con.createStatement();
                statement.executeUpdate(commandUpdate);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(null,"Report successfully","Report",JOptionPane.INFORMATION_MESSAGE);
//            comboBox.addItem((String) cbFinish.getSelectedItem());
//            cbFinish.removeItemAt(cbFinish.getSelectedIndex());
            cbFinish.removeAllItems();
            comboBox.removeAllItems();
            try {
                Statement statement=con.createStatement();
                String commandSelect="SELECT plate FROM chairinfo"+datePicker.getDate().getDay()+" where Status='running' ORDER BY `plate`";
                ResultSet result=statement.executeQuery(commandSelect);
                while (result.next()){
                    cbFinish.addItem(result.getString(1));
                    //System.out.println(result.getString(1));
                }

            } catch (SQLException ed) {
                ed.printStackTrace();
            }
            try {
                Statement statement=con.createStatement();
                String commandSelect="SELECT plate FROM chairinfo"+datePicker.getDate().getDay()+" where Status='free' ORDER BY `plate`";
                ResultSet result=statement.executeQuery(commandSelect);
                while (result.next()){
                    comboBox.addItem(result.getString(1));
                    //System.out.println(result.getString(1));
                }

            } catch (SQLException ed) {
                ed.printStackTrace();
            }
        });
        //finished
        datePicker.setDate(new Date());
        datePicker.addPropertyChangeListener(e->{
            cbFinish.removeAllItems();
            comboBox.removeAllItems();
            try {
                Statement statement=con.createStatement();
                String commandSelect="SELECT plate FROM chairinfo"+datePicker.getDate().getDay()+" where Status='running' ORDER BY `plate`";
                ResultSet result=statement.executeQuery(commandSelect);
                while (result.next()){
                    cbFinish.addItem(result.getString(1));
                    //System.out.println(result.getString(1));
                }

            } catch (SQLException ed) {
                ed.printStackTrace();
            }
            try {
                Statement statement=con.createStatement();
                String commandSelect="SELECT plate FROM chairinfo"+datePicker.getDate().getDay()+" where Status='free' ORDER BY `plate`";
                ResultSet result=statement.executeQuery(commandSelect);
                while (result.next()){
                    comboBox.addItem(result.getString(1));
                    //System.out.println(result.getString(1));
                }

            } catch (SQLException ed) {
                ed.printStackTrace();
            }
        });
        lblFinish.setFont(font);
        lblFinish.setBounds(20,120,100,25);
        btnFinish.setFont(font);
        btnFinish.setBounds(350,120,100,30);
        cbFinish.setFont(font);
        cbFinish.setBounds(120,120,200,30);

        //back
        JLabel lblBack = new JLabel("Back");
        lblBack.setFont(font);
        lblBack.setBounds(5,2,80,20);
        add(lblBack);
        lblBack.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                closeWindow();
                new AdminSwitching();
            }

        });

        add(cbFinish);
        add(btnFinish);
        add(lblFinish);

        openWindow();
    }
}
