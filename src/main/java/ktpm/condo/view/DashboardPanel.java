package ktpm.condo.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Giao diện Dashboard chính của hệ thống quản lý chung cư.
 * Cung cấp các nút điều hướng đến các chức năng chính:
 * <ul>
 *     <li>Quản lý Hộ khẩu & Nhân khẩu</li>
 *     <li>Quản lý Phí & Thông báo</li>
 *     <li>Quản lý Tiện ích</li>
 *     <li>Thống kê & Báo cáo</li>
 * </ul>
 */
public class DashboardPanel extends JPanel {

    /**
     * Khởi tạo Dashboard hiển thị 4 nút chức năng.
     *
     * @param parentFrame JFrame chính để thay đổi nội dung khi chọn module
     */
    public DashboardPanel(JFrame parentFrame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JButton btnHousehold = new JButton("Quản lý Hộ khẩu & Nhân khẩu");
        JButton btnFee = new JButton("Quản lý Phí & Thông báo");
        JButton btnFacility = new JButton("Quản lý Tiện ích");
        JButton btnReport = new JButton("Thống kê & Báo cáo");

        Dimension btnSize = new Dimension(250, 50);
        btnHousehold.setPreferredSize(btnSize);
        btnFee.setPreferredSize(btnSize);
        btnFacility.setPreferredSize(btnSize);
        btnReport.setPreferredSize(btnSize);

        gbc.gridy = 0; add(btnHousehold, gbc);
        gbc.gridy = 1; add(btnFee, gbc);
        gbc.gridy = 2; add(btnFacility, gbc);
        gbc.gridy = 3; add(btnReport, gbc);

        // Sự kiện chuyển panel tương ứng
        btnHousehold.addActionListener(e -> switchPanel(parentFrame, new HouseholdPanel(parentFrame), "Quản lý Hộ khẩu & Nhân khẩu"));
        btnFee.addActionListener(e -> switchPanel(parentFrame, new FeePanel(parentFrame), "Quản lý Phí & Thông báo"));
        btnFacility.addActionListener(e -> switchPanel(parentFrame, new FacilityPanel(parentFrame), "Quản lý Tiện ích"));
        btnReport.addActionListener(e -> switchPanel(parentFrame, new ReportPanel(parentFrame), "Thống kê & Báo cáo"));
    }

    /**
     * Thay đổi nội dung hiển thị trong JFrame cha.
     *
     * @param frame    JFrame cần thay đổi nội dung
     * @param newPanel Panel mới để hiển thị
     * @param title    Tiêu đề cửa sổ mới
     */
    private void switchPanel(JFrame frame, JPanel newPanel, String title) {
        frame.setTitle(title);
        frame.setContentPane(newPanel);
        frame.revalidate();
        frame.repaint();
    }
}
