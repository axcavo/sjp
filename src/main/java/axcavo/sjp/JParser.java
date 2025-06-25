package axcavo.sjp;

import static axcavo.sjp.TokenType.*;

import java.util.ArrayList;
import java.util.List;


class JParser {
    private final List<Token> tokens;
    private int current = 0;

    JParser(List<Token> tokens) {
        this.tokens = tokens;
    }

    Obj parse() {
        return object();
    }

    private Obj objectStart() {
        Token token = consume(OBJECT_START, "Expected '{' at the beginning of the object.");
        return new Obj.ObjectStart(token);
    }

    private Obj objectEnd() {
        Token token = consume(OBJECT_END, "Expected '}' at the end of the object.");
        return new Obj.ObjectEnd(token);
    }

    private Obj object() {
        Obj.ObjectStart objectStart = (Obj.ObjectStart) objectStart();

        List<Obj.Pair> pairs = new ArrayList<>();

        if (!check(OBJECT_END)) {
            do {
                pairs.add((Obj.Pair) pair());
            } while (match(VALUE_SEPARATOR));
        }

        Obj.ObjectEnd objectEnd = (Obj.ObjectEnd) objectEnd();

        return new Obj.Object(objectStart, pairs, objectEnd);
    }

    private Obj stringValue() {
        Token token = consume(STRING_VALUE, "Expected String.");
        return new Obj.StringValue(token);
    }

    private Obj valueSeparator() {
        Token token = consume(KEY_SEPARATOR, "Expected ':'");
        return new Obj.ValueSeparator(token);
    }

    private Obj pair() {
        Obj.StringValue key = (Obj.StringValue) stringValue();
        valueSeparator();
        Obj.Value value = (Obj.Value) value();

        return new Obj.Pair(key, value);
    }

    private Obj arrayStart() {
        Token token = consume(ARRAY_START, "Expected '['.");
        return new Obj.ArrayStart(token);
    }

    private Obj arrayEnd() {
        Token token = consume(ARRAY_END, "Expected ']'.");
        return new Obj.ArrayEnd(token);
    }

    private Obj array() {
        Obj.ArrayStart arrayStart = (Obj.ArrayStart) arrayStart();

        List<Obj.Value> values = new ArrayList<>();

        if (!check(ARRAY_END)) {
            do {
                values.add((Obj.Value) value());
            } while (match(VALUE_SEPARATOR));
        }

        Obj.ArrayEnd arrayEnd = (Obj.ArrayEnd) arrayEnd();

        return new Obj.Array(arrayStart, values, arrayEnd);
    }

    private Obj numberValue() {
        Token token = consume(NUMBER_VALUE, "Expected number.");
        return new Obj.NumberValue(token);
    }

    private Obj booleanValue() {
        Token token = consume(BOOLEAN_VALUE, "Expected boolean.");
        return new Obj.BooleanValue(token);
    }

    private Obj nullValue() {
        Token token = consume(NUMBER_VALUE, "Expected null.");
        return new Obj.NullValue(token);
    }

    private Obj value() {
        if (check(STRING_VALUE)) return new Obj.Value(stringValue());
        if (check(NUMBER_VALUE)) return new Obj.Value(numberValue());
        if (check(BOOLEAN_VALUE)) return new Obj.Value(booleanValue());
        if (check(NULL_VALUE)) return new Obj.Value(nullValue());
        if (check(OBJECT_START)) return new Obj.Value(object());
        if (check(ARRAY_START)) return new Obj.Value(array());

        throw new JsonParserException("error");
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw new JsonParserException(message + " Found: " + peek().type + " (" + peek().lexeme + ")");
    }


    /*
    static void error(Token token, String message) {
        if (token.type == EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }
    */
}
