package no.solg.sqliteviewer;

import java.util.List;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialog;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;

public class ColumnDialog {
    private String tableName;
    private MultiWindowTextGUI gui;
    private DatabaseClient dbClient;
    private String columnName;

    public ColumnDialog(String tableName, MultiWindowTextGUI gui, DatabaseClient dbClient) {
        this.tableName = tableName;
        this.gui = gui;
        this.dbClient = dbClient;
    }

    public boolean show() {
        columnName = null;
        List<String> colNames = dbClient.getColumnNames(tableName);
        ActionListDialogBuilder b = new ActionListDialogBuilder()
        .setTitle("Column name")
        .setDescription("Select column");
        for (String cn: colNames) {
            b.addAction(cn, new Runnable() {
                @Override
                public void run() {
                    columnName = cn;
                }
            });
        }

        ActionListDialog dialog = b.build();
        dialog.setCloseWindowWithEscape(true);
        dialog.showDialog(gui);

        if (columnName != null) {
            ColumnInfoDialog columnInfoDialog = new ColumnInfoDialog(tableName, columnName, gui, dbClient);
            columnInfoDialog.show();
            return false;
        } else {
            return true;
        }
    }
}
