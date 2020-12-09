package dsfinalprojectelevatorsimulator;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import static dsfinalprojectelevatorsimulator.elevatorgui.filetName;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunElevator {
   
    static Document document = new Document();
    protected static ArrayList<transitionRequest>transitionRequest = new ArrayList<>();
    protected static Elevator elevatorReal = new Elevator();
    protected static ArrayList<Request> sortedR = new ArrayList<>();
    protected static ArrayList<Integer> requestProcess = new ArrayList<>();
    private static int processed = 0;
    
    public static void doIt(ArrayList<Request> arr, ArrayList<Request> upList, ArrayList<Request> downList) throws FileNotFoundException {
                    try {
                PdfWriter.getInstance(document,new FileOutputStream(elevatorgui.filetName.getText()+".pdf"));
            } catch (FileNotFoundException | DocumentException ex) {
                Logger.getLogger(elevatorgui.class.getName()).log(Level.SEVERE, null, ex);
            }
            document.open();

               
        try {
             Image imag =Image.getInstance("noname7snip.png");
            document.add(imag);
        } catch (DocumentException ex) {
            Logger.getLogger(RunElevator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RunElevator.class.getName()).log(Level.SEVERE, null, ex);
        }


             RunElevator.printLog(new Date().toString());
         RunElevator.printLog("Elevator report by Group NoNameSeven");
         pdflog(new Date().toString());
         pdflog("Elevator report by Group NoNameSeven");
         
          RunElevator.printLog("Members: Ng Jih Yann,Wee Weing Hwee ,Ang Chun Wah, Eng Tze Qian");
          pdflog("Members: Ng Jih Yann,Wee Weing Hwee ,Ang Chun Wah, Eng Tze Qian");
          RunElevator.pdflog("");
          RunElevator.pdflog("Elevator Log");
          RunElevator.pdflog("");
         RunElevator.printLog("");
          RunElevator.printLog("Elevator Log");
          RunElevator.printLog("");
        display(arr);
        sortRequest(arr);
        display(arr);
        splitRequest(arr, upList, downList);
        System.out.println("Displaying up list:");
        display(upList);
        System.out.println("Displaying down list");
        display(downList);
        runList(upList, downList, arr);
         RunElevator.printLog("");
        calculator(sortedR, requestProcess);
         RunElevator.printLog("");
        RunElevator.printLog("1 second consumes 2.5Watt of electrical energy");
        pdflog("1 second consumes 2.5Watt of electrical energy");
        System.out.println("1 second consumes 2.5Watt of electrical energy");
        RunElevator.printLog(elevatorReal.getCurrentTime()+" seconds are used for this trip");
        pdflog(elevatorReal.getCurrentTime()+" seconds are used for this trip");
        System.out.println(elevatorReal.getCurrentTime()+" seconds are used for this trip");
         RunElevator.printLog(elevatorReal.getCurrentTime()*2.5+" Watt of electrical energy is used");
         pdflog(elevatorReal.getCurrentTime()*2.5+" Watt of electrical energy is used");
        System.out.println(elevatorReal.getCurrentTime()*2.5+" Watt of electrical energy is used");
        elevatorReal.reset();
        sortedR = new ArrayList<>();
        requestProcess = new ArrayList<>();
        processed = 0;
        document.close();
    }

    public static void pdflog(String string){
        try {
document.add(new Paragraph(string));
        } catch (DocumentException ex) {
            Logger.getLogger(RunElevator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static void printLog(String string) {
        try {
            
            PrintWriter pw = new PrintWriter(new FileOutputStream(elevatorgui.fileName, true));
            pw.println(string);
            pw.close();
        } catch (IOException e) {
        }
    }

    public static void display(ArrayList<Request> arr) {
        for (int i = 0; i < arr.size(); i++) {
            System.out.print("Request id is : " + arr.get(i).getRequestID());
            System.out.print(" Request time is : " + arr.get(i).getRequestTime());
            System.out.print(" Source floor is : " + arr.get(i).getSourceFloor());
            System.out.print(" Destination floor is : " + arr.get(i).getDestinationFloor());
            System.out.println("");
        }
    }

    public static void sortRequest(ArrayList<Request> arr) {
        //sort the requests based on requestTime
        System.out.println("Sorting request...");
        boolean needNextPass = true;
        for (int k = 1; k < arr.size() && needNextPass; k++) {
            needNextPass = false;
            for (int i = 0; i < arr.size() - k; i++) {
                if (arr.get(i).getRequestTime() > arr.get(i + 1).getRequestTime()) {
                    Request temp = arr.get(i);
                    arr.set(i, arr.get(i + 1));
                    arr.set(i + 1, temp);
                    needNextPass = true;
                }
            }
        }
    }

    public static void InsertUp(ArrayList<Request> arr, ArrayList<Request> up) {
        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i + 1).getDirection().equals("up")) {
                if (arr.get(0).move() >= arr.get(i + 1).getRequestTime()) {
                    if (arr.get(0).getElevatorFloor(arr.get(i + 1).getRequestTime()) < arr.get(i + 1).getSourceFloor()) {
                        up.add(arr.get(i + 1));
                    }

                }
            }
        }
    }

    public static void splitRequest(ArrayList<Request> arr, ArrayList<Request> up, ArrayList<Request> down) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getDirection().equals("up")) {
                up.add(arr.get(i));
            } else {
                down.add(arr.get(i));
            }
        }
    }

    public static void calculator(ArrayList<Request> sortedR, ArrayList<Integer> requestProcess) {
        for (int i = 0; i < sortedR.size(); i++) {
            RunElevator.printLog("*************************************************************");
            pdflog("*************************************************************");
            System.out.println("*************************************************************");
            RunElevator.printLog("Request of id (" + sortedR.get(i).getRequestID() + ") " + " Source: " + sortedR.get(i).getSourceFloor() + " Destination: " + sortedR.get(i).getDestinationFloor());
            System.out.println("Request of id (" + sortedR.get(i).getRequestID() + ") " + " Source: " + sortedR.get(i).getSourceFloor() + " Destination: " + sortedR.get(i).getDestinationFloor());
            pdflog("Request of id (" + sortedR.get(i).getRequestID() + ") " + " Source: " + sortedR.get(i).getSourceFloor() + " Destination: " + sortedR.get(i).getDestinationFloor());
            int time = requestProcess.get(i) - sortedR.get(i).getRequestTime();
            System.out.println("Time elapsed for request  = " + time);
            RunElevator.printLog("Time elapsed for request  = " + time);
            pdflog("Time elapsed for request  = " + time);

        }
        System.out.println("-----------------------------------------------------------");
        RunElevator.printLog("-----------------------------------------------------------");
        pdflog("-----------------------------------------------------------");
        System.out.println("Total time elapsed for whole process: " + elevatorReal.getCurrentTime());
        RunElevator.printLog("Total time elapsed for whole process: " + elevatorReal.getCurrentTime());
        pdflog("Total time elapsed for whole process: " + elevatorReal.getCurrentTime());
    }

    public static void runList(ArrayList<Request> up, ArrayList<Request> down, ArrayList<Request> arr) { //need to run destination and need to identify direction
        ArrayList<Request> tempdown = (ArrayList<Request>) down.clone();
        ArrayList<Request> tempup = (ArrayList<Request>) up.clone();
        Request savedDownRequest = null;
        Request savedRequest = null;
        int savedDestination = -100;
        int savedDownDestination = -100;
        int upSize = up.size();
        int downSize = down.size();
        int upCount = 0;
        int downCount = 0;
        int totalSize = upSize + downSize;
        int totalCount = upCount + downCount;
        boolean goUp = false;
        ArrayList<Integer> source = new ArrayList<>();
        ArrayList<Integer> destination = new ArrayList<>();
        ArrayList<Request> tempUp = up;
        ArrayList<Request> tempDown = down;
        boolean upEmpty = false;
        boolean downEmpty = false;

        ArrayList<Integer> goToOpenFrequency = new ArrayList<>();
        int trackgoto = 0;

        int uploopc, upaddedc, downloopc, downaddedc;
        uploopc = downloopc = -1;
        upaddedc = downaddedc = 1;
        while (upCount + downCount < totalSize) {
            processed = sortedR.size();
            //determine which direction by comparing requestTime
            //what if there is no element in upList or downList
            if (up.size() == 0) {
                goUp = false;
                upEmpty = true;
            }
            if (down.size() == 0) {
                goUp = true;
                downEmpty = true;
            }

            if (upEmpty != true && downEmpty != true) {
                if (up.get(0).getRequestTime() < down.get(0).getRequestTime()) {//if up.get(0) is null, we will get an error,so if there is null,we cannot let this code to be run
                    goUp = true;
                } else if (up.get(0).getRequestTime() == down.get(0).getRequestTime()) {
                    int upfloorDif = Math.abs(elevatorReal.getCurrentFloor() - up.get(0).getSourceFloor());//always be a positive integer
                    int downfloorDif = Math.abs(elevatorReal.getCurrentFloor() - down.get(0).getSourceFloor());//always be a positive integer
                    if (upfloorDif < downfloorDif) {
                        goUp = true;
                    } else {
                        goUp = false;
                    }
                } else {
                    goUp = false;
                }
            }

            if (upCount == upSize) {//all up has been processed
                goUp = false;
            }
            if (downCount == downSize) {
                goUp = true;
            }

            System.out.println("goUp =" + goUp);
            //run goTo down
            if (goUp == false) {// add source and destination into ArrayList
                int downremoveCount = 0;
                int ggg = 0;
                source.clear();
                destination.clear();
                for (int i = 0; i < down.size(); i++) {

                    downloopc++;
                    int floorofFirst = down.get(0).getSourceFloor();
                    int tempy = 0;

                    if (i == 0 && down.get(0).getRequestTime() > elevatorReal.getCurrentTime()) { // new change time
                        System.out.println(elevatorReal.getCurrentTime() + ": elevator stays idle at floor" + elevatorReal.getCurrentFloor());
                        RunElevator.printLog(elevatorReal.getCurrentTime() + ": elevator stays idle at floor" + elevatorReal.getCurrentFloor());
                        pdflog(elevatorReal.getCurrentTime() + ": elevator stays idle at floor" + elevatorReal.getCurrentFloor());
                        elevatorReal.setCurrentTime(down.get(0).getRequestTime());
                         
                     
                    }
                    if (i == 0) {
                        source.add(down.get(i).getSourceFloor());
//                          if(down.get(down.size()-1).getDestinationFloor()!=up.get(0).getSourceFloor()){ //modified
                        destination.add(down.get(i).getDestinationFloor());
                        sortedR.add(down.get(i));
//                          }
                        downCount++;
                        downremoveCount++;

                        continue;
                    }
                    if (down.get(0).getFloor(down.get(i).getRequestTime(), down.get(i)) == -100) {
                        if (down.get(i).getSourceFloor() <= down.get(0).getSourceFloor()) {
                            source.add(down.get(i).getSourceFloor());
//                          if(down.get(down.size()-1).getDestinationFloor()!=up.get(0).getSourceFloor()){ //modified
                            destination.add(down.get(i).getDestinationFloor());
                            sortedR.add(down.get(i));
//                          }
                            downCount++;
                            downremoveCount++;
                            Request tempo = down.get(i);
                            ggg += 1;
                            down.set(i, down.get(ggg));
                            down.set(ggg, tempo);

//                           Request temptemp = tempdown.get(downloopc);
//                           tempdown.set(downloopc, tempdown.get(downaddedc));
//                           tempdown.set(downaddedc,temptemp);
//                           downaddedc++;
                            continue;
                        }
                    }
                    if (down.get(0).move() >= down.get(i).getRequestTime()) { //changed
//                      if(down.get(0).getFloor(down.get(i).getRequestTime())==-100){
//                          tempy=floorofFirst;
//                      }
//                      else{

                        tempy = down.get(0).getFloor(down.get(i).getRequestTime(), down.get(i));
//                      }
                        if (tempy >= down.get(i).getSourceFloor()) {//already processed more than one up

                            source.add(down.get(i).getSourceFloor());
//                          if(down.get(down.size()-1).getDestinationFloor()!=up.get(0).getSourceFloor()){ //modified
                            destination.add(down.get(i).getDestinationFloor());
                            sortedR.add(down.get(i));
//                          }
                            downCount++;
                            downremoveCount++;
                            Request tempo = down.get(i);
                            ggg += 1;
                            down.set(i, down.get(ggg));
                            down.set(ggg, tempo);

//                            Request temptemp = tempdown.get(downloopc);
//                           tempdown.set(downloopc, tempdown.get(downaddedc));
//                           tempdown.set(downaddedc,temptemp);
//                           downaddedc++;
                        }
                    }

                }

                //remove added down list
                Request firstDownSourceRemoved = null;
                for (int j = 0; j < downremoveCount; j++) {
                    Request x = down.remove(0);
                    if (j == 0) {
                        firstDownSourceRemoved = x;
                    }
                    if (j == downremoveCount - 1) {
                        savedDownRequest = x;
                    }
                }
//            for(int s=1;s<down.size();s++){ // sort new down.get(0)
//                for(int j=0;j<down.size()-1;j++){ // sort new down.get(0)
//                            Request temp=down.get(j);
//                            down.set(j, down.get(j+1));
//                            down.set(j+1, temp);
//                          }
//            }
//            
                //sort source so that smallest is at 0 using bubble sort( biggest???)
                for (int j = 1; j < source.size(); j++) {
                    for (int s = 0; s < source.size() - 1; s++) {
                        if (source.get(s) < source.get(s + 1)) {
                            int temp = source.get(s);
                            source.set(s, source.get(s + 1));
                            source.set(s + 1, temp);
                        }
                    }
                }
                //sort destination
                for (int j = 1; j < destination.size(); j++) {
                    for (int s = 0; s < destination.size() - 1; s++) {
                        if (destination.get(s) < destination.get(s + 1)) {
                            int temp = destination.get(s);
                            destination.set(s, destination.get(s + 1));
                            destination.set(s + 1, temp);
                            //sort sortedR as well
                            int x = processed + s;
                            Request temp2 = sortedR.get(x);
                            sortedR.set(x, sortedR.get(x + 1));
                            sortedR.set(x + 1, temp2);
                        }
                    }
                }

                savedDownDestination = destination.get(destination.size() - 1);

                System.out.println(source.toString());
                System.out.println(destination.toString());

                int sourceWent = 0; //to determine how many source have been run,if all have been run, sourceFirstValue = -1
                int destinationWent = 0;
                int goSize = source.size() + destination.size();
                int sourcesize = source.size();
                int destinationsize = destination.size();
                while (sourceWent + destinationWent < goSize) {
                    int sourceFirstValue = source.get(0);
                    int destinationFirstValue = destination.get(0);
                    //if source smaller then go to source first,else go to destination first
                    int frequency = 0;

                    if (sourceWent >= sourcesize) {
                        sourceFirstValue = -1;
                    }

                    if (destinationWent >= destinationsize) {
                        destinationFirstValue = -1;
                    }

                    if (sourceFirstValue > destinationFirstValue) {

                        //1.find frequency of source 
                        for (int j = 0; j < source.size(); j++) {
                            if (source.get(0).equals(source.get(j))) {
                                frequency++;// at least 1
                            }
                        }
                        //go to source
                        if (sourceWent > 0) {//modified **************************************
                            elevatorReal.goTo(source.get(0), true, frequency, "down", arr);
                            transitionRequest.add(new transitionRequest(source.get(0),"in",frequency));
                        } else if (sourceWent == 0) {
                            if (savedRequest != null) {
                                if (savedRequest.move() - (elevatorReal.getCurrentTime() - savedRequest.getRequestTime()) >= firstDownSourceRemoved.getRequestTime()) {
                                    if (upCount == tempup.size()) {

                                        if (savedDestination == source.get(0)) {
                                            elevatorReal.goToHalfClose(savedDestination, frequency, arr);
                                            transitionRequest.add(new transitionRequest(savedDestination,"halfclose",frequency));
                                            for (int i = 0; i < goToOpenFrequency.get(trackgoto); i++) {
                                                requestProcess.add(elevatorReal.getCurrentTime());
                                            }
                                            trackgoto += 1;

                                        } else {
                                            elevatorReal.goTo(source.get(0), true, frequency, "down", arr);
                                            transitionRequest.add(new transitionRequest(source.get(0),"in",frequency));
                                        }
                                    }
                                    //////////////////////
                                    if (upCount < tempup.size()) {
//                                    System.out.println("yesfffff");
                                        boolean smallestT = false;
                                        for (int gg = upCount; gg < tempup.size(); gg++) {
                                            if (tempup.get(gg).getRequestTime() < firstDownSourceRemoved.getRequestTime()) {
                                                smallestT = true;
                                            }
                                        }
                                        if (smallestT == true) {
                                            elevatorReal.goTo(source.get(0), true, frequency, "down", arr);
                                            transitionRequest.add(new transitionRequest(source.get(0),"in",frequency));

                                        } else {
                                            if (savedDestination == firstDownSourceRemoved.getSourceFloor()) {  //modified
                                                elevatorReal.goToHalfClose(savedDestination, frequency, arr);
                                                transitionRequest.add(new transitionRequest(savedDestination,"halfclose",frequency));
                                                for (int i = 0; i < goToOpenFrequency.get(trackgoto); i++) {
                                                    requestProcess.add(elevatorReal.getCurrentTime());
                                                }
                                                trackgoto += 1;
                                            } else {
                                                elevatorReal.goTo(source.get(0), true, frequency, "down", arr);
                                                transitionRequest.add(new transitionRequest(source.get(0),"in",frequency));
                                            }
                                        }
                                    }
                                } else {
                                    elevatorReal.goTo(source.get(0), true, frequency, "down", arr);
                                    transitionRequest.add(new transitionRequest(source.get(0),"in",frequency));
                                }
                            } else {
                                elevatorReal.goTo(source.get(0), true, frequency, "down", arr);
                                transitionRequest.add(new transitionRequest(source.get(0),"in",frequency));
                            }//end savedDownRequest==null

                        }

                        for (int j = 0; j < frequency; j++) {
                            sourceWent++;
                        }

                        //make multiple source into only one source
                        ArrayList<Integer> newsource = new ArrayList<>();
                        if (frequency > 1) {
                            for (int j = 0; j < source.size(); j++) {
                                if (j == 0) {
                                    newsource.add(source.get(0));
                                } else if (!source.get(0).equals(source.get(j))) {
                                    newsource.add(source.get(j));
                                }
                            }

                            source = newsource;
                        }//end if

                        //sort new .get(0)
                        if (source.size() > 1) {//if only one then no need sort
                            for (int j = 0; j < source.size() - 1; j++) {
                                int temp = source.get(j);
                                source.set(j, source.get(j + 1));
                                source.set(j + 1, temp);
                            }
                        }
                    } //else if
                    else if (destinationFirstValue > sourceFirstValue) {

                        //1.find frequency of destination 
                        frequency = 0; //reinitialized frequency
                        for (int j = 0; j < destination.size(); j++) {
                            if (destination.get(0).equals(destination.get(j))) {
                                frequency++;// at least 1
                            }
                        }
                        //go to destination

//                elevatorReal.goTo(destination.get(0), false, frequency,"down");
                        for (int j = 0; j < frequency; j++) {
                            destinationWent += 1;
                        }

                        if (destinationWent < downremoveCount) {//modified **************************************
                            elevatorReal.goTo(destination.get(0), false, frequency, "down", arr);
                            transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                            for (int i = 0; i < frequency; i++) {
                                requestProcess.add(elevatorReal.getCurrentTime());
                            }

                        } else if (destinationWent == downremoveCount) {

                            if (savedDownRequest != null) {

                                if (up.size() != 0) {

                                    if (savedDownRequest.move() - (elevatorReal.getCurrentTime() - savedDownRequest.getRequestTime()) >= up.get(0).getRequestTime()) {

                                        if (downCount == tempdown.size()) {

                                            if (savedDownDestination == up.get(0).getSourceFloor()) {  //modified
                                                elevatorReal.goToHalfOpen(savedDownDestination, frequency, arr);
                                                  transitionRequest.add(new transitionRequest(savedDownDestination,"halfopen",frequency));
                                                goToOpenFrequency.add(frequency);
                                            } else {
                                                elevatorReal.goTo(destination.get(0), false, frequency, "down", arr);
                                                transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                                                for (int i = 0; i < frequency; i++) {
                                                    requestProcess.add(elevatorReal.getCurrentTime());
                                                }
                                            }
                                        }
                                        if (downCount < tempdown.size()) {

                                            boolean smallestT = false;
                                            for (int gg = downCount; gg < tempdown.size(); gg++) {
                                                if (tempdown.get(gg).getRequestTime() < up.get(0).getRequestTime()) {
                                                    smallestT = true;
                                                }
                                            }
                                            if (smallestT == true) {
                                                elevatorReal.goTo(destination.get(0), false, frequency, "down", arr);
                                                transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                                                for (int i = 0; i < frequency; i++) {
                                                    requestProcess.add(elevatorReal.getCurrentTime());
                                                }
                                            } else {
                                                if (savedDownDestination == up.get(0).getSourceFloor()) {  //modified
                                                    elevatorReal.goToHalfOpen(savedDownDestination, frequency, arr);
                                                    transitionRequest.add(new transitionRequest(savedDownDestination,"halfopen",frequency));
                                                    goToOpenFrequency.add(frequency);
                                                } else {
                                                    elevatorReal.goTo(destination.get(0), false, frequency, "down", arr);
                                                    transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                                                    for (int i = 0; i < frequency; i++) {
                                                        requestProcess.add(elevatorReal.getCurrentTime());
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        elevatorReal.goTo(destination.get(0), false, frequency, "down", arr);
                                        transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                                        for (int i = 0; i < frequency; i++) {
                                            requestProcess.add(elevatorReal.getCurrentTime());
                                        }
                                    }

                                } else {
                                    elevatorReal.goTo(destination.get(0), false, frequency, "down", arr);
                                    transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                                    for (int i = 0; i < frequency; i++) {
                                        requestProcess.add(elevatorReal.getCurrentTime());
                                    }
                                }
                            } else {
                                elevatorReal.goTo(destination.get(0), false, frequency, "down", arr);
                                transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                                for (int i = 0; i < frequency; i++) {
                                    requestProcess.add(elevatorReal.getCurrentTime());
                                }
                            }

                        }
                        //make multiple destination into only one destination
                        ArrayList<Integer> newdestination = new ArrayList<>();
                        if (frequency > 1) {
                            for (int j = 0; j < destination.size(); j++) {
                                if (j == 0) {
                                    newdestination.add(destination.get(0));
                                } else if (!destination.get(0).equals(destination.get(j))) {
                                    newdestination.add(destination.get(j));
                                }
                            }

                            destination = newdestination;
                        }//end if

                        //sort new .get(0) for destination
                        if (destination.size() > 1) {//if only one then no need sort
                            for (int j = 0; j < destination.size() - 1; j++) {
                                int temp = destination.get(j);
                                destination.set(j, destination.get(j + 1));
                                destination.set(j + 1, temp);
                            }
                        }
                    } else if (destinationFirstValue == sourceFirstValue) { // for down
//            1.find frequency of destination 

                        int sourcefrequency = 0;
                        int destinationfrequency = 0;//reinitialized frequency
                        for (int j = 0; j < source.size(); j++) {
                            if (source.get(0).equals(source.get(j))) {
                                sourcefrequency++;// at least 1
                            }
                        }
                        for (int j = 0; j < destination.size(); j++) {
                            if (destination.get(0).equals(destination.get(j))) {
                                destinationfrequency++;// at least 1
                            }
                        }
                        elevatorReal.goToHalfOpen(destinationFirstValue, destinationfrequency, arr);
                        transitionRequest.add(new transitionRequest(destinationFirstValue,"halfopen",destinationfrequency));
                        elevatorReal.goToHalfClose(destinationFirstValue, sourcefrequency, arr);
                         transitionRequest.add(new transitionRequest(destinationFirstValue,"halfclose",sourcefrequency));
                        for (int i = 0; i < destinationfrequency; i++) {
                            requestProcess.add(elevatorReal.getCurrentTime());
                        }

                        for (int j = 0; j < sourcefrequency; j++) {
                            sourceWent += 1;
                        }
                        for (int j = 0; j < destinationfrequency; j++) {
                            destinationWent += 1;
                        }
                        ArrayList<Integer> newdestination = new ArrayList<>();
                        if (destinationfrequency > 1) {
                            for (int j = 0; j < destination.size(); j++) {
                                if (j == 0) {
                                    newdestination.add(destination.get(0));
                                } else if (!destination.get(0).equals(destination.get(j))) {
                                    newdestination.add(destination.get(j));
                                }
                            }

                            destination = newdestination;
                        }//end if

                        ArrayList<Integer> newsource = new ArrayList<>();
                        if (sourcefrequency > 1) {
                            for (int j = 0; j < source.size(); j++) {
                                if (j == 0) {
                                    newsource.add(source.get(0));
                                } else if (!source.get(0).equals(source.get(j))) {
                                    newsource.add(source.get(j));
                                }
                            }

                            source = source;
                        }//end if
                        //sort new .get(0) for destination
                        if (source.size() > 1) {//if only one then no need sort
                            for (int j = 0; j < source.size() - 1; j++) {
                                int temp = source.get(j);
                                source.set(j, source.get(j + 1));
                                source.set(j + 1, temp);
                            }
                        }
                        //sort new .get(0) for destination
                        if (destination.size() > 1) {//if only one then no need sort
                            for (int j = 0; j < destination.size() - 1; j++) {
                                int temp = destination.get(j);
                                destination.set(j, destination.get(j + 1));
                                destination.set(j + 1, temp);
                            }
                        }
                    }
                }//end big while loop
            }//end if goUp==false

            if (goUp == true) { // add source and destination into ArrayList
                source.clear();
                destination.clear();
                int upremoveCount = 0;
                int tempx = 0;
                int ggg = 0;
                for (int i = 0; i < up.size(); i++) {
                    uploopc++;
                    int firstFloor = up.get(0).getSourceFloor();
                    if (i == 0 && up.get(0).getRequestTime() > elevatorReal.getCurrentTime()) { // new change time
                        System.out.println(elevatorReal.getCurrentTime() + ": elevator stays idle at floor" + elevatorReal.getCurrentFloor());
                        RunElevator.printLog(elevatorReal.getCurrentTime() + ": elevator stays idle at floor" + elevatorReal.getCurrentFloor());
                        pdflog(elevatorReal.getCurrentTime() + ": elevator stays idle at floor" + elevatorReal.getCurrentFloor());
                        elevatorReal.setCurrentTime(up.get(0).getRequestTime());
                    }
                    if (i == 0) {
                        source.add(up.get(i).getSourceFloor());
//                          if(up.get(up.size()-1).getDestinationFloor()!=down.get(0).getSourceFloor()){
                        destination.add(up.get(i).getDestinationFloor());
                        sortedR.add(up.get(i));
//                          }
                        upCount++;
                        upremoveCount++;
                        continue;
                    }
                    if (up.get(0).getFloor(up.get(i).getRequestTime(), up.get(i)) == -100) {
//                    System.out.println("floor time"+up.get(i).getRequestTime());
//                    System.out.println("current time "+elevatorReal.getCurrentTime());
//                    System.out.println("*************************************************");
                        if (up.get(i).getSourceFloor() >= up.get(0).getSourceFloor()) {
                            source.add(up.get(i).getSourceFloor());
//                          if(up.get(up.size()-1).getDestinationFloor()!=down.get(0).getSourceFloor()){
                            destination.add(up.get(i).getDestinationFloor());
                            sortedR.add(up.get(i));
//                          }
                            upCount++;
                            upremoveCount++;
                            ggg += 1;
                            Request tempo = up.get(i);
                            up.set(i, up.get(ggg));
                            up.set(ggg, tempo);

//                            Request temptemp = tempup.get(uploopc);
//                           tempup.set(uploopc, tempup.get(upaddedc));
//                           tempup.set(upaddedc,temptemp);
//                           upaddedc++;
                            continue;
                        }
                    }
                    if (up.get(0).move() >= up.get(i).getRequestTime()) { //changed

                        tempx = up.get(0).getFloor(up.get(i).getRequestTime(), up.get(i));
//                      }
                        if (tempx <= up.get(i).getSourceFloor()) {//already processed more than one up
                            source.add(up.get(i).getSourceFloor());
//                          if(up.get(up.size()-1).getDestinationFloor()!=down.get(0).getSourceFloor()){
                            destination.add(up.get(i).getDestinationFloor());
                            sortedR.add(up.get(i));
//                          }
                            upCount++;
                            upremoveCount++;
                            Request tempo = up.get(i);
                            ggg += 1;

                            up.set(i, up.get(ggg));
                            up.set(ggg, tempo);

//                           Request temptemp = tempup.get(uploopc);
//                           tempup.set(uploopc, tempup.get(upaddedc));
//                           tempup.set(upaddedc,temptemp);
//                           upaddedc++;
                        }
                    }

                }

                Request firstSourceRemoved = null;

                for (int j = 0; j < upremoveCount; j++) {//remove added up list

                    Request x = up.remove(0);
                    if (j == 0) {
                        firstSourceRemoved = x;
                    }
                    if (j == upremoveCount - 1) {
                        savedRequest = x;
                    }
                }

//            for(int s=0;s<up.size();s++){ // sort new up.get(0)
//                for(int j=0;j<up.size()-1;j++){ 
//                                Request temp=up.get(j);
//                                up.set(j, up.get(j+1));
//                                up.set(j+1, temp);
//                              }
//            }
                //sort source so that smallest is at 0 using bubble sort
                for (int j = 1; j < source.size(); j++) {
                    for (int s = 0; s < source.size() - 1; s++) {
                        if (source.get(s) > source.get(s + 1)) {
                            int temp = source.get(s);
                            source.set(s, source.get(s + 1));
                            source.set(s + 1, temp);
                        }
                    }
                }
                //sort destination
                for (int j = 1; j < destination.size(); j++) {
                    for (int s = 0; s < destination.size() - 1; s++) {
                        if (destination.get(s) > destination.get(s + 1)) {
                            int temp = destination.get(s);
                            destination.set(s, destination.get(s + 1));
                            destination.set(s + 1, temp);
                            //sort sortedR as well
                            int x = processed + s;
                            Request temp2 = sortedR.get(x);
                            sortedR.set(x, sortedR.get(x + 1));
                            sortedR.set(x + 1, temp2);
                        }
                    }
                }

                savedDestination = destination.get(destination.size() - 1);
                System.out.println(source.toString());
                System.out.println(destination.toString());

                int sourceWent = 0; //to determine how many source have been run,if all have been run, sourceFirstValue = -1
                int destinationWent = 0;
                int goSize = source.size() + destination.size();
                int sourcesize = source.size();
                int destinationsize = destination.size();
                while (sourceWent + destinationWent < goSize) {
                    int sourceFirstValue = source.get(0);
                    int destinationFirstValue = destination.get(0);
                    //if source smaller then go to source first,else go to destination first
                    int frequency = 0;

                    if (sourceWent >= sourcesize) {
                        sourceFirstValue = 100;
                    }

                    if (destinationWent >= destinationsize) {
                        destinationFirstValue = 100;
                    }
                    ;
                    if (sourceFirstValue < destinationFirstValue) {
                        //1.find frequency of source 
                        for (int j = 0; j < source.size(); j++) {
                            if (source.get(0).equals(source.get(j))) {
                                frequency++;// at least 1
                            }
                        }
                        //go to source

                        if (sourceWent > 0) {//modified **************************************

                            elevatorReal.goTo(source.get(0), true, frequency, "up", arr);
                             transitionRequest.add(new transitionRequest(source.get(0),"in",frequency));

                        } else if (sourceWent == 0) {

//                     ********
                            if (savedDownRequest != null) {
                                if (savedDownRequest.move() - (elevatorReal.getCurrentTime() - savedDownRequest.getRequestTime()) >= firstSourceRemoved.getRequestTime()) {

                                    if (downCount == tempdown.size()) {

                                        if (savedDownDestination == source.get(0)) {  //modified
//                                                System.out.println(savedDownRequest.getSourceFloor());
//                                                 System.out.println("hereeeeeeeeeeeeeeeeeeeeeeeeeee");
                                            elevatorReal.goToHalfClose(savedDownDestination, frequency, arr);
                                            transitionRequest.add(new transitionRequest(savedDownDestination,"halfclose",frequency));
                                            for (int i = 0; i < goToOpenFrequency.get(trackgoto); i++) {
                                                requestProcess.add(elevatorReal.getCurrentTime());
                                            }
                                            trackgoto += 1;
                                        } else {
                                            elevatorReal.goTo(source.get(0), true, frequency, "up", arr);
                                            transitionRequest.add(new transitionRequest(source.get(0),"in",frequency));
                                        }
                                    }
                                    if (downCount < tempdown.size()) {
//                                    System.out.println("yesfffff");
                                        boolean smallestT = false;
                                        for (int gg = downCount; gg < tempdown.size(); gg++) {
                                            if (tempdown.get(gg).getRequestTime() < firstSourceRemoved.getRequestTime()) {
                                                smallestT = true;
                                            }
                                        }
                                        if (smallestT == true) {
                                            elevatorReal.goTo(source.get(0), true, frequency, "up", arr);
                                            transitionRequest.add(new transitionRequest(source.get(0),"in",frequency));
                                        } else {
                                            if (savedDownDestination == firstSourceRemoved.getSourceFloor()) {  //modified
                                                elevatorReal.goToHalfClose(savedDownDestination, frequency, arr);
                                                transitionRequest.add(new transitionRequest(savedDownDestination,"halfclose",frequency));
                                                for (int i = 0; i < goToOpenFrequency.get(trackgoto); i++) {
                                                    requestProcess.add(elevatorReal.getCurrentTime());
                                                }
                                                trackgoto += 1;
                                            } else {
                                                elevatorReal.goTo(source.get(0), true, frequency, "up", arr);
                                                transitionRequest.add(new transitionRequest(source.get(0),"in",frequency));
                                            }
                                        }
                                    }
                                } else {
                                    elevatorReal.goTo(source.get(0), true, frequency, "up", arr);
                                    transitionRequest.add(new transitionRequest(source.get(0),"in",frequency));
                                }
                            } else {
                                elevatorReal.goTo(source.get(0), true, frequency, "up", arr);
                                transitionRequest.add(new transitionRequest(source.get(0),"in",frequency));
                            }//end savedDownRequest==null

                        }

                        for (int j = 0; j < frequency; j++) {
                            sourceWent++;
                        }
                        System.out.println("source went = " + sourceWent);
                        //make multiple source into only one source
                        ArrayList<Integer> newsource = new ArrayList<>();
                        if (frequency > 1) {
                            for (int j = 0; j < source.size(); j++) {
                                if (j == 0) {
                                    newsource.add(source.get(0));
                                } else if (!source.get(0).equals(source.get(j))) {
                                    newsource.add(source.get(j));
                                }
                            }

                            source = newsource;

                        }//end if

                        //sort new .get(0)
                        if (source.size() > 1) {//if only one then no need sort
                            for (int j = 0; j < source.size() - 1; j++) {
                                int temp = source.get(j);
                                source.set(j, source.get(j + 1));
                                source.set(j + 1, temp);
                            }
                        }

                    } //else if
                    else if (destinationFirstValue < sourceFirstValue) {
                        //1.find frequency of destination 
                        frequency = 0; //reinitialized frequency
                        for (int j = 0; j < destination.size(); j++) {
                            if (destination.get(0).equals(destination.get(j))) {
                                frequency++;// at least 1
                            }
                        }
                        //go to destination
                        for (int j = 0; j < frequency; j++) {
                            destinationWent += 1;
                        }
                        if (destinationWent < upremoveCount) {

                            elevatorReal.goTo(destination.get(0), false, frequency, "up", arr);
                            transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                            for (int i = 0; i < frequency; i++) {
                                requestProcess.add(elevatorReal.getCurrentTime());
                            }
//                     }
                        } else if (destinationWent == upremoveCount) {
                            if (savedRequest != null) {
                                if (down.size() != 0) {

                                    if (savedRequest.move() - (elevatorReal.getCurrentTime() - savedRequest.getRequestTime()) >= down.get(0).getRequestTime()) {

                                        if (upCount == tempup.size()) {
                                            if (savedDestination == down.get(0).getSourceFloor()) { // modified
                                                elevatorReal.goToHalfOpen(savedDestination, frequency, arr);
                                                 transitionRequest.add(new transitionRequest(savedDestination,"halfopen",frequency));
                                                goToOpenFrequency.add(frequency);
                                            } else { //added else
                                                elevatorReal.goTo(destination.get(0), false, frequency, "up", arr);
                                                transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                                                for (int i = 0; i < frequency; i++) {
                                                    requestProcess.add(elevatorReal.getCurrentTime());
                                                }
                                            }
                                        }
                                        if (upCount < tempup.size()) {//////

                                            boolean smallestT = false;
                                            for (int gg = upCount; gg < tempup.size(); gg++) {
                                                if (tempup.get(gg).getRequestTime() < down.get(0).getRequestTime()) {
                                                    smallestT = true;
                                                }
                                            }
                                            if (smallestT == true) {
                                                elevatorReal.goTo(destination.get(0), false, frequency, "up", arr);
                                                transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                                                for (int i = 0; i < frequency; i++) {
                                                    requestProcess.add(elevatorReal.getCurrentTime());
                                                }
                                            } else {
                                                if (savedDestination == down.get(0).getSourceFloor()) {  //modified
                                                    elevatorReal.goToHalfOpen(savedDestination, frequency, arr);
                                                    transitionRequest.add(new transitionRequest(savedDestination,"halfopen",frequency));
                                                    goToOpenFrequency.add(frequency);
                                                } else {
                                                    elevatorReal.goTo(destination.get(0), false, frequency, "up", arr);
                                                    transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                                                    for (int i = 0; i < frequency; i++) {
                                                        requestProcess.add(elevatorReal.getCurrentTime());
                                                    }
                                                }
                                            }
                                        }////////
                                    } else {
                                        elevatorReal.goTo(destination.get(0), false, frequency, "up", arr);
                                        transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                                        for (int i = 0; i < frequency; i++) {
                                            requestProcess.add(elevatorReal.getCurrentTime());
                                        }
                                    }
                                } else {
                                    elevatorReal.goTo(destination.get(0), false, frequency, "up", arr);
                                    transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                                    for (int i = 0; i < frequency; i++) {
                                        requestProcess.add(elevatorReal.getCurrentTime());
                                    }
                                }
                            }//null
                        } else {
                            elevatorReal.goTo(destination.get(0), false, frequency, "up", arr);
                            transitionRequest.add(new transitionRequest(destination.get(0),"out",frequency));
                            for (int i = 0; i < frequency; i++) {
                                requestProcess.add(elevatorReal.getCurrentTime());
                            }
                        }
                        //make multiple destination into only one destination
                        ArrayList<Integer> newdestination = new ArrayList<>();
                        if (frequency > 1) {
                            for (int j = 0; j < destination.size(); j++) {
                                if (j == 0) {
                                    newdestination.add(destination.get(0));
                                } else if (!destination.get(0).equals(destination.get(j))) {
                                    newdestination.add(destination.get(j));
                                }
                            }

                            destination = newdestination;
                        }//end if

                        //sort new .get(0) for destination
                        if (destination.size() > 1) {//if only one then no need sort
                            for (int j = 0; j < destination.size() - 1; j++) {
                                int temp = destination.get(j);
                                destination.set(j, destination.get(j + 1));
                                destination.set(j + 1, temp);
                            }
                        }
                    } else if (destinationFirstValue == sourceFirstValue) {


                        int sourcefrequency = 0;
                        int destinationfrequency = 0;//reinitialized frequency
                        for (int j = 0; j < source.size(); j++) {
                            if (source.get(0).equals(source.get(j))) {
                                sourcefrequency++;// at least 1
                            }
                        }
                        for (int j = 0; j < destination.size(); j++) {
                            if (destination.get(0).equals(destination.get(j))) {
                                destinationfrequency++;// at least 1
                            }
                        }
                        elevatorReal.goToHalfOpen(destinationFirstValue, destinationfrequency, arr);
                        transitionRequest.add(new transitionRequest(destinationFirstValue,"halfopen",destinationfrequency));
                        elevatorReal.goToHalfClose(destinationFirstValue, sourcefrequency, arr);
                        transitionRequest.add(new transitionRequest(destinationFirstValue,"halfclose",sourcefrequency));
                        for (int i = 0; i < destinationfrequency; i++) {
                            requestProcess.add(elevatorReal.getCurrentTime());
                        }

                        for (int j = 0; j < sourcefrequency; j++) {
                            sourceWent += 1;
                        }
                        for (int j = 0; j < destinationfrequency; j++) {
                            destinationWent += 1;
                        }
                        ArrayList<Integer> newdestination = new ArrayList<>();
                        if (destinationfrequency > 1) {
                            for (int j = 0; j < destination.size(); j++) {
                                if (j == 0) {
                                    newdestination.add(destination.get(0));
                                } else if (!destination.get(0).equals(destination.get(j))) {
                                    newdestination.add(destination.get(j));
                                }
                            }

                            destination = newdestination;
                        }//end if

                        ArrayList<Integer> newsource = new ArrayList<>();
                        if (sourcefrequency > 1) {
                            for (int j = 0; j < source.size(); j++) {
                                if (j == 0) {
                                    newsource.add(source.get(0));
                                } else if (!source.get(0).equals(source.get(j))) {
                                    newsource.add(source.get(j));
                                }
                            }

                            source = source;
                        }//end if
                        //sort new .get(0) for destination
                        if (source.size() > 1) {//if only one then no need sort
                            for (int j = 0; j < source.size() - 1; j++) {
                                int temp = source.get(j);
                                source.set(j, source.get(j + 1));
                                source.set(j + 1, temp);
                            }
                        }
                        //sort new .get(0) for destination
                        if (destination.size() > 1) {//if only one then no need sort
                            for (int j = 0; j < destination.size() - 1; j++) {
                                int temp = destination.get(j);
                                destination.set(j, destination.get(j + 1));
                                destination.set(j + 1, temp);
                            }
                        }

                    }

                }

            }//end big while loop
        }//end if goup==true

    }//end super big while loop

}//end run list method

