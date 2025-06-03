package ktpm.condo;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ktpm.condo.view.DashboardPanel;

/**
 * Entry point chính của ứng dụng.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hệ thống quản lý Chung cư");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new DashboardPanel(frame));
            frame.setVisible(true);
        });
    }
}
