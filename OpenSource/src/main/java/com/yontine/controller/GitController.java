package com.yontine.controller;

import com.yontine.pojo.Result;
import com.yontine.pojo.VO.*;
import com.yontine.service.GitService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class GitController {

    @Resource
    private GitService gitService;

    @GetMapping("/project")
    public Result project() {
        List<ProjectVO> projectVOS = gitService.project();
        return Result.success(projectVOS);
    }

//    @GetMapping("/language")
//    public Result language() {
//        List<LanguageTopVO> languageTopVOS = gitService.language();
//        System.out.println(languageTopVOS.size());
//        return Result.success(languageTopVOS);
//    }

    @GetMapping("/language")
    public Result language() {
        List<LanguageTopVO> languageTopVOS = gitService.language();
        System.out.println(languageTopVOS.size());
        return Result.success(languageTopVOS);
    }
    @GetMapping("/protocol")
    public Result protocol() {
        List<ProtocolVO> protocolVOS = gitService.protocol();
        return Result.success(protocolVOS);
    }
    @GetMapping("/country_user")
    public Result country_user() {
        List<CountryUserVO> countryUserVOS = gitService.country_user();
        return Result.success(countryUserVOS);
    }
    @GetMapping("/country_followers")
    public Result country_followers() {
        List<CountryFollowerVO> countryFollowerVOS = gitService.country_followers();
        return Result.success(countryFollowerVOS);
    }
    @GetMapping("/language_projectNum")
    public Result language_projectNum() {
        List<LanguageTopVO> languageTopVOS = gitService.language();
        return Result.success(languageTopVOS);
    }

    @GetMapping("/language_star")
    public Result language_star() {
        List<LanguageStarVO> languageStarVOS = gitService.language_star();
        return Result.success(languageStarVOS);
    }

    @GetMapping("/language_time")
    public Result language_time() {
        List<LanguageVO> languageVOS = gitService.language_time();
        return Result.success(languageVOS);
    }
    @GetMapping("/TimeOperateCommit")
    public Result TimeOperateCommit() {
        List<Operate_CommitVO> operateCommitVOS = gitService.TimeOperateCommit();
        return Result.success(operateCommitVOS);
    }

    @GetMapping("/language_predict")
    public Result language_predict() {
        List<LanguagePreVO> languagePreVOS = gitService.language_predict();
        return Result.success(languagePreVOS);
    }
    @GetMapping("/total_num")
    public Result total_num() {
        List<User_YearVO> userYearVOS = gitService.total_num();
        return Result.success(userYearVOS);
    }
    @GetMapping("/active")
    public Result active() {
        List<Language_NumVO> languageNumVOS = gitService.active();
        return Result.success(languageNumVOS);
    }
}
