package com.example.prj1be.controller;

import com.example.prj1be.domain.Board;
import com.example.prj1be.domain.Member;
import com.example.prj1be.domain.MyDto1;
import com.example.prj1be.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService service;

    @PostMapping("add")
    public ResponseEntity add(@RequestBody MyDto1 dto, @SessionAttribute(value = "login",required = false) Member login) {
        if (login == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!service.validate(dto)) {
            return ResponseEntity.badRequest().build();
        }

        if (service.save(dto,login)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("list")
    public List<MyDto1> List() {
        List<MyDto1> dto = service.findAll();
        return dto;
    }

    @GetMapping("id/{id}")
    public MyDto1 get(@PathVariable Integer id) {
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
    public ResponseEntity edit(@RequestBody MyDto1 dto, @SessionAttribute(value = "login",required = false)Member login) {
        if (login == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!service.hasAccess(dto.getId(),login)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (service.edit(dto)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }




}
