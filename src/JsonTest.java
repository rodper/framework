import br.eti.rodper.json.JsonArray;
import br.eti.rodper.json.JsonObject;
import br.eti.rodper.json.JsonParser;
import br.eti.rodper.json.JsonValue;


public class JsonTest {

	public static void main(String[] args) {

		// ------------------------------------ //
		// ------ Parsing a json content ------ //
		// ------------------------------------ //

		String json = "{\"widget\": {"
					+ "    \"debug\": \"on\","
					+ "    \"window\": {"
					+ "        \"title\": \"Sample Konfabulator Widget\","
					+ "        \"name\": \"main_window\","
					+ "        \"width\": 500.52,"
					+ "        \"height\": 300"
					+ "    },"
					+ "    \"image\": { "
					+ "        \"src\": \"Images/Sun.png\","
					+ "        \"name\": \"sun1\","
					+ "        \"hOffset\": 250,"
					+ "        \"vOffset\": 250,"
					+ "        \"alignment\": \"center\""
					+ "    },"
					+ "    \"text\": {"
					+ "        \"data\": \"Click Here\","
					+ "        \"size\": 36,"
					+ "        \"style\": [\"bold\", \"underscore\"],"
					+ "        \"name\": \"text1\","
					+ "        \"hOffset\": 250,"
					+ "        \"vOffset\": 100,"
					+ "        \"alignment\": \"center\","
					+ "        \"onMouseUp\": \"sun1.opacity = (sun1.opacity / 100) * 90;\""
					+ "    }"
					+ "}}";

		JsonObject object = JsonParser.parseObject(json);
		JsonObject widget = object.get("widget");

		String debug = widget.get("debug");
		System.out.println(debug);

		JsonObject window = widget.get("window");
		Number width = window.get("width");
		Number height = window.get("height");
		System.out.println(width + ":" + height);

		JsonObject text = widget.get("text");
		System.out.println(text.get("onMouseUp"));

		JsonArray style = text.get("style");
		System.out.println(style.get(0));

		// ------------------------------------ //
		// ------ Building a json object ------ //
		// ------------------------------------ //

		JsonObject request = new JsonObject()
			.put("widget", new JsonObject()
				.put("debug", new JsonValue("on"))
				.put("window",  new JsonObject()
					.put("width", new JsonValue(500))
					.put("height", new JsonValue(300))
				)
				.put("text", new JsonObject()
					.put("onMouseUp", new JsonValue("sun1.opacity = (sun1.opacity / 100) * 90;"))
					.put("style", new JsonArray()
						.add(new JsonValue("bold"))
						.add(new JsonValue("underscore"))
					)
				)
			);

		System.out.println(request);
	}
}
