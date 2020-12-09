package dsfinalprojectelevatorsimulator;

//yann ds group: wah,wee,tze qian
public class Request extends RunElevator{
    
    public int currentTimeElevator =elevatorReal.getCurrentTime();
    public int currentFloorElevator = elevatorReal.getCurrentFloor();
    private int tempTime;
    
    private int requestID;
    private int requestTime;
    private int sourceFloor;
    private int destinationFloor;
    private String direction;


    public Request(int requestID, int requestTime, int sourceFloor, int destinationFloor) {
        this.requestID = requestID;
        this.requestTime = requestTime;
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        if(sourceFloor>destinationFloor){
            direction="down";
        }else{
            direction="up";
        }
    }

    public void elevatorgoUp(){
//        goUp();
    }
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    
   
    public int getFloor(int requestTime,Request source){
        int floor =elevatorReal.getCurrentFloor();
        int currentTime =elevatorReal.getCurrentTime();
//        int timerange = elevatorReal.getCurrentTime();
        if(requestTime<=currentTime){
            return -100;//it is the floor of source of .get(0)
        }
        if(floor!=sourceFloor){ //start from source to destination 
            while(floor>sourceFloor){
                floor-=1;
                currentTime+=1;
//                if(requestTime==currentTime){
//                    return floor;
//                }
            }
            while(floor<sourceFloor){
                floor+=1;
                currentTime+=1;
//                if(requestTime==currentTime){
//                    return floor;
//                }
            }
        }
        if(currentTime>=source.getRequestTime()){
            return -100;
        }
        //open door+close door+ppl enter since source is reached
        for(int i=0;i<14;i++){
            currentTime+=1;
            if(requestTime==currentTime){
                    return floor;
                }
        }
        
        if(floor!=destinationFloor){
            while(floor>destinationFloor){
                floor-=1;
                currentTime+=1;
                 if(requestTime==currentTime){
                    return floor;
                }
            }
            while(floor<destinationFloor){
                floor+=1;
                currentTime+=1;
                 if(requestTime==currentTime){
                    return floor;
                }
            }
        }
        for(int i=0;i<14;i++){
            currentTime+=1;
            if(requestTime==currentTime){
                    return floor;
                }
        }
        return -10000;// error if this is returned
    }
    public int getElevatorFloor(int requestTime){ //error in this method
       int current = elevatorReal.getCurrentFloor();
         int source = sourceFloor;
         int destination = destinationFloor;
         int requestTemp =tempTime;
         int z;
            if(current>source) {
                z=current-source;
            }
            else {
                z=source-current;
            }
//        System.out.println(requestTemp+ " : Heading to floor "+source);
        for(int i=z;z>0;z--){
            if(requestTemp!=(requestTime-currentTimeElevator)){
                 requestTemp+=1;
                 current+=1;
            }
            else{
                return current;
            }
           
        }
//        System.out.println(requestTemp+" : Reached floor "+source);
//         System.out.println(requestTemp+" opening door");
          if(requestTemp<requestTime){
                 requestTemp+=5;
            }
            else{
                return current;
            }
//         System.out.println(requestTemp+" Opened door");
        if(requestTemp<requestTime){
                 requestTemp+=4;
            }
            else{
                return current;
            }
//         System.out.println(requestTemp+" 1 passenger enter");
//         System.out.println(requestTemp+" closing door");
           if(requestTemp<requestTime){
                 requestTemp+=5;
            }
            else{
                return current;
            }
//         System.out.println(requestTemp+"Door closed");
         
         
        int x;
        if(destination>source) {
            x=destination-source;
        }
        else {
            x=source-destination;
        }
        
//        System.out.println(requestTemp+ " : Heading to floor "+destination);
        for(int i=x;x>0;x--){
            if(requestTemp!=requestTime){
                 requestTemp+=1;
                 current+=1;
            }
            else{
                return current;
            }
        }
//        System.out.println(requestTemp+" : Reached floor "+destination);
//         System.out.println(requestTemp+" opening door");
            if(requestTemp<requestTime){
                 requestTemp+=5;
            }
            else{
                return current;
            }
//         System.out.println(requestTemp+"Opened door");
          if(requestTemp<requestTime){
                 requestTemp+=4;
            }
            else{
                return current;
            }
//         System.out.println(requestTemp+"1 passenger left");
//         System.out.println(requestTemp+" closing door");
           if(requestTemp<requestTime){
                 requestTemp+=5;
            }
            else{
                return current;
            }
           return destination;
//         System.out.println(requestTemp+"Door closed");
    }

     public int move(){ // this method need to access elevator time 
         
//         System.out.println("****this is demo*****");
         int current = elevatorReal.getCurrentFloor();
         int source = sourceFloor;
         int destination = destinationFloor;
         int requestTemp =elevatorReal.getCurrentTime();//this is changed to real current 
         int z;
            if(current>source) {
                z=current-source;
            }
            else {
                z=source-current;
            }
//        System.out.println(requestTemp+ " : Heading to floor "+source);
        for(int i=z;i>0;i--){ //maybe this no need add time
            requestTemp+=1;
        }
//        System.out.println(requestTemp+" : Reached floor "+source);
//         System.out.println(requestTemp+" opening door");
          requestTemp+=5;
//         System.out.println(requestTemp+" Opened door");
        requestTemp+=4;
//         System.out.println(requestTemp+" 1 passenger enter");
//         System.out.println(requestTemp+" closing door");
         requestTemp+=5;
//         System.out.println(requestTemp+"Door closed");
         
         
        int x;
        if(destination>source) {
            x=destination-source;
        }
        else {
            x=source-destination;
        }
        
//        System.out.println(requestTemp+ " : Heading to floor "+destination);
        for(int i=x;i>0;i--){
            requestTemp+=1;
        }
//        System.out.println(requestTemp+" : Reached floor "+destination);
//         System.out.println(requestTemp+" opening door");
          requestTemp+=5;
//         System.out.println(requestTemp+"Opened door");
        requestTemp+=4;
//         System.out.println(requestTemp+"1 passenger left");
//         System.out.println(requestTemp+" closing door");
         requestTemp+=5;
//         System.out.println(requestTemp+"Door closed");
//         System.out.println("****this is demo****");
        return requestTemp;
    }
    
    public void openDoorDemo(){
        int requestTemp = this.currentTimeElevator;
        System.out.println(requestTemp+" : Door opening");
        requestTime+=5;
        System.out.println(requestTime+" : Door opened");
    }
    
    public void closeDoorDemo(){
        int requestTemp = this.currentTimeElevator;
        System.out.println(requestTemp+" : Door closing");
        requestTemp+=5;
        System.out.println(requestTemp+" : Door closed");
    } 
    
    public void pplEnterDemo(){
        int requestTemp = this.currentTimeElevator;
        requestTime+=4;
        System.out.println(requestTime+" : 1 passenger(s) entered the elevator");
    }
    
    public void pplLeaveDemo(){
        int requestTemp = this.currentTimeElevator;
        requestTime+=4;
        System.out.println(requestTime+" : 1 passenger(s) lelf the elevator");
    }
    
    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public void setSourceFloor(int sourceFloor) {
        this.sourceFloor = sourceFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }
    
}
