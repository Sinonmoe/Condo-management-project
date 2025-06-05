package ktpm.condo.view.facility_panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.controller.FacilityController;
import ktpm.condo.model.entity.FacilityBooking;
import ktpm.condo.view.BasePanel;
import ktpm.condo.view.DashboardPanel;

public class FacilityPanel extends BasePanel {
    private final FacilityController controller = new FacilityController();
    private final JFrame parentFrame;

    private final DefaultTableModel tableModel;
    private final JTable table;

    private final JTextField tfFilterFacilityName = createTextField(10);
    private final JTextField tfFilterHouseholdId = createTextField(5);
    private final JTextField tfFilterUsageDate = createTextField(10);

    public FacilityPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(createLabel("Tên tiện ích:"));
        filterPanel.add(tfFilterFacilityName);
        filterPanel.add(createLabel("ID hộ khẩu:"));
        filterPanel.add(tfFilterHouseholdId);
        filterPanel.add(createLabel("Ngày sử dụng (yyyy-MM-dd):"));
        filterPanel.add(tfFilterUsageDate);
        JButton btnFilter = createButton("Lọc");
        filterPanel.add(btnFilter);
        add(filterPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Tên tiện ích", "ID hộ khẩu", "Ngày sử dụng"}, 0);
        table = createTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = createButton("Thêm tiện ích");
        JButton btnDelete = createButton("Xoá tiện ích");
        JButton btnRefresh = createButton("Làm mới");
        JButton btnBack = createButton("Quay lại");

        btnPanel.add(btnAdd);
        btnPanel.add(btnDelete);
        btnPanel.add(btnRefresh);
        btnPanel.add(btnBack);
        add(btnPanel, BorderLayout.SOUTH);

        btnFilter.addActionListener(e -> applyFilter());
        btnAdd.addActionListener(e -> addBooking());
        btnDelete.addActionListener(e -> deleteBooking());
        btnRefresh.addActionListener(e -> loadData());
        btnBack.addActionListener(e -> goBack());

        loadData();
    }

    private void loadData() {
        List<FacilityBooking> list = controller.getAllBookings();
        fillTable(list);
    }

    private void applyFilter() {
        try {
            List<FacilityBooking> filtered = controller.filterBookings(
                tfFilterFacilityName.getText(),
                tfFilterHouseholdId.getText(),
                tfFilterUsageDate.getText()
            );
            fillTable(filtered);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi lọc dữ liệu", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void fillTable(List<FacilityBooking> list) {
        tableModel.setRowCount(0);
        for (FacilityBooking fb : list) {
            tableModel.addRow(new Object[]{
                fb.getId(),
                fb.getFacilityName(),
                fb.getHouseholdId(),
                fb.getUsageDate()
            });
        }
    }

    private void addBooking() {
        // Thay thế AddBookingDialog bằng AddBookingPanel
        AddBookingPanel panel = new AddBookingPanel(controller);
        int result = JOptionPane.showConfirmDialog(
            this, panel, "Thêm lượt đặt tiện ích", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (panel.saveBooking()) {
                JOptionPane.showMessageDialog(this, "Đã thêm lượt đặt tiện ích.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteBooking() {
        int selected = table.getSelectedRow();
        if (selected != -1) {
            int id = (int) tableModel.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xoá tiện ích này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.deleteBooking(id)) {
                    JOptionPane.showMessageDialog(this, "Đã xoá.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xoá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tiện ích để xoá.");
        }
    }

    private void goBack() {
        if (parentFrame != null) {
            parentFrame.setTitle("Hệ thống quản lý Chung cư");
            parentFrame.setContentPane(new DashboardPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }
}
