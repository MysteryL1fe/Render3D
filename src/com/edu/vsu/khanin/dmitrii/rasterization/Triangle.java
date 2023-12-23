/*
import java.awt.*;

public class Triangle {
    private final Point[] points = new Point[3];

    public Triangle (Point p1, Point p2, Point p3) {
        this.points[0] = p1;
        this.points[1] = p2;
        this.points[2] = p3;
    }

    public int getColor(int x, int y) {
        double l1 = (((points[1].getY() - points[2].getY()) * ((double) x - points[2].getX()))
                + ((points[2].getX() - points[1].getX()) * ((double) y - points[2].getY()))) /
                (((points[1].getY()) - points[2].getY()) * ((points[0].getX() - points[2].getX()))
                        + ((points[2].getX() - points[1].getX()) * (points[0].getY() - points[2].getY())));
        double l2 = (((points[2].getY() - points[0].getY()) * ((double) x - points[2].getX()))
                + ((points[0].getX() - points[2].getX()) * ((double) y - points[2].getY()))) /
                (((points[1].getY()) - points[2].getY()) * ((points[0].getX() - points[2].getX()))
                        + ((points[2].getX() - points[1].getX()) * (points[0].getY() - points[2].getY())));
        double l3 = 1 - l1 - l2;

        int pixelValue = Color.WHITE.getRGB();

        if (l1 > 0 && l1 < 1 && l2 > 0 && l2 < 1 && l3 > 0 && l3 < 1) { // Закраска треугольника
            int r = makeColor((int)(points[0].getColor().getRed() * l1), (int)(points[0].getColor().getGreen() * l1), (int)(points[0].getColor().getBlue() * l1)).getRGB();
            int g = makeColor((int)(points[1].getColor().getRed() * l2), (int)(points[1].getColor().getGreen() * l2), (int)(points[1].getColor().getBlue() * l2)).getRGB();
            int b = makeColor((int)(points[2].getColor().getRed() * l3), (int)(points[2].getColor().getGreen() * l3), (int)(points[2].getColor().getBlue() * l3)).getRGB();

            pixelValue = r + g + b;
        }

        return pixelValue;
    }

    private Color makeColor(int r, int g, int b) {
        return new Color(r, g, b);
    }

}
*/
