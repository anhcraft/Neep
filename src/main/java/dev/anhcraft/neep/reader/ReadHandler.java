package dev.anhcraft.neep.reader;

import dev.anhcraft.neep.errors.NeepReaderException;
import dev.anhcraft.neep.struct.*;
import dev.anhcraft.neep.utils.Mark;
import dev.anhcraft.neep.utils.MathUtil;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ReadHandler {
    private static final Predicate<Character> KEY_VALIDATOR = c -> Character.isLetterOrDigit(c) || c == '_' || c == '-';

    private ReadContext readContext;
    private String key;
    // 0: key
    // 1: mid (key -> value)
    // 2: value (string)
    // 3: value (expression)
    // 4: value (list)
    // 5: value (section)
    // 6: mid (key -> [value -> key])
    private byte mode;
    // has ' or " surround the value or not
    private boolean valueBound;
    // current escape status
    private boolean escaped;
    // if the key is reading and there is a line break
    // this one will be true. it makes sure that:
    // along with space, line break is also a thing to
    // separate key with opening mark or value
    private boolean keySeparated;
    private StringBuilder stringBuilder = new StringBuilder();

    public byte getDefaultMode(){
        // entries in list ignores the key so the default mode
        // will be 1 instead of 0 like normal
        return (byte) (readContext.getContainer() instanceof NeepList ? 1 : 0);
    }

    public ReadHandler(ReadContext readContext) {
        this.readContext = readContext;
        mode = getDefaultMode();
    }

    private void finishEntry() {
        String value = stringBuilder.toString();
        if(!valueBound) {
            // only trim if the string was not in "" or ``
            value = value.trim();
        }
        NeepDynamic<?> entry;
        if(mode == 3) {
            entry = new NeepExpression(
                    readContext.getContainer(),
                    key,
                    value.trim(),
                    null
            );
        } else if(MathUtil.isNumber(value)) {
            if (value.indexOf('.') >= 0) {
                entry = new NeepDouble(
                        readContext.getContainer(),
                        key,
                        value,
                        null
                );
            } else {
                // even the value with 10 or 11 length can be an integer.... we should treat it as long
                // as currently there is no correct way to check if a string is an integer or long
                boolean i = (value.charAt(0) == '-') ? value.length() <= 10 : value.length() <= 9;
                entry = i ? new NeepInt(
                        readContext.getContainer(),
                        key,
                        value,
                        null
                ) : new NeepLong(
                        readContext.getContainer(),
                        key,
                        value,
                        null
                );
            }
        } else {
            if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                entry = new NeepBoolean(
                        readContext.getContainer(),
                        key,
                        value,
                        null
                );
            } else {
                entry = new NeepString(
                        readContext.getContainer(),
                        key,
                        value,
                        null
                );
            }
        }
        readContext.submit(entry);
        stringBuilder = new StringBuilder();
        valueBound = false;
    }

    public boolean next(char c) throws NeepReaderException {
        if(Mark.isCommentIdf(c)) {
            // not sure why if there is an element with
            // inlined comment in a list, the next element
            // will duplicate the old one, thus generating
            // a total of 3 elements
            // ...
            if(getDefaultMode() == 0) {
                // push the last entry
                end();
            }
            // collect all characters until meet the line break (or EOS)
            readContext.collectUtil(Mark.LINE_BREAK, sb -> {
                NeepComment comment = new NeepComment(
                        readContext.getContainer(),
                        sb.toString(),
                        readContext.getLastInlinedEntry() != null
                );
                if(readContext.getLastInlinedEntry() != null) {
                    if (readContext.getLastInlinedEntry().isElement()) {
                        readContext.getLastInlinedEntry().asElement().setInlineComment(comment);
                        readContext.setLastInlinedEntry(null);
                    } else {
                        try {
                            readContext.report("Comment not allowed to be put here");
                        } catch (NeepReaderException e) {
                            e.printStackTrace();
                        }
                    }
                }
                readContext.submit(comment);
                mode = getDefaultMode();
            });
            return true;
        }

        // in case if we see any closing mark...
        // only certain modes are allowed to be
        // involved in this case, e.g, mode 2 is
        // after the last entry's value, mode 1
        // is between key and value (list)
        // or before the next key (section)
        if((Mark.isCloseListIdf(c) && (mode == 1 || mode == 2)) || (Mark.isCloseSectionIdf(c) && (mode == 0 || mode == 2))) {
            // this rule only available for value without marks
            // if, e.g the value is inside "", we can't apply this rule
            if(!valueBound) {
                // make sure the current context is belong to a children container
                // for e.g, if the root has child callback -> something wrong
                if (readContext.getChildCallback() != null) {
                    // only push the last entry if the container is section
                    // the list is a special case (as their keys are auto-generated)
                    if (mode == 0) {
                        end();
                    }
                    readContext.getChildCallback().accept(readContext);
                } else {
                    readContext.report("Something wrong happened");
                }
                return false;
            }
        }

        // the main mode (getting key)
        if(mode == 0) {
            boolean om = Mark.isOpenSectionIdf(c) || Mark.isOpenListIdf(c);
            // ignore all line breaks in key
            if(c == Mark.LINE_BREAK) {
                // make sure the key is being read
                if(stringBuilder.length() > 0) {
                    keySeparated = true;
                }
                return true;
            }
            // if there is a space -> the next will be value
            // if there is an opening mark and had a line break
            // means the mark was in another line -> list/section
            else if(c == ' ' || (keySeparated && om)) {
                // if we have any space, it means the whole key has been read
                // but make sure there was other character in the key
                // for e.g: "  " is not a valid key
                if(stringBuilder.length() > 0) {
                    keySeparated = false;
                    key = stringBuilder.toString();
                    mode = 1;
                    // special case:
                    // key
                    // {
                    // when the opening mark is in another
                    // line compared to the key, the cursor
                    // in mode 1 is after the opening mark
                    // thus, can cause an error, since it can't
                    // see the opening mark to init a list/section
                    // this is a temp fix for this:
                    if(om) {
                        readContext.moveCursor(-1);
                    }
                }
            } else if(om){
                readContext.report("At least one space needed between key and opening mark");
            } else if(KEY_VALIDATOR.test(c)) {
                // having line breaks before key is allowed, but if
                // the key is being read and there WAS a line break,
                // this violates the rule; we should put the check
                // here since other exceptions have been checked,
                // e.g opening marks - which are allowed to put
                // after a line break
                if(keySeparated && stringBuilder.length() > 0) {
                    readContext.report("Multiple-lined key is not allowed: "+stringBuilder.toString());
                    return false;
                }
                stringBuilder.append(c);
            } else {
                readContext.report("Invalid character: " + c +" (only [A-Za-z0-9-_] are allowed)");
            }
        } else if(mode == 1) {
            // if the default mode is 1, it means the key has
            // been skipped, we have to manually generate it here
            if(getDefaultMode() == 1) {
                if(readContext.getContainer() instanceof NeepList){
                    // only count the elements (skip comments)
                    key = Long.toString(readContext.getContainer().stream().filter(o -> o instanceof NeepElement).count());
                } else {
                    readContext.report("Something wrong happened");
                    return false;
                }
            }
            if(Mark.isStringIdf(c)) {
                mode = 2;
                stringBuilder = new StringBuilder();
                valueBound = true;
            } else if(Mark.isExpressionIdf(c)) {
                mode = 3;
                stringBuilder = new StringBuilder();
                valueBound = true;
            } else if(Mark.isOpenListIdf(c)) {
                mode = 4;
            } else if(Mark.isOpenSectionIdf(c)) {
                mode = 5;
            } else if(Mark.isCloseSectionIdf(c) || Mark.isCloseListIdf(c)) {
                readContext.report("Container closed unexpectedly! ");
            }
            // between the key and the value can contain spaces or line breaks
            // if we meet other character, it means the value is present now
            // the value in this case is not surrounded by value mark (") or (`)
            else if(c != ' ' && c != Mark.LINE_BREAK) {
                mode = 2;
                stringBuilder = new StringBuilder();
                // we have to append the current character!!!
                // e.g: "key value"; the current char is "v"
                stringBuilder.append(c);
                valueBound = false;
            }
        } else if(mode == 2 || mode == 3) {
            boolean a = Mark.isStringIdf(c);
            boolean b = Mark.isExpressionIdf(c);
            if(a || b) {
                if(escaped) {
                    escaped = false;
                } else {
                    if(!valueBound) {
                        // e.g: key value" (after the value is a closing mark)
                        readContext.report("Closing mark found while opening mark not");
                    }
                    // make sure the opening mark and closing mark are same type
                    // e.g: ` must go with `, not "
                    else if((mode == 2 && a) || (mode == 3 && b)) {
                        finishEntry();
                        mode = 6;
                    } else {
                        readContext.report("Unmatched closing mark");
                    }
                    return true;
                }
            }
            // if the value was not surrounded by value mark, the line break
            // is where the value will not be read anymore
            else if(c == Mark.LINE_BREAK && !valueBound) {
                finishEntry();
                mode = getDefaultMode();
                readContext.setLastInlinedEntry(null);
                return true;
            }
            else if(c == '\\') {
                escaped = !escaped;
                return true; // skip "\"
            }
            stringBuilder.append(c);
        } else if(mode == 4) {
            mode = 6;
            NeepList<?> component = new NeepList<>(
                    readContext.getContainer(),
                    key,
                    null,
                    new ArrayList<>()
            );
            new ReadContext(
                    readContext,
                    component,
                    last -> {
                        readContext.setCursor(last.getCursor());
                        readContext.submit(component);
                        stringBuilder = new StringBuilder();
                        try {
                            readContext.handle();
                        } catch (NeepReaderException e) {
                            e.printStackTrace();
                        }
                    }
            ).handle();
            return false;
        } else if(mode == 5) {
            mode = 6;
            NeepSection component = new NeepSection(
                    readContext.getContainer(),
                    key,
                    null,
                    new ArrayList<>()
            );
            new ReadContext(
                    readContext,
                    component,
                    last -> {
                        readContext.setCursor(last.getCursor());
                        readContext.submit(component);
                        stringBuilder = new StringBuilder();
                        try {
                            readContext.handle();
                        } catch (NeepReaderException e) {
                            e.printStackTrace();
                        }
                    }
            ).handle();
            return false;
        } else if(mode == 6){
            // between entries must have at least one space
            // or line break, e,g:
            // key_1 "value" key_2 "value"
            if(c == ' ' || c == Mark.LINE_BREAK) {
                if(c == Mark.LINE_BREAK) {
                    // new line -> no inlined entry
                    readContext.setLastInlinedEntry(null);
                }
                mode = getDefaultMode();
            } else {
                readContext.report("Invalid character " + c);
            }
        }
        return true;
    }

    public void end() throws NeepReaderException {
        // it should have mode != 6 here but we have a rule is
        // comment must have at least one space after the last entry
        // like: key "value" # comment
        // adding mode != 6 will break this rule
        if(mode == 2 || mode == 3 || getDefaultMode() == 1){
            finishEntry();
        } else if((mode != 0 || stringBuilder.length() > 0)) {
            readContext.report("Entry ended unexpectedly!");
        }
    }

    public void eos() throws NeepReaderException {
        if(readContext.getChildCallback() != null){
            readContext.report("Container ended unexpectedly!");
        } else if(mode != 0 && mode != 1 && mode != 6) {
            // spacial case: <begin>key value<end>
            if(!valueBound && (mode == 2 || mode == 3)) {
                finishEntry();
                return;
            }
            readContext.report("Entry ended unexpectedly!");
        }
    }
}
