package com.smallhua.org.mapper;

import com.smallhua.org.model.TChunk;
import com.smallhua.org.model.TChunkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TChunkMapper {
    long countByExample(TChunkExample example);

    int deleteByExample(TChunkExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TChunk record);

    int insertSelective(TChunk record);

    List<TChunk> selectByExample(TChunkExample example);

    TChunk selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TChunk record, @Param("example") TChunkExample example);

    int updateByExample(@Param("record") TChunk record, @Param("example") TChunkExample example);

    int updateByPrimaryKeySelective(TChunk record);

    int updateByPrimaryKey(TChunk record);
}