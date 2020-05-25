import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import org.sqlite.SQLiteConfig;
import java.text.SimpleDateFormat;
import java.util.*;

public class Fitnessabfungerar {

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
    //Schedule
    static int scheduleDate;
    static String scheduleTime;
    static int sessionID;
    

    static String databas;
    static PreparedStatement send;
    static Connection conn = null;
    static String str;
    static int getTodaysdate(){
               
               String str = LocalDate.now().toString();
               str = str.replace( "-" , "");
               return Integer.parseInt(str);
               }
    static String addOneMonth()  {
        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        
        
             
        return sdf.format(cal.getTime());
        
}




    // Sokvog till SQLite-databas. OBS! andra sokvag sa att den pekar ut din databas
    public static final String DB_URL = "jdbc:sqlite:C:/programmering/membership_course_db4.4.db";
    // Namnet på den driver som används av java för attprata med SQLite
    public static final String DRIVER = "org.sqlite.JDBC";

    public static void main(String[] args) throws IOException {
        Connection conn = null;

        // Kod for att skapa uppkoppling mot SQLite-dabatasen
        try {
            Class.forName(DRIVER);
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true); // Denna kodrad ser till att satta databasen i ett lage dar den ger felmeddelande ifall man bryter mot nagon frammande-nyckel-regel
            conn = DriverManager.getConnection(DB_URL, config.toProperties());
        } catch (Exception e) {
            // Om java-progammet inte lyckas koppla upp sig mot databasen (t ex om fel sokvag eller om driver inte hittas) sa kommer ett felmeddelande skrivas ut
            System.out.println(e.toString());
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
            System.out.println("C - Cancel membership");
            System.out.println("B - Book course");
            System.out.println("R - Report on new members");
            System.out.println("RM - Report on course attendance");
            
    	 /*  System.out.println("L - Se tidrapporter for person");
    	   System.out.println("S - Se summa arbetade timmar");
    	   System.out.println("A - Se alla personer och deras tidsrapporter"); */
            System.out.println("Q - Quit");
            
           

           String val = input.readLine();

            switch (val) {
            
             
                case "P":

                    System.out.println("Enter Date of birth (YYYYMMDD)");
                    int dateOfBirth = Integer.parseInt(input.readLine());

                    System.out.println("Enter First name");
                    String mFirstName = input.readLine();

                    System.out.println("Enter Last name");
                    String mLastName = input.readLine();

                    System.out.println("Enter Email");
                    String mEmail = input.readLine();

                    System.out.println("Enter Phone number");
                    mPhoneNo = Integer.parseInt(input.readLine());

                    System.out.println("Enter Address");
                    String mAddress = input.readLine();

                    System.out.println("Enter Zipcode");
                    int mZipCode = Integer.parseInt(input.readLine());                   
                                   
                       

                    System.out.println("Enter End date");
                    endDate = input.readLine();
                    
                    if (endDate.equals(0)) {

                        try {
                            String insertp = "INSERT INTO Member(dateOfBirth, mFirstName, mLastName, mAddress, mZipCode, mPhoneNo, mEmail, joinDate) VALUES(?,?,?,?,?,?,?,?)";
                            PreparedStatement pstmt = conn.prepareStatement(insertp);
                            pstmt.setInt(1, dateOfBirth);
                            pstmt.setString(2, mFirstName);
                            pstmt.setString(3, mLastName);
                            pstmt.setString(4, mAddress);
                            pstmt.setInt(5, mZipCode);
                            pstmt.setInt(6, mPhoneNo);
                            pstmt.setString(7, mEmail);
                            pstmt.setInt(8, getTodaysdate());
                            pstmt.executeUpdate();
                            pstmt.close();
                        } catch (java.sql.SQLException e1) {
                            System.out.println(e1.getMessage());
                        }
                    } else {
                        try {
                            String insertp = "INSERT INTO Member(dateOfBirth, mFirstName, mLastName, mAddress, mZipCode, mPhoneNo, mEmail, joinDate, endDate) VALUES(?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pstmt = conn.prepareStatement(insertp);
                            pstmt.setInt(1, dateOfBirth);
                            pstmt.setString(2, mFirstName);
                            pstmt.setString(3, mLastName);
                            pstmt.setString(4, mAddress);
                            pstmt.setInt(5, mZipCode);
                            pstmt.setInt(6, mPhoneNo);
                            pstmt.setString(7, mEmail);
                            pstmt.setInt(8, joinDate);
                            pstmt.setString(9, endDate);
                            pstmt.executeUpdate();
                            pstmt.close();
                        } catch (java.sql.SQLException e1) {
                            System.out.println(e1.getMessage());
                        }
                    }
                    break;

                case "G"://update email
                    System.out.println("Enter memberID");
                    memberID = Integer.parseInt(input.readLine());

                    System.out.println("Enter Email");
                    mEmail = input.readLine();

                    try {
                        String insertz = "UPDATE Member SET mEmail=? WHERE memberID= ?";
                        PreparedStatement pstmt = conn.prepareStatement(insertz);
                        pstmt.setString(1, mEmail);
                        pstmt.setInt(2, memberID);

                        int rowAffected = pstmt.executeUpdate();
                        System.out.println(String.format("Row affected %d", rowAffected));

                    } catch (java.sql.SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                    break;

                case "U"://update phoneNO
                    System.out.println("Enter memberID");

                    System.out.println("Enter Phone number");
                    mPhoneNo = Integer.parseInt(input.readLine());

                    try {
                        String inserty = "UPDATE Member SET mPhoneNo=? WHERE memberID= ?";
                        PreparedStatement pstmt = conn.prepareStatement(inserty);
                        pstmt.setInt(1, mPhoneNo);
                        pstmt.setInt(2, memberID);

                        int rowAffected = pstmt.executeUpdate();
                        System.out.println(String.format("Row affected %d", rowAffected));

                    } catch (java.sql.SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                    break;

                case "N"://update address
                    System.out.println("Enter memberID");
                    memberID = Integer.parseInt(input.readLine());

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

                        int rowAffected = pstmt.executeUpdate();
                        System.out.println(String.format("Row affected %d", rowAffected));

                    } catch (java.sql.SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                    break;

                case "B":

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
                    } catch (java.sql.SQLException e2) {
                        System.out.println(e2.getMessage());
                    }

                    

                    try {
                        String inserth = "INSERT INTO CourseSessionAttendance(memberID, sessionID) VALUES(?,?)";
                        PreparedStatement pstmt = conn.prepareStatement(inserth);
                        pstmt.setInt(1, memberID);
                        pstmt.setInt(2, sessionID);
                        pstmt.executeUpdate();
                        pstmt.close();
                    } catch (java.sql.SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                    break;
 
                case "M"://update membership type
                    System.out.println("Enter memberID");
                    memberID = Integer.parseInt(input.readLine());

                    System.out.println("Enter desired membership type \nChoose between Gold, Silver and Bronze");
                    typeID = input.readLine();


                    try {
                        String insertg = "UPDATE Membership SET typeID=? WHERE memberID= ?";
                        PreparedStatement pstmt = conn.prepareStatement(insertg);
                        pstmt.setString(1, typeID);
                        pstmt.setInt(2, memberID);

                        int rowAffected = pstmt.executeUpdate();
                        System.out.println(String.format("Row affected %d", rowAffected));

                    } catch (java.sql.SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                    break;

                case "C"://cancel membership

                    System.out.println("Enter memberID");
                    memberID = Integer.parseInt(input.readLine());
                    
                    

                    /*System.out.println("Enter endDate");
                    endDate = Integer.parseInt(input.readLine());*/

                    try {
                        String inserta = "UPDATE Member SET endDate=? WHERE memberID= ?";
                        PreparedStatement pstmt = conn.prepareStatement(inserta);
                        pstmt.setString(1, addOneMonth());
                        pstmt.setInt(2, memberID);

                        int rowAffected = pstmt.executeUpdate();


                    } catch (java.sql.SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                    break;

                case "R": //report on new members

                    System.out.println("Enter join date in format YYYYMMDD");
                    joinDate = Integer.parseInt(input.readLine());

                    try {
                        String selectg = "select * FROM MEMBER WHERE joinDate >= ?";
                        PreparedStatement pstmt = conn.prepareStatement(selectg);
                        pstmt.setInt(1, joinDate);
                        ResultSet rs = pstmt.executeQuery();
                        System.out.println("memberID, date of birth, first name, last name, address, Zip code, phone number, email, join date, end date");
                        while (rs.next()) {
                            
                            System.out.println(rs.getInt("memberID") + " " + rs.getInt("dateOfBirth") + " " + rs.getString("mFirstName") + " " + rs.getString("mLastName") + " " + rs.getString("mAddress") + " " + rs.getInt("mZipCode") + " " + rs.getInt("mPhoneNo") + " " + rs.getString("mEmail") + " " + rs.getInt("joinDate") + " " + rs.getString("endDate"));
                        }
                        pstmt.close();
                        rs.close();

                    } catch (java.sql.SQLException e3) {
                        System.out.println(e3.getMessage());
                    }
                    break;

                case "T":
                    System.out.println("Ange Personnr (YYYYMMDD)");
                    int pnr2 = Integer.parseInt(input.readLine());
                    System.out.println("Ange Datum");
                    String Datum = input.readLine();
                    System.out.println("Ange Antal Timmar");
                    String AntalTimmar = input.readLine();
                    try {
                        String inserth = "INSERT INTO Tidbok(PNr, Datum, AntalTimmar) VALUES(?,?,?)";
                        PreparedStatement pstmt = conn.prepareStatement(inserth);
                        pstmt.setInt(1, pnr2);
                        pstmt.setString(2, Datum);
                        pstmt.setString(3, AntalTimmar);
                        pstmt.executeUpdate();
                        pstmt.close();
                    } catch (java.sql.SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                    break;

                case "L":
                    System.out.println("Ange Personnr (YYYYMMDD)");
                    int pnr4 = Integer.parseInt(input.readLine());

                    try {
                        String select1 = "select Tidbok.PNr, Tidbok.Datum, Tidbok.AntalTimmar from Tidbok where PNr = ? Order by Datum";
                        PreparedStatement pstmt = conn.prepareStatement(select1);
                        pstmt.setInt(1, pnr4);
                        ResultSet rs = pstmt.executeQuery();
                        while (rs.next()) {
                            System.out.println(rs.getString("PNr") + " " + rs.getString("Datum") + " " + rs.getString("Antaltimmar"));
                        }
                        pstmt.close();
                        rs.close();

                    } catch (java.sql.SQLException e3) {
                        System.out.println(e3.getMessage());
                    }
                    break;

                case "S":
                    System.out.println("Ange Personnr (YYYYMMDD)");
                    int pnr3 = Integer.parseInt(input.readLine());
                    try {
                        String select1 = "select Tidbok.PNr, sum(Tidbok.AntalTimmar) from Tidbok where PNr = ?";
                        PreparedStatement pstmt = conn.prepareStatement(select1);
                        pstmt.setInt(1, pnr3);
                        ResultSet rs = pstmt.executeQuery();
                        System.out.println(rs.getString("PNr") + " " + rs.getString("sum(Tidbok.AntalTimmar)"));
                        pstmt.close();
                        rs.close();

                    } catch (java.sql.SQLException e4) {
                        System.out.println(e4);
                    }
                    break;
                case "A":

                    try {

                        String select1 = "select Person.PNr, FNamn, ENamn, Datum, AntalTimmar from Person left outer join Tidbok on Person.PNr=Tidbok.PNr";
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(select1);
                        while (rs.next()) {
                            System.out.println(rs.getString("PNr") + " " + rs.getString("FNamn") + " " + rs.getString("ENamn") + " " + rs.getString("Datum") + " " + rs.getString("AntalTimmar"));
                        }
                        stmt.close();
                        rs.close();

                    } catch (java.sql.SQLException e5) {
                        System.out.println(e5);
                    }
                    break;
                    
                    

                case "Q":
                    System.out.println("exit program");
                    fortsatt = false;
                    break;

                default:
                    System.out.println("fel val");
                    break;

            }
        }
    }
}
   
            
       /*     GAMMAL KOD FRÅN TIG058
            case 'T':
            System.out.println("Ange Personnr (YYYYMMDD)");
            int pnr2 = Integer.parseInt(input.readLine());
            System.out.println("Ange Datum");
            String Datum = input.readLine();
            System.out.println("Ange Antal Timmar");
            String AntalTimmar = input.readLine();
            try {
               String inserth = "INSERT INTO Tidbok(PNr, Datum, AntalTimmar) VALUES(?,?,?)";
               PreparedStatement pstmt = conn.prepareStatement(inserth);
               pstmt.setInt(1, pnr2);
               pstmt.setString(2, Datum);
               pstmt.setString(3, AntalTimmar);
               pstmt.executeUpdate();
               pstmt.close();
            }
            catch (java.sql.SQLException e2){
               System.out.println(e2.getMessage());
            }
            break;

            case 'L':
            System.out.println("Ange Personnr (YYYYMMDD)");
            int pnr4 = Integer.parseInt(input.readLine());

            try {
               String select1 = "select Tidbok.PNr, Tidbok.Datum, Tidbok.AntalTimmar from Tidbok where PNr = ? Order by Datum";
               PreparedStatement pstmt = conn.prepareStatement(select1);
               pstmt.setInt(1, pnr4);
               ResultSet rs = pstmt.executeQuery();
               while (rs.next()) {
               System.out.println(rs.getString("PNr") + " " + rs.getString("Datum") + " " + rs.getString("Antaltimmar"));
               }
               pstmt.close();
               rs.close();

            }
            catch (java.sql.SQLException e3) {
               System.out.println(e3.getMessage());
            }
            break;

            case 'S':
               System.out.println("Ange Personnr (YYYYMMDD)");
               int pnr3 = Integer.parseInt(input.readLine());
               try {
               String select1 = "select Tidbok.PNr, sum(Tidbok.AntalTimmar) from Tidbok where PNr = ?";
               PreparedStatement pstmt = conn.prepareStatement(select1);
               pstmt.setInt(1, pnr3);
               ResultSet rs = pstmt.executeQuery();
               System.out.println(rs.getString("PNr") + " " + rs.getString("sum(Tidbok.AntalTimmar)"));
               pstmt.close();
               rs.close();
               
            }
            catch (java.sql.SQLException e4) {
               System.out.println(e4);
            }
            break;
            case 'A':

            try {
            
               String select1 = "select Person.PNr, FNamn, ENamn, Datum, AntalTimmar from Person left outer join Tidbok on Person.PNr=Tidbok.PNr";
               Statement stmt = conn.createStatement();
               ResultSet rs = stmt.executeQuery(select1);
               while (rs.next()) {
                  System.out.println(rs.getString("PNr") + " " + rs.getString("FNamn") + " " + rs.getString("ENamn") + " " + rs.getString("Datum") + " " + rs.getString("AntalTimmar"));
               }
               stmt.close();
               rs.close();
               
            }
            catch (java.sql.SQLException e5) {
               System.out.println(e5);
            }
            break; */