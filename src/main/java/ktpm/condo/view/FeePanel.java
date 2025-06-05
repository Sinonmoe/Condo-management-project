package ktpm.condo.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.controller.FeeController;
import ktpm.condo.model.entity.Fee;

/**
 * Giao diện quản lý phí, chia thành 2 bảng:
 * - Phí chưa thanh toán: chỉ có thể chuyển trạng thái
 * - Phí đã thanh toán: có thể xoá
 */
public class FeePanel extends BasePanel {
    private final FeeController controller = new FeeController();

    private final JTable tableUnpaid;
    private final JTable tablePaid;
    private final DefaultTableModel modelUnpaid;
    private final DefaultTableModel modelPaid;

    private final JTextField tfFilterId = createTextField(5);
    private final JTextField tfFilterType = createTextField(10);

    private final JFrame parentFrame;

    public FeePanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        // Khu vực lọc
        JPanel filterPanel = new JPanel();
        filterPanel.add(createLabel("ID hộ khẩu:"));
        filterPanel.add(tfFilterId);
        filterPanel.add(createLabel("Loại phí:"));
        filterPanel.add(tfFilterType);
        JButton btnFilter = createButton("Lọc");
        filterPanel.add(btnFilter);
        add(filterPanel, BorderLayout.NORTH);

        // Hai bảng
        JPanel center = new JPanel(new GridLayout(2, 1));

        modelUnpaid = new DefaultTableModel(new Object[]{"ID", "Hộ", "Loại", "Số tiền", "Hạn nộp"}, 0);
        modelPaid = new DefaultTableModel(new Object[]{"ID", "Hộ", "Loại", "Số tiền", "Hạn nộp"}, 0);

        tableUnpaid = createTable(modelUnpaid);
        tablePaid = createTable(modelPaid);

        center.add(new JScrollPane(tableUnpaid));
        center.add(new JScrollPane(tablePaid));

        add(center, BorderLayout.CENTER);

        // Nút chức năng
        JPanel btnPanel = new JPanel();
        JButton btnMarkPaid = createButton("Đánh dấu đã thanh toán");
        JButton btnDelete = createButton("Xoá phí đã thanh toán");
        JButton btnBack = createButton("Quay lại");

        btnPanel.add(btnMarkPaid);
        btnPanel.add(btnDelete);
        btnPanel.add(btnBack);

        add(btnPanel, BorderLayout.SOUTH);

        // Sự kiện
        btnFilter.addActionListener(e -> applyFilter());
        btnMarkPaid.addActionListener(e -> markAsPaid());
        btnDelete.addActionListener(e -> deletePaid());
        btnBack.addActionListener(e -> goBack());

        loadData();
    }

    private void applyFilter() {
        Integer id = null;
        String type = null;

        try {
            if (!tfFilterId.getText().trim().isEmpty())
                id = Integer.parseInt(tfFilterId.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID hộ khẩu phải là số nguyên.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!tfFilterType.getText().trim().isEmpty())
            type = tfFilterType.getText().trim();

        List<Fee> unpaid = controller.filterFees(id, type, "Chưa thanh toán");
        List<Fee> paid = controller.filterFees(id, type, "Đã thanh toán");
        fillTable(modelUnpaid, unpaid);
        fillTable(modelPaid, paid);
    }

    private void loadData() {
        fillTable(modelUnpaid, controller.getFeesByStatus("Chưa thanh toán"));
        fillTable(modelPaid, controller.getFeesByStatus("Đã thanh toán"));
    }

    private void fillTable(DefaultTableModel model, List<Fee> list) {
        model.setRowCount(0);
        for (Fee f : list) {
            model.addRow(new Object[]{f.getId(), f.getHouseholdId(), f.getType(), f.getAmount(), f.getDueDate()});
        }
    }

    private void markAsPaid() {
        int selected = tableUnpaid.getSelectedRow();
        if (selected != -1) {
            int id = (int) modelUnpaid.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận đánh dấu đã thanh toán?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.updateFeeStatus(id, "Đã thanh toán")) {
                    JOptionPane.showMessageDialog(this, "Đã cập nhật.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Thao tác thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deletePaid() {
        int selected = tablePaid.getSelectedRow();
        if (selected != -1) {
            int id = (int) modelPaid.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xoá phí này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.deleteFee(id)) {
                    JOptionPane.showMessageDialog(this, "Đã xoá.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Xoá thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
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
