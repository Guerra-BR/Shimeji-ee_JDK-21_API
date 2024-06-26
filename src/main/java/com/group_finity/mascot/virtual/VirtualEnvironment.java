package com.group_finity.mascot.virtual;

import com.group_finity.mascot.VShimejiApplication;
import com.group_finity.mascot.environment.Area;
import com.group_finity.mascot.environment.Environment;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Virtual desktop environment.
 *
 * @author Kilkakon
 * @since 1.0.20
 */
class VirtualEnvironment extends Environment {
    private final JFrame display = new JFrame();

    private final Area activeIE = new Area();

    @Override
    public Area getWorkArea() {
        return getScreen();
    }

    @Override
    public Area getActiveIE() {
        return activeIE;
    }

    @Override
    public String getActiveIETitle() {
        return null;
    }

    @Override
    public long getActiveWindowId() {
        return 0;
    }

    @Override
    public void moveActiveIE(final Point point) {
    }

    @Override
    public void restoreIE() {
    }

    @Override
    public void refreshCache() {
        // I feel so refreshed

        // good for you buddy
    }

    @Override
    public void init() {
        display.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                VShimejiApplication.getInstance().exit();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        display.setTitle(VShimejiApplication.getInstance().getLanguageBundle().getString("ShimejiEE"));
        String[] windowArray = VShimejiApplication.getInstance().getProperties().getProperty("WindowSize", "600x500").split("x");
        display.getContentPane().setPreferredSize(new Dimension(Integer.parseInt(windowArray[0]), Integer.parseInt(windowArray[1])));
        display.getContentPane().setLayout(null);
        display.getContentPane().setBackground(Color.decode(VShimejiApplication.getInstance().getProperties().getProperty("Background", "#00FF00")));
        display.setBackground(Color.decode(VShimejiApplication.getInstance().getProperties().getProperty("Background", "#00FF00")));
        display.setAutoRequestFocus(false);

        BufferedImage image = null;
        try {
            image = ImageIO.read(VShimejiApplication.ICON_FILE.toFile());
        } catch (IOException e) {
            // not bothering reporting errors with loading the tray icon as it would have already been reported to the user by now
        } finally {
            if (image == null) {
                image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            }
        }
        display.setIconImage(image);

        SwingUtilities.invokeLater(() -> {
            display.pack();
            display.setVisible(true);
            display.toFront();
        });

        activeIE.set(new Rectangle(-500, -500, 0, 0));

        tick();
    }

    @Override
    public void tick() {
        getScreenRect().setBounds(display.getContentPane().getBounds());
        getScreen().set(getScreenRect());

        PointerInfo info = MouseInfo.getPointerInfo();
        Point point = new Point(0, 0);
        if (info != null) {
            point = info.getLocation();
            SwingUtilities.convertPointFromScreen(point, display.getContentPane());
        }
        getCursor().set(point);
    }

    @Override
    public void dispose() {
        display.dispose();
    }

    public void addShimeji(final VirtualTranslucentPanel shimeji) {
        SwingUtilities.invokeLater(() -> {
            if (display.getContentPane().getSize().width > 0 && display.getContentPane().getSize().height > 0) {
                display.setPreferredSize(display.getSize());
                display.getRootPane().setPreferredSize(display.getRootPane().getSize());
                display.getContentPane().setPreferredSize(display.getContentPane().getSize());
            }
            shimeji.setOpaque(false);
            display.getContentPane().add(shimeji);
        });
    }
}
