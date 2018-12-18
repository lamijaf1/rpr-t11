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
    public String url = "jdbc:sqlite/baza.db";
    //com.mysql.cj.jdbc.Driver
    private static PreparedStatement stmt1, stmt2;


    public GeografijaDAO() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
            stmt1 = conn.prepareStatement("SELECT id,naziv,broj_stanovnika FROM grad WHERE drzava=?");
            ResultSet rs = stmt1.executeQuery();
            while (rs.next()) {
                // System.out.println()
                Grad k = new Grad(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                gradovi.add(k);
            }
            // ne znam postaviti strani kljuc za ovaj slucaj pa ne mogu odabrati dobro
            stmt2 = conn.prepareStatement("SELECT  naziv FROM drzava WHERE glavni_grad=?");
            ResultSet rs1 = stmt2.executeQuery();
            while (rs1.next()) {
                // System.out.println()
                Drzava d = new Drzava(rs1.getInt(1), rs1.getString(2), rs1.getInt(3));
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
        String url = "jdbc:sqlite:baza.db";
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
                + "	drzava int \n"
                + ");";
        String drzava= "CREATE TABLE IF NOT EXISTS drzava (\n"
                + "	id int PRIMARY KEY,\n"
                + "	naziv text NOT NULL,\n"
                + "	CONSTRAINT drzava_grad_id_fk FOREIGN KEY (glavni_grad) REFERENCES grad (id)\n"
                + ");";
        String url = "jdbc:sqlite:baza.db";
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
            String url = "jdbc:sqlite:baza.db";
            conn = DriverManager.getConnection(url);
            if (conn!=null)
                System.out.println("Connected to db.");
        }
        catch (SQLException ex) {
            System.err.println("Couldn't connect."+ex.getMessage());
        }
    }
    public static  void insertToDatabase(){
        connect();
        try {
            stmt1 = conn.prepareStatement("INSERT INTO drzava(id,naziv,glavni_grad) VALUES(1,'Velika Britanija',1)");
            stmt1.execute();
            stmt1 = conn.prepareStatement("INSERT INTO drzava(naziv,glavni_grad) VALUES(2,'Francuska',2)");
            stmt1.execute();
            stmt1 = conn.prepareStatement("INSERT INTO drzava(naziv,glavni_grad) VALUES(3,'Austrija',3)");
            stmt1.execute();
            stmt1 = conn.prepareStatement("INSERT INTO drzava(naziv,glavni_grad) VALUES(4,'Velika Britanija',4)");
            stmt1.execute();
            stmt1 = conn.prepareStatement("INSERT INTO drzava(naziv,glavni_grad) VALUES(5,'Austrija',5)");
            stmt1.execute();
            stmt1.close();

        } catch (Exception e) {
            e.getMessage();
        }
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

