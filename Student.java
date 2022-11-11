//package GUI;

//import SQL.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    public JFrame jf = new JFrame("Student");
    public String stuIdLogin;// = "50000150";
    public String dateLogin;// = "2019/11/23";

    public viewPanel view;
    public JPanel vp = new JPanel();
    public CardLayout cardvp = new CardLayout();
    public JPanel vp_0, vp_1, vp_2, vp_3, vp_4;

    public Student(String stuIdLogin, String dateLogin) throws IOException, SQLException {
        this.stuIdLogin = stuIdLogin;
        this.dateLogin = dateLogin;

        //set the frame size
        jf.setBounds(300,100,1000,  800);

        //press the panel for title
        Title title = new Title();
        jf.add(title.tp, BorderLayout.NORTH);

        view = new viewPanel();
        cardvp.show(vp,"0");
        //add main panel
        JPanel mp=new MainPanel();
        jf.add(mp,BorderLayout.CENTER);

        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public class Title extends JPanel{
        public JPanel tp = new JPanel();

        public Title() {
            JButton logout = new JButton("Logout");
            logoutListenerClass listenerlg = new logoutListenerClass();
            logout.addActionListener(listenerlg);

            tp.setBounds(300, 100,800,70);
            Box b1 = Box.createVerticalBox();
            Box b2 = Box.createHorizontalBox();
            Box b3 = Box.createHorizontalBox();
            tp.add(b1);
            //logout.setBounds(300,130,400,25);
            JLabel title_l = new JLabel("Course Register System");
            title_l.setFont(new Font("",Font.BOLD, 18));
            b3.add(title_l);
            b3.add(Box.createRigidArea(new Dimension(50,20)));
            b1.add(b3);
            //tp.setBounds(300, 100,800,25);
            JLabel stu_l = new JLabel("[Student]");
            b1.add(stu_l);
            //logout.setBounds(800,130,400,25);
            b2.add(Box.createHorizontalGlue());
            b2.add(stu_l);
            b2.add(logout);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);
            //this txtfield is for the selected course or student name
        }

        private class logoutListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                jf.setVisible(false);
                Login newreg = new Login();
            }
        }
    }

    public class MainPanel extends JPanel{
        JPanel bp = new JPanel();
        choosePanel cp = new choosePanel();
        // According to the listener(Which Label is selected in choosePanel),
        // set the parameter of viewPanel to different value, to get the different panel.
        // In default, set the value to "default";


        public MainPanel() throws IOException, SQLException {
            bp.setPreferredSize(new Dimension(800,20));
            this.add(bp, BorderLayout.NORTH);
            this.add(cp.cp, BorderLayout.WEST);
            this.add(vp, BorderLayout.EAST);
        }

    }

    public class choosePanel{
        public JPanel cp = new JPanel();
        public JButton lcbt = new JButton("List Courses");
        public JButton lrcbt = new JButton("List Registered Courses");
        public JButton rcbt = new JButton("Register Courses");
        public JButton mpibt = new JButton("Modify Personal Information");

        public choosePanel() {


            cp.setPreferredSize(new Dimension(200,800));
            Box b1 = Box.createVerticalBox();

            b1.add(Box.createRigidArea(new Dimension(200, 30)));
            Box b2 = Box.createHorizontalBox();
            b2.add(lcbt);
            lcbtListenerClass listener1 = new lcbtListenerClass();
            lcbt.addActionListener(listener1);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);

            b1.add(Box.createRigidArea(new Dimension(200, 30)));
            b2 = Box.createHorizontalBox();
            b2.add(lrcbt);
            lrcbtListenerClass listener2 = new lrcbtListenerClass();
            lrcbt.addActionListener(listener2);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);

            b1.add(Box.createRigidArea(new Dimension(200, 30)));
            b2 = Box.createHorizontalBox();
            b2.add(rcbt);
            rcbtListenerClass listener3 = new rcbtListenerClass();
            rcbt.addActionListener(listener3);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);

            b1.add(Box.createRigidArea(new Dimension(200, 30)));
            b2 = Box.createHorizontalBox();
            b2.add(mpibt);
            mpibtListenerClass listener4 = new mpibtListenerClass();
            mpibt.addActionListener(listener4);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);
            cp.add(b1);
        }

        private class lcbtListenerClass implements ActionListener{
            public void actionPerformed(ActionEvent e){
                /*viewPanel vp = null;
                try {
                    vp = new viewPanel("List Courses");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                jf.add(vp.vp, BorderLayout.EAST);
                jf.setVisible(true);*/
                cardvp.show(vp, "1");
            }
        }

        private class lrcbtListenerClass implements ActionListener{
            public void actionPerformed(ActionEvent e){
                vp_2 = new JPanel();
                try {
                    view.listRegisteredCourses();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                vp.add("2",vp_2);
                cardvp.show(vp, "2");
            }
        }

        private class rcbtListenerClass implements ActionListener{
            public void actionPerformed(ActionEvent e){
                vp_3 = new JPanel();
                try {
                    view.registerCourses();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                vp.add("3",vp_3);
                cardvp.show(vp, "3");
            }
        }

        private class mpibtListenerClass implements ActionListener{
            public void actionPerformed(ActionEvent e){
                vp_4 = new JPanel();
                try {
                    view.modifyPersonalInformation();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                vp.add("4",vp_4);
                cardvp.show(vp, "4");
            }
        }

    }

    public class viewPanel extends JPanel{

        public viewPanel() throws IOException, SQLException {
            vp.setPreferredSize(new Dimension(700,800));
            vp.setBackground(Color.white);
            vp.setLayout(cardvp);

            vp_0 = new JPanel();
            defaultvp();
            vp_1 = new JPanel();
            listCourses();
            vp_2 = new JPanel();
            listRegisteredCourses();
            vp_3 = new JPanel();
            registerCourses();
            vp_4 = new JPanel();
            modifyPersonalInformation();

            vp.add("0",vp_0);
            vp.add("1",vp_1);
            vp.add("2",vp_2);
            vp.add("3",vp_3);
            vp.add("4",vp_4);
        }

        public void defaultvp() throws IOException, SQLException {
            Box b1 = Box.createVerticalBox();

            setInformation(b1, "STUDENT_ID");
            setInformation(b1, "STUDENT_NAME");
            setInformation(b1, "DEPARTMENT");
            setInformation(b1, "ADDRESS");
            setInformation(b1, "BIRTHDATE");
            setInformation(b1, "GENDER");

            vp_0.add(b1);
        }

        private void setInformation(Box b1, String s) throws IOException, SQLException {
            Box b2 = Box.createHorizontalBox();
            b2.setPreferredSize(new Dimension(600, 50));
            JLabel sl = new JLabel("  "+s+":  ");
            operationSQL test_getInfo = new operationSQL();
            JLabel sl2 = new JLabel(test_getInfo.getStuInformation(stuIdLogin,s));
            //JLabel sl2 = new JLabel("*****");
            b2.add(sl);
            b2.add(sl2);
            b2.add(Box.createHorizontalGlue());
            b1.add(Box.createRigidArea(new Dimension(600,30)));
            b1.add(b2);
        }


        //need implementation:
        //set vp to LIST COURSES
        public void listCourses() throws IOException, SQLException {
            operationSQL test1 = new operationSQL();
            JTable table_list = new JTable();
            table_list.setPreferredScrollableViewportSize(new Dimension(600,500));
            table_list.setBorder(new LineBorder(new Color(0, 0, 0)));
            String[] head=new String[] {
                    "COURSE_ID", "COURSE_TITLE", "STAFF_NAME", "SECTION",
            };

            DefaultTableModel tableModel=new DefaultTableModel(test1.question_b(),head){
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            table_list.setModel(tableModel);

            table_list.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table_list.getColumnModel().getColumn(0).setPreferredWidth(100);
            table_list.getColumnModel().getColumn(1).setPreferredWidth(200);
            table_list.getColumnModel().getColumn(2).setPreferredWidth(130);
            table_list.getColumnModel().getColumn(3).setPreferredWidth(100);
            vp_1.add(new JScrollPane(table_list));
        }

        //need implementation:
        //set vp to LIST REGISTERED COURSES
        public void listRegisteredCourses()throws IOException,SQLException{
            operationSQL test = new operationSQL();
            JTable table_list = new JTable();
            table_list.setPreferredScrollableViewportSize(new Dimension(600,500));
            table_list.setBorder(new LineBorder(new Color(0, 0, 0)));
            String[] head=new String[] {
                    "COURSE_ID", "COURSE_TITLE", "STAFF_NAME", "SECTION",
            };

            DefaultTableModel tableModel=new DefaultTableModel(test.question_c(stuIdLogin),head){
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            table_list.setModel(tableModel);

            table_list.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table_list.getColumnModel().getColumn(0).setPreferredWidth(100);
            table_list.getColumnModel().getColumn(1).setPreferredWidth(200);
            table_list.getColumnModel().getColumn(2).setPreferredWidth(130);
            table_list.getColumnModel().getColumn(3).setPreferredWidth(100);
            vp_2.add(new JScrollPane(table_list));
        }


        //need implementation:
        //set vp to REGISTER COURSES
        public JTable table_reg;
        public JLabel hintl = new JLabel("");
        public JLabel hintl2 = new JLabel("");
        public void registerCourses() throws IOException, SQLException {
            hintl = new JLabel("");
            hintl2 = new JLabel("");
            JLabel regInstruction1 = new JLabel("Choose the line of the course you want to register, and click the button \"Register\".");
            regInstruction1.setPreferredSize(new Dimension(600,20));
            JLabel regInstruction2 = new JLabel("Check whether you have successfully register the course, according to the prompt line under the button.");
            regInstruction2.setPreferredSize(new Dimension(600,20));
            JLabel regInstruction3 = new JLabel("Hint: For one operation, you can only register one course.");
            regInstruction3.setPreferredSize(new Dimension(600,20));
            table_reg = new JTable();
            table_reg.setPreferredScrollableViewportSize(new Dimension(600,450));
            table_reg.setBorder(new LineBorder(new Color(0, 0, 0)));
            String[] head=new String[] {
                    "COURSE_ID", "COURSE_TITLE", "STAFF_NAME", "SECTION",
            };

            /*Object[][] data = {
                    new Object[]{"CS2172", "Fundamentals of Computing", "Ka Chun K. LEE", "C01"},
                    new Object[]{"CS2468", "Data Structures and Data Mgt", "Chung Sing V. LEE", "C01"}
            };*/
            operationSQL test2_list = new operationSQL();
            DefaultTableModel tableModel=new DefaultTableModel(test2_list.question_dSelect(stuIdLogin),head){
                //DefaultTableModel tableModel=new DefaultTableModel(data,head){
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            table_reg.setModel(tableModel);

            table_reg.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table_reg.getColumnModel().getColumn(0).setPreferredWidth(100);
            table_reg.getColumnModel().getColumn(1).setPreferredWidth(200);
            table_reg.getColumnModel().getColumn(2).setPreferredWidth(130);
            table_reg.getColumnModel().getColumn(3).setPreferredWidth(100);
            Box b1 = Box.createVerticalBox();
            Box b2 = Box.createHorizontalBox();
            b2.add(regInstruction1);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);
            b2 = Box.createHorizontalBox();
            b2.add(regInstruction2);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);
            b2 = Box.createHorizontalBox();
            b2.add(regInstruction3);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);

            b1.add(new JScrollPane(table_reg));

            b2 = Box.createHorizontalBox();
            JButton regbt = new JButton("Register");
            b2.add(Box.createHorizontalGlue());
            b2.add(regbt);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);

            b2 = Box.createHorizontalBox();
            b2.add(Box.createHorizontalGlue());
            b2.add(hintl);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);

            b2 = Box.createHorizontalBox();
            b2.add(Box.createHorizontalGlue());
            b2.add(hintl2);
            b2.add(Box.createHorizontalGlue());
            b1.add(b2);
            vp_3.add(b1);

            regbtListenerClass listener5 = new regbtListenerClass();
            regbt.addActionListener(listener5);

        }

        private class regbtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){

                int count=table_reg.getSelectedRow();
                if(count!=-1){
                    String getCourseId= table_reg.getValueAt(count, 0).toString();
                    try {
                        operationSQL test2_reg = new operationSQL();
                        test2_reg.question_dReg(stuIdLogin,getCourseId,dateLogin);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    hintl.setText("You have successfully register the course "+getCourseId+".");
                    hintl2.setText("If you want to register another course, click \"Register Courses\" button first.");
                    count=-1;
                }
                else{
                    hintl.setText("Fail to register. Please choose a course first.");
                }
            }
        }

        //need implementation:
        //set vp to MODIFY PERSONAL INFORMATION
        public void modifyPersonalInformation()throws IOException,SQLException{
            Box b = Box.createVerticalBox();
            JLabel hint=new JLabel("");

            Box b1=Box.createHorizontalBox();
            JLabel id=new JLabel(" STUDENT_ID : ");
            JTextField tId=new JTextField(stuIdLogin);
            tId.setEditable(false);
            b1.add(id);
            b1.add(tId);

            Box b2=Box.createHorizontalBox();
            JLabel name=new JLabel(" STUDENT_NAME : ");
            JTextField tName=new JTextField();
            tName.setText(new operationSQL().getStuInformation(stuIdLogin,"STUDENT_NAME"));
            tName.setEditable(false);
            JButton bN=new JButton("Modify");
            bN.addActionListener(new ModifyLsn(tName));
            JButton btnN=new JButton("Save");
            btnN.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tName.getText().length()>100 ||tName.getText().length()==0) {
                        hint.setText("Error! Please input again!");
                        return;
                    }
                    else {
                        try {
                            operationSQL test = new operationSQL();
                            test.question_e("STUDENT_NAME", tName.getText(), stuIdLogin);
                            tName.setEditable(false);
                            hint.setText("Modified successfully!");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            hint.setText("Modify failed! Please input valid content.");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            hint.setText("Modify failed! Please input valid content.");
                        }
                    }
                }
            });
            b2.add(name);
            b2.add(tName);
            b2.add(bN);
            b2.add(btnN);


            Box b3=Box.createHorizontalBox();
            JLabel dept=new JLabel(" DEPARTMENT : ");
            JTextField tDept=new JTextField();
            tDept.setText(new operationSQL().getStuInformation(stuIdLogin,"DEPARTMENT"));
            tDept.setEditable(false);
            JButton bD=new JButton("Modify");
            bD.addActionListener(new ModifyLsn(tDept));
            JButton btnD=new JButton("Save");
            btnD.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tDept.getText().length()>100 ||tDept.getText().length()==0) {
                        hint.setText("Error! Please input again!");
                        return;
                    }
                    else {
                        try {
                            operationSQL test = new operationSQL();
                            test.question_e("DEPARTMENT", tDept.getText(), stuIdLogin);
                            tDept.setEditable(false);
                            hint.setText("Modified successfully!");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            hint.setText("Modify failed! Please input valid content.");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            hint.setText("Modify failed! Please input valid content.");
                        }
                    }
                }
            });
            b3.add(dept);
            b3.add(tDept);
            b3.add(bD);
            b3.add(btnD);

            Box b4=Box.createHorizontalBox();
            JLabel addr=new JLabel(" ADDRESS : ");
            JTextField tAddr=new JTextField();
            tAddr.setText(new operationSQL().getStuInformation(stuIdLogin,"ADDRESS"));
            tAddr.setEditable(false);
            JButton bA=new JButton("Modify");
            bA.addActionListener(new ModifyLsn(tAddr));
            JButton btnA=new JButton("Save");
            btnA.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tAddr.getText().length()>100 ||tAddr.getText().length()==0) {
                        hint.setText("Error! Please input again!");
                        return;
                    }
                    else {
                        try {
                            operationSQL test = new operationSQL();
                            test.question_e("ADDRESS", tAddr.getText(), stuIdLogin);
                            tAddr.setEditable(false);
                            hint.setText("Modified successfully!");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            hint.setText("Modify failed! Please input valid content.");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            hint.setText("Modify failed! Please input valid content.");
                        }
                    }
                }
            });
            b4.add(addr);
            b4.add(tAddr);
            b4.add(bA);
            b4.add(btnA);

            Box b5=Box.createHorizontalBox();
            JLabel bir=new JLabel(" BIRTHDATE : ");
            JTextField tBir=new JTextField();
            tBir.setText(new operationSQL().getStuInformation(stuIdLogin,"BIRTHDATE").split(" ")[0]);
            tBir.setEditable(false);
            JButton bB=new JButton("Modify");
            bB.addActionListener(new ModifyLsn(tBir));
            JButton btnB=new JButton("Save");
            btnB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tBir.getText().length()>10 ||tBir.getText().length()==0) {
                        hint.setText("Error! Please input again!");
                        return;
                    }
                    else {
                        try {
                            operationSQL test = new operationSQL();
                            test.question_e("BIRTHDATE", tBir.getText(), stuIdLogin);
                            tBir.setEditable(false);
                            hint.setText("Modified successfully!");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            hint.setText("Modify failed! Please input valid content.");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            hint.setText("Modify failed! Please input valid content.");
                        }
                    }
                }
            });
            b5.add(bir);
            b5.add(tBir);
            b5.add(bB);
            b5.add(btnB);

            Box b6=Box.createHorizontalBox();
            JLabel gen=new JLabel(" GENDER : ");
            JTextField tGen=new JTextField();
            tGen.setText(new operationSQL().getStuInformation(stuIdLogin,"GENDER"));
            tGen.setEditable(false);
            JButton bG=new JButton("Modify");
            bG.addActionListener(new ModifyLsn(tGen));
            JButton btnG=new JButton("Save");
            btnG.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if ((!tGen.getText().equals("FEMALE"))&&(!tGen.getText().equals("MALE"))) {
                        hint.setText("Error! Please input again!");
                        return;
                    }
                    else {
                        try {
                            operationSQL test = new operationSQL();
                            test.question_e("GENDER", tGen.getText(), stuIdLogin);
                            tGen.setEditable(false);
                            hint.setText("Modified successfully!");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            hint.setText("Modify failed! Please input valid content.");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            hint.setText("Modify failed! Please input valid content.");
                        }
                    }
                }
            });
            b6.add(gen);
            b6.add(tGen);
            b6.add(bG);
            b6.add(btnG);

            b.add(Box.createRigidArea(new Dimension(600,50)));
            b.add(b1);
            b.add(Box.createRigidArea(new Dimension(600,50)));
            b.add(b2);
            b.add(Box.createRigidArea(new Dimension(600,50)));
            b.add(b3);
            b.add(Box.createRigidArea(new Dimension(600,50)));
            b.add(b4);
            b.add(Box.createRigidArea(new Dimension(600,50)));
            b.add(b5);
            b.add(Box.createRigidArea(new Dimension(600,50)));
            b.add(b6);

            b.add(hint);

            vp_4.add(b);
        }
        public class ModifyLsn implements ActionListener{
            JTextField t;
            ModifyLsn(JTextField t){
                this.t=t;
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                t.setEditable(true);
            }
        }
        public class SaveLsn implements ActionListener {
            String attribute,modified_data;
            JTextField t;
            JLabel hint;
            public SaveLsn(String attribute,String modified_data,JLabel hint,JTextField t){
                this.attribute=attribute;
                this.modified_data=modified_data;
                this.hint=hint;
                this.t=t;
            }


            @Override
            public void actionPerformed(ActionEvent e){
                switch (attribute){
                    case "STUDENT_NAME":
                    case "DEPARTMENT":
                    case "ADDRESS":
                        if (modified_data.length()>100 || modified_data.length()==0) {
                            hint.setText("Error! Please input again!");
                            return;
                        }
                        break;
                    case "BIRTHDATE":
                        if (modified_data.length()>10 || modified_data.length()==0) {
                            hint.setText("Error! Please input again!");
                            return;
                        }
                        break;
                    case "GENDER":
                        if ((!modified_data.equals("FEMALE"))&&(!modified_data.equals("MALE"))) {
                            hint.setText("Error! Please input again!");
                            return;
                        }
                        break;
                }
                try {
                    operationSQL test = new operationSQL();
                    test.question_e(attribute, modified_data, stuIdLogin);
                    t.setEditable(false);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    hint.setText("Modify failed! Please input valid content.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    hint.setText("Modify failed! Please input valid content.");
                }
            }
        }

    }



    /*public static void main(String[] args) throws IOException, SQLException {
        Student stu=new Student("50000150","2019/11/23");
    }*/


}