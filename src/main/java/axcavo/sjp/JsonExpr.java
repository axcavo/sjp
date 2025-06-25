package axcavo.sjp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

abstract class JsonExpr<R> {
    abstract R map();

    static class StringValue extends JsonExpr<String> {
        final Token token;

        StringValue(Token token) {
            this.token = token;
        }

        @Override
        String map() {
            return token.lexeme;
        }
    }

    static class NumberValue extends JsonExpr<Double> {
        final Token token;

        NumberValue(Token token) {
            this.token = token;
        }

        @Override
        Double map() {
            return Double.valueOf(token.lexeme);
        }
    }

    static class BooleanValue extends JsonExpr<Boolean> {
        final Token token;

        BooleanValue(Token token) {
            this.token = token;
        }

        @Override
        Boolean map() {
            return Boolean.valueOf(token.lexeme);
        }
    }

    static class NullValue extends JsonExpr<Object> {
        final Token token;

        NullValue(Token token) {
            this.token = token;
        }

        @Override
        ObjectExpr map() {
            return null;
        }
    }

    static class Value<R> extends JsonExpr<R> {
        final JsonExpr<R> value;

        Value(JsonExpr<R> value) {
            this.value = value;
        }

        @Override
        R map() {
            return value.map();
        }
    }

    static class PairExpr extends JsonExpr<Map.Entry<String, Object>> {
        final StringValue key;
        final JsonExpr<?> value;

        public PairExpr(StringValue key, JsonExpr<?> value) {
            this.key = key;
            this.value = value;
        }

        @Override
        Entry<String, Object> map() {
            return Map.entry(key.map(), value.map());
        }
    }

    static class ObjectExpr extends JsonExpr<Map<String, Object>> {
        final List<JsonExpr.PairExpr> pairs;

        public ObjectExpr(List<JsonExpr.PairExpr> pairs) {
            this.pairs = pairs;
        }

        @Override
        Map<String, Object> map() {
            Map<String, Object> result = new LinkedHashMap<>();
            for (JsonExpr<Map.Entry<String, Object>> pairExpr : pairs) {
                Map.Entry<String, Object> entry = pairExpr.map();
                result.put(entry.getKey(), entry.getValue());
            }
            return result;
        }
   }

    static class ArrayExpr extends JsonExpr<List<Object>> {
        final List<JsonExpr<?>> values;

        public ArrayExpr(List<JsonExpr<?>> values) {
            this.values = values;
        }

        @Override
        List<Object> map() {
            List<Object> list = new ArrayList<>();
            for (JsonExpr<?> value : values) {
                list.add(value.map());
            }
            return list;
        }
    }
}
