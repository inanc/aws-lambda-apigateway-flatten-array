package example;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class LambdaRequestHandlerTest {

    private LambdaRequestHandler lambdaRequestHandlerUnderTest;

    @Before
    public void setUp() {
        lambdaRequestHandlerUnderTest = new LambdaRequestHandler();
    }

    @Test
    public void testHandleRequest() throws Exception {
        String requestJson = "{\n" +
                "    \"headers\": {\n" +
                "        \"accept\": \"*/*\",\n" +
                "        \"accept-encoding\": \"gzip, deflate, br\",\n" +
                "        \"content-length\": \"38\",\n" +
                "        \"content-type\": \"text/plain\"\n" +
                "    },\n" +
                "    \"isBase64Encoded\": false,\n" +
                "    \"rawPath\": \"/array\",\n" +
                "    \"routeKey\": \"POST /array\",\n" +
                "    \"requestContext\": {\n" +
                "        \"http\": {\n" +
                "            \"method\": \"POST\",\n" +
                "            \"path\": \"/array\",\n" +
                "            \"protocol\": \"HTTP/1.1\",\n" +
                "            \"userAgent\": \"PostmanRuntime/7.28.4\"\n" +
                "        },\n" +
                "        \"routeKey\": \"POST /array\"\n" +
                "    },\n" +
                "    \"body\": \"{\\\"input\\\":[1,[2,3,[4]],[5],6,[7,[8]]]} \",\n" +
                "    \"version\": \"2.0\",\n" +
                "    \"rawQueryString\": \"\"\n" +
                "}";
        final InputStream inputStream = new ByteArrayInputStream(requestJson.getBytes());
        final OutputStream outputStream = new ByteArrayOutputStream();
        final Context context = new TestContext();

        lambdaRequestHandlerUnderTest.handleRequest(inputStream, outputStream, context);

        Assert.assertEquals("{\"output\":[1,2,3,4,5,6,7,8]}", outputStream.toString());
    }

    @Test
    public void testHandleRequest_EmptyInputStream() throws Exception {
        final InputStream inputStream = new ByteArrayInputStream("{}".getBytes());
        final OutputStream outputStream = new ByteArrayOutputStream();
        final Context context = new TestContext();

        lambdaRequestHandlerUnderTest.handleRequest(inputStream, outputStream, context);
        Assert.assertEquals("{}", outputStream.toString());
    }

    @Test(expected = IOException.class)
    public void testHandleRequest_BrokenInputStream() throws Exception {
        final InputStream inputStream = new InputStream() {
            private final IOException exception = new IOException("Error");

            @Override
            public int read() throws IOException {
                throw exception;
            }

            @Override
            public int available() throws IOException {
                throw exception;
            }

            @Override
            public long skip(final long n) throws IOException {
                throw exception;
            }

            @Override
            public synchronized void reset() throws IOException {
                throw exception;
            }

            @Override
            public void close() throws IOException {
                throw exception;
            }
        };
        final OutputStream outputStream = new ByteArrayOutputStream();
        final Context context = new TestContext();

        lambdaRequestHandlerUnderTest.handleRequest(inputStream, outputStream, context);
    }

}
