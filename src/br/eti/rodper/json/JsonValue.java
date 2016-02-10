package br.eti.rodper.json;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Developed based on http://www.json.org/
 *
 * @author rodper
 *
 */
public class JsonValue {

	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.US);
	{
		NUMBER_FORMAT.setMaximumFractionDigits(340);
		NUMBER_FORMAT.setGroupingUsed(false);
	}

	private Object value;

	public JsonValue() {
		this.value = null;
	}

	public JsonValue(JsonObject object) {
		this.value = object;
	}

	public JsonValue(JsonArray array) {
		this.value = array;
	}

	public JsonValue(boolean bool) {
		this.value = bool;
	}

	public JsonValue(Number number) {
		this.value = number;
	}

	public JsonValue(String string) {
		this.value = string;
	}

	@SafeVarargs
	@SuppressWarnings("unchecked")
	public final <T> T getValue(Class<T>... type) {
		return (T) value;
	}

	@Override
	public String toString() {

		if (value == null) {
			return "null";
		}
		if (value instanceof String) {
			return "\"" + value + "\"";
		}
		if (value instanceof Number) {
			return NUMBER_FORMAT.format(value);
		}

		return value.toString();
	}
}
