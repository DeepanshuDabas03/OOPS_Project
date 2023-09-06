import com.sun.javafx.animation.TickCalculation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
class company {
    private String name;
    private int ctc;
    private Date codate;
    private Scanner myscan = new Scanner(System.in);
    private SimpleDateFormat formatter11 = new SimpleDateFormat("MMM dd yyyy HH:mm");
    private ArrayList<student> appliedstudents=new ArrayList<student>();

    public ArrayList<student> getOfferedstudents() {
        selectstudent();
        return offeredstudents;
    }

    private ArrayList<student> offeredstudents=new ArrayList<student>();

    public ArrayList<student> getAppliedstudents() {
        return appliedstudents;
    }
    private void selectstudent(){
        if(appliedstudents.size()==1){
            offeredstudents.add(appliedstudents.get(0));
            return;
        }
        for(student i:appliedstudents){
            int rand = (int) (Math.random() * 2+1);
            if(rand==1){
                offeredstudents.add(i);
            }
        }
        if(offeredstudents.size()==0){
            offeredstudents.add(appliedstudents.get(0));
            return;
        }
    }

    public company(String name, String role, int ctc, double cgpa) {
        this.name = name;
        this.ctc = ctc;
        this.cgpa = cgpa;
        this.role = role;
    }

    private double cgpa;
    private String role;

    public String getName() {
        return name;
    }

    public int getCtc() {
        return ctc;
    }

    public String getRole() {
        return role;
    }

    public double cgpacriteria() {
        return cgpa;
    }

    public void call(iiitd_placement_cell cell, company com) throws ParseException {
        while (true) {
            System.out.println("Welcome " + this.name);
            System.out.println("1) Update Role\n" + "2) Update Package\n" + "3) Update CGPA criteria\n" + "4) Register To Institute Drive\n" + "5) Back");
            int var = this.myscan.nextInt();
            if (var == 1) {
                myscan.nextLine();
                this.role = this.myscan.nextLine();
            } else if (var == 2) {
                this.ctc = this.myscan.nextInt();
            } else if (var == 3) {
                this.cgpa = this.myscan.nextDouble();
            } else if (var == 4) {
                System.out.print("Please enter date in format (MMM dd yyyy HH:mm):");
                this.myscan.nextLine();
                String s1=this.myscan.nextLine();
                this.codate=this.formatter11.parse(s1);
                if (this.codate.compareTo(cell.getCdate()) >= 0 && this.codate.compareTo(cell.getCdate1()) <= 0) {
                    cell.addcompany(com);
                    System.out.println("Registered!!!");
                }
            } else if (var == 5) {
                return;
            }
        }
    }

    public void addstudent(student stu) {
        this.appliedstudents.add(stu);
    }
}

class data{
    public void setB(boolean b) {
        this.b = b;
    }

    private company c;
    private boolean b=false;

    public company getC() {
        return c;
    }

    public boolean isB() {
        return b;
    }

    public data(company c) {
        this.c = c;
    }
}

class iiitd_placement_cell{
    private Date sdate;
    private Date sdate1;
    private Date cdate;
    private Date cdate1;
    private ArrayList<student> registered_student=new ArrayList<student>();
    private ArrayList<company> registered_company=new ArrayList<company>();
       private SimpleDateFormat formatter1=new SimpleDateFormat("MMM dd yyyy HH:mm");
    private Scanner input=new Scanner(System.in);
    public java.util.Date getSdate() {
        return sdate;
    }
    public java.util.Date getSdate1() {
        return sdate1;
    }
    public java.util.Date getCdate() {
        return cdate;
    }
    public java.util.Date getCdate1() {
        return cdate1;
    }
    public void addstudent(student stu) {
        this.registered_student.add(stu);
    }
    private void Open_Student_Registrations() throws ParseException {
        System.out.println("Fill in the details(Format:-MMM dd yyyy HH:mm):-\n" + "1) Set the Opening time for Student registrations\n" + "2) Set the Ending time for Student registrations.");
        input.nextLine();
        String s1=this.input.nextLine();
        String s2=this.input.nextLine();
        this.sdate=this.formatter1.parse(s1);
        this.sdate1= this.formatter1.parse(s2);
        System.out.println("Institute is open for student-registrations for the placement drive");
    }
    private void Open_Company_Registrations() throws ParseException {
        System.out.println("Fill in the details(Format:-MMM dd yyyy HH:mm):-\n" + "1) Set the Opening time for company registrations\n2) Set the Closing time for company registrations");
        input.nextLine();
        String s1=this.input.nextLine();
        String s2=this.input.nextLine();
        this.cdate=this.formatter1.parse(s1);
        this.cdate1= this.formatter1.parse(s2);
        System.out.println("Institute is open for company registrations for the placements");
    }

     public void updatecgpa(double cgpa, double d11, String name){
        for(student i:this.registered_student){
            if(i.getName().equals(name) && i.getCGPA()==cgpa){
                i.setCGPA(d11);
            }
        }
    }

    public void enter() throws ParseException {
        while(true){
            System.out.println("Welcome IIITD Placement Cell\n" +
                    "1) Open Student Registrations\n" +
                    "2) Open Company Registrations\n" +
                    "3) Get Number of Student Registrations\n" +
                    "4) Get Number of Company Registrations\n" +
                    "5) Get Number of Offered/Unoffered/Blocked Students\n" +
                    "6) Get Student Details\n" +
                    "7) Get Company Details\n" +
                    "8) Get Average Package\n" +
                    "9) Get Company Process Results\n" +
                    "10) Back");
            int a=this.input.nextInt();
            if(a==1){
                Open_Student_Registrations();
            }
            else if(a==2){
                Open_Company_Registrations();
            }
            else if(a==3){
                System.out.println("The number of students registered for the placement drive:"+this.registered_student.size());
            }
            else if(a==4){
                System.out.println("The number of companies registered for the placement drive:"+this.registered_company.size());
            }
            else if(a==5){
                int offer=0;
                int notoffer=0;
                int block=0;
                int placed=0;
                for(student i:registered_student){
                    i.check();
                    if(i.getIsplaced()==1) {
                        System.out.println(i.getName()+" is placed in "+i.getComp().getName());
                    }
                    if(i.getIsplaced()==2){
                        System.out.println(i.getName()+" is blocked");
                    }
                    else{
                        if(i.getOffered().size()>=1){
                            System.out.println(i.getName()+" is offered in "+i.getComp().getName());
                        }
                        else{
                            System.out.println(i.getName()+" isn't offered");
                        }
                    }
                }
                System.out.println("Total:\nPlaced:"+placed+"\nUnplaced:"+notoffer+"\nBlocked:"+block+"\nOffered:"+offer);
            }
            else if(a==6){
                input.nextLine();
                String s1=this.input.nextLine();
                int roll=this.input.nextInt();
                for(company i:this.registered_company){
                    ArrayList<student> n=i.getAppliedstudents();
                    int flag=0;
                    for(student j:n){
                        if(j.getRoll_No()==roll && j.getName().equals(s1)){
                            System.out.println(s1+" applied for "+i.getName());
                            flag=1;
                            break;
                        }
                    }
                    if(flag==0){
                        System.out.println(s1+" didn't apply for "+i.getName());
                    }
                    if(flag==1){
                        ArrayList<student> k=i.getOfferedstudents();
                        for(student j:k){
                            if(j.getRoll_No()==roll && j.getName().equals(s1)){
                                System.out.println(s1+" is offered package in "+i.getName());
                                flag=0;
                                break;
                            }
                        }
                        if(flag==0){
                            System.out.println(s1+"isn't offered package in "+i.getName());
                        }
                    }
                }
            }
            else if(a==7){
                input.nextLine();
                String s1=this.input.nextLine();
                company cope=null;
                for(company i:registered_company){
                    if(i.getName().equals(s1)){
                        cope=i;
                        break;
                    }
                }
                System.out.println("Details of company:\nName:"+cope.getName()+"\nRole Offered:"+cope.getRole()+"\nPackage:"+cope.getCtc()+"\nCGPA Criteria"+cope.cgpacriteria());
                ArrayList<student> n=cope.getOfferedstudents();
                System.out.println("The following students are offered package:");
                int num=1;
                for(student i:n){
                    System.out.println(num+") Name:"+i.getName()+"\nRoll No."+i.getRoll_No());
                    num++;
                }
            }
            else if(a==8){
                double average=0;
                int total=0;
                for(company i:registered_company){
                    average+=(i.getCtc())*i.getOfferedstudents().size();
                    total+=i.getOfferedstudents().size();
                }
                average/=total;
                System.out.println("Average package offered:"+average);
            }
            else if(a==9){
                input.nextLine();
                String s1=this.input.nextLine();
                company cope=null;
                for(company i:registered_company){
                    if(i.getName().equals(s1)){
                        cope=i;
                        break;
                    }
                }
                ArrayList<student> n=cope.getOfferedstudents();
                System.out.println("The following students are offered package:");
                int num=1;
                for(student i:n){
                    System.out.println(num+") Name:"+i.getName()+"\nRoll No."+i.getRoll_No()+"\nCGPA:"+i.getCGPA()+"\nBranch:"+i.getBranch());
                    num++;
                }
            }

            else if(a==10){
                return;
            }
        }
    }

    public void addcompany(company com) {
        this.registered_company.add(com);
    }
}
class student{
    private String name;
    private int Roll_No;
    private double CGPA;
    private String Branch;

    public int getIsplaced() {
        return isplaced;
    }

    private ArrayList<data> d=new ArrayList<data>();
    private ArrayList<company> reject=new ArrayList<company>();
    private ArrayList<company> offered=new ArrayList<company>();
    private int isplaced=0;
    private company comp;
     private SimpleDateFormat formatter1=new SimpleDateFormat("MMM dd yyyy HH:mm");
    private Date date;
    private Scanner myscan=new Scanner(System.in);
    public void check(){
        if(reject.size()!=0)
        {
            if(offered.size()==0){
                this.isplaced=2;
            }
        }
    }
    public student(java.lang.String name, int roll_No, double CGPA, String Branch,ArrayList<company> cp) {
        this.name = name;
        this.Roll_No = roll_No;
        this.CGPA = CGPA;
        this.Branch = Branch;
        for(company i:cp){
            if(i.cgpacriteria()<=this.CGPA){
                data pp=new data(i);
                this.d.add(pp);
            }
        }
    }
    public String getName() {
        return name;
    }

    public int getRoll_No() {
        return Roll_No;
    }

    public double getCGPA() {
        return CGPA;
    }

    public void setCGPA(double CGPA) {
        this.CGPA = CGPA;
    }

    private void Get_All_available_companies(student stu, iiitd_placement_cell cell){
        if(this.isplaced==2){
            System.out.println("Unavailable");
            return;
        }
        System.out.println("List of All available companies is as follows:");
        int num=1;
        for(data i:this.d){
            company j=i.getC();
            if(!i.isB()){
                System.out.println(num+") CompanyName:"+j.getName()+"\n"+"   Company Role Offering:"+j.getRole()+"\n"+"   Company Package:"+j.getCtc()+" LPA\n"+"Company CGPA criteria:"+j.cgpacriteria());
                num++;
            }
        }
    }
    private void Register_For_Placement_Drive(student stu,iiitd_placement_cell cell) throws ParseException {
        System.out.print("Please enter date and time in format MMM dd yyyy HH:mm(without anything extra): ");
        myscan.nextLine();
        String input=this.myscan.nextLine();
        this.date=this.formatter1.parse(input);
        if(this.date.compareTo(cell.getSdate())>=0 && this.date.compareTo(cell.getSdate1())<=0){
            System.out.println(this.name+" Registered for the Placement Drive at IIITD!!!!\n"+"Your details are:\n"+"Name: "+this.name+"\n"+"RollNo:"+this.Roll_No+"\n"+"CGPA:"+this.CGPA+"\n"+"Branch:"+this.Branch+"\n");
            cell.addstudent(stu);
        }
        else{
            System.out.println("Sorry "+this.name+" Registrations aren't open currently");
        }
    }
    public void addoffer(company cp){
        this.offered.add(cp);
    }

    public String getBranch() {
        return Branch;
    }

    public company getComp() {
        return comp;
    }

    public  void getmax(){
        if(offered.size()==0){
            this.isplaced=0;
            return;
        }

        company my=offered.get(0);
        for (company i:offered){
            if(i.getCtc()> my.getCtc()){
                my=i;
            }
        }
        this.comp=my;
    }
    private void Register_For_Company(student stu,iiitd_placement_cell cell){
        myscan.nextLine();
        String s1=this.myscan.nextLine();
        for(data i:d){
            company j=i.getC();
            if(j.getName().equals(s1)){
                if(isplaced==1 &&j.getCtc()>=3*this.comp.getCtc()){
                    System.out.println("Successfully Registered for "+j.getRole()+" Role at "+j.getName()+"!!!!");
                    i.setB(true);
                    j.addstudent(stu);
                    return;
                }
                System.out.println("Successfully Registered for "+j.getRole()+" Role at "+j.getName()+"!!!!");
                i.setB(true);
                j.addstudent(stu);
            }
        }
    }
    private void Get_Current_Status(){
        check();
        if(this.isplaced==2){
            System.out.println("Sorry,You have been blocked from placement drive");
        }
        if(this.offered.size()==0){
            System.out.println("You haven't been offered");
        }
        if(this.offered.size()>=1){
            getmax();
            System.out.println("You have been offered by "+this.comp.getName()+"!! Please accept the offer");
        }

    }
    private void Update_CGPA(iiitd_placement_cell cell,student stu){
        double d11=this.myscan.nextDouble();
        cell.updatecgpa(this.CGPA,d11,this.name);
    }
    private void Accept_offer(iiitd_placement_cell cell,student stu){
        this.isplaced=1;
    }
    private void Reject_offer(iiitd_placement_cell cell,student stu){
        reject.add(comp);
        offered.remove(comp);
        check();
    }

    public ArrayList<company> getOffered() {
        return offered;
    }

    public void call(student stu, iiitd_placement_cell cell) throws ParseException {

        while(true){
            System.out.println("Welcome "+this.name+" !!"+"\n" + "1) Register For Placement Drive\n" + "2) Register For Company\n" + "3) Get All available companies\n" + "4) Get Current Status\n" + "5) Update CGPA\n" + "6) Accept offer\n" + "7) Reject offer\n" + "8) Back");
            int var=this.myscan.nextInt();
            if(var==8){
                return;
            }
            else if(var==1){
                Register_For_Placement_Drive(stu,cell);
            }
            else if(var==2){
                Register_For_Company(stu,cell);
            }
            else if(var==3){
                Get_All_available_companies(stu,cell);
            }
            else if(var==4){
                Get_Current_Status();
            }
            else if(var==5){
                Update_CGPA(cell,stu);
            }
            else if (var==6) {
                Accept_offer(cell,stu);
            }
            else if(var==7){
                Reject_offer(cell,stu);
            }
        }
    }
}
class futurebuilder{
    private ArrayList<student> Students=new ArrayList<student>();
    private ArrayList<company> Company=new ArrayList<company>();
    private iiitd_placement_cell cell=new iiitd_placement_cell();
    private  Scanner inp=new Scanner(System.in);
    public void enter() throws ParseException {
        while(true){
            System.out.println("Choose the mode you want to Enter in:-\n1) Enter as Student Mode\n2) Enter as Company Mode\n3) Enter as Placement Cell Mode\n4) Return To Main Application");
            int a=inp.nextInt();
            if(a==4){
                return;
            }
            else if(a==1){
                while(true){
                    System.out.println("Choose the Student Query to perform-\n" + "1) Enter as a Student(Give Student Name, and Roll No.)\n" + "\n" + "2) Add students\n" + "3) Back");
                    int c=this.inp.nextInt();
                    if(c==1){
                        String s=this.inp.next();
                        String s1=this.inp.next();
                        String sn=s+" "+s1;
                        int roll_no=this.inp.nextInt();
                        student stu=null;
                        for(student i:this.Students){
                            if(i.getName().equals(sn) && i.getRoll_No()==roll_no)
                            {
                                stu=i;
                                break;
                            }
                        }
                        stu.call(stu,cell);
                    }
                    else if(c==2){
                        System.out.println("Number of students to add");
                        int d=this.inp.nextInt();
                        System.out.println("Please add students Name, Roll No, CGPA, Branch(in order):");
                        inp.nextLine();
                        for(int i=0;i<d;i++){
                            String name=this.inp.nextLine();
                            int roll_no=this.inp.nextInt();
                            double cgpa=this.inp.nextDouble();
                            inp.nextLine();
                            String branch=this.inp.nextLine();
                            student s1=new student(name,roll_no,cgpa,branch,Company);
                            this.Students.add(s1);
                            System.out.println();
                        }
                    }
                    else{
                        break;
                    }
                }
            }
            else if(a==2) {
                while (true) {
                    System.out.println("Choose the Company Query to perform-\n1) Add Company and Details\n2) Choose Company\n3) Get Available Companies\n4) Back\n");
                    int var = this.inp.nextInt();
                    if (var == 1) {
                        inp.nextLine();
                        String s11=inp.nextLine();
                        String s12=inp.nextLine();
                        int ctc=inp.nextInt();
                        double cgpa= inp.nextDouble();
                        company cop=new company(s11,s12,ctc,cgpa);
                        Company.add(cop);
                    }
                    else if (var == 2) {
                        System.out.println("Choose To enter into mode of Available Companies:");
                        int num=1;
                        for(company i:Company){
                            System.out.println(num+") "+i.getName());
                        }
                        int inpu=this.inp.nextInt();
                        company c=Company.get(inpu-1);
                        c.call(cell,c);
                    }
                    else if (var == 3) {
                        int num=1;
                        for(company i:Company){
                            System.out.println(num+") "+i.getName());
                            num++;
                        }
                    }
                    else if (var == 4) {
                        break;
                    }
                }
            }
            else if(a==3){
                cell.enter();
            }
        }
    }
}

public class deepanshu_2021249 {
    public static void main(String[] args) throws ParseException {
        Scanner input=new Scanner(System.in);
        while(true) {
            System.out.println("Welcome to FutureBuilder:\n1) Enter the Application\n2)Exit the Application");
            int welcome = input.nextInt();
            if (welcome == 1) {
                futurebuilder p = new futurebuilder();
                p.enter();
            }
            if(welcome==2){
                break;
            }
        }
    }
}
