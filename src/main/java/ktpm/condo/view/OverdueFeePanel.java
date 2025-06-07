package ktpm.condo.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

import ktpm.condo.controller.FeeController;
import ktpm.condo.model.entity.Fee;

public class OverdueFeePanel extends BasePanel {
    private final FeeController controller = new FeeController();
    private final JTable table;
    private final DefaultTableModel tableModel;

    private final JFrame parentFrame;

    public OverdueFeePanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("📅 Danh sách phí quá hạn chưa thanh toán");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Hộ", "Loại", "Số tiền", "Hạn nộp", "Trạng thái"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(28);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnBack = new JButton("Quay lại");
        btnBack.addActionListener(e -> {
            parentFrame.setContentPane(new FeePanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        loadOverdueData();
    }

    private void loadOverdueData() {
        List<Fee> list = controller.getOverdueUnpaidFees(LocalDate.now());
        tableModel.setRowCount(0);
        for (Fee f : list) {
            tableModel.addRow(new Object[]{
                    f.getId(), f.getHouseholdId(), f.getType(), f.getAmount(), f.getDueDate(), f.getStatus()
            });
        }
    }
}
