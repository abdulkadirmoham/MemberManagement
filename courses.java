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
    static int couSesDate;
    static int couSesTime;
    static int sessionID;
    static int currentDate;
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
      
         System.out.println("P - New member");
         System.out.println("U - Update Phone number for member");
         System.out.println("G - Update Email for member");
         System.out.println("N - Update Address for member");
         System.out.println("M - Update membership type");
         System.pit.println("B - Book course")
         System.out.println("C - Cancel membership");
         System.out.println("R - Report on new members");
    	 /*  System.out.println("L - Se tidrapporter for person");
    	   System.out.println("S - Se summa arbetade timmar");
    	   System.out.println("A - Se alla personer och deras tidsrapporter"); */
    	   System.out.println("Q - Quit");
         
         String val2 = input.readLine();
         char val = val2.charAt(0);   
         
         switch (val) {
             case 'B':

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

                 System.out.println("Enter SessionID");
                 String sessionID = input.readLine();

                 try {
                     String inserth = "INSERT INTO CourseSessionAttendance(memberID, sessionID) VALUES(?,?)";
                     PreparedStatement pstmt = conn.prepareStatement(inserth);
                     pstmt.setInt(1, memberID);
                     pstmt.setString(2, sessionID);
                     pstmt.executeUpdate();
                     pstmt.close();
                 }
                 catch (java.sql.SQLException e2){
                     System.out.println(e2.getMessage());
                 }
                 break;
         }
