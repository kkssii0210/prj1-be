package com.example.prj1be.service;

import com.example.prj1be.domain.Board;
import com.example.prj1be.domain.Member;
import com.example.prj1be.domain.MyDto1;
import com.example.prj1be.mapper.BoardMapper;
import com.example.prj1be.mapper.CommentMapper;
import com.example.prj1be.mapper.FileMapper;
import com.example.prj1be.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;
    private final MemberService memberService;
    private final LikeMapper likeMapper;
    private final CommentMapper commentMapper;
    private final FileMapper fileMapper;
    @Value("${aws.s3.bucket.name}")
    private String bucket;
    private final S3Client s3;


    public boolean save(Board board, Member login, MultipartFile[] files) throws IOException {
        board.setWriter(login.getId());
        int cnt = mapper.insert(board);
        // boardFile 테이블에 files 정보 저장
        // Id,boardId, name
        if (files != null){
            for (int i = 0; i < files.length; i++) {
                fileMapper.insert(board.getId(),files[i].getOriginalFilename());
                upload(board.getId(), files[i]);
            }
        }

        // 실제 파일을 S3 bucket에 upload
        return cnt == 1;
    }

    private void upload(Integer boardId, MultipartFile file) throws IOException {
        // 파일 저장 경로
        // C:\Temp\prj1\게시물번호\파일명
//            File folder = new File("C:\\Temp\\prj1\\"+boardId);
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }
//            String path = folder.getAbsolutePath() + "\\" + file.getOriginalFilename();
//            File des = new File(path);
//            file.transferTo(des);
        String key = "prj1/" + boardId + "/" +file.getOriginalFilename();
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
            s3.putObject(objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

    }

    public boolean validate(Board board) {
        if (board == null) {
            return false;
        }
        if (board.getContent() == null || board.getContent().isBlank()) {
            return false;
        }
        if (board.getTitle()==null||board.getTitle().isBlank()){
            return false;
        }
        return true;
    }
    public Map<String, Object> list(Integer page, String keyword) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> pageInfo = new HashMap<>();

//        int countAll = mapper.countAll();
        int countAll = mapper.countAll("%"+keyword+"%");
        int lastPageNumber = (countAll - 1) / 10 + 1;
        int startPageNumber = (page - 1) / 10 * 10 + 1;
        int endPageNumber = startPageNumber + 9;
        endPageNumber = Math.min(endPageNumber, lastPageNumber);
        int prevPageNumber = startPageNumber - 10;
        int nextPageNumber = endPageNumber + 1;

        pageInfo.put("currentPageNumber", page);
        pageInfo.put("startPageNumber", startPageNumber);
        pageInfo.put("endPageNumber", endPageNumber);
        if (prevPageNumber > 0) {
            pageInfo.put("prevPageNumber", prevPageNumber);
        }
        if (nextPageNumber <= lastPageNumber) {
            pageInfo.put("nextPageNumber", nextPageNumber);
        }

        int from = (page - 1) * 10;
        map.put("boardList", mapper.selectAll(from,"%"+keyword+"%"));
        map.put("pageInfo", pageInfo);
        return map;
    }
    public Board get(Integer id) {
        return mapper.selectById(id);
    }
    public boolean remove(Integer id) {
        likeMapper.deleteById(id);
        commentMapper.deleteByBoardId(id);
        return mapper.deleteById(id) == 1;
    }

    public boolean edit(MyDto1 dto) {
        return mapper.updateById(dto) == 1;
    }

    public boolean hasAccess(Integer id, Member login) {
        if (login == null) {
            return false;
        }

        if (login.isAdmin()) {
            return true;
        }

        Board board = mapper.selectById(id);

        return board.getWriter().equals(login.getId());
    }

}
