//package GUI;

//import SQL.operationSQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class Login {
    public JFrame jf = new JFrame("Login");
    private String password_stu = "12345678";
    private String password_ad = "87654321";
    private String date = "2019/11/26";

    public Login(){
        //set the frame size
        jf.setBounds(300,100,500,  400);

        //press the button to logout
        //may need further improvement on the look
        Title title = new Title();
        jf.add(title.b1, BorderLayout.NORTH);

        //add main panel
        MainPanel mp=new MainPanel();
        jf.add(mp.mp,BorderLayout.CENTER);

        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public class Title extends JPanel{
        public Box b1 = Box.createHorizontalBox();

        public Title() {
            JLabel titlel = new JLabel("Welcome to Course Register System");
            titlel.setFont(new Font("", Font.BOLD, 20));
            b1.setPreferredSize(new Dimension(500, 50));
            b1.add(Box.createHorizontalGlue());
            b1.add(titlel);
            b1.add(Box.createHorizontalGlue());
        }
    }

    public class MainPanel extends JPanel{
        public JPanel mp;
        public JPanel sp;
        public JPanel sp_0;
        public JPanel sp_1;
        public JPanel sp_2;
        private CardLayout card = new CardLayout();
        public Box b1;
        public JTextField stf;
        public JTextField pstf;
        public JTextField patf;
        JLabel hintsl;
        JLabel hintal;


        public MainPanel(){
            mp = new JPanel();
            mp.setBackground(Color.white);

            Box bv = Box.createVerticalBox();
            Box bi = Box.createHorizontalBox();

            JLabel il = new JLabel("Please Choose Your Identity: ");
            JButton sbt = new JButton("Student");
            sbtListenerClass listener1 = new sbtListenerClass();
            sbt.addActionListener(listener1);
            JButton abt = new JButton("Administrator");
            abtListenerClass listener2 = new abtListenerClass();
            abt.addActionListener(listener2);

            bi.add(il);
            bi.add(Box.createRigidArea(new Dimension(10,10)));
            bi.add(sbt);
            bi.add(Box.createRigidArea(new Dimension(10,10)));
            bi.add(abt);
            bi.add(Box.createRigidArea(new Dimension(10,10)));

            bv.add(Box.createRigidArea(new Dimension(20,20)));
            bv.add(bi);
            bv.add(Box.createRigidArea(new Dimension(20,20)));
            mp.add(bv,BorderLayout.NORTH);

            sp = new JPanel();
            sp.setLayout(card);
            mp.add(sp, BorderLayout.CENTER);

            sp_0 = new JPanel();
            sp_0.setBackground(Color.white);
            sp_1 = new JPanel();
            showStu();
            sp_2 = new JPanel();
            showAd();

            sp.add("0",sp_0);
            sp.add("1",sp_1);
            sp.add("2",sp_2);

            card.show(sp,"0");
        }

        private class sbtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
               card.show(sp,"1");
            }
        }

        private class abtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
                card.show(sp,"2");
            }
        }

        private void showStu(){
            sp_1 = new JPanel();
            sp_1.setPreferredSize(new Dimension(400,230));
            sp_1.setBackground(Color.LIGHT_GRAY);

            JLabel il = new JLabel("Identity: ");
            il.setPreferredSize(new Dimension(80,20));
            JLabel isl = new JLabel("Student");
            isl.setPreferredSize(new Dimension(200,20));
            JLabel sl = new JLabel("Student ID: ");
            sl.setPreferredSize(new Dimension(80,20));
            stf = new JTextField();
            stf.setPreferredSize(new Dimension(200,20));
            JLabel pl = new JLabel("Password: ");
            pl.setPreferredSize(new Dimension(80,20));
            pstf = new JTextField();
            pstf.setPreferredSize(new Dimension(200,20));
            JButton lb = new JButton("Login");
            lsbtListenerClass listener3 = new lsbtListenerClass();
            lb.addActionListener(listener3);
            hintsl = new JLabel("");


            b1 = Box.createVerticalBox();
            Box b2 = Box.createHorizontalBox();

            b1.add(Box.createRigidArea(new Dimension(20,20)));
            b2.add(il);
            b2.add(Box.createHorizontalGlue());
            b2.add(isl);
            b1.add(b2);

            b2 = Box.createHorizontalBox();
            b1.add(Box.createRigidArea(new Dimension(20,20)));
            b2.add(sl);
            b2.add(stf);
            b1.add(b2);

            b2 = Box.createHorizontalBox();
            b1.add(Box.createRigidArea(new Dimension(20,20)));
            b2.add(pl);
            b2.add(pstf);
            b1.add(b2);

            b2 = Box.createHorizontalBox();
            b1.add(Box.createRigidArea(new Dimension(20,20)));
            b2.add(Box.createHorizontalGlue());
            b2.add(lb);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);

            b2 = Box.createHorizontalBox();
            b1.add(Box.createRigidArea(new Dimension(10,10)));
            b2.add(Box.createHorizontalGlue());
            b2.add(hintsl);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);

            sp_1.add(b1,BorderLayout.CENTER);
            /*mp.add(sp, BorderLayout.CENTER);
            mp.setVisible(true);
            jf.setVisible(true);*/
        }

        private class lsbtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
                try {
                    logAsStu();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void showAd(){
            sp_2 = new JPanel();
            sp_2.setPreferredSize(new Dimension(400,230));
            sp_2.setBackground(Color.LIGHT_GRAY);

            JLabel il = new JLabel("Identity: ");
            il.setPreferredSize(new Dimension(80,20));
            JLabel ial = new JLabel("Administrator");
            ial.setPreferredSize(new Dimension(200,20));
            JLabel pl = new JLabel("Password: ");
            pl.setPreferredSize(new Dimension(80,20));
            patf = new JTextField();
            patf.setPreferredSize(new Dimension(200,20));
            JButton lb = new JButton("Login");
            labtListenerClass listener4 = new labtListenerClass();
            lb.addActionListener(listener4);
            hintal = new JLabel("");

            b1 = Box.createVerticalBox();
            Box b2 = Box.createHorizontalBox();

            b1.add(Box.createRigidArea(new Dimension(20,20)));
            b2.add(il);
            b2.add(Box.createHorizontalGlue());
            b2.add(ial);
            b1.add(b2);

            b2 = Box.createHorizontalBox();
            b1.add(Box.createRigidArea(new Dimension(20,20)));
            b2.add(pl);
            b2.add(patf);
            b1.add(b2);

            b2 = Box.createHorizontalBox();
            b1.add(Box.createRigidArea(new Dimension(20,20)));
            b2.add(Box.createHorizontalGlue());
            b2.add(lb);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);

            b2 = Box.createHorizontalBox();
            b1.add(Box.createRigidArea(new Dimension(10,10)));
            b2.add(Box.createHorizontalGlue());
            b2.add(hintal);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);

            sp_2.add(b1,BorderLayout.CENTER);
            /*mp.add(sp, BorderLayout.CENTER);
            mp.setVisible(true);
            jf.setVisible(true);*/
        }

        private class labtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
                try {
                    logAsAd();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void logAsStu() throws IOException, SQLException {
            String s1 = stf.getText();
            String s2 = pstf.getText();
            operationSQL test = new operationSQL();
            //int exist=1;
            int exist = test.searchStuId(s1);
            if(exist>0 && !s2.equals(password_stu)) hintsl.setText("Student ID or Password Error");
            else {
                System.out.println("Login Student Successful");
                jf.setVisible(false);
                Student stu=new Student(s1,date);

            }
        }

        private void logAsAd() throws IOException, SQLException {
            String s1 = patf.getText();

            if(!s1.equals(password_ad)) hintal.setText("Password Error");
            else {
                System.out.println("Login Administrator Successful");
                jf.setVisible(false);
                Administrator ad=new Administrator();
            }

        }

    }

    /*public static void main(String[] args){
        Login lg=new Login();
    }*/


}