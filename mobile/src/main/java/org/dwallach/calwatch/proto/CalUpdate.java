package org.dwallach.calwatch.proto;

// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: src/main/java/org/dwallach/calwatch/proto/calendar.proto
import com.squareup.wire.Message;
import com.squareup.wire.ProtoEnum;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

import static com.squareup.wire.Message.Datatype.BOOL;
import static com.squareup.wire.Message.Datatype.ENUM;
import static com.squareup.wire.Message.Datatype.INT32;
import static com.squareup.wire.Message.Datatype.INT64;
import static com.squareup.wire.Message.Label.REPEATED;
import static com.squareup.wire.Message.Label.REQUIRED;

/**
 * compile with:
 * % cd CalWatch/mobile
 * % java -jar wire-compiler-1.5.1-jar-with-dependencies.jar --proto_path=src/main/java --java_out=src/main/java/org/dwallach/calwatch/proto org/dwallach/calwatch/proto/calendar.proto
 * then edit the resulting class file (by hand... uggh) to add a package name, since this seems to be broken
 * then copy the file from the 'mobile' side over to the 'wear' side
 * Hopefully, this won't have to happen very often. All of this really belongs in the Gradle file,
 * with automated support from Android Studio, but there are bugs surrounding that. Lovely. So we go
 * by hand.
 */
public final class CalUpdate extends Message {

  public static final FaceStyle DEFAULT_STYLE = FaceStyle.TOOL;
  public static final Boolean DEFAULT_SHOWSECONDS = true;
  public static final List<Event> DEFAULT_EVENTS = Collections.emptyList();

  @ProtoField(tag = 1, type = ENUM, label = REQUIRED)
  public final FaceStyle style;

  @ProtoField(tag = 2, type = BOOL, label = REQUIRED)
  public final Boolean showSeconds;

  @ProtoField(tag = 3, label = REPEATED)
  public final List<Event> events;

  public CalUpdate(FaceStyle style, Boolean showSeconds, List<Event> events) {
    this.style = style;
    this.showSeconds = showSeconds;
    this.events = immutableCopyOf(events);
  }

  private CalUpdate(Builder builder) {
    this(builder.style, builder.showSeconds, builder.events);
    setBuilder(builder);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof CalUpdate)) return false;
    CalUpdate o = (CalUpdate) other;
    return equals(style, o.style)
        && equals(showSeconds, o.showSeconds)
        && equals(events, o.events);
  }

  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = style != null ? style.hashCode() : 0;
      result = result * 37 + (showSeconds != null ? showSeconds.hashCode() : 0);
      result = result * 37 + (events != null ? events.hashCode() : 1);
      hashCode = result;
    }
    return result;
  }

  public static final class Builder extends Message.Builder<CalUpdate> {

    public FaceStyle style;
    public Boolean showSeconds;
    public List<Event> events;

    public Builder() {
    }

    public Builder(CalUpdate message) {
      super(message);
      if (message == null) return;
      this.style = message.style;
      this.showSeconds = message.showSeconds;
      this.events = copyOf(message.events);
    }

    public Builder style(FaceStyle style) {
      this.style = style;
      return this;
    }

    public Builder showSeconds(Boolean showSeconds) {
      this.showSeconds = showSeconds;
      return this;
    }

    public Builder events(List<Event> events) {
      this.events = checkForNulls(events);
      return this;
    }

    @Override
    public CalUpdate build() {
      checkRequiredFields();
      return new CalUpdate(this);
    }
  }

  public static final class Event extends Message {

    public static final Long DEFAULT_STARTTIME = 0L;
    public static final Long DEFAULT_ENDTIME = 0L;
    public static final Integer DEFAULT_EVENTCOLOR = 0;
    public static final Integer DEFAULT_MINLEVEL = 0;
    public static final Integer DEFAULT_MAXLEVEL = 0;

    @ProtoField(tag = 1, type = INT64, label = REQUIRED)
    public final Long startTime;

    @ProtoField(tag = 2, type = INT64, label = REQUIRED)
    public final Long endTime;

    @ProtoField(tag = 3, type = INT32, label = REQUIRED)
    public final Integer eventColor;

    @ProtoField(tag = 4, type = INT32, label = REQUIRED)
    public final Integer minLevel;

    @ProtoField(tag = 5, type = INT32, label = REQUIRED)
    public final Integer maxLevel;

    public Event(Long startTime, Long endTime, Integer eventColor, Integer minLevel, Integer maxLevel) {
      this.startTime = startTime;
      this.endTime = endTime;
      this.eventColor = eventColor;
      this.minLevel = minLevel;
      this.maxLevel = maxLevel;
    }

    private Event(Builder builder) {
      this(builder.startTime, builder.endTime, builder.eventColor, builder.minLevel, builder.maxLevel);
      setBuilder(builder);
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Event)) return false;
      Event o = (Event) other;
      return equals(startTime, o.startTime)
          && equals(endTime, o.endTime)
          && equals(eventColor, o.eventColor)
          && equals(minLevel, o.minLevel)
          && equals(maxLevel, o.maxLevel);
    }

    @Override
    public int hashCode() {
      int result = hashCode;
      if (result == 0) {
        result = startTime != null ? startTime.hashCode() : 0;
        result = result * 37 + (endTime != null ? endTime.hashCode() : 0);
        result = result * 37 + (eventColor != null ? eventColor.hashCode() : 0);
        result = result * 37 + (minLevel != null ? minLevel.hashCode() : 0);
        result = result * 37 + (maxLevel != null ? maxLevel.hashCode() : 0);
        hashCode = result;
      }
      return result;
    }

    public static final class Builder extends Message.Builder<Event> {

      public Long startTime;
      public Long endTime;
      public Integer eventColor;
      public Integer minLevel;
      public Integer maxLevel;

      public Builder() {
      }

      public Builder(Event message) {
        super(message);
        if (message == null) return;
        this.startTime = message.startTime;
        this.endTime = message.endTime;
        this.eventColor = message.eventColor;
        this.minLevel = message.minLevel;
        this.maxLevel = message.maxLevel;
      }

      public Builder startTime(Long startTime) {
        this.startTime = startTime;
        return this;
      }

      public Builder endTime(Long endTime) {
        this.endTime = endTime;
        return this;
      }

      public Builder eventColor(Integer eventColor) {
        this.eventColor = eventColor;
        return this;
      }

      public Builder minLevel(Integer minLevel) {
        this.minLevel = minLevel;
        return this;
      }

      public Builder maxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
        return this;
      }

      @Override
      public Event build() {
        checkRequiredFields();
        return new Event(this);
      }
    }
  }

  public enum FaceStyle
      implements ProtoEnum {
    TOOL(0),
    LITE(1),
    NUMBERS(2);

    private final int value;

    private FaceStyle(int value) {
      this.value = value;
    }

    @Override
    public int getValue() {
      return value;
    }
  }
}
