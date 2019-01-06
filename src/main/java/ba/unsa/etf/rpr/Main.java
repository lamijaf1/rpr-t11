package ba.unsa.etf.rpr;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Main extends Application {
    public  String ispisiGradove(){
        ArrayList<Grad> gradovi = GeografijaDAO.getInstance().gradovi();
        String s="";
        for(Grad grad: gradovi){
            s += grad.toString();
        }
        return s;
    }
    public  void glavniGrad(){
        System.out.println("Unesite naziv drzave: ");
        Scanner ulaz = new Scanner(System.in);
        GeografijaDAO dao = GeografijaDAO.getInstance();
        String naziv = ulaz.nextLine();
        Grad glavniGrad = dao.glavniGrad(naziv);
        if(glavniGrad == null) System.out.println("Nepostojeca drzava");
        else System.out.println("Glavni grad drzave " + naziv + " je " + glavniGrad.getNaziv());
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        GeografijaDAO model = GeografijaDAO.getInstance();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/sample.fxml" ), bundle);
        Parent root = loader.load();
        primaryStage.setTitle("Geografija");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
