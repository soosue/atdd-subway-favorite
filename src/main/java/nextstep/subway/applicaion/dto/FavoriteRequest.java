package nextstep.subway.applicaion.dto;

public class FavoriteRequest {
    private Long source;
    private Long target;

    public FavoriteRequest(Long source, Long target) {
        this.source = source;
        this.target = target;
    }
}
