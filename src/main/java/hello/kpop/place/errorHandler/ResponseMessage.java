package hello.kpop.place.errorHandler;

public class ResponseMessage {
    public static final String REGISTER_SUCCESS = "장소 등록 성공";
    public static final String REGISTER_FAIL = "장소 등록 실패, 아티스트 ID가 존재하지 않습니다.";
    public static final String FOUND_PLACE_ONE_SUCCESS = "선택한 장소 조회 성공";
    public static final String NOT_FOUND_PLACE_ONE = "선택한 장소 조회 실패, 장소 ID가 존재하지 않습니다.";
    public static final String UPDATE_PLACE_SUCCESS = "장소 정보 수정 성공";
    public static final String UPDATE_PLACE_FAIL = "장소 정보 수정 실패, 장소 ID가 존재하지 않습니다.";
    public static final String DELETE_PLACE_SUCCESS = "장소 삭제 성공";
    public static final String DELETE_PLACE_FAIL = "장소 삭제 실패, 장소 ID가 존재하지 않습니다.";
}
