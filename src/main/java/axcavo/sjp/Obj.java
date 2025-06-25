package axcavo.sjp;

import java.util.List;

abstract class Obj {
    static interface Visitor<R> {
        R visitStringValueObj(Obj.StringValue obj);
        R visitNumberValueObj(Obj.NumberValue obj);
        R visitBooleanValueObj(Obj.BooleanValue obj);
        R visitNullValueObj(Obj.NullValue obj);
        R visitObjectStartObj(Obj.ObjectStart obj);
        R visitObjectEndObj(Obj.ObjectEnd obj);
        R visitArrayStartObj(Obj.ArrayStart obj);
        R visitArrayEndObj(Obj.ArrayEnd obj);
        R visitObjectObj(Obj.Object obj);
        R visitArrayObj(Obj.Array obj);
        R visitValueObj(Obj.Value obj);
        R visitpairObj(Obj.Pair obj);
        R visitKeySeparatorObj(Obj.KeySeparator obj);
        R visitValueSeparatorObj(Obj.ValueSeparator obj);
    }

    abstract <R> R accept(Visitor<R> visitor);

    static class StringValue extends Obj {
        final Token token;

        StringValue(Token token) {
            this.token = token;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitStringValueObj(this);
        }
    }

    static class NumberValue extends Obj {
        final Token token;

        NumberValue(Token token) {
            this.token = token;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitNumberValueObj(this);
        }
    }

    static class BooleanValue extends Obj {
        final Token token;

        BooleanValue(Token token) {
            this.token = token;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBooleanValueObj(this);
        }
    }

    static class NullValue extends Obj {
        final Token token;

        NullValue(Token token) {
            this.token = token;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitNullValueObj(this);
        }
    }

    static class KeySeparator extends Obj {
        final Token token;

        KeySeparator(Token token) {
            this.token = token;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitKeySeparatorObj(this);
        }
    }

    static class ValueSeparator extends Obj {
        final Token token;

        ValueSeparator(Token token) {
            this.token = token;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitValueSeparatorObj(this);
        }
    }

    static class ObjectStart extends Obj {
        final Token token;

        ObjectStart(Token token) {
            this.token = token;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitObjectStartObj(this);
        }
    }

    static class ObjectEnd extends Obj {
        final Token token;

        ObjectEnd(Token token) {
            this.token = token;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitObjectEndObj(this);
        }
    }

    static class ArrayStart extends Obj {
        final Token token;

        ArrayStart(Token token) {
            this.token = token;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitArrayStartObj(this);
        }
    }

    static class ArrayEnd extends Obj {
        final Token token;

        ArrayEnd(Token token) {
            this.token = token;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitArrayEndObj(this);
        }
    }

    static class Value extends Obj {
        final Obj value;

        Value(Obj value) {
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitValueObj(this);
        }
    }

    static class Pair extends Obj {
        final StringValue key;
        final Value value;

        public Pair(StringValue key, Value value) {
            this.key = key;
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitpairObj(this);
        }
    }

    static class Object extends Obj {
        final ObjectStart objectStart;
        final List<Pair> pairs;
        final ObjectEnd objectEnd;

        public Object(ObjectStart objectStart, List<Pair> pairs, ObjectEnd objectEnd) {
            this.objectStart = objectStart;
            this.pairs = pairs;
            this.objectEnd = objectEnd;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitObjectObj(this);
        }
    }

    static class Array extends Obj {
        final ArrayStart arrayStart;
        final List<Value> values;
        final ArrayEnd arrayEnd;

        public Array(ArrayStart arrayStart, List<Value> values, ArrayEnd arrayEnd) {
            this.arrayStart = arrayStart;
            this.values = values;
            this.arrayEnd = arrayEnd;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitArrayObj(this);
        }
    }
}
