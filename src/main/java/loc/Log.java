package loc;

public class Log {

    static final String OPENING = "Opening: ";
    static final String OPENING_SUCCESS = " file(s) successfully loaded";
    static final String SAVING = "Saving: ";
    static final String EXPORTING = "Exporting: ";
    static final String MERGING = "Merging: ";
    static final String SETTINGS_CREATED = "Language settings created.";
    static final String SETTINGS_IMPORTED = "Imported language settings: ";
    static final String CHOICE_LOCKED = "Selection pool locked: ";
    static final String ERROR_OPENING = "ERROR! Could not open file ";
    static final String ERROR_SAVING = "ERROR! Could not save file ";
    static final String ERROR_SAVING_TO = "ERROR! Could not save to ";
    static final String ERROR_EXPORTING = "ERROR! Could not export file ";
    static final String ERROR_EXPORTING_TO = "ERROR! Could not export file ";
    static final String ERROR_MERGING = "ERROR! Could not merge language values and/or create a new merged file";
    static final String TOTAL_FILES_OPENED = "Current total number of files loaded: ";


    public static void print (String text) {
        GUIManager.getInstance().getLog().setCaretPosition(GUIManager.getInstance().getLog().getDocument().getLength());
        GUIManager.getInstance().getLog().append(text + "\n");

    }


    //                log.setCaretPosition(log.getDocument().getLength());
//                log.append("Opening: " + file.getName() + "." + newline);
//                log.setCaretPosition(log.getDocument().getLength());

}
