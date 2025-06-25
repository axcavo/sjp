package axcavo.sjp;

import java.util.Map;

/**
 * <p><strong>SimpleJsonParser</strong>—or <em>sjp</em> for short—is the main (and only public) class of this API.</p>
 * 
 * <p>Want to see explore the project repository? Check out the project on GitHub:<br>
 * <a href="https://github.com/axcavo/sjp">https://github.com/axcavo/sjp</a></p>
 * 
 * @author axcavo
 */
public class SimpleJsonParser {

    /**
     * <p>This is what it's all about—the <code>parse</code> method!</p>
     * <p>So, what does it do? Let’s break it down into three simple steps:</p>
     * 
     * <ol>
     *  <li><strong>Scanning:</strong> We scan the input and break it down into small, meaningful pieces called <em>tokens</em>.</li>
     *  <li><strong>AST:</strong> Those tokens get turned into an abstract syntax tree—basically a bunch of linked syntax rules.</li>
     *  <li><strong>Mapping:</strong> Finally, we walk the tree and map everything into the output <code>Map</code>.</li>
     * </ol>
     * 
     * @param data the string that's supposed to contain json formatted data.
     * @return A classic {@link java.util.Map} representing the parsed JSON.
     */
    public Map<String, Object> parse(String data) {
        Scanner scanner = new Scanner(data);
        TreeMapper parser = new TreeMapper(scanner.scanTokens());
        return parser.map();
    }
}
