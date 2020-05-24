import java.io.*;
import java.sql.*;
import org.sqlite.SQLiteConfig;
import java.util.*;

public class courses {
 
      static int dateOfBirth;
      static String mFirstName;
      static String mLastName;
      static String mAddress;
      static int mZipCode;
      static int mPhoneNo;
      static int joinDate;
      static int endDate; 
      static String mEmail;
   
      // Payment
      static int memberID;
      static int paymentID;
      static String paymethod;
      static int paymentDate;
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
   public static final String DB_URL = "jdbc:sqlite:C:/programmering/membership_course_db4.2.db";
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
    	   System.out.println("Q - Quit");
         
         String val = input.readLine();
         
         switch (val) {
             case "BC": // Book course

                 System.out.println("Enter memberID");
                 int memberID = Integer.parseInt(input.readLine());
                 System.out.println("Enter current date(YYYYMMDD)");
                 int currentDate = Integer.parseInt(input.readLine());
                 try {
                     String selectCourses = "select CourseSession.sessionID, Course.courseName, CourseSession.couSesDate, CourseSession.couSesTime from CourseSession,Course where Course.courseID=CourseSession.courseID AND CousesDate > ? order by CouSesDate ASC;";
                     PreparedStatement pstmt = conn.prepareStatement(selectCourses);
                     pstmt.setInt(1, currentDate);
                     ResultSet rs = pstmt.executeQuery();
                     System.out.println("Session, Course, Date, Time");
                     while (rs.next()) {
                         System.out.println(rs.getInt("sessionID") + " " + rs.getString("courseName") + " " + rs.getInt("couSesDate") + " " + rs.getInt("couSesTime"));
                     }
                     pstmt.close();
                     rs.close();
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
                    
