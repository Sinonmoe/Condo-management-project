package ktpm.condo.view.household_view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.controller.household_controller.HouseholdController;
import ktpm.condo.model.entity.Household;
import ktpm.condo.view.BasePanel;
import ktpm.condo.view.DashboardPanel;

/**
 * Giao diện Swing để quản lý danh sách hộ khẩu trong hệ thống.
 */
public class HouseholdPanel extends BasePanel {
    private final HouseholdController controller = new HouseholdController();
    private JTable table;
    private DefaultTableModel tableModel;
    private final JFrame parentFrame;

    /**
     * Khởi tạo HouseholdPanel kèm tham chiếu JFrame chính.
     *
     * @param parentFrame cửa sổ chính để điều hướng
     */
    public HouseholdPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Căn hộ", "Mã hộ", "Số thành viên"}, 0);
        table = createTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnAdd = createButton("Thêm hộ");
        JButton btnDelete = createButton("Xoá hộ");
        JButton btnViewCitizens = createButton("Xem nhân khẩu");
        JButton btnBack = createButton("Quay lại");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewCitizens);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addHousehold());
        btnDelete.addActionListener(e -> deleteSelected());
        btnViewCitizens.addActionListener(e -> viewCitizens());
        btnBack.addActionListener(e -> goBack());

        loadData();
    }

    /**
     * Quay lại giao diện chính.
     */
    private void goBack() {
        parentFrame.setTitle("Hệ thống quản lý Chung cư");
        parentFrame.setContentPane(new DashboardPanel(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    /**
     * Tải dữ liệu hộ khẩu từ controller và hiển thị.
     */
    private void loadData() {
        tableModel.setRowCount(0);
        List<Household> households = controller.getAllHouseholds();
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
     * Hiển thị hộp thoại thêm hộ khẩu mới.
     */
    private void addHousehold() {
        JTextField tfApartment = createTextField(15);
        JTextField tfCode = createTextField(15);
        // Bỏ trường số thành viên, vì mặc định là 0, tự động cập nhật theo trigger

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(createLabel("Căn hộ:"));
        panel.add(tfApartment);
        panel.add(createLabel("Mã hộ khẩu:"));
        panel.add(tfCode);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm hộ khẩu", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (controller.addHousehold(tfApartment.getText(), tfCode.getText())) {
                JOptionPane.showMessageDialog(this, "Đã thêm thành công.");
                loadData();  // Tự động cập nhật bảng
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại hoặc dữ liệu không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Xoá hộ khẩu được chọn nếu xác nhận từ người dùng.
     */
    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xoá hộ khẩu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.deleteHousehold(id)) {
                    JOptionPane.showMessageDialog(this, "Đã xoá.");
                    loadData();  // Tự động cập nhật bảng
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xoá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hộ khẩu để xoá.");
        }
    }

    /**
     * Hiển thị danh sách nhân khẩu thuộc hộ khẩu được chọn.
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
