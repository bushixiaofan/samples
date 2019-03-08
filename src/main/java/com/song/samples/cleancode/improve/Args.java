package com.song.samples.cleancode.improve;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * 格式化字符串类
 *
 * @author: songzeqi
 * @Date: 2018-06-14 下午2:08
 */

public class Args {
    private Map<Character, ArgumentMarshaler> argumentMarshalerMap;
    private Set<ArgumentMarshaler> argumentMarshalers;
    private ListIterator<String> currentArgument;

    public Args(String schema, String[] args) throws ArgsException {
        this.argumentMarshalerMap = Maps.newHashMap();
        this.argumentMarshalers = Sets.newHashSet();

        parseSchema(schema);
        parseArgumentStrings(Lists.newArrayList(args));
    }

    private void parseSchema(String schema) throws ArgsException {
        for (String element : ",".split(schema)) {
            if (element.length() > 0) {
                parseSchemaElement(element.trim());
            }
        }
    }

    private void validateSchemaElementId(char elementId) throws ArgsException {
        if (!Character.isLetter(elementId)) {
            throw new ArgsException(elementId, null, ArgsException.ErrorCode.INVALID_ARGUMENT_FORMAT);
        }
    }

    private void parseSchemaElement(String element) throws ArgsException {
        char elementId = element.charAt(0);
        String parameter = element.substring(1);
        validateSchemaElementId(elementId);
        if (parameter.length() == 0) {
            argumentMarshalerMap.put(elementId, new BooleanArgumentMarshaler());
        }
    }

    private void parseArgumentStrings(List<String> args) {

    }
}
