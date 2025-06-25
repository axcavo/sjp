package axcavo.sjp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import axcavo.sjp.Obj.Array;
import axcavo.sjp.Obj.ArrayEnd;
import axcavo.sjp.Obj.ArrayStart;
import axcavo.sjp.Obj.BooleanValue;
import axcavo.sjp.Obj.KeySeparator;
import axcavo.sjp.Obj.NullValue;
import axcavo.sjp.Obj.NumberValue;
import axcavo.sjp.Obj.ObjectEnd;
import axcavo.sjp.Obj.ObjectStart;
import axcavo.sjp.Obj.Pair;
import axcavo.sjp.Obj.StringValue;
import axcavo.sjp.Obj.ValueSeparator;

public class AstMapper implements Obj.Visitor<Object> {

    public Object map(Obj obj)  {
        return obj.accept(this);
    }

    @Override
    public Object visitStringValueObj(StringValue obj) {
        return obj.token.lexeme;
    }

    @Override
    public Object visitNumberValueObj(NumberValue obj) {
        return Double.valueOf(obj.token.lexeme);
    }

    @Override
    public Object visitBooleanValueObj(BooleanValue obj) {
        return Boolean.valueOf(obj.token.lexeme);
    }

    @Override
    public Object visitNullValueObj(NullValue obj) {
        return null;
    }

    @Override
    public Object visitObjectStartObj(ObjectStart obj) {
        return null;
    }

    @Override
    public Object visitObjectEndObj(ObjectEnd obj) {
        return null;
    }

    @Override
    public Object visitArrayStartObj(ArrayStart obj) {
        return null;
    }

    @Override
    public Object visitArrayEndObj(ArrayEnd obj) {
        return null;
    }

    @Override
    public Object visitObjectObj(axcavo.sjp.Obj.Object obj) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (Obj.Pair pair : obj.pairs) {
            String key = (String) pair.key.accept(this);
            Object value = pair.value.accept(this);
            map.put(key, value);
        }
        return map;
    }

    @Override
    public Object visitArrayObj(Array obj) {
        List<Object> list = new ArrayList<>();
        for (Obj.Value value : obj.values) {
            list.add(value.accept(this));
        }
        return list;
    }

    @Override
    public Object visitValueObj(Obj.Value obj) {
        Obj current = obj.value;
        while (current instanceof Obj.Value) {
            current = ((Obj.Value) current).value;
        }
        return current.accept(this);
    }


    @Override
    public Object visitpairObj(Pair obj) {
        throw new UnsupportedOperationException("Pairs should be handled in visitObjectObj");
    }

    @Override
    public Object visitKeySeparatorObj(KeySeparator obj) {
        return null;
    }

    @Override
    public Object visitValueSeparatorObj(ValueSeparator obj) {
        return null;
    }

        
}
