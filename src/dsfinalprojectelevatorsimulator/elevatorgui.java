package dsfinalprojectelevatorsimulator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import static com.sun.javafx.css.FontFace.FontFaceSrcType.URL;
import static dsfinalprojectelevatorsimulator.RunElevator.document;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.DataFormat.URL;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.print.PrintException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


public class elevatorgui extends Application {
//     static Document document = new Document();
              
    static TextField filetName = new TextField();
    protected static boolean newWin = false;
    static String fileName = "";
    static String pdfName = "";
    Stage window;
    int count = 0;
    int temp = 1;
    Request r;
    int reqnum = 1;
    ArrayList<Request> arr = new ArrayList<>();//arrayList to store requests
    ArrayList<Request> upList = new ArrayList<>();
    ArrayList<Request> downList = new ArrayList<>();
    boolean reenter = true;
    @Override
    public void start(Stage primaryStage) throws Exception {
//         URL resource = getClass().getResource("A-Rainbow.mp3");
// MediaPlayer a =new MediaPlayer(new Media("A-Rainbow.mp3"));
// a.setOnEndOfMedia(new Runnable() {
//       public void run() {
//         a.seek(Duration.ZERO);
//       }
//   });
//  a.play();
//String musicFile = "click_0.mp3";     // For example
//
//Media sound = new Media(new File(musicFile).toURI().toString());
//MediaPlayer mediaPlayer = new MediaPlayer(sound);
// mediaPlayer.setOnEndOfMedia(new Runnable() {
//       public void run() {
//         mediaPlayer.seek(Duration.ZERO);
//       }
//   });
//mediaPlayer.play();
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        System.out.println("Enter the request ID, request time,  source floor, destination floor");
        System.out.println("There are total of 11 floors,floor G(enter 0 for G)-floor 10");
        window = primaryStage;
        window.setTitle("Elevator Simulator");

        //Input image
//        FileInputStream input = new FileInputStream("elevator.png");
//        FileInputStream in = new FileInputStream("WhatsApp Image 2018-04-23 at 16.58.26.jpeg");
        Image img = new Image("WhatsApp Image 2018-04-23 at 16.58.26.jpeg");
        Image image = new Image("elevator.png");
        ImageView imageView = new ImageView(image);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(150);
        imgView.setFitWidth(250);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setTranslateX(490);
        imageView.setTranslateY(80);
        imgView.setTranslateX(100);
        imgView.setTranslateY(-50);

        //animate the image
        transition(imageView);

        //Number of request label
        Label numLabel = new Label("Select number of request:");
        numLabel.setTranslateX(0);
        numLabel.setTranslateY(110);

        //Number of request combo box
        ComboBox<Integer> req = new ComboBox<>();
        req.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        req.getSelectionModel().select(0);
        req.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                count = 0;
                arr = new ArrayList<>(newValue);
                upList = new ArrayList<>();
                downList = new ArrayList<>();
            }
        });
        req.setTranslateX(190);
        req.setTranslateY(110);

        //request count label
        Label reqcount = new Label("Enter request  " + reqnum);
        GridPane.setConstraints(reqcount, 0, 5);

        //ID label
        Label idLabel = new Label("Request ID:");
        GridPane.setConstraints(idLabel, 0, 6);

        //ID textfield
//        TextField id = new TextField();
        Label id = new Label();
        id.setText(String.format("%04d", 1));

        id.setMaxWidth(130);
        //id.setPromptText("eg:0001");
        id.setTranslateX(190);
        id.setTranslateY(225);
        //GridPane.setConstraints(id, 1, 6);

        //Request Time label
        Label timeLabel = new Label("Enter request time:");
        GridPane.setConstraints(timeLabel, 0, 7);

        //Request Time textfield
        TextField time = new TextField();
        time.setMaxWidth(130);
        time.setTranslateX(190);
        time.setTranslateY(270);
        //GridPane.setConstraints(time, 1, 7);

        //Source floor label
        Label srclabel = new Label("Select source floor:");
        GridPane.setConstraints(srclabel, 0, 8);

        //Source floor combobox
        ComboBox<Integer> src = new ComboBox<>();
        src.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        src.setTranslateX(190);
        src.setTranslateY(310);
        //GridPane.setConstraints(src, 1, 8);

        //Destination floor label
        Label destlabel = new Label("Select destination:");
        GridPane.setConstraints(destlabel, 0, 9);
        
        //file name label
        Label fileLabel = new Label("Enter log file name:");
        GridPane.setConstraints(fileLabel, 0, 10);
        //filename text field
        
        filetName.setMaxWidth(130);
        filetName.setTranslateX(190);
        filetName.setTranslateY(385);
        
        
        //Destination combobox
        ComboBox<Integer> dest = new ComboBox<>();
        dest.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        dest.setTranslateX(190);
        dest.setTranslateY(350);
        //GridPane.setConstraints(dest, 1, 9);

        //Create  request button
        Button btn = new Button("Request");
        btn.setId("requestBtn");
        btn.setTranslateX(190);
        btn.setTranslateY(420);
        //GridPane.setConstraints(btn, 1, 10);

        // Run elevator
        Button displaybtn = new Button("Confirm & Run Elevator");
        displaybtn.setId("reportBtn");
        displaybtn.setTranslateX(-5);
        displaybtn.setTranslateY(460);
        displaybtn.setPadding(new Insets(5, 100, 5, 100));
        displaybtn.setTextAlignment(TextAlignment.CENTER);
        //GridPane.setConstraints(displaybtn, 2, 10);
        displaybtn.setDisable(true);
        displaybtn.setOnAction(e -> {
            play_go();

             try{
                fileName=filetName.getText()+".txt";
                File file=new File(fileName);
                PrintWriter pw=new PrintWriter(new FileOutputStream(fileName));
             }catch(IOException o){}

            btn.setDisable(false);
            displaybtn.setDisable(true);
            try {
                RunElevator.doIt(arr, upList, downList);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(elevatorgui.class.getName()).log(Level.SEVERE, null, ex);
            }
            window.close();
            testingMain.start();

        });

        //request button action
        btn.setOnAction(e -> {
            play_request();

            if (src.getSelectionModel().getSelectedItem() == dest.getSelectionModel().getSelectedItem()) {
                reenter = false;
                alertbox.warning();

            }

            if (reenter) {
                reqcount.setText("Enter request   " + (reqnum + 1));
                if (reqnum == req.getValue()) {
                    reqcount.setText("Enter request   " + reqnum);
                    btn.setDisable(true);
                    displaybtn.setDisable(false);
                }

                if (count < req.getValue()) {
                    r = new Request(Integer.parseInt(id.getText()), Integer.parseInt(time.getText()), src.getValue(), dest.getValue());
                    id.setText(String.format("%04d", reqnum + 1));
                    if (reqnum == req.getValue()) {
                        id.setText(String.format("%04d", reqnum));
                    }

                    arr.add(r);
                    count++;
                    reqnum++;
                }
            }
        });

        //create reset button
        Button resetbtn = new Button("Reset");
        resetbtn.setId("reportBtn");
        resetbtn.setTranslateX(270);
        resetbtn.setTranslateY(420);
        resetbtn.setOnAction(e -> {

play_audio();
            temp++;

            arr = new ArrayList<>();
            upList = new ArrayList<>();
            downList = new ArrayList<>();
            RunElevator.transitionRequest.clear();
            req.getSelectionModel().select(0);

            time.setText("");
            filetName.setText("");
            src.getSelectionModel().clearSelection();
            dest.getSelectionModel().clearSelection();
//            arr = new ArrayList<>();
//            upList = new ArrayList<>();
//            downList = new ArrayList<>();
            reqnum = 1;
            id.setText(String.format("%04d", 1));
            reqcount.setText("Enter request 1");
            newWin = true;
            reenter=true;
            count=0;
            btn.setDisable(false);
            displaybtn.setDisable(true);
        });

        


        //Group member name
        Label jy = new Label("By : Ng Jih Yann");
        Label hwee = new Label("       Wee Weing Hwee");
        Label ang = new Label("       Ang Chun Wah");
        Label qian = new Label("       Eng Tze Qian");

        jy.setTranslateX(-310);
        jy.setTranslateY(550);
        hwee.setTranslateX(-225);
        hwee.setTranslateY(550);
        ang.setTranslateX(-100);
        ang.setTranslateY(550);
        qian.setTranslateX(10);
        qian.setTranslateY(550);

        play_name();
        //creating layout
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(80, 20, 20, 330));
        layout.setVgap(20);
        layout.setHgap(0);
        layout.getChildren().addAll(qian, hwee, ang, jy, imgView, idLabel, id, timeLabel, time, dest, destlabel, srclabel, src, btn, imageView, numLabel, req, displaybtn, reqcount, resetbtn,fileLabel,filetName);
        Scene scene = new Scene(layout, 1080, 720);
        scene.getStylesheets().add("style.css");
        window.getIcons().add(new Image("elevator.png"));
        window.setScene(scene);
        window.show();

    }
               public void play_name(){
        AudioClip note = new AudioClip(this.getClass().getResource("noname7.mp3").toString());
        note.play();
    }
        public void play_request(){
        AudioClip note = new AudioClip(this.getClass().getResource("request.mp3").toString());
        note.play();
    }
    public void play_audio(){
        AudioClip note = new AudioClip(this.getClass().getResource("reset.mp3").toString());
        note.play();
    }
        public void play_go(){
        AudioClip note = new AudioClip(this.getClass().getResource("start.mp3").toString());
        note.play();
    }
    public static void transition(ImageView imageView) {
        TranslateTransition tt = new TranslateTransition();
        tt.setToY(330);
        tt.setAutoReverse(true);
        tt.setCycleCount(Timeline.INDEFINITE);
        tt.setDuration(Duration.seconds(2.3));
        tt.setNode(imageView);
        tt.play();
    }

    public static void main(String[] args) {
        launch(args);
//        Application.launch(Transition.class,args);
    }

}
