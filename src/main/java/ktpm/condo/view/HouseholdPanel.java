package ktpm.condo.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.model.dao.HouseholdDAO;
import ktpm.condo.model.entity.Household;

/**
 * Giao diện Swing để quản lý danh sách hộ khẩu trong hệ thống.
 */
public class HouseholdPanel extends JPanel {
    private final HouseholdDAO dao = new HouseholdDAO();
    private JTable table;
    private DefaultTableModel tableModel;

    public HouseholdPanel() {
        setLayout(new BorderLayout());

        // Bảng danh sách hộ khẩu
        tableModel = new DefaultTableModel(new Object[]{"ID", "Căn hộ", "Mã hộ", "Số thành viên"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel nút chức năng
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm hộ");
        JButton btnDelete = new JButton("Xoá hộ");
        JButton btnRefresh = new JButton("Làm mới");
        JButton btnViewCitizens = new JButton("Xem nhân khẩu");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewCitizens);
        buttonPanel.add(btnRefresh);
        add(buttonPanel, BorderLayout.SOUTH);

        // Gán sự kiện
        btnAdd.addActionListener(e -> addHousehold());
        btnDelete.addActionListener(e -> deleteSelected());
        btnRefresh.addActionListener(e -> loadData());
        btnViewCitizens.addActionListener(e -> viewCitizens());

        // Load dữ liệu ban đầu
        loadData();
    }

    /**
     * Tải lại dữ liệu từ CSDL lên bảng.
     */
    private void loadData() {
        tableModel.setRowCount(0);
        List<Household> households = dao.getAllHouseholds();
        for (Household h : households) {
            tableModel.addRow(new Object[]{
                h.getId(),
                h.getApartmentNumber(),
                h.getHouseholdCode(),
                h.getNumberOfMembers()
            });
        }
    }

    /**
     * Mở form thêm hộ khẩu mới.
     */
    private void addHousehold() {
        JTextField tfApartment = new JTextField();
        JTextField tfCode = new JTextField();
        JTextField tfMembers = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Căn hộ:"));
        panel.add(tfApartment);
        panel.add(new JLabel("Mã hộ khẩu:"));
        panel.add(tfCode);
        panel.add(new JLabel("Số thành viên:"));
        panel.add(tfMembers);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm hộ khẩu", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Household h = new Household();
                h.setApartmentNumber(tfApartment.getText());
                h.setHouseholdCode(tfCode.getText());
                h.setNumberOfMembers(Integer.parseInt(tfMembers.getText()));
                if (dao.addHousehold(h)) {
                    JOptionPane.showMessageDialog(this, "Đã thêm thành công.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số thành viên phải là số nguyên.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Xoá hộ khẩu được chọn.
     */
    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xoá hộ khẩu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.deleteHousehold(id)) {
                    JOptionPane.showMessageDialog(this, "Đã xoá.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Xoá thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hộ khẩu để xoá.");
        }
    }

    /**
     * Hiển thị danh sách nhân khẩu của hộ khẩu đang chọn.
     */
    private void viewCitizens() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int householdId = (int) tableModel.getValueAt(selectedRow, 0);
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Nhân khẩu hộ " + householdId, true);
            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(null);
            dialog.setContentPane(new CitizenPanel(householdId));
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hộ khẩu để xem nhân khẩu.");
        }
    }
}
