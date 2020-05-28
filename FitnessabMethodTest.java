import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import org.sqlite.SQLiteConfig;
import java.text.SimpleDateFormat;
import java.util.*;

   public class FitnessabMethodTest {     // Base code is from our previous project in TIG058
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
    static String mPw;
    // Payment
    static int memberID;
    static int paymentID;
    static String paymentmethod;
    static int paymentDate;
    // Facility
    static int facilityID;
    static String fAddress;
    static String fPhone;
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
    //Schedule
    static int scheduleDate;
    static String scheduleTime;
    static int sessionID;
    static int getTodaysdate(){
      String str = LocalDate.now().toString();
      str = str.replace( "-" , "");
      return Integer.parseInt(str);
       }
    static String addOneMonth()  {
        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);        //https://beginnersbook.com/2013/05/simple-date-format-java/
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
          return sdf.format(cal.getTime());
     }
    //Connection
    static String databas;
    static PreparedStatement send;
    static Connection conn = null;
    static String str;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    

    // Sokvog till SQLite-databas. OBS! andra sokvag sa att den pekar ut din databas
    public static final String DB_URL = "jdbc:sqlite:C:/programmering/membership_course_db4.7.db";
    // Namnet p� den driver som anv�nds av java f�r attprata med SQLite
    public static final String DRIVER = "org.sqlite.JDBC";

    public static void main(String[] args) throws IOException {

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

        boolean fortsatt = true;

        while (fortsatt) {
            System.out.println("MAIN MENU");
            System.out.println("P - New member");
            System.out.println("CX - Create payment");
            System.out.println("ND - update payment method");
            System.out.println("U - Update Phone number for member");
            System.out.println("G - Update Email for member");
            System.out.println("N - Update Address for member");
            System.out.println("M - Update membership type");
            System.out.println("C - Cancel membership");
            System.out.println("R - Report on new members");
            System.out.println("RM - Report on course attendance");
            System.out.println("NE - Add new employee");
            
            
    	 /*  System.out.println("L - Se tidrapporter for person");
    	   System.out.println("S - Se summa arbetade timmar");
    	   System.out.println("A - Se alla personer och deras tidsrapporter"); */
            System.out.println("Q - Quit");
            
           

         String val = input.readLine();
         
         switch (val.toUpperCase()) {
            
             
                case "P": // New member
                newMember();
                break;
                
                case "CX": //Create payment
                createPayment();
                break;

                case "G"://update email
                updatEmail();
                break;

                case "U"://update phoneNO
                updatePhoneNo();
                break;

                case "N"://update address
                updateAddress();
                break;
 
                case "M"://update membership type
                updateMembershipType();
                break;

                case "C"://cancel membership
                cancelMembership();
                break;

                case "R": //report on new members
                createReportMember();
                break;
               
               case "NE": //New employee
               newEmployee();   
               break;
               
               case "ND": //update payment
               updatepayment();
               break;
               
               case "Q":
               System.out.println("Program terminated");
               fortsatt = false;
               break;

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
                            
                           System.out.println ("Welcome as new member to FitnessAB, please go to main menu and choose CX to choose your payment method\n");
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
                            
                           System.out.println ("Welcome as new member to FitnessAB, please go to main menu and choose CX to choose your payment method\n");
                        } catch (java.sql.SQLException e1) {
                            System.out.println(e1.getMessage());
                        }
                    }
                  }
        public static void updatEmail() throws IOException { // funkar
           
           System.out.println("Enter memberID");
           memberID = Integer.parseInt(input.readLine());
           System.out.println("Enter Email");
           mEmail = input.readLine();
           
           try {
            String insertz = "UPDATE Member SET mEmail=? WHERE memberID= ?";
            PreparedStatement pstmt = conn.prepareStatement(insertz);
            pstmt.setString(1, mEmail);
            pstmt.setInt(2, memberID);
            int rowAffected = pstmt.executeUpdate();                              //https://stackoverflow.com/questions/2571915/return-number-of-rows-affected-by-sql-update-statement-in-java
            System.out.println(String.format("Row affected %d", rowAffected));

              } 
              catch (java.sql.SQLException e2) {
              System.out.println(e2.getMessage());
            }
          }
          
       public static void updatePhoneNo() throws IOException { // funkar 
         System.out.println("Enter memberID");
         memberID = Integer.parseInt(input.readLine());
         System.out.println("Enter Phone number");
         mPhoneNo = input.readLine();
         
         
       try {
          String inserty = "UPDATE Member SET mPhoneNo=? WHERE memberID= ?";
          PreparedStatement pstmt = conn.prepareStatement(inserty);
          pstmt.setString(1, mPhoneNo);
          pstmt.setInt(2, memberID);
          
          int rowAffected = pstmt.executeUpdate();                             //https://stackoverflow.com/questions/2571915/return-number-of-rows-affected-by-sql-update-statement-in-java
          System.out.println(String.format("Row affected %d", rowAffected));
            } 
           catch (java.sql.SQLException e2) {
           System.out.println(e2.getMessage());
           }
         }
      
       public static void updateAddress() throws IOException { //funkar
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

       } 
       catch (java.sql.SQLException e2) {
       System.out.println(e2.getMessage());
         }
        }
      
      public static void updateMembershipType() throws IOException { //funkar
        try {
         System.out.println("Enter memberID");
         memberID = Integer.parseInt(input.readLine());

         System.out.println("Enter desired membership type \nChoose between Gold, Silver and Bronze");
         typeID = input.readLine();


         String insertg = "UPDATE Membership SET typeID=? WHERE memberID= ?";
         PreparedStatement pstmt = conn.prepareStatement(insertg);
         pstmt.setString(1, typeID);
         pstmt.setInt(2, memberID);

         int rowAffected = pstmt.executeUpdate();
         System.out.println(String.format("Row affected %d", rowAffected));

         } 
         catch (java.sql.SQLException e2) {
         System.out.println(e2.getMessage());
       }
     }
     
     public static void cancelMembership() throws IOException { //funkar, lade in text som skriver ut n�r medlemskap avslutas
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
         System.out.println(String.format("Your membership ends: " + addOneMonth()));

         } 
         catch (java.sql.SQLException e2) {
         System.out.println(e2.getMessage());
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
    public static void updatepayment() throws IOException { 
      System.out.println("Enter payment ID");
      paymentID = Integer.parseInt(input.readLine());
                    /*System.out.println("Enter endDate");
                    endDate = Integer.parseInt(input.readLine());*/
         System.out.println("Enter payment method");
         paymentmethod = input.readLine();
         
       try {
         String insertq = "UPDATE Payment SET paymentmethod=? WHERE paymentID= ?";
         PreparedStatement pstmt = conn.prepareStatement(insertq);
         pstmt.setString(1, paymentmethod);
         pstmt.setInt(2, paymentID);

         pstmt.executeUpdate();
         System.out.println("Payment method is updated!");

         } 
         catch (java.sql.SQLException e2) {
         System.out.println(e2.getMessage());
       }
      }
   public static void newEmployee() throws IOException { //funkar
   
            System.out.println("Enter facilityID");
            int facilityID = Integer.parseInt(input.readLine());
            
            System.out.println("Enter eFirst name");
            String eFirstName = input.readLine();
            
            System.out.println("Enter eLast name");
            String eLastName = input.readLine();
            
            System.out.println("Enter position");
            String position = input.readLine();
            
            System.out.println("Enter ePhone number");
            ePhoneNo = input.readLine();
                        
            System.out.println("Enter eEmail");
            String eEmail = input.readLine();
            
            System.out.println("Choose password");
            String ePw = input.readLine();
                        
                       
               try {
                  String insertp = "INSERT INTO Employee (facilityID, eFirstName, eLastName, position, ePhoneNo, eEmail, ePw) VALUES (?,?,?,?,?,?,?);";
                  PreparedStatement pstmt = conn.prepareStatement(insertp);
                  pstmt.setInt(1, facilityID);
                  pstmt.setString(2, eFirstName);
                  pstmt.setString(3, eLastName);
                  pstmt.setString(4, position);
                  pstmt.setString(5, ePhoneNo);
                  pstmt.setString(6, eEmail);
                  pstmt.setString(7, ePw);
                           
                  pstmt.executeUpdate();
                  pstmt.close();
                  }
               catch (java.sql.SQLException e1){
                  System.out.println(e1.getMessage());         
         }
       }
       
       public static void createPayment() throws IOException {
       try {
       System.out.println("Enter member ID"); 
       memberID = Integer.parseInt(input.readLine()); 
       System.out.println ("Enter payment method, choose between: Direct debit, Invoice, Cashier"); 
       paymentMethod = input.readLine(); 
       
       String payment = "INSERT INTO Payment (memberID, paymentMethod, payDate) VALUES (?,?,?)"; 
       PreparedStatement pstmt = conn.prepareStatement(payment);
       pstmt.setInt(1, memberID);
       pstmt.setString(2, paymentMethod);
       pstmt.setInt(3, getTodaysdate()); 
       pstmt.executeUpdate();
       pstmt.close(); 
        
       System.out.print ("Payment created successfully.   \n");  
      }
       catch (java.sql.SQLException e2) {
       System.out.println(e2.getMessage());
         }
       }
     }
       
      /*     GAMMAL KOD FR�N TIG058
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