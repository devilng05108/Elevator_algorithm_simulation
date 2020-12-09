
package dsfinalprojectelevatorsimulator;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class alertbox {
    
    public static void warning(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Warning");
        
        Label label1 = new Label("Source floor cannot be same as destination \nfloor! Please re-enter request");
        Button btn = new Button("OK");
        btn.setOnAction(e->{
        window.close();
        
        });
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label1,btn);
        vbox.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(vbox,320,250);
        window.setScene(scene);
        window.show();
    }
        
    
}
