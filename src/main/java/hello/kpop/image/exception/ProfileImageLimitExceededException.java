package hello.kpop.image.exception;

public class ProfileImageLimitExceededException extends RuntimeException {
    public ProfileImageLimitExceededException(String message) {
        super(message);
    }
}