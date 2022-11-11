//package GUI;

//import SQL.operationSQL;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;

public class Administrator {
    public JFrame jf = new JFrame("Administrator");
    public JPanel mp;
    public Administrator() throws IOException, SQLException {
        //set the frame size
        jf.setBounds(10,0,1000,  800);

        //press the button to logout
        //may need further improvement on the look
        Title title = new Title();
        jf.add(title.tp, BorderLayout.NORTH);

        //add main panel
        mp=new MainPanel();
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
            JLabel stu_l = new JLabel("[Administrator]");
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
        gradePanel gp = new gradePanel();
        String[] ca={"ID","Title","Staff","Section"};
        String[] sa={"ID","Name","Department","Address","Birthday","Gender"};
        JPanel cp = new ContentTable("course table:",380,ca);
        JPanel sp = new ContentTable("student table:",480,sa);

        public MainPanel() throws IOException, SQLException {
            this.add(gp.gp, BorderLayout.NORTH);
            this.add(cp, BorderLayout.WEST);
            this.add(sp, BorderLayout.EAST);
        }
    }

    public JButton tbt = new JButton("top 5 students");
    public class gradePanel implements ActionListener{
        public JPanel gp = new JPanel();
        public JTextField txt = new JTextField(40);
        public JButton dbtn = new JButton("Details");

        public gradePanel() {
            gp.setPreferredSize(new Dimension(800, 40));
            JLabel jl = new JLabel("Grade Sheet for : ");

            //this txtfield is for the selected course or student name
            txt.setText("Please type in student ID ");
            txt.setAutoscrolls(true);
            //gp.add(jl);
            //gp.add(txt);
            dbtn.addActionListener(this);
            //gp.add(dbtn);

            Box b1 = Box.createHorizontalBox();
            b1.add(Box.createRigidArea(new Dimension(50,20)));
            b1.add(jl);
            b1.add(txt);
            b1.add(dbtn);
            b1.add(Box.createRigidArea(new Dimension(50,20)));
            b1.add(tbt);
            tbtListenerClass listener_t = new tbtListenerClass();
            tbt.addActionListener(listener_t);
            gp.add(b1);
        }

        private class tbtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
                try {
                    tStu();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //need test whether this is a valid statement
            try {
                if (new operationSQL().getStuInformation(txt.getText(),"STUDENT_NAME")=="") {
                    txt.setText("Please input valid Student ID!");
                    return;
                }
                else {
                    try {
                        changeGrade(txt.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public JFrame tSf;
    public JPanel tp;
    public JTable table1;
    public JTable table2;

    public void tStu() throws IOException, SQLException {
        tSf = new JFrame("List top 5 students");
        tSf.setBounds(100,120,800,600);
        tp = new JPanel();
        tp.setPreferredSize(new Dimension(250,250));

        JLabel mcrbt= new JLabel("Top 5 students that have most courses registered");
        mcrbt.setFont(new Font("",Font.BOLD, 16));
        JLabel hagvbt= new JLabel("Top 5 students with the highest average grade values");
        hagvbt.setFont(new Font("",Font.BOLD, 16));
        setTable1();
        setTable2();

        Box b1 = Box.createVerticalBox();
        b1.add(mcrbt);
        b1.add(new JScrollPane(table1));
        b1.add(Box.createRigidArea(new Dimension(20,20)));
        b1.add(hagvbt);
        b1.add(new JScrollPane(table2));

        tp.add(b1);
        tSf.add(tp);

        tSf.setVisible(true);
    }

    public void setTable1() throws IOException, SQLException {
        table1 = new JTable();
        table1.setPreferredScrollableViewportSize(new Dimension(600,200));
        table1.setBorder(new LineBorder(new Color(0, 0, 0)));
        String[] head=new String[] {
                "STUDENT_ID", "NUMBER_OF_REGISTERED_COURSES",
        };

        /*Object[][] data = {
                new Object[]{"dfs","dfsd","dfsfd",10}
        };*/
        operationSQL test_t1 = new operationSQL();
        Object[][] data = test_t1.question_l();
        DefaultTableModel tableModel=new DefaultTableModel(data,head){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        table1.setModel(tableModel);

        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    public void setTable2() throws IOException, SQLException {
        table2 = new JTable();
        table2.setPreferredScrollableViewportSize(new Dimension(600,200));
        table2.setBorder(new LineBorder(new Color(0, 0, 0)));
        String[] head=new String[] {
                "STUDENT_ID", "AVERAGE_GRADE_VALUES",
        };

        /*Object[][] data = {
                new Object[]{"dfs","dfsd","dfsfd",5}
        };*/
        operationSQL test_t2 = new operationSQL();
        Object[][] data = test_t2.question_m();
        DefaultTableModel tableModel=new DefaultTableModel(data,head){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        table2.setModel(tableModel);

        table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    public void changeGrade(String s) throws IOException, SQLException {
        JFrame jfr=new JFrame("Grade");
        jfr.setBounds(350,200,400,200);

        Box b=Box.createVerticalBox();
        Box b1=Box.createHorizontalBox();
        JLabel jl1=new JLabel("Student ID : "+s);
        jl1.setPreferredSize(new Dimension(200,40));
        jl1.setHorizontalAlignment(JLabel.LEFT);
        b1.add(jl1);
        b.add(Box.createRigidArea(new Dimension(20,20)));
        b.add(b1);
        b.add(Box.createRigidArea(new Dimension(20,20)));

        Box b2=Box.createHorizontalBox();
        JLabel jl2=new JLabel("Course ID : ");
        JComboBox<Object> cb = new JComboBox<Object>();
        cb.addItem("--Please select a course--");
        //add registered course from the database
        Object[][] data=new operationSQL().question_c(s);
        for (int i=0;i<data.length;i++){
            cb.addItem(data[i][0]);
        }
        cb.setPreferredSize(new Dimension(60,40));
        jl2.setPreferredSize(new Dimension(80,40));
        b2.add(jl2);
        b2.add(cb);
        b.add(b2);
        b.add(Box.createRigidArea(new Dimension(20,20)));

        Box b4=Box.createHorizontalBox();
        JLabel jl4=new JLabel("Grade : ");
        JTextField grade=new JTextField("");
        grade.setEditable(false);
        grade.setHorizontalAlignment(JTextField.CENTER);
        jl4.setPreferredSize(new Dimension(50,70));
        b4.createRigidArea(new Dimension(20,20));
        JButton view=new JButton("view");
        view.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    grade.setText(new operationSQL().getGrade(s,(String) cb.getSelectedItem()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JButton btn1=new JButton("Modify");
        btn1.addActionListener(new Modify(grade));
        JButton btn2=new JButton("Save");
        btn2.addActionListener(new Save(grade,s, (String) cb.getSelectedItem()));
        b4.add(jl4);
        b4.add(grade);
        b4.add(view);
        b4.add(btn1);
        b4.add(btn2);
        b.add(b4);
        b.add(Box.createRigidArea(new Dimension(20,20)));

        jfr.add(b);
        jfr.setVisible(true);
        jfr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public class Modify implements ActionListener{
        public JTextField grade;
        public Modify(JTextField grade){
            this.grade=grade;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            grade.setText("Please input 0-100");
            grade.setEditable(true);
        }
    }

    public class Save implements ActionListener{
        public JTextField grade;
        public String studentID,courseID;
        public Save(JTextField grade,String studentID,String courseID){
            this.grade=grade;
            this.courseID=courseID;
            this.studentID=studentID;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int g=Integer.parseInt(grade.getText());
            if(g>=0 && g<=100) {
                grade.setEditable(false);
                try {
                    new operationSQL().question_k(g+"",studentID,courseID);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else grade.setText("Invalid! Please input again!");
        }
    }

    public class ContentTable extends JPanel{
        public String name;
        public JLabel jl;
        public JPanel contentPanel,btnPanel;
        public String[] attributes;
        public JTable table;
        private int width;

        public ContentTable(String name,int width,String[] attributes) throws IOException, SQLException {
            this.name=name;
            this.width=width;
            this.attributes=attributes;
            //set the panel
            this.setPreferredSize(new Dimension(width,600));
            this.setBackground(Color.white);

            //set the label
            this.jl=new JLabel(name);
            jl.setPreferredSize(new Dimension(width,20));
            this.add(jl,BorderLayout.NORTH);

            //generate the default table
            this.buildOrigCP();
            this.add(contentPanel);

            //generate the button panel
            if(name.equals("course table:")){
                btnPanel = new BtnsCourse(table);
            }
            if(name.equals("student table:")){
                btnPanel = new BtnsStu(table);
            }
            this.add(btnPanel);
        }

        //need implementation:
        //generate all the courses and students
        public void buildOrigCP() throws IOException, SQLException {
            //generate default content list
            operationSQL test_list = new operationSQL();
            contentPanel=new JPanel();
            contentPanel.setPreferredSize(new Dimension(width,450));
            contentPanel.setBackground(Color.white);

            //generate a table that contains all the courses and students
            table=new JTable();

            if(name.equals("course table:")) {
                table.setPreferredScrollableViewportSize(new Dimension(width-20,400));
                /*Object[][] data = {
                        new Object[]{"CS2172", "Fundamentals of Computing", "Ka Chun K. LEE", "C01"},
                        new Object[]{"CS2468", "Data Structures and Data Mgt", "Chung Sing V. LEE", "C01"}
                };*/
                Object[][] data = test_list.question_fCourse();
                DefaultTableModel tableModel=new DefaultTableModel(data,attributes){
                    public boolean isCellEditable(int row, int column)
                    {
                        return false;
                    }
                };
                table.setModel(tableModel);

                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.getColumnModel().getColumn(0).setPreferredWidth(50);
                table.getColumnModel().getColumn(1).setPreferredWidth(180);
                table.getColumnModel().getColumn(2).setPreferredWidth(130);
                table.getColumnModel().getColumn(3).setPreferredWidth(50);
            }
            if(name.equals("student table:")){
                table.setPreferredScrollableViewportSize(new Dimension(width - 20, 360));
                /*Object[][] data ={
                        new Object[]{"50000001", "Anita Faust", "CS", "Department of Computer Science, City University of Hong Kong", "1988/4/22", "FEMALE"},
                        new Object[]{"50000002", "Annemarie Sommer", "CS", "Department of Computer Science, City University of Hong Kong", "1987/9/15", "FEMALE"}
                };*/
                Object[][] data = test_list.question_fStu();
                contentPanel.add(contentPanel.add(new ScrPanel(table).scrPanel));
                DefaultTableModel tableModel = new DefaultTableModel(data, attributes) {
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                table.setModel(tableModel);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.getColumnModel().getColumn(0).setPreferredWidth(80);
                table.getColumnModel().getColumn(1).setPreferredWidth(160);
                table.getColumnModel().getColumn(2).setPreferredWidth(100);
                table.getColumnModel().getColumn(3).setPreferredWidth(400);
                table.getColumnModel().getColumn(4).setPreferredWidth(80);
                table.getColumnModel().getColumn(5).setPreferredWidth(80);
            }
            contentPanel.add(new JScrollPane(table));
        }

        public class ScrPanel implements ActionListener{
            public JPanel scrPanel;
            public JComboBox cmb;
            public JButton search;
            public JTextField t;
            ScrPanel(JTable table) throws IOException, SQLException {
                //generate scrollPanel
                this.scrPanel=new JPanel();
                scrPanel.setBackground(Color.white);
                /*JComboBox<Object> cmb = new JComboBox<Object>();
                cmb.addItem("--Please select a department--");
                //list all the department from the database
                String[] allDept=new operationSQL().getAllDept();
                for (int i=0;i<allDept.length;i++){
                    cmb.addItem(allDept[i]);
                }
                scrPanel.add(cmb);*/
                JLabel d=new JLabel("Type in DEPT:");
                t=new JTextField("Type here");
                t.setPreferredSize(new Dimension(200,20));
                search=new JButton("Search");
                search.addActionListener(this);
                JButton defaultBtn=new JButton("Default");
                defaultBtn.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Object[][] data=new operationSQL().question_fStu();
                            for (int i=0;i<table.getRowCount();i++){
                                for (int j=0;j<6;j++) table.setValueAt(data[i][j],i,j);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                scrPanel.add(d);
                scrPanel.add(t);
                scrPanel.add(search);
                scrPanel.add(defaultBtn);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Object[][] data= new Object[0][];
                try {
                    if(!Arrays.asList(new operationSQL().getAllDept()).contains(t.getText())) t.setText("Please input a valid dept!");
                    else data = new operationSQL().question_g(t.getText());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                for (int i=0;i<table.getRowCount();i++){
                    if (i<data.length) for (int j=0;j<6;j++) table.setValueAt(data[i][j],i,j);
                    else  for(int j=0;j<6;j++) table.setValueAt("",i,j);
                }
            }
        }
    }

    public class BtnsCourse extends JPanel {
        JTable tableC;
        JButton add = new JButton("ADD");
        JButton modify = new JButton("MODIFY");
        JButton delete = new JButton("DELETE");
        JLabel hintC = new JLabel(" ");
        JFrame addCf;
        JTextField cidtf;
        JTextField cttf;
        JTextField sntf;
        JTextField stf;
        JTextField t;

        public BtnsCourse(JTable tableC) {
            this.tableC = tableC;
            this.setBackground(Color.white);
            Box bv = Box.createVerticalBox();
            Box bh = Box.createHorizontalBox();

            bh.add(Box.createHorizontalGlue());
            bh.add(add);
            abtListenerClass listener6 = new abtListenerClass();
            add.addActionListener(listener6);
            bh.add(Box.createHorizontalGlue());
            modify.addActionListener(new ModifyCLsn());
            bh.add(modify);
            bh.add(Box.createHorizontalGlue());
            bh.add(delete);
            dbtListenerClass listener7 = new dbtListenerClass();
            delete.addActionListener(listener7);
            bh.add(Box.createHorizontalGlue());
            bv.add(bh);

            bh = Box.createHorizontalBox();
            bh.add(Box.createHorizontalGlue());
            bh.add(hintC);
            bh.add(Box.createHorizontalGlue());
            bv.add(bh);

            bh=Box.createHorizontalBox();
            bh.add(Box.createHorizontalGlue());
            JLabel l=new JLabel("Modify selected cell value to"+" : ");
            this.t=new JTextField("");
            bh.add(l);
            bh.add(t);
            bh.add(Box.createHorizontalGlue());
            bv.add(bh);

            this.add(bv);
        }

        private class ModifyCLsn implements ActionListener{
            String[] a={"COURSE_ID","COURSE_TITLE","STAFF_NAME","SECTION"};
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableC.getSelectedColumn()==0){
                    hintC.setText("Course ID cannot be modified!");
                    return;
                }
				if (tableC.getSelectedColumn()==3 && t.getText().length()>3){
                    hintC.setText("Too Long! Please input again!");
                    return;
                }
                if (t.getText()=="") return;
                else{
                    int selectedRow= tableC.getSelectedRow(),selectedColmn=tableC.getSelectedColumn();
                    hintC.setText("Please type in the value and click Modify Button again:  ");
                    tableC.getModel().setValueAt(t.getText(),selectedRow,selectedColmn);
                    try {
                        new operationSQL().question_iCourse((String)tableC.getValueAt(tableC.getSelectedRow(),0),
                                a[tableC.getSelectedColumn()],t.getText());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    return;
                }
            }
        }

        private class abtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
                addCouse();
            }
        }

        private class dbtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
                try {
                    deleteCouse();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void addCouse(){
            addCf = new JFrame("Add Course");
            addCf.setBounds(100,250,300,300);
            JPanel addp = new JPanel();
            addp.setBackground(Color.LIGHT_GRAY);
            addp.setPreferredSize(new Dimension(250,250));
            addp.setLayout(new GridLayout(5,2,10,20));

            JLabel cidl = new JLabel("COURSE_ID: ");
            JLabel ctl = new JLabel("COURSE_TITLE: ");
            JLabel snl = new JLabel("STAFF_NAME: ");
            JLabel sl = new JLabel("SECTION: ");

            cidtf = new JTextField(6);
            cttf = new JTextField(100);
            sntf = new JTextField(100);
            stf = new JTextField(3);

            JButton cfbt = new JButton("Confirm");
            cfbtListenerClass listener7 = new cfbtListenerClass();
            cfbt.addActionListener(listener7);
            JButton ccbt = new JButton("Cancel");
            ccbtListenerClass listener8 = new ccbtListenerClass();
            ccbt.addActionListener(listener8);

            addp.add(cidl);
            addp.add(cidtf);
            addp.add(ctl);
            addp.add(cttf);
            addp.add(snl);
            addp.add(sntf);
            addp.add(sl);
            addp.add(stf);
            addp.add(cfbt);
            addp.add(ccbt);
            addCf.add(addp, BorderLayout.CENTER);
            addCf.setVisible(true);
        }

        private class cfbtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
                try {
                    addToSql();

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private class ccbtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
                addCf.setVisible(false);
            }
        }

        private void addToSql() throws IOException, SQLException {
            Object[] data = {
                    cidtf.getText(),
                    cttf.getText(),
                    sntf.getText(),
                    stf.getText()
            };
            String s1 = cidtf.getText();
            String s2 = cttf.getText();
            String s3 = sntf.getText();
            String s4 = stf.getText();

            if(s1.length()>6 || s1.length()==0) cidtf.setText("Error! Please re-input.");
            if(s2.length()>100 || s2.length()==0) cttf.setText("Error! Please re-input.");
            if(s3.length()>100 || s3.length()==0) sntf.setText("Error! Please re-input.");
            if(s4.length()>3 || s4.length()==0) stf.setText("Error! Please re-input.");
            if(s1.length()<=6 && s1.length()>0 && s2.length()<=100 && s2.length()>0 && s3.length()<=100 && s3.length()>0 && s4.length()<=3 && s4.length()>0){
                System.out.println(data[0]+","+data[1]+","+data[2]+","+data[3]);

                operationSQL test_addCourse = new operationSQL();
                test_addCourse.question_hCourse(s1,s2,s3,s4);
                resetCourseTable();
                jf.setVisible(true);
                hintC.setText("Add Successful");
                addCf.setVisible(false);
            }
        }

        private void deleteCouse() throws IOException, SQLException {
            int count=tableC.getSelectedRow();
            if(count!=-1){
                String getCourseId= tableC.getValueAt(count, 0).toString();
                operationSQL test_deleteC = new operationSQL();
                test_deleteC.question_jCourse(getCourseId);
                resetCourseTable();
                jf.setVisible(true);
                hintC.setText("Delete Successful");
                count=-1;
            }
            else{
                hintC.setText("Delete Error. Please choose a course first.");
            }
        }

        public void resetCourseTable() throws IOException, SQLException {
            String[] ca={"ID","Title","Staff","Section"};
            /*Object[][] data_list = {
                    new Object[]{"CS2172", "Fundamentals of Computing", "Ka Chun K. LEE", "C01"},
                    new Object[]{"CS2468", "Data Structures and Data Mgt", "Chung Sing V. LEE", "C01"}
            };*/
            operationSQL test_list = new operationSQL();
            Object[][] data_list = test_list.question_fCourse();
            DefaultTableModel tableModel=new DefaultTableModel(data_list,ca){
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            tableC.setModel(tableModel);
            tableC.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tableC.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableC.getColumnModel().getColumn(1).setPreferredWidth(180);
            tableC.getColumnModel().getColumn(2).setPreferredWidth(130);
            tableC.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

    }

    public class BtnsStu extends JPanel {
        JButton add = new JButton("ADD");
        JButton modify = new JButton("MODIFY");
        JButton delete = new JButton("DELETE");
        JLabel hintS = new JLabel("");
        JFrame addSf;
        JTextField sidtf;
        JTextField sntf;
        JTextField dtf;
        JTextField atf;
        JTextField btf;
        JTextField gtf;
        JTable tableS;
        JTextField t;

        public BtnsStu(JTable tableS) {
            this.tableS = tableS;
            this.setBackground(Color.white);

            Box bv = Box.createVerticalBox();
            Box bh = Box.createHorizontalBox();

            bh.add(Box.createHorizontalGlue());
            bh.add(add);
            abtListenerClass listener6 = new abtListenerClass();
            add.addActionListener(listener6);
            bh.add(Box.createHorizontalGlue());
            modify.addActionListener(new ModifySLsn());
            bh.add(modify);
            bh.add(Box.createHorizontalGlue());
            bh.add(delete);
            dbtListenerClass listener7 = new dbtListenerClass();
            delete.addActionListener(listener7);
            bh.add(Box.createHorizontalGlue());
            bv.add(bh);

            bh = Box.createHorizontalBox();
            bh.add(Box.createHorizontalGlue());
            bh.add(hintS);
            bh.add(Box.createHorizontalGlue());
            bv.add(bh);
            bh.add(Box.createHorizontalGlue());
            bh.add(Box.createRigidArea(new Dimension(bh.getWidth(),20)));

            bh=Box.createHorizontalBox();
            bh.add(Box.createHorizontalGlue());
            JLabel l=new JLabel("Modify selected cell value to"+" : ");
            this.t=new JTextField("");
            bh.add(l);
            bh.add(t);
            bh.add(Box.createHorizontalGlue());
            bv.add(bh);
            this.add(bv);
            /*dbtListenerClass listener7 = new dbtListenerClass();
            delete.addActionListener(listener7);*/
        }

        private class ModifySLsn implements ActionListener{
            String[] a={"STUDENT_ID","STUDENT_NAME","DEPARTMENT","ADDRESS","BIRTHDATE","GENDER"};
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableS.getSelectedColumn()==0){
                    hintS.setText("Student ID cannot be modified!");
                    return;
                }
				if ((tableS.getSelectedColumn()==4 && t.getText().length()>10) || (tableS.getSelectedColumn()==5 && t.getText().length()>6)){
                    hintS.setText("Too Long! Please input again!");
                    return;
                }
                if (t.getText()=="") return;
                else{
                    int selectedRow= tableS.getSelectedRow(),selectedColmn=tableS.getSelectedColumn();
                    hintS.setText("Please type in the value and click Modify Button again:  ");
                    tableS.getModel().setValueAt(t.getText(),selectedRow,selectedColmn);
                    try {
                        new operationSQL().question_iStu(a[tableS.getSelectedColumn()],t.getText(),
                                (String)tableS.getValueAt(tableS.getSelectedRow(),0));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    return;
                }
            }
        }

        private class abtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
                addStu();
            }
        }

        private class dbtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
                try {
                    deleteStudent();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void addStu(){
            addSf = new JFrame("Add Student");
            addSf.setBounds(500,250,400,300);
            JPanel addp = new JPanel();
            addp.setBackground(Color.LIGHT_GRAY);
            addp.setPreferredSize(new Dimension(350,350));
            addp.setLayout(new GridLayout(7,2,10,20));

            JLabel sidl = new JLabel("STUDENT_ID: ");
            JLabel snl = new JLabel("STUDENT_NAME: ");
            JLabel dl = new JLabel("DEPARTMENT: ");
            JLabel al = new JLabel("ADDRESS: ");
            JLabel bl = new JLabel("BIRTHDATE(YYYY/MM/DD): ");
            JLabel gl = new JLabel("GENDER(MALE/FEMALE): ");

            sidtf = new JTextField(8);
            sntf = new JTextField(100);
            dtf = new JTextField(100);
            atf = new JTextField(255);
            btf = new JTextField(10);
            gtf = new JTextField(6);

            JButton cfbt = new JButton("Confirm");
            cfbtListenerClass listener7 = new cfbtListenerClass();
            cfbt.addActionListener(listener7);
            JButton ccbt = new JButton("Cancel");
            ccbtListenerClass listener8 = new ccbtListenerClass();
            ccbt.addActionListener(listener8);

            addp.add(sidl);
            addp.add(sidtf);
            addp.add(snl);
            addp.add(sntf);
            addp.add(dl);
            addp.add(dtf);
            addp.add(al);
            addp.add(atf);
            addp.add(bl);
            addp.add(btf);
            addp.add(gl);
            addp.add(gtf);
            addp.add(cfbt);
            addp.add(ccbt);
            addSf.add(addp, BorderLayout.CENTER);
            addSf.setVisible(true);
        }

        private class cfbtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
                try {
                    addToSql();

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private class ccbtListenerClass implements ActionListener {
            public void actionPerformed(ActionEvent e){
                addSf.setVisible(false);
            }
        }

        private void addToSql() throws IOException, SQLException {
            Object[] data = {
                    sidtf.getText(),
                    sntf.getText(),
                    dtf.getText(),
                    atf.getText(),
                    btf.getText(),
                    gtf.getText()
            };
            String s1 = sidtf.getText();
            String s2 = sntf.getText();
            String s3 = dtf.getText();
            String s4 = atf.getText();
            String s5 = btf.getText();
            String s6 = gtf.getText();

            if(s1.length()>8 || s1.length()==0) sidtf.setText("Error! Please re-input.");
            if(s2.length()>100 || s2.length()==0) sntf.setText("Error! Please re-input.");
            if(s3.length()>100 || s3.length()==0) dtf.setText("Error! Please re-input.");
            if(s4.length()>255 || s4.length()==0) atf.setText("Error! Please re-input.");
            if(s5.length()>10 || s5.length()==0) btf.setText("Error! Please re-input.");
            if(!s6.equals("FEMALE") && !s6.equals("MALE")) gtf.setText("Error! Please re-input.");
            if(s1.length()<=8 && s1.length()>0 && s2.length()<=100 && s2.length()>0 && s3.length()<=100 && s3.length()>0 && s4.length()<=255 && s4.length()>0 && s5.length()<=10 && s5.length()>0 && (s6.equals("FEMALE")||s6.equals("MALE"))){
                System.out.println(data[0]+","+data[1]+","+data[2]+","+data[3]+","+data[4]+","+data[5]);
                operationSQL test_addStu = new operationSQL();
                test_addStu.question_hStu(s1,s2,s3,s4,s5,s6);
                resetStudentTable();
                jf.setVisible(true);
                hintS.setText("Add Successful");
                addSf.setVisible(false);
            }

        }

        private void deleteStudent() throws IOException, SQLException {
            int count=tableS.getSelectedRow();
            if(count!=-1){
                String getStudentId= tableS.getValueAt(count, 0).toString();
                operationSQL test_deleteS = new operationSQL();
                test_deleteS.question_jStu(getStudentId);
                System.out.println(getStudentId);
                resetStudentTable();
                jf.setVisible(true);
                hintS.setText("Delete Successful");
                count=-1;
            }
            else{
                hintS.setText("Delete Error. Please choose a student first.");
            }
        }

        public void resetStudentTable() throws IOException, SQLException {
            String[] sa={"ID","Name","Department","Address","Birthday","Gender"};
            /*Object[][] data_list ={
                    new Object[]{"50000001", "Anita Faust", "CS", "Department of Computer Science, City University of Hong Kong", "1988/4/22", "FEMALE"},
                    new Object[]{"50000002", "Annemarie Sommer", "CS", "Department of Computer Science, City University of Hong Kong", "1987/9/15", "FEMALE"}
            };*/
            operationSQL test_list = new operationSQL();
            Object[][] data_list = test_list.question_fStu();
            DefaultTableModel tableModel=new DefaultTableModel(data_list,sa){
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            tableS.setModel(tableModel);
            tableS.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tableS.getColumnModel().getColumn(0).setPreferredWidth(80);
            tableS.getColumnModel().getColumn(1).setPreferredWidth(160);
            tableS.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableS.getColumnModel().getColumn(3).setPreferredWidth(400);
            tableS.getColumnModel().getColumn(4).setPreferredWidth(80);
            tableS.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

    }

    /*public static void main(String[] args) throws IOException, SQLException {
        Administrator ad=new Administrator();
    }*/

}