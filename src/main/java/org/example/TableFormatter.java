package org.example;

import java.util.List;

public class TableFormatter {
    public static String formatTable(String[] headers, List<String[]> rows) {
        int[] columnWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                columnWidths[i] = Math.max(columnWidths[i], row[i].length());
            }
        }

        StringBuilder table = new StringBuilder();
        table.append(formatRow(headers, columnWidths));
        table.append(formatSeparator(columnWidths));

        for (String[] row : rows) {
            table.append(formatRow(row, columnWidths));
        }

        return table.toString();
    }

    private static String formatRow(String[] row, int[] columnWidths) {
        StringBuilder rowBuilder = new StringBuilder("|");
        for (int i = 0; i < row.length; i++) {
            rowBuilder.append(" ")
                    .append(padRight(row[i], columnWidths[i]))
                    .append(" |");
        }
        rowBuilder.append("\n");
        return rowBuilder.toString();
    }

    private static String formatSeparator(int[] columnWidths) {
        StringBuilder separatorBuilder = new StringBuilder("+");
        for (int width : columnWidths) {
            separatorBuilder.append("-".repeat(width + 2)).append("+");
        }
        separatorBuilder.append("\n");
        return separatorBuilder.toString();
    }

    private static String padRight(String text, int length) {
        return String.format("%-" + length + "s", text);
    }
}
