package com.smallhua.org.mapper;

import com.smallhua.org.model.TUserMir;
import com.smallhua.org.model.TUserMirExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUserMirMapper {
    long countByExample(TUserMirExample example);

    int deleteByExample(TUserMirExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TUserMir record);

    int insertSelective(TUserMir record);

    List<TUserMir> selectByExample(TUserMirExample example);

    TUserMir selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TUserMir record, @Param("example") TUserMirExample example);

    int updateByExample(@Param("record") TUserMir record, @Param("example") TUserMirExample example);

    int updateByPrimaryKeySelective(TUserMir record);

    int updateByPrimaryKey(TUserMir record);
}