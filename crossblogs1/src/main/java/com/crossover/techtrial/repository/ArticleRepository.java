package com.crossover.techtrial.repository;

import java.util.List;
import java.util.concurrent.Future;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.crossover.techtrial.model.Article;
import org.springframework.scheduling.annotation.Async;

@RepositoryRestResource(exported = false)
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

	@Async
	Future<List<Article>> findTop5ByTitleContainingIgnoreCase(String title);

}
