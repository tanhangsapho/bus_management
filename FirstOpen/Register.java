package Project_Final.FirstOpen;

import Project_Final.Window;
import Project_Final.MySqlService.MySqlProvider;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Register {
  Window wd=new Window();
  public Register(){
    Connection mysql=MySqlProvider.MysqlService();
    //Declare components
    JPanel jpanel=new JPanel();
    JLabel txtPhone=new JLabel("Phone:");
    JLabel txtPassword=new JLabel("Password:");
    JLabel txtRegister=new JLabel("Register");
    JLabel txtRe_password=new JLabel("Re-password:");
    JLabel txtFullName=new JLabel("Full Name:");
    JTextField jtfFullName=new JTextField();
    JTextField jtfPhone=new JTextField();
    JPasswordField jtfPassword=new JPasswordField();
    JPasswordField btnRe_password=new JPasswordField();
    JLabel txtAlready=new JLabel("<html><u>Have account already?</u></html>");
    Button tbnCreate=new Button("Create");
    Font font=new Font("Arial", Font.BOLD,14);
    //For Default Window
    wd.setSize(601, 380);
    //For Penel
    jpanel.setLayout(null);
    jpanel.setSize(new Dimension(601, 380));
    jpanel.setBackground(Color.WHITE);
    txtRegister.setFont(new Font("Arial Black",Font.BOLD,19));
    txtRegister.setBounds(240,25,100,30);
    txtRegister.setForeground(Color.BLACK);
    jpanel.add(txtRegister);
    //for full name
    txtFullName.setFont(font);
    txtFullName.setBounds(65,75,100,25);
    txtFullName.setForeground(Color.BLACK);
    jtfFullName.setBounds(165,75,271,25);
    jtfFullName.setBackground(new Color(204,204,255));
    jpanel.add(jtfFullName);
    jpanel.add(txtFullName);
    //For Phone
    txtPhone.setFont(font);
    txtPhone.setBounds(65,120,100,25);
    txtPhone.setForeground(Color.BLACK);
    jtfPhone.setBounds(165,120,271,25);
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
    txtPassword.setBounds(65,160,100,25);
    txtPassword.setForeground(Color.BLACK);
    jtfPassword.setBackground(new Color(204,204,255));
    jtfPassword.setBounds(165,160,271,25);
    jtfPassword.setEchoChar('X');
    jpanel.add(txtPassword);
    jpanel.add(jtfPassword);
    //Re Password
    txtRe_password.setBounds(65,200,100,25);
    txtRe_password.setFont(font);
    btnRe_password.setBounds(165,200,271,25);
    btnRe_password.setBackground(new Color(204,204,255));
    btnRe_password.setEchoChar('X');
    jpanel.add(btnRe_password);
    jpanel.add(txtRe_password);
    //Label have account already
    txtAlready.setFont(new Font("Arial",Font.ITALIC,11));
    txtAlready.setForeground(Color.blue);
    txtAlready.setBounds(170,225,250,20);
    txtAlready.setCursor(new Cursor(Cursor.HAND_CURSOR));
    txtAlready.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        switchWindow();
        super.mouseClicked(e);
      }
    });
    jpanel.add(txtAlready);
    //Button Create
    tbnCreate.setBackground(Color.BLUE);
    tbnCreate.setFont(new Font("Arial", 1, 14));
    tbnCreate.setForeground(new Color(255, 255, 255));
    tbnCreate.setCursor(new Cursor(Cursor.HAND_CURSOR));
    tbnCreate.setBounds(250,260,100,30);
    jpanel.add(tbnCreate);
    //where to store data to table in mysql
    tbnCreate.addActionListener(e->{
      if(jtfPhone.getText().length()>=9&&jtfPhone.getText().length()<=10){
        jtfPhone.setBackground(new Color(204,204,255));
        if(jtfPassword.getText().length()>=8){
          jtfPassword.setBackground(new Color(204,204,255));
          if(jtfPassword.getText().equals(btnRe_password.getText())){
            btnRe_password.setBackground(new Color(204,204,255));
            try {
              Statement statement=mysql.createStatement();
              String commandSelector="SELECT * FROM userdata WHERE `phone`='"+jtfPhone.getText()+"'LIMIT 1;";
              ResultSet result=statement.executeQuery(commandSelector);
              int count=0;
              while(result.next()){
                count++;
                break;
              }
              if(count==0){
                String command="INSERT INTO userdata (`username`,`phone`,`password`,`admin`) values('"+jtfFullName.getText()+"','"+jtfPhone.getText()+"','"+jtfPassword.getText()+"',0)";
                //System.out.println(command);
                statement.executeUpdate(command);
                wd.closeWindow();
                new Login();

              }else{
                JOptionPane.showMessageDialog(null,"Phone number has used already","Re-Password",JOptionPane.ERROR_MESSAGE);
              }

            } catch (SQLException erorr) {
              erorr.printStackTrace();
            }
          }else{
            btnRe_password.setBackground( new Color(250, 125, 125));
            JOptionPane.showMessageDialog(null,"Password not match","Re-Password",JOptionPane.ERROR_MESSAGE);
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

    wd.add(jpanel);
    wd.openWindow();

  }
  private void switchWindow(){
    wd.closeWindow();
    new Login();
  }
}
