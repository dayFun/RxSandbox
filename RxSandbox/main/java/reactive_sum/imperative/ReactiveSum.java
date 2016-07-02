package reactive_sum.imperative;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReactiveSum {

    private static final String PARSE_INPUT_REGEX = "(?<variable>[a|b]):\\s(?<value>\\d+)";
    private static final String QUIT_FLAG = "quit";
    private static final String SUM_MESSAGE = "Sum: ";
    private static final String ERROR_MESSAGE = "Error: Invalid input";
    private final ReactiveSumModel reactiveSumModel = new ReactiveSumModel();




    public boolean parseInput(IoWrapper ioWrapper) {
        String input = ioWrapper.getInput();

        if (input.equalsIgnoreCase(QUIT_FLAG)) {
            return false;
        }

        Matcher matcher = Pattern.compile(PARSE_INPUT_REGEX).matcher(input);
        if (matcher.matches()) {
            String variable = matcher.group("variable");
            String value = matcher.group("value");
            setValue(variable, value);
            if (reactiveSumModel.isSumUpdated()) {
                ioWrapper.printMessage(SUM_MESSAGE + reactiveSumModel.getSum());
            }
        } else {
            ioWrapper.printMessage(ERROR_MESSAGE);
        }

        return true;
    }

    private void setValue(String inputVariable, String value) {
        int intValue = Integer.valueOf(value);

        if(inputVariable.equals("a")) {
            reactiveSumModel.setA(intValue);
        } else if(inputVariable.equals("b")) {
            reactiveSumModel.setB(intValue);
        }
    }

    public ReactiveSumModel getReactiveSumModel() {
        return reactiveSumModel;
    }
}
