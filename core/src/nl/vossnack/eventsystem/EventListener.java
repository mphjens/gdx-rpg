/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.eventsystem;

/**
 *
 * @author Jens
 */
public abstract interface EventListener {
    abstract void onEventStart();
    abstract void onEventEnd();
    abstract void onEventUpdate(float deltatime);
}
