package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;



//This is the Main Interface Controller
public class Controller {

    public static int LoginP_SSN=0;
    public static String LoginP_Name;
    public static String LoginP_Gender;
    public static String Namethearpist;
    public static int LoginT_SSN=0;


    @FXML
    private Hyperlink facebookLine;

    @FXML
    private Button btnSignUp;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnAboutUs;



    @FXML
    void setAboutUs(ActionEvent event) throws IOException { // close the original window, and open a new window to display information about the clinic
        // get a handle to the stage
        Stage MainStage = (Stage) btnAboutUs.getScene().getWindow();
        // do what you have to do
        MainStage.close();
        final Stage AboutUsStage = new Stage();
        AboutUsStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        AboutUsStage.initOwner(app_stage);
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/AboutUs.fxml"));
        Scene scene11 = new Scene(nextSceneParent);
        AboutUsStage.setScene(scene11);
        AboutUsStage.show();
    }

    @FXML
    void goToFacebook(ActionEvent event) { // go the facebook page of lina's pro clinic
        if(Desktop.isDesktopSupported())
        {
            try {
                Desktop.getDesktop().browse(new URI("https://www.facebook.com/Linas-Pro-Clinic-110444627152002"));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

    @FXML
    void setSignIn(ActionEvent event) throws IOException { // open the window provided for the sign in
        // get a handle to the stage
        Stage MainStage = (Stage) btnAboutUs.getScene().getWindow();
        // do what you have to do
        MainStage.close();
        final Stage AboutUsStage = new Stage();
        AboutUsStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        AboutUsStage.initOwner(app_stage);
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/SignIn.fxml"));
        Scene scene11 = new Scene(nextSceneParent);
        AboutUsStage.setScene(scene11);
        AboutUsStage.show();
    }

    @FXML
    void setSignUp(ActionEvent event) throws IOException { // open the wnidow provided for the sign up
        // get a handle to the stage
        Stage MainStage = (Stage) btnAboutUs.getScene().getWindow();
        // do what you have to do
        MainStage.close();
        final Stage AboutUsStage = new Stage();
        AboutUsStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        AboutUsStage.initOwner(app_stage);
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/SignUp.fxml"));
        Scene scene11 = new Scene(nextSceneParent);
        AboutUsStage.setScene(scene11);
        AboutUsStage.show();
    }


}
