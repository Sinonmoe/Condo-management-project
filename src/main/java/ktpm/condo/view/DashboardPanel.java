package ktpm.condo.view;

import java.awt.*;
import javax.swing.*;

import ktpm.condo.view.facility_panel.FacilityPanel;
import ktpm.condo.view.household_view.HouseholdPanel;

/**
 * Giao diện Dashboard chính của hệ thống quản lý chung cư.
 */
public class DashboardPanel extends BasePanel {

    public DashboardPanel(JFrame parentFrame) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20)); // padding quanh panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 🔷 Tiêu đề nổi bật
        JLabel title = createTitleLabel("HỆ THỐNG QUẢN LÝ CHUNG CƯ");
        gbc.gridy = 0;
        add(title, gbc);

        // 🔷 Các nút chức năng chính
        JButton btnHousehold = createButton(" Quản lý Hộ khẩu & Nhân khẩu");
        JButton btnFee = createButton(" Quản lý Phí & Thông báo");
        JButton btnFacility = createButton(" Quản lý Tiện ích");
        JButton btnReport = createButton(" Thống kê & Báo cáo");

        Dimension btnSize = new Dimension(280, 50);
        JButton[] buttons = {btnHousehold, btnFee, btnFacility, btnReport};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setPreferredSize(btnSize);
            gbc.gridy = i + 1;
            add(buttons[i], gbc);
        }

        // 🧭 Sự kiện chuyển panel tương ứng
        btnHousehold.addActionListener(e -> switchPanel(parentFrame, new HouseholdPanel(parentFrame), "Quản lý Hộ khẩu & Nhân khẩu"));
        btnFee.addActionListener(e -> switchPanel(parentFrame, new FeePanel(parentFrame), "Quản lý Phí & Thông báo"));
        btnFacility.addActionListener(e -> switchPanel(parentFrame, new FacilityPanel(parentFrame), "Quản lý Tiện ích"));
        btnReport.addActionListener(e -> switchPanel(parentFrame, new ReportPanel(parentFrame), "Thống kê & Báo cáo"));
    }

    private void switchPanel(JFrame frame, JPanel newPanel, String title) {
        frame.setTitle(title);
        frame.setContentPane(newPanel);
        frame.revalidate();
        frame.repaint();
    }
}
