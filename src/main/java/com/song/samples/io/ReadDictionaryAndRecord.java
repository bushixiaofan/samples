package com.song.samples.io;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;

/**
 * @author: songzeqi
 * @Date: 2019-09-19 11:23 AM
 */

public class ReadDictionaryAndRecord {
    public static void main(String[] args) {
        File file = new File("/Users/songzeqi/Workspace/finance-creditpay-api-case");
        List<String> keyWordsList = Lists.newArrayList();
        try {
            keyWordsList = scanDictionaryAndPrintKeyWords(file, "userId\":");
        } catch (IOException e) {
            e.printStackTrace();
        }
        keyWordsList.stream().distinct().filter(a-> !Strings.isNullOrEmpty(a.trim())).forEach((String a) ->{
            System.out.println(a);
        });
    }

    public static List<String> scanDictionaryAndPrintKeyWords(File file, String keyWords) throws IOException {
        File[] files = file.listFiles();
        List<String> keyWordsList = Lists.newArrayList();
        if (files == null) {
            return Lists.newArrayList();
        }
        for(int i = 0; i< files.length; i++) {
            if (files[i].isDirectory()) {
                keyWordsList.addAll(scanDictionaryAndPrintKeyWords(files[i], keyWords));
            } else if (files[i].isFile()) {
                keyWordsList.addAll(readFileAndPrintKeyWords(files[i], keyWords));
            }
        }
        return keyWordsList;
    }

    public static List<String> readFileAndPrintKeyWords(File file, String keyWords) throws IOException {
        List<String> keyWordsList = Lists.newArrayList();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replace(" ", "");
                if (line.contains(keyWords)) {
                    String after = line.substring(line.indexOf(keyWords));
                    if (after.startsWith(keyWords + "\"")) {
                        String[] splitStrings = after.split("\"");
                        if (splitStrings.length > 2) {
                            keyWordsList.add(splitStrings[2].trim());
                        }
                    } else {
                        String[] splitStrings = after.split(":");
                        System.out.println(after);
                        if (splitStrings.length > 1) {
                            String temp = splitStrings[1].trim();
                            String[] tempSps = temp.split(",");
                            if (tempSps.length > 0) {
                                temp = tempSps[0];
                            }
                            tempSps = temp.split("}");
                            if (tempSps.length > 0) {
                                temp = tempSps[0];
                            }
                            keyWordsList.add(temp);
                        }
                    }
                }
            }
        }
        return keyWordsList;
    }
}
