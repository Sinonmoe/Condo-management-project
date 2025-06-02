package ktpm.condo;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ktpm.condo.view.HouseholdPanel;

/**
 * Lớp khởi động chính của ứng dụng Quản lý Chung cư.
 * Hiển thị giao diện quản lý hộ khẩu và nhân khẩu.
 */
public class Main {
    public static void main(String[] args) {
        // Thiết lập Look and Feel mặc định của hệ thống
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Không thể thiết lập LookAndFeel hệ thống.");
        }

        // Tạo cửa sổ chính của ứng dụng
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hệ thống quản lý Hộ khẩu & Nhân khẩu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null); // căn giữa màn hình

            frame.setContentPane(new HouseholdPanel());
            frame.setVisible(true);
        });
    }
}
