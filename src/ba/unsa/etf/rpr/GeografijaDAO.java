package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleObjectProperty;

import java.sql.*;
import java.util.ArrayList;

public class GeografijaDAO {
    ArrayList<Grad> gradovi=new ArrayList<>();
    ArrayList<Drzava> drzave=new ArrayList<>();
    private static GeografijaDAO instance = null;

    private Connection conn;  /* i ostalo što treba za bazu */
    String url = "jdbc:sqlite:resources/baza.db";
    private PreparedStatement stmt1,stmt2;


    private GeografijaDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:sqlite:resources/baza.db");
            stmt1 = conn.prepareStatement("SELECT id, naziv,  broj_stanovnika FROM main.grad WHERE main.drzava.naziv= ?");
            ResultSet rs = stmt1.executeQuery();
            while (rs.next()) {
                // System.out.println()
                Grad k = new Grad(rs.getInt(1), rs.getString(2), rs.getInt(3));
                gradovi.add(k);
            }
            // ne znam postaviti strani kljuc za ovaj slucaj pa ne mogu odabrati dobro
            stmt2=conn.prepareStatement("SELECT id, naziv FROM main.drzava WHERE main.grad=?");
            ResultSet rs = stmt2.executeQuery();
            while (rs.next()) {
                // System.out.println()
                Drzava d=new Drzava(rs.getInt(1), rs.getString(2), rs.getInt(3));
                drzave.add(d);
            }

        } catch(SQLException e) {
            System.out.println("Neuspješno čitanje iz baze: " + e.getMessage());
        }

    }


    public static GeografijaDAO getInstance() {
        if (instance == null) initialize();
        return instance;
    }
    public static void removeInstance() { instance = null; }



    private static void initialize() {
        instance = new GeografijaDAO();
    }

    public ArrayList<Grad> gradovi() {
        ArrayList<Grad> grad=new ArrayList<>();
        return grad;
    }

    public Grad glavniGrad(String glavniGrad) {
        Grad grad=null;
        return grad;
    }



    public void obrisiDrzavu(String kina) {
    }

    public Drzava nadjiDrzavu(String francuska) {
        Drzava d=null;
        return d;
    }

    public void dodajGrad(Grad grad) {
        gradovi.add(grad);
    }


    public void dodajDrzavu(Drzava drzava) {
        drzave.add(drzava)
    }

    public void izmijeniGrad(Grad grad) {
        stmt1 = conn.prepareStatement("UPDATE main.drzava SET main.drzava.naziv=?, main.drzava.glavni_grad=?");
        stmt1.setInt(1, grad.getId());
        stmt1.setString(2, grad.getNaziv());
        stmt1.setInt(3, grad.getBrojStanovnika());
    }
}
