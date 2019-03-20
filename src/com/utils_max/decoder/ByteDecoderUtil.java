package com.utils_max.decoder;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.*;

public class ByteDecoderUtil {

    public static <T> T decode(final byte[] data, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        List<T> list = decodeList(data, clazz, 1);
        return list.get(0);
    }

    public static <T> List<T> decodeList(final byte[] data, Class<T> clazz, int count) throws IllegalAccessException, InstantiationException {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = new ArrayList<Field>(Arrays.asList(fields));
        Class<?> superClass = clazz.getSuperclass();
        while (superClass != null && !superClass.equals(Object.class)) {
            Field[] supperFields = superClass.getDeclaredFields();
            if (supperFields != null && supperFields.length > 0) {
                fieldList.addAll(new ArrayList<Field>(Arrays.asList(supperFields)));
            }
            superClass = superClass.getSuperclass();
        }
        Map<Integer, Field> orderMap = new HashMap<Integer, Field>();
        Map<String, Field> nameMap = new HashMap<String, Field>();
        for (Field field : fieldList) {
            ResponsePosition position = field.getAnnotation(ResponsePosition.class);
            if (position != null) {
                orderMap.put(position.order(), field);
                nameMap.put(field.getName(), field);
            }
        }
        int offset = 0;
        int fieldsCount = orderMap.size();
        List<T> resultList = new LinkedList<T>();
        for (int dataIndex = 0; dataIndex < count; dataIndex++) {
            T t = clazz.newInstance();
            for (int i = 0; i < fieldsCount; i++) {
                Field field = orderMap.get(i);
                field.setAccessible(true);
                ResponsePosition position = field.getAnnotation(ResponsePosition.class);
                int length = position.length();
                Class<?> fieldType = field.getType();
                if (position.isLoop()) {
                    String loopCountParam = position.loopCountParam();
                    if (!StringUtils.isEmpty(loopCountParam)) {
                        int loopCount = nameMap.get(loopCountParam).getInt(t);
                        if (fieldType.equals(List.class)) {
                            Class<?> msgClass = position.msgClass();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(data.length - offset);
                            byteBuffer.put(data, offset, data.length - offset);
                            List<?> msgList = decodeList(byteBuffer.array(), msgClass, loopCount);
                            field.set(t, msgClass);
                            for (Object object : msgList) {
                                //TODO offset更新

                            }
                        }
                    }
                }
                if (length == 0) {
                    String lengthParam = position.lengthParam();
                    if (!StringUtils.isEmpty(lengthParam)) {
                        Field fieldTemp = nameMap.get(lengthParam);
                        length = fieldTemp.getInt(t);
                    }
                }
                if (length >= 0) {
                    int max = 20;
                    if (length / 1024 / 1024 >= max) {
                        throw new RuntimeException("length is over then 20M !");
                    }
                    ByteBuffer byteBuffer = ByteBuffer.allocate(length);
                    byteBuffer.put(data, offset, length);
                    offset += length;
                    if (!position.ignore()) {
                        if (fieldType.equals(String.class)) {
                            field.set(t, new String(byteBuffer.array()));
                        } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                            field.set(t, byteBuffer.getInt(0));
                        } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                            field.set(t, byteBuffer.getLong(0));
                        } else if (fieldType.equals(byte[].class)) {
                            field.set(t, byteBuffer.array());
                        }

                    }
                }
            }
            resultList.add(t);
        }
        return resultList;
    }
}
