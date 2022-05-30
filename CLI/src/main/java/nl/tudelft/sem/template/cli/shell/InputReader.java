package nl.tudelft.sem.template.cli.shell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jline.reader.LineReader;
import org.springframework.util.StringUtils;

/**
 * Input reader
 * Taken from https://github.com/dmadunic/clidemo
 */
public class InputReader {

    public static final Character DEFAULT_MASK = '*';

    private transient Character mask;

    private transient LineReader lineReader;

    private transient ShellHelper shellHelper;

    public InputReader(LineReader lineReader, ShellHelper shellHelper) {
        this(lineReader, shellHelper, null);
    }

    /**
     * reads an input.
     *
     * @param lineReader line reader.
     * @param shellHelper shell helper.
     * @param mask mask.
     */
    public InputReader(LineReader lineReader, ShellHelper shellHelper, Character mask) {
        this.lineReader = lineReader;
        this.shellHelper = shellHelper;
        this.mask = mask != null ? mask : DEFAULT_MASK;
    }

    public String prompt(String prompt) {
        return prompt(prompt, null, true);
    }

    public String prompt(String prompt, String defaultValue) {
        return prompt(prompt, defaultValue, true);
    }

    /**
     * Prompts user for input.
     *
     * @param prompt       prompt.
     * @param defaultValue default value.
     * @param echo         if it should echo.
     * @return answer to prompt.
     */
    public String prompt(String prompt, String defaultValue, boolean echo) {
        String answer;

        if (echo) {
            answer = lineReader.readLine(prompt + ": ");
        } else {
            answer = lineReader.readLine(prompt + ": ", mask);
        }
        if (StringUtils.isEmpty(answer)) {
            return defaultValue;
        }
        return answer;
    }

    /**
     * Loops until one of the `options` is provided.
     * Pressing return is equivalent to: `defaultValue`.
     * <br/>
     * Passing null for defaultValue signifies that there is no default value. <br/>
     * Passing "" or null among optionsAsList means that empty answer is allowed,
     * in these cases this method returns
     * empty String "" as the result of its execution.
     */
    public String promptWithOptions(String prompt, String defaultValue,
                                    List<String> optionsAsList) {
        String answer;
        List<String> allowedAnswers = new ArrayList<>(optionsAsList);
        if (StringUtils.hasText(defaultValue)) {
            allowedAnswers.add("");
        }
        do {
            answer = lineReader
                    .readLine(String.format("%s %s: ", prompt,
                            formatOptions(defaultValue, optionsAsList)));
        } while (!allowedAnswers.contains(answer) && !"".equals(answer));

        if (StringUtils.isEmpty(answer) && allowedAnswers.contains("")) {
            return defaultValue;
        }
        return answer;
    }

    private List<String> formatOptions(String defaultValue, List<String> optionsAsList) {
        String val;
        List<String> result = new ArrayList();
        for (String option : optionsAsList) {
            String emptyString = "";
            if (emptyString.equals(option) || option == null) {
                val = "''";
            } else {
                val = option;
            }
            if (defaultValue != null) {
                if (defaultValue.equals(option) || (defaultValue.equals("") && option == null)) {
                    val = shellHelper.getInfoMessage(val);
                }
            }
            result.add(val);
        }
        return result;
    }

    /**
     * Loops until one value from the list of options is selected,
     * printing each option on its own line.
     */
    public String selectFromList(String headingMessage, String promptMessage,
                                 Map<String, String> options, boolean ignoreCase,
                                 String defaultValue) {
        String answer;
        String emptyString = "";
        Set<String> allowedAnswers = new HashSet<>(options.keySet());
        if (defaultValue != null && !defaultValue.equals("")) {
            allowedAnswers.add("");
        }
        shellHelper.print(String.format("%s: ", headingMessage));
        do {
            for (Map.Entry<String, String> option : options.entrySet()) {
                if (defaultValue != null) {
                    if (option.getKey()
                            .equals(defaultValue)) {
                        String defaultMarker = "*";
                        shellHelper
                                .printInfo(String.format("%s [%s] %s ", defaultMarker,
                                        option.getKey(), option.getValue()));
                    }
                } else {
                    shellHelper
                            .print(String.format("  [%s] %s", option.getKey(), option.getValue()));
                }
            }
            answer = lineReader
                    .readLine(String.format("%s: ", promptMessage));
        } while (!containsString(allowedAnswers, answer, ignoreCase)
                && !emptyString.equals(answer));

        if (StringUtils.isEmpty(answer) && allowedAnswers.contains("")) {
            return defaultValue;
        }
        return answer;
    }

    private boolean containsString(Set<String> l, String s, boolean ignoreCase) {
        if (!ignoreCase) {
            return l.contains(s);
        }
        Iterator<String> it = l.iterator();
        while (it.hasNext()) {
            if (it.next().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

}