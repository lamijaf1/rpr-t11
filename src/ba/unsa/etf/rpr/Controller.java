package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.scene.control.*;


import java.util.ResourceBundle;


public class Controller {
    public TextField drzavaNaziv;
    public Button stampaj;
    public Button pronadjiGlavniGrad;
    public Button obrisiDrzavu;
    public Button ispisGradova,pronadjiDrzavu;
    public Menu file,help,view,Jezik;
    public  MenuItem bosanski,njemacki,engleski,francuski,close,saveAs;
    public Main Main;
    public Label statusMsg,drzava;


    public void obrisiDrzavu(ActionEvent actionEvent) {
        Grad glavniGrad = GeografijaDAO.getInstance().glavniGrad(drzavaNaziv.getText());
        GeografijaDAO.getInstance().obrisiDrzavu(drzavaNaziv.getText());
        if(GeografijaDAO.getInstance().nadjiDrzavu(drzavaNaziv.getText())==null)statusMsg.setText("Drzava ne postoji ili je obrisana!");
        else statusMsg.setText("Drzava nije obrisana!");
    }

    public void ispisiGradove(ActionEvent actionEvent) {
        String s="Gradovi: "+Main.ispisiGradove();
        System.out.println(s);
        statusMsg.setText("Ispisujem gradove.");

    }

    public void pronadiDrzave(ActionEvent actionEvent) {

    }

    public void stampaj(ActionEvent actionEvent) {

    }

    public void glavniGrad(ActionEvent actionEvent) {
        Grad glavniGrad = GeografijaDAO.getInstance().glavniGrad(drzavaNaziv.getText());
        String imeGrada=glavniGrad.getNaziv();
        statusMsg.setText("Glavni grad unesene drzave: "+ imeGrada);
    }

    public void saveAs(ActionEvent actionEvent) {
    }

    public void jezik(ActionEvent actionEvent) {
        String property="";
        MenuItem m=(MenuItem)actionEvent.getSource();
        switch(m.getId()) {
            case "bosanski":
                property="Translation_bs";
                break;
            case "francuski":
                property="Translation_fr";
                break;
            case "njemacki":
                property="Translation_de";
                break;
            case "engleski":
                property="Translation_en_US";
                break;
        }
        file.setText(ResourceBundle.getBundle(property).getString("File"));
        help.setText(ResourceBundle.getBundle(property).getString("Help"));
        view.setText(ResourceBundle.getBundle(property).getString("View"));
        close.setText(ResourceBundle.getBundle(property).getString("Close"));
        saveAs.setText(ResourceBundle.getBundle(property).getString("SaveAs"));
        Jezik.setText(ResourceBundle.getBundle(property).getString("jezik"));
        obrisiDrzavu.setText(ResourceBundle.getBundle(property).getString("obrisiDrzavu"));
        ispisGradova.setText(ResourceBundle.getBundle(property).getString("ispisiGradove"));
        pronadjiDrzavu.setText(ResourceBundle.getBundle(property).getString("pronadiDrzavu"));
        pronadjiGlavniGrad.setText(ResourceBundle.getBundle(property).getString("pronadiGlavniGrad"));
        stampaj.setText(ResourceBundle.getBundle(property).getString("stampaj"));
        statusMsg.setText(ResourceBundle.getBundle(property).getString("statusMsg"));
        bosanski.setText(ResourceBundle.getBundle(property).getString("bosanski"));
        engleski.setText(ResourceBundle.getBundle(property).getString("engleski"));
        francuski.setText(ResourceBundle.getBundle(property).getString("francuski"));
        njemacki.setText(ResourceBundle.getBundle(property).getString("njemacki"));
        drzava.setText(ResourceBundle.getBundle(property).getString("drzava"));
        statusMsg.setText(ResourceBundle.getBundle(property).getString("statusMsg"));
    }
}
