package no.solg.sqliteviewer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;

public class ColumnInfoDialog {
    private String tableName;
    private String columnName;
    private MultiWindowTextGUI gui;
    private DatabaseClient dbClient;


    public ColumnInfoDialog(String tableName, String columnName, MultiWindowTextGUI gui, DatabaseClient dbClient) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.gui = gui;
        this.dbClient = dbClient;
    }

    public void show() {
        Panel panel = new Panel();
        BasicWindow window = new BasicWindow();

        panel.setLayoutManager(new GridLayout(2));

        final Label countOutput = new Label("");
        final Label minOutput = new Label("");
        final Label maxOutput = new Label("");
        final Label avgOutput = new Label("");

        countOutput.setPreferredSize(new TerminalSize(10, 1));

        new Button("Count", new Runnable() {
            @Override
            public void run() {
                int count = dbClient.getCount(tableName, columnName);
                countOutput.setText(String.valueOf(count));
            }
        }).addTo(panel);
        countOutput.addTo(panel);

        new Button("Min", new Runnable() {
            @Override
            public void run() {
                double val = dbClient.getAggregated(tableName, columnName, "min");
                minOutput.setText(String.valueOf(val));
            }
        }).addTo(panel);
        minOutput.addTo(panel);

        new Button("Max", new Runnable() {
            @Override
            public void run() {
                double val = dbClient.getAggregated(tableName, columnName, "max");
                maxOutput.setText(String.valueOf(val));
            }
        }).addTo(panel);
        maxOutput.addTo(panel);

        new Button("Avg", new Runnable() {
            @Override
            public void run() {
                double val = dbClient.getAggregated(tableName, columnName, "avg");
                avgOutput.setText(String.valueOf(val));
            }
        }).addTo(panel);
        avgOutput.addTo(panel);

        new Button("Cancel", new Runnable() {
            @Override
            public void run() {
                window.close();
            }
        }).addTo(panel);

        window.setComponent(panel);

        gui.addWindowAndWait(window);
    }
}
