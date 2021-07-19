package com.smallhua.org.mapper;

import com.smallhua.org.model.TFileInfo;
import com.smallhua.org.model.TFileInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFileInfoMapper {
    long countByExample(TFileInfoExample example);

    int deleteByExample(TFileInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TFileInfo record);

    int insertSelective(TFileInfo record);

    List<TFileInfo> selectByExample(TFileInfoExample example);

    TFileInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TFileInfo record, @Param("example") TFileInfoExample example);

    int updateByExample(@Param("record") TFileInfo record, @Param("example") TFileInfoExample example);

    int updateByPrimaryKeySelective(TFileInfo record);

    int updateByPrimaryKey(TFileInfo record);
}