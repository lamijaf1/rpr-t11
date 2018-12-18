package ba.unsa.etf.rpr;
//POSTOJI PROBLEM KOD KREIRANJA BAZE
import javafx.beans.property.SimpleObjectProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GeografijaDAO {
    ArrayList<Grad> gradovi = new ArrayList<>();
    ArrayList<Drzava> drzave = new ArrayList<>();
    private static GeografijaDAO instance = null;

    private static final String INSERT_SQL1 = "INSERT INTO Remuneraciones(id, naziv, , glavni_grad) VALUES(?, ?, ?)";
    private static final String INSERT_SQL2 = "INSERT INTO Remuneraciones(id, naziv, , broj_stanovnika, drzava) VALUES(?, ?, ?,?)";
    private static Connection conn;  /* i ostalo što treba za bazu */
    public String url = "jdbc:sqlite:resources/baza.db";
    //com.mysql.cj.jdbc.Driver
    private PreparedStatement stmt1, stmt2;


    public GeografijaDAO() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:sqlite:resources/baza.db");
            stmt1 = conn.prepareStatement("SELECT id, naziv,  broj_stanovnika FROM main.grad WHERE main.drzava.naziv= ?");
            ResultSet rs = stmt1.executeQuery();
            while (rs.next()) {
                // System.out.println()
                Grad k = new Grad(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
                gradovi.add(k);
            }
            // ne znam postaviti strani kljuc za ovaj slucaj pa ne mogu odabrati dobro
            stmt2 = conn.prepareStatement("SELECT id, naziv FROM main.drzava WHERE main.grad=?");
            ResultSet rs1 = stmt2.executeQuery();
            while (rs1.next()) {
                // System.out.println()
                Drzava d = new Drzava(rs1.getInt(1), rs1.getString(2), rs1.getString(3));
                drzave.add(d);
            }

        } catch (Exception e) {
            System.out.println("Neuspješno čitanje iz baze: " + e.getMessage());
        }

    }


    public  static  GeografijaDAO getInstance() {
        if (instance == null) initialize();
        return instance;
    }

    public  static void removeInstance() {
        instance = null;
    }


    private  static void initialize() {
        if(instance==null){
            createNewDatabase("baza");
            createNewTable();
            insertToDatabase();;
        }
        instance = new GeografijaDAO();
    }



    public ArrayList<Grad> gradovi() {
        Collections.sort(gradovi, new Comparator<Grad>() {
            @Override
            public int compare(Grad z1, Grad z2) {
                if (z1.getBrojStanovnika() > z2.getBrojStanovnika()) return -1;
                if (z1.getBrojStanovnika() < z2.getBrojStanovnika()) return 1;
                return 0;
            }
        });
        return gradovi;
    }

    public Grad glavniGrad(String glavniGrad) {
        for (int i = 0; i < drzave.size(); i++) {
            if (drzave.get(i).getNaziv().equals(glavniGrad)) return drzave.get(i).getGlavni_grad();
        }
        return null;
    }


    public void obrisiDrzavu(String drzava) {
        for (int i = 0; i < drzave.size(); i++) {
            if (drzave.get(i).getNaziv().equals(drzava)) drzave.remove(i);
        }
    }

    public Drzava nadjiDrzavu(String drzava) {
        Drzava d = null;
    /*    stmt2=conn.prepareStatement("SELECT id, naziv FROM main.drzava WHERE main.grad=?");
        ResultSet rs = stmt2.executeQuery();
        while (rs.next()) {
            // System.out.println()
            d=new Drzava(rs.getInt(1), rs.getString(2), rs.getInt(3));
            if(d.getNaziv().equals(drzava))return d;
        }*/
        for (int i = 0; i < drzave.size(); i++) {
            if (drzave.get(i).getNaziv().equals(drzava)) return d;
        }
        return null;
    }

    public void dodajGrad(Grad grad) {
        gradovi.add(grad);
    }


    public void dodajDrzavu(Drzava drzava) {
        drzave.add(drzava);
    }

    public void izmijeniGrad(Grad grad) {
        try {
            stmt1 = conn.prepareStatement("UPDATE main.drzava SET main.drzava.naziv=?, main.drzava.glavni_grad=?");
            stmt1.setInt(1, grad.getId());
            stmt1.setString(2, grad.getNaziv());
            stmt1.setInt(3, grad.getBrojStanovnika());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void createNewDatabase(String fileName) {

        // url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        String url = "jdbc:sqlite:resources/baza.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void createNewTable() {
        // SQLite connection string
       // String url = "jdbc:sqlite:C://sqlite/db/baza.db";

        // SQL statement for creating a new table
        String grad = "CREATE TABLE IF NOT EXISTS grad (\n"
                + "	id int PRIMARY KEY,\n"
                + "	naziv text NOT NULL,\n"
                + "	CONSTRAINT grad_drzava_glavni_grad_fk FOREIGN KEY (id) REFERENCES drzava (glavni_grad)\n"
                + ");";

        String drzava= "CREATE TABLE IF NOT EXISTS drzava (\n"
                + "	id int PRIMARY KEY,\n"
                + "	naziv text NOT NULL,\n"
                + "	CONSTRAINT drzava_grad_broj_stanovnika_fk FOREIGN KEY (id) REFERENCES grad (broj_stanovnika)\n"
                + ");";
        String url = "jdbc:sqlite:resources/baza.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             Statement stmt1 = conn.createStatement()) {
            // create a new table
            stmt.execute(grad);
            stmt1.execute(drzava);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void connect(){
        try{
            //String route= "C:\\Lamija baza.db";
            String url = "jdbc:sqlite:resources/baza.db";
            conn = DriverManager.getConnection(url);
            if (conn!=null)
                System.out.println("Connected to db.");
        }
        catch (SQLException ex) {
            System.err.println("Couldn't connect."+ex.getMessage());
        }
    }
    public  static void insertToDatabase(){
        connect();
        int numRowsInserted = 0;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(INSERT_SQL2);
            ps.setString(2,"London");
            ps.setString(2,"Pariz");
            ps.setString(2,"Beč");
            ps.setString(2,"Manchester");
            ps.setString(2,"Graz");
            ps.setInt(3,8825000);
            ps.setInt(3,2206488);
            ps.setInt(3,1899055);
            ps.setInt(3,545500);
            ps.setInt(3,280200);
            ps = conn.prepareStatement(INSERT_SQL1);
            ps.setString(2,"Velika Britanija");
            ps.setString(2,"Francuska");
            ps.setString(2,"Austrija");
            ps.setString(2,"Velika Britanija");
            ps.setString(2,"Austrija");
            numRowsInserted = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }
        //return numRowsInserted;
    }
    public static void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

