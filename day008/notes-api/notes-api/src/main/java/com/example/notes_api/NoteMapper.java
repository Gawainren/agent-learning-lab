package com.example.notes_api;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper 
public interface NoteMapper {
    @Select ("""
                    SELECT id, owner_id, title
                    FROM agent_learning.notes
                    WHERE owner_id = #{ownerId}
                    ORDER BY id ASC
                    LIMIT #{limit} OFFSET #{offset}
                    """)
    public List<Map<String, Object>> findByOwner(@Param("ownerId")long ownerId,@Param("limit")int limit,@Param("offset")long offset);
}
