package logico;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelHoverEffect {
    private static final int ANIMATION_DURATION = 200; // milliseconds
    private static final int ANIMATION_STEPS = 20;
    private static final Color NORMAL_COLOR = new Color(90, 162, 232);
    private static final Color HOVER_COLOR = new Color(220, 231, 242);

    public static void addHoverEffectToPanel(JPanel panel) {
        panel.setBackground(NORMAL_COLOR);
        
        Timer fadeInTimer = new Timer(ANIMATION_DURATION / ANIMATION_STEPS, null);
        Timer fadeOutTimer = new Timer(ANIMATION_DURATION / ANIMATION_STEPS, null);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (fadeOutTimer.isRunning()) {
                    fadeOutTimer.stop();
                }
                startFadeAnimation(panel, NORMAL_COLOR, HOVER_COLOR, fadeInTimer);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (fadeInTimer.isRunning()) {
                    fadeInTimer.stop();
                }
                if ( !isMouseOverPanel(panel, e.getPoint()) ) {
                	startFadeAnimation(panel, HOVER_COLOR, NORMAL_COLOR, fadeOutTimer);
                }
                
            }
        });
    }

    private static void startFadeAnimation(JPanel panel, Color fromColor, Color toColor, Timer timer) {
        final int[] step = {0};
        
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (step[0] < ANIMATION_STEPS) {
                    float ratio = (float) step[0] / ANIMATION_STEPS;
                    Color newColor = interpolateColor(fromColor, toColor, ratio);
                    panel.setBackground(newColor);
                    panel.repaint();
                    step[0]++;
                } else {
                    panel.setBackground(toColor);
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        
        timer.start();
    }
    
    private static boolean isMouseOverPanel(JPanel panel, Point mousePoint) {
		boolean inBounds = false;
	    for (Component comp : panel.getComponents()) {
	        if (comp.getBounds().contains(mousePoint)) {
	        	inBounds = true;
	        }
	    }
	    return inBounds;
	}

    private static Color interpolateColor(Color c1, Color c2, float ratio) {
        int red = (int) (c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
        int green = (int) (c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
        int blue = (int) (c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
        return new Color(red, green, blue);
    }
    
}