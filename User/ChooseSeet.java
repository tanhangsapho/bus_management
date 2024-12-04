package Project_Final.User;

import Project_Final.MySqlService.MySqlProvider;
import Project_Final.Window;

import javax.swing.*;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChooseSeet extends Window {
    ArrayList<String> list=new ArrayList<String>();
    Connection conn = MySqlProvider.MysqlService();
    public ChooseSeet(String username,String phone, String from, String to, Date dt,String tm,int option,HashMap<String,Integer[]> map,HashMap<String,String> mapName,HashMap<String,String> mapPhone,HashMap<String,String> mapPlate,ArrayList<String> mapBus) throws SQLException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(400,500);
        setLocationRelativeTo(null);
        setLayout(null);
        JPanel panel=new JPanel();

        JComboBox comboBox=new JComboBox();
        for(int i=0;i<mapBus.size();i++) {
            comboBox.addItem(mapBus.get(i));
        }


        comboBox.setBounds(100,50,200,30);
        add(comboBox);
        panel.setSize(400,350);
        panel.setBounds(0,100,panel.getWidth(),panel.getHeight());
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(7,7));
        //Back
        JButton btnBack=new JButton("Back");
        btnBack.setBackground(Color.yellow);
        btnBack.setBounds(5,5,100,25);
        add(btnBack);
        btnBack.addActionListener(e->{
            closeWindow();
            if(option==1){
                new BusTicket().display();
            }else{
                new BusTicket(username,phone).display();
            }

        });
        //Next
        JButton btnNext=new JButton("Next");
        btnNext.setBackground(Color.yellow);
        btnNext.setBounds(280,5,100,25);
        add(btnNext);

        //comboBox Action
        JCheckBox[] checkBok=new JCheckBox[49];
        ArrayList<Integer> listInt=new ArrayList<Integer>();
        for(int i=0;i<checkBok.length;i++){
            checkBok[i]=new JCheckBox((i+1)+"");
            checkBok[i].setBackground(Color.WHITE);
            checkBok[i].setFont(new Font("Arial",Font.BOLD,15));
            panel.add(checkBok[i]);
        }
        comboBox.addPropertyChangeListener(e->{
            //System.out.println(comboBox.getSelectedItem().toString());

            Integer[] listChairs= map.get((String) comboBox.getSelectedItem());
            for(int i=0; i<49; i++){

                if(listChairs[i]==1){

                    checkBok[i].setForeground(Color.red);
                    checkBok[i].setEnabled(false);

                }else{
                    checkBok[i].setEnabled(true);
                    checkBok[i].setForeground(Color.BLUE);

                }
            }
        });
        add(panel);
        setVisible(true);
        btnNext.addActionListener(e->{
            int k=0;
            for (int i = 0; i < checkBok.length; i++) {
                if (checkBok[i].isSelected()) {
                    k++;
                    list.add(checkBok[i].getText());
                }
            }

            StringBuilder seats= new StringBuilder();
            for(int i=0;i<k;i++){
                if(i<k-1){
                    seats.append(list.get(i)+",");
                }else{
                    seats.append(list.get(i));
                }

            }
            String seated= String.valueOf(seats);
            double price=k*4.5;
            if(k==0){
                JOptionPane.showMessageDialog(null,"Please choose at least one seat");
            }else{
                closeWindow();
                try {
                    new Information(username,phone,from,to,dt,tm,mapName.get((String) comboBox.getSelectedItem()),mapPhone.get((String) comboBox.getSelectedItem()),mapPlate.get((String) comboBox.getSelectedItem()),seated,price,list,option,map,mapName,mapPhone,mapPlate,mapBus).display();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });



    }
}
