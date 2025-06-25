package axcavo.sjp;

import axcavo.sjp.Obj.Array;
import axcavo.sjp.Obj.ArrayEnd;
import axcavo.sjp.Obj.ArrayStart;
import axcavo.sjp.Obj.BooleanValue;
import axcavo.sjp.Obj.KeySeparator;
import axcavo.sjp.Obj.NullValue;
import axcavo.sjp.Obj.NumberValue;
import axcavo.sjp.Obj.Object;
import axcavo.sjp.Obj.ObjectEnd;
import axcavo.sjp.Obj.ObjectStart;
import axcavo.sjp.Obj.Pair;
import axcavo.sjp.Obj.StringValue;
import axcavo.sjp.Obj.Value;
import axcavo.sjp.Obj.ValueSeparator;

public class AstPrinter implements Obj.Visitor<String> {
    String print(Obj obj) {
        return obj.accept(this);
    }

    private String parenthesize(String name, Obj... objs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Obj obj : objs) {
            builder.append(" ");
            builder.append(obj.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }

    @Override
    public String visitStringValueObj(StringValue obj) {
        return "(String \"" + obj.token.lexeme + "\")";
    }

    @Override
    public String visitNumberValueObj(NumberValue obj) {
        return "(Number " + obj.token.lexeme + ")";
    }

    @Override
    public String visitBooleanValueObj(BooleanValue obj) {
        return "(Boolean " + obj.token.lexeme + ")";
    }

    @Override
    public String visitNullValueObj(NullValue obj) {
        return "(Null " + obj.token.lexeme + ")";
    }

    @Override
    public String visitKeySeparatorObj(KeySeparator obj) {
        return "(KeySeparator " + obj.token.lexeme + ")";
    }

    @Override
    public String visitValueSeparatorObj(ValueSeparator obj) {
        return "(ValueSeparator " + obj.token.lexeme + ")";
    }

    @Override
    public String visitObjectStartObj(ObjectStart obj) {
        return "(ObjectStart " + obj.token.lexeme + ")";
    }

    @Override
    public String visitObjectEndObj(ObjectEnd obj) {
        return "(ObjectEnd " + obj.token.lexeme + ")";
    }

    @Override
    public String visitArrayStartObj(ArrayStart obj) {
        return "(ArrayStart " + obj.token.lexeme + ")";
    }

    @Override
    public String visitArrayEndObj(ArrayEnd obj) {
        return "(ArrayEnd " + obj.token.lexeme + ")";
    }


    @Override
    public String visitObjectObj(Object obj) {
        return parenthesize("Object", obj.pairs.toArray(new Obj[0]));
    }

    @Override
    public String visitArrayObj(Array obj) {
        return parenthesize("Array", obj.values.toArray(new Obj[0]));
    }

    @Override
    public String visitValueObj(Value obj) {
        return parenthesize("Value", obj.value);
    }

    @Override
    public String visitpairObj(Pair obj) {
        return parenthesize("Pair", obj.key, obj.value);
    }
}
