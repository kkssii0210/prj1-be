package com.example.prj1be.controller;

import com.example.prj1be.domain.Board;
import com.example.prj1be.domain.Member;
import com.example.prj1be.domain.MyDto1;
import com.example.prj1be.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService service;

    @PostMapping("add")
    public ResponseEntity add(Board board,@RequestParam(value = "uploadFiles[]",required = false) MultipartFile[] files, @SessionAttribute(value = "login",required = false) Member login) throws IOException {
        System.out.println("---");
        if (login == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!service.validate(board)) {
            return ResponseEntity.badRequest().build();
        }

        if (service.save(board,login,files)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
    @Cacheable(value = "listCache",cacheManager = "albumCacheManager")
    @GetMapping("list")
    public Map<String, Object> list(@RequestParam(value = "p", defaultValue = "1") Integer page,
                                    @RequestParam(value = "k",defaultValue = "")String keyword,
                                    @RequestParam(value = "c",defaultValue = "all")String category) {
        return service.list(page,keyword,category);
    }

    @GetMapping("id/{id}")
    public Board get(@PathVariable Integer id) {
        return service.get(id);
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity remove(@PathVariable Integer id, @SessionAttribute(value = "login",required = false) Member login) {

        if (login == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!service.hasAccess(id,login)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (service.remove(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("edit")
    public ResponseEntity edit(Board board,@RequestParam(value = "removeFileIds[]",required = false)List<Integer> removeFileIds,
                               @RequestParam(value = "uploadFiles[]",required = false) MultipartFile[] uploadFiles,
                               @SessionAttribute(value = "login",required = false)Member login) throws IOException {

        if (login == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!service.hasAccess(board.getId(),login)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (service.edit(board,removeFileIds,uploadFiles)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }





}
