package com.github.framework.evo.common.uitl;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * User: Kyll
 * Date: 2018-03-09 14:54
 */
@Slf4j
public class DateUtil {
	private static final String PATTERN_DATE = "yyyy-MM-dd";
	private static final String PATTERN_DATE_COMPACT = "yyyyMMdd";
	private static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	private static final String PATTERN_DATE_TIME_COMPACT = "yyyyMMddHHmmss";
	private static final String PATTERN_YEAR_MONTH = "yyyy-MM";
	private static final String PATTERN_YEAR_MONTH_COMPACT = "yyyyMM";
	private static final String PATTERN_TIME = "HH:mm:ss";
	private static final String PATTERN_TIME_COMPACT = "HHmmss";

	// Date 传统用法 START
	@Deprecated
	public static Date now() {
		return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
	}

	@Deprecated
	public static Date parseDate(String str) {
		return Date.from(LocalDate.parse(str, DateTimeFormatter.ofPattern(PATTERN_DATE)).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@Deprecated
	public static Date parseDateCompact(String str) {
		return Date.from(LocalDate.parse(str, DateTimeFormatter.ofPattern(PATTERN_DATE_COMPACT)).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@Deprecated
	public static Date parseDatetime(String str) {
		return parse(str, PATTERN_DATE_TIME);
	}

	@Deprecated
	public static Date parseDatetimeCompact(String str) {
		return parse(str, PATTERN_DATE_TIME_COMPACT);
	}

	@Deprecated
	public static Date parse(String str, String pattern) {
		return Date.from(LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern)).atZone(ZoneId.systemDefault()).toInstant());
	}

	@Deprecated
	public static String formatDate(Date date) {
		return format(date, PATTERN_DATE);
	}

	@Deprecated
	public static String formatDateCompact(Date date) {
		return format(date, PATTERN_DATE_COMPACT);
	}

	@Deprecated
	public static String formatDatetime(Date date) {
		return format(date, PATTERN_DATE_TIME);
	}

	@Deprecated
	public static String formatDatetimeCompact(Date date) {
		return format(date, PATTERN_DATE_TIME_COMPACT);
	}

	@Deprecated
	public static String format(Date date, String pattern) {
		return date == null ? null : DateTimeFormatter.ofPattern(pattern).format(LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault()));
	}

	@Deprecated
	public static Date removeHMS(Date date) {
		return parseDate(formatDate(date));
	}

	@Deprecated
	public static Date plusNanos(Date date, long nanos) {
		return toDate(toLocalDateTime(date).plusNanos(nanos));
	}

	@Deprecated
	public static Date plusSeconds(Date date, long seconds) {
		return toDate(toLocalDateTime(date).plusSeconds(seconds));
	}

	@Deprecated
	public static Date plusMinutes(Date date, long minutes) {
		return toDate(toLocalDateTime(date).plusMinutes(minutes));
	}

	@Deprecated
	public static Date plusHours(Date date, long hours) {
		return toDate(toLocalDateTime(date).plusHours(hours));
	}

	@Deprecated
	public static Date plusDays(Date date, long days) {
		return toDate(toLocalDateTime(date).plusDays(days));
	}

	@Deprecated
	public static Date plusWeeks(Date date, long weeks) {
		return toDate(toLocalDateTime(date).plusWeeks(weeks));
	}

	@Deprecated
	public static Date plusMonths(Date date, long months) {
		return toDate(toLocalDateTime(date).plusMonths(months));
	}

	@Deprecated
	public static Date plusYears(Date date, long years) {
		return toDate(toLocalDateTime(date).plusYears(years));
	}

	@Deprecated
	public static Date minusNanos(Date date, long nanos) {
		return toDate(toLocalDateTime(date).minusNanos(nanos));
	}

	@Deprecated
	public static Date minusSeconds(Date date, long seconds) {
		return toDate(toLocalDateTime(date).minusSeconds(seconds));
	}

	@Deprecated
	public static Date minusMinutes(Date date, long minutes) {
		return toDate(toLocalDateTime(date).minusMinutes(minutes));
	}

	@Deprecated
	public static Date minusHours(Date date, long hours) {
		return toDate(toLocalDateTime(date).minusHours(hours));
	}

	@Deprecated
	public static Date minusDays(Date date, long days) {
		return toDate(toLocalDateTime(date).minusDays(days));
	}

	@Deprecated
	public static Date minusWeeks(Date date, long weeks) {
		return toDate(toLocalDateTime(date).minusWeeks(weeks));
	}

	@Deprecated
	public static Date minusMonths(Date date, long months) {
		return toDate(toLocalDateTime(date).minusMonths(months));
	}

	@Deprecated
	public static Date minusYears(Date date, long years) {
		return toDate(toLocalDateTime(date).minusYears(years));
	}
	// Date 传统用法 END

	// JDK8 新用法 START
	public static LocalDate nowLocalDate() {
		return LocalDate.now();
	}

	public static LocalDateTime nowLocalDateTime() {
		return LocalDateTime.now();
	}

	public static LocalTime nowLocalTime() {
		return LocalTime.now();
	}

	public static YearMonth nowYearMonth() {
		return YearMonth.now();
	}

	public static LocalDate parseLocalDate(String str) {
		return parseLocalDate(str, PATTERN_DATE);
	}

	public static LocalDate parseLocalDateCompact(String str) {
		return parseLocalDate(str, PATTERN_DATE_TIME_COMPACT);
	}

	public static LocalDateTime parseLocalDateTime(String str) {
		return parseLocalDateTime(str, PATTERN_DATE_TIME);
	}

	public static LocalDateTime parseLocalDateTimeCompact(String str) {
		return parseLocalDateTime(str, PATTERN_DATE_TIME_COMPACT);
	}

	public static LocalTime parseLocalTime(String str) {
		return parseLocalTime(str, PATTERN_TIME);
	}

	public static LocalTime parseLocalTimeCompact(String str) {
		return parseLocalTime(str, PATTERN_TIME_COMPACT);
	}

	public static YearMonth parseYearMonth(String str) {
		return parseYearMonth(str, PATTERN_YEAR_MONTH);
	}

	public static YearMonth parseYearMonthCompact(String str) {
		return parseYearMonth(str, PATTERN_YEAR_MONTH_COMPACT);
	}

	public static LocalDate parseLocalDate(String str, String pattern) {
		return LocalDate.parse(str, DateTimeFormatter.ofPattern(pattern));
	}

	public static LocalDateTime parseLocalDateTime(String str, String pattern) {
		return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern));
	}

	public static LocalTime parseLocalTime(String str, String pattern) {
		return LocalTime.parse(str, DateTimeFormatter.ofPattern(pattern));
	}

	public static YearMonth parseYearMonth(String str, String pattern) {
		return YearMonth.parse(str, DateTimeFormatter.ofPattern(pattern));
	}

	public static String formatLocalDate(LocalDate localDate) {
		return format(localDate, PATTERN_DATE);
	}

	public static String formatLocalDate(Date date) {
		return format(toLocalDate(date), PATTERN_DATE);
	}

	public static String formatLocalDateCompact(LocalDate localDate) {
		return format(localDate, PATTERN_DATE_COMPACT);
	}

	public static String formatLocalDateCompact(Date date) {
		return format(toLocalDate(date), PATTERN_DATE_COMPACT);
	}

	public static String formatLocalDateTime(LocalDateTime localDateTime) {
		return format(localDateTime, PATTERN_DATE_TIME);
	}

	public static String formatLocalDateTime(Date date) {
		return format(toLocalDateTime(date), PATTERN_DATE_TIME);
	}

	public static String formatLocalDateTimeCompact(LocalDateTime localDateTime) {
		return format(localDateTime, PATTERN_DATE_TIME_COMPACT);
	}

	public static String formatLocalDateTimeCompact(Date date) {
		return format(toLocalDateTime(date), PATTERN_DATE_TIME_COMPACT);
	}

	public static String formatLocalTime(LocalTime localTime) {
		return format(localTime, PATTERN_TIME);
	}

	public static String formatLocalTime(Date date) {
		return format(toLocalTime(date), PATTERN_TIME);
	}

	public static String formatLocalTimeCompact(LocalTime localTime) {
		return format(localTime, PATTERN_TIME_COMPACT);
	}

	public static String formatLocalTimeCompact(Date date) {
		return format(toLocalTime(date), PATTERN_TIME_COMPACT);
	}

	public static String formatYearMonth(YearMonth yearMonth) {
		return format(yearMonth, PATTERN_YEAR_MONTH);
	}

	public static String formatYearMonthCompact(YearMonth yearMonth) {
		return format(yearMonth, PATTERN_YEAR_MONTH_COMPACT);
	}

	public static String format(TemporalAccessor temporal, String pattern) {
		return temporal == null ? null : DateTimeFormatter.ofPattern(pattern).format(temporal);
	}

	public static LocalDate toLocalDate(int year, int month, int dayOfMonth) {
		return LocalDate.of(year, month, dayOfMonth);
	}

	public static LocalDate toLocalDate(long millis) {
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDate toLocalDate(YearMonth yearMonth) {
		return toLocalDate(yearMonth, 1);
	}

	public static LocalDate toLocalDate(YearMonth yearMonth, int dayOfMonth) {
		return dayOfMonth > 0 ? yearMonth.atDay(dayOfMonth) : yearMonth.atEndOfMonth();
	}

	public static LocalDate toLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime toLocalDateTime(long millis) {
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static LocalDateTime toLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static LocalTime toLocalTime(long millis) {
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalTime();
	}

	public static LocalTime toLocalTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
	}

	public static Date toDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date toDate(LocalTime localTime) {
		return Date.from(localTime.atDate(LocalDate.of(1970, 1, 1)).atZone(ZoneId.systemDefault()).toInstant());
	}

	public static int compareDate(String date1, String date2) {
		return compareDate(date1, date2, PATTERN_DATE);
	}

	public static int compareDateTime(String dateTime1, String dateTime2) {
		return compareDateTime(dateTime1, dateTime2, PATTERN_DATE_TIME);
	}

	public static int compareTime(String time1, String time2) {
		return compareTime(time1, time2, PATTERN_TIME);
	}

	public static int compareYearMonth(String date1, String date2) {
		return compareYearMonth(date1, date2, PATTERN_YEAR_MONTH);
	}

	public static int compareDate(String date1, String date2, String pattern) {
		return parseLocalDate(date1, pattern).compareTo(parseLocalDate(date2, pattern));
	}

	public static int compareDateTime(String dateTime1, String dateTime2, String pattern) {
		return parseLocalDateTime(dateTime1, pattern).compareTo(parseLocalDateTime(dateTime2, pattern));
	}

	public static int compareTime(String time1, String time2, String pattern) {
		return parseLocalTime(time1, pattern).compareTo(parseLocalTime(time2, pattern));
	}

	public static int compareYearMonth(String date1, String date2, String pattern) {
		return parseYearMonth(date1, pattern).compareTo(parseYearMonth(date2, pattern));
	}

	public static boolean inRangeTime(String time, String startTime, String endTime) {
		LocalTime lt = LocalTime.parse(time);
		return lt.isAfter(LocalTime.parse(startTime)) && lt.isBefore(LocalTime.parse(endTime));
	}

	public static long dateIntervalNanos(String startDate, String endDate) {
		return Duration.between(LocalDate.parse(startDate), LocalDate.parse(endDate)).toNanos();
	}

	public static long dateIntervalMillis(String startDate, String endDate) {
		return Duration.between(LocalDate.parse(startDate), LocalDate.parse(endDate)).toMillis();
	}

	public static long dateIntervalMinutes(String startDate, String endDate) {
		return Duration.between(LocalDate.parse(startDate), LocalDate.parse(endDate)).toMinutes();
	}

	public static long dateIntervalHours(String startDate, String endDate) {
		return Duration.between(LocalDate.parse(startDate), LocalDate.parse(endDate)).toHours();
	}

	public static long dateIntervalDays(String startDate, String endDate) {
		return Math.abs(LocalDate.parse(startDate).toEpochDay() - LocalDate.parse(endDate).toEpochDay());
	}

	public static long dateTimeIntervalNanos(String startDateTime, String endDateTime) {
		return Duration.between(parseLocalDateTime(startDateTime), parseLocalDateTime(endDateTime)).toNanos();
	}

	public static long dateTimeIntervalMillis(String startDateTime, String endDateTime) {
		return Duration.between(parseLocalDateTime(startDateTime), parseLocalDateTime(endDateTime)).toMillis();
	}

	public static long dateTimeIntervalMinutes(String startDateTime, String endDateTime) {
		return Duration.between(parseLocalDateTime(startDateTime), parseLocalDateTime(endDateTime)).toMinutes();
	}

	public static long dateTimeIntervalHours(String startDateTime, String endDateTime) {
		return Duration.between(parseLocalDateTime(startDateTime), parseLocalDateTime(endDateTime)).toHours();
	}

	public static long dateTimeIntervalDays(String startDateTime, String endDateTime) {
		return Duration.between(parseLocalDateTime(startDateTime), parseLocalDateTime(endDateTime)).toDays();
	}

	public static long timeIntervalNanos(String startTime, String endTime) {
		return Duration.between(LocalTime.parse(startTime), LocalTime.parse(endTime)).toNanos();
	}

	public static long timeIntervalMillis(String startTime, String endTime) {
		return Duration.between(LocalTime.parse(startTime), LocalTime.parse(endTime)).toMillis();
	}

	public static long timeIntervalMinutes(String startTime, String endTime) {
		return Duration.between(LocalTime.parse(startTime), LocalTime.parse(endTime)).toMinutes();
	}

	public static long timeIntervalHours(String startTime, String endTime) {
		return Duration.between(LocalTime.parse(startTime), LocalTime.parse(endTime)).toHours();
	}

	public static long timeIntervalDays(String startTime, String endTime) {
		return Duration.between(LocalTime.parse(startTime), LocalTime.parse(endTime)).toDays();
	}

	public static long yearMonthIntervalMonth(String startYearMonth, String endYearMonth) {
		Integer[] syds = ArrayUtil.toIntegerArray(startYearMonth.split("-"));
		Integer[] eyds = ArrayUtil.toIntegerArray(endYearMonth.split("-"));

		return Math.abs(Math.abs(syds[0] - eyds[0]) * 12 + (syds[0] > eyds[0] ? syds[1] : eyds[1]) - (syds[0] > eyds[0] ? eyds[1] : syds[1]));
	}
	// JDK8 新用法 END
}
