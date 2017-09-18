/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.eventsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Jens
 */
public class EventManager {
    
    //List<Event> events;
    HashMap<Event, List<EventListener>> subscribers;
    
    public EventManager(){
        subscribers = new HashMap();
    }
    
    public void subscribe(Event event, EventListener listener){
        if(!subscribers.containsKey(event)){
            List<EventListener> newSubsList = new ArrayList<EventListener>();
            newSubsList.add(listener);
            subscribers.put(event, newSubsList);
        }
        else{
            subscribers.get(event).add(listener);
        }
        
    }
    
    public void update(float deltatime){
        
    }
}
