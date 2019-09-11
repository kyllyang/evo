package com.github.framework.evo.common.uitl;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

	public static Date parseDate(String str) {
		return Date.from(LocalDate.parse(str, DateTimeFormatter.ofPattern(PATTERN_DATE)).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date parseDateCompact(String str) {
		return Date.from(LocalDate.parse(str, DateTimeFormatter.ofPattern(PATTERN_DATE_COMPACT)).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date parseDatetime(String str) {
		return parse(str, PATTERN_DATE_TIME);
	}

	public static Date parseDatetimeCompact(String str) {
		return parse(str, PATTERN_DATE_TIME_COMPACT);
	}

	public static Date parse(String str, String pattern) {
		return Date.from(LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern)).atZone(ZoneId.systemDefault()).toInstant());
	}

	public static String formatDate(Date date) {
		return format(date, PATTERN_DATE);
	}

	public static String formatDateCompact(Date date) {
		return format(date, PATTERN_DATE_COMPACT);
	}

	public static String formatDatetime(Date date) {
		return format(date, PATTERN_DATE_TIME);
	}

	public static String formatDatetimeCompact(Date date) {
		return format(date, PATTERN_DATE_TIME_COMPACT);
	}

	public static String format(Date date, String pattern) {
		return date == null ? null : DateTimeFormatter.ofPattern(pattern).format(LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault()));
	}

	public static String formatDate(LocalDate localDate) {
		return format(localDate, PATTERN_DATE);
	}

	public static String formatDateCompact(LocalDate localDate) {
		return format(localDate, PATTERN_DATE_COMPACT);
	}

	public static String formatDatetime(LocalDateTime localDateTime) {
		return format(localDateTime, PATTERN_DATE_TIME);
	}

	public static String formatDatetimeCompact(LocalDateTime localDateTime) {
		return format(localDateTime, PATTERN_DATE_TIME_COMPACT);
	}

	public static String format(TemporalAccessor temporal, String pattern) {
		return temporal == null ? null : DateTimeFormatter.ofPattern(pattern).format(temporal);
	}

	public static Date removeHMS(Date date) {
		return parseDate(formatDate(date));
	}

	public static Date plusNanos(Date date, long nanos) {
		return toDate(toDateTime(date).plusNanos(nanos));
	}

	public static Date plusSeconds(Date date, long seconds) {
		return toDate(toDateTime(date).plusSeconds(seconds));
	}

	public static Date plusMinutes(Date date, long minutes) {
		return toDate(toDateTime(date).plusMinutes(minutes));
	}

	public static Date plusHours(Date date, long hours) {
		return toDate(toDateTime(date).plusHours(hours));
	}

	public static Date plusDays(Date date, long days) {
		return toDate(toDateTime(date).plusDays(days));
	}

	public static Date plusWeeks(Date date, long weeks) {
		return toDate(toDateTime(date).plusWeeks(weeks));
	}

	public static Date plusMonths(Date date, long months) {
		return toDate(toDateTime(date).plusMonths(months));
	}

	public static Date plusYears(Date date, long years) {
		return toDate(toDateTime(date).plusYears(years));
	}

	public static Date minusNanos(Date date, long nanos) {
		return toDate(toDateTime(date).minusNanos(nanos));
	}

	public static Date minusSeconds(Date date, long seconds) {
		return toDate(toDateTime(date).minusSeconds(seconds));
	}

	public static Date minusMinutes(Date date, long minutes) {
		return toDate(toDateTime(date).minusMinutes(minutes));
	}

	public static Date minusHours(Date date, long hours) {
		return toDate(toDateTime(date).minusHours(hours));
	}

	public static Date minusDays(Date date, long days) {
		return toDate(toDateTime(date).minusDays(days));
	}

	public static Date minusWeeks(Date date, long weeks) {
		return toDate(toDateTime(date).minusWeeks(weeks));
	}

	public static Date minusMonths(Date date, long months) {
		return toDate(toDateTime(date).minusMonths(months));
	}

	public static Date minusYears(Date date, long years) {
		return toDate(toDateTime(date).minusYears(years));
	}

	public static Date now() {
		return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate nowDate() {
		return LocalDate.now();
	}

	public static LocalDateTime nowDateTime() {
		return LocalDateTime.now();
	}

	public static LocalTime nowTime() {
		return LocalTime.now();
	}

	public static LocalDate toDate(long millis) {
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime toDateTime(long millis) {
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static LocalTime toTime(long millis) {
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalTime();
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

	public static LocalDate toDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime toDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static LocalTime toTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
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
		return Duration.between(LocalDateTime.parse(startDateTime), LocalDateTime.parse(endDateTime)).toNanos();
	}

	public static long dateTimeIntervalMillis(String startDateTime, String endDateTime) {
		return Duration.between(LocalDateTime.parse(startDateTime), LocalDateTime.parse(endDateTime)).toMillis();
	}

	public static long dateTimeIntervalMinutes(String startDateTime, String endDateTime) {
		return Duration.between(LocalDateTime.parse(startDateTime), LocalDateTime.parse(endDateTime)).toMinutes();
	}

	public static long dateTimeIntervalHours(String startDateTime, String endDateTime) {
		return Duration.between(LocalDateTime.parse(startDateTime), LocalDateTime.parse(endDateTime)).toHours();
	}

	public static long dateTimeIntervalDays(String startDateTime, String endDateTime) {
		return Duration.between(LocalDateTime.parse(startDateTime), LocalDateTime.parse(endDateTime)).toDays();
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
}
