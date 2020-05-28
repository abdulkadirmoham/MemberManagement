import java.io.*;
import java.sql.*;
import org.sqlite.SQLiteConfig;
import java.util.*;
import java.util.Date;
import java.time.LocalDate;

public class coursesTestBC {

   // Sokvog till SQLite-databas. OBS! andra sokvag sa att den pekar ut din databas
   public static final String DB_URL = "jdbc:sqlite:C:/Users/hanna/OneDrive/Dokument/Systemvetenskap/TIG059/Membership_project_java/membership_course_db4.6.db";
   // Namnet pa den driver som anvands av java for attprata med SQLite
   public static final String DRIVER = "org.sqlite.JDBC";  

        //Member
      static int dateOfBirth;
      static String mFirstName;
      static String mLastName;
      static String mAddress;
      static int mZipCode;
      static int mPhoneNo;
      static int joinDate;
      static String endDate; 
      static String mEmail;
       // Payment
      static int memberID;
      static int paymentID;
      static String paymethod;
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
      static int ePhoneNo;
      static String eEMail;
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
  
      
      
      boolean fortsatt = true;
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
     System.out.println ("Enter your member ID"); 
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
 public static void cancelCourse() throws IOException  { //funkar
             try {
             System.out.println("Enter your member ID"); 
             memberID = Integer.parseInt (input.readLine());
            
               String selectCourses = "SELECT CourseSession.sessionID, Course.courseName, CourseSession.couSesDate, CourseSession.CouSesTime, Facility.facilityName FROM Course, CourseSession, CourseEnrollment, Employee,Facility WHERE CourseSession.sessionID = CourseEnrollment.sessionID AND CourseSession.empID=Employee.empID AND Employee.facilityID=Facility.facilityID AND CourseSession.courseID = Course.courseID AND memberID=? AND cousesDate>=? ORDER BY Facility.facilityID ASC;";
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
          
 public static void bookCourse() throws IOException { //funkar, ändrat i SQL-sökning så att man får skriva facility-namn och får det utskrivet istället för ID
               
            System.out.println("Enter memberID");
            memberID = Integer.parseInt(input.readLine());
                      
            System.out.println("Enter facility name");
            facilityName = input.readLine();
               
            try {
               String selectCourses = "SELECT CourseSession.sessionID, Course.courseName, CourseSession.couSesDate, CourseSession.couSesTime, Facility.facilityName from CourseSession, Course, Employee, Facility WHERE Course.courseID=CourseSession.courseID AND CourseSession.empID=Employee.empID AND CourseSession.facilityID = Facility.facilityID AND Facility.facilityName=? AND CousesDate > ? ORDER BY CouSesDate ASC;";
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
         
         System.out.print("Session " + sessionID + " created successfully.  \n");

            } 
            
         catch (java.sql.SQLException e2) {
         System.out.println(e2.getMessage());
         }
       }
        
        
      }
            