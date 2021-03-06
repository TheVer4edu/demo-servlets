package com.example.demoservlets;

import java.nio.file.attribute.FileTime;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class FileInfo {

    private final String name;
    private final boolean isDirectory;
    private final String lastChanged;
    private final String size;

    public FileInfo(String name, boolean isDirectory, FileTime lastChanged, long size) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.lastChanged = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a").format(lastChanged.toMillis());
        this.size = isDirectory ? "0" : convertToStringRepresentation(size);
    }

    public String getLastChanged() {
        return lastChanged;
    }

    public String getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;

    public static String convertToStringRepresentation(final long value){
        final long[] dividers = new long[] { T, G, M, K, 1 };
        final String[] units = new String[] { "TB", "GB", "MB", "KB", "B" };
        if(value < 0)
            throw new IllegalArgumentException("Invalid file size: " + value);
        String result = null;
        for(int i = 0; i < dividers.length; i++){
            final long divider = dividers[i];
            if(value >= divider){
                result = format(value, divider, units[i]);
                break;
            }
        }
        return result;
    }

    private static String format(final long value,
                                 final long divider,
                                 final String unit){
        final double result =
                divider > 1 ? (double) value / (double) divider : (double) value;
        return new DecimalFormat("#,##0.#").format(result) + " " + unit;
    }
}
