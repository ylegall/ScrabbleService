package org.ygl;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class ScrabbleService extends AbstractHandler {

    private static final Pattern PATH_REGEX = Pattern.compile("/words/([a-zA-Z]+)");

    private ScrabbleIndex index;
    private ArraySerializer serializer = new ArraySerializer();

    private ScrabbleService(ScrabbleIndex index) throws Exception {
        this.index = index;
    }

    /**
     * parses the path query param
     * @param path a String matching "/words/{param}"
     * @return empty if the path is invalid
     */
    static String parsePath(String path) {
        Matcher matcher = PATH_REGEX.matcher(path);
        if (matcher.matches()) {
            return matcher.group(1).toLowerCase();
        }
        return "";
    }

    @Override
    public void handle(
            String path,
            Request baseRequest,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        String letters = parsePath(path);
        if (letters.isEmpty()) return;

        try {
            long elapsed = System.nanoTime();

            List<String> matches = index.getMatchingWords(letters);
            String jsonResult = serializer.toJsonArray(matches);
            response.getWriter().write(jsonResult);

            elapsed = System.nanoTime() - elapsed;
            System.out.println(jsonResult);
            System.out.println("elapsed ms: " + (elapsed / 1000000.0));
            System.out.println();

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().write(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            baseRequest.setHandled(true);
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        server.setHandler(new ScrabbleService(new ScanningScrabbleIndex("wordsEn.txt")));

        server.start();
        server.join();
    }
}
