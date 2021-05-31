package com.smallhua.org.front.controller;

import com.smallhua.org.common.api.CommonPage;
import com.smallhua.org.common.api.CommonResult;
import com.smallhua.org.common.domain.BaseParam;
import com.smallhua.org.front.service.CommentService;
import com.smallhua.org.model.TComment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ZZH
 * @create 2021/5/25
 * @since 1.0.0
 */
@RestController
@RequestMapping("commentController")
@Api("评论管理")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    @ApiOperation("动态查询")
    public CommonPage<TComment> selAllComments(@RequestBody BaseParam baseParam){
        return commentService.selAllComments(baseParam);
    }

    @PostMapping("/public/comments")
    @ApiOperation("动态查询-公开")
    public CommonPage<TComment> publicAllComments(@RequestBody BaseParam baseParam){
        return commentService.selAllComments(baseParam);
    }

    @PostMapping("comment")
    @ApiOperation("发布评论")
    public CommonResult saveComment(@RequestBody TComment comment){
        return commentService.saveComment(comment);
    }


    @DeleteMapping("comment/{id}")
    @ApiOperation("发布评论")
    public CommonResult delComment(@PathVariable("id") Long id){
        return commentService.delComment(id);
    }

}