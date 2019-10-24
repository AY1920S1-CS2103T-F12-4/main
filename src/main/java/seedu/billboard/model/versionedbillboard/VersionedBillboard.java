package seedu.billboard.model.versionedbillboard;

import java.util.Stack;

import seedu.billboard.model.Model;

/**
 * Undo command List, store the previous model and command.
 */
public class VersionedBillboard {
    private static Stack<Model> stateList = new Stack<>();
    private static Stack<String> cmdList = new Stack<>();
    private static int currentStatePointer = 0;

    /**
     * Saves the current billboard state in state history.
     */
    public static void commit(Model model) {
        if (currentStatePointer != 0) {
            for (int i = 0; i < currentStatePointer; i++) {
                stateList.pop();
                cmdList.pop();
            }
            currentStatePointer = 0;
        }
        stateList.push(model);
    }

    public static void addCmd(String cmd) {
        cmdList.push(cmd);
    }

    /**
     *  Restores the previous billboard state from state history.
     */
    public static Model getUndoModel() {
        currentStatePointer++;
        return stateList.get(stateList.size() - currentStatePointer - 1);
    }

    public static String getUndoCmd() {
        return cmdList.get(cmdList.size() - currentStatePointer);
    }

    /**
     *  Restores a previously undone billboard state from state history.
     */
    public static Model getRedoModel() {
        currentStatePointer--;
        return stateList.get(stateList.size() - currentStatePointer - 1);
    }

    public static String getRedoCmd() {
        return cmdList.get(cmdList.size() - currentStatePointer);
    }

    public static boolean isUndoable() {
        return currentStatePointer != cmdList.size() && !cmdList.empty();
    }

    public static boolean isRedoable() {
        return currentStatePointer != 0 && !cmdList.empty();
    }

    /**
     * Clear all the state history.
     */
    public static void clearStateList() {
        stateList.clear();
        cmdList.clear();
        currentStatePointer = 0;
    }

    /**
     * Compare the history of two Billboard states.
     */
    public boolean compareBillboardModels(Stack<Model> modelList) {
        return VersionedBillboard.stateList.equals(modelList);
    }

    public static void setCurrentStatePointer(int currentStatePointer) {
        VersionedBillboard.currentStatePointer = currentStatePointer;
    }
}
