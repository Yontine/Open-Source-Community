package com.yontine.service.impl;


import com.yontine.mapper.AllMapper;
import com.yontine.pojo.VO.*;
import com.yontine.service.AllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllServicelmpl implements AllService {


    @Autowired
    private AllMapper allMapper;

    @Override
    public List<ProjectVO> project() {
        List<ProjectVO> projectVOS = allMapper.project();
        int rank = 1;
        for (ProjectVO pr : projectVOS) {
            pr.setRank(rank);
            rank++;
        }
        return projectVOS;
    }

    @Override
    public List<LanguageTopVO> language() {
        List<LanguageTopVO> languageTopVOS = allMapper.language();
        int rank = 1;
        for (LanguageTopVO la : languageTopVOS) {
            la.setRank(rank);
            rank++;
        }
        return languageTopVOS;
    }

    @Override
    public List<ProtocolVO> protocol() {
        List<ProtocolVO> protocolVOS = allMapper.protocol();
        int rank = 1;
        for (ProtocolVO pr : protocolVOS) {
            pr.setRank(rank);
            rank++;
        }
        return protocolVOS;
    }

    @Override
    public List<CountryUserVO> country_user() {
        List<CountryUserVO> countryUserVOS = allMapper.country_user();
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
        List<CountryFollowerVO> countryFollowerVOS = allMapper.country_followers();
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
        List<LanguageStarVO> languageStarVOS = allMapper.language_star();
        int rank = 1;
        for (LanguageStarVO co : languageStarVOS) {
            co.setRank(rank);
            rank++;
        }
        return languageStarVOS;
    }

    @Override
    public List<LanguageVO> language_time() {
        List<LanguageVO> languageVOS = allMapper.language_time();
        return languageVOS;
    }

    @Override
    public List<Operate_CommitVO> TimeOperateCommit() {
        List<Operate_CommitVO> operateCommitVOS = allMapper.TimeOperateCommit();
        return operateCommitVOS;
    }

    @Override
    public List<LanguagePreVO> language_predict() {
        List<LanguagePreVO> languagePreVOS = allMapper.language_predict();
        return languagePreVOS;
    }

    @Override
    public List<User_YearVO> total_num() {
        List<User_YearVO> userYearVOS = allMapper.total_num();
        return userYearVOS;
    }

    @Override
    public List<Language_NumVO> active() {
        List<Language_NumVO> languageNumVOS = allMapper.active();
        return languageNumVOS;
    }
}
