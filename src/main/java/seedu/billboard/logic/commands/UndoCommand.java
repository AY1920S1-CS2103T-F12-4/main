package seedu.billboard.logic.commands;

import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.undo.UndoList;

/**
 * Undo the previous edit action.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String EMPTY_UNDO_LIST = "There is no command to be undo.";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!UndoList.isUndoable()) {
            throw new CommandException(EMPTY_UNDO_LIST);
        }

        String undoCmd = UndoList.getCmd();
        Model undoModel = UndoList.getModel();
        model.setModel(undoModel);
        CommandResult cmdResult = new CommandResult(COMMAND_WORD + ": " + undoCmd);
        return cmdResult;
    }
}