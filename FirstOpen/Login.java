package Project_Final.FirstOpen;

import Project_Final.Admin.AdminSwitching;
import Project_Final.MySqlService.MySqlProvider;
import Project_Final.User.BusTicket;
import Project_Final.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Login{
  Window wd=new Window();
  public Login(){
    Connection conn=MySqlProvider.MysqlService();
    //Declare components
    JPanel jpanel=new JPanel();
    JLabel txtPhone=new JLabel("Phone:");
    JLabel txtPassword=new JLabel("Password:");
    JLabel txtLogin=new JLabel("Login");
    JTextField jtfPhone=new JTextField();
    JPasswordField jtfPassword=new JPasswordField();
    JLabel txtForgot=new JLabel("<html><u>Create a new account?</u></html>");
    Button tbnLogin=new Button("Login");
    Font font=new Font("Arial", Font.BOLD,14);
    //For Default Window
      wd.setSize(601, 287);
    //For Penel
    jpanel.setLayout(null);
    jpanel.setSize(new Dimension(601, 287));
    jpanel.setBackground(Color.WHITE);
    txtLogin.setFont(new Font("Arial Black",Font.BOLD,19));
    txtLogin.setBounds(260,25,60,30);
    txtLogin.setForeground(Color.BLACK);
    jpanel.add(txtLogin);

    //For Phone
    txtPhone.setFont(font);
    txtPhone.setBounds(65,70,100,25);
    txtPhone.setForeground(Color.BLACK);
    jtfPhone.setBounds(165,70,271,25);
    jtfPhone.setBackground(new Color(204,204,255));
    jtfPhone.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        if(!Character.isDigit(e.getKeyChar())){
          e.consume();
        }
        super.keyTyped(e);
      }
    });
    jpanel.add(txtPhone);
    jpanel.add(jtfPhone);
    //For Password
    txtPassword.setFont(font);
    txtPassword.setBounds(65,110,100,25);
    txtPassword.setForeground(Color.BLACK);
    jtfPassword.setBackground(new Color(204,204,255));
    jtfPassword.setBounds(165,110,271,25);
    jtfPassword.setEchoChar('X');
    jpanel.add(txtPassword);
    jpanel.add(jtfPassword);
    //Label forgot password
    txtForgot.setFont(new Font("Arial",Font.ITALIC,11));
    txtForgot.setForeground(Color.blue);
    txtForgot.setBounds(170,135,150,20);
    txtForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
    txtForgot.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        switchWindow();
        super.mouseClicked(e);
      }
    });
    jpanel.add(txtForgot);
    //Button Login
    tbnLogin.setBackground(Color.BLUE);
    tbnLogin.setFont(new Font("Arial", 1, 14));
    tbnLogin.setForeground(new Color(255, 255, 255));
    tbnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
    tbnLogin.setBounds(250,170,100,30);
    //execute login
    tbnLogin.addActionListener(e->{
      if(jtfPhone.getText().length()>=9&&jtfPhone.getText().length()<=10){
        jtfPhone.setBackground(new Color(204,204,255));
        if(jtfPassword.getText().length()>=8){
          jtfPassword.setBackground(new Color(204,204,255));
          String commandLogin="SELECT * FROM userdata WHERE `phone`='"+jtfPhone.getText()+"' and `password`='"+jtfPassword.getText()+"' LIMIT 1;";
          try {
            ResultSet result=MySqlProvider.MysqlService().createStatement().executeQuery(commandLogin);
            int count =0;
            int admin=0;
            String username ="",phone="";
            while(result.next()){
              admin=result.getInt(5);
              username=result.getString(2);
              phone=result.getString(3);
              count++;
              break;
            }
            System.out.println("count="+count+"\tadmin="+admin);
            if(count == 1){
              if(admin==1){
                wd.closeWindow();
                new AdminSwitching();
              }else if(admin==0){
                wd.closeWindow();
                new BusTicket(username,phone).display();
              }
            }else{
              JOptionPane.showMessageDialog(null,"Information has provide is invalid","Information",JOptionPane.ERROR_MESSAGE);
            }
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }
        }else{
          jtfPassword.setBackground( new Color(250, 125, 125));
          JOptionPane.showMessageDialog(null,"Password must greater than 8 ","Password",JOptionPane.ERROR_MESSAGE);
        }
      }else{
        jtfPhone.setBackground( new Color(250, 125, 125));
        JOptionPane.showMessageDialog(null,"Phone number is invalid","Phone number",JOptionPane.ERROR_MESSAGE);
      }
    });
    jpanel.add(tbnLogin);
    wd.add(jpanel);
    wd.setVisible(true);

  }
  private void switchWindow(){
    wd.closeWindow();
    new Register();
  }
}
