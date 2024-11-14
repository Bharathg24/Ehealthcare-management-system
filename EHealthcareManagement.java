import java.sql.*;
import java.util.Scanner;

public class EHealthcareManagement {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/data";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "12345";

    private Connection connection;

    public EHealthcareManagement() {
        connect();
    }
    private void connect() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void checkConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerPatient(Scanner scanner) {
        checkConnection();
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Contact Number: ");
        String contact = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        String sql = "INSERT INTO patients (name, dob, gender, contact, address) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setDate(2, Date.valueOf(dob));
            pstmt.setString(3, gender);
            pstmt.setString(4, contact);
            pstmt.setString(5, address);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int patientId = rs.getInt(1);
                System.out.println("Patient registered successfully with ID: " + patientId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void scheduleAppointment(Scanner scanner) {
        checkConnection();

        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();

        scanner.nextLine(); 

        System.out.print("Enter Appointment Date (YYYY-MM-DD HH:MM:SS): ");
        String appointmentDate = scanner.nextLine();

        System.out.print("Enter Reason for Appointment: ");
        String reason = scanner.nextLine();

        String sql = "INSERT INTO appointments (patient_id, appointment_date, reason) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, patientId);
            pstmt.setString(2, appointmentDate);
            pstmt.setString(3, reason);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int appointmentId = rs.getInt(1);
                System.out.println("Appointment scheduled successfully with ID: " + appointmentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addEHR(Scanner scanner) {
        checkConnection();
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
    
        scanner.nextLine();
    
        System.out.print("Enter Record Date (YYYY-MM-DD HH:MM:SS): ");
        String recordDate = scanner.nextLine();
    
        System.out.print("Enter Diagnosis: ");
        String diagnosis = scanner.nextLine();
    
        System.out.print("Enter Treatment: ");
        String treatment = scanner.nextLine();
    
        System.out.print("Enter Notes: ");
        String notes = scanner.nextLine();
    
        String sql = "INSERT INTO ehr (patient_id, record_date, diagnosis, treatment, notes) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            pstmt.setString(2, recordDate);
            pstmt.setString(3, diagnosis);
            pstmt.setString(4, treatment);
            pstmt.setString(5, notes);
            pstmt.executeUpdate();
            System.out.println("Electronic Health Record added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void scheduleTelemedicineAppointment(Scanner scanner) {
        checkConnection();
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
    
        scanner.nextLine();
    
        System.out.print("Enter Appointment Date (YYYY-MM-DD HH:MM:SS): ");
        String appointmentDate = scanner.nextLine();
    
        System.out.print("Enter Reason for Appointment: ");
        String reason = scanner.nextLine();
    
        System.out.print("Enter Video Link: ");
        String videoLink = scanner.nextLine();
    
        String sql = "INSERT INTO telemedicine_appointments (patient_id, appointment_date, video_link, reason, status) VALUES (?, ?, ?, ?, 'Scheduled')";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            pstmt.setString(2, appointmentDate);
            pstmt.setString(3, videoLink);
            pstmt.setString(4, reason);
            pstmt.executeUpdate();
            System.out.println("Telemedicine appointment scheduled successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
    public void addPrescription(Scanner scanner) {
        checkConnection();
        System.out.print("Enter Appointment ID: ");
        int appointmentId = scanner.nextInt();

        scanner.nextLine();
        System.out.print("Enter Medicine Name: ");
        String medicine = scanner.nextLine();

        System.out.print("Enter Dosage: ");
        String dosage = scanner.nextLine();

        System.out.print("Enter Instructions: ");
        String instructions = scanner.nextLine();

        String sql = "INSERT INTO prescriptions (appointment_id, medicine, dosage, instructions) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            pstmt.setString(2, medicine);
            pstmt.setString(3, dosage);
            pstmt.setString(4, instructions);
            pstmt.executeUpdate();
            System.out.println("Prescription added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBilling(Scanner scanner) {
        checkConnection();
        System.out.print("Enter Appointment ID: ");
        int appointmentId = scanner.nextInt();

        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();

        scanner.nextLine(); 

        System.out.print("Enter Billing Date (YYYY-MM-DD HH:MM:SS): ");
        String billingDate = scanner.nextLine();

        System.out.print("Enter Status: ");
        String status = scanner.nextLine();

        String sql = "INSERT INTO billing (appointment_id, amount, billing_date, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, billingDate);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
            System.out.println("Billing information added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLabTest(Scanner scanner) {
        checkConnection();
        System.out.print("Enter Appointment ID: ");
        int appointmentId = scanner.nextInt();

        scanner.nextLine(); 

        System.out.print("Enter Test Name: ");
        String testName = scanner.nextLine();

        System.out.print("Enter Test Date (YYYY-MM-DD HH:MM:SS): ");
        String testDate = scanner.nextLine();

        System.out.print("Enter Result: ");
        String result = scanner.nextLine();

        String sql = "INSERT INTO lab_tests (appointment_id, test_name, test_date, result) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            pstmt.setString(2, testName);
            pstmt.setString(3, testDate);
            pstmt.setString(4, result);
            pstmt.executeUpdate();
            System.out.println("Lab test information added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePharmacyStock(Scanner scanner) {
        checkConnection();
        System.out.print("Enter Medicine Name: ");
        String medicine = scanner.nextLine();

        System.out.print("Enter Stock Amount: ");
        int stock = scanner.nextInt();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        scanner.nextLine(); 

        String sql = "INSERT INTO pharmacy (medicine, stock, price) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE stock = ?, price = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, medicine);
            pstmt.setInt(2, stock);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, stock);
            pstmt.setDouble(5, price);
            pstmt.executeUpdate();
            System.out.println("Pharmacy stock updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNotification(Scanner scanner) {
        checkConnection();
        System.out.print("Enter Notification Message: ");
        String message = scanner.nextLine();

        System.out.print("Enter Notification Date (YYYY-MM-DD HH:MM:SS): ");
        String notificationDate = scanner.nextLine();

        String sql = "INSERT INTO notifications (message, notification_date) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, message);
            pstmt.setString(2, notificationDate);
            pstmt.executeUpdate();
            System.out.println("Notification added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAnalytics(Scanner scanner) {
        checkConnection();
        System.out.print("Enter Metric: ");
        String metric = scanner.nextLine();

        System.out.print("Enter Value: ");
        String value = scanner.nextLine();

        System.out.print("Enter Date (YYYY-MM-DD HH:MM:SS): ");
        String date = scanner.nextLine();

        String sql = "INSERT INTO analytics (metric, value, date) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, metric);
            pstmt.setString(2, value);
            pstmt.setString(3, date);
            pstmt.executeUpdate();
            System.out.println("Analytics data added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSecurityLog(Scanner scanner) {
        checkConnection();
        System.out.print("Enter Log Message: ");
        String logMessage = scanner.nextLine();

        System.out.print("Enter Log Date (YYYY-MM-DD HH:MM:SS): ");
        String logDate = scanner.nextLine();

        String sql = "INSERT INTO security_logs (log_message, log_date) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, logMessage);
            pstmt.setString(2, logDate);
            pstmt.executeUpdate();
            System.out.println("Security log added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EHealthcareManagement system = new EHealthcareManagement();
        
        while (true) {
            System.out.println("Welcome to the E-Healthcare Management System");
            System.out.println("1. Register Patient");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. Add Prescription");
            System.out.println("4. Add Billing");
            System.out.println("5. Add Lab Test");
            System.out.println("6. Update Pharmacy Stock");
            System.out.println("7. Add Notification");
            System.out.println("8. Add Analytics Data");
            System.out.println("9. Add Security Log");
            System.out.println("10. Add Electronic Health Record (EHR)");
            System.out.println("11. Schedule Telemedicine Appointment");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
    
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    system.registerPatient(scanner);
                    break;
                case 2:
                    system.scheduleAppointment(scanner);
                    break;
                case 3:
                    system.addPrescription(scanner);
                    break;
                case 4:
                    system.addBilling(scanner);
                    break;
                case 5:
                    system.addLabTest(scanner);
                    break;
                case 6:
                    system.updatePharmacyStock(scanner);
                    break;
                case 7:
                    system.addNotification(scanner);
                    break;
                case 8:
                    system.addAnalytics(scanner);
                    break;
                case 9:
                    system.addSecurityLog(scanner);
                    break;
                case 10:
                    system.addEHR(scanner);
                    break;
                case 11:
                    system.scheduleTelemedicineAppointment(scanner);
                    break;
                case 0:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    return; 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}    