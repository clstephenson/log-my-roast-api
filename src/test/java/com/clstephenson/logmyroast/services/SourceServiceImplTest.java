package com.clstephenson.logmyroast.services;

import com.clstephenson.logmyroast.models.Source;
import com.clstephenson.logmyroast.repositories.SourceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SourceServiceImplTest {

    private static final String SOURCE_1_NAME = "Test source 1";
    private static final String SOURCE_2_NAME = "Test source 2";
    @Mock
    SourceRepository sourceRepositoryMock;
    @InjectMocks
    SourceServiceImpl sourceService;
    Source source1;
    Source source2;
    List<Source> sources;

    @Before
    public void setup() {
        source1 = new Source(SOURCE_1_NAME);
        source2 = new Source(SOURCE_2_NAME);
        sources = Arrays.asList(source1, source2);
    }

    @Test
    public void findAllSources_ReturnListOfSources() {
        when(sourceRepositoryMock.findAll()).thenReturn(sources);
        assertThat(sourceService.findAllSources(), equalTo(sources));

        verify(sourceRepositoryMock, times(1)).findAll();
    }

    @Test
    public void findSourceById_ReturnSource() {
        when(sourceRepositoryMock.findById(anyInt())).thenReturn(Optional.of(source1));
        assertThat(sourceService.findSourceById(1).get(), equalTo(source1));

        verify(sourceRepositoryMock, times(1)).findById(anyInt());
    }

    @Test
    public void save_ReturnSavedSource() {
        Source source3 = new Source("Test Source");
        when(sourceRepositoryMock.save(source3)).thenReturn(source3);
        Source savedSource = sourceService.save(source3);
        assertThat(savedSource.getName(), is("Test Source"));

        verify(sourceRepositoryMock, times(1)).save(any(Source.class));
    }

    @Test
    public void deleteById_repositoryMethodCalled() {
        doNothing().when(sourceRepositoryMock).deleteById(anyInt());
        sourceService.deleteSourceById(1);
        verify(sourceRepositoryMock, times(1)).deleteById(anyInt());
    }
}