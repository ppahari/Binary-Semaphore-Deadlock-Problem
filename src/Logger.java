import java.io.FileWriter;
import java.io.IOException;

/**
 * Logger class is used to write any output in a text file.
 */
public class Logger {
    private static final String OUTPUT_FILE_NAME = "logs.txt";
    private FileWriter writer;

    /**
     * Default constructor.
     */
    public Logger() {
        initFileWriter();
    }

    /**
     * Initialize FileWriter instance with the OUTPUT_FILE_NAME constant.
     * Handle IOException.
     */
    private void initFileWriter() {
        try {
            writer = new FileWriter(OUTPUT_FILE_NAME);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write a String line into the text file.
     * Handle IOException via try/catch.
     * @param line is a line to write.
     */
    public void writeLine(String line) {
        try {
            System.out.println(line);
            writer.write(line);
            writer.write(System.getProperty( "line.separator" ));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the print writer stream.
     * Handle the IOException.
     */
    public void flush() {
        try {
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
