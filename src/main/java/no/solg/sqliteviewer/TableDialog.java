package no.solg.sqliteviewer;

import java.util.List;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialog;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;

public class TableDialog {
    private MultiWindowTextGUI gui;
    private DatabaseClient dbClient;
    private String tableName;

    public TableDialog(MultiWindowTextGUI gui, DatabaseClient dbClient) {
        this.gui = gui;
        this.dbClient = dbClient;
    }

    public boolean show() {
        tableName = null;
        List <String> tableNames = dbClient.getTableNames();
        ActionListDialogBuilder b = new ActionListDialogBuilder()
        .setTitle("Table name")
        .setDescription("Select table");
        for (String tn: tableNames) {
            b.addAction(tn, new Runnable() {
                @Override
                public void run() {
                    tableName = tn;
                }
            });
        }

        ActionListDialog dialog = b.build();
        dialog.setCloseWindowWithEscape(true);
        dialog.showDialog(gui);

        if (tableName != null) {
            boolean cancelled;
            do {
                ColumnDialog columnDialog = new ColumnDialog(tableName, gui, dbClient);
                cancelled = columnDialog.show();
            } while (! cancelled);
            return false;
        } else {
            return true;
        }
    }
}
