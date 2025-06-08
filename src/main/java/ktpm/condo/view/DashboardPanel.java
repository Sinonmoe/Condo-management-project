package ktpm.condo.view;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

import ktpm.condo.view.facility_panel.FacilityPanel;
import ktpm.condo.view.household_view.HouseholdPanel;

/**
 * Giao diện Dashboard chính của hệ thống quản lý chung cư.
 */
public class DashboardPanel extends BasePanel {

    public DashboardPanel(JFrame parentFrame) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // 🔷 Tiêu đề nổi bật
        JLabel title = createTitleLabel("HỆ THỐNG QUẢN LÝ CHUNG CƯ");
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 25, 10); // Tăng khoảng cách dưới title
        add(title, gbc);

        // Reset insets cho các button
        gbc.insets = new Insets(15, 10, 15, 10);

        // 🔷 Các nút chức năng chính với icon
        JButton btnHousehold = createEnhancedButton(" Quản lý Hộ khẩu & Nhân khẩu", new Color(52, 152, 219));
        JButton btnFee = createEnhancedButton(" Quản lý Phí & Thông báo", new Color(155, 89, 182));
        JButton btnFacility = createEnhancedButton(" Quản lý Tiện ích", new Color(46, 204, 113));
        JButton btnReport = createEnhancedButton(" Thống kê & Báo cáo", new Color(231, 76, 60));

        JButton[] buttons = {btnHousehold, btnFee, btnFacility, btnReport};
        for (int i = 0; i < buttons.length; i++) {
            gbc.gridy = i + 2;
            add(buttons[i], gbc);
        }

        // 🧭 Sự kiện chuyển panel tương ứng
        btnHousehold.addActionListener(e -> switchPanel(parentFrame, new HouseholdPanel(parentFrame), "Quản lý Hộ khẩu & Nhân khẩu"));
        btnFee.addActionListener(e -> switchPanel(parentFrame, new FeePanel(parentFrame), "Quản lý Phí & Thông báo"));
        btnFacility.addActionListener(e -> switchPanel(parentFrame, new FacilityPanel(parentFrame), "Quản lý Tiện ích"));
        btnReport.addActionListener(e -> switchPanel(parentFrame, new ReportPanel(parentFrame), "Thống kê & Báo cáo"));
    }



    /**
     * Tạo button với màu sắc và hiệu ứng đẹp hơn
     */
    private JButton createEnhancedButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradient background
                Color color1 = baseColor;
                Color color2 = baseColor.darker();
                
                if (getModel().isPressed()) {
                    color1 = baseColor.darker();
                    color2 = baseColor.darker().darker();
                } else if (getModel().isRollover()) {
                    color1 = baseColor.brighter();
                    color2 = baseColor;
                }
                
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                
                // Text
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), textX, textY);
            }
        };
        
        button.setPreferredSize(new Dimension(320, 55));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }

    /**
     * Override paintComponent để vẽ background gradient
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Background gradient từ trắng đến xám nhạt
        Color color1 = new Color(236, 240, 241);
        Color color2 = new Color(189, 195, 199);
        GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Vẽ các hình trang trí
        g2d.setColor(new Color(255, 255, 255, 30));
        for (int i = 0; i < 5; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            int size = (int) (Math.random() * 100 + 50);
            g2d.fillOval(x, y, size, size);
        }
    }

    private void switchPanel(JFrame frame, JPanel newPanel, String title) {
        frame.setTitle(title);
        frame.setContentPane(newPanel);
        frame.revalidate();
        frame.repaint();
    }
}