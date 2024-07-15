package hello.kpop.goods.errorHandler;

public class GoodsResponseMessage {
    public static final String GOODS_REGISTER_SUCCESS = "굿즈 등록 성공";
    public static final String UNAUTHORIZED_GOODS_REGISTER = "굿즈 등록 실패, 권한이 없습니다.";
    public static final String NOT_FOUND_ARTIST_ID = "아티스트 ID가 존재하지 않습니다.";
    public static final String GOODS_CATEGORY_NOT_ENTERED = "굿즈 카테고리 코드 입력 필요";
    public static final String NAME_NOT_ENTERED = "굿즈명 입력 필요";
    public static final String PRICE_NOT_ENTERED = "굿즈가격 입력 필요";
    public static final String FOUND_GOODS_ONE_SUCCESS = "선택한 굿즈 조회 성공";
    public static final String NOT_FOUND_GOODS_ONE = "선택한 굿즈 조회 실패, 굿즈 ID가 존재하지 않습니다.";
    public static final String UPDATE_GOODS_SUCCESS = "굿즈 정보 수정 성공";
    public static final String UPDATE_GOODS_FAIL = "굿즈 정보 수정 실패, 굿즈 ID가 존재하지 않습니다.";
    public static final String UNAUTHORIZED_GOODS_UPDATE = "굿즈 수정 실패, 권한이 없습니다.";
    public static final String DELETE_GOODS_SUCCESS = "굿즈 삭제 성공";
    public static final String DELETE_GOODS_FAIL = "굿즈 삭제 실패, 굿즈 ID가 존재하지 않습니다.";
    public static final String UNAUTHORIZED_GOODS_DELETE = "굿즈 삭제 실패, 권한이 없습니다.";
    public static final String DELETED_GOODS = "삭제된 굿즈입니다.";
    public static final String NON_NEGATIVE_VALUES = "양수만 가능합니다.";
}
