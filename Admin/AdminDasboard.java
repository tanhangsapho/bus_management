package Project_Final.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import Project_Final.MySqlService.MySqlProvider;
import Project_Final.User.BusTicket;
import Project_Final.Window;
import com.toedter.calendar.JDateChooser;


public class AdminDasboard extends Window{

    JScrollPane jScrollPane=new JScrollPane();
    boolean option=true;
    Connection con= MySqlProvider.MysqlService();

    //    Window wd=new Window();
    public AdminDasboard(){
        setLayout(null);
        setSize(1100,500);
        JButton btnInsert=new JButton("Insert");
        JButton btnDelete =new JButton("Delete");
        JDateChooser calendar=new JDateChooser();
        JPanel panelTable =new JPanel();
        setLocationRelativeTo(null);
        panelTable.setLayout(null);
        panelTable.setBackground(Color.CYAN);
        panelTable.setBounds(50,50,1000,300);
        Font font=new Font("Arial Black",Font.BOLD,15);
        //calendar.
        calendar.setBounds(450,15,200,20);
        //calendar.setDate(new Date);
        //Jan 17, 2023
        Date nowDate=new Date();
        String date = (1900+nowDate.getYear())+"-"+(1+nowDate.getMonth())+"-"+nowDate.getDate();
        Date currentDate= null;
        try {
            currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setDate(currentDate);
        AtomicReference<JTable> table = new AtomicReference<>();
        calendar.addPropertyChangeListener(e->{
            SimpleDateFormat dcnf = new SimpleDateFormat("dd-MM-yyyy");
            String dated = dcnf.format(calendar.getDate());
            table.set(generateTable(dated));
            JScrollPane scrollPane =new JScrollPane(table.get());
            scrollPane.setSize(panelTable.getWidth(),panelTable.getHeight());
            table.get().setSize(panelTable.getWidth(),panelTable.getHeight());
            panelTable.add(scrollPane);
            panelTable.setComponentZOrder(scrollPane,0);
            table.get().getModel().addTableModelListener(exs -> {
                String[] datarow=new String[10];
                for(int i=0;i<10;i++){
                    datarow[i]= table.get().getModel().getValueAt(table.get().getSelectedRow(),i).toString();
                }
                try {
                    Statement statement=con.createStatement();
                    String commandUpdate="UPDATE `datatabel` SET `name` = '"+datarow[1]+"', `phone` = '"+datarow[2]+"', `from` = '"+datarow[3]+"', `to` = '"+datarow[4]+"', `date` = '"+datarow[5]+"', `time` = '"+datarow[6]+"', `seat` = '"+datarow[7]+"', `total` = '"+datarow[8]+"', `bus_plate` = '"+datarow[9]+"' WHERE `id` = "+datarow[0]+";";
                    //System.out.println(commandUpdate);
                    int a=statement.executeUpdate(commandUpdate);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

        });

        add(calendar);
        //table

        //delete
        btnDelete.setFont(font);
        btnDelete.setBounds(400,400,100,35);
        add(btnDelete);
        btnDelete.addActionListener(ed->{
            String st = table.get().getModel().getValueAt(0,0).toString();
            int index= table.get().getSelectedRow();
            int id=Integer.parseInt(table.get().getModel().getValueAt(index,0).toString());
            System.out.println(st);
            //JOptionPane.showConfirmDialog(null,"Are you sure?","Delete",JOptionPane.YES_NO_OPTION);
            JCheckBox showAgain=new JCheckBox("Don't show again!");
//                DefaultTableModel dtm=(DefaultTableModel) table.get().getModel();
//                dtm.removeRow(index);
            if(option){
                JOptionPane.showConfirmDialog(null,showAgain,"Delete",JOptionPane.YES_NO_OPTION);
                if(showAgain.isSelected()){
                    option=false;
                }
                ((DefaultTableModel) table.get().getModel()).removeRow(index);
                deleteDataFromDatabase(id);
            }else{
                ((DefaultTableModel) table.get().getModel()).removeRow(index);
                deleteDataFromDatabase(id);
            }
        });
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
        //insert
        btnInsert.setFont(font);
        btnInsert.setBounds(550,400,100,35);
        add(btnInsert);
        btnInsert.addActionListener(e->{
            //open form for user input
//            SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
//            String datef = dcn.format(calendar.getDate() );
            closeWindow();
            new BusTicket().display();
        });
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panelTable);
        setVisible(true);

    }
    private JTable generateTable(String date){
        List<String[]> list = new ArrayList<String[]>();
        try {
            Statement statement=con.createStatement();
            String commandSelect="SELECT * FROM datatabel where `date`='"+date+"'";
            //System.out.println(commandSelect);
            ResultSet result=statement.executeQuery(commandSelect);
            while (result.next()){
                String[] datarow=new String[10];
                for(int i=0;i<10;i++){
                    datarow[i]=result.getString(i+1);
                }
                list.add(datarow);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //String[][] data=new String[list.size()][3];
        String[] columnNames={"No","Name","Phone","From","To","Date","Time","Seats","Total","Bus Plate"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
        list.forEach(dt->{
            tableModel.addRow(new Object[]{dt[0],dt[1],dt[2],dt[3],dt[4],dt[5],dt[6],dt[7],dt[8],dt[9]});

        });
        tableModel.addRow(new Object[]{"","",""});
        JTable table =new JTable(tableModel);
        table.addRowSelectionInterval(0,0);
        table.setSize(1000,500);
        //table.setDefaultEditor(Object.class,null);

        return table;

    }

    private void deleteDataFromDatabase(int id){
        try {
            Statement statement=con.createStatement();
            String commandDelete="DELETE FROM datatabel where id="+id+"";
            statement.executeUpdate(commandDelete);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
