package logico;
import javax.swing.*;
import java.awt.*;

public class WindowResizer {
    private JDialog frame;
    private Timer timer;
    private int startWidth, startHeight;
    private int targetWidth, targetHeight;
    private long startTime;
    private float durationSeconds;
    private AnimationType animationType;
    
    public WindowResizer(JDialog frame, int targetWidth, int targetHeight, float durationSeconds, AnimationType animationType) {
        this.frame = frame;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        this.durationSeconds = durationSeconds;
        this.animationType = animationType;
        
        timer = new Timer(16, e -> resizeStep()); // ~60 FPS
    }
    
    private void resizeStep() {
        long currentTime = System.currentTimeMillis();
        float progress = (float)(currentTime - startTime) / (durationSeconds * 1000);
        
        if (progress >= 1.0f) {
            frame.setSize(targetWidth, targetHeight);
            timer.stop();
            return;
        }
        
        float easeProgress = getEaseProgress(progress);
        
        int newWidth = Math.round(startWidth + (targetWidth - startWidth) * easeProgress);
        int newHeight = Math.round(startHeight + (targetHeight - startHeight) * easeProgress);
        
        frame.setSize(newWidth, newHeight);
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
        startWidth = frame.getWidth();
        startHeight = frame.getHeight();
        timer.start();
    }
}