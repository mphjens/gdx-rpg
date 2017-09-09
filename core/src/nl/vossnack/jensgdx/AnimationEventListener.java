/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

/**
 *
 * @author Jens
 */
public interface AnimationEventListener {
    public void onAnimComplete(String animname);
    public void onAnimStart(String animname);
    public void onAnimLooped(String animname);
}
