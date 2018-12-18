package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Grad {
    private SimpleIntegerProperty id= new SimpleIntegerProperty(0);
    private SimpleStringProperty naziv = new SimpleStringProperty("");
    private SimpleIntegerProperty broj_stanovnika = new SimpleIntegerProperty(0);
    private Drzava drzava;

    public Grad(){}
    public Grad(int id, String naziv, int broj_stanovnika, int drzava) {
        this.id=new SimpleIntegerProperty(id);
        this.naziv=new SimpleStringProperty(naziv);
        this.broj_stanovnika=new SimpleIntegerProperty(broj_stanovnika);
       // this.drzava.setNaziv(drzava);
    }


    public void setBrojStanovnika(int i) {
        this.broj_stanovnika.set(i);
    }

    public void setDrzava(Drzava drzava) {
        this.drzava=drzava;
    }

    public int getBrojStanovnika() {
        return broj_stanovnika.get();
    }


    public SimpleIntegerProperty idProperty() {return id; }
    public int getId() { return id.get();}
    //public void setId(int id) { this.id.set(id); }

    public String getNaziv() { return naziv.get(); }
    public SimpleStringProperty nazivProperty() { return naziv; }
    public void setNaziv(String naziv) { this.naziv.set(naziv); }

    public SimpleIntegerProperty broj_stanovnikaProperty() {return broj_stanovnika; }
    //public void setBroj_stanovnika(int broj_stanovnika) {this.broj_stanovnika.set(broj_stanovnika); }
    //public void getBroj_stanovnika(){ return broj_stanovnika.get(); }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setNaziv() {
        //his.setNaziv();
    }
}
