package ba.unsa.etf.pnwt.ArticleManagement.service;

import ba.unsa.etf.pnwt.ArticleManagement.model.Article;
import ba.unsa.etf.pnwt.ArticleManagement.exception.ArticleNotFoundException;
import ba.unsa.etf.pnwt.ArticleManagement.repository.ArticleRepository;
import ba.unsa.etf.pnwt.proto.LoggingRequest;
import ba.unsa.etf.pnwt.proto.LoggingResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @GrpcClient("logging")
    ba.unsa.etf.pnwt.proto.LoggingServiceGrpc.LoggingServiceBlockingStub loggingServiceBlockingStub;

    public Article getArticleById(String id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            LoggingRequest loggingRequest = LoggingRequest.newBuilder()
                    .setServiceName("ArticleService")
                    .setControllerName("ArticleController")
                    .setActionUrl("/articlemanagement/article")
                    .setActionType("GET")
                    .setActionResponse("SUCCESS")
                    .build();
            loggingServiceBlockingStub.logRequest(loggingRequest);
            return optionalArticle.get();
        } else {
            LoggingRequest loggingRequest = LoggingRequest.newBuilder()
                    .setServiceName("ArticleService")
                    .setControllerName("ArticleController")
                    .setActionUrl("/articlemanagement/article")
                    .setActionType("GET")
                    .setActionResponse("ERROR")
                    .build();
            loggingServiceBlockingStub.logRequest(loggingRequest);
            throw new ArticleNotFoundException(id);
        }
    }

    public Article createArticle(Article article) {
        LoggingRequest loggingRequest = LoggingRequest.newBuilder()
                .setServiceName("ArticleService")
                .setControllerName("ArticleController")
                .setActionType("POST")
                .setActionResponse("SUCCESS")
                .build();
        LoggingResponse loggingResponse = loggingServiceBlockingStub.logRequest(loggingRequest);

        return articleRepository.save(article);
    }

    public void deleteArticleById(String id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));

        articleRepository.deleteById(id);
    }

    public boolean existsArticleById(String id) {
        return articleRepository.existsById(id);
    }

    public Article updateArticle(Article updatedArticle) {
        return articleRepository.save(updatedArticle);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public List<Article> findArticlesByAuthor(String authorName) {
        return articleRepository.findByAuthor(authorName);
    }

    public List<Article> findArticlesByKeyword(String keyword) {
        return articleRepository.findByKeywordInTitle(keyword);
    }
}