package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleObjectProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GeografijaDAO {
    ArrayList<Grad> gradovi = new ArrayList<>();
    ArrayList<Drzava> drzave = new ArrayList<>();
    private static GeografijaDAO instance = null;

    private Connection conn;  /* i ostalo što treba za bazu */
    String url = "jdbc:sqlite:resources/baza.db";
    private PreparedStatement stmt1, stmt2;


    private GeografijaDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
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
    //ArrayList<Grad> gradovi() - vraća spisak gradova sortiranih po broju stanovnika u opadajućem redoslijedu


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
}
