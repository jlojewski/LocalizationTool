package loc;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        IOManager.getInstance().setExpandableListOfLoadedFiles(new ArrayList<List<TranslationEntry>>());
        GUIManager.getInstance().prepareMainGUI();
        GUIManager.getInstance().showMainGUI();



    }
}
