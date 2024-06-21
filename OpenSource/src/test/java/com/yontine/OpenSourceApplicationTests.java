package com.yontine;

import com.yontine.mapper.GitMapper;
import com.yontine.pojo.VO.*;
import com.yontine.service.impl.GitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(SpringRunner.class)
public class OpenSourceApplicationTests {
    @Mock
    private GitMapper gitMapper;
    @InjectMocks
    private GitServiceImpl gitService;

    @Test
    public void testProject() {
        List<ProjectVO> projectVOS = Arrays.asList(new ProjectVO(), new ProjectVO());
        when(gitMapper.project()).thenReturn(projectVOS);

        List<ProjectVO> result = gitService.project();

        verify(gitMapper).project();
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getRank());
        assertEquals(2, result.get(1).getRank());
    }

    @Test
    public void testLanguage() {
        List<LanguageTopVO> languageTopVOS = Arrays.asList(new LanguageTopVO(), new LanguageTopVO());
        when(gitMapper.language()).thenReturn(languageTopVOS);

        List<LanguageTopVO> result = gitService.language();

        verify(gitMapper).language();
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getRank());
        assertEquals(2, result.get(1).getRank());
    }

    @Test
    public void testProtocol() {
        List<ProtocolVO> protocolVOS = Arrays.asList(new ProtocolVO(), new ProtocolVO());
        when(gitMapper.protocol()).thenReturn(protocolVOS);

        List<ProtocolVO> result = gitService.protocol();

        verify(gitMapper).protocol();
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getRank());
        assertEquals(2, result.get(1).getRank());
    }

    @Test
    public void testCountryUser() {
        List<CountryUserVO> countryUserVOS = Arrays.asList(new CountryUserVO(), new CountryUserVO());
        when(gitMapper.country_user()).thenReturn(countryUserVOS);

        List<CountryUserVO> result = gitService.country_user();

        verify(gitMapper).country_user();
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getRank());
        assertEquals(2, result.get(1).getRank());
        assertEquals("Unknown", result.get(0).getLocation());
        assertEquals("Unknown", result.get(1).getLocation());
    }

    @Test
    public void testCountryFollowers() {
        List<CountryFollowerVO> countryFollowerVOS = Arrays.asList(new CountryFollowerVO(), new CountryFollowerVO());
        when(gitMapper.country_followers()).thenReturn(countryFollowerVOS);

        List<CountryFollowerVO> result = gitService.country_followers();

        verify(gitMapper).country_followers();
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getRank());
        assertEquals(2, result.get(1).getRank());
        assertEquals("Unknown", result.get(0).getLocation());
        assertEquals("Unknown", result.get(1).getLocation());
    }

    @Test
    public void testLanguageStar() {
        List<LanguageStarVO> languageStarVOS = Arrays.asList(new LanguageStarVO(), new LanguageStarVO());
        when(gitMapper.language_star()).thenReturn(languageStarVOS);

        List<LanguageStarVO> result = gitService.language_star();

        verify(gitMapper).language_star();
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getRank());
        assertEquals(2, result.get(1).getRank());
    }

    @Test
    public void testLanguageTime() {
        List<LanguageVO> languageVOS = Arrays.asList(new LanguageVO(), new LanguageVO());
        when(gitMapper.language_time()).thenReturn(languageVOS);

        List<LanguageVO> result = gitService.language_time();

        verify(gitMapper).language_time();
        assertEquals(2, result.size());
    }

    @Test
    public void testTimeOperateCommit() {
        List<Operate_CommitVO> operateCommitVOS = Arrays.asList(new Operate_CommitVO(), new Operate_CommitVO());
        when(gitMapper.TimeOperateCommit()).thenReturn(operateCommitVOS);

        List<Operate_CommitVO> result = gitService.TimeOperateCommit();

        verify(gitMapper).TimeOperateCommit();
        assertEquals(2, result.size());
    }

    @Test
    public void testLanguagePredict() {
        List<LanguagePreVO> languagePreVOS = Arrays.asList(new LanguagePreVO(), new LanguagePreVO());
        when(gitMapper.language_predict()).thenReturn(languagePreVOS);

        List<LanguagePreVO> result = gitService.language_predict();

        verify(gitMapper).language_predict();
        assertEquals(2, result.size());
    }

    @Test
    public void testTotalNum() {
        List<User_YearVO> userYearVOS = Arrays.asList(new User_YearVO(), new User_YearVO());
        when(gitMapper.total_num()).thenReturn(userYearVOS);

        List<User_YearVO> result = gitService.total_num();

        verify(gitMapper).total_num();
        assertEquals(2, result.size());
    }

    @Test
    public void testActive() {
        List<Language_NumVO> languageNumVOS = Arrays.asList(new Language_NumVO(), new Language_NumVO());
        when(gitMapper.active()).thenReturn(languageNumVOS);

        List<Language_NumVO> result = gitService.active();

        verify(gitMapper).active();
        assertEquals(2, result.size());
    }

}
