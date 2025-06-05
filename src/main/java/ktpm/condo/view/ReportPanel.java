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

import ktpm.condo.controller.household_controller.ReportController;

/**
 * Giao diện báo cáo thống kê với nhiều loại báo cáo.
 */
public class ReportPanel extends BasePanel {
    private final ReportController controller = new ReportController();
    private final JFrame parentFrame;

    private JTable table;
    private DefaultTableModel model;

    public ReportPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initUI();
        showPopulationReport(); // Mặc định báo cáo dân cư
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnPopulation = createButton("Báo cáo dân cư");
        JButton btnFeeByMonth = createButton("Báo cáo thu phí theo tháng");
        JButton btnFeeByType = createButton("Báo cáo thu phí theo dịch vụ");
        JButton btnFacility = createButton("Báo cáo tiện ích");

        btnPanel.add(btnPopulation);
        btnPanel.add(btnFeeByMonth);
        btnPanel.add(btnFeeByType);
        btnPanel.add(btnFacility);
        add(btnPanel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        table = createTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnBack = createButton("Quay lại Dashboard");
        btnBack.addActionListener(e -> {
            parentFrame.setTitle("Hệ thống quản lý Chung cư");
            parentFrame.setContentPane(new DashboardPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        btnPopulation.addActionListener(e -> showPopulationReport());
        btnFeeByMonth.addActionListener(e -> showFeeByMonthReport());
        btnFeeByType.addActionListener(e -> showFeeByTypeReport());
        btnFacility.addActionListener(e -> showFacilityReport());
    }

    private void showPopulationReport() {
        Map<String, Integer> data = controller.getPopulationByBuildingFloor();
        model.setColumnIdentifiers(new Object[]{"Tòa - Tầng", "Số cư dân"});
        model.setRowCount(0);
        data.forEach((k, v) -> model.addRow(new Object[]{k, v}));
    }

    private void showFeeByMonthReport() {
        Map<String, Double> data = controller.getTotalFeeByMonth();
        model.setColumnIdentifiers(new Object[]{"Tháng", "Tổng thu phí"});
        model.setRowCount(0);
        data.forEach((k, v) -> model.addRow(new Object[]{k, v}));
    }

    private void showFeeByTypeReport() {
        Map<String, Double> data = controller.getTotalFeeByServiceType();
        model.setColumnIdentifiers(new Object[]{"Loại dịch vụ", "Tổng thu"});
        model.setRowCount(0);
        data.forEach((k, v) -> model.addRow(new Object[]{k, v}));
    }

    private void showFacilityReport() {
        Map<String, Integer> data = controller.getFacilityUsageByType();
        model.setColumnIdentifiers(new Object[]{"Tên tiện ích", "Số lượt sử dụng"});
        model.setRowCount(0);
        data.forEach((k, v) -> model.addRow(new Object[]{k, v}));
    }
}
