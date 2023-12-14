import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class GUI {
    JFrame frame;
    GanntChart topPanel;
    JPanel bottomLeftPanel, bottomRightPanel, tablePanel;
    JScrollPane scrollPane;

    HashMap<Process, Integer> processIndex;
   static List<Process> processes;
    JPanel rightPanel = new JPanel();


    public GUI(List<Process> processes, int maxTime) {
        this.processes = processes;
        frame = new JFrame("CPU Scheduling Algorithms");
        topPanel = new GanntChart(processes, maxTime);

        scrollPane = new JScrollPane(topPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 0, 1700 - 400, Math.min(500, (30 * processes.size()) + 20));
        scrollPane.setBackground(Color.gray);
        scrollPane.setPreferredSize(new Dimension(1700 - 400, Math.min(500, (30 * processes.size()) + 20)));

        bottomLeftPanel = new JPanel();
        bottomLeftPanel.setBackground(Color.gray);
        bottomLeftPanel.setLayout(new GridLayout(2, 1));

        tablePanel = new JPanel();
        tablePanel.setBackground(Color.gray);
        tablePanel.setBounds(0, 0, 1000, 100);
        tablePanel.setPreferredSize(new Dimension(500, 100));
        tablePanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        tablePanel.setAlignmentY(JPanel.TOP_ALIGNMENT);

        frame.setSize(scrollPane.getWidth() + 400, scrollPane.getHeight() + 150);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setLayout(new BorderLayout());


        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);


        frame.getContentPane().add(bottomLeftPanel, BorderLayout.SOUTH);

        rightPanel.setLayout(new GridLayout(2, 1));
        rightPanel.add(tablePanel);
        rightPanel.setBackground(Color.gray);

        frame.getContentPane().add(rightPanel, BorderLayout.EAST);
        frame.getContentPane().setBackground(Color.gray);
        frame.getContentPane().add(bottomLeftPanel, BorderLayout.SOUTH);
    }

    public void show() {
        topPanel.show();
        addProcessesInfo();
        frame.setVisible(true);
    }

    public void addLifeBlock(Process p, int start, int end) {
        topPanel.addLifeBlock(p, start, end);
    }

    public void addStats(double awt, double att) {
        bottomLeftPanel.add(new JLabel(
                "<html>Average Waiting Time : " + awt + "<br/>Average Turn Around Time : " + att + "</html>"));
    }

    public void addProcessesInfo() {
        String[] columnNames = {"Process", "Color", "Name", "PID", "Priority"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);




        int cnt = 1;
        for (Process p : processes) {
            JPanel panel = new JPanel();
            model.addRow(new Object[]{cnt++, "  ", p.getName(), p.getId(), p.getPriority()});
            panel.setPreferredSize(new Dimension(1, 1));

        }


        int rowHeight = table.getRowHeight();
        int numRows = table.getRowCount();
        int newHeight = Math.min(500, numRows * rowHeight);
        tablePanel.setPreferredSize(new Dimension(500, newHeight));
//        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(1).setCellRenderer( new ColoredTableCellRenderer());

//        }
        tablePanel.add(table.getTableHeader());

        tablePanel.add(table);

    }


}

class ColoredTableCellRenderer extends DefaultTableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        Component renderer = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

            renderer.setBackground(GanntChart.processColor.get(GUI.processes.get(row).getColor()));

        return renderer;
    }
};
