package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LambdaRequestHandler implements RequestStreamHandler {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.US_ASCII));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.US_ASCII)));
        try {
            JsonObject requestObject = gson.fromJson(reader, JsonObject.class).getAsJsonObject();

            JSONObject responseJson = new JSONObject();

            if (requestObject.get("body") != null) {
                String body = requestObject.get("body").toString();
                String numbers = body.substring(body.lastIndexOf(":") + 1).replaceAll("[\"\\[\\]}]", "");

                responseJson.put("output", new JSONArray("[" + numbers + "]"));
            }

            writer.write(responseJson.toString());

            if (writer.checkError()) {
                logger.log("WARNING: Writer encountered an error.");
            }
        } catch (IllegalStateException | JsonSyntaxException exception) {
            logger.log(exception.toString());
        } finally {
            reader.close();
            writer.close();
        }
    }
}