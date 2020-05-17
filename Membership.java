import java.io.*;
import java.sql.*;
import org.sqlite.SQLiteConfig;
import java.util.*;


public class Membership {


   // S�kv�g till SQLite-databas. OBS! �ndra s�kv�g s� att den pekar ut din databas
   public static final String DB_URL = "jdbc:sqlite:C:/programmering/membership_course_db4.2";  
   // Namnet p� den driver som anv�nds av java f�r att prata med SQLite
   public static final String DRIVER = "org.sqlite.JDBC"; 
   static Scanner sc = new Scanner(System.in);
  
  //member
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
   
   static String databas;
   static PreparedStatement send; 
   static Connection conn = null;

   public static void main(String[] args) throws IOException {
     

      

      // Kod f�r att skapa uppkoppling mot SQLite-dabatasen
      try {
         Class.forName(DRIVER);
         SQLiteConfig config = new SQLiteConfig();  
         config.enforceForeignKeys(true); // Denna kodrad ser till att s�tta databasen i ett l�ge d�r den ger felmeddelande ifall man bryter mot n�gon fr�mmande-nyckel-regel
         conn = DriverManager.getConnection("jdbc:sqlite:C:/programmering/membership_course_db4.2",config.toProperties());  
      } catch (Exception e) {
         // Om java-progammet inte lyckas koppla upp sig mot databasen (t ex om fel s�kv�g eller om driver inte hittas) s� kommer ett felmeddelande skrivas ut
         System.out.println( e.toString() );
         System.exit(0);
      }
      
      
      
      // PLATS F�R DEKLARATIONER OCH KOD //
       while(true) {
       System.out.println("Tidrapporteringsprogram");
       System.out.println("P - Ny person" + "\n" + "T - Ny tidrapport" + "\n" + "L - Se tidrapport" + "\n" + "S - Se summan" + "\n" + "A - Se alla tidrapporter" + "\n" + "Q - avbryt");
      
       String input = sc.next();
       switch(input.toUpperCase()) {
        case "P":
         newMember();
        break;
          
       
        case "Q":
         System.out.println("Du avslutade programmet");
          System.exit(0);
        default:
         System.out.println("Ditt val �r fel");
      }
    }
  }

 public static void newMember() {
  try { 
  System.out.println("Enter date of birth in YYYYmmdd");
   dateOfBirth = sc.nextInt();
    sc.nextLine(); 
    
  System.out.println("Enter first name");
     mFirstName = sc.nextLine();
         sc.nextLine(); 

           
  System.out.println("Enter last name");
    mLastName = sc.nextLine();
        sc.nextLine(); 

 
 System.out.println("Enter address");
    mAddress = sc.nextLine(); 

    
    System.out.println("Enter Zip Code");
    mZipCode = sc.nextInt();
    
    System.out.println("Enter phone number");
    mPhoneNo = sc.nextInt();
    sc.nextLine(); 
    
    System.out.println("Enter email");
    mEmail = sc.nextLine();
    
    System.out.println("Enter join date");
    joinDate = sc.nextInt();
    
    System.out.println("Enter end date");
    endDate = sc.nextInt();
    
  databas = "INSERT INTO Member (dateOfBirth , mFirstName, mLastName, mAddress, mZipCode, mPhoneNo, joinDate, endDate)" + "VALUES("+ dateOfBirth +",'"+ mFirstName + ",'" + mLastName + ",'" + mAddress + ",'" + mZipCode + ",'" + mPhoneNo +",'" + joinDate + ",'" + endDate + "')";

  send=conn.prepareStatement(databas);
  send.executeUpdate();
  }
  catch(SQLException SE) {
   System.out.println(SE);
  
  }
  
   }
 }
 
         

  
  

