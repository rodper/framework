package br.eti.rodper.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Developed based on http://www.json.org/
 *
 * @author rodper
 *
 */
public class JsonObject implements Iterable<Entry<String, JsonValue>> {

	private  Map<String, JsonValue> members;

	public JsonObject() {
		this.members = new HashMap<>() ;
	}

	@SafeVarargs
	@SuppressWarnings("unchecked")
	public final <T> T get(String name, Class<T>... type) {
		return (T) members.get(name).getValue();
	}

	public JsonObject put(String name, JsonValue value) {
		members.put(name, value);
		return this;
	}

	public JsonObject put(String name, JsonObject object) {
		put(name, new JsonValue(object));
		return this;
	}

	public JsonObject put(String name, JsonArray object) {
		put(name, new JsonValue(object));
		return this;
	}

	public boolean isEmpty() {
		return members.isEmpty();
	}

	public int size() {
		return members.size();
	}

	@Override
	public Iterator<Entry<String, JsonValue>> iterator() {
		return members.entrySet().iterator();
	}

	@Override
	public String toString() {

		StringBuilder values = new StringBuilder("{");

		for(Entry<String, JsonValue> entry : members.entrySet()) {
			values.append("\"").append(entry.getKey()).append("\":").append(entry.getValue());
			values.append(",");
		}

		if(values.length() > 1) {
			values.deleteCharAt(values.length() - 1);
		}

		return values.append("}").toString();
	}
}
