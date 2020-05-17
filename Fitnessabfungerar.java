import java.io.*;
import java.sql.*;
import org.sqlite.SQLiteConfig;

public class Fitnessab {
 
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

   
 
   // Sokvog till SQLite-databas. OBS! andra sokvag sa att den pekar ut din databas
   public static final String DB_URL = "jdbc:sqlite:fitnessdb.db";
   // Namnet på den driver som används av java för att prata med SQLite
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
      
         System.out.println("P - Ny medlem");
    	   System.out.println("T - Ny tidrapport");
    	   System.out.println("L - Se tidrapporter for person");
    	   System.out.println("S - Se summa arbetade timmar");
    	   System.out.println("A - Se alla personer och deras tidsrapporter");
    	   System.out.println("Q - Avsluta");
         
         String val2 = input.readLine();
         char val = val2.charAt(0);   
         
         switch (val) {
            case 'P':
            
            System.out.println("Enter Date of birth (YYYYMMDD)");
            int dateOfBirth = Integer.parseInt(input.readLine());
            
            System.out.println("Enter First name");
            String mFirstName = input.readLine();
            
            System.out.println("Enter Last name");
            String mLastName = input.readLine();
            
            System.out.println("Enter Email");
            String mEmail = input.readLine();
            
            System.out.println("Enter Phone number");
            int mPhoneNo = Integer.parseInt(input.readLine());
                        
            System.out.println("Enter Address");
            String mAddress = input.readLine();
                        
            System.out.println("Enter Zipcode");
            int mZipCode = Integer.parseInt(input.readLine());
            
            System.out.println("Enter Join date");
            int joinDate = Integer.parseInt(input.readLine());
            
            System.out.println("Enter End date");
            int endDate = Integer.parseInt(input.readLine());
            if (endDate==0){
            
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
                  pstmt.setInt(8, joinDate);           
                  pstmt.executeUpdate();
                  pstmt.close();
                  }
               catch (java.sql.SQLException e1){
                  System.out.println(e1.getMessage());         
                  }
               }
                  else {
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
                  pstmt.setInt(9, endDate);
                pstmt.executeUpdate();
                pstmt.close();
             }
             catch (java.sql.SQLException e1){
                System.out.println(e1.getMessage());         
                }
            }
            break;
   
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
            break;
     
            case 'Q':
            System.out.println("avslutar program");
            fortsatt = false;
            break;

            default:
            System.out.println("fel val");
            break;
            
         }
      }
   } 
}
   
