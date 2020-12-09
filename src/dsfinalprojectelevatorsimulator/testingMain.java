/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsfinalprojectelevatorsimulator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
/**
 *
 * @author devilng05108
 */
public class testingMain {
    public static void main(String[] args) {
       start2();

    }
    public static void start() {
//		super.start();
		try {
                        // Because we need to init the JavaFX toolkit - which usually Application.launch does
                        // I'm not sure if this way of launching has any effect on anything
			new JFXPanel();

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// Your class that extends Application
					new Transition().start(new Stage());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
        public static void start2() {
//		super.start();
		try {
                        // Because we need to init the JavaFX toolkit - which usually Application.launch does
                        // I'm not sure if this way of launching has any effect on anything
			new JFXPanel();

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
                                    try {
                                        // Your class that extends Application
                                        new elevatorgui().start(new Stage());
                                    } catch (Exception ex) {
                                        Logger.getLogger(testingMain.class.getName()).log(Level.SEVERE, null, ex);
                                    }
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
