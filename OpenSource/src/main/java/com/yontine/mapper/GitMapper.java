package com.yontine.mapper;


import com.yontine.pojo.VO.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GitMapper {

    @Select("select name,url,description,language,star from shequ order by star desc ")
    List<ProjectVO> project();

//    @Select("select language,count(*) as project_num from shequ where language is not null group by language order by project_num desc")
//    List<LanguageTopVO> language();


    @Select("select language,count(*) as project_num from shequ where language is not null group by language order by project_num desc")
    List<LanguageTopVO> language();



    @Select("select protocol,count(*) as project_num from shequ where protocol is not null group by protocol order by project_num desc")
    List<ProtocolVO> protocol();

    @Select("select location,count(*) as users from shequ group by location order by users desc")
    List<CountryUserVO> country_user();

    @Select("select location,sum(followers) as followers from shequ group by location order by followers desc")
    List<CountryFollowerVO> country_followers();

    @Select("select language,sum(star) as star from shequ where language is not null group by language order by star desc")
    List<LanguageStarVO> language_star();

    @Select("select * from language")
    List<LanguageVO> language_time();

    @Select("select * from operate_commit")
    List<Operate_CommitVO> TimeOperateCommit();

    @Select("select * from pre")
    List<LanguagePreVO> language_predict();

    @Select("select * from user_year")
    List<User_YearVO> total_num();

    @Select("select * from language_now")
    List<Language_NumVO> active();
}
