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

import ktpm.condo.model.entity.Household;
import ktpm.condo.model.service.HouseholdService;

/**
 * Giao diện Swing để quản lý danh sách hộ khẩu trong hệ thống.
 */
public class HouseholdPanel extends JPanel {
    private final HouseholdService service = new HouseholdService();
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

        tableModel = new DefaultTableModel(new Object[]{"ID", "Căn hộ", "Mã hộ", "Số thành viên"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm hộ");
        JButton btnDelete = new JButton("Xoá hộ");
        JButton btnViewCitizens = new JButton("Xem nhân khẩu");
        JButton btnRefresh = new JButton("Làm mới");
        JButton btnBack = new JButton("Quay lại");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewCitizens);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addHousehold());
        btnDelete.addActionListener(e -> deleteSelected());
        btnViewCitizens.addActionListener(e -> viewCitizens());
        btnRefresh.addActionListener(e -> loadData());
        btnBack.addActionListener(e -> goBack());

        loadData();
    }

    private void goBack() {
        parentFrame.setTitle("Hệ thống quản lý Chung cư");
        parentFrame.setContentPane(new DashboardPanel(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }


    private void loadData() {
        tableModel.setRowCount(0);
        List<Household> households = service.getAll();
        for (Household h : households) {
            tableModel.addRow(new Object[]{
                h.getId(),
                h.getApartmentNumber(),
                h.getHouseholdCode(),
                h.getNumberOfMembers()
            });
        }
    }

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
                if (service.add(h)) {
                    JOptionPane.showMessageDialog(this, "Đã thêm thành công.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số thành viên phải là số.", "Lỗi nhập", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xoá hộ khẩu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (service.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Đã xoá.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xoá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hộ khẩu để xoá.");
        }
    }

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
