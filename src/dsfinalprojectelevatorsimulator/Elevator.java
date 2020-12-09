/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsfinalprojectelevatorsimulator;
import java.util.ArrayList;
public class Elevator {
        private static int currentFloor;
        private int currentTime;
        private String direction;
        private boolean idle;
        private int passengers;

   
    public Elevator() {
        currentFloor=0;
        currentTime=0;
        direction="up";
        passengers=0;
    }
    
    public void stopped(){
        idle=true;
    }

    public void moving(){
        idle=false;
    }
    
    public void timer(ArrayList<Request>arr){
        int[]timeArr=new int[arr.size()]; //array to save request time
        for(int i=0;i<arr.size();i++){
            timeArr[i]=arr.get(i).getRequestTime();
        }
        for(int i=0;i<timeArr.length;i++){
            if(currentTime==timeArr[i]){
                RunElevator.printLog(currentTime+" : A request of id("+arr.get(i).getRequestID()+") is received at request time: "+arr.get(i).getRequestTime());
                System.out.println(currentTime+" : A request of id("+arr.get(i).getRequestID()+") is received at request time: "+arr.get(i).getRequestTime());
                 RunElevator.pdflog(currentTime+" : A request of id("+arr.get(i).getRequestID()+") is received at request time: "+arr.get(i).getRequestTime());
            }
        }
    }
    public void goToHalfOpen(int floor,int frequency,ArrayList<Request>arr){
        timer(arr);
        RunElevator.printLog(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
        System.out.println(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
        RunElevator.pdflog(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
                while(currentFloor<floor){
                    goUp(arr);
                }
                while(currentFloor>floor){
                    goDown(arr);
                }
                //reached
                RunElevator.printLog(currentTime+": Reached floor "+ floor);
                RunElevator.pdflog(currentTime+": Reached floor "+ floor);
                System.out.println(currentTime+": Reached floor "+ floor);
                openDoor(arr);
                pplLeave(frequency,arr);
                //no close door and time need to -=4
    }
    public void goToHalfClose(int floor,int frequency,ArrayList<Request>arr){ //modify this to now show multiple requests

                currentTime-=4;
                specialpplEnter(frequency);
                
                closeDoor(arr);
            
    }

    public void goTo(int floor,boolean pickUp,int frequency,String upOrDown,ArrayList<Request>arr){
        if(pickUp==true){
            if(upOrDown.equals("up")){
                timer(arr);
                RunElevator.printLog(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
                RunElevator.pdflog(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
                System.out.println(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
                
                while(currentFloor<floor){
                    goUp(arr);
                }
                while(currentFloor>floor){
                    goDown(arr);
                }
                //reached
                RunElevator.printLog(currentTime+": Reached floor "+ floor);
                RunElevator.pdflog(currentTime+": Reached floor "+ floor);
                System.out.println(currentTime+": Reached floor "+ floor);
                openDoor(arr);
             
                     pplEnter(frequency,arr);
              
                closeDoor(arr);
            }
            else if(upOrDown.equals("down")){
                timer(arr);
                RunElevator.printLog(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
                RunElevator.pdflog(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
                System.out.println(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
                while(currentFloor<floor){
                    goUp(arr);
                }
                while(currentFloor>floor){
                    goDown(arr);
                }
                //reached
                RunElevator.printLog(currentTime+": Reached floor "+ floor);
                RunElevator.pdflog(currentTime+": Reached floor "+ floor);
                System.out.println(currentTime+": Reached floor "+ floor);
                openDoor(arr);
             
                     pplEnter(frequency,arr);
              
                closeDoor(arr);
            }
        }
        else{
           if(upOrDown.equals("up")){
               timer(arr);
               RunElevator.printLog(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
               RunElevator.pdflog(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
               System.out.println(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
                while(currentFloor<floor){
                    goUp(arr);
                }
                while(currentFloor>floor){
                    goDown(arr);
                }
                //reached
                RunElevator.printLog(currentTime+": Reached floor "+ floor);
                RunElevator.pdflog(currentTime+": Reached floor "+ floor);
                System.out.println(currentTime+": Reached floor "+ floor);
                openDoor(arr);
                
                     pplLeave(frequency,arr);
                
                closeDoor(arr);
            }
           else if(upOrDown.equals("down")){
               timer(arr);
               RunElevator.printLog(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
               RunElevator.pdflog(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
               System.out.println(currentTime+": Going to floor "+floor+" from floor "+currentFloor);
               while(currentFloor<floor){
                    goUp(arr);
                }
                while(currentFloor>floor){
                    goDown(arr);
                }
                //reached
                RunElevator.printLog(currentTime+": Reached floor "+ floor);
                 RunElevator.pdflog(currentTime+": Reached floor "+ floor);
                System.out.println(currentTime+": Reached floor "+ floor);
                openDoor(arr);
                
                     pplLeave(frequency,arr);
                
                closeDoor(arr);
            }
        }
    }
    
    public void openDoor(ArrayList<Request>arr){
         RunElevator.printLog(currentTime+":Door opening");
        System.out.println(currentTime+":Door opening");
        RunElevator.pdflog(currentTime+":Door opening");
        for(int i=0;i<5;i++){
            
            currentTime+=1;
            timer(arr);
        }
       RunElevator.printLog(currentTime+":Door opened");
       RunElevator.pdflog(currentTime+":Door opened");
        System.out.println(currentTime+":Door opened");
    }
    public void closeDoor(ArrayList<Request>arr){
         
        RunElevator.printLog(currentTime+":Door closing");
        RunElevator.pdflog(currentTime+":Door closing");
        System.out.println(currentTime+":Door closing");
         for(int i=0;i<5;i++){
            
            currentTime+=1;
            timer(arr);
        }
         RunElevator.printLog(currentTime+":Door closed");
         RunElevator.pdflog(currentTime+":Door closed");
        System.out.println(currentTime+":Door closed");
    }
    public void specialpplEnter(int frequency){ // need to add frequency as parameter
        
        for(int i=0;i<4;i++){
            
            currentTime+=1;
           
        }
        for(int i=0;i<frequency;i++){
        passengers+=1;
        }
        RunElevator.printLog(currentTime+": "+frequency+" passenger(s) entered the elevator");
        RunElevator.printLog("There are currently " + passengers+" passengers in elevator");
        RunElevator.pdflog(currentTime+": "+frequency+" passenger(s) entered the elevator");
        RunElevator.pdflog("There are currently " + passengers+" passengers in elevator");
        System.out.println(currentTime+": "+frequency+" passenger(s) entered the elevator");
        System.out.println("There are currently " + passengers+" passengers in elevator");
    }
    public void pplEnter(int frequency,ArrayList<Request>arr){ // need to add frequency as parameter
        
        for(int i=0;i<4;i++){
            
            currentTime+=1;
            timer(arr);
        }
        for(int i=0;i<frequency;i++){
        passengers+=1;
        }
        RunElevator.printLog(currentTime+": "+frequency+" passenger(s) entered the elevator");
        RunElevator.printLog("There are currently " + passengers+" passengers in elevator");
        RunElevator.pdflog(currentTime+": "+frequency+" passenger(s) entered the elevator");
        RunElevator.pdflog("There are currently " + passengers+" passengers in elevator");
        System.out.println(currentTime+": "+frequency+" passenger(s) entered the elevator");
        System.out.println("There are currently " + passengers+" passengers in elevator");
    }
    public void pplLeave(int frequency,ArrayList<Request>arr){
         
        for(int i=0;i<4;i++){
            
            currentTime+=1;
            timer(arr);
        }
        for(int i=0;i<frequency;i++){
        passengers-=1;
        }
        RunElevator.printLog(currentTime+": "+frequency+" passenger(s) left the elevator");
        RunElevator.printLog("There are currently " + passengers+" passengers in elevator");
        RunElevator.pdflog(currentTime+": "+frequency+" passenger(s) left the elevator");
        RunElevator.pdflog("There are currently " + passengers+" passengers in elevator");
        System.out.println(currentTime+": "+frequency+" passenger(s) left the elevator");
         System.out.println("There are currently " + passengers+" passengers in elevator");
    }
    public void goUp(ArrayList<Request>arr){
        
        currentFloor+=1;
        currentTime+=1;
        timer(arr);
    }
    
    public void goDown(ArrayList<Request>arr){
        
        currentFloor-=1;
        currentTime+=1;
        timer(arr);
    }
    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
        
    public void reset(){
        currentFloor=0;
        currentTime=0;
        direction="up";
        passengers=0;
        
        
    }
        
        
        
}

