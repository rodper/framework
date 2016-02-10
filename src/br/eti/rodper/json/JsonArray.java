package br.eti.rodper.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Developed based on http://www.json.org/
 *
 * @author rodper
 *
 */
public class JsonArray implements Iterable<JsonValue>{

	private List<JsonValue> elements;

	public JsonArray() {
		elements = new ArrayList<>();
	}

	@SafeVarargs
	@SuppressWarnings("unchecked")
	public final <T> T get(int index, Class<T>... type) {
		return (T) elements.get(index).getValue();
	}

	public JsonArray add(JsonValue value) {
		elements.add(value);
		return this;
	}

	public JsonArray add(JsonObject object) {
		add(new JsonValue(object));
		return this;
	}

	public JsonArray add(JsonArray array) {
		add(new JsonValue(array));
		return this;
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public int size() {
		return elements.size();
	}

	@Override
	public Iterator<JsonValue> iterator() {
		return elements.iterator();
	}

	@Override
	public String toString() {

		StringBuilder values = new StringBuilder("[");

		for(JsonValue element : elements) {
			values.append(element).append(",");
		}

		if(values.length() > 1) {
			values.deleteCharAt(values.length() - 1);
		}

		return values.append("]").toString();
	}
}
