import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Graphics;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CodeXam extends JPanel { //The drawing is defined to be a JPanel, which comes from the package javax.swing. A JPanel is a component you can draw in.
/*
    *Simply put, we use the serialVersionUID attribute to remember versions of a Serializable class to verify that a loaded class and the serialized object are compatible.
    *The serialVersionUID attributes of different classes are independent. Therefore, it is not necessary for different classes to have unique values
    *A serializable class can declare its own serialVersionUID explicitly by declaring a field named serialVersionUID that must be static, final, and of type long
    *It is not necessary for two classes to have unique values. It means two different classes can have same serialVersionUID value*/

    @Serial
    private static final long serialVersionUID = - 43L; //components have a silly serial version UID. Just put one in.

    private final List<List<Point>> curves = new ArrayList<>();

    public CodeXam() {
        // Register event listeners on construction of the panel.
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                var newCurve = new ArrayList<Point>();
                newCurve.add(new Point(e.getX(), e.getY()));
                curves.add(newCurve);
            }
        });
        /*You can read the component’s current size at any time with the graphics object’s getWidth() and getHeight() methods.
        Often, but not always, you’re drawing will be relative to these values.*/

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                curves.get(curves.size() - 1).add(new Point(e.getX(), e.getY()));

                /**
                 * The panel saves the state of the drawing as a list of curves, where each curve is a list of points.
                 * Pressing the mouse button starts a new curve; dragging the mouse adds the current location of the mouse to the current curve.
                 * The entire drawing is rendered when needed, as usual, in paintComponent().
                 *Notice the call to repaint when dragging. This tells Java to redraw the panel as soon as it can.
                 */
                repaint(0, 0, getWidth(), getHeight()); //The upper-left corner of the component is at (0,0). x-coordinates extend to the right; y-coordinates extend to the bottom.
            }
        });
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (var curve: curves) {
            var previousPoint = curve.get(0);
            for (var point: curve) {
                g.drawLine(previousPoint.x, previousPoint.y, point.x, point.y);
                previousPoint = point;
            }
        }
    }

    /**
     * A little driver in case you want to sketch as a stand-alone application.
     * We stuck a main method in there only so we can easily see the panel.
     * In practice, we should make components just do their own thing,
     * then write a separate application class with a main method.
     */
    public static void main(String[] args) { //
        SwingUtilities.invokeLater(() -> {
            var frame = new JFrame("Simple Sketching Program");
            frame.getContentPane().add(new CodeXam(), BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setVisible(true);
        });
    }
}