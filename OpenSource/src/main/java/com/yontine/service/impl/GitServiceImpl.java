package com.yontine.service.impl;


import com.yontine.mapper.GitMapper;
import com.yontine.pojo.VO.*;
import com.yontine.service.GitService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GitServiceImpl implements GitService {


    @Resource
    private GitMapper gitMapper;

    @Override
    public List<ProjectVO> project() {
        List<ProjectVO> projectVOS = gitMapper.project();
        int rank = 1;
        for (ProjectVO pr : projectVOS) {
            pr.setRank(rank);
            rank++;
        }
        return projectVOS;
    }

    @Override
    public List<LanguageTopVO> language() {
        List<LanguageTopVO> languageTopVOS = gitMapper.language();
        int rank = 1;
        for (LanguageTopVO la : languageTopVOS) {
            la.setRank(rank);
            rank++;
        }
        return languageTopVOS;
    }

    @Override
    public List<ProtocolVO> protocol() {
        List<ProtocolVO> protocolVOS = gitMapper.protocol();
        int rank = 1;
        for (ProtocolVO pr : protocolVOS) {
            pr.setRank(rank);
            rank++;
        }
        return protocolVOS;
    }

    @Override
    public List<CountryUserVO> country_user() {
        List<CountryUserVO> countryUserVOS = gitMapper.country_user();
        int rank = 1;
        for (CountryUserVO co : countryUserVOS) {
            if (co.getLocation() == null)
                co.setLocation("Unknown");
            co.setRank(rank);
            rank++;
        }
        return countryUserVOS;
    }

    @Override
    public List<CountryFollowerVO> country_followers() {
        List<CountryFollowerVO> countryFollowerVOS = gitMapper.country_followers();
        int rank = 1;
        for (CountryFollowerVO co : countryFollowerVOS) {
            if (co.getLocation() == null)
                co.setLocation("Unknown");
            co.setRank(rank);
            rank++;
        }
        return countryFollowerVOS;
    }

    @Override
    public List<LanguageStarVO> language_star() {
        List<LanguageStarVO> languageStarVOS = gitMapper.language_star();
        int rank = 1;
        for (LanguageStarVO co : languageStarVOS) {
            co.setRank(rank);
            rank++;
        }
        return languageStarVOS;
    }

    @Override
    public List<LanguageVO> language_time() {
        List<LanguageVO> languageVOS = gitMapper.language_time();
        return languageVOS;
    }

    @Override
    public List<Operate_CommitVO> TimeOperateCommit() {
        List<Operate_CommitVO> operateCommitVOS = gitMapper.TimeOperateCommit();
        return operateCommitVOS;
    }

    @Override
    public List<LanguagePreVO> language_predict() {
        List<LanguagePreVO> languagePreVOS = gitMapper.language_predict();
        return languagePreVOS;
    }

    @Override
    public List<User_YearVO> total_num() {
        List<User_YearVO> userYearVOS = gitMapper.total_num();
        return userYearVOS;
    }

    @Override
    public List<Language_NumVO> active() {
        List<Language_NumVO> languageNumVOS = gitMapper.active();
        return languageNumVOS;
    }
}
