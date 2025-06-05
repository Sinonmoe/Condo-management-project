package ktpm.condo.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Lớp cơ sở cho các panel trong giao diện, cung cấp các phương thức tiện ích tạo
 * component chuẩn và đồng bộ phong cách.
 */
public class BasePanel extends JPanel {

    // ✅ Style constants
    protected final Color PRIMARY_COLOR = new Color(30, 144, 255); // Dodger Blue
    protected final Color TITLE_COLOR = new Color(44, 62, 80);     // Midnight Blue
    protected final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    protected final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 16);

    /**
     * Tạo JLabel với font thường.
     */
    protected JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_REGULAR);
        return label;
    }

    /**
     * Tạo JLabel tiêu đề nổi bật.
     */
    protected JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(TITLE_COLOR);
        return label;
    }

    /**
     * Tạo JButton với màu nền, font đậm và màu chữ trắng.
     */
    protected JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_BOLD);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Tạo JTextField với font chuẩn và số cột.
     */
    protected JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(FONT_REGULAR);
        return textField;
    }

    /**
     * Tạo JTable với font chuẩn cho bảng và header.
     */
    protected JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(FONT_REGULAR);
        table.setRowHeight(22);
        table.getTableHeader().setFont(FONT_BOLD);
        return table;
    }
}
