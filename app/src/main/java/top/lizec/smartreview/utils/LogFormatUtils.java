package top.lizec.smartreview.utils;

public class LogFormatUtils {

    public static String argToString(Object[] args) {
        if(args == null || args.length == 0) {
            return "";
        } else {
            int iMax = args.length - 1;
            StringBuilder b = new StringBuilder();
            for (int i = 0; ; i++) {
                b.append(args[i]);
                if (i == iMax)
                    return b.toString();
                b.append(", ");
            }
        }
    }
}
