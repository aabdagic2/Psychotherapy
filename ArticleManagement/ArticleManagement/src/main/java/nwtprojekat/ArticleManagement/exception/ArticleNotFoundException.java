package nwtprojekat.ArticleManagement.exception;

public class ArticleNotFoundException extends RuntimeException {
    private String error = "validation";
    private String message;

    public ArticleNotFoundException(String id) {
        super("Article with ID " + id + " not found!");
        this.message = "Article with ID " + id + " not found!";
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}