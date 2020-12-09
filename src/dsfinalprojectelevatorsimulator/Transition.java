package dsfinalprojectelevatorsimulator;

import static dsfinalprojectelevatorsimulator.elevatorgui.filetName;
import java.awt.Desktop;
import java.awt.TextField;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.launch;
import static javafx.application.Application.setUserAgentStylesheet;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Asus
 */
public class Transition extends Application {
    static int xxxx=0;
    static TranslateTransition tt = new TranslateTransition();
    static Rectangle rec, door;
    static double currentfloor = 0;
    static int numPassenger = 0;
    static Label label, label2, passenger;
    static String condition = "in";
    static Timeline timeline = new Timeline();

    @Override
    public void start(Stage primaryStage) {
//        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        xxxx+=1;
        rec = new Rectangle(175, 500, 50, 50);
        rec.setFill(Color.BLACK);

        door = new Rectangle(200, 500, 1, 50);
        door.setFill(Color.DARKGREY);

         passenger = new Label( Integer.toString(numPassenger),new ImageView("standing-up-man-.png"));
        passenger.setStyle("-fx-border-color:black");
        passenger.setMinWidth(30);
        passenger.setLayoutX(400);
        passenger.setLayoutY(520);

        label = new Label("Passenger(s) in elavator : ",new ImageView("group.png"));
        label.setPadding(new Insets(5, 0, 5, 80));
        label.setFont(Font.font(16));
        label2 = new Label(Integer.toString(numPassenger));
        label2.setFont(Font.font(16));
        label2.setPadding(new Insets(5, 0, 5, 0));

        Label b10 = new Label("Floor 10");
        b10.setStyle("-fx-border-color:black");
        b10.setPadding(new Insets(0, 0, 0, 35));
        b10.setMinWidth(400);
        b10.setMinHeight(50);

        Label b9 = new Label("Floor 9");
        b9.setStyle("-fx-border-color:black");
        b9.setPadding(new Insets(0, 0, 0, 35));
        b9.setMinWidth(400);
        b9.setMinHeight(50);

        Label b8 = new Label("Floor 8");
        b8.setStyle("-fx-border-color:black");
        b8.setPadding(new Insets(0, 0, 0, 35));
        b8.setMinWidth(400);
        b8.setMinHeight(50);

        Label b7 = new Label("Floor 7");
        b7.setStyle("-fx-border-color:black");
        b7.setPadding(new Insets(0, 0, 0, 35));
        b7.setMinWidth(400);
        b7.setMinHeight(50);

        Label b6 = new Label("Floor 6");
        b6.setStyle("-fx-border-color:black");
        b6.setPadding(new Insets(0, 0, 0, 35));
        b6.setMinWidth(400);
        b6.setMinHeight(50);

        Label b5 = new Label("Floor 5");
        b5.setStyle("-fx-border-color:black");
        b5.setPadding(new Insets(0, 0, 0, 35));
        b5.setMinWidth(400);
        b5.setMinHeight(50);

        Label b4 = new Label("Floor 4");
        b4.setStyle("-fx-border-color:black");
        b4.setPadding(new Insets(0, 0, 0, 35));
        b4.setMinWidth(400);
        b4.setMinHeight(50);

        Label b3 = new Label("Floor 3");
        b3.setStyle("-fx-border-color:black");
        b3.setPadding(new Insets(0, 0, 0, 35));
        b3.setMinWidth(400);
        b3.setMinHeight(50);

        Label b2 = new Label("Floor 2");
        b2.setStyle("-fx-border-color:black");
        b2.setPadding(new Insets(0, 0, 0, 35));
        b2.setMinWidth(400);
        b2.setMinHeight(50);

        Label b1 = new Label("Floor 1");
        b1.setStyle("-fx-border-color:black");
        b1.setPadding(new Insets(0, 0, 0, 35));
        b1.setMinWidth(400);
        b1.setMinHeight(50);

        Label b0 = new Label("Floor 0");
        b0.setStyle("-fx-border-color:black");
        b0.setPadding(new Insets(0, 0, 0, 35));
        b0.setMinWidth(400);
        b0.setMinHeight(50);
        
        Button pdfbtn2 = new Button("View elevator report(.pdf)");
         pdfbtn2.setId("reportcolour");
        pdfbtn2.setOnAction(e->{
            play_pdf();
                        File pdfFile = new File(filetName.getText()+".pdf");
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().open(pdfFile);
                    } catch (IOException ex) {

                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Awt Desktop is not supported!");
                }
            } else {
                System.out.println("File is not exists!");
            }

//            RunElevator.transitionRequest.clear();
//            setNumPassenger(0);
//            testingMain.start2();
//            primaryStage.close();
        });
        
        Button gnr = new Button("View elevator log file(.txt)");
        gnr.setId("reportcolour");
        gnr.setOnAction(ex -> {
            play_log();
            File txtFile = new File(filetName.getText()+".txt");
            if (txtFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().open(txtFile);
                    } catch (IOException exx) {

                        exx.printStackTrace();
                    }
                } else {
                    System.out.println("Awt Desktop is not supported!");
                }
            } else {
                System.out.println("File is not exists!");
            }

        });

        HBox hb = new HBox();
        hb.setPadding(new Insets(25, 0, 0, 0));
        hb.getChildren().addAll(label, label2);
        
        HBox hb2 = new HBox();
        hb2.setPadding(new Insets(20,20,20,20));
        hb2.getChildren().addAll(pdfbtn2,gnr);
        hb2.setSpacing(50);
        

        
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0, 0, 0, 0));
        vbox.getChildren().addAll(b10, b9, b8, b7, b6, b5, b4, b3, b2, b1, b0, hb,hb2);

        Pane root = new Pane();
        BackgroundFill bg = new BackgroundFill(Color.rgb(215, 221, 229, 1), new CornerRadii(1), new Insets(0));
        root.getChildren().addAll(rec, door, passenger, vbox);
        root.setBackground(new Background(bg));
        Scene scene = new Scene(root, 400, 700);

        ArrayList<SequentialTransition> y = new ArrayList<>();
        SequentialTransition sqarray = new SequentialTransition();

        for (int kk = 0; kk < RunElevator.transitionRequest.size(); kk++) {
             sqarray.getChildren().add(DMOVE(RunElevator.transitionRequest.get(kk).sourceOrDestination, currentfloor, RunElevator.transitionRequest.get(kk).doorMovement, RunElevator.transitionRequest.get(kk).frequency));;
        }
        
        
        sqarray.playFromStart();
        scene.getStylesheets().add("transition.css");
        primaryStage.getIcons().add(new Image("elevatortitle.png"));
        primaryStage.setTitle("  NoName7 - Elevator Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
        public void play_pdf(){
        AudioClip note = new AudioClip(this.getClass().getResource("pdfwatson.mp3").toString());
        note.play();
    }
                public void play_log(){
        AudioClip note = new AudioClip(this.getClass().getResource("textfile.mp3").toString());
        note.play();
    }
                        public void play_up(){
        AudioClip note = new AudioClip(this.getClass().getResource("up.mp3").toString());
        note.play();
    }
        
//        public void play_audio_open(){
//        AudioClip note = new AudioClip(this.getClass().getResource("open.wav").toString());
//        note.play();
//    }
//                public void play_audio_close(){
//        AudioClip note = new AudioClip(this.getClass().getResource("close.wav").toString());
//        note.play();
//    }
    public TranslateTransition MOVE(double go, double current) {
        
        double sec = (go - current);

        if (sec < 0) {
            sec = sec * -1;
        }
        TranslateTransition tt = new TranslateTransition();
//        tt.setOnFinished(e -> MOVE(go, current));
        tt.setDuration(Duration.seconds(sec / 2));
        tt.setToY(-(go * 50));
        tt.setNode(rec);
        return tt;
    }

    public SequentialTransition DMOVE(double go, double current, String condition, int frequency) {
        double sec = (go - current);

        if (sec < 0) {
            sec = sec * -1;
        }
        PauseTransition pause = new PauseTransition(Duration.millis(1));

        TranslateTransition tt = new TranslateTransition();
        pause.setOnFinished(e -> DMOVE(go, current, condition, frequency));
        tt.setDuration(Duration.seconds(sec / 2));
        tt.setToY(-(go * 50));
        tt.setNode(door);

        TranslateTransition tt2 = new TranslateTransition();
//        tt2.setOnFinished(e -> MOVE(go, current));
        tt2.setDuration(Duration.seconds(sec/2));
        tt2.setToY(-(go * 50));
        tt2.setNode(rec);
        

       
        ParallelTransition parallel = new ParallelTransition();
        parallel.getChildren().add(tt);
        parallel.getChildren().add(tt2);

        SequentialTransition seq = new SequentialTransition();
        if (condition.equalsIgnoreCase("in")) {
            seq = new SequentialTransition(parallel, doorOpen(),pause, passengerIn(true, go), doorClose());
            numPassenger += frequency;
            setNumPassenger(numPassenger);
            setLabel(frequency);
        } else if (condition.equalsIgnoreCase("out")) {
            seq = new SequentialTransition(parallel, doorOpen(),pause, passengerOut(true, go), doorClose());
            numPassenger -= frequency;
            setNumPassenger(numPassenger);
            setLabel(frequency);
        } else if (condition.equalsIgnoreCase("halfopen")) {
            seq = new SequentialTransition(parallel, doorOpen(),pause, passengerOut(true, go));
            numPassenger -= frequency;
            setNumPassenger(numPassenger);
            setLabel(frequency);
        } else if (condition.equalsIgnoreCase("halfclose")) {
            seq = new SequentialTransition(parallel,pause, passengerIn(true, go), doorClose());
            numPassenger += frequency;
            setNumPassenger(numPassenger);
            setLabel(frequency);
        }
        currentfloor = go;
        return seq;
    }

    public static SequentialTransition passengerIn(boolean source, double go) {
        PsetTo(go);
        TranslateTransition tt = new TranslateTransition();
        TranslateTransition t2 = new TranslateTransition();
        SequentialTransition seq = new SequentialTransition(tt, t2);

        if (source) {
            tt.setDuration(Duration.seconds(2.5));
            tt.setToX(-215);
            tt.setNode(passenger);
            


            t2.setDelay(Duration.seconds(0.5));
            t2.setDuration(Duration.millis(1));
            t2.setToX(0);
            t2.setNode(passenger);
        }
        return seq;
    }

    public static SequentialTransition passengerOut(boolean destination, double go) {
        TranslateTransition tt = new TranslateTransition();
        TranslateTransition t2 = new TranslateTransition();
        SequentialTransition seq = new SequentialTransition(tt, t2);

        if (destination) {
            tt.setDuration(Duration.millis(1));
            tt.setToX(-215);
            tt.setToY(-(go * 50));
            tt.setNode(passenger);

            t2.setDuration(Duration.seconds(2.5));
            t2.setToX(0);
            t2.setNode(passenger);
        }
        return seq;
    }

    public static void setNumPassenger(int num) {
        label2.setText(Integer.toString(numPassenger));
    }

    public static void setLabel(int frequency) {
        passenger.setText(Integer.toString(frequency));
    }

    public  ScaleTransition doorOpen() {
//        if(xxxx==1)
//        play_audio_open();
        ScaleTransition st = new ScaleTransition(Duration.seconds(2), door);
        
        st.setToX(51);
        st.setCycleCount(1);
        return st;
    }

    public ScaleTransition doorClose() {
//        if(xxxx==1)
//        play_audio_close();
        ScaleTransition st2 = new ScaleTransition(Duration.seconds(2), door);
        
        st2.setToX(1);
        st2.setCycleCount(1);
        return st2;
    }

    public static void PsetTo(double go) {
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(1));
        tt.setToY(-(go * 50));
        tt.setNode(passenger);
        tt.play();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
