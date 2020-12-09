/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsfinalprojectelevatorsimulator;

/**
 *
 * @author devilng05108
 */
public class transitionRequest {
    protected  int sourceOrDestination;
    protected  String doorMovement; //in , out , halfopen, halfclose
    protected int frequency;
    public transitionRequest(int sourceOrDestination, String doorMovement,int frequency) {
        this.sourceOrDestination = sourceOrDestination;
        this.doorMovement = doorMovement;
        this.frequency=frequency;
    }

   
 
}
