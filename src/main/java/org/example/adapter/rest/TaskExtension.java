package org.example.adapter.rest;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.example.core.domain.Task;

import io.quarkus.qute.TemplateExtension;

@TemplateExtension
public class TaskExtension {

    private static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd.MM.uuuu HH:mm")
            .withLocale(Locale.GERMANY)
            .withZone(ZoneId.systemDefault());

    public static String formatTimestamp(Task task) {
        return formatter.format(task.getCreated());

    }
}
