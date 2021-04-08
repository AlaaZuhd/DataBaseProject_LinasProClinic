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

    @FXML
    private Hyperlink facebookLine;

    @FXML
    private Button btnSignUp;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnAboutUs;

    @FXML
    void ff9900(ActionEvent event) {

    }


    @FXML
    void setAboutUs(ActionEvent event) throws IOException { // close the orginal window
        // get a handle to the stage
        Stage MainStage = (Stage) btnAboutUs.getScene().getWindow();
        // do what you have to do
        MainStage.close();
        final Stage AboutUsStage = new Stage();
        AboutUsStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        AboutUsStage.initOwner(app_stage);
        System.out.println("YAY\n\n");
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/AboutUs.fxml"));
        System.out.println("WTF");
        Scene scene11 = new Scene(nextSceneParent);
        AboutUsStage.setScene(scene11);
        AboutUsStage.show();
    }

    @FXML
    void goToFacebook(ActionEvent event) {
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
    void setSignIn(ActionEvent event) throws IOException {
        // get a handle to the stage
        Stage MainStage = (Stage) btnAboutUs.getScene().getWindow();
        // do what you have to do
        MainStage.close();
        final Stage AboutUsStage = new Stage();
        AboutUsStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        AboutUsStage.initOwner(app_stage);
        System.out.println("YAY\n\n");
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/SignIn.fxml"));
        System.out.println("WTF");
        Scene scene11 = new Scene(nextSceneParent);
        AboutUsStage.setScene(scene11);
        AboutUsStage.show();
    }

    @FXML
    void setSignUp(ActionEvent event) throws IOException {
        // get a handle to the stage
        Stage MainStage = (Stage) btnAboutUs.getScene().getWindow();
        // do what you have to do
        MainStage.close();
        final Stage AboutUsStage = new Stage();
        AboutUsStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        AboutUsStage.initOwner(app_stage);
        System.out.println("YAY\n\n");
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/SignUp.fxml"));
        System.out.println("WTF");
        Scene scene11 = new Scene(nextSceneParent);
        AboutUsStage.setScene(scene11);
        AboutUsStage.show();
    }


}
