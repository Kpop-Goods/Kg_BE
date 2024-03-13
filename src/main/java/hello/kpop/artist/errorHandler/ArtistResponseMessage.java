package hello.kpop.artist.errorHandler;

public class ArtistResponseMessage {
    public static final String NOT_FOUND_AGENCY_ID = "소속사 ID가 존재하지 않습니다.";
    public static final String ARTIST_REGISTER_FAIL = "소속사 등록 실패, 로그인이 필요합니다.";
    public static final String ARTIST_CODE_NOT_ENTERED = "아티스트 코드 입력 필요";
    public static final String ARTIST_NAME_NOT_ENTERED = "아티스트 명 입력 필요";
    public static final String ARTIST_COMMENT_NOT_ENTERED = "아티스트 설명 입력 필요";
    public static final String ARTIST_IMAGE_NOT_ENTERED = "아티스트 이미지 입력 필요";
    public static final String OVERLAP_ARTIST_NAME = "이미 등록된 아티스트입니다.";
    public static final String ARTIST_REGISTER_SUCCESS = "아티스트 등록 성공";
    public static final String FOUND_ARTIST_ONE_FAIL = "선택한 아티스트 조회 실패, 아티스트 ID가 존재하지 않습니다.";
    public static final String FOUND_ARTIST_ONE_SUCCESS = "선택한 아티스트 조회 성공";
    public static final String UPDATE_ARTIST_FAIL = "아티스트 정보 수정 실패, 아티스트 ID가 존재하지 않습니다.";
    public static final String UPDATE_ARTIST_SUCCESS = "아티스트 정보 수정 성공";
    public static final String DELETE_ARTIST_FAIL = "아티스트 삭제 실패, 아티스트 ID가 존재하지 않습니다.";
    public static final String DELETE_ARTIST_SUCCESS = "아티스트 삭제 성공";
    public static final String DELETED_ARTIST = "삭제된 아티스트입니다.";
}
