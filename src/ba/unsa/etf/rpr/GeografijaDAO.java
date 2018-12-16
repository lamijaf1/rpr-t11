package ba.unsa.etf.rpr;

import java.util.ArrayList;

public class GeografijaDAO {
    public static void removeInstance() {
    }

    public static GeografijaDAO getInstance() {
        GeografijaDAO instance=null;
        if (instance == null) initialize();
        return instance;
    }

    private static void initialize() {
    }

    public ArrayList<Grad> gradovi() {
        ArrayList<Grad> grad=new ArrayList<>();
        return grad;
    }

    public Grad glavniGrad(String bosna_i_hercegovina) {
        Grad glavni=null;
        return glavni;
    }



    public void obrisiDrzavu(String kina) {
    }

    public Drzava nadjiDrzavu(String francuska) {
        Drzava d=null;
        return d;
    }

    public void dodajGrad(Grad grad) {
    }

    public void dodajDrzavu(Drzava bih) {
    }

    public void izmijeniGrad(Grad bech) {
    }
}
