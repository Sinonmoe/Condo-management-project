package ktpm.condo.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Lớp cơ sở cho các panel trong giao diện, cung cấp các phương thức tiện ích tạo
 * component chuẩn và đồng bộ phong cách.
 */
public class BasePanel extends JPanel {

    /**
     * Tạo JLabel với font mặc định.
     *
     * @param text nội dung nhãn
     * @return JLabel đã thiết lập font
     */
    protected JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(getDefaultFont());
        return label;
    }

    /**
     * Tạo JButton với font và màu sắc mặc định.
     *
     * @param text nội dung nút
     * @return JButton đã thiết lập font và màu sắc
     */
    protected JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(getDefaultFont());
        button.setBackground(new Color(30, 144, 255));  // Màu xanh dương (Dodger Blue)
        button.setForeground(Color.WHITE);              // Màu chữ trắng
        button.setFocusPainted(false);                   // Bỏ viền focus mặc định để nhìn hiện đại hơn
        return button;
    }

    /**
     * Tạo JTextField với font mặc định.
     *
     * @param columns số cột hiển thị
     * @return JTextField đã thiết lập font
     */
    protected JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(getDefaultFont());
        return textField;
    }

    /**
     * Tạo JTable với font mặc định cho bảng và header.
     *
     * @param model model dữ liệu cho bảng
     * @return JTable đã thiết lập font
     */
    protected JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(getDefaultFont());
        table.getTableHeader().setFont(getDefaultFont());
        return table;
    }

    /**
     * Lấy font mặc định dùng chung cho giao diện.
     *
     * @return font mặc định
     */
    protected Font getDefaultFont() {
        return new Font("Segoe UI", Font.PLAIN, 14);
    }
}
