/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysterydungeon.animation;

import java.awt.image.BufferedImage;
import mysterydungeon.DungeonComp;

/**
 * An interface for creating animations.
 * Subclasses can control nearly all aspects of an animation using these
 * methods. It's fun!
 * @author jlamanna
 */
public interface Animation
{
    /**
     * Changes the various parameters.
     * Implementing classes should use this method to modify parameters, such as
     * x or y values, or a frame counter.
     * @return True of this animation is done, false if not.
     */
    boolean animate();
    
    /**
     * Returns the image.
     * @return The image to be displayed this frame of animation.
     */
    BufferedImage getImage();
    
    /**
     * Get the X coordinate of the top-left corner of the image.
     * @return The X coordinate of the top-left corner of the image.
     */
    int getX();
    
    /**
     * Get the Y coordinate  of the top-left corner of the image.
     * @return The Y coordinate of the top-left corner of the image.
     */
    int getY();
    
    /**
     * Scales an image.
     * This method takes a base image, and scales it by some amount. The algorithm
     * uses is a basic one, with no optimization.
     * @param original The original image.
     * @param dx The amount to scale in X. The new width is equal to the old width, times this value.
     * @param dy The amount to scale in Y. The new height is equal to the old height, times this value.
     * @return The new image, scaled to the old one.
     */
    public static BufferedImage scale(BufferedImage original, double dx, double dy)
    {
        BufferedImage newImage = new BufferedImage((int)(original.getWidth()*dx), (int)(original.getHeight()*dy), BufferedImage.TYPE_4BYTE_ABGR);
        for(int xx = 0; xx < newImage.getWidth(); xx++)
        {
            for(int yy = 0; yy < newImage.getHeight(); yy++)
            {
                int pixel = original.getRGB((int)(xx/dx), (int)(yy/dy));
                newImage.setRGB(xx, yy, pixel);
            }
        }
        return newImage;
    }
    
    /**
     * Pause program execution by some amount of time.
     * @param millis The number of milliseconds to wait.
     */
    public static void delay(long millis)
    {
        long waitUntil = System.currentTimeMillis() + millis;
        while(System.currentTimeMillis() < waitUntil)
        {
            //Waiting...
        }
    }
    
    /**
     * Play a certain animation.
     * Animations that are played a fix amount of time should use this method
     * to animate. This method repeatedly calls the animate() method until it
     * returns true, in which case it terminates. The animation may or may not
     * be removed from the animation queue, depending on the boolean.
     * @param anim The animation to play.
     * @param delay The amount of time to wait between calls to animate().
     * @param remove True if the animation should be removed when done, false if not.
     */
    public static void animate(Animation anim, long delay, boolean remove)
    {
        DungeonComp component = DungeonComp.getInstance();
        component.addAnimation(anim);
        do
        {
            component.paintImmediately(anim.getX(), anim.getY(), anim.getImage().getWidth(), anim.getImage().getHeight());
            delay(delay);
        } while (!anim.animate());
        if(remove){component.removeAnimation(anim);}
    }
    
    /**
     * Play a certain animation.
     * Animations that should play immediately should use this method
     * to animate. This method repeatedly calls the animate() method until
     * it returns true, in which case it terminates. The animation
     * will then be removed from the queue.
     * @param anim The animation to play.
     * @param delay The amount of time to wait between calls to animate().
     */
    public static void animate(Animation anim, long delay)
    {
        animate(anim, delay, true);
    }
    
    /**
     * Play a certain animation.
     * Animations that should play immediately should use this method
     * to animate. This method repeatedly calls the animate() method until
     * it returns true, in which case it terminates. Repaint is called on
     * the entire screen, instead of just where the image is being painted.
     * @param anim The animation to play.
     * @param delay The amount of time to wait between calls to animate().
     */
    public static void animateScreen(Animation anim, long delay)
    {
        DungeonComp component = DungeonComp.getInstance();
        component.addAnimation(anim);
        do
        {
            component.paintImmediately(0, 0, component.getWidth(), component.getHeight());
            delay(delay);
        } while (!anim.animate());
        component.removeAnimation(anim);
    }
}
