package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_ARCHIVE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.logic.commands.AddArchiveCommand;

public class AddArchiveCommandParserTest {

    private AddArchiveCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new AddArchiveCommandParser();
    }

    @Test
    public void parse_validArgs_success() {
        String validArgs = INDEX_FIRST_EXPENSE.getOneBased() + " " + PREFIX_ARCHIVE + "luxury";
        assertParseSuccess(parser, validArgs, new AddArchiveCommand("luxury", INDEX_FIRST_EXPENSE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String invalidIndex = "a " + PREFIX_ARCHIVE + "luxury";
        assertParseFailure(parser, invalidIndex,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArchiveName_throwsParseException() {
        String invalidArchiveName = INDEX_FIRST_EXPENSE.getOneBased() + " " + PREFIX_ARCHIVE;
        assertParseFailure(parser, invalidArchiveName,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddArchiveCommand.MESSAGE_USAGE));
    }
}
