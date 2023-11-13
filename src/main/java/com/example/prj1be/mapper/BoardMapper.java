package com.example.prj1be.mapper;

import com.example.prj1be.domain.Board;
import com.example.prj1be.domain.MyDto1;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {
    @Insert("""
INSERT INTO board (title, content, writer)
VALUES (#{title},#{content},#{writer})
""")
    int insert(Board board);

    @Select("""
SELECT id,title,writer,inserted
FROM board
ORDER BY id desc
""")
    List<MyDto1> selectAll();
@Select("""
SELECT *
FROM board
WHERE id = #{id}
""")
    MyDto1 selectById(Integer id);
@Delete("""
DELETE FROM board
WHERE id = #{id}
""")
    int deleteById(Integer id);
@Update("""
UPDATE board
SET title=#{title},
content=#{content},
writer=#{writer}
WHERE id=#{id}
""")
    int updateById(MyDto1 dto);
}