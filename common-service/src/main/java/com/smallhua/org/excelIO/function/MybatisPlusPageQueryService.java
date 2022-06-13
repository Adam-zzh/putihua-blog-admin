package com.smallhua.org.excelIO.function;

import java.util.List;

@FunctionalInterface
public interface MybatisPlusPageQueryService<E> {

    /**
     * @description: 获取分页查询数据
     * 注意：mybatis-plus 的 current 参数是从 1 开始
     * @param current 当前页，从 1 开始
     * @param size
     */
    List<E> data(int current, int size);
}
