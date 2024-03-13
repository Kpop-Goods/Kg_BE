package hello.kpop.agency.errorHandler;

public class AgencyResponseMessage {
    public static final String AGENCY_REGISTER_SUCCESS = "소속사 등록 성공";
    public static final String OVERLAP_AGENCY = "이미 등록된 소속사입니다.";
    public static final String AGENCY_REGISTER_FAIL = "소속사 등록 실패, 권한이 없습니다.";
    public static final String AGENCY_CATEGORY_NOT_ENTERED = "소속사 카테고리 입력 필요";
    public static final String AGENCY_NAME_NOT_ENTERED = "소속사 이름 입력 필요";
    public static final String FOUND_AGENCY_ONE_SUCCESS = "선택한 소속사 조회 성공";
    public static final String NOT_FOUND_AGENCY_ONE = "선택한 소속사 조회 실패, 소속사 ID가 존재하지 않습니다.";
    public static final String UPDATE_AGENCY_SUCCESS = "소속사 정보 수정 성공";
    public static final String UPDATE_AGENCY_FAIL = "소속사 정보 수정 실패, 소속사 ID가 존재하지 않습니다.";
    public static final String DELETE_AGENCY_SUCCESS = "소속사 삭제 성공";
    public static final String DELETE_AGENCY_FAIL = "소속사 삭제 실패, 소속사 ID가 존재하지 않습니다.";
    public static final String DELETED_AGENCY = "삭제된 소속사입니다.";
}
