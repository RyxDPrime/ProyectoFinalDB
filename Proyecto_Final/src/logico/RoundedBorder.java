package logico;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

public class RoundedBorder extends AbstractBorder {
    private Color borderColor;
    private int thickness;
    private int radius;

    public RoundedBorder(Color borderColor, int thickness, int radius) {
        this.borderColor = borderColor;
        this.thickness = thickness;
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Crear la forma redondeada
        RoundRectangle2D roundedRect = new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius);
        
        // Crear el �rea del componente
        Area cornerArea = new Area(new Rectangle(0, 0, width, height));
        
        // Sustraer la forma redondeada
        cornerArea.subtract(new Area(roundedRect));

        // Hacer las esquinas transparentes
        //g2d.setComposite(AlphaComposite.Clear);
        g2d.setColor(c.getParent().getBackground());
        g2d.fill(cornerArea);
        g2d.setComposite(AlphaComposite.SrcOver);

        // Dibujar el borde
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(roundedRect);

        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = thickness;
        return insets;
    }
}