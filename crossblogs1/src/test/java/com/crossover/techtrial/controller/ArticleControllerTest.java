package com.crossover.techtrial.controller;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crossover.techtrial.model.Article;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArticleControllerTest {

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setup() throws Exception {

	}

	@Test
	public void testArticleShouldBeCreated() throws Exception {
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"hello\" }");
		ResponseEntity<Article> resultAsset = template.postForEntity("/articles", article, Article.class);
		Assert.assertNotNull(resultAsset.getBody().getId());
	}

	@Test
	public void testArticleGetArticleById() throws Exception {
		ResponseEntity<Article> resultAsset = template.getForEntity("/articles/4", Article.class);
		Assert.assertNotNull(resultAsset.getBody().getId());
	}

	@Test
	public void testArticleGetArticleByIdNotExist() throws Exception {
		ResponseEntity<Article> resultAsset = template.getForEntity("/articles/40", Article.class);
		Assert.assertNotNull(resultAsset.getStatusCode().is4xxClientError());
	}

	@Test
	public void testUpdateArticle() throws Exception {
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"test@gmail.com\", \"title\": \"hello\" }");
		ResponseEntity<Article> resultAsset = template.exchange("/articles/4",HttpMethod.PUT, article, Article.class);
		Assert.assertNotNull(resultAsset.getBody().getId());
	}

	@Test
	public void testUpdateArticleNotExist() throws Exception {
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"test@gmail.com\", \"title\": \"hello\" }");
		ResponseEntity<Article> resultAsset = template.exchange("/articles/400",HttpMethod.PUT, article, Article.class);
		Assert.assertNotNull(resultAsset.getStatusCode().is4xxClientError());
	}

	@Test
	//searchArticles
	public void testDeleteArticles() throws Exception {
		ResponseEntity<Object> resultAsset = template.exchange("/articles/2",HttpMethod.DELETE,null,Object.class);
		Assert.assertNotNull(resultAsset.getStatusCode().is2xxSuccessful());
	}

	@Test
	public void searchArticleByTitle() throws Exception {
		ResponseEntity<List> results = template.getForEntity("/articles/search?text=how",List.class);
		Assert.assertTrue(results.getBody().size() > 1);
	}

	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(body, headers);
	}
}
