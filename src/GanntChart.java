import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GanntChart extends JPanel {
    HashMap<Process, Integer> processIndex;
   static HashMap<String, Color> processColor;
    List<List<JPanel>> matrix;

    public GanntChart(List<Process> processes, int maxTime) {
        super();
        processIndex = new HashMap<>();
        matrix = new ArrayList<List<JPanel>>();

        
        GridLayout layout = new GridLayout(processes.size() + 1, maxTime + 1);
        this.setLayout(layout);
        this.setBounds(0, 0, 50 * (maxTime), 30 * (processes.size()));
        this.setPreferredSize(new Dimension(50 * (maxTime), 30 * (processes.size())));
        this.setSize(getPreferredSize());

        
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Process");
        panel.add(label);
        panel.setPreferredSize(new Dimension(panel.getPreferredSize().width, 20));
        List<JPanel> row = new ArrayList<JPanel>();
        row.add(panel);

        for(int i = 0; i <= maxTime; i++) {
            panel = new JPanel();
            label = new JLabel(Integer.toString(i));
            panel.setPreferredSize(new Dimension(panel.getPreferredSize().width, 20));
            panel.add(label);
            row.add(panel);
        }
        matrix.add(row);

        addProcesses(processes, maxTime);

        processColor = new HashMap<>();
        processColor.put("RED", Color.RED);
        processColor.put("BLUE", Color.BLUE);
        processColor.put("GREEN", Color.GREEN);
        processColor.put("YELLOW", Color.YELLOW);
        processColor.put("ORANGE", Color.ORANGE);
        processColor.put("PINK", Color.PINK);
        processColor.put("CYAN", Color.CYAN);
        processColor.put("MAGENTA", Color.MAGENTA);
        processColor.put("GRAY", Color.GRAY);
        processColor.put("BLACK", Color.BLACK);
    }

    public void addProcesses(List<Process> processes, int maxTime) {
        int i = 0;
        for (Process process : processes) {
            processIndex.put(process, i++);
            JPanel panel = new JPanel();
            JLabel label = new JLabel(process.getName());
            panel.add(label);
            List<JPanel> row = new ArrayList<JPanel>();
            row.add(panel);
            for (int j = 0; j <= maxTime; j++) {
                panel = new JPanel();
                panel.setPreferredSize(new Dimension(panel.getPreferredSize().width, 20));
                row.add(panel);
            }
            matrix.add(row);
        }
    }
    public void show() {
        for (List<JPanel> row : matrix) {
            for (JPanel panel : row) {
                this.add(panel);
            }
        }
    }
    public void addLifeBlock(Process process, int start, int end) {
        int index = processIndex.get(process);
        for (int i = start; i < end; i++) {
            JPanel panel = matrix.get(index + 1).get(i + 1);
            panel.setBackground(processColor.get(process.getColor()));
        }
    }
}
