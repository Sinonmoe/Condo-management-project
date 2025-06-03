package ktpm.condo.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.controller.ReportController;

/**
 * Giao diện báo cáo thống kê với nhiều loại báo cáo.
 */
public class ReportPanel extends JPanel {
    private final ReportController controller = new ReportController();
    private final JFrame parentFrame;

    private JTable table;
    private DefaultTableModel model;

    public ReportPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initUI();
        showPopulationReport(); // Mặc định hiển thị báo cáo dân cư
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Panel chứa 3 nút báo cáo
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnPopulation = new JButton("Báo cáo dân cư");
        JButton btnFee = new JButton("Báo cáo thu phí");
        JButton btnFacility = new JButton("Báo cáo tiện ích");
        btnPanel.add(btnPopulation);
        btnPanel.add(btnFee);
        btnPanel.add(btnFacility);

        add(btnPanel, BorderLayout.NORTH);

        // Bảng hiển thị dữ liệu báo cáo
        model = new DefaultTableModel();
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Nút quay lại
        JButton btnBack = new JButton("Quay lại Dashboard");
        btnBack.addActionListener(e -> {
            parentFrame.setTitle("Hệ thống quản lý Chung cư");
            parentFrame.setContentPane(new DashboardPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // Sự kiện nút
        btnPopulation.addActionListener(e -> showPopulationReport());
        btnFee.addActionListener(e -> showFeeReport());
        btnFacility.addActionListener(e -> showFacilityReport());
    }

    private void showPopulationReport() {
        Map<String, Integer> data = controller.getPopulationByBuildingFloor();
        model.setColumnIdentifiers(new Object[]{"Tòa - Tầng", "Số cư dân"});
        model.setRowCount(0);
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }

    private void showFeeReport() {
        Map<String, Double> data = controller.getTotalFeeByMonth();
        model.setColumnIdentifiers(new Object[]{"Tháng", "Tổng thu phí"});
        model.setRowCount(0);
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }

    private void showFacilityReport() {
        Map<String, Integer> data = controller.getFacilityUsageByType();
        model.setColumnIdentifiers(new Object[]{"Tên tiện ích", "Số lượt sử dụng"});
        model.setRowCount(0);
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }
}
