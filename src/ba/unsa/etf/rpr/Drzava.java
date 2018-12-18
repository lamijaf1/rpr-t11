package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Drzava {
    private SimpleIntegerProperty id= new SimpleIntegerProperty(0);
    private SimpleStringProperty naziv = new SimpleStringProperty("");
    private Grad glavni_grad;

    public Drzava() {}
    public Drzava(int id, String naziv, int glavni_grad) {
        this.id=new SimpleIntegerProperty(id);
        this.naziv=new SimpleStringProperty(naziv);
       // this.glavni_grad.setNaziv(glavni_grad);
    }


    public void setGlavniGrad(Grad glavni_grad) {
        this.setGlavni_grad(glavni_grad);
    }

    public SimpleIntegerProperty idProperty() {return id; }
    public int getId() { return id.get();}
    public void setId(int id) { this.id.set(id); }

    public String getNaziv() { return naziv.get(); }
    public SimpleStringProperty nazivProperty() { return naziv; }
    public void setNaziv(String naziv) { this.naziv.set(naziv); }


    public Grad getGlavni_grad() { return glavni_grad; }
    public void setGlavni_grad(Grad glavni_grad) { this.glavni_grad = glavni_grad; }
}
