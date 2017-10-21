import java.time.LocalDate;

public class DateCounter {
    public static boolean isLeapYear(int year) {
        if(year % 4 == 0) {
            if(year % 100 == 0) {
                if(year % 400 == 0) {
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static int daysInMonth(int year, int month) {
        int days = 0;
        switch (month) {
            case 1: days = 31;
                    break;
            case 2: if(isLeapYear(year)) {
                        days = 29;
                    } else {
                        days = 28;
                    }
                    break;
            case 3: days = 31;
                    break;
            case 4: days = 30;
                    break;
            case 5: days = 31;
                    break;
            case 6: days = 30;
                    break;
            case 7: days = 31;
                    break;
            case 8: days = 31;
                    break;
            case 9: days = 30;
                    break;
            case 10: days = 31;
                    break;
            case 11: days = 30;
                    break;
            case 12: days = 31;
                    break;
        }
        return days;
    }

    public static boolean isValidDate(int year, int month, int day) {
        int daysOfMonth = daysInMonth(year, month);
        if(year <= 0) {
            return false;
        } else if (month < 1 || month > 12) {
            return false;
        } else if(day < 1 || day > daysOfMonth) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean datesInOrder(int year0, int month0, int day0, int year1, int month1, int day1) {
        if(year0 == year1) {
            if(month0 == month1) {
                if(day0 == day1) {
                    return true;
                } else {
                    if(day0 < day1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                if(month0 < month1) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if(year0 < year1) {
                return true;
            } else {
                return false;
            }
        }
    }

    // Incomplete
    public static int daysBetween(int year0, int month0, int day0, int year1, int month1, int day1) {
        boolean counting = true;
        int currentYear = 0;
        int currentMonth = 0;
        int currentDay = 0;
        int endYear = 0;
        int endMonth = 0;
        int endDay = 0;
        int days = 0;
        if(datesInOrder(year0, month0, day0, year1, month1, day1)) {
            currentYear = year0;
            currentMonth = month0;
            currentDay = day0;
            endYear = year1;
            endMonth = month1;
            endDay = day1;
        } else {
            currentYear = year1;
            currentMonth = month1;
            currentDay = day1;
            endYear = year0;
            endMonth = month0;
            endDay = day0;
        }
        while(counting) {
            boolean sameYear = (currentYear == endYear);
            boolean sameMonth = (currentMonth == endMonth);
            boolean sameDay = (currentDay == endDay);
            if(sameYear && sameMonth && sameDay) {
                counting = false;
                break;
            }
            if(isValidDate(currentYear, currentMonth, currentDay)) {
                currentDay++;
                days++;
            } else {
                currentDay = 1;
                currentMonth++;
                if(!isValidDate(currentYear, currentMonth, currentDay)) {
                    currentMonth = 1;
                    currentYear++;
                    }
                }
            }
        return days;
    }

    public static int ageInDays(int birthyear, int birthmonth, int birthday) {
        int[] todayArray = today();
        int currentYear = todayArray[0];
        int currentMonth = todayArray[1];
        int currentDay = todayArray[2];
        return daysBetween(birthyear, birthmonth, birthday, currentYear, currentMonth, currentDay);
    }

    public static void main(String[] args) {
        boolean validArguments = true;
        boolean noErrors = true;
        int[] datesArray = new int[args.length];
        try {
            for(int x = 0; x < args.length; x++) {
                datesArray[x] = Integer.parseInt(args[x]);
            }
        } catch (NumberFormatException e) { // NumberFormatException
            System.out.println("One or more of the supplied dates is not valid.");
            noErrors = false;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java DateCounter <one or two dates in year-month-day order>");
            noErrors = false;
        }

        if(noErrors) {
            for(int x = 0; x < args.length; x++) {
                datesArray[x] = Integer.parseInt(args[x]);
            }
            for(int x = 0; x < args.length; x++) {
                if(args[x] == "") {
                    validArguments = false;
                } else if((datesArray.length == 3) || (datesArray.length == 6)) {
                    if(!isValidDate(datesArray[0], datesArray[1], datesArray[2])) {
                        validArguments = false;
                    }
                } else if(datesArray.length == 6) {
                    if((!isValidDate(datesArray[0], datesArray[1], datesArray[2])) || (!isValidDate(datesArray[3], datesArray[4], datesArray[5]))) {
                        validArguments = false;
                    }
                }
            }
            if(datesArray.length < 1) {
                System.out.println("Usage: java DateCounter <one or two dates in year-month-day order>");
            } else if(!validArguments) {
                System.out.println("One or more of the supplied dates is not valid.");
            } else if ((datesArray.length != 3) && (datesArray.length != 6)) {
                System.out.println("You must have either 3 or 6 arguments for function to work properly.");
            } else if(datesArray.length == 3) {
                System.out.println("You are " + ageInDays(datesArray[0], datesArray[1], datesArray[2]) + " days old");
            } else if(datesArray.length == 6) {
                System.out.println(daysBetween(datesArray[0], datesArray[1], datesArray[2], datesArray[3], datesArray[4], datesArray[5]) + " days");
            }
        }
    }

    /**
     * Returns today's date as a three-element integer array in year-month-day order.
     */
     public static int[] today() {
         LocalDate now = LocalDate.now();
         return new int[] {
             now.getYear(), now.getMonth().getValue(), now.getDayOfMonth()
         };
     }

}
