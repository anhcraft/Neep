package dev.anhcraft.neep.writer;

import dev.anhcraft.neep.errors.NeepWriterException;
import dev.anhcraft.neep.struct.*;
import dev.anhcraft.neep.utils.Mark;

public class WriteHandler {
    private static final String SPACE = "  ";
    private WriteContext context;

    public WriteHandler(WriteContext context) {
        this.context = context;
    }

    public void write() throws NeepWriterException {
        for (NeepComponent component : context.getContainer()){
            if(component instanceof NeepDynamic<?>) {
                NeepDynamic<?> np = (NeepDynamic<?>) component;
                char mark = component instanceof NeepExpression ? Mark.EXPRESSION_BOUND : Mark.STRING_BOUND;
                if (context.getContainer() instanceof NeepList) {
                    context.newLine(mark);
                } else {
                    context.newLine(np.getKey()).append(' ').append(mark);
                }
                char[] chars = np.stringifyValue().toCharArray();
                for (char c : chars){
                    if(Mark.isExpressionIdf(c) || Mark.isStringIdf(c)) {
                        context.append('\\');
                    }
                    context.append(c);
                }
                context.append(mark);
                if(np.getInlineComment() != null) {
                    context.append(" #").append(np.getInlineComment().getContent());
                }
                context.nextLine();
            } else if(component instanceof NeepList<?>) {
                NeepList<?> list = (NeepList<?>) component;
                if (context.getContainer() instanceof NeepList) {
                    context.newLine(Mark.LIST_OPEN);
                } else {
                    context.newLine(list.getKey()).append(" ").append(Mark.LIST_OPEN);
                }
                if(list.size() == 0) {
                    context.append(Mark.LIST_CLOSE).nextLine();
                } else if(list.get(0) instanceof NeepComponent) {
                    @SuppressWarnings("unchecked")
                    WriteContext writeContext = new WriteContext(
                            (NeepList<NeepComponent>) list,
                            context.getPrefix() + SPACE
                    );
                    writeContext.getWriteHandler().write();
                    context.nextLine();
                    context.append(writeContext.buildString());
                    context.newLine(Mark.LIST_CLOSE).nextLine();
                } else {
                    context.report("Unsupported list type");
                }
            } else if(component instanceof NeepSection) {
                NeepSection section = (NeepSection) component;
                if (context.getContainer() instanceof NeepList) {
                    context.newLine(Mark.SECTION_OPEN);
                } else {
                    context.newLine(section.getKey()).append(" ").append(Mark.SECTION_OPEN);
                }
                if(section.size() == 0) {
                    context.append(Mark.SECTION_CLOSE).nextLine();
                } else {
                    WriteContext writeContext = new WriteContext(
                            section,
                            context.getPrefix() + SPACE
                    );
                    writeContext.getWriteHandler().write();
                    context.nextLine();
                    context.append(writeContext.buildString());
                    context.newLine(Mark.SECTION_CLOSE).nextLine();
                }
            } else if(component instanceof NeepComment) {
                if(!((NeepComment) component).isInlined()) {
                    context.newLine("#").append(((NeepComment) component).getContent()).nextLine();
                }
            } else {
                context.report("Unsupported component type: " + component.getClass().getSimpleName());
            }
        }
    }
}
