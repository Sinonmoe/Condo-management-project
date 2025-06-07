package ktpm.condo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.controller.FeeController;
import ktpm.condo.model.entity.Fee;

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
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // padding ngoài

        // Bộ lọc
        JPanel filterPanel = new JPanel();
        filterPanel.setBorder(BorderFactory.createTitledBorder("🔎 Bộ lọc tìm kiếm"));
        filterPanel.add(createLabel("ID hộ khẩu:"));
        filterPanel.add(tfFilterId);
        filterPanel.add(createLabel("Loại phí:"));
        filterPanel.add(tfFilterType);
        JButton btnFilter = createButton("Lọc");
        filterPanel.add(btnFilter);
        add(filterPanel, BorderLayout.NORTH);

        // Hai bảng với tiêu đề rõ ràng
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // Bảng khoản chưa đóng
        modelUnpaid = new DefaultTableModel(new Object[]{"ID", "Hộ", "Loại", "Số tiền", "Hạn nộp"}, 0);
        tableUnpaid = createTable(modelUnpaid);
        styleTable(tableUnpaid);
        JPanel panelUnpaid = createTablePanel("📌 Những khoản chưa đóng", tableUnpaid);

        // Bảng khoản đã đóng
        modelPaid = new DefaultTableModel(new Object[]{"ID", "Hộ", "Loại", "Số tiền", "Hạn nộp"}, 0);
        tablePaid = createTable(modelPaid);
        styleTable(tablePaid);
        JPanel panelPaid = createTablePanel("✅ Những khoản đã đóng", tablePaid);

        center.add(panelUnpaid);
        center.add(Box.createVerticalStrut(15)); // khoảng cách
        center.add(panelPaid);

        add(center, BorderLayout.CENTER);

        // Các nút thao tác và nút quay lại được chia thành 2 nhóm để căn chỉnh hợp lý
        JPanel btnPanel = new JPanel(new BorderLayout(10, 10));

        // Nhóm nút thao tác bên trái
        JPanel actionButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JButton btnAddUnpaid = createButton("Thêm phí chưa thanh toán");
        JButton btnMarkPaid = createButton("Đánh dấu đã thanh toán");
        JButton btnOverdue = createButton("📅 Phí quá hạn");
        actionButtons.add(btnOverdue);


        actionButtons.add(btnAddUnpaid);
        actionButtons.add(btnMarkPaid);


        // Nhóm nút quay lại bên phải
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnBack = createButton("Quay lại");
        backButtonPanel.add(btnBack);

        // Thêm hai nhóm vào panel chính
        btnPanel.add(actionButtons, BorderLayout.WEST);
        btnPanel.add(backButtonPanel, BorderLayout.EAST);

        add(btnPanel, BorderLayout.SOUTH);

        // Sự kiện
        btnFilter.addActionListener(e -> applyFilter());
        btnAddUnpaid.addActionListener(e -> addUnpaidFee());
        btnMarkPaid.addActionListener(e -> markAsPaid());
        btnOverdue.addActionListener(e -> {
            parentFrame.setContentPane(new OverdueFeePanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });


        btnBack.addActionListener(e -> goBack());

        loadData();
    }


    private JPanel createTablePanel(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(0x333333)
        ));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
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

    private void addUnpaidFee() {
        JTextField tfHouseholdId = new JTextField();
        JTextField tfType = new JTextField();
        JTextField tfAmount = new JTextField();
        JTextField tfDueDate = new JTextField("yyyy-MM-dd");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("ID hộ khẩu:"));
        panel.add(tfHouseholdId);
        panel.add(new JLabel("Loại phí:"));
        panel.add(tfType);
        panel.add(new JLabel("Số tiền:"));
        panel.add(tfAmount);
        panel.add(new JLabel("Hạn nộp (yyyy-MM-dd):"));
        panel.add(tfDueDate);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm phí chưa thanh toán", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int householdId = Integer.parseInt(tfHouseholdId.getText().trim());
                String type = tfType.getText().trim();
                double amount = Double.parseDouble(tfAmount.getText().trim());
                LocalDate dueDate = LocalDate.parse(tfDueDate.getText().trim());

                Fee newFee = new Fee();
                newFee.setHouseholdId(householdId);
                newFee.setType(type);
                newFee.setAmount(amount);
                newFee.setDueDate(dueDate);
                newFee.setStatus("Chưa thanh toán");

                if (controller.addFee(newFee)) {
                    JOptionPane.showMessageDialog(this, "Thêm phí thành công.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm phí thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            }
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



    private void goBack() {
        if (parentFrame != null) {
            parentFrame.setTitle("Hệ thống quản lý Chung cư");
            parentFrame.setContentPane(new DashboardPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }
}
