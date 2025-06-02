package ktpm.condo.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.model.dao.CitizenDAO;
import ktpm.condo.model.entity.Citizen;

/**
 * Panel quản lý nhân khẩu thuộc một hộ khẩu cụ thể.
 */
public class CitizenPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private final CitizenDAO dao = new CitizenDAO();
    private final int householdId;

    public CitizenPanel(int householdId) {
        this.householdId = householdId;
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Họ tên", "Ngày sinh", "Giới tính", "Nghề nghiệp", "Quan hệ"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnAdd = new JButton("Thêm nhân khẩu");
        JButton btnDelete = new JButton("Xoá nhân khẩu");

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnDelete);
        add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addCitizen());
        btnDelete.addActionListener(e -> deleteCitizen());

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Citizen> list = dao.getCitizensByHouseholdId(householdId);
        for (Citizen c : list) {
            tableModel.addRow(new Object[]{
                    c.getId(),
                    c.getName(),
                    c.getDateOfBirth(),
                    c.getGender(),
                    c.getJob(),
                    c.getRelationshipToHead()
            });
        }
    }

    private void addCitizen() {
        JTextField tfName = new JTextField();
        JTextField tfDOB = new JTextField("yyyy-MM-dd");
        JTextField tfGender = new JTextField();
        JTextField tfJob = new JTextField();
        JTextField tfRelation = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Họ tên:"));
        panel.add(tfName);
        panel.add(new JLabel("Ngày sinh (yyyy-MM-dd):"));
        panel.add(tfDOB);
        panel.add(new JLabel("Giới tính:"));
        panel.add(tfGender);
        panel.add(new JLabel("Nghề nghiệp:"));
        panel.add(tfJob);
        panel.add(new JLabel("Quan hệ với chủ hộ:"));
        panel.add(tfRelation);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm nhân khẩu", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Citizen c = new Citizen();
            c.setHouseholdId(householdId);
            c.setName(tfName.getText());
            c.setDateOfBirth(LocalDate.parse(tfDOB.getText()));
            c.setGender(tfGender.getText());
            c.setJob(tfJob.getText());
            c.setRelationshipToHead(tfRelation.getText());

            if (dao.addCitizen(c)) {
                JOptionPane.showMessageDialog(this, "Đã thêm.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi thêm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteCitizen() {
        int selected = table.getSelectedRow();
        if (selected != -1) {
            int id = (int) tableModel.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xoá nhân khẩu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.deleteCitizen(id)) {
                    JOptionPane.showMessageDialog(this, "Đã xoá.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xoá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
