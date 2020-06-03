import java.io.*;
import java.sql.*;
import org.sqlite.SQLiteConfig;
import java.util.*;
import java.util.Date;
import java.time.LocalDate;
import java.text.SimpleDateFormat;

public class Abfitness { // The base code is taken from our previous project

   // Sokvog till SQLite-databas. OBS! andra sokvag sa att den pekar ut din databas
   public static final String DB_URL = "jdbc:sqlite:C:/programmering/membership_course_db5.0.db";
   // Namnet pa den driver som anvands av java for attprata med SQLite
   public static final String DRIVER = "org.sqlite.JDBC";  

        //Member
      static int dateOfBirth;
      static String mFirstName;
      static String mLastName;
      static String mAddress;
      static int mZipCode;
      static String mPhoneNo;
      static int joinDate;
      static String endDate; 
      static String mEmail;
       // Payment
      static int memberID;
      static int paymentID;
      static int payDate;
      static String paymethod;
      static String paymentMethod;
      static String paymentDate;
       // Facility
      static int facilityID;
      static String facilityName;
      static String fAddress;
      static int fPhone;
      static String FEmail;
        //Membership type
      static String typeID;
      static int price;
        //employee
      static int empID;
      static String eFirstName;
      static String eLastName;
      static String position;
      static String ePhoneNo;
      static String eEMail;
      static String ePw;
       //Enrollment
      static int currentDate;
      
      static int getTodaysdate(){
         String str = LocalDate.now().toString();
         str = str.replace("-","");
         return Integer.parseInt(str);
         }
       //Course Session
      static int sessionID;
      static int capacity;
      static int couSesDate;
      static String couSesTime;
      static int roomNo;
       //Course
     static int courseID;
     static String courseName;
      
      //login
      static String memberTruePassword;
      static String memberPassword;
      static String empTruePassword;
      static String empPassword;
      //
      static String addOneMonth()  {
         String DATE_FORMAT = "yyyyMMdd";
         SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);        //https://beginnersbook.com/2013/05/simple-date-format-java/
         Calendar cal = Calendar.getInstance();
         cal.add(Calendar.MONTH, 1);
         return sdf.format(cal.getTime());
      }

      
      static String databas;
      static PreparedStatement send; 
      static Connection conn = null;
      static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

 
 public static void main(String[] args) throws IOException {

    // Kod for att skapa uppkoppling mot SQLite-dabatasen
      try {
         Class.forName(DRIVER);
         SQLiteConfig config = new SQLiteConfig();  
         config.enforceForeignKeys(true); // Denna kodrad ser till att satta databasen i ett lage dar den ger felmeddelande ifall man bryter mot nagon frammande-nyckel-regel
         conn = DriverManager.getConnection(DB_URL,config.toProperties());  
      } catch (Exception e) {
         // Om java-progammet inte lyckas koppla upp sig mot databasen (t ex om fel sokvag eller om driver inte hittas) sa kommer ett felmeddelande skrivas ut
         System.out.println( e.toString() );
         System.exit(0);
      }
         
  // PLATS FOR DEKLARATIONER OCH KOD //
  
      
      
     /* boolean fortsatt = true;
        while (fortsatt) { 
      
         System.out.println("BC - Book course");
         System.out.println("CC - Create course");
         System.out.println("CS - Create course session"); 
         System.out.println("DC - Delete course");
         System.out.println("CM - Cancel course for member");
         System.out.println("SB - See booked courses");
         System.out.println("RM - Create report on number of visits of specific course"); 
    	   System.out.println("Q - Quit");
         
         String val = input.readLine();
         
         switch (val.toUpperCase()) {
         
           case "BC": // Book course
           bookCourse();
           break;
            
           case "CC": //create course
           createCourse();
           break;
           
           case "CS": //create coursesession
           createSession();
           break;
                  
           case "DC": //Delete course
           deleteCourse();
           break;
            
           case "CM": //Cancel course for member
           cancelCourse();
           break;

            case "SB": // See booked courses for member
            seeBookings();
            break;
            
            case "RM": // Report on course attendance
            createReport();
            break;

            case "Q":
            System.out.println("Program terminated");
            System.exit(0);
         

            default:
            System.out.println("Wrong choice");
            break;
         }
      }*/
   mainMenu();
   }
  
   public static void mainMenu() throws IOException {
         boolean fortsatt1 = true;
         while (true){
            System.out.println("1. - Sign in as member");
            System.out.println("2. - Sign in as Employee");
            System.out.println("3. - New Member");         
         
            String mainVal = input.readLine();
            switch (mainVal.toUpperCase()) {
         
               case "1": // Sign in as member
               loginMember();
               break;
            
               case "2": // Sign in as Employee
               loginEmployee();
               break;
               
               case "3": //new member.
               newMember();
            
               case "0": // Exit
               fortsatt1=false;
            }
         }
      }
  
      
      
      public static void loginMember() throws IOException{
  
      System.out.println("Enter MemberID");
      memberID = Integer.parseInt(input.readLine());
      System.out.println("Enter Password");
      String memberPassword = input.readLine();
   
      try{
         String selectmPw = "select Member.mPw from Member where memberID = ?";
         PreparedStatement pstmt = conn.prepareStatement(selectmPw);
         pstmt.setInt(1, memberID);
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()){
            memberTruePassword = rs.getString("mPw");
            pstmt.close();
            rs.close();
         }
         if(memberTruePassword.equals(memberPassword)){
            memberMenu();
         }
         else{
            System.out.println("Wrong password");
            mainMenu();
         }
      }
      catch (java.sql.SQLException e2) {
         System.out.println(e2.getMessage());
      }
   }
        
      
   public static void loginEmployee() throws IOException{
  
      System.out.println("Enter EmployeeID");
      empID = Integer.parseInt(input.readLine());
      System.out.println("Enter Password");
      String empPassword = input.readLine();
   
      try{
         String selectmPw = "select Employee.ePw from Employee where empID = ?";
         PreparedStatement pstmt = conn.prepareStatement(selectmPw);
         pstmt.setInt(1, empID);
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()){
            empTruePassword = rs.getString("ePw");
            pstmt.close();
            rs.close();
         }
         if(empTruePassword.equals(empPassword)){
            employeeMenu();
         }
         else{
            System.out.println("Wrong password");
            mainMenu();
         }
      }
      catch (java.sql.SQLException e2) {
         System.out.println(e2.getMessage());
      }
   }

      
      
   public static void employeeMenu() throws IOException {
      boolean fortsatt = true;
      while (true) { 
      
         System.out.println("BC - Book course for member");
         System.out.println("CC - Create course");
         System.out.println("CS - Create course session");
         System.out.println("DC - Delete course");
         System.out.println("CM - Cancel course for member");
         System.out.println("SB - See members booked courses");
         System.out.println("RM - Create report on number of visits of specific course session"); 
         System.out.println("VM - View member profile"); 
         
    	   System.out.println("L - Log out");
         
         String val = input.readLine();
         
         switch (val.toUpperCase()) {
         
            case "BC": // Book course
            bookCourse();
            break;
            
            case "CC": //create course
            createCourse();
            break;
            
            case "CS": // create course session
            createSession();
            break;
                  
            case "DC": //Delete course
            deleteCourse();
            break;
            
            case "CM": //Cancel course for member
            cancelCourse();
            break;

            case "SB": // See booked courses for member
            seeBookings();
            break;
            
            case "RM": // Report on course attendance
            createReport();
            break;
            
            case "VM": // View member profile
            memberProfile();
            myUpdates();
            break;
            
            
            
            case "L":
            System.out.println("Signed out.");
            mainMenu();
         

            default:
            System.out.println("Wrong choice");
            break;
         }
      }
   }
   
   public static void newMember() throws IOException { 
   
  //funkar
   
                    System.out.println("Enter Date of birth (YYYYMMDD)");
                    int dateOfBirth = Integer.parseInt(input.readLine());

                    System.out.println("Enter First name");
                    String mFirstName = input.readLine();

                    System.out.println("Enter Last name");
                    String mLastName = input.readLine();

                    System.out.println("Enter Email");
                    String mEmail = input.readLine();

                    System.out.println("Enter Phone number");
                    mPhoneNo = input.readLine();

                    System.out.println("Enter Address");
                    String mAddress = input.readLine();

                    System.out.println("Enter Zipcode");
                    int mZipCode = Integer.parseInt(input.readLine());                   
                                                          
                    System.out.println("Enter end date");
                    endDate = input.readLine();
                    
                    System.out.println("Choose password");
                    String mPw = input.readLine();
                    
                    System.out.println("Choose membership type! \nGold:399kr Silver:299kr Bronze:199kr");
                    String typeID = input.readLine();
                    
                    System.out.println("Enter payment method, choose between: Direct debit, Invoice, Cashier");
                    String paymentMethod = input.readLine();
                    
                    
                    
                    
                    
               String p1 = "Direct debit";
               String p2 = "Invoice";
               String p3 = "Cashier";
               String p4 = "Gold";
               String p5 = "Silver";
               String p6 = "Bronze";
            
         if (paymentMethod.equalsIgnoreCase(p1) || paymentMethod.equalsIgnoreCase(p2) || paymentMethod.equalsIgnoreCase(p3)) { 
           if (typeID.equalsIgnoreCase(p4) || typeID.equalsIgnoreCase(p5) || typeID.equalsIgnoreCase(p6))  { 
                  if (endDate.equals ("null")) {

                        try {
                            String insertp = "INSERT INTO Member(dateOfBirth, mFirstName, mLastName, mAddress, mZipCode, mPhoneNo, mEmail, joinDate, mPw) VALUES(?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pstmt = conn.prepareStatement(insertp);
                            pstmt.setInt(1, dateOfBirth);
                            pstmt.setString(2, mFirstName);
                            pstmt.setString(3, mLastName);
                            pstmt.setString(4, mAddress);
                            pstmt.setInt(5, mZipCode);
                            pstmt.setString(6, mPhoneNo);
                            pstmt.setString(7, mEmail);
                            pstmt.setInt(8, getTodaysdate());
                            pstmt.setString(9, mPw);
                            pstmt.executeUpdate();
                            pstmt.close();
                            
                           System.out.println ("Welcome as a new member to FitnessAB!");
                        } catch (java.sql.SQLException e1) {
                            System.out.println(e1.getMessage());
                        }
                    } else {
                        try {
                            String insertd = "INSERT INTO Member(dateOfBirth, mFirstName, mLastName, mAddress, mZipCode, mPhoneNo, mEmail, joinDate, endDate, mPw) VALUES(?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pstmt = conn.prepareStatement(insertd);
                            pstmt.setInt(1, dateOfBirth);
                            pstmt.setString(2, mFirstName);
                            pstmt.setString(3, mLastName);
                            pstmt.setString(4, mAddress);
                            pstmt.setInt(5, mZipCode);
                            pstmt.setString(6, mPhoneNo);
                            pstmt.setString(7, mEmail);
                            pstmt.setInt(8, getTodaysdate());
                            pstmt.setString(9, endDate);
                            pstmt.setString(10, mPw);
                            pstmt.executeUpdate();
                            pstmt.close();
                            
                           System.out.println ("Welcome as new member to FitnessAB");
                        } catch (java.sql.SQLException e1) {
                            System.out.println(e1.getMessage());
                        }
                       
                    }
                    try{
                    String grud = "SELECT * FROM Member WHERE  memberID = (SELECT MAX(memberID)  FROM Member)";
                      PreparedStatement pstmt = conn.prepareStatement(grud);
                      
                      ResultSet rs = pstmt.executeQuery(); 
                       while (rs.next()){
                        memberID = rs.getInt("memberID");
                        pstmt.close();
                        rs.close();
                        System.out.println("Your member ID is " + memberID + "\n");                
                     }
                  }
                  catch (java.sql.SQLException e2){
                  System.out.println(e2.getMessage());
               }
            try{
            String tudy = "Insert into Membership(memberID, typeID) VALUES(?,?)"; //insert into member tabellen memberID och typeID
            PreparedStatement pstmt = conn.prepareStatement(tudy);
                     pstmt.setInt(1, memberID);
                     pstmt.setString(2, typeID);
                     pstmt.executeUpdate();
                     pstmt.close();
                    

            }
                  catch (java.sql.SQLException e2){
                  System.out.println(e2.getMessage());
               }
              try{ 
               
                 
            String diii = "Insert into Payment(memberID, paymentMethod, payDate) VALUES(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(diii);
                     pstmt.setInt(1, memberID);
                     pstmt.setString(2, paymentMethod);
                     pstmt.setString(3, addOneMonth());
                     pstmt.executeUpdate();
                     pstmt.close();
                    

            
                  }
                   
                  catch (java.sql.SQLException e2){
                  System.out.println(e2.getMessage());
               }
               }
                 else{ 
                 System.out.println ("Wrong choice.");                  
                                                           
               }
             }
                                           
               else{ 
                 System.out.println ("Wrong choice.");                  
                                                           
               }
               }
       
   public static void memberMenu() throws IOException{
      boolean fortsatt = true;
      while (true) { 
      
         System.out.println("BC - Book course");
         System.out.println("CM - Cancel course");
         System.out.println("SB - See booked courses");
         System.out.println("MP - My profile");
    	   System.out.println("L - Log out");
         
         String val = input.readLine();
         
         switch (val.toUpperCase()) {
         
            case "BC": // Book course
            memberBookCourse();
            break;
                                        
            case "CM": //Cancel course for member
            memberCancelCourse();
            break;

            case "SB": // See booked courses for member
            memberSeeBookings();
            break;
            
            case "MP": // View profile and update.
            myProfile();
            myUpdates();
            break;
            
            case "L":
            System.out.println("Signed out.");
            memberID = 0;
            mainMenu();
         

            default:
            System.out.println("Wrong choice");
            break;
         }
      }
   }   

   public static void myUpdates() throws IOException{
      boolean fortsatt = true;
      while (true) { 
      
            System.out.println("1 - update payment method");
            System.out.println("2 - Update Phone number for member");
            System.out.println("3 - Update Email for member");
            System.out.println("4 - Update Address for member");
            System.out.println("5 - Update membership type");
            System.out.println("0 - Cancel membership");
            System.out.println("\n" + "B - Back");
         
         String val = input.readLine();
         
         switch (val.toUpperCase()) {
         
            case "1": // Update Payment Method
            memberUpdatePayment();
            break;
                                        
            case "2": //Update member phone number
            updatePhoneNo();
            break;

            case "3": // Update email
            updatEmail();
            break;
            
            case "4": //update address.
            updateAddress();
            break;
            
            case "5": //update membership
            updateMembershipType();
            break;
            
            case "0":
            cancelMembership();
            break;
            
            case "B"://back to previous menu.
            memberMenu();
            default:
            System.out.println("Wrong choice");
            break;
         }
      }
   }
   
   public static void myProfile() throws IOException {
      try {    
         String profile = "SELECT Member.mFirstName, Member.mLastName, Member.dateOfBirth, Member.memberID, Membership.typeID, Member.mEmail, Member.mPhoneNo, Member.mAddress, Member.mZipCode, Payment.paymentMethod FROM Member, Membership, Payment WHERE Member.memberID =? AND Member.memberID = Membership.memberID AND Member.memberID = Payment.memberID";    
         PreparedStatement pstmt = conn.prepareStatement(profile);
         pstmt.setInt(1, memberID);
         ResultSet rs = pstmt.executeQuery();
         System.out.print("My profile \n");   
         while (rs.next()) {
            System.out.println("First name: " + rs.getString("mFirstName") + "\n" + "Last name: " + rs.getString("mLastName") + "\n" + "Date of birth: " + rs.getInt("dateOfBirth") + "\n" + "Member ID: "+ rs.getInt("memberID") + "\n" + "Membership type: " + rs.getString("typeID") + "\n" + "E-mail: " + rs.getString("mEmail") + "\n" + "Phone number: " + rs.getString("mPhoneNo") + "\n" + "Address: " + rs.getString("mAddress") + "\n" + "Zip code: "+ rs.getInt("mZipCode") + "\n" + "Payment method: " + rs.getString("paymentMethod"));
         }
         pstmt.close();
         rs.close();
      } 
      catch (java.sql.SQLException e3) {
         System.out.println(e3.getMessage());
      }
   }
   public static void memberProfile() throws IOException {
      try {    
      System.out.println("Enter member ID");
      memberID = Integer.parseInt(input.readLine()); 
      
         String profile = "SELECT Member.mFirstName, Member.mLastName, Member.dateOfBirth, Member.memberID, Membership.typeID, Member.mEmail, Member.mPhoneNo, Member.mAddress, Member.mZipCode, Payment.paymentMethod FROM Member, Membership, Payment WHERE Member.memberID =? AND Member.memberID = Membership.memberID AND Member.memberID = Payment.memberID";    
         PreparedStatement pstmt = conn.prepareStatement(profile);
         pstmt.setInt(1, memberID);
         ResultSet rs = pstmt.executeQuery();
         System.out.print(memberID +"\n");   
         while (rs.next()) {
            System.out.println("First name: " + rs.getString("mFirstName") + "\n" + "Last name: " + rs.getString("mLastName") + "\n" + "Date of birth: " + rs.getInt("dateOfBirth") + "\n" + "Member ID: "+ rs.getInt("memberID") + "\n" + "Membership type: " + rs.getString("typeID") + "\n" + "E-mail: " + rs.getString("mEmail") + "\n" + "Phone number: " + rs.getString("mPhoneNo") + "\n" + "Address: " + rs.getString("mAddress") + "\n" + "Zip code: "+ rs.getInt("mZipCode") + "\n" + "Payment method: " + rs.getString("paymentMethod"));
         }
         pstmt.close();
         rs.close();
      } 
      catch (java.sql.SQLException e3) {
         System.out.println(e3.getMessage());
      }
   }
   
  
   
            
   public static void createCourse() throws IOException { // funkar
      try {      
         System.out.println("Enter course name");
         courseName = input.readLine();

         String inser = "INSERT INTO Course(courseName) VALUES(?)";
         PreparedStatement pstmt = conn.prepareStatement(inser);
         pstmt.setString(1, courseName);
         pstmt.executeUpdate();
         pstmt.close();
         
      } 
            
      catch (java.sql.SQLException e2) {
         System.out.println(e2.getMessage());
      }
      try{
                    String grud = "SELECT * FROM Course WHERE  courseID = (SELECT MAX(courseID)  FROM Course)";
                      PreparedStatement pstmt = conn.prepareStatement(grud);
                      
                      ResultSet rs = pstmt.executeQuery(); 
                       while (rs.next()){
                        int courseID = rs.getInt("courseID");
                        pstmt.close();
                        rs.close();
                        System.out.println("Course ID " + courseID + " is created succesfully! \n");                
                     }
                  }
                  catch (java.sql.SQLException e2){
                  System.out.println(e2.getMessage());
               }
   }
                
      public static void deleteCourse() throws IOException { // Funkar om man inte vill ta bort courses som har coursesessions då bryts foreign key constraint
         try {
            System.out.println("Enter courseID"); 
            courseID = Integer.parseInt(input.readLine());
            
        
            String delete = "DELETE FROM Course WHERE courseID=?";
            PreparedStatement pstmt = conn.prepareStatement(delete);
            pstmt.setInt(1, courseID);
            
            int rowAffected = pstmt.executeUpdate();
            //System.out.println(String.format("Row affected %d", rowAffected));
            System.out.println("Course " + courseID + " deleted successfully");
            
         }
      catch(SQLException e) {
         System.out.println(e);            
      }
   }
            
   public static void createReport() throws IOException { //funkar
      try {
         System.out.println("Enter sessionID");
         sessionID = Integer.parseInt(input.readLine());
               
         String selef = "select count(memberID) from CourseEnrollment where sessionID = ?";
         PreparedStatement pstmt = conn.prepareStatement(selef);
         pstmt.setInt(1, sessionID);
         ResultSet rs = pstmt.executeQuery();
               
         while (rs.next()) {
            System.out.println(rs.getInt("count(memberID)"));
         }
         pstmt.close();
         rs.close();
      } 
      catch(SQLException e) {
         System.out.println(e);
      }
   }
                    
   public static void seeBookings() throws IOException { //funkar
      try {
         System.out.println ("Enter member ID"); 
         memberID = Integer.parseInt(input.readLine());
         String seeBookings = "SELECT Course.courseName, CourseSession.couSesDate, Facility.facilityName FROM Course, CourseSession, CourseEnrollment, Facility WHERE CourseSession.sessionID = CourseEnrollment.sessionID AND CourseSession.courseID = Course.courseID AND CourseSession.facilityID = Facility.facilityID AND memberID = ?";             
         PreparedStatement pstmt = conn.prepareStatement(seeBookings);
         pstmt.setInt(1, memberID);
         ResultSet rs = pstmt.executeQuery();
         System.out.println("Your booked courses: " + "\n" + "Course name" + "\t" + " | " + "Course date" + "\t" + " | " + "Facility");
             
         while (rs.next()) {
            System.out.println (rs.getString("courseName") + "\t" + " | " + rs.getInt("couSesDate") + "\t" + " | " + rs.getString("facilityName"));
         }
         pstmt.close();
         rs.close();

      }
      catch(SQLException e) {
         System.out.println(e);
      }
   }
   
   public static void memberSeeBookings() throws IOException { //funkar
      try {
         String seeBookings = "SELECT Course.courseName, CourseSession.couSesDate, Facility.facilityName FROM Course, CourseSession, CourseEnrollment, Facility WHERE CourseSession.sessionID = CourseEnrollment.sessionID AND CourseSession.courseID = Course.courseID AND CourseSession.facilityID = Facility.facilityID AND memberID = ?";             
         PreparedStatement pstmt = conn.prepareStatement(seeBookings);
         pstmt.setInt(1, memberID);
         ResultSet rs = pstmt.executeQuery();
         System.out.println("Your booked courses: " + "\n" + "Course name" + "\t" + " | " + "Course date" + "\t" + " | " + "Facility"+"\n--");
             
         while (rs.next()) {
            System.out.println (rs.getString("courseName") + "\t" + " | " + rs.getInt("couSesDate") + "\t" + " | " + rs.getString("facilityName"));
         }
         System.out.println("--\n");
         pstmt.close();
         rs.close();

      }
      catch(SQLException e) {
         System.out.println(e);
      }
   } 
   
   public static void cancelCourse() throws IOException  { //funkar
      try {
         System.out.println("Enter member ID"); 
         memberID = Integer.parseInt (input.readLine());
            
         String selectCourses = "SELECT CourseSession.sessionID, Course.courseName, CourseSession.couSesDate, CourseSession.CouSesTime, Facility.facilityName FROM Course, CourseSession, CourseEnrollment, Employee,Facility WHERE CourseSession.sessionID = CourseEnrollment.sessionID AND CourseSession.empID=Employee.empID AND Employee.facilityID=Facility.facilityID AND CourseSession.courseID = Course.courseID AND memberID=? AND cousesDate>=? ORDER BY Facility.facilityID ASC";
         PreparedStatement pstmt = conn.prepareStatement(selectCourses);
         pstmt.setInt(1, memberID);
         pstmt.setInt(2, getTodaysdate());
         ResultSet rs = pstmt.executeQuery();
         System.out.println("Session, Course, Date, Time, Facility\n");
               
         while (rs.next()) {
            System.out.println(rs.getInt("sessionID") + " " + rs.getString("courseName") + " " + rs.getInt("couSesDate") + " " + rs.getString("couSesTime") + " " + rs.getString("FacilityName"));
         }
         pstmt.close();
         rs.close();
      }
            
      catch(SQLException e) {
         System.out.println(e);                 
      }
               
      System.out.println("Enter session ID you want to cancel your spot in, press 0 to go back to main menu"); 
      sessionID = Integer.parseInt (input.readLine());
            
      if (sessionID == 0) {
         System.out.println ("No session cancelled."); 
      }
            
      else {
            
         try {
            String cancel = "DELETE FROM CourseEnrollment WHERE sessionID=? AND memberID=?";
            PreparedStatement pstmt = conn.prepareStatement(cancel);
            pstmt.setInt(1, sessionID);
            pstmt.setInt(2, memberID);
            
            int rowAffected = pstmt.executeUpdate();
            //System.out.println(String.format("Row affected %d", rowAffected));
            System.out.println("Course session " + sessionID + " cancelled successfully");
            
         }
         catch(SQLException e) {
            System.out.println(e);            
         } 
      }
   }
   
   public static void memberCancelCourse() throws IOException  {
      try {           
         String selectCourses = "SELECT CourseSession.sessionID, Course.courseName, CourseSession.couSesDate, CourseSession.CouSesTime, Facility.facilityName FROM Course, CourseSession, CourseEnrollment, Employee,Facility WHERE CourseSession.sessionID = CourseEnrollment.sessionID AND CourseSession.empID=Employee.empID AND Employee.facilityID=Facility.facilityID AND CourseSession.courseID = Course.courseID AND memberID=? AND cousesDate>=? ORDER BY Facility.facilityID ASC";
         PreparedStatement pstmt = conn.prepareStatement(selectCourses);
         pstmt.setInt(1, memberID);
         pstmt.setInt(2, getTodaysdate());
         ResultSet rs = pstmt.executeQuery();
         System.out.println("Session, Course, Date, Time, Facility\n");
               
         while (rs.next()) {
            System.out.println(rs.getInt("sessionID") + " " + rs.getString("courseName") + " " + rs.getInt("couSesDate") + " " + rs.getString("couSesTime") + " " + rs.getString("FacilityName"));
         }
         pstmt.close();
         rs.close();
      }          
      catch(SQLException e) {
         System.out.println(e);                 
      }
               
      System.out.println("Enter session ID you want to cancel your spot in, press 0 to go back to main menu"); 
      sessionID = Integer.parseInt(input.readLine());
            
      if (sessionID == 0) {
         System.out.println ("No session found"); 
         // break;
      }            
      else {
         try {
            String cancel = "DELETE FROM CourseEnrollment WHERE sessionID=? AND memberID=?";
            PreparedStatement pstmt = conn.prepareStatement(cancel);
            pstmt.setInt(1, sessionID);
            pstmt.setInt(2, memberID);
            
            int rowAffected = pstmt.executeUpdate();
            //System.out.println(String.format("Row affected %d", rowAffected));
            System.out.println("Course session " + sessionID + " cancelled successfully");            
         }
         catch(SQLException e) {
            System.out.println(e);            
         } 
      }
   }

   
          
   public static void bookCourse() throws IOException { //funkar, ändrat i SQL-sökning så att man får skriva facility-namn och får det utskrivet istället för ID
               
      System.out.println("Enter memberID");
      memberID = Integer.parseInt(input.readLine());
                      
      System.out.println("Enter facility name");
      facilityName = input.readLine();
               
      try {
         String selectCourses = "SELECT CourseSession.sessionID, Course.courseName, CourseSession.couSesDate, CourseSession.couSesTime, Facility.facilityName from CourseSession, Course, Employee, Facility WHERE Course.courseID=CourseSession.courseID AND CourseSession.empID=Employee.empID AND CourseSession.facilityID = Facility.facilityID AND Facility.facilityName=? AND CousesDate > ? ORDER BY CouSesDate ASC";
         PreparedStatement pstmt = conn.prepareStatement(selectCourses);
         pstmt.setString(1, facilityName);
         pstmt.setInt(2, getTodaysdate());
         ResultSet rs = pstmt.executeQuery();
         System.out.println("Session, Course, Date, Time, Facility");
               
         while (rs.next()) {
            System.out.println(rs.getInt("sessionID") + " " + rs.getString("courseName") + " " + rs.getInt("couSesDate") + " " + rs.getString("couSesTime") + " " + rs.getString("facilityName"));
         }
         pstmt.close();
         rs.close();
      }
               
      catch (java.sql.SQLException e2){
         System.out.println(e2.getMessage());
      }
               
      System.out.println("Enter SessionID");
      sessionID = Integer.parseInt(input.readLine());
            
                      
      try {
         String check = "select Membership.typeID from Membership where memberID=?";
         PreparedStatement pstmt = conn.prepareStatement(check);
         pstmt.setInt(1, memberID);
         ResultSet rs = pstmt.executeQuery();
               
         while (rs.next()){
            typeID = rs.getString("typeID");
            pstmt.close();
            rs.close();
         }
      }
      catch (java.sql.SQLException e2){
         System.out.println(e2.getMessage());
      }
               
                 
      try {
         System.out.println(typeID);
         if (typeID.equals("Gold")){  
            String inserth = "INSERT INTO CourseEnrollment(memberID, sessionID) VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(inserth);
            pstmt.setInt(1, memberID);
            pstmt.setInt(2, sessionID);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.print("Member " + memberID + " added to course.  \n");
         }
         else{
            System.out.println("Upgrade your membership to Gold for access to courses.");
         }
      }
      catch (java.sql.SQLException e2){
         System.out.println(e2.getMessage());
      }
   }
             
   
   
   
   public static void createSession() throws IOException { //funkar men ger inte ut det sessionID som skapats utan skriver alltid ut "Session 0 created successfully."
      try {      
         System.out.println("Enter course ID");
         courseID = Integer.parseInt(input.readLine());
         System.out.println ("Enter facility ID");
         facilityID = Integer.parseInt(input.readLine());
         System.out.println ("Enter session capacity");
         capacity = Integer.parseInt(input.readLine());
         System.out.println ("Enter session date in format YYYYMMDD");
         couSesDate = Integer.parseInt(input.readLine());
         System.out.println ("Enter session time in format XX:XX");
         couSesTime = input.readLine(); 
         System.out.println ("Enter session's room number");
         roomNo = Integer.parseInt(input.readLine()); 
         System.out.println ("Enter the employee ID on who will instruct session"); 
         empID = Integer.parseInt(input.readLine());
          

         String insertg = "INSERT INTO CourseSession(courseID, facilityID, capacity, couSesDate, couSesTime, roomNo, empID) VALUES(?,?,?,?,?,?,?)";
         PreparedStatement pstmt = conn.prepareStatement(insertg);
         pstmt.setInt(1, courseID);
         pstmt.setInt(2, facilityID);
         pstmt.setInt(3, capacity);
         pstmt.setInt(4, couSesDate);
         pstmt.setString(5, couSesTime);
         pstmt.setInt(6, roomNo);
         pstmt.setInt(7, empID);
         
          
         pstmt.executeUpdate();
         pstmt.close();
         System.out.println("Session created!");
         
      } 
            
      catch (java.sql.SQLException e2) {
         System.out.println(e2.getMessage());
      }
   }
   
   public static void memberBookCourse() throws IOException { //funkar, ändrat i SQL-sökning så att man får skriva facility-namn och får det utskrivet istället för ID
                      
      System.out.println("Enter facility name");
      facilityName = input.readLine();
               
      try {
         String selectCourses = "SELECT CourseSession.sessionID, Course.courseName, CourseSession.couSesDate, CourseSession.couSesTime, Facility.facilityName from CourseSession, Course, Employee, Facility WHERE Course.courseID=CourseSession.courseID AND CourseSession.empID=Employee.empID AND CourseSession.facilityID = Facility.facilityID AND Facility.facilityName=? AND CousesDate > ? ORDER BY CouSesDate ASC";
         PreparedStatement pstmt = conn.prepareStatement(selectCourses);
         pstmt.setString(1, facilityName);
         pstmt.setInt(2, getTodaysdate());
         ResultSet rs = pstmt.executeQuery();
         System.out.println("Session, Course, Date, Time, Facility");
         
         while (rs.next()) {
            System.out.println(rs.getInt("sessionID") + " " + rs.getString("courseName") + " " + rs.getInt("couSesDate") + " " + rs.getString("couSesTime") + " " + rs.getString("facilityName"));
         }
         pstmt.close();
         rs.close();
      }
               
      catch (java.sql.SQLException e2){
         System.out.println(e2.getMessage());
      }
               
      System.out.println("Enter SessionID");
      sessionID = Integer.parseInt(input.readLine());
            
                      
      try {
         String check = "select Membership.typeID from Membership where memberID=?";
         PreparedStatement pstmt = conn.prepareStatement(check);
         pstmt.setInt(1, memberID);
         ResultSet rs = pstmt.executeQuery();
               
         while (rs.next()){
            typeID = rs.getString("typeID");
            pstmt.close();
            rs.close();
         }
      }
      catch (java.sql.SQLException e2){
         System.out.println(e2.getMessage());
      }
               
                 
      try {
         System.out.println(typeID);
         if (typeID.equals("Gold")){  
            String inserth = "INSERT INTO CourseEnrollment(memberID, sessionID) VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(inserth);
            pstmt.setInt(1, memberID);
            pstmt.setInt(2, sessionID);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.print("Member " + memberID + " added to course.  \n");
         }
         else{
         System.out.println("Upgrade your membership to Gold for access to courses.");
         }
      }
      catch (java.sql.SQLException e2){
         System.out.println(e2.getMessage());
      }
   } 
   
   public static void updatePhoneNo() throws IOException { // funkar 
      
      System.out.println("Enter Phone number");
      mPhoneNo = input.readLine();                  
      try {
         String inserty = "UPDATE Member SET mPhoneNo=? WHERE memberID= ?";
         PreparedStatement pstmt = conn.prepareStatement(inserty);
         pstmt.setString(1, mPhoneNo);
         pstmt.setInt(2, memberID);          
         pstmt.executeUpdate();                             //https://stackoverflow.com/questions/2571915/return-number-of-rows-affected-by-sql-update-statement-in-java
         System.out.println("Phone number updated successfully");
      } 
      catch (java.sql.SQLException e2) {
         System.out.println(e2.getMessage());
      }
   }
   
   public static void updateAddress() throws IOException { //funkar
      
      System.out.println("Enter Address");
      mAddress = input.readLine();
      System.out.println("Enter Zip Code");
      mZipCode = Integer.parseInt(input.readLine());

      try {
         String insertg = "UPDATE Member SET mAddress=?, mZipCode=? WHERE memberID= ?";
         PreparedStatement pstmt = conn.prepareStatement(insertg);
         pstmt.setString(1, mAddress);
         pstmt.setInt(2, mZipCode);
         pstmt.setInt(3, memberID);

         pstmt.executeUpdate();
         System.out.println("Address updated successfully");

      } 
      catch (java.sql.SQLException e2) {
         System.out.println(e2.getMessage());
      }
   }

   public static void updateMembershipType() throws IOException { //funkar
        try {
        

         System.out.println("Enter desired membership type \nChoose between Gold, Silver and Bronze");
         typeID = input.readLine();


         String insertg = "UPDATE Membership SET typeID=? WHERE memberID= ?";
         PreparedStatement pstmt = conn.prepareStatement(insertg);
         pstmt.setString(1, typeID);
         pstmt.setInt(2, memberID);

         pstmt.executeUpdate();
         System.out.println("Membership type updated successfully");

         } 
         catch (java.sql.SQLException e2) {
         System.out.println(e2.getMessage());
       }
     }

       public static void updatePayment() throws IOException {
   
       
       System.out.println ("Enter payment method, choose between: Direct debit, Invoice, Cashier"); 
       paymentMethod = input.readLine(); 
       
           try {
       String p1 = "Direct debit";
       String p2 = "Invoice";
       String p3 = "Cashier";

         if (paymentMethod.equalsIgnoreCase(p1) || paymentMethod.equalsIgnoreCase(p2) || paymentMethod.equalsIgnoreCase(p3)){
       
       String payment = "UPDATE Payment SET paymentmethod=? WHERE memberID= ?"; 
       PreparedStatement pstmt = conn.prepareStatement(payment);
       pstmt.setString(1, paymentMethod);
       pstmt.setInt(2, memberID); 
       pstmt.executeUpdate();
       pstmt.close(); 
        
       System.out.print ("Payment method updated successfully.   \n");  
      }
      else {
      System.out.println("Wrong choice."); 
     }
    }
      catch (java.sql.SQLException e2) {
       System.out.println(e2.getMessage());
     }
    }
    
   public static void memberUpdatePayment() throws IOException {
       System.out.println ("Enter payment method, choose between: Direct debit, Invoice, Cashier"); 
       paymentMethod = input.readLine(); 
       
           try {
       String p1 = "Direct debit";
       String p2 = "Invoice";
       String p3 = "Cashier";

         if (paymentMethod.equalsIgnoreCase(p1) || paymentMethod.equalsIgnoreCase(p2) || paymentMethod.equalsIgnoreCase(p3)){
       
       String payment = "UPDATE Payment SET paymentmethod=? WHERE memberID= ?"; 
       PreparedStatement pstmt = conn.prepareStatement(payment);
       pstmt.setString(1, paymentMethod);
       pstmt.setInt(2, memberID); 
       pstmt.executeUpdate();
       pstmt.close(); 
        
       System.out.print ("Payment method updated successfully.   \n");  
      }
      else {
      System.out.println("Wrong choice."); 
     }
    }
      catch (java.sql.SQLException e2) {
       System.out.println(e2.getMessage());
     }
    }
   
   public static void updatEmail() throws IOException { // funkar
           
      System.out.println("Enter Email");
      mEmail = input.readLine();
           
      try {
         String insertz = "UPDATE Member SET mEmail=? WHERE memberID= ?";
         PreparedStatement pstmt = conn.prepareStatement(insertz);
         pstmt.setString(1, mEmail);
         pstmt.setInt(2, memberID);
         pstmt.executeUpdate();                              //https://stackoverflow.com/questions/2571915/return-number-of-rows-affected-by-sql-update-statement-in-java
         System.out.println("Email updated successfully");

      } 
      catch (java.sql.SQLException e2) {
         System.out.println(e2.getMessage());
      }
   }
   
   public static void cancelMembership() throws IOException { //funkar, lade in text som skriver ut nï¿½r medlemskap avslutas
      System.out.println("If you are sure input YES");
      String cancel = input.readLine();
                    /*System.out.println("Enter endDate");
                    endDate = Integer.parseInt(input.readLine());*/
      if (cancel.equalsIgnoreCase("YES")){
       try {
         String inserta = "UPDATE Member SET endDate=? WHERE memberID= ?";
         PreparedStatement pstmt = conn.prepareStatement(inserta);
         pstmt.setString(1, addOneMonth());
         pstmt.setInt(2, memberID);

         int rowAffected = pstmt.executeUpdate();
         System.out.println(String.format("Your membership ends: " + addOneMonth()));

         } 
         
         catch (java.sql.SQLException e2) {
            System.out.println(e2.getMessage());
         }
      }
         else { System.out.println("Cancellation aborted");
         myUpdates();
         }
      }
      
     public static void createReportMember() throws IOException { 
     //funkar
      System.out.println("Enter join date in format YYYYMMDD");
      joinDate = Integer.parseInt(input.readLine());

       try {
          String selectg = "select * FROM MEMBER WHERE joinDate >= ?";
          PreparedStatement pstmt = conn.prepareStatement(selectg);
          pstmt.setInt(1, joinDate);
          ResultSet rs = pstmt.executeQuery();
          System.out.println("memberID, date of birth, first name, last name, address, Zip code, phone number, email, join date, end date");
       
          while (rs.next()) {
           System.out.println(rs.getInt("memberID") + " " + rs.getInt("dateOfBirth") + " " + rs.getString("mFirstName") + " " + rs.getString("mLastName") + " " + rs.getString("mAddress") + " " + rs.getInt("mZipCode") + " " + rs.getString("mPhoneNo") + " " + rs.getString("mEmail") + " " + rs.getInt("joinDate") + " " + rs.getString("endDate"));
          }
          pstmt.close();
          rs.close();
         } 
        catch (java.sql.SQLException e3) {
        System.out.println(e3.getMessage());
      }
     }

}     