import java.io.*;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Sample {

    // Hardcoded Credentials
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    public static void main(String[] args) throws Exception {

        // Hardcoded Credential
        System.out.println(USERNAME + ":" + PASSWORD);

        // Command Injection
        if (args.length > 0) {
            Runtime.getRuntime().exec("ping " + args[0]);
        }

        // Path Traversal
        if (args.length > 1) {
            File file = new File("C:\temp\" + args[1]);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            System.out.println(reader.readLine());
            reader.close();
        }

        // Weak Hash Algorithm (MD5)
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.digest("Checkmarx".getBytes());

        // Weak Random
        java.util.Random random = new java.util.Random();
        int token = random.nextInt();
        System.out.println(token);

        // SQL Injection
        if (args.length > 2) {
            String id = args[2];

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/test",
                    USERNAME,
                    PASSWORD);

            Statement stmt = conn.createStatement();
            stmt.executeQuery("SELECT * FROM users WHERE id = '" + id + "'");

            stmt.close();
            conn.close();
        }

        // Information Exposure
        try {
            throw new Exception("Test Exception");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
