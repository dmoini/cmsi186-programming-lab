import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Town2D is a graphics "shell" for a car simulation. Feel free to read this code to learn what it does, but don't
 * feel bad if it's too hard to handle at this stage.
 */
public class Town2D {

    private static class CarSnapshot {
        public int id;
        public int street;
        public int avenue;
        public Car.Direction direction;
    }

    private static class Snapshot {
        public List<CarSnapshot> carSnapshots;
    }

    private static class Town extends JComponent {

        private boolean showTracks = true;
        private List<Snapshot> snapshots = new ArrayList<Snapshot>();

        private int streetHeight;
        private int avenueWidth;
        private int carWidth;
        private int carHeight;

        private static final int MAXIMUM_SNAPSHOT_SIZE = 1000;

        public Town(boolean showTracks) {
            super();
            this.showTracks = showTracks;
        }

        public void addSnapshot(Snapshot snapshot) {
            snapshots.add(snapshot);
            if (snapshots.size() > MAXIMUM_SNAPSHOT_SIZE) {
                snapshots.remove(0);
            }
            repaint();
        }

        private static final int MAXIMUM_STREET = 10;
        private static final int MAXIMUM_AVENUE = 10;
        private static final int MARGIN = 5;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g.create();

            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            int townHeight = getHeight() - MARGIN - MARGIN;
            int townWidth = getWidth() - MARGIN - MARGIN;

            int streetPartitionCount = MAXIMUM_STREET * 2 - 1;
            int avenuePartitionCount = MAXIMUM_AVENUE * 2 - 1;

            streetHeight = townHeight / streetPartitionCount;
            avenueWidth = townWidth / avenuePartitionCount;
            carWidth = Math.min(streetHeight, avenueWidth) - MARGIN;
            carHeight = carWidth / 2 - MARGIN;

            // Adjust the height and width to account for uneven division.
            townHeight = streetHeight * streetPartitionCount;
            townWidth = avenueWidth * avenuePartitionCount;

            g2d.setColor(Color.BLACK);
            for (int street = 0; street < MAXIMUM_STREET; street++) {
                g2d.fillRect(MARGIN, MARGIN + street * streetHeight * 2, townWidth, streetHeight);
                for (int avenue = 0; avenue < MAXIMUM_AVENUE; avenue++) {
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(MARGIN + avenue * avenueWidth * 2, MARGIN, avenueWidth, townHeight);
                }
            }

            g2d.setColor(Color.YELLOW);
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                    new float[] { MARGIN }, 0);
            g2d.setStroke(dashed);
            for (int street = 0; street < MAXIMUM_STREET; street++) {
                int streetDividerY = MARGIN + street * streetHeight * 2 + streetHeight / 2;
                g2d.drawLine(MARGIN + avenueWidth / 2, streetDividerY,
                        MARGIN + (avenuePartitionCount - 1) * avenueWidth + avenueWidth / 2, streetDividerY);
                for (int avenue = 0; avenue < MAXIMUM_AVENUE; avenue++) {
                    int avenueDividerX = MARGIN + avenue * avenueWidth * 2 + avenueWidth / 2;
                    g2d.drawLine(avenueDividerX, MARGIN + streetHeight / 2,
                            avenueDividerX, MARGIN + (streetPartitionCount - 1) * streetHeight + streetHeight / 2);
                }
            }
            g2d.dispose();

            g2d = (Graphics2D)g.create();
            if (showTracks) {
                g2d.setColor(Color.BLACK);
                g2d.setBackground(Color.GRAY);
                for (Snapshot snapshot: snapshots) {
                    paintSnapshot(g2d, snapshot);
                }
            }

            if (!snapshots.isEmpty()) {
                g2d.setColor(Color.RED);
                g2d.setBackground(Color.WHITE);
                paintSnapshot(g2d, snapshots.get(snapshots.size() - 1));
            }
            g2d.dispose();
        }

        private void paintSnapshot(Graphics2D g2d, Snapshot snapshot) {
            for (CarSnapshot carSnapshot: snapshot.carSnapshots) {
                paintCar(g2d, carSnapshot);
            }
        }

        private void paintCar(Graphics2D g2d, CarSnapshot carSnapshot) {
            AffineTransform saveTransform = g2d.getTransform();
            g2d.translate(MARGIN, MARGIN);

            FontMetrics fontMetrics = g2d.getFontMetrics(g2d.getFont());
            int carX = (carSnapshot.avenue - 1) * avenueWidth * 2 + avenueWidth / 2;
            int carY = (carSnapshot.street - 1) * streetHeight * 2 + streetHeight / 2;
            g2d.translate(carX, carY);

            switch (carSnapshot.direction) {
                case N: g2d.rotate(-Math.PI / 2.0); break;
                case S: g2d.rotate(Math.PI / 2.0); break;
                case W: g2d.rotate(Math.PI); break;
                case E: break; // No-op.
            }

            if (carSnapshot.direction == Car.Direction.N || carSnapshot.direction == Car.Direction.S) {
                g2d.translate(0, avenueWidth / 4);
            } else {
                g2d.translate(0, streetHeight / 4);
            }

            String id = Integer.toString(carSnapshot.id);
            if (carSnapshot.direction == Car.Direction.W) {
                id = "<" + id;
                g2d.rotate(Math.PI);
            } else {
                id = id + ">";
            }
            g2d.clearRect(-carWidth / 2, -carHeight / 2, carWidth, carHeight);
            g2d.drawString(id, -fontMetrics.stringWidth(id) / 2, fontMetrics.getAscent() / 2 - 1);
            g2d.setTransform(saveTransform);
        }
    
    }

    private static class Updater implements Runnable {
        
        private Town town;
        private Snapshot snapshot;
        
        public Updater(Town town, Snapshot snapshot) {
            this.town = town;
            this.snapshot = snapshot;
        }
        
        @Override
        public void run() {
            town.addSnapshot(snapshot);
        }
    }

    private static final long DEFAULT_INTERVAL = 1000;

    private static long getInterval(String arg) {
        try {
            return Long.parseLong(arg);
        } catch (NumberFormatException nfe) {
            System.out.println("Unrecognized interval argument; defaulting to " + DEFAULT_INTERVAL + ".");
            return DEFAULT_INTERVAL;
        }
    }

    public static void main(String[] args) {
        long interval = args.length == 0 ? DEFAULT_INTERVAL : getInterval(args[0]);

        JFrame townFrame = new JFrame("Town 2D");
        townFrame.setSize(800, 800);
        townFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Town town = new Town(args.length > 1);
        townFrame.setContentPane(town);
        townFrame.setVisible(true);
        Scanner scanner = null;
        try {
            scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
            Pattern carPattern = Pattern.compile("^car \\d+: ");
            List<CarSnapshot> currentCarSnapshotList = new ArrayList<CarSnapshot>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if ("".equals(line)) {
                    Snapshot snapshot = new Snapshot();
                    snapshot.carSnapshots = currentCarSnapshotList;
                    currentCarSnapshotList = new ArrayList<CarSnapshot>();
                    SwingUtilities.invokeLater(new Updater(town, snapshot));
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException ie) {
                        // Non-critical; ignore.
                    }
                } else if (carPattern.matcher(line).find()) {
                    String[] carData = line.split(" ");
                    CarSnapshot carSnapshot = new CarSnapshot();
                    carSnapshot.id = Integer.parseInt(carData[1].substring(0, carData[1].length() - 1));
                    carSnapshot.street = Integer.parseInt(carData[2]);
                    carSnapshot.avenue = Integer.parseInt(carData[3]);
                    carSnapshot.direction = Car.Direction.valueOf(carData[4]);
                    currentCarSnapshotList.add(carSnapshot);
                }

                System.out.println(line);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
