package seedu.billboard.logic.parser;

import seedu.billboard.commons.core.index.Index;
import seedu.billboard.logic.commands.AddArchiveCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_ARCHIVE;

/**
 * Parses input arguments and creates a new AddArchiveCommand object
 */
public class AddArchiveCommandParser implements Parser<AddArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddArchiveCommand
     * and returns a AddArchiveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddArchiveCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ARCHIVE);

        try {
            String archiveName = ParserUtil.parseArchive(argMultimap.getValue(PREFIX_ARCHIVE).get());
            Index targetIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            return new AddArchiveCommand(archiveName, targetIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddArchiveCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}