package hello.kpop.place.errorHandler;

public class PlaceResponseMessage {
    public static final String EVENT_REGISTER_SUCCESS = "이벤트 등록 성공";
    public static final String EVENT_REGISTER_FAIL = "이벤트 등록 실패, 로그인이 필요합니다.";
    public static final String OVERLAP_EVENT = "이미 등록된 이벤트입니다.";
    public static final String NOT_FOUND_ARTIST_ID = "아티스트 ID가 존재하지 않습니다.";
    public static final String EVENT_CATEGORY_NOT_ENTERED = "이벤트 카테고리 코드 입력 필요";
    public static final String START_DATE_NOT_ENTERED = "이벤트 시작 날짜 입력 필요";
    public static final String END_DATE_NOT_ENTERED = "이벤트 마감 날짜 입력 필요";
    public static final String NAME_NOT_ENTERED = "이벤트명 입력 필요";
    public static final String ADDRESS_NOT_ENTERED = "이벤트 우편번호 입력 필요";
    public static final String STREET_ADDRESS_NOT_ENTERED = "이벤트 지번주소(기본주소) 입력 필요";
    public static final String FOUND_PLACE_ONE_SUCCESS = "선택한 이벤트 조회 성공";
    public static final String NOT_FOUND_PLACE_ONE = "선택한 이벤트 조회 실패, 이벤트 ID가 존재하지 않습니다.";
    public static final String UPDATE_PLACE_SUCCESS = "이벤트 정보 수정 성공";
    public static final String UPDATE_PLACE_FAIL = "이벤트 정보 수정 실패, 이벤트 ID가 존재하지 않습니다.";
    public static final String DELETE_PLACE_SUCCESS = "이벤트 삭제 성공";
    public static final String DELETE_PLACE_FAIL = "이벤트 삭제 실패, 이벤트 ID가 존재하지 않습니다.";
    public static final String DELETED_PLACE = "삭제된 이벤트입니다.";
}
