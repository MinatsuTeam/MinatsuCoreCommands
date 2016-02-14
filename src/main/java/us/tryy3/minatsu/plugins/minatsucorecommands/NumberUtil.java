package us.tryy3.minatsu.plugins.minatsucorecommands;

/**
 * Created by tryy3 on 2016-02-14.
 */
public class NumberUtil {
    public static boolean isInt(final String sInt)
    {
        try
        {
            Integer.parseInt(sInt);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }
}
