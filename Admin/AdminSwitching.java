package Project_Final.Admin;

import Project_Final.Window;

import java.awt.*;

public class AdminSwitching extends Window {
    Font font = new Font("arial",Font.PLAIN,14);
    public AdminSwitching(){
        Button btnTable = new Button("Table");
        Button btnReport=new Button("Report");
        setSize(300,120);
        setLocationRelativeTo(null);

        btnTable.setFont(font);
        btnTable.setBounds(25,25,100,30);
        btnReport.setFont(font);
        btnReport.setBounds(160,25,100,30);
        add(btnTable);
        add(btnReport);
        btnTable.addActionListener(e->{
            closeWindow();
            new AdminDasboard();
        });
        btnReport.addActionListener(e->{
            closeWindow();
            new Reporting();
        });
        openWindow();
    }
}
