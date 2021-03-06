package grabber;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class ParseDateTest {
    @Test
    public void parsingDateBase() {
        ParseDate date = new ParseDate("25 апр 15, 13:27");
        LocalDateTime dateour = date.parseMethod();
        assertThat(dateour.getYear(), is(2015));
        assertThat(dateour.getMonthValue(), is(4));
        assertThat(dateour.getDayOfMonth(), is(25));
        assertThat(dateour.getHour(), is(13));
        assertThat(dateour.getMinute(), is(27));
    }

    @Test@Ignore
    public void parsingDateToday() {
        ParseDate date = new ParseDate("сегодня, 05:02");
        LocalDateTime dateour = date.parseMethod();
        assertThat(dateour.getYear(), is(2020));
        assertThat(dateour.getMonthValue(), is(10));
        assertThat(dateour.getDayOfMonth(), is(25));
        assertThat(dateour.getHour(), is(5));
        assertThat(dateour.getMinute(), is(2));
    }

    @Test@Ignore
    public void parsingDateYesterday() {
        ParseDate date = new ParseDate("вчера, 11:05");
        LocalDateTime dateour = date.parseMethod();
        assertThat(dateour.getYear(), is(2020));
        assertThat(dateour.getMonthValue(), is(10));
        assertThat(dateour.getDayOfMonth(), is(24));
        assertThat(dateour.getHour(), is(11));
        assertThat(dateour.getMinute(), is(5));
    }

    @Test
    public void parsingDateDateOther() {
        ParseDate date = new ParseDate("31 дек 19, 15:41");
        LocalDateTime dateour = date.parseMethod();
        assertThat(dateour.getYear(), is(2019));
        assertThat(dateour.getMonthValue(), is(12));
        assertThat(dateour.getDayOfMonth(), is(31));
        assertThat(dateour.getHour(), is(15));
        assertThat(dateour.getMinute(), is(41));
    }

    @Test(expected = DateTimeException.class)
    public void incorrectData() {
        ParseDate date = new ParseDate("45 дек 19, 25:41");
        LocalDateTime dateour = date.parseMethod();
        System.out.println(dateour);
    }
}