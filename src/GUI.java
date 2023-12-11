import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class GUI {
    JFrame frame;
    GanntChart topPanel;
    JPanel bottomLeftPanel, bottomRightPanel;
    JScrollPane scrollPane;
    DefaultTableCellRenderer cellRenderer;

    HashMap<Process, Integer> processIndex;

    public GUI(List<Process> processes, int maxTime) {
        frame = new JFrame("CPU Scheduling Algorithms");

        topPanel = new GanntChart(processes, maxTime);

        scrollPane = new JScrollPane(topPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 0, 1700-20, Math.min(500, (30 * processes.size())+20));
        scrollPane.setBackground(Color.gray);
        scrollPane.setPreferredSize(new Dimension(1700-20, Math.min(500, (30 * processes.size())+20)));

        bottomLeftPanel = new JPanel();
        bottomLeftPanel.setBackground(Color.WHITE);
        bottomLeftPanel.setLayout(new GridLayout(2, 1));

        bottomRightPanel = new JPanel();
        bottomRightPanel.setBounds(1000, 500, 1000, 500);
        bottomRightPanel.setBackground(Color.WHITE);
        bottomRightPanel.setPreferredSize(new Dimension(1000, 300));
        bottomRightPanel.setLayout(new GridLayout(processes.size()+1, 1));

        frame.setSize(scrollPane.getWidth()+20, scrollPane.getHeight() + 150);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(bottomLeftPanel, BorderLayout.SOUTH);
        frame.add(bottomRightPanel);
    }

    public void show() {
        topPanel.show();
        frame.setVisible(true);
    }

    public void addLifeBlock(Process p, int start, int end) {
        topPanel.addLifeBlock(p, start, end);
    }

    public void addStats(double awt, double att) {
        bottomLeftPanel.add(new JLabel(
                "<html>Average Waiting Time : " + awt + "<br/>Average Turn Around Time : " + att + "</html>"));
    }
}
