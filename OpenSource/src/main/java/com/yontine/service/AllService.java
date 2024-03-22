package com.yontine.service;

import com.yontine.pojo.VO.*;

import java.util.List;

public interface AllService {
    List<ProjectVO> project();

    List<LanguageTopVO> language();

    List<ProtocolVO> protocol();

    List<CountryUserVO> country_user();

    List<CountryFollowerVO> country_followers();

    List<LanguageStarVO> language_star();

    List<LanguageVO> language_time();

    List<Operate_CommitVO> TimeOperateCommit();

    List<LanguagePreVO> language_predict();

    List<User_YearVO> total_num();

    List<Language_NumVO> active();
}
