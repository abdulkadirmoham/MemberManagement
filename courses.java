import java.io.*;
import java.sql.*;
import org.sqlite.SQLiteConfig;
import java.util.*;
import java.util.Date;
import java.time.LocalDate;

public class courses {
 
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
     //Schedule
    static int scheduleDate;
    static String scheduleTime;
    
      static String databas;
      static PreparedStatement send; 
      static Connection conn = null;

   
 
   // Sokvog till SQLite-databas. OBS! andra sokvag sa att den pekar ut din databas
   public static final String DB_URL = "jdbc:sqlite:C:/programmering/membership_course_db4.4.db";
   // Namnet pa den driver som anvands av java for attprata med SQLite
   public static final String DRIVER = "org.sqlite.JDBC";  

   public static void main(String[] args) throws IOException {
      Connection conn = null;

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
      
        //member
      
      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
      boolean fortsatt = true;
      
      while (fortsatt) {
      
         System.out.println("BC - Book course");
         System.out.println("CC - Create course");
         System.out.println("DC - Delete course");
         System.out.println("CM - Cancel course for member");
         System.out.println("SB - See booked courses");
         System.out.println("RM - Create report on number of visits of specific course"); 
    	   System.out.println("Q - Quit");
         
         String val = input.readLine();
         
         switch (val) {
         
             case "BC": // Book course
           
            System.out.println("Enter memberID");
            int memberID = Integer.parseInt(input.readLine());
                      
            System.out.println("Enter Facility");
            int FacilityID = Integer.parseInt(input.readLine());
               
               try {
               String selectCourses = "select CourseSession.sessionID, Course.courseName, CourseSession.couSesDate, CourseSession.couSesTime, Employee.facilityID from CourseSession,Course,Employee where Course.courseID=CourseSession.courseID AND CourseSession.empID=Employee.empID AND Employee.EmpID=? AND CousesDate > ? order by CouSesDate ASC;";
               PreparedStatement pstmt = conn.prepareStatement(selectCourses);
               pstmt.setInt(1, FacilityID);
               pstmt.setInt(2, getTodaysdate());
               ResultSet rs = pstmt.executeQuery();
               System.out.println("Session, Course, Date, Time, Facility");
               
               while (rs.next()) {
                  System.out.println(rs.getInt("sessionID") + " " + rs.getString("courseName") + " " + rs.getInt("couSesDate") + " " + rs.getString("couSesTime") + " " + rs.getInt("FacilityID"));
                  }
               pstmt.close();
               rs.close();
               }
               
            catch (java.sql.SQLException e2){
               System.out.println(e2.getMessage());
               }
               
            System.out.println("Enter SessionID");
            int sessionID = Integer.parseInt(input.readLine());
            
                      
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
                  if (typeID.equals("Gold")){  
                     String inserth = "INSERT INTO CourseEnrollment(memberID, sessionID) VALUES(?,?)";
                     PreparedStatement pstmt = conn.prepareStatement(inserth);
                     pstmt.setInt(1, memberID);
                     pstmt.setInt(2, sessionID);
                     pstmt.executeUpdate();
                     pstmt.close();
                     System.out.print("Member " + memberID + " Added to course.  \n");
                  }
                  else{
                     System.out.println("Upgrade your membership to Gold for access to courses.");
                  }
               }
               catch (java.sql.SQLException e2){
                  System.out.println(e2.getMessage());
               }
            break;
            
            
                     
                 case "CC": //create course
                    
                    System.out.println("Enter course name");
                    courseName = input.readLine();
                    
                   
                    try {
                        String inser = "INSERT INTO Course(courseName) VALUES(?)";
                        PreparedStatement pstmt = conn.prepareStatement(inser);
                        pstmt.setString(1, courseName);
                        pstmt.executeUpdate();
                        pstmt.close();
                    } catch (java.sql.SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                  break;
                  
           case "DC": //Delete course
           
            System.out.println("Enter courseID"); 
            courseID = Integer.parseInt(input.readLine());
            
            try {
               String delete = "DELETE FROM Course WHERE courseID=?";
               PreparedStatement pstmt = conn.prepareStatement(delete);
               pstmt.setInt(1, courseID);
            
            int rowAffected = pstmt.executeUpdate();
            //System.out.println(String.format("Row affected %d", rowAffected));
            System.out.println("Course " + courseID + " deleted successfully");
            
            }
            catch (java.sql.SQLException e2){
            System.out.println(e2.getMessage());
            
            } 
            break;
            
            case "CM": //Cancel course for member
            
            System.out.println("Enter your member ID"); 
            memberID = Integer.parseInt (input.readLine());
            
            System.out.println("Enter session ID you want to cancel your spot in"); 
            sessionID = Integer.parseInt (input.readLine());
            
            try {
               String cancel = "DELETE FROM CourseEnrollment WHERE sessionID=? AND memberID=?";
               PreparedStatement pstmt = conn.prepareStatement(cancel);
               pstmt.setInt(1, sessionID);
               pstmt.setInt(2, memberID);
            
            int rowAffected = pstmt.executeUpdate();
            //System.out.println(String.format("Row affected %d", rowAffected));
            System.out.println("Course session " + sessionID + " cancelled successfully");
            
            }
            catch (java.sql.SQLException e2){
            System.out.println(e2.getMessage());
            
            } 
            break;

            
            case "SB": // See booked courses for member
            
            System.out.println ("Enter your member ID"); 
            memberID = Integer.parseInt(input.readLine());
            
            try {
            String seeBookings = "SELECT Course.courseName, CourseSession.couSesDate FROM Course, CourseSession, CourseEnrollment WHERE CourseSession.sessionID = CourseEnrollment.sessionID AND CourseSession.courseID = Course.courseID AND memberID = ?";             
            PreparedStatement pstmt = conn.prepareStatement(seeBookings);
            pstmt.setInt(1, memberID);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Your booked courses: " + "\n" + "Course name" + "   " + "Course date");
             
              while (rs.next()) {
               System.out.println (rs.getString("courseName") + "          " + rs.getInt("couSesDate"));
              }
               pstmt.close();
               rs.close();

            }
            catch (java.sql.SQLException e3) {
               System.out.println(e3.getMessage());
            }
            break;
            
            case "RM": // on course attendance

                    System.out.println("Enter sessionID");
                    sessionID = Integer.parseInt(input.readLine());

                    try {
                        String selef = "select count(memberID) from CourseEnrollment where sessionID = ?";
                        PreparedStatement pstmt = conn.prepareStatement(selef);
                        pstmt.setInt(1, sessionID);
                        ResultSet rs = pstmt.executeQuery();
                        while (rs.next()) {
                            System.out.println(rs.getInt("count(memberID)"));
                        }
                        pstmt.close();
                        rs.close();

                    } catch (java.sql.SQLException e3) {
                        System.out.println(e3.getMessage());
                    }
                    break;

                 
           case "Q":
            System.out.println("Exit program");
            fortsatt = false;
            break;

            default:
            System.out.println("Wrong choice");
            break;
          
         }
      }
   } 
}
                    
