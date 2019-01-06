package ba.unsa.etf.rpr;
//POSTOJI PROBLEM KOD KREIRANJA BAZE

//import javafx.beans.property.SimpleObjectProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GeografijaDAO {
    ArrayList<Grad> gradovi = new ArrayList<Grad>();
    ArrayList<Drzava> drzave = new ArrayList<Drzava>();
    private static GeografijaDAO instance = null;

    public static Connection getConn() {
        return conn;
    }

    private static Connection conn;  /* i ostalo što treba za bazu */
    public String url="jdbc:sqlite:resources/baza.db";


    public GeografijaDAO(GeografijaDAO d){instance=d;}
    public static GeografijaDAO getInstance() {
        if (instance == null) initialize();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    private static void initialize() {
        instance = new GeografijaDAO();
    }

    public GeografijaDAO() {
        try {
            connect();
            createNewTable();
            insertToDatabase();
        } catch(Exception e) {
        }
    }
    public static void connect(){
        try{
            String url = "jdbc:sqlite:resources/baza.db";
            conn = DriverManager.getConnection(url);
            if (conn!=null)
                System.out.println("Connected to db.");
        }
        catch (SQLException ex) {
            System.err.println("Couldn't connect."+ex.getMessage());
        }
    }
    public static void createNewTable() {
        String grad = "CREATE TABLE IF NOT EXISTS grad\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY,\n" +
                "    naziv text,\n" +
                "    broj_stanovnika INTEGER ,\n" +
                "    drzava INTEGER ,\n" +
                "    CONSTRAINT grad_drzava_id_fk FOREIGN KEY (drzava) REFERENCES drzava (id)\n" +
                ");";
        String drzava = "CREATE TABLE IF NOT EXISTS drzava\n"
                +"(\n"
                + "    id INTEGER PRIMARY KEY,\n"
                + "    naziv text,\n"
                + "    glavni_grad INTEGER ,\n"
                + "    CONSTRAINT drzava_grad_id_fk FOREIGN KEY (glavni_grad) REFERENCES grad (id)\n" +
                ");";
        String url = "jdbc:sqlite:resources/baza.db";

        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            Statement stmt1 = conn.createStatement();
            stmt.execute(grad);
            stmt1.execute(drzava);
        } catch (SQLException e) {
            System.out.println("lamija");
            System.out.println(e.getMessage());
        }
    }
    private void insertToDatabase() {
// Pariz
        Grad pariz = new Grad();
        pariz.setNaziv("Pariz");
        pariz.setBrojStanovnika(2200000);
        Drzava francuska = new Drzava();
        francuska.setNaziv("Francuska");
        francuska.setGlavniGrad(pariz);
        pariz.setDrzava(francuska);
        dodajDrzavu(francuska);
        dodajGrad(pariz);
//london
        Grad london = new Grad();
        london.setNaziv("London");
        london.setBrojStanovnika(8136000);
        Drzava velikaBritanija = new Drzava();
        velikaBritanija.setNaziv("Velika Britanija");
        velikaBritanija.setGlavniGrad(london);
        london.setDrzava(velikaBritanija);
        dodajDrzavu(velikaBritanija);
        dodajGrad(london);
//BEC
        Grad bec = new Grad();
        bec.setNaziv("Beč");
        bec.setBrojStanovnika(1867000);
        Drzava austrija = new Drzava();
        austrija.setNaziv("Austrija");
        austrija.setGlavniGrad(bec);
        bec.setDrzava(austrija);
        dodajDrzavu(austrija);
        dodajGrad(bec);
//MANCHESTER
        Grad mancester = new Grad();
        mancester.setNaziv("Manchester");
        mancester.setBrojStanovnika(510746);
        mancester.setDrzava(velikaBritanija);
        dodajGrad(mancester);
//GRAZ
        Grad graz = new Grad();
        graz.setNaziv("Graz");
        graz.setBrojStanovnika(283869);
        graz.setDrzava(austrija);
        dodajGrad(graz);
    }
    public ArrayList<Grad> gradovi(){
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
            if (drzave.get(i).getNaziv().equals(glavniGrad)) return drzave.get(i).getGlavniGrad();
        }
        return null;
    }


    public void obrisiDrzavu(String drzava) {
        Drzava d=nadjiDrzavu(drzava);
        if(d==null)return;
        ArrayList<Grad> noviGradovi=new ArrayList<Grad>();
        for (int i = 0; i < gradovi.size(); i++) {
            if(gradovi.get(i).getDrzava().getNaziv().equals(drzava)) noviGradovi.add(gradovi.get(i));
        }
        for (int i = 0; i < drzave.size(); i++) {
            if (drzave.get(i).getNaziv().equals(drzava)) drzave.remove(i);
        }
        gradovi.removeAll(noviGradovi);
        try {

            PreparedStatement stmt1= conn.prepareStatement("DELETE FROM drzava WHERE naziv=?");
            PreparedStatement  stmt2= conn.prepareStatement("DELETE FROM grad WHERE drzava=?");
            stmt1.setInt(1, d.getId());
            stmt2.executeUpdate();
            stmt1.setString(1, drzava);
            stmt1.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Drzava nadjiDrzavu(String drzava) {
        // Drzava d = null;
        for (int i = 0; i < drzave.size(); i++) {
            if (drzave.get(i).getNaziv().equals(drzava)) return drzave.get(i);
        }
        return null;
    }

    void dodajGrad(Grad grad) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT OR REPLACE INTO grad(naziv, broj_stanovnika, drzava) VALUES(?,?,?)");
            statement.setString(1, grad.getNaziv());
            statement.setInt(2, grad.getBrojStanovnika());
            statement.setInt(3, grad.getDrzava().getId());
            statement.executeUpdate();
            gradovi.add(grad);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void dodajDrzavu(Drzava drzava) {
        drzave.add(drzava);
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT OR REPLACE INTO drzava(naziv, glavni_grad) VALUES(?,?)");
            statement.setString(1, drzava.getNaziv());
            statement.setInt(2, drzava.getGlavniGrad().getId());
            statement.executeUpdate();
            //drzave.add(drzava);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void izmijeniGrad(Grad grad) {
        try {
            PreparedStatement stmt1 = conn.prepareStatement("UPDATE main.drzava SET main.drzava.naziv=?, main.drzava.glavni_grad=?");
            stmt1.setInt(1, grad.getId());
            stmt1.setString(2, grad.getNaziv());
            stmt1.setInt(3, grad.getBrojStanovnika());
        } catch (Exception e) {
            e.getMessage();
        }
    }



    public static  void insertToDatabase1(){
        connect();
        try {
            PreparedStatement stmt1=conn.prepareStatement("INSERT INTO grad(id,naziv,broj_stanovnika,drzava) VALUES (1,'London', 8825000,1)");
            stmt1.execute();
            stmt1=conn.prepareStatement("INSERT INTO grad(id,naziv,broj_stanovnika,drzava) VALUES (2,'Pariz',2206488,2)");
            stmt1.execute();
            stmt1= conn.prepareStatement("INSERT INTO grad(id,naziv,broj_stanovnika,drzava) VALUES (3,'Beč',1899055,3)");
            stmt1.execute();
            stmt1=conn.prepareStatement("INSERT INTO grad(id,naziv,broj_stanovnika,drzava) VALUES(4,'Manchester',545500,4)");
            stmt1.execute();
            stmt1=conn.prepareStatement("INSERT INTO grad(id,naziv,broj_stanovnika,drzava) VALUES (5,'Graz',280200,5)");
            stmt1.execute();
            stmt1 = conn.prepareStatement("INSERT INTO drzava(id,naziv,glavni_grad) VALUES(1,'Velika Britanija',1)");
            stmt1.execute();
            stmt1 = conn.prepareStatement("INSERT INTO drzava(id,naziv,glavni_grad) VALUES(2,'Francuska',2)");
            stmt1.execute();
            stmt1 = conn.prepareStatement("INSERT INTO drzava(id,naziv,glavni_grad) VALUES(3,'Austrija',3)");
            stmt1.execute();
            stmt1 = conn.prepareStatement("INSERT INTO drzava(id,naziv,glavni_grad) VALUES(4,'Velika Britanija',4)");
            stmt1.execute();
            stmt1 = conn.prepareStatement("INSERT INTO drzava(id,naziv,glavni_grad) VALUES(5,'Austrija',5)");
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

