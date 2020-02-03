package no.solg.sqliteviewer;

import java.io.File;
import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.FileDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class App 
{
    static Screen initTerminal() {
        Screen screen = null;
        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
        } catch (IOException e) {
            System.out.println("Unable to setup terminal");
        }
        return screen;
    }

    static File getInputFile(String[] args, MultiWindowTextGUI gui) {
        if (args.length == 1) {
            return new File(args[0]);
        } else {
            FileDialog fileDialog = new FileDialog("Database", "Select a SQLite database", "Open", new TerminalSize(50, 10), false, null);
            return fileDialog.showDialog(gui);
        }
    }

    public static void main(String[] args) {
        Screen screen = initTerminal();

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
                new EmptySpace(TextColor.ANSI.BLUE));

        File dbFile = getInputFile(args, gui);
        if (dbFile != null) {
            DatabaseClient dbClient = new DatabaseClient(dbFile);
            TableDialog tableDialog = new TableDialog(gui, dbClient);
            boolean cancelled;
            do {
                cancelled = tableDialog.show();
            } while (! cancelled);

            try {
                screen.stopScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
