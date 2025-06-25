package axcavo.sjp;

import static axcavo.sjp.TokenType.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


class TreeMapper {
    private final List<Token> tokens;
    private int current = 0;

    TreeMapper(List<Token> tokens) {
        this.tokens = tokens;
    }

    Map<String, Object> map() {
        return object().map();
    }

    private JsonExpr.ObjectExpr object() {
        consume(OBJECT_START, "Expected '{'");

        List<JsonExpr.PairExpr> pairs = new ArrayList<>();

        if (!check(OBJECT_END)) {
            do {
                pairs.add((JsonExpr.PairExpr) pair());
            } while (match(VALUE_SEPARATOR));
        }

        consume(OBJECT_END, "Expected '}'");

        return new JsonExpr.ObjectExpr(pairs);
    }

    private JsonExpr<String> stringValue() {
        Token token = consume(STRING_VALUE, "Expected String.");
        return new JsonExpr.StringValue(token);
    }

    private JsonExpr<Map.Entry<String, Object>> pair() {
        JsonExpr.StringValue key = (JsonExpr.StringValue) stringValue();
        consume(KEY_SEPARATOR, "Expected ':'");
        JsonExpr<?> value = value();

        return new JsonExpr.PairExpr(key, value);
    }

    private JsonExpr<List<Object>> array() {
        consume(ARRAY_START, "Expected '['");

        List<JsonExpr<?>> values = new ArrayList<>();

        if (!check(ARRAY_END)) {
            do {
                values.add(value());
            } while (match(VALUE_SEPARATOR));
        }

        consume(ARRAY_END, "Expected ']'");

        return new JsonExpr.ArrayExpr(values);
    }

    private JsonExpr<Double> numberValue() {
        Token token = consume(NUMBER_VALUE, "Expected Number.");
        return new JsonExpr.NumberValue(token);
    }

    private JsonExpr<Boolean> booleanValue() {
        Token token = consume(BOOLEAN_VALUE, "Expected Boolean.");
        return new JsonExpr.BooleanValue(token);
    }

    private JsonExpr<Object> nullValue() {
        Token token = consume(NULL_VALUE, "Expected Null.");
        return new JsonExpr.NullValue(token);
    }

    private JsonExpr<?> value() {
        if (check(STRING_VALUE)) return stringValue();
        if (check(NUMBER_VALUE)) return numberValue();
        if (check(BOOLEAN_VALUE)) return booleanValue();
        if (check(NULL_VALUE)) return nullValue();
        if (check(OBJECT_START)) return object();
        if (check(ARRAY_START)) return array();

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
        throw new JsonParserException(String.format("%s Found: '%s'. At line %d.", message, peek().lexeme, peek().line));
    }
}
