package logico;
import javax.swing.*;
import java.awt.*;

public class MoveToXY {
    
    private JComponent component;
    private Timer timer;
    private int startX, startY;
    private int targetX, targetY;
    private long startTime;
    private float durationSeconds;
    private AnimationType animationType;
    
    public MoveToXY(JComponent component, int targetX, int targetY, float durationSeconds, AnimationType animationType) {
        this.component = component;
        this.targetX = targetX;
        this.targetY = targetY;
        this.durationSeconds = durationSeconds;
        this.animationType = animationType;
        
        timer = new Timer(16, e -> moveStep()); // ~60 FPS
    }
    
    private void moveStep() {
        long currentTime = System.currentTimeMillis();
        float progress = (float)(currentTime - startTime) / (durationSeconds * 1000f);
        
        if (progress >= 1) {
            component.setLocation(targetX, targetY);
            timer.stop();
            return;
        }
        
        float easeProgress = getEaseProgress(progress);
        
        int newX = Math.round(startX + (targetX - startX) * easeProgress);
        int newY = Math.round(startY + (targetY - startY) * easeProgress);
        
        component.setLocation(newX, newY);
    }
    
    private float getEaseProgress(float t) {
        switch (animationType) {
            case LINEAR:
                return t;
            case EASE_IN:
                return t * t * t;
            case EASE_OUT:
                return 1 - (1 - t) * (1 - t) * (1 - t);
            case EASE_IN_OUT:
                return t < 0.5 ? 4 * t * t * t : 1 - (float)Math.pow(-2 * t + 2, 3) / 2;
            default:
                return t;
        }
    }
    
    public void start() {
        startTime = System.currentTimeMillis();
        startX = component.getX();
        startY = component.getY();
        timer.start();
    }
}