package dev.anhcraft.neep;

public class Mark {
    public static final char PATH_SEPARATOR = '.';
    public static final char LINE_BREAK = '\n';
    public static final char STRING_BOUND = '"';
    public static final char EXPRESSION_BOUND = '`';
    public static final char LIST_OPEN = '[';
    public static final char LIST_CLOSE = ']';
    public static final char SECTION_OPEN = '{';
    public static final char SECTION_CLOSE = '}';
    public static final char COMMENT = '#';

    public static boolean isStringIdf(int c){
        return c == STRING_BOUND;
    }

    public static boolean isExpressionIdf(int c){
        return c == EXPRESSION_BOUND;
    }

    public static boolean isOpenListIdf(int c){
        return c == LIST_OPEN;
    }

    public static boolean isCloseListIdf(int c){
        return c == LIST_CLOSE;
    }

    public static boolean isOpenSectionIdf(int c){
        return c == SECTION_OPEN;
    }

    public static boolean isCloseSectionIdf(int c){
        return c == SECTION_CLOSE;
    }

    public static boolean isCommentIdf(int c){
        return c == COMMENT;
    }
}
