package uk.ac.aber.cs31620.abercon2019.model.util;

import java.util.Comparator;
import java.util.Date;

/**
 * DateComparator.java - A comparator used to compare two dates. The dates are sorted in
 * descending order so as to facilitate a Stack.pop() method call.
 * <p>
 * Code has been adapted from:
 * <a href="https://tech.chitgoks.com/2009/09/25/java-sort-date-using-comparator/></a>
 *
 * @author Michael Male
 * @version 1.0  PRODUCTION
 */
public class DateComparator implements Comparator<Date> {
    @Override
    public int compare(Date s1, Date s2) {
        long n1 = s1.getTime();
        long n2 = s2.getTime();
        if (n1 < n2) {
            return 1;
        } else if (n1 > n2) {
            return -1;
        } else {
            return 0;
        }
    }
}
